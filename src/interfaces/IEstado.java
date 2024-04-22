package interfaces;

import Excepciones.ExcepcionDeInterrupcion;
import Excepciones.ExcepcionFinConexion;

public interface IEstado {
	public void MostrarEstado() throws ExcepcionFinConexion, ExcepcionDeInterrupcion; //la idea es q al llamar este metodo desde el controlador de estadistico se obtenga un string con el estado
}
