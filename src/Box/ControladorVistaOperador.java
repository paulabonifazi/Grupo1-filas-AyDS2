package Box;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

import TCP.TCPCliente;

public class ControladorVistaOperador implements ActionListener {
	
	private IVistaOperador vista;
	private EnviadorMensajes enviadorMensajes;
	private BlockingQueue<String> colamensajes;
	private GestorCliente gcliente;
	private StateAbstracta ventanaState;
	private int numeroBox;
	private int tamCola;
	private String dni;
	
	public ControladorVistaOperador() {
		super();
		ventanaState=new InactivoState(this);
	}
	

	public void setVista(IVistaOperador vista) {
		this.vista = vista;
		vista.
	}


	
	public void setEnviadorMensajes(EnviadorMensajes enviadorMensajes) {
		this.enviadorMensajes = enviadorMensajes;
	}

	public void setColamensajes(BlockingQueue<String> colamensajes) {
		this.colamensajes = colamensajes;
	}

	public void setGcliente(GestorCliente gcliente) {
		this.gcliente = gcliente;
	}

	public void setNumeroBox(int numeroBox) {
		this.numeroBox=numeroBox;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}


	public void cambiarEstado(StateAbstracta estadonuevo) {
		this.ventanaState=estadonuevo;
		
	}
	@Override
	public void actionPerformed(ActionEvent evento) {
		if (evento.getActionCommand().equals(IVistaOperador.SOLICITARCLIENTE)) {
			this.ventanaState.solicitarCliente();
		}
		else if (evento.getActionCommand().equals(IVistaOperador.CANCELAR)) {
			this.ventanaState.solicitudCancelada();
			
		}
		else if (evento.getActionCommand().equals(IVistaOperador.AUSENTE)) {
			this.vista.confirmacionAusenteVentana();
			
		}
		else if (evento.getActionCommand().equals(IVistaOperador.SIAUSENTE)) {
			this.ventanaState.finalizarAtencionAusente();
		}
		else if (evento.getActionCommand().equals(IVistaOperador.NOAUSENTE)) {
			this.vista.clienteAsignadoVentana(dni);
			
		}
		else if (evento.getActionCommand().equals(IVistaOperador.ATENDIDO)) {
			this.vista.confirmacionAtendidoVentana();
			
		}
		else if (evento.getActionCommand().equals(IVistaOperador.SIATENDIDO)) {
			this.ventanaState.finalizarAtencionAtendido();
			
		}
		else if (evento.getActionCommand().equals(IVistaOperador.NOATENDIDO)) {
			this.vista.clienteAsignadoVentana(dni);
			
		}
		else {}
			/**
			 * Error xd
			 * 
			 */
	}
	
	public void solicitarCliente() {
		colamensajes.add("solicitudTurno");
		enviadorMensajes.start();
		vista.esperandoVentana();
		vista.deshabilitarBotonCancelar(); // podria estar por defecto en esperandoVentana()
	}
	
	public void asignarCliente(String elementos) {
		ventanaState.asignarCliente();
	}
	
	public void clienteAsignado(String dni) {
		vista.clienteAsignadoVentana(dni);
	}
	
	
	public void habilitarBotonCancelar() {
		ventanaState.activarBotonCancelar();
	}

	public void habilitarBotonCancelarVentana() {
		vista.habilitarBotonCancelar();
	}
	
	
	public void actualizarEstadoCola(int tamCola) { //ACA HAY Q AVISARLE A LA VENTANA
		this.tamCola=tamCola;
	}

	public void solicitudCancelada() {
		ventanaState.solicitudCancelada();
	}
	
	public void solicitudCanceladaVentana
	
	controlador.solicitarClienteVentana();


	public void mostrarError(String e) {}

	
	
	
	
}
