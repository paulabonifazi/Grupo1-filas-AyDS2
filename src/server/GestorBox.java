package server;

import java.util.Iterator;
import java.util.LinkedList;

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
	private GestorSolicitud buscaSolicitud;
	private Turno turno;
	private Solicitud solicitud;
	private Atencion atencion;
	private int desconexiones=0;
	private LinkedList<Esclavo> listaEsclavos;
	
	
	public GestorBox(MonitorDeCola cola, MonitorNotificacion llamados, Historico historico, TCPServidor conexion,String IPClienteEsperado,String IDBox,LinkedList<Esclavo> listaEsclavos) {
		super();
		this.cola = cola;
		this.llamados = llamados;
		this.historico = historico;
		this.conexion = conexion;
		this.ipClienteEsperado=IPClienteEsperado;
		this.IDBox=IDBox;
		this.listaEsclavos=listaEsclavos;
		this.turno=new Turno();
	}


	@Override
    public void run() {
		String mensaje = null;
		String DNI;
		desconexiones=0;
	 	try {
	 		this.conexion.aceptarConexion(600000);
	 		if(conexion.validarIPCliente(ipClienteEsperado)) {
	 			while(desconexiones<2) {
	 				mensaje = null;
		 			try {
						mensaje=this.conexion.recibirmensajeDeCliente(0, false); //Se recibe como mensaje: "<Operacion>"
						desconexiones=0;
						switch (mensaje) {
		                    case "solicitudTurno":
		                    	if(cola.hasAtencion(IDBox)==null) {
		                    		solicitudTurno();
		                    	}
		                    	else {
		                    		enviarMensaje(conexion,"Tiene una atencion pendiente");
		                    	}
		                        break;
		                    case "Cancelar":
		                    	enviarMensaje(conexion,"DebeTenerSolicitud1ro");
		                        break;
		                    case "Fin":
		                    		DNI=cola.finAtencion(IDBox);
			                    	if(DNI!=null) {
			                    		Atencion atencion= new Atencion(new Turno(DNI), new Solicitud(IDBox));
			                    		atencion.registrarFin();
										historico.agregarAtencion(atencion); //se agrega la atencion al historico
										enviarMensaje(conexion,"Recibido");
			                    	}
			                    	else
			                    		enviarMensaje(conexion,"DebeTenerAtencion1ro");
		                    	break;
		                    case "Ausente":
		                    		DNI=cola.finAtencion(IDBox);
		                    	if(DNI!=null) {
		                    		cola.put(new Turno(DNI));
		                    		enviarMensaje(conexion,"Recibido");
		                    	}
		                    	else
		                    		enviarMensaje(conexion,"DebeTenerAtencion1ro");
		                        break;
		                    default:
		                    	enviarMensaje(conexion,"InstruccionInexistente");
				 			}
					} catch (ExcepcionFinTimeoutLectura e) {
						//no hay timeOut por lo que no puede ocurrir
					}
		 			catch (ExcepcionFinConexion|ExcepcionDeInterrupcion e) { //ocurre unicamente si no se puede recibir mensaje del box
		 				if (desconexiones<2) {
							desconexiones++;
							Thread.sleep(500);
		 				}
					}
	 			}
	 		}
		} 
	 	catch (ExcepcionErrorAlAceptar | ExcepcionFinTimeoutAceptar e) {
	 	}
	 	catch (InterruptedException |ExcepcionDeInterrupcion e) {
		}
	 	finally {
	 		try {
	 			if(buscaSolicitud!=null && buscaSolicitud.isAlive())
					this.buscaSolicitud.interrupt();
	 			DNI=cola.finAtencion(IDBox);
	 			if(DNI!=null) {
	 				try {
	 					if(turno.getDni()!=null ) {
	 						cola.put(turno);
	 					}
	 					else
	 						cola.put(new Turno(DNI));
					} catch (InterruptedException e) {
					}
	 			}
				conexion.cerrarPuertoServidor(); //por si acaso no se cerro (si se cierra y ya estaba cerrado se tira la excepcion error al cerrar)
				conexion.cerrarConexion();
			} catch (ExcepcionErrorAlCerrar e1) {
				// no puede hacerse nada mas que terminar el thread
			}
	 	}
	}
	
	
	private void enviarMensaje(TCPServidor conexion, String mensaje) throws ExcepcionFinConexion, ExcepcionDeInterrupcion {
		try {
			
			conexion.enviarMensajeACliente(mensaje, false); //se envia el estado de la cola de turnos
		} catch (ExcepcionLecturaErronea a) {
			//nunca ocurre porque no se habilita la comprobacion
		}
	}
	


	@Override
	public void solicitudTurno() throws ExcepcionFinConexion, ExcepcionDeInterrupcion, InterruptedException  {
		String mensaje=null;
		this.turno=new Turno(); //crea un nuevo turno (por si se corta la comunicacion verificar que no se retiro dni)
		buscaSolicitud=new GestorSolicitud(this.turno,cola,IDBox); //va y espera en la cola por una asignacion de turno, mientras que el gestor de box espera por un mensaje del operario (revisando cuando está el turno solicitado)
        solicitud=new Solicitud(IDBox); //se crea la solicitud para registrar la hora de solicitud del turno
        buscaSolicitud.start(); //se ejecuta el thread que busca el turno
		Thread.sleep(1000);//espera 0,5 segundos por el resultado del turno
        
		Boolean fin;
		if(turno.getDni()==null) { // si todavia no lo consiguio, avisa al cliente que puede activar el boton cancelar
        	enviarMensaje(conexion, "ActCancelar");
        	//una vez que activa el boton cancelar vuelve a la espera de un mensaje pero por un determinado tiempo
        	fin=false;
        	while(!fin && desconexiones<2) {
        		mensaje=null;
        		try {
					mensaje=this.conexion.recibirmensajeDeCliente(5000, false); //Se espera por 5 segundos el cancelar y se recibe como mensaje: "<Operacion>"
					desconexiones=0;
					if(mensaje.equals("Cancelar")){
						fin=true;
						if(turno.getDni()==null) { //si todavia no se encontro... se corta la busqueda
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
						enviarMensaje(conexion,"Estado;"+cola.size()+";"+IpEsclavos());
					} //y vuelve al ciclo (a esperar por mensaje del cliente)
				}catch (ExcepcionFinConexion | ExcepcionDeInterrupcion e) { // ocurre unicametne si no se puede recibir mensaje del box
					desconexiones++;
					Thread.sleep(500);
				}
       
        	}
		}
		if(desconexiones<2) {
        	if(turno.getDni()==null) {
        			enviarMensaje(conexion,"Cancelado");
        	}
        	else {
        		atencion=new Atencion(turno, solicitud); //registra la hora de comienzo de la atencion
        		enviarMensaje(conexion,"Atencion;"+turno.getDni()+";"+IpEsclavos());
        		llamados.put(new Llamado(atencion.getDNI(), atencion.getBox())); //se coloca el llamado en el buffer para mostrarlo por pantalla (en caso de que este activado)
        		//se debe esperar por la ausencia o por el fin de atencion
        		mensaje=null;
        		fin=false;
        		while(!fin && desconexiones<2) {
	        		try {
						mensaje=this.conexion.recibirmensajeDeCliente(0, false);//Se espera por mensaje y se recibe como mensaje: "<Operacion>"
						desconexiones=0;
						if(mensaje.equals("Fin")) {
							cola.finAtencion(IDBox);
							fin=true;
							atencion.registrarFin(); //se registra la hora del fin de la atencion
							historico.agregarAtencion(atencion); //se agrega la atencion al historico
							enviarMensaje(conexion,"Recibido");
						}
						else {
							if(mensaje.equals("Ausente")) {
								cola.finAtencion(IDBox);
								fin=true;
								if(turno.getAusencias()<1) {
									turno.addAusencia();
									cola.put(turno);
								}
								else
									llamados.put(new Llamado(atencion.getDNI(), ""));
								enviarMensaje(conexion,"Recibido");
							}
							else
								if(!mensaje.equals("Cancelar"))//por si a caso llego un cancelar no detectado
									enviarMensaje(conexion,"ErrorOperacion");
						}
	        		} catch (ExcepcionFinTimeoutLectura  e) {
						//no puede ocurrir, porque no hay timeout
					} catch (ExcepcionFinConexion |ExcepcionDeInterrupcion e) { //ocurre en caso de que no se pueda recibir mensaje del box
						desconexiones++;
						Thread.sleep(500);
					}
        		}
        	}
		}
	}
	
	private String IpEsclavos() {
		String ips="";
		Iterator<Esclavo> it=listaEsclavos.iterator();
		Esclavo actual;
		while(it.hasNext()) {
			actual=it.next();
			ips+=actual.getIP();
			if(it.hasNext()) {
				ips+="$";
			}
		}
		return ips;
	}
	
}
