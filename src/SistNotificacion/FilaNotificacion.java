package SistNotificacion;

public class FilaNotificacion {
	private int segundosMostrado;
	private String dni;
	private String box;
	
	
	public FilaNotificacion(String dni, String box) {
		super();
		this.segundosMostrado = 30;
		this.dni = dni;
		this.box = box;
	}
	
	public FilaNotificacion() {
		super();
		this.segundosMostrado = 99999999; //creo que con un tiempo grande los llamados no se borrarían
		this.dni = "";
		this.box = "";
	}
	
	public void pasarSegundo() {
		this.segundosMostrado--;
	}
	
	public boolean finalizoTiempo() {
		return (segundosMostrado<=0);
	}


	public String getDni() {
		return dni;
	}


	public String getBox() {
		return box;
	}

	
}
