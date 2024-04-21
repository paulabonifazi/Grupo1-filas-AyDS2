package estadistico;

import java.util.Observable;

import Excepciones.ExcepcionErrorAlCerrar;
import Excepciones.ExcepcionErrorConexion;
import Excepciones.ExcepcionFinConexion;
import Excepciones.ExcepcionLecturaErronea;
import TCP.TCPCliente;
@SuppressWarnings("deprecation")
public class GestorConexionEstadistico extends Observable implements IEstado{
	private TCPCliente conexion;
	public GestorConexionEstadistico() {
		// TODO Auto-generated constructor stub
	}

	public void MostrarEstado() {
		try {
			conexion.enviarMensajeAlSerivor("MostrarEstado", false);
			String estado=conexion.recibirmensajeDeServidor(false);
			// Patrón para extraer los valores
			 String[]elementos= estado.split("/");
             int clientesAtendidos=Integer.parseInt(elementos[0]);
             int clientesEnEspera=Integer.parseInt(elementos[2]);
             Double tPromEsp=(double) 0;
             Double tPromSoli = (double) 0;
             Double tPromAtc=(double) 0;
             if(elementos[1]!=null&& !elementos[1].isBlank()&& !elementos[1].isEmpty()) {
            	String[] Ctiempos= elementos[1].split(";");
	             Long[] tEsperaArray = new Long[Ctiempos.length];
	             Long[] tSolicitudArray = new Long[Ctiempos.length];
	             Long[] tAtencionArray = new Long[Ctiempos.length];
	            String[]tiempos=null;
	            
	            for (int i = 0; i < Ctiempos.length; i++) {
	         	   tiempos=Ctiempos[i].split(",");
		         	   tEsperaArray[i]=Long.parseLong(tiempos[0]);
		         	   tSolicitudArray[i]=Long.parseLong(tiempos[1]);
		         	   tAtencionArray[i]=Long.parseLong(tiempos[2]);
	            }
	            tPromEsp=CalculosEstadisticos.calcularPromedio(tEsperaArray);
	            tPromSoli=CalculosEstadisticos.calcularPromedio(tSolicitudArray);
	            tPromAtc=CalculosEstadisticos.calcularPromedio(tAtencionArray);
            }  
            setChanged();
			notifyObservers(clientesAtendidos+";"+tPromEsp+";"+tPromSoli+";"+tPromAtc+";"+clientesEnEspera); //problema de conexion vuelve al login
		} catch (ExcepcionLecturaErronea e) {
			//no puede ocurrir
		} catch (ExcepcionFinConexion e) {
			setChanged();
			notifyObservers("Conexion"); //problema de conexion vuelve al login
		}
		
	}
	
	public void loginSV(String contrasenia,String IP,int puerto) {
		try {
			conexion=new TCPCliente(IP, puerto);
			conexion.enviarMensajeAlSerivor(contrasenia+";Estadisticos", false);
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
					notifyObservers("Exito");
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
}
