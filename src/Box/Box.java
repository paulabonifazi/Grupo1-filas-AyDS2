package Box;

import Excepciones.ExcecionErrorAlCerrar;
import Servidor.IConexion;
import TCP.TCPServidor;

public class Box implements IConexion{
	private TCPServidor conexion;
	private String ID;
	private Thread hilo;
	
	public Box(TCPServidor conexion, Thread hilo,String ID) {
		this.conexion=conexion;
		this.ID=ID;
		this.hilo= hilo;
	}

	@Override
	public int getPuerto() {
		return conexion.getPuerto();
	}

	@Override
	public String getIP() {
		return conexion.getIPCliente();
	}
	
	@Override
	public String getID() {
		return ID;
	}
	
	public boolean isConectado() {
		return this.hilo.isAlive();
	}
	
	@Override
	public void cerrarConexion() throws ExcecionErrorAlCerrar {
		this.conexion.cerrarPuertoServidor();
		this.hilo.interrupt(); //en caso de que este dormido en la cola
	}
	
}