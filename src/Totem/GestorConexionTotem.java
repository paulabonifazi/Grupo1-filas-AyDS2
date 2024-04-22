package Totem;

import java.util.Observable;

import Excepciones.ExcepcionErrorAlCerrar;
import Excepciones.ExcepcionErrorConexion;
import Excepciones.ExcepcionFinConexion;
import Excepciones.ExcepcionLecturaErronea;
import TCP.TCPCliente;
import interfaces.IRegistro;
@SuppressWarnings("deprecation")
public class GestorConexionTotem extends Observable implements IRegistro{
	private TCPCliente conexion;
	public GestorConexionTotem() {
		// TODO Auto-generated constructor stub
	}

	
	
	public void loginSV(String contrasenia,String IP,int puerto) {
		try {
			conexion=new TCPCliente(IP, puerto);
			conexion.enviarMensajeAlServidor(contrasenia+";Totem", false);
			String respuesta=conexion.recibirmensajeDeServidor(false);
			if(respuesta!=null) {
				String[] elementos = respuesta.split(";");	
				if(elementos.length >= 2 && elementos[0].equals("Exito")) {
					try {
						conexion.cerrarConexion();
					} catch (ExcepcionErrorAlCerrar e) {
						//si ocurre no se puede hacer nada
					}
					conexion=new TCPCliente(IP, Integer.parseInt(elementos[1]));
					setChanged();
					notifyObservers("Entra");
				}
				else {
					setChanged();
					notifyObservers("Contrasenia");
				}
			}
			else {
				setChanged();
				notifyObservers("Conexion");
			}
		}
		catch (ExcepcionErrorConexion|ExcepcionFinConexion|ExcepcionLecturaErronea e) {
			setChanged();
			notifyObservers("Conexion");
		}
		
	}

	@Override
	public void registrar(String DNI) {
			try {
				conexion.enviarMensajeAlServidor("Registro;"+DNI, false);
				String estado=conexion.recibirmensajeDeServidor(false);
	            setChanged();
				notifyObservers(estado); //problema de conexion vuelve al login
			} catch (ExcepcionLecturaErronea e) {
				//no puede ocurrir
			} catch (ExcepcionFinConexion e) {
				setChanged();
				notifyObservers("Conexion"); //problema de conexion vuelve al login
			}
			
		}
}
