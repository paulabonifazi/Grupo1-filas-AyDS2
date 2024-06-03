package persistencia;

import java.io.IOException;

import server.IWritterLog;

public class GestorPersistencia extends Thread {
	private IWritterLog writterlog;
	private MonitorPersistencia colaPersistencia;
	
	
	
	public GestorPersistencia(MonitorPersistencia colaPersistencia, IWritterLog writterlog) {
		super();
		this.writterlog = writterlog;
		this.colaPersistencia=colaPersistencia;
	}



	@Override
    public void run() {
		String evento=null;
		try{
			while(!this.isInterrupted()) {
				evento=	colaPersistencia.take();
				writterlog.registraevento(evento);
			}
		}
		catch(IOException | InterruptedException e) {
			
		}
		finally {
			System.out.println("Se cerro el gestor de persistencia");
		}
		
		
	}
}
