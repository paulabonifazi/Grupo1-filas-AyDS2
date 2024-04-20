package Box;

public abstract class StateAbstracta {
	ControladorVistaOperador controlador;

	public StateAbstracta(ControladorVistaOperador controlador) {
		super();
		this.controlador = controlador;
	}

	public abstract void solicitarCliente();
	
	public abstract void solicitudCancelada();
	
	public abstract void asignarCliente(String dni);
	
	public abstract void activarBotonCancelar();
	
	public abstract void finalizarAtencionAusente();
	
	public abstract void finalizarAtencionAtendido();
}
