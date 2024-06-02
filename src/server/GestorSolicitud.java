package server;

public class GestorSolicitud extends Thread {
	private Turno turno;
	private MonitorDeCola cola;
	private String IdBox;
	
	
	public GestorSolicitud(Turno turno, MonitorDeCola cola,String Idbox) {
		super();
		this.turno = turno;
		this.cola = cola;
		this.IdBox=Idbox;
	}
	@Override
    public void run() {
		try {
			Turno solicitado=cola.take(this.IdBox);
			turno.setTurno(solicitado.getCliente(), solicitado.getHrRegistro(),solicitado.getHrIngresoCola(),solicitado.getAusencias());
		} catch (InterruptedException e) {
			//si se interrumpe finaliza el thread
		}
	}
}
