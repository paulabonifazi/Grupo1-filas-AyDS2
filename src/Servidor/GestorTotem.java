package Servidor;

import Excepciones.ExcecionErrorAlCerrar;
import Excepciones.ExcepcionDeInterrupcion;
import Excepciones.ExcepcionErrorAlAceptar;
import Excepciones.ExcepcionFinConexion;
import Excepciones.ExcepcionFinTimeoutAceptar;
import Excepciones.ExcepcionFinTimeoutLectura;
import Excepciones.ExcepcionLecturaErronea;
import TCP.TCPServidor;
import interfaces.IRegistro;

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
		 			try {
						mensaje=this.conexion.recibirmensajeDeCliente(0, false); //Se recibe como mensaje: "Registrar;<DNI>"
						elementos=mensaje.split(";");
					} catch (ExcepcionFinTimeoutLectura e) {
						//no hay timeOut por lo que no puede ocurrir
					}
		 			//No se hace verificacion, la precondicion de registrar es que se recibe un DNI!!! (numerico y con su formato)
		 			if (elementos[0].equals("Registro")) {
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
	 	catch (ExcepcionErrorAlAceptar | ExcepcionFinTimeoutAceptar | ExcepcionDeInterrupcion|ExcepcionFinConexion e) {
			try {
				conexion.cerrarConexion();
				conexion.cerrarPuertoServidor(); //por si acaso no se cerro (si se cierra y ya estaba cerrado se tira la excepcion error al cerrar)
			} catch (ExcecionErrorAlCerrar e1) {
				// no puede hacerse nada más que terminar el thread
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
