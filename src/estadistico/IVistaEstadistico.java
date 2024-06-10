package estadistico;

import java.awt.event.ActionListener;

public interface IVistaEstadistico {
	void setActionListener(ActionListener actionListener);
	void entraSV();
	void salirSV();
	void errorIngreso(String motivo);
	String getIP();
	int getPuerto();
	String getContrasenia();
	void actualiza(String tamCola, String cantAtendidos, String TPromEsp, String TPromAtn, String TPromLlam);
	void pausarTemporizador();
}
