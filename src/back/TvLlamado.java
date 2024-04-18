package back;

public class TvLlamado implements IConexion{
	private TCPServidor conexion;
	private String ID ;
	private Thread hilo;
	
	public TvLlamado(TCPServidor conexion, Thread hilo) {
		this.conexion=conexion;
		this.ID="L";
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
		this.conexion.cerrarPuertoServidor();
	}
	
}
