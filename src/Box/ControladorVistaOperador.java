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
	private String estadoCola;
	
	public ControladorVistaOperador() {
		super();
		ventanaState=new InactivoState(this);
	}
	

	public void setVista(IVistaOperador vista) {
		this.vista = vista;
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
	
	public void cambiarEstado(StateAbstracta estadonuevo) {
		this.ventanaState=estadonuevo;
		
	}
	@Override
	public void actionPerformed(ActionEvent evento) {
		if (evento.getActionCommand().equals(IVistaOperador.SOLICITARCLIENTE)) {
			colamensajes.add(IVistaOperador.SOLICITARCLIENTE);
			enviadorMensajes.start();
			
		}
		else if (evento.getActionCommand().equals(IVistaOperador.CANCELAR)) {
			
			
		}
		else if (evento.getActionCommand().equals(IVistaOperador.AUSENTE)) {
			
			
		}
		else if (evento.getActionCommand().equals(IVistaOperador.SIAUSENTE)) {
			
			
		}
		else if (evento.getActionCommand().equals(IVistaOperador.NOAUSENTE)) {
			
			
		}
		else if (evento.getActionCommand().equals(IVistaOperador.ATENDIDO)) {
			
			
		}
		else if (evento.getActionCommand().equals(IVistaOperador.SIATENDIDO)) {
			
			
		}
		else if (evento.getActionCommand().equals(IVistaOperador.NOATENDIDO)) {
			
			
		}
		else {}
			/**
			 * Error xd
			 * 
			 */
	}
	
	public void asignarCliente() {
		ventanaState.asignarCliente();
	}
	
	public void clienteAsignadoVentana() {
		
		
		
	}
	
	
	
	public void recibirMensajesTCP() {
		
		
		
	}


	public void habilitarBotonCancelar() {
		vista.
		
	}
}
