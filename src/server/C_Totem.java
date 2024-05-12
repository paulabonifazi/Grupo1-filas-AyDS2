package server;
import Excepciones.*;
import TCP.TCPServidor;

public class C_Totem implements IConexion{
	private TCPServidor conexion;
	private String ID;
	private Thread hilo;
	private static int siguienteID=0;
	
	public C_Totem(TCPServidor conexion,Thread hilo) {
		this.conexion=conexion;
		this.ID="T"+siguienteID++;
		this.hilo=hilo;
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
		this.conexion.cerrarConexion();
		this.conexion.cerrarPuertoServidor(); //en caso de que este dormido en un metodo .net
		
		this.hilo.interrupt(); //en caso de que este dormido en la cola
	}
	
	@Override
	public String toString() {
		return ID+","+conexion.getIPCliente()+","+conexion.getPuerto();
	}
}
