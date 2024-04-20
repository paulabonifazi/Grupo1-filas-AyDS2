package estadistico;

import Excepciones.ExcepcionErrorConexion;
import Excepciones.ExcepcionFinConexion;
import Excepciones.ExcepcionLecturaErronea;

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
	
	public void loginSV(String contraseña,String IP,int puerto) {
		try {
			conexion=new TCPCliente(IP, puerto);
			conexion.enviarMensajeAlSerivor(contraseña+";Estadisticos", false);
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
