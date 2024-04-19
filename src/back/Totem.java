package back;

import Excepciones.ExcecionErrorAlCerrar;

public class Totem implements IConexion{
	private TCPServidor conexion;
	private String ID;
	private Thread hilo;
	private static int siguienteID=0;
	
	public Totem(TCPServidor conexion,Thread hilo) {
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
	public void cerrarConexion() throws ExcecionErrorAlCerrar {
		this.conexion.cerrarPuertoServidor(); //en caso de que este dormido en un metodo .net
		this.hilo.interrupt(); //en caso de que este dormido en la cola
	}
	
	
}
