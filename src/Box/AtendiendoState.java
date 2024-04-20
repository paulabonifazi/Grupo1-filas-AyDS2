package Box;

public class AtendiendoState extends StateAbstracta{

	public AtendiendoState(ControladorVistaOperador controlador) {
		super(controlador);
	}

	@Override
	public void solicitarCliente() {	
	}

	@Override
	public void solicitudCancelada() {	
	}

	@Override
	public void asignarCliente(String dni) {	
	}

	@Override
	public void activarBotonCancelar() {
	}

	@Override
	public void finalizarAtencionAusente() {
		controlador.cambiarEstado(new InactivoState(controlador));
	}

	@Override
	public void finalizarAtencionAtendido() {
		controlador.cambiarEstado(new InactivoState(controlador));
	}



}
