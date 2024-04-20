package Box;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

import TCP.TCPCliente;

public class ControladorVistaOperador implements ActionListener {
	IVistaOperador vista;
	EnviadorMensajes enviadorMensajes;
	BlockingQueue<String> colamensajes;
	GestorCliente gcliente;
	
	public ControladorVistaOperador(IVistaOperador vista, EnviadorMensajes enviadorMensajes, BlockingQueue<String> colamensajes,GestorCliente gcliente) {
		super();
		this.vista = vista;
		this.enviadorMensajes = enviadorMensajes;
		this.colamensajes=colamensajes;
		this.gcliente=gcliente;
		gcliente.start();
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
	
	
	
	public void recibirMensajesTCP() {
		
		
		
	}
}
