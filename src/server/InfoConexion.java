package server;

public class InfoConexion {
	private String ID;
	private String IP;
	private int puerto;
	public InfoConexion(String id, String ip, String puerto) {
		this.ID=id;
		this.IP=ip;
		this.puerto=Integer.parseInt(puerto);;
	}
	
	public InfoConexion(String id, String ip, int puerto) {
		this.ID=id;
		this.IP=ip;
		this.puerto=puerto;
	}

	
	public int getPuerto() {
		return puerto;
	}

	
	public String getIP() {
		return IP;
	}
	
	
	public String getID() {
		return ID;
	}
	
	
}
