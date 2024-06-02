package server;

import java.util.Comparator;

public class FactoryStrategyQueue {
	ParametrosDeConexion parametros;
	
	public FactoryStrategyQueue(ParametrosDeConexion parametros) {
		this.parametros=parametros;
	}


	public Comparator<Turno> getStrategy(){
		String estrategia=parametros.getEstrategia();
		if (estrategia.equals("Edad")) {
			return new StrategyQueueEdad();
		}
		else
			if (estrategia.equals("Grupos")){
				return new StrategyQueueAfinidad(parametros.getGrupos());
			}
			else
				return new StrategyQueueFIFO();
		
	}
}
