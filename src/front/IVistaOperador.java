package front;

import java.awt.event.ActionListener;

public interface IVistaOperador {
	
	static final String LLAMAR = "Llamar";
	static final String ELIMINAR = "Eliminar";
	
	void setDisplay(String clienteActual);
	int getNumeroPuesto();
	void habilitarBotonEliminar();
	void deshabilitarBotonEliminar();
	void dispose();
	void setNumeroPuesto(int numero);
	void setActionListener(ActionListener c);
	void abrir();
	

}
