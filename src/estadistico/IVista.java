package estadistico;

import java.awt.event.ActionListener;

public interface IVista {
	void setActionListener(ActionListener actionListener);
	void entraSV();
	void salirSV();
	void errorIngreso(String motivo);
	void actualiza(int tamCola,int cantAtendidos, long TPromEsp,long TPromAtn,long TPromLlam);
	String getIP();
	String getPuerto();
	String getContrasenia();
	
}
