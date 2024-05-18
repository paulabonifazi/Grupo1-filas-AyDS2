package SistNotificacion;

import Box.ControladorLogin;
import Box.VentanaLogin;

public class MainNotificacion {

	public static void main(String[] args) {
		VentanaLogin ventanaLogin=new VentanaLogin();
		ControladorLogin controladorLogin= new ControladorLogin(ventanaLogin);
		ventanaLogin.setControlador(controladorLogin);
		ReceptorDeNotificaciones receptor = new ReceptorDeNotificaciones();
		ControladorVistaMonitor controladorMonitor=new ControladorVistaMonitor(receptor);
		receptor.setControlador(controladorMonitor);
		controladorMonitor.setLogin(controladorLogin);
		controladorLogin.mostrarVentana();
		controladorMonitor.intentarConexion();
	}

	
	
}
