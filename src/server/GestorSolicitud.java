package server;

public class GestorSolicitud extends Thread {
	private Turno turno;
	private MonitorDeCola cola;
	
	
	public GestorSolicitud(Turno turno, MonitorDeCola cola) {
		super();
		this.turno = turno;
		this.cola = cola;
	}
	@Override
    public void run() {
		try {
			Turno solicitado=cola.take();
			turno.setTurno(solicitado.getDni(), solicitado.getHrRegistro());
		} catch (InterruptedException e) {
			//si se interrumpe finaliza el thread
		}
	}
}
