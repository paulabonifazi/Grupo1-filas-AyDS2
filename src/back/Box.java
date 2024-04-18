package back;

public class Box implements IConexion{
	private String IPCliente;
	private int PuertoServidor;
	private String ID;
	private Thread hilo;
	private static int siguienteID=0;
	
	public Box(String IPCliente, int puertoServidor, Thread hilo) {
		this.IPCliente=IPCliente;
		this.PuertoServidor=puertoServidor;
		this.ID="B"+siguienteID++;
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
}
