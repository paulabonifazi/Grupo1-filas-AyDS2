package back;

public class Totem implements IConexion{
	private String IPCliente;
	private int PuertoServidor;
	private String ID;
	private static int siguienteID;
	
	public Totem(String IPCliente, int puertoServidor) {
		this.IPCliente=IPCliente;
		this.PuertoServidor=puertoServidor;
		this.ID="T"+siguienteID++;
	}

	@Override
	public int getPuerto() {
		return this.getPuerto();
	}

	@Override
	public String getIP() {
		return this.getIP();
	}
	
	@Override
	public String getID() {
		return this.getID();
	}
}
