package estadistico;

import java.awt.event.ActionListener;

public interface IVistaLogin {
	
	public void entraSV();
	public void salirSV();
	public void errorIngreso(String motivo);
	public String getIP();
	public String getPuerto();
	public String getContrasenia();
	
}
