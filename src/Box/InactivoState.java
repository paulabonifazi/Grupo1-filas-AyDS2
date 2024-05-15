package Box;

public class InactivoState extends StateAbstracta{

	public InactivoState(ControladorVistaOperador controlador) {
		super(controlador);
	}

	@Override
	public void solicitarCliente() {
		controlador.cambiarEstado(new EsperaState(controlador));
		controlador.solicitarCliente();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finalizarAtencionAtendido() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelarSolicitud() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recibirConfirmacion() {
		// TODO Auto-generated method stub
		
	}

}