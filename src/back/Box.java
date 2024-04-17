package back;

public class Box implements IConexion{
	private String IPCliente;
	private int PuertoServidor;
	private String ID;
	private static int siguienteID=0;
	
	public Box(String IPCliente, int puertoServidor) {
		this.IPCliente=IPCliente;
		this.PuertoServidor=puertoServidor;
		this.ID="B"+siguienteID++;
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
}
