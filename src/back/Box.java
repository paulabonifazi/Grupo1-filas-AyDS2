package back;

public class Box implements IConexion{
	private String IPCliente;
	private int PuertoServidor;
	private String ID;
	private Thread hilo;
	
	public Box(String IPCliente, int puertoServidor, Thread hilo,String ID) {
		this.IPCliente=IPCliente;
		this.PuertoServidor=puertoServidor;
		this.ID=ID;
		this.hilo= hilo;
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