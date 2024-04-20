package Box;

public class EsperaState extends StateAbstracta {

	public EsperaState(ControladorVistaOperador controlador) {
		super(controlador);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void solicitarCliente() {	
	}

	@Override
	public void activarBotonCancelar() {
		controlador.habilitarBotonCancelar();
	}

	@Override
	public void solicitudCancelada() {
		controlador.cambiarEstado(new InactivoState(controlador));
	}

	@Override
	public void asignarCliente(String dni) {
		controlador.cambiarEstado(new AtendiendoState(controlador));
		controlador.clienteAsignado(dni);
	}

	@Override
	public void finalizarAtencionAusente() {	
	}

	@Override
	public void finalizarAtencionAtendido() {
	}


	
	
	
}
