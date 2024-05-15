package Box;

import java.util.concurrent.LinkedBlockingDeque;

import javax.swing.JOptionPane;

import Excepciones.ExcepcionErrorAlCerrar;
import Excepciones.ExcepcionErrorConexion;
import Excepciones.ExcepcionFinConexion;
import Excepciones.ExcepcionLecturaErronea;
import TCP.TCPCliente;

public class GestorCliente extends Thread{
	TCPCliente cliente;
	ControladorVistaOperador controlador;
	StateAbstracta estado;
	
	public GestorCliente(TCPCliente cliente,ControladorVistaOperador controlador) {
		super();
		this.cliente = cliente;
		this.controlador=controlador;
	}

	public void run() {
		String mensaje;
		String[] elementos;
		System.out.println("Se creo un Thread de GestorCliente"); //DEBUG
		while(!this.isInterrupted()) {
			mensaje=null;
			elementos=null;
			if (!cliente.estaCerrado()){		
				do
					try {
						mensaje=cliente.recibirmensajeDeServidor(false);
					} catch (ExcepcionFinConexion e) {
						this.interrupt();
						try {
							cliente.cerrarConexion();
						} catch (ExcepcionErrorAlCerrar e1) {
						}
						System.out.println("GestorCliente ExcfinConec"); //DEBUG
						controlador.reintentarConexion();
						
					}
				while (mensaje==null && !this.isInterrupted());
				//Mensaje recibido
				if (mensaje!=null) {
					elementos = mensaje.split(";");	
					System.out.println(elementos[0]); // DEBUG
					switch (elementos[0]) {
		                case "Estado": //Actualizar personas en pantalla
		                	controlador.actualizarEstadoCola(Integer.parseInt(elementos[1]));
		                	if (elementos.length>2) {
		                		controlador.actualizarEsclavosServidor(elementos[2]);
		                		System.out.println(elementos[2]); // DEBUG
		                	}
		                    break;
		                case "Atencion": //Asignar cliente
		                	controlador.asignarCliente(elementos[1]);
		                	if (elementos.length>2)
		                		controlador.actualizarEsclavosServidor(elementos[2]);
		                    break;
		                case "ActCancelar":
		                	controlador.habilitarBotonCancelar();
		                	break;
		                case "Cancelado":
		                	controlador.solicitudCancelada();
		                	break;
		                case "Recibido":
		                	controlador.confirmacionRecibida();
		                	break;
		                default:
		                	controlador.mostrarError(mensaje);
		                   
		            }
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