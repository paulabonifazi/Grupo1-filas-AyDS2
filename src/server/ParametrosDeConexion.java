package server;

public class ParametrosDeConexion {
	private int PuertoLibre; 
	private String IP; 
	private String contraseña;
	private boolean finalizar=false;

	public ParametrosDeConexion(String contraseña) {
		this.contraseña=contraseña;
	}

	public int getPuertoLibre() { //lo utiliza el controlador del servidor (main) para mostrarlo por pantalla
		return PuertoLibre;
	}

	public void setPuertoLibre(int puertoLibre) { //lo utiliza el gestor de conexiones para setear el puerto que utiliza
		PuertoLibre = puertoLibre;
	}

	public String getContraseña() { //lo utiliza el controlador del servidor (main) para mostrarlo por pantalla
		return contraseña;
	}

	public void setContraseña(String contraseña) { //lo utiliza el controlador del servidor (main) para cambiar el codigo que debe ingresar en la comunicacion para permitirse la conexion (lo interpreta el gestor de conexiones)
		this.contraseña = contraseña;
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
