package estadistico;

import java.util.LinkedList;
import java.util.Observable;

import Excepciones.ExcepcionErrorAlCerrar;
import Excepciones.ExcepcionErrorConexion;
import Excepciones.ExcepcionFinConexion;
import Excepciones.ExcepcionLecturaErronea;
import TCP.TCPCliente;
import interfaces.IEstado;
@SuppressWarnings("deprecation")
public class GestorConexionEstadistico extends Observable implements IEstado{
	private TCPCliente conexion;
	private LinkedList<String> ipEsclavos=new LinkedList<String>();
	private int puerto;
	public GestorConexionEstadistico() {
		// TODO Auto-generated constructor stub
	}

	public void MostrarEstado() {
		try {
			conexion.enviarMensajeAlServidor("MostrarEstado", false);
			int desconexion=0;
			boolean recibido=false;
			boolean sinesclavos=false;
			while (!recibido && !sinesclavos ) {
				try {	
					String estado=conexion.recibirmensajeDeServidor(false);
					recibido=true;
					// Patrón para extraer los valores
					 String[]elementos=estado.split("-");
					 int j=1;
					 ipEsclavos=new LinkedList<String>();
					 while(j<elementos.length) {
							if(!elementos[j].isBlank() && !elementos[j].isEmpty()) {
								ipEsclavos.add(elementos[j]);
							}
							j++;
						}
					 String[]estadisticas= elementos[0].split("/");
		             int clientesAtendidos=Integer.parseInt(estadisticas[0]);
		             int clientesEnEspera=Integer.parseInt(estadisticas[2]);
		             Double tPromEsp=(double) 0;
		             Double tPromSoli = (double) 0;
		             Double tPromAtc=(double) 0;
		             if(estadisticas[1]!=null&& !estadisticas[1].isBlank()&& !estadisticas[1].isEmpty()) {
		            	String[] Ctiempos= estadisticas[1].split(";");
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
			
				}catch (ExcepcionFinConexion e1) {
					if(desconexion<2)
						desconexion++;
					else {
							try {
								Thread.sleep(3000); //para esperar que el esclavo se establezca como maestro
							} catch (InterruptedException e) {}
							Boolean conectado=false;
							String ip="";
							while(!conectado && !sinesclavos) {
								try {
									conexion.cerrarConexion();
								} catch (ExcepcionErrorAlCerrar e) {}
									desconexion=0;
									if(!ipEsclavos.isEmpty()) {
										try {
											ip=ipEsclavos.remove();
											conexion= new TCPCliente(ip, this.puerto);
											conectado=true;
											conexion.enviarMensajeAlServidor("MostrarEstado", false);
										} catch (ExcepcionErrorConexion e) {
										}
									}
									else
										sinesclavos=true;
							}
						}
				
				}
			}
			if(!recibido) {
					setChanged();
					notifyObservers("Conexion"); //problema de conexion vuelve al login
			}
		} catch (ExcepcionLecturaErronea|ExcepcionFinConexion e) {
					//no puede ocurrir
		}
	}
	
	public void loginSV(String contrasenia,String IP,int puerto) {
		try {
			conexion=new TCPCliente(IP, puerto);
			conexion.enviarMensajeAlServidor(contrasenia+";Estadisticos", false);
			String respuesta=conexion.recibirmensajeDeServidor(false);
			if(respuesta!=null) {
				String[] elementos = respuesta.split(";");	
				if(elementos.length >= 2 && elementos[0].equals("Exito")) {
					try {
						conexion.cerrarConexion();
					} catch (ExcepcionErrorAlCerrar e) {
						//si ocurre no se puede hacer nada
					}
					this.puerto=Integer.parseInt(elementos[1]);
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
