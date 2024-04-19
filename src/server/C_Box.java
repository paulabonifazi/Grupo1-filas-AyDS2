package server;

import Excepciones.*;
import TCP.TCPServidor;

public class C_Box implements IConexion{
	private TCPServidor conexion;
	private String ID;
	private Thread hilo;
	
	public C_Box(TCPServidor conexion, Thread hilo,String ID) {
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
	public void cerrarConexion() throws ExcepcionErrorAlCerrar {
		this.conexion.cerrarPuertoServidor();
		this.hilo.interrupt(); //en caso de que este dormido en la cola
	}
	
}