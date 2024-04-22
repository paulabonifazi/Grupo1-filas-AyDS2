package Box;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class MainControladorBox {
	
	public static void main(String[] args) {
		
		VentanaLogin ventana=new VentanaLogin();
		ControladorLogin controladorLogin= new ControladorLogin(ventana);
		ventana.setControlador(controladorLogin);
		ControladorVistaOperador controladorOperador=new ControladorVistaOperador();
		controladorOperador.setLogin(controladorLogin);
		controladorLogin.mostrarVentana();
		controladorOperador.solicitarNumeroBox();
		controladorOperador.intentarConexion();
	}
}