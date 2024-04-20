package Box;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import Excepciones.ExcepcionErrorConexion;
import TCP.*;

public class MainControladorBox {

	public static void main(String[] args) {
		TCPCliente cliente=null;
		try {
			cliente=new TCPCliente("localhost",1234);
		} catch (ExcepcionErrorConexion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		EnviadorMensajes enviadorMensajes=new EnviadorMensajes(cliente);
		IVistaOperador vista=new VistaOperador();
		BlockingQueue cola=new LinkedBlockingDeque<String>();
		GestorCliente gcliente=new GestorCliente(cliente);
		ControladorVistaOperador controlador=new ControladorVistaOperador(vista,enviadorMensajes,cola,gcliente);
		vista.setControlador(controlador);
		
		
		
	}

}
