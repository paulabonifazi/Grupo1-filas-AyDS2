package Box;

public abstract class StateAbstracta {
	ControladorVistaOperador controlador;

	public StateAbstracta(ControladorVistaOperador controlador) {
		super();
		this.controlador = controlador;
	}

	public abstract void asignarCliente();
	
	public abstract void activarBotonCancelar();
}
