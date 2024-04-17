package back;

public class ExcepcionLecturaErronea extends Exception {
	private static final long serialVersionUID = 1L;
	private String mensajeleido;

	public ExcepcionLecturaErronea(String mensajeleido) {
		super();
		this.mensajeleido=mensajeleido;
	}

	public String getMensajeleido() {
		return mensajeleido;
	}	
}
