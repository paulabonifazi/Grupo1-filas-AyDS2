package estadistico;

import Excepciones.ExcepcionErrorAlCerrar;
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
				if(elementos.length >= 2 && elementos[0].equals("Exito")) {
					try {
						conexion.cerrarConexion();
					} catch (ExcepcionErrorAlCerrar e) {
						//si ocurre no se puede hacer nada
					}
					conexion=new TCPCliente(IP, Integer.parseInt(elementos[1]));
				}
				else {
					//error de contraseña
				}
			}
			else {
				//notify. error login por error de conexion (Ip o puerto erroneo)
			}
		} 
		catch (ExcepcionErrorConexion|ExcepcionFinConexion e) {
			//notify. error login por error de conexion (Ip o puerto erroneo)
		} catch (ExcepcionLecturaErronea e) {
			// No puede ocurrir xq no se valida (false)
		}
	}
	
	
}
