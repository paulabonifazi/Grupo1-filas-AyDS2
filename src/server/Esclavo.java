package server;

public class Esclavo{
	private String ID;
	private int puerto;
	private String IP;
	
	public Esclavo(String id, int puerto, String ip) {
		this.ID=id;
		this.puerto=puerto;
		this.IP=ip;
	}

	public String getID() {
		return ID;
	}

	public int getPuerto() {
		return puerto;
	}

	public String getIP() {
		return IP;
	}
	
	

	public void setID(String iD) {
		ID = iD;
	}

	@Override
	public String toString() {
		return this.ID+","+this.IP+","+this.puerto;
	}
	
	
}
