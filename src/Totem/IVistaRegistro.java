package Totem;

import java.awt.event.ActionListener;

public interface IVistaRegistro {
	
	static final String CERO = "0";
	static final String UNO = "1";
	static final String DOS = "2";
	static final String TRES = "3";
	static final String CUATRO = "4";
	static final String CINCO = "5";
	static final String SEIS = "6";
	static final String SIETE = "7";
	static final String OCHO = "8";
	static final String NUEVE = "9";
	static final String BACKSPACE = "eliminar";
	static final String ENVIAR = "enviar";

	String getLabelDisplay();

	void setLabelDisplay(String texto);

	void habilitarEnvio();

	void deshabilitarEnvio();

	void setActionCommands();
	
	void setActionListener(ActionListener c);

	void abrir();

}
