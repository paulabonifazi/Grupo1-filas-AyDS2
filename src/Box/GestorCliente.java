package Box;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import javax.swing.JOptionPane;

import Excepciones.ExcepcionErrorAlCerrar;
import Excepciones.ExcepcionErrorConexion;
import Excepciones.ExcepcionFinConexion;
import Excepciones.ExcepcionLecturaErronea;
import TCP.TCPCliente;

public class GestorCliente extends Thread{
	TCPCliente cliente;
	BlockingQueue<String> blockingQueue;
	ControladorVistaOperador controlador;
	StateAbstracta estado;
	
	public GestorCliente(TCPCliente cliente,ControladorVistaOperador controlador) {
		super();
		this.cliente = cliente;
		blockingQueue = new LinkedBlockingDeque<>();
		this.controlador=controlador;
	}


	public void run() {
		String mensaje;
		String[] elementos;
		while(true) {
			mensaje=null;
			elementos=null;
			if (!cliente.estaCerrado()){		
				do
					try {
						mensaje=cliente.recibirmensajeDeServidor(false);
					} catch (ExcepcionFinConexion e) {
						// TODO Auto-generated catch block
						System.exit(0);
					}
				while (mensaje==null);
				//Mensaje recibido
				elementos = mensaje.split(";");	
				switch (elementos[0]) {
	                case "Estado": //Actualizar personas en pantalla
	                	controlador.actualizarEstadoCola(Integer.parseInt(elementos[1]));
	                    break;
	                case "Atencion": //Asignar cliente
	                	controlador.asignarCliente(elementos[1]);
	                    break;
	                case "ActCancelar":
	                	controlador.habilitarBotonCancelar();
	                	break;
	                case "Cancelado":
	                	controlador.solicitudCancelada();
	                	break;
	                default:
	                	controlador.mostrarError(mensaje);
	                   
	            }
			} else {
				try {
					cliente.cerrarConexion();
					interrupt();
				} catch (ExcepcionErrorAlCerrar e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			
		
		
	}
}
}