package back;

public class TvLlamado implements IConexion{
	private String IPCliente;
	private int PuertoServidor;
	private String ID ;
	
	public TvLlamado(String IPCliente, int puertoServidor) {
		this.IPCliente=IPCliente;
		this.PuertoServidor=puertoServidor;
		this.ID="L";
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
