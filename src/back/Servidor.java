package back;

import interfaces.ILlamado;
import interfaces.IRegistro;

public class Servidor extends Thread implements ILlamado, IRegistro{

	public static void main(String[] args) {
		MonitorDeCola cola = new MonitorDeCola();
		MonitorNotificacion bufferSalida=new MonitorNotificacion();
		Atenciones historico= new Atenciones();
		Conexiones conexiones= new Conexiones(); 
		GestorConexion gestorConexion= new GestorConexion(conexiones,historico,bufferSalida,cola);
	}

}
