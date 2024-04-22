package Totem;

import java.awt.event.ActionListener;

public interface IVistaRegistro {

	public void setActionListener(ActionListener c);
	public void salirSV();
	public void entraSV();
	String getIP();
	int getPuerto();
	String getContrasenia();
	String getDNI();
	public void muestraRtado(String mensaje);
	public void errorIngreso(String mensaje);
	public void resetearDNI();
}
