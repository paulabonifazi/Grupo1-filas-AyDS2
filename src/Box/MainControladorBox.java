package Box;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import Excepciones.ExcepcionErrorConexion;
import TCP.*;

public class MainControladorBox {

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
		TCPCliente cliente=null;
		try {
			cliente=new TCPCliente("localhost",1234);
		} catch (ExcepcionErrorConexion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		recibirmensaje
		try {
			cliente=new TCPCliente(ipRecibida,puertoRecibido);
		} catch (ExcepcionErrorConexion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ControladorVistaOperador controlador=new ControladorVistaOperador();
		EnviadorMensajes enviadorMensajes=new EnviadorMensajes(cliente);
		IVistaOperador vista=new VistaOperador();
		vista.setControlador(controlador);
		BlockingQueue cola=new LinkedBlockingDeque<String>();
		GestorCliente gcliente=new GestorCliente(cliente,controlador);
		
		controlador.setVista(vista);
		controlador.setEnviadorMensajes(enviadorMensajes);
		controlador.setColamensajes(cola);
		controlador.setGcliente(gcliente);
		
		
		
		
	}

}
