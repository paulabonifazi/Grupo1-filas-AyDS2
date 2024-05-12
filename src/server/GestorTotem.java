package server;

import interfaces.IRegistro;

import java.util.LinkedList;

import Excepciones.*;
import TCP.TCPServidor;
public class GestorTotem  extends Thread implements IRegistro{
	MonitorDeCola cola;
	String IPClienteEsperado;
	TCPServidor conexion;
	LinkedList<Esclavo> listaEsclavos;
	
	public GestorTotem(MonitorDeCola cola, TCPServidor conexion, String IPClienteEsperado) {
		this.cola=cola;
		this.conexion=conexion;
		this.IPClienteEsperado=IPClienteEsperado;
	}
	
	@Override
    public void run() {
		String mensaje = null;
		String[] elementos = null;
		int desconexiones=0;
	 	try {
	 		this.conexion.aceptarConexion(7000); //espera por 7 segundos
	 		if(conexion.validarIPCliente(IPClienteEsperado)) {
	 			while(desconexiones<2) {
	 				try {
						mensaje=this.conexion.recibirmensajeDeCliente(0, false); //Se recibe como mensaje: "Registrar;<DNI>"
						desconexiones=0;
						elementos=mensaje.split(";");
						//No se hace verificacion, la precondicion de registrar es que se recibe un DNI!!! (numerico y con su formato)
			 			if (elementos[0].equals("Registro") && elementos.length >= 2 && !elementos[1].isBlank() && !elementos[1].isEmpty() && elementos[1].matches("\\d{8}")) {
			 				this.registrar(elementos[1]);
			 				}
			 			else {
				 			try {
								conexion.enviarMensajeACliente("InstruccionInexistente", false);
				 			} catch (ExcepcionLecturaErronea e) {
								//nunca ocurre porque no se habilita la comprobacion
							}
			 			}
	 				}
	 				catch(ExcepcionFinConexion |ExcepcionDeInterrupcion e) { //las desconexiones provienen de no poder recibir mensaje del totem
	 					desconexiones++;
	 					Thread.sleep(500);
	 				}
	 			}
	 			//TODO intentó 2 veces conectarse, busca un esclavo hasta poder establecer conexión
	 		}
		} 
	 	catch (ExcepcionErrorAlAceptar | ExcepcionFinTimeoutAceptar e) {
		}
	 	catch (InterruptedException |ExcepcionDeInterrupcion|ExcepcionFinTimeoutLectura e) {
		} 
	 	finally {
	 		try {
				conexion.cerrarPuertoServidor(); //por si acaso no se cerro (si se cierra y ya estaba cerrado se tira la excepcion error al cerrar)
				conexion.cerrarConexion();
	 		} catch (ExcepcionErrorAlCerrar e1) {
				// no puede hacerse nada más que terminar el thread
			}
	 	}
	}
	
	
	@Override
	public void registrar(String DNI) throws  InterruptedException{
		String mensaje = null;
		if(!cola.contiene(DNI)) {
				Turno turno=new Turno(DNI); //al crear el turno se registra la hora
				cola.put(turno);
				mensaje= "Exito";
		}
		else
			mensaje="DniRepetido";
		try {
			conexion.enviarMensajeACliente(mensaje, false);
		} catch (ExcepcionLecturaErronea |ExcepcionFinConexion| ExcepcionDeInterrupcion e) {
			//nunca ocurre porque no se habilita la comprobacion
		}
	}

}
