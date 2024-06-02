package server;

public class EstrategiaTiempo implements IStrategy {

	@Override
	public int compare(Turno T1, Turno T2) { 
		return T2.getHrIngresoCola().compareTo(T1.getHrIngresoCola());
	}

}
