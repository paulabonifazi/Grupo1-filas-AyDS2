package back;

public class GestorConexion extends Thread {
		private MonitorDeCola cola;
		private MonitorNotificacion bufferSalida;
		private Atenciones historico;
		private Conexiones conexiones;
		private TCPServidor puertoEntrada;
		
		
		public GestorConexion(MonitorDeCola cola, MonitorNotificacion bufferSalida, Atenciones historico,
				Conexiones conexiones) {
			super();
			this.cola = cola;
			this.bufferSalida = bufferSalida;
			this.historico = historico;
			this.conexiones = conexiones;
		}
		
		 @Override
		    public void run() {
			 	try {
					this.puertoEntrada=new TCPServidor();
					conexiones.setPuertoLibre(puertoEntrada.getPuerto());
					while(true) {
						try {
							this.puertoEntrada.aceptarConexion();
							String mensaje=this.puertoEntrada.recibirmensajeDeCliente(0, false);
							if  (mensaje!=null){
								String[] elementos = mensaje.split(";"); // Contrase�a
								
							}
						} catch (ExcepcionErrorAlAceptar | ExcepcionFinConexion | ExcepcionFinTimeoutLectura e) {
							//como se corta la ejecuci�n no se sigue con el codigo y se vuelve a empezar el ciclo
						}
					}
				} catch (ExcepcionNoHayPuertos e) {
					e.printStackTrace(); //ERROR DE NO HAY PUERTOS ES IRRECUPERABLE
				}
		    }

}
