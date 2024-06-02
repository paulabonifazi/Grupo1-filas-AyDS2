package server;

import java.util.Iterator;

public class EstrategiaEdad implements IStrategy {

	@Override
	public int compare(Turno T1, Turno T2) { //1 0 o -1
		int rta=0;
		Iterator<String> it=ParametrosDeConexion.getInstance().getGrupos().iterator();
		int edadT1=T1.getedad();
		int edadT2=T2.getedad();
		boolean encuentra=false;
		int edad=0, anterior=200;
		while(it.hasNext() && !encuentra){
			edad=Integer.parseInt(it.next());
			if((edadT1>edad && edadT1<=anterior)|| (edadT2>edad && edadT2<=anterior)) {
				encuentra=true;
			}
		}
		if((edadT1>edad && edadT1<=anterior) && (edadT2>edad && edadT2<=anterior)) {
			rta= new EstrategiaTiempo().compare(T1, T2);
		}
		else {
			if(edadT1>edad && edadT1<=anterior) {
				return 1;
			}
			else {
				return -1;
			}
				
		}
		return rta;
	}

}
