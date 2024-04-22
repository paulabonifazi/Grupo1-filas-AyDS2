package SistNotificacion;

import Box.ControladorLogin;
import Box.ControladorVistaOperador;
import Box.VentanaLogin;

public class MainNotificacion {

	public static void main(String[] args) {
		VentanaLogin ventana=new VentanaLogin();
		ControladorLogin controladorLogin= new ControladorLogin(ventana);
		ventana.setControlador(controladorLogin);
		ReceptorDeNotificaciones receptor = new ReceptorDeNotificaciones();
		ControladorVistaMonitor controladorMonitor=new ControladorVistaMonitor(receptor);
		receptor.setControlador(controladorMonitor);
		controladorMonitor.setLogin(controladorLogin);
		controladorLogin.mostrarVentana();
		controladorMonitor.intentarConexion();
	}

}
