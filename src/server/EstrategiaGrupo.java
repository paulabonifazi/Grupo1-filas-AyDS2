package server;

import java.util.Iterator;

public class EstrategiaGrupo implements IStrategy {

	@Override
	public int compare(Turno T1, Turno T2) { //1 0 o -1
		int rta=0;
		Iterator<String> it=ParametrosDeConexion.getInstance().getGrupos().iterator();
		String grupoT1=T1.getgrupo();
		String grupoT2=T2.getgrupo();
		String grupo="";
		boolean encuentra=false;
		while(it.hasNext() && !encuentra){
			grupo=it.next();
			if(grupoT1.equals(grupo)|| grupoT2.equals(grupo)) {
				encuentra=true;
			}
		}
		if(grupoT1.equals(grupo)&& grupoT2.equals(grupo)) {
			rta= new EstrategiaTiempo().compare(T1, T2);
		}
		else {
			if(grupoT1.equals(grupo)) {
				return 1;
			}
			else {
				return -1;
			}
				
		}
		return rta;
	}

}
