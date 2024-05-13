package SistNotificacion;

import java.util.LinkedList;

import Excepciones.ExcepcionErrorAlCerrar;
import Excepciones.ExcepcionErrorConexion;
import Excepciones.ExcepcionFinConexion;
import TCP.TCPCliente;

public class ReceptorDeNotificaciones extends Thread{
	private TCPCliente cliente;
	private ControladorVistaMonitor controlador;
	private LinkedList<String> ipEsclavos=new LinkedList<String>();
	private int puerto;
	
	
	public ReceptorDeNotificaciones() {
		super();

	}

	public void setCliente(TCPCliente cliente) {
		this.cliente = cliente;
	}


	public void setControlador(ControladorVistaMonitor controlador) {
		this.controlador = controlador;
	}

	public void setpuerto(int puerto) {
		this.puerto = puerto;
	}
	
	public void run() {
		String mensaje;
		String[] elementos,llamado;
		Boolean recibido=true;
		Boolean sinEsclavos=false;
		int desconexion=0;
		while (recibido && !sinEsclavos ) {
			mensaje=null;
			elementos=null;
			try {
				mensaje=cliente.recibirmensajeDeServidor(true);
				recibido=true;
				
				elementos = mensaje.split("/");	
				llamado=elementos[0].split(";");
				actualizarLista(new FilaNotificacion(llamado[0],llamado[1]));
				int i=1;
				this.ipEsclavos=new LinkedList<String>();
				while(i<elementos.length) {
					if(!elementos[i].isBlank() && !elementos[i].isEmpty()) {
						ipEsclavos.add(elementos[i]);
					}
					i++;
				}
			} catch (ExcepcionFinConexion e) {
				if(desconexion<2) {
					desconexion++;
				}
				else {
					Boolean conectado=false;
					String ip="";
					while(!conectado && !sinEsclavos) {
						try {
							cliente.cerrarConexion();
						} catch (ExcepcionErrorAlCerrar e1) {}
							desconexion=0;
							if(!ipEsclavos.isEmpty()) {
								try {
									ip=ipEsclavos.remove();
									cliente= new TCPCliente(ip, this.puerto);
									conectado=true;
								} catch (ExcepcionErrorConexion e1) {
								}
							}
							else {
								sinEsclavos=true;
							}
					}
				}
			}
		}
		if(!recibido || sinEsclavos) {
			controlador.volverLoginError();
		}
	}
	
	public void actualizarLista(FilaNotificacion fila) {
		try {
			controlador.semaforo.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		controlador.actualizarLista(fila);
		controlador.semaforo.release();
		controlador.actualizarVista();
			
	}			
}