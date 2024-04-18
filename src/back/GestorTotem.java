package back;

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
		 				if(this.registrar(elementos[1])) 
		 					respuesta="Exito";
		 				else {
		 					throw new ExcepcionDeInterrupcion(); //no se pudo cargar el elemento en la cola porque se interrumpio al thread, hay que cortar la conexion
		 				}
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
	public Boolean registrar(String DNI){
		Turno turno=new Turno(DNI);
		try {
			cola.put(turno);
			return true;
		} catch (InterruptedException e) {
			return false;
		}
	}

}
