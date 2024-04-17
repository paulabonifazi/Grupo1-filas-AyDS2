package back;

public class ExcepcionNoHayPuertos extends Exception {

	private static final long serialVersionUID = 1L;

	public ExcepcionNoHayPuertos() {
		super("ERROR: SERVIDOR SIN PUERTOS DISPONIBLES");
	}
	
}
