package server;

import Excepciones.ExcepcionDeInterrupcion;
import Excepciones.ExcepcionErrorAlAceptar;
import Excepciones.ExcepcionErrorAlCerrar;
import Excepciones.ExcepcionFinConexion;
import Excepciones.ExcepcionFinTimeoutAceptar;
import Excepciones.ExcepcionFinTimeoutLectura;
import Excepciones.ExcepcionLecturaErronea;
import TCP.TCPServidor;
import interfaces.IAtencion;

public class GestorBox extends Thread implements IAtencion{
	private MonitorDeCola cola;
	private MonitorNotificacion llamados;
	private Historico historico;
	private TCPServidor conexion;
	private String ipClienteEsperado;
	private String IDBox;
	
	
	public GestorBox(MonitorDeCola cola, MonitorNotificacion llamados, Historico historico, TCPServidor conexion,String IPClienteEsperado,String IDBox) {
		super();
		this.cola = cola;
		this.llamados = llamados;
		this.historico = historico;
		this.conexion = conexion;
		this.ipClienteEsperado=IPClienteEsperado;
		this.IDBox=IDBox;
	}


	@Override
    public void run() {
		String mensaje = null;
		GestorSolicitud buscaSolicitud=null;
		Turno turno=null;
		Solicitud solicitud=null;
		Atencion atencion=null;
		Boolean fin;
	 	try {
	 		this.conexion.aceptarConexion(7000); //espera por 7 segundos
	 		if(conexion.validarIPCliente(ipClienteEsperado)) {
	 			while(true) {
	 				mensaje = null;
	 				buscaSolicitud=null;
	 				turno=null;
	 				solicitud=null;
	 				atencion=null;
		 			try {
						mensaje=this.conexion.recibirmensajeDeCliente(0, false); //Se recibe como mensaje: "<Operacion>"+
					} catch (ExcepcionFinTimeoutLectura e) {
						//no hay timeOut por lo que no puede ocurrir
					}
		 			switch (mensaje) {
	                    case "solicitudTurno":
	                    	turno=new Turno(null);
	                        buscaSolicitud=new GestorSolicitud(turno,cola); //va y espera en la cola por una asignacion de turno, mientras que el gestor de box espera por un mensaje del operario (revisando cuando está el turno solicitado)
	                        solicitud=new Solicitud(IDBox); //se crea la solicitud para registrar la hora de solicitud del turno
	                        buscaSolicitud.start(); //se ejecuta el thread que busca el turno
	                        Thread.sleep(500); //espera 0,5 segundos por el resultado del turno
	                        if(turno.getDni()==null) { // si todavia no lo consiguio, avisa al cliente que puede activar el boton cancelar
	                        	enviarMensaje(conexion, "ActCancelar");
	                        	//una vez que activa el boton cancelar vuelve a la espera de un mensaje pero por un determinado tiempo
	                        	fin=false;
	                        	while(fin) {
	                        		mensaje=null;
	                        		try {
	            						mensaje=this.conexion.recibirmensajeDeCliente(5000, false); //Se espera por 5 segundos el cancelar y se recibe como mensaje: "<Operacion>"
	            						if(mensaje.equals("Cancelar")){
	            							fin=true;
	            							if(turno.getDni()!=null) {
	            								buscaSolicitud.interrupt();
	            							}
	            						}
	            						else {
	            							enviarMensaje(conexion, "ErrorDeoperacion");
	            						}
	            					} catch (ExcepcionFinTimeoutLectura e) {
	            						if(turno.getDni()!=null) {
	            							fin=true;
	            						}
	            						else { //mostrar estado
	            							enviarMensaje(conexion,"Estado;"+cola.size());
	            						} //y vuelve al ciclo (a esperar por mensaje del cliente)
	            					}
	                        	}
	                        	if(turno.getDni()==null) {
	                        		enviarMensaje(conexion,"Cancelado");
	                        	}
	                        	else {
	                        		atencion=new Atencion(turno, solicitud); //registra la hora de comienzo de la atencion
	                        		enviarMensaje(conexion,"Atencion;"+turno.getDni());
	                        		//se debe esperar por la ausencia o por el fin de atencion
	                        		mensaje=null;
	                        		try {
										mensaje=this.conexion.recibirmensajeDeCliente(0, false);//Se espera por mensaje y se recibe como mensaje: "<Operacion>"
									} catch (ExcepcionFinTimeoutLectura e) {
										//no puede ocurrir, porque no hay timeout
									} 
            						if(mensaje.equals("Fin")) {
            							atencion.registrarFin(); //se registra la hora del fin de la atencion
            							historico.agregarAtencion(atencion); //se agrega la atencion al historico
            							llamados.put(atencion); //se coloca el llamado en el buffer para mostrarlo por pantalla
            						}
            						else {
            							if(mensaje.equals("ausente")) {
            								if(turno.getAusenias()<1) {
            									turno.addAusencia();
            									cola.put(turno);
            								}
            								//si no es su primera ausencia entonces no se vuelve a reingresar y se pierde el turno
            							}
            							else
            								enviarMensaje(conexion,"ErrorOperacion");
            						}
	                        	}
	                  
	                        }
	                        break;
	                    case "cancelarSolicitud":
	                    	enviarMensaje(conexion,"DebeTenerSolicitud1ro");
	                        break;
	                    case "finalizarAtencion":
	                    case "ausencia":
	                    	enviarMensaje(conexion,"DebeTenerAtencion1ro");
	                        break;
	                    default:
	                    	enviarMensaje(conexion,"InstruccionInexistente");
			 			}
	 			}
	 		}
		} 
	 	catch (ExcepcionErrorAlAceptar | ExcepcionFinTimeoutAceptar e) {
			try {
				conexion.cerrarPuertoServidor(); //por si acaso no se cerro (si se cierra y ya estaba cerrado se tira la excepcion error al cerrar)
			} catch (ExcepcionErrorAlCerrar e1) {
				// no puede hacerse nada más que terminar el thread
			}
		}
	 	catch (ExcepcionDeInterrupcion|ExcepcionFinConexion|InterruptedException e) {
			try {
				conexion.cerrarConexion();
				conexion.cerrarPuertoServidor(); //por si acaso no se cerro (si se cierra y ya estaba cerrado se tira la excepcion error al cerrar)
			} catch (ExcepcionErrorAlCerrar e1) {
				// no puede hacerse nada más que terminar el thread
			}
		}
	}
	
	
	private void enviarMensaje(TCPServidor conexion, String mensaje) throws ExcepcionFinConexion, ExcepcionDeInterrupcion {
		try {
			conexion.enviarMensajeACliente(Integer.toString(this.cola.size()), false); //se envia el estado de la cola de turnos
		} catch (ExcepcionLecturaErronea a) {
			//nunca ocurre porque no se habilita la comprobacion
		}
	}
	
	
	
	
	
	/*@Override
	public String registrar(String DNI){
		try {
			if(!cola.contiene(DNI)) {
				Turno turno=new Turno(DNI); //al crear el turno se registra la hora
				cola.put(turno);
				return "Exito";
			}
			return "DniRepetido";
		} catch (InterruptedException e) {
			return "Interrumpido";
		}
	}*/


	@Override
	public String solicitudTurno() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String cancelarSolicitud() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String finalizarAtencion() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String ausencia() {
		// TODO Auto-generated method stub
		return null;
	}

}
