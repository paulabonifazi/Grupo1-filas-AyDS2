package interfaces;

import Excepciones.ExcepcionDeInterrupcion;
import Excepciones.ExcepcionFinConexion;

public interface IAtencion {
	public void solicitudTurno() throws ExcepcionFinConexion, ExcepcionDeInterrupcion, InterruptedException; //la idea es que en el box este metodo modifique la ventana segun los mensajes que recibe
}
