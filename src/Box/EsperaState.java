package Box;

public class EsperaState extends StateAbstracta {

	public EsperaState(ControladorVistaOperador controlador) {
		super(controlador);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void asignarCliente() {
		controlador.clienteAsignadoVentana();
		controlador.cambiarEstado(new AtendiendoState(controlador));

	}

	@Override
	public void activarBotonCancelar() {
		controlador.habilitarBotonCancelar();
		
	}
	
}
