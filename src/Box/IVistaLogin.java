package Box;

import java.awt.event.ActionListener;

public interface IVistaLogin {
	
	public void errorIngreso(String motivo);
	public String getIP();
	public String getPuerto();
	public String getContrasenia();
	public void setControlador(ControladorLogin c);
	public void initComponents();
}
