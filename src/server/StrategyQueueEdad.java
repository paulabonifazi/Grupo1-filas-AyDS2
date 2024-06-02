package server;

public class StrategyQueueEdad implements StrategyQueue {
	
	@Override
	public int compare(Turno t1, Turno t2) {
		Cliente c1=t1.getCliente();
		Cliente c2=t2.getCliente();
		int res=getPriority(c1.getEdad()) - getPriority(c2.getEdad());
		if (res==0)
			return (t1.getHrIngresoCola().compareTo(t2.getHrIngresoCola()));
		else 
			return res;
    }

    private int getPriority(int  edad) {
    	int prioridad;
    	
    	if (edad<=30) // joven
    		prioridad=3;
    	else
    		if (edad>30 && edad<65)//adulto
    			prioridad=2;
    		else
    			prioridad=1;// adulto mayor >65
    	return prioridad;
        }
}
	
	

