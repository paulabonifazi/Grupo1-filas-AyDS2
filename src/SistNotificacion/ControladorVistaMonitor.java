package SistNotificacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observer;
import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;

import Box.ControladorLogin;
import Box.IVistaOperador;
import Box.VistaOperador;
import Excepciones.ExcepcionErrorAlCerrar;
import Excepciones.ExcepcionErrorConexion;
import Excepciones.ExcepcionFinConexion;
import Excepciones.ExcepcionLecturaErronea;
import TCP.TCPCliente;

public class ControladorVistaMonitor extends Thread {
	private FilaNotificacion[] columnaNotificacion;
	private ControladorLogin controladorLogin;
	private TCPCliente cliente;
	private ReceptorDeNotificaciones receptor;
	private IVistaMonitor vista;
	public Semaphore semaforo;
	
	public ControladorVistaMonitor(ReceptorDeNotificaciones receptor) {
		super();
		this.receptor = receptor;
		this.columnaNotificacion=new FilaNotificacion[6];
		columnaNotificacion[0]=new FilaNotificacion("","");
		semaforo=new Semaphore(1);
	}

	public void run(){ //Pasa el tiempo
		while(true) {
			try {
				Thread.sleep(1000);//esto es un segundo? si
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			try {
				semaforo.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i=0;i<6;i++) {
				if (columnaNotificacion[i]!=null) {
					if (columnaNotificacion[i].finalizoTiempo())
						columnaNotificacion[i]=new FilaNotificacion();
					else
						columnaNotificacion[i].pasarSegundo();
				}
			}
			actualizarVista();
			semaforo.release();
		}
	}


	public void intentarConexion() {
		try {
			this.intentarConexionConServidor();
		
		}catch (ExcepcionErrorConexion | ExcepcionFinConexion e) {
			JOptionPane.showMessageDialog(null, "ERROR DE CONEXION :(");
			int confirmado = JOptionPane.showConfirmDialog(null,"�Desea Intentar nuevamente?");
				if (JOptionPane.OK_OPTION == confirmado) {
					controladorLogin.mostrarVentana();
					intentarConexion();
				}
				else {
					System.exit(0);
				}
		}
	}

	private void intentarConexionConServidor() throws ExcepcionErrorConexion,ExcepcionFinConexion{ // PostCondicion Conexion exitosa. 
		cliente=null;
		String mensaje;
		String[] elementos;
		
		ArrayList<String> datosConexion = controladorLogin.getDatosConexion(); //0 ip 1 puerto 2 password
		
		if (datosConexion.get(0)=="" || datosConexion.get(1)=="" || datosConexion.get(1)=="")
			throw new ExcepcionErrorConexion();
		try {
			Integer.parseInt(datosConexion.get(1));
	    }catch(NumberFormatException e) {
	    	throw new ExcepcionErrorConexion();
	    }
		
		
		cliente=new TCPCliente(datosConexion.get(0),Integer.parseInt(datosConexion.get(1)));
	
		mensaje= datosConexion.get(2) + ";" + "TvLlamado";
		
		try {
			cliente.enviarMensajeAlServidor(mensaje, false);
		} catch (ExcepcionLecturaErronea e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//while ((mensaje = cliente.recibirmensajeDeServidor(false)) != null)
		//No se si lo de abajo es lo mismo
		do
			mensaje=cliente.recibirmensajeDeServidor(false);
		while (mensaje==null);
		
		
		elementos = mensaje.split(";"); // "Exito";(puerto)
		
		if (elementos[0].equals("Exito")){
			try {
				cliente.cerrarConexion();
			} catch (ExcepcionErrorAlCerrar e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			datosConexion.add(1,elementos[1]); // Reemplazo los datos de conexion
			cliente=new TCPCliente(datosConexion.get(0),Integer.parseInt(datosConexion.get(1)));
			JOptionPane.showMessageDialog(null, "Conexion exitosa :D");
		}
		else {
			throw new ExcepcionErrorConexion();
		}
		iniciarPrograma();
	}

	public void setLogin(ControladorLogin controladorLogin) {
		// TODO Auto-generated method stub
		this.controladorLogin=controladorLogin;
	}
	
	public void setVista(IVistaMonitor vista) {
		this.vista = vista;
	}

	public void iniciarPrograma() {
		this.receptor.setCliente(cliente);
		this.receptor.start();
		IVistaMonitor vista=new VistaNotificacion();
		this.columnaNotificacion[0]=new FilaNotificacion();
		this.columnaNotificacion[1]=new FilaNotificacion();
		this.columnaNotificacion[2]=new FilaNotificacion();
		this.columnaNotificacion[3]=new FilaNotificacion();
		this.columnaNotificacion[4]=new FilaNotificacion();
		this.columnaNotificacion[5]=new FilaNotificacion();
		this.setVista(vista);
		vista.abrir();
		this.start();
		
	}

	public void actualizarLista(FilaNotificacion fila) {
		
		for (int i = 4; i >= 0; i--) {                
			columnaNotificacion[i+1] = columnaNotificacion[i];
		}
		columnaNotificacion[0]=fila;
		actualizarVista();
	}

	public void actualizarVista() {
		vista.cambiarContenidoFila(0, columnaNotificacion[0].getDni(), columnaNotificacion[0].getBox());
		vista.cambiarContenidoFila(1, columnaNotificacion[1].getDni(), columnaNotificacion[1].getBox());
		vista.cambiarContenidoFila(2, columnaNotificacion[2].getDni(), columnaNotificacion[2].getBox());
		vista.cambiarContenidoFila(3, columnaNotificacion[3].getDni(), columnaNotificacion[3].getBox());
		vista.cambiarContenidoFila(4, columnaNotificacion[4].getDni(), columnaNotificacion[4].getBox());
		vista.cambiarContenidoFila(5, columnaNotificacion[5].getDni(), columnaNotificacion[5].getBox());
		
	}
	
	
}
