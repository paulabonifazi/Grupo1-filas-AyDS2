package Box;

import javax.swing.JOptionPane;

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
		controlador.habilitarBotonCancelarVentana();
	}

	@Override
	public void solicitudCancelada() {
		controlador.cambiarEstado(new InactivoState(controlador));
		controlador.solicitudCanceladaVentana();
	}

	public void cancelarSolicitud() {
		controlador.solicitarCancelacion();
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

	@Override
	public void recibirConfirmacion() {
		// TODO Auto-generated method stub
		
	}


	
	
	
}