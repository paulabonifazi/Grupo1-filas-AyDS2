package server;

public class StrategyQueueFIFO implements StrategyQueue  {
	
	@Override
	public int compare(Turno t1, Turno t2) {
		return (t1.getHrIngresoCola().compareTo(t2.getHrIngresoCola()));
    }
}
