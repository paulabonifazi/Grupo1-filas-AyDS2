package Totem;

import java.awt.event.ActionListener;

public interface IVistaRegistro {

	public void setActionListener(ActionListener c);
	public void salirSV();
	public void entraSV();
	public void muestraRtado();
	String getIP();
	int getPuerto();
	String getContrasenia();
	String getDNI();
	public void errorIngreso(String motivo);

}
