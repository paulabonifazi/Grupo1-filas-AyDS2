package SistNotificacion;

import Excepciones.ExcepcionErrorAlCerrar;
import Excepciones.ExcepcionFinConexion;

public class HijoReceptor extends ReceptorDeNotificaciones {
	
	
	public HijoReceptor() {
		super();
	}
	
	public void run() {
	
		try {
			actualizarLista(new FilaNotificacion("432422","4"));
			Thread.sleep(5000);
			actualizarLista(new FilaNotificacion("72836","6"));
			Thread.sleep(5000);
			actualizarLista(new FilaNotificacion("696966","3"));
			Thread.sleep(5000);
			actualizarLista(new FilaNotificacion("abcde","3"));
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*public void run() {
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
				
				actualizarLista(new FilaNotificacion(elementos[0],elementos[1]));
	                   
	        
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
	
	}*/
}
