package server;

import interfaces.IEstado;
import Excepciones.*;
public class GestorEstadistico extends Thread implements IEstado{
			MonitorDeCola cola;
			String IPClienteEsperado;
			Historico historico;
			TCPServidor conexion;
			public GestorEstadistico(MonitorDeCola cola,Historico historico, TCPServidor conexion, String IPClienteEsperado) {
				this.cola=cola;
				this.conexion=conexion;
				this.IPClienteEsperado=IPClienteEsperado;
			}
			
			@Override
		    public void run() {
				String mensaje = null;
				String[] elementos = null;
				String respuesta=null;
			 	try {
			 		this.conexion.aceptarConexion(7000); //espera por 7 segundos
			 		if(conexion.validarIPCliente(IPClienteEsperado)) {
			 			while(true) {
				 			try {
								mensaje=this.conexion.recibirmensajeDeCliente(0, false); //Se recibe como mensaje: "MostrarEstado"
								elementos=mensaje.split(";");
							} catch (ExcepcionFinTimeoutLectura e) {
								//no hay timeOut por lo que no puede ocurrir
							}
				 			if (elementos[0].equals("MostrarEstado")) {
				 				respuesta=this.MostrarEstado();
				 			}
				 			else
				 				respuesta="InstruccionInexistente";
				 			try {
								conexion.enviarMensajeACliente(respuesta, false);
							} catch (ExcepcionLecturaErronea e) {
								//nunca ocurre porque no se habilita la comprobacion
							}
			 			}
			 		}
				} 
			 	catch (ExcepcionErrorAlAceptar | ExcepcionFinTimeoutAceptar | ExcepcionDeInterrupcion|ExcepcionFinConexion e) {
					try {
						conexion.cerrarConexion();
						conexion.cerrarPuertoServidor(); //por si acaso no se cerro (si se cierra y ya estaba cerrado se tira la excepcion error al cerrar)
					} catch (ExcepcionErrorAlCerrar e1) {
						// no puede hacerse nada m�s que terminar el thread
					}
				}
			}

			@Override
			public String MostrarEstado() {
				return this.historico.estado()+"/"+cola.size(); //estructura de Estado: "ClientesAtendidos/<t.espera,t.solicitud,t.atencion>/ClientesEnEspera"
			}

		}
