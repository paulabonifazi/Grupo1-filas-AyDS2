package server;

import interfaces.IEstado;
import Excepciones.*;
import TCP.TCPServidor;
public class GestorEstadistico extends Thread implements IEstado{
			MonitorDeCola cola;
			String IPClienteEsperado;
			Historico historico;
			TCPServidor conexion;
			public GestorEstadistico(MonitorDeCola cola,Historico historico, TCPServidor conexion, String IPClienteEsperado) {
				this.cola=cola;
				this.conexion=conexion;
				this.IPClienteEsperado=IPClienteEsperado;
				this.historico=historico;
			}
			
			@Override
		    public void run() {
				String mensaje = null;
			 	try {
			 		this.conexion.aceptarConexion(7000); //espera por 7 segundos
			 		if(conexion.validarIPCliente(IPClienteEsperado)) {
			 			while(true) {
				 			try {
								mensaje=this.conexion.recibirmensajeDeCliente(0, false); //Se recibe como mensaje: "MostrarEstado"
							} catch (ExcepcionFinTimeoutLectura e) {
								//no hay timeOut por lo que no puede ocurrir
							}
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
			 	catch (ExcepcionDeInterrupcion|ExcepcionFinConexion e) {
					try {
						conexion.cerrarConexion();
						conexion.cerrarPuertoServidor(); //por si acaso no se cerro (si se cierra y ya estaba cerrado se tira la excepcion error al cerrar)
					} catch (ExcepcionErrorAlCerrar e1) {
						// no puede hacerse nada más que terminar el thread
					}
				}
			}

			@Override
			public void MostrarEstado() throws ExcepcionFinConexion, ExcepcionDeInterrupcion {
				try {
					conexion.enviarMensajeACliente(this.historico.estado()+"/"+cola.size(), false);//estructura de Estado: "ClientesAtendidos/<t.espera,t.solicitud,t.atencion>;...;...;.../ClientesEnEspera"
				} catch (ExcepcionLecturaErronea e) {
					//nunca ocurre porque no se habilita la comprobacion
				}
			}

		}

