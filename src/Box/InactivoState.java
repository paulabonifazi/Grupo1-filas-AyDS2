package Box;

public class InactivoState extends StateAbstracta{

	public InactivoState(ControladorVistaOperador controlador) {
		super(controlador);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void asignarCliente() {
		controlador.clienteAsignadoVentana();
		controlador.cambiarEstado(new AtendiendoState(controlador));
	}

}
