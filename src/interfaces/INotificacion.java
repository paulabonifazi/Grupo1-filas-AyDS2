package interfaces;

import Excepciones.ExcepcionDeInterrupcion;
import Excepciones.ExcepcionFinConexion;
import Excepciones.ExcepcionLecturaErronea;

public interface INotificacion {
	public void mostrar(String dni, String IDBox) throws ExcepcionFinConexion, ExcepcionDeInterrupcion, ExcepcionLecturaErronea; //la idea es que en el TV se espera recibir un mensaje de servidor con estos datos y cuando los reciba modifique la vista
}
