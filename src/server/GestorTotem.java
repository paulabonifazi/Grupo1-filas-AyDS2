package server;

import interfaces.IRegistro;
import Excepciones.*;
import TCP.TCPServidor;
public class GestorTotem  extends Thread implements IRegistro{
	MonitorDeCola cola;
	String IPClienteEsperado;
	TCPServidor conexion;
	public GestorTotem(MonitorDeCola cola, TCPServidor conexion, String IPClienteEsperado) {
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
						mensaje=this.conexion.recibirmensajeDeCliente(0, false); //Se recibe como mensaje: "Registrar;<DNI>"
						elementos=mensaje.split(";");
		 			//No se hace verificacion, la precondicion de registrar es que se recibe un DNI!!! (numerico y con su formato)
		 			if (elementos[0].equals("Registro") && elementos.length >= 2 && !elementos[1].isBlank() && !elementos[1].isEmpty() && elementos[1].matches("\\d{8}")) {
		 				respuesta=this.registrar(elementos[1]);
		 				if(respuesta.equals("Interrumpido"))
		 					throw new ExcepcionDeInterrupcion(); //no se pudo cargar el elemento en la cola porque se interrumpio al thread, hay que cortar la conexion
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
	 	catch (ExcepcionErrorAlAceptar | ExcepcionFinTimeoutAceptar e) {
			try {
				conexion.cerrarPuertoServidor(); //por si acaso no se cerro (si se cierra y ya estaba cerrado se tira la excepcion error al cerrar)
			} catch (ExcepcionErrorAlCerrar e1) {
				// no puede hacerse nada m�s que terminar el thread
			}
		}
	 	catch (ExcepcionDeInterrupcion|ExcepcionFinConexion|ExcepcionFinTimeoutLectura e) {
			try {
				conexion.cerrarConexion();
				conexion.cerrarPuertoServidor(); //por si acaso no se cerro (si se cierra y ya estaba cerrado se tira la excepcion error al cerrar)
			} catch (ExcepcionErrorAlCerrar e1) {
				// no puede hacerse nada m�s que terminar el thread
			}
		}
	}
	
	
	@Override
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
	}

}
