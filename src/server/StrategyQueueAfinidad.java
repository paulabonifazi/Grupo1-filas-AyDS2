package server;

import java.util.ArrayList;

public class StrategyQueueAfinidad implements StrategyQueue {
	private ArrayList<String> gruposAfinidad;
	
	
	public StrategyQueueAfinidad(ArrayList<String> gruposAfinidad) {
		super();
		this.gruposAfinidad = gruposAfinidad;
	}

	@Override
	public int compare(Turno t1, Turno t2) {
		Cliente c1=t1.getCliente();
		Cliente c2=t2.getCliente();
        int res=getPriority(c1.getGrupo()) - getPriority(c2.getGrupo());
		if (res==0)
			return (t1.getHrIngresoCola().compareTo(t2.getHrIngresoCola()));
		else 
			return res;
    }

    private int getPriority(String membresia) {
    	
    	int prioridad;
    	int i=0;
    	while (i<gruposAfinidad.size()&& !gruposAfinidad.get(i).equalsIgnoreCase(membresia)) {
    		i++;
    	}
    	prioridad=i;
    	return prioridad;
        }
}
