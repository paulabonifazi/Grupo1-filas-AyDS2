package Box;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import Excepciones.ExcepcionErrorConexion;
import TCP.*;

public class MainControladorBox {
	
	TCPCliente cliente=null;
	
	public static void main(String[] args) {
		
		
		/**
		 * Pedir Informacion de conexion(ip,puerto)
		 * mensaje=contraseña,tipo,numerobox,
		 * recibe exito,puerto
		 * desconectar
		 * conectar puerto
		 * mensaje, solicitar turno ->server verifica si hay cliente, caso contrario habilita botoncancelar
		 *  exito,dni
		 * 
		 *  estado cada x tiempo
		 *  
		 * mensaje cancelado 
		 */
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
