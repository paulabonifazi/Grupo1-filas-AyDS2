package back;

import interfaces.ILlamado;
import interfaces.IRegistro;

public class Servidor extends Thread implements ILlamado, IRegistro{
	
	private MonitorDeCola cola;
	private Conexion conexion;
	private GestorConexion gestorConexion;
	private MonitorNotificacion bufferSalida;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
