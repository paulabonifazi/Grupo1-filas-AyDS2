package server;

import java.util.Comparator;

public interface StrategyQueue extends Comparator<Turno>{

	@Override
	public int compare(Turno o1, Turno o2) ;
	
}
