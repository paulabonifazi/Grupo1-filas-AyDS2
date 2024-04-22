package Box;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import javax.swing.JOptionPane;

import Excepciones.ExcepcionErrorAlCerrar;
import Excepciones.ExcepcionErrorConexion;
import Excepciones.ExcepcionFinConexion;
import Excepciones.ExcepcionLecturaErronea;
import TCP.TCPCliente;

public class ControladorVistaOperador implements ActionListener {
	
	private IVistaOperador vista;
	private EnviadorMensajes enviadorMensajes;
	private BlockingQueue<String> colamensajes;
	private GestorCliente gcliente;
	private StateAbstracta ventanaState;
	private int numeroBox;
	private int tamCola;
	private String dni;
	ControladorLogin controladorLogin;
	TCPCliente cliente;
	
	public TCPCliente getTCPCliente() {
		return cliente;
	}


	public void setCliente(TCPCliente cliente) {
		this.cliente = cliente;
	}


	public ControladorVistaOperador() {
		super();
		ventanaState=new InactivoState(this);
	}
	

	public void setVista(IVistaOperador vista) {
		this.vista = vista;
	}


	
	public void setEnviadorMensajes(EnviadorMensajes enviadorMensajes) {
		this.enviadorMensajes = enviadorMensajes;
	}

	public void setColamensajes(BlockingQueue<String> colamensajes) {
		this.colamensajes = colamensajes;
	}

	public void setGcliente(GestorCliente gcliente) {
		this.gcliente = gcliente;
	}

	public void setNumeroBox(int numeroBox) {
		this.numeroBox=numeroBox;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}


	public void cambiarEstado(StateAbstracta estadonuevo) {
		this.ventanaState=estadonuevo;
		
	}
	@Override
	public void actionPerformed(ActionEvent evento) {
		
		if (evento.getActionCommand().equals(IVistaOperador.SOLICITARCLIENTE)) {
			this.ventanaState.solicitarCliente();

		}
		else if (evento.getActionCommand().equals(IVistaOperador.CANCELAR)) {
			this.ventanaState.cancelarSolicitud();
			
		}
		else if (evento.getActionCommand().equals(IVistaOperador.AUSENTE)) {
			this.vista.confirmacionAusenteVentana();
			
		}
		else if (evento.getActionCommand().equals(IVistaOperador.SIAUSENTE)) {
			this.ventanaState.finalizarAtencionAusente();
		}
		else if (evento.getActionCommand().equals(IVistaOperador.NOAUSENTE)) {
			this.vista.clienteAsignadoVentana(dni);
			
		}
		else if (evento.getActionCommand().equals(IVistaOperador.ATENDIDO)) {
			this.vista.confirmacionAtendidoVentana();
			
		}
		else if (evento.getActionCommand().equals(IVistaOperador.SIATENDIDO)) {
			this.ventanaState.finalizarAtencionAtendido();
			
		}
		else if (evento.getActionCommand().equals(IVistaOperador.NOATENDIDO)) {
			this.vista.clienteAsignadoVentana(dni);
			
		}
		else {}
			/**
			 * Error xd
			 * 
			 */
	}
	
	public void solicitarCliente() {
		colamensajes.add("solicitudTurno");
	
		vista.esperandoVentana();
		vista.deshabilitarBotonCancelar(); // podria estar por defecto en esperandoVentana()
	}
	
	public void asignarCliente(String dni) {
		ventanaState.asignarCliente(dni);
	}
	
	public void clienteAsignado(String dni) {
		vista.clienteAsignadoVentana(dni);
	}
	
	
	public void habilitarBotonCancelar() {
		ventanaState.activarBotonCancelar();
	}

	public void habilitarBotonCancelarVentana() {
		vista.habilitarBotonCancelar();
	}
	
	
	public void actualizarEstadoCola(int tamCola) { //ACA HAY Q AVISARLE A LA VENTANA
		this.tamCola=tamCola;
	}


	
	public void finalizarAtencionClienteAusente() {
		colamensajes.add("Ausente");
		vista.solicitarClienteVentana();
	}

	public void finalizarAtencionClienteAtendido() {
		colamensajes.add("Fin");
		vista.solicitarClienteVentana();
	}
	
	public void solicitarCancelacion() {
		colamensajes.add("Cancelar");
		
	}
	
	public void solicitudCancelada() {
		ventanaState.solicitudCancelada();
	}
	
	public void solicitudCanceladaVentana() {
		vista.solicitarClienteVentana();
	}
	
	public void mostrarError(String e) {
		vista.mostrarError(e);
	}

	public void setLogin(ControladorLogin controladorLogin) {
		this.controladorLogin=controladorLogin;
		
	}

	public void intentarConexion() {
		try {
			this.intentarConexionConServidor();
		
		}catch (ExcepcionErrorConexion | ExcepcionFinConexion e) {
			JOptionPane.showMessageDialog(null, "ERROR DE CONEXION :(");
			int confirmado = JOptionPane.showConfirmDialog(null,"¿Desea Intentar nuevamente?");
				if (JOptionPane.OK_OPTION == confirmado) {
					controladorLogin.mostrarVentana();
					solicitarNumeroBox();
					intentarConexion();
				}
				else {
					System.exit(0);
				}
		}
		iniciarPrograma();
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

		mensaje= datosConexion.get(2) + ";" + "Box" + ";" + Integer.toString(numeroBox);
		
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

	public void solicitarNumeroBox() {
		String seleccion = JOptionPane.showInputDialog(
				   null,
				   "INGRESE EL NUMERO DE ESTE BOX",
				   JOptionPane.QUESTION_MESSAGE);
		if (seleccion!=null)
			this.numeroBox = Integer.parseInt(seleccion);
	}
	
	public void iniciarPrograma() {
		
		
		IVistaOperador vista=new VistaOperador();
		BlockingQueue cola=new LinkedBlockingDeque<String>();
		GestorCliente gcliente=new GestorCliente(this.getTCPCliente(),this);
		EnviadorMensajes enviadorMensajes=new EnviadorMensajes(this.getTCPCliente(),cola);
		
		setVista(vista);
		setEnviadorMensajes(enviadorMensajes);
		setColamensajes(cola);
		setGcliente(gcliente);
		gcliente.start();
		enviadorMensajes.start();
		vista.setControlador(this);
		vista.setNumeroPuesto(numeroBox);
		vista.abrir();
		
	}
	
	
	
}
