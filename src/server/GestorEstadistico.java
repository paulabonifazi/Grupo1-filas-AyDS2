package server;

import interfaces.IEstado;

import java.util.Iterator;
import java.util.LinkedList;

import Excepciones.*;
import TCP.TCPServidor;
public class GestorEstadistico extends Thread implements IEstado{
			MonitorDeCola cola;
			String IPClienteEsperado;
			Historico historico;
			TCPServidor conexion;
			private LinkedList<Esclavo> listaEsclavos;
			public GestorEstadistico(MonitorDeCola cola,Historico historico, TCPServidor conexion, String IPClienteEsperado,LinkedList<Esclavo> listaEsclavos) {
				this.cola=cola;
				this.conexion=conexion;
				this.IPClienteEsperado=IPClienteEsperado;
				this.historico=historico;
				this.listaEsclavos=listaEsclavos;
			}
			
			@Override
		    public void run() {
				String mensaje = null;
				int desconexiones=0;
			 	try {
			 		this.conexion.aceptarConexion(100000); //espera por 7 segundos
			 		if(conexion.validarIPCliente(IPClienteEsperado)) {
			 			while(desconexiones<2) {
				 			try {
								mensaje=this.conexion.recibirmensajeDeCliente(0, false); //Se recibe como mensaje: "MostrarEstado"
								desconexiones=0;
								if (mensaje.equals("MostrarEstado")) {
					 				this.MostrarEstado();
					 			}
					 			else {
						 			try {
										conexion.enviarMensajeACliente("InstruccionInexistente", false);
									} catch (ExcepcionLecturaErronea e) {
										//nunca ocurre porque no se habilita la comprobacion
									}
					 			}
							} catch (ExcepcionFinTimeoutLectura e) {
								//no hay timeOut por lo que no puede ocurrir
							}
				 			catch(ExcepcionFinConexion|ExcepcionDeInterrupcion e) { //ocurre cuando no se puede recibir mensaje del estadístico
				 					desconexiones++;
									Thread.sleep(500);
				 			}
			 			}
			 		}
			 		try {
						conexion.cerrarConexion();
						conexion.cerrarPuertoServidor();
					} catch (ExcepcionErrorAlCerrar e1) {
						// no puede hacerse nada más que terminar el thread
					}
				} 
			 	catch (ExcepcionErrorAlAceptar | ExcepcionFinTimeoutAceptar e) {
					try {
						conexion.cerrarPuertoServidor(); //por si acaso no se cerro (si se cierra y ya estaba cerrado se tira la excepcion error al cerrar)
					} catch (ExcepcionErrorAlCerrar e1) {
						// no puede hacerse nada más que terminar el thread
					}
				}
			 	catch (ExcepcionDeInterrupcion|InterruptedException e) {
					try {
						conexion.cerrarConexion();
						conexion.cerrarPuertoServidor(); //por si acaso no se cerro (si se cierra y ya estaba cerrado se tira la excepcion error al cerrar)
					} catch (ExcepcionErrorAlCerrar e1) {
						// no puede hacerse nada más que terminar el thread
					}
				}
			}

			@Override
			public void MostrarEstado()  {
				try {
					conexion.enviarMensajeACliente(this.historico.tiempos()+"/"+cola.size()+"$"+IpEsclavos(), false);//estructura de Estado: "ClientesAtendidos/<t.espera,t.solicitud,t.atencion>;...;...;.../ClientesEnEspera"
				} catch (ExcepcionLecturaErronea|ExcepcionFinConexion | ExcepcionDeInterrupcion e) {
					//nunca ocurre porque no se habilita la comprobacion
				}
			}
			
			private String IpEsclavos() {
				String ips="";
				Iterator<Esclavo> it=this.listaEsclavos.iterator();
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

