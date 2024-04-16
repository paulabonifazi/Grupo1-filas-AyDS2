package back;

import interfaces.ILlamado;

/*el box solicita un turno (blockingqueue) se crea un subthread que espera por un 
 * elemento de la blockingqueue 
 * cuando se le asigna un elemento se crea la atencion*/
public class Atencion extends Thread implements ILlamado{

}
