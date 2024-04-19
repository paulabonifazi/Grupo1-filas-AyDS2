package server;

public class ParametrosDeConexion {
	private int PuertoLibre; 
	private String IP; 
	private String contrase�a;
	private boolean finalizar=false;

	public ParametrosDeConexion(String contrase�a) {
		this.contrase�a=contrase�a;
	}

	public int getPuertoLibre() { //lo utiliza el controlador del servidor (main) para mostrarlo por pantalla
		return PuertoLibre;
	}

	public void setPuertoLibre(int puertoLibre) { //lo utiliza el gestor de conexiones para setear el puerto que utiliza
		PuertoLibre = puertoLibre;
	}

	public String getContrase�a() { //lo utiliza el controlador del servidor (main) para mostrarlo por pantalla
		return contrase�a;
	}

	public void setContrase�a(String contrase�a) { //lo utiliza el controlador del servidor (main) para cambiar el codigo que debe ingresar en la comunicacion para permitirse la conexion (lo interpreta el gestor de conexiones)
		this.contrase�a = contrase�a;
	}

	public boolean isFinalizar() { //lo consulta el gestor de conexiones para verificar que se quiere finalizar (y no sea un error)
		return finalizar;
	}

	public void setFinalizar(boolean finalizar) { //lo setea el controlador del servidor (en este caso el main)
		this.finalizar = finalizar;
	}

	public String getIP() {//lo utiliza el controlador del servidor (main) para mostrarlo por pantalla
		return IP;
	}
	
}
