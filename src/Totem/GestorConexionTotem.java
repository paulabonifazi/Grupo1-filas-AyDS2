package Totem;

import java.util.LinkedList;
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
	private LinkedList<String> ipEsclavos;
	private int puerto;
	
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
					this.puerto=Integer.parseInt(elementos[1]);
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
				int desconexion=0;
				boolean recibido=false;
				boolean sinesclavos=false;
				while (!recibido && !sinesclavos ) {
					try {
						String[] elementos= conexion.recibirmensajeDeServidor(false).split("$");
						recibido=true;
						int i=1;
						this.ipEsclavos=new LinkedList<String>();
						while(i<elementos.length) {
							if(!elementos[i].isBlank() && !elementos[i].isEmpty()) {
								ipEsclavos.add(elementos[i]);
							}
							i++;
						}
						String estado=elementos[0];
						setChanged();
						notifyObservers(estado);
					} catch (ExcepcionFinConexion e1) {
							if(desconexion<2)
								desconexion++;
							else {
									desconexion=0;
									try {
										Thread.sleep(3000); //para esperar que el esclavo se establezca como maestro
									} catch (InterruptedException e) {}
									Boolean conectado=false;
									while(!conectado && !ipEsclavos.isEmpty()) {
										try {
											conexion.cerrarConexion();
										} catch (ExcepcionErrorAlCerrar e) {}
										try {
											conexion= new TCPCliente(ipEsclavos.remove(), this.puerto);
											conectado=true;
										} catch (ExcepcionErrorConexion e) {
										}
									}
									if(ipEsclavos.isEmpty()) {
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
}
