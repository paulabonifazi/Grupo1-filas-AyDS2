package Servidor;

import Excepciones.ExcecionErrorAlCerrar;

public interface IConexion {
	public int getPuerto();
	public String getIP();
	public String getID();
	public boolean isConectado();
	public void cerrarConexion()throws ExcecionErrorAlCerrar;
}
