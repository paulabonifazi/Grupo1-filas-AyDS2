package estadistico;

import Excepciones.ExcepcionErrorConexion;
import Excepciones.ExcepcionFinConexion;
import Excepciones.ExcepcionLecturaErronea;
import TCP.TCPCliente;

public class GestorConexionEstadistico implements IEstado{
	private TCPCliente conexion;
	public GestorConexionEstadistico() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String MostrarEstado() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void loginSV(String contrasenia,String IP,int puerto) {
		try {
			conexion=new TCPCliente(IP, puerto);
			conexion.enviarMensajeAlSerivor(contrasenia+";Estadisticos", false);
			String respuesta=conexion.recibirmensajeDeServidor(null);
			if(respuesta!=null) {
				String[] elementos = respuesta.split(";");	
				if(elementos.length >= 2) {
					
				}
				else {
					
				}
			}
			else {
				
			}
		} 
		catch (ExcepcionErrorConexion|ExcepcionFinConexion e) {
			//notify. error login por error de conexion
		} catch (ExcepcionLecturaErronea e) {
			// No puede ocurrir xq no se valida (false)
		}
	}
	
	
}
