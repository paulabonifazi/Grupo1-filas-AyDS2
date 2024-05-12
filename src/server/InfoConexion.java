package server;

public class InfoConexion {
	String ID;
	String IP;
	int puerto;
	public InfoConexion(String id, String ip, String puerto) {
		this.ID=id;
		this.IP=ip;
		this.puerto=Integer.parseInt(puerto);;
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
