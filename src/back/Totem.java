package back;

public class Totem implements IConexion{
	private String IPCliente;
	private int PuertoServidor;
	private String ID;
	private Thread hilo;
	private static int siguienteID=0;
	
	public Totem(String IPCliente, int puertoServidor,Thread hilo) {
		this.IPCliente=IPCliente;
		this.PuertoServidor=puertoServidor;
		this.ID="T"+siguienteID++;
		this.hilo=hilo;
	}

	@Override
	public int getPuerto() {
		return PuertoServidor;
	}

	@Override
	public String getIP() {
		return IPCliente;
	}
	
	@Override
	public String getID() {
		return ID;
	}

	public boolean isConectado() {
		return this.hilo.isAlive();
	}
	
	@Override
	public void cerrarConexion() {
		this.hilo.interrupt();
	}
	
	
}
