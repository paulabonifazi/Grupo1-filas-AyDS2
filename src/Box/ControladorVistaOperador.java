package Box;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
	private ControladorLogin controladorLogin;
	private TCPCliente cliente;
	private ArrayList<String> datosConexion;
	private ArrayList<String> listaServidoresEsclavos;
	
	
	public TCPCliente getTCPCliente() {
		return cliente;
	}


	public void setCliente(TCPCliente cliente) {
		this.cliente = cliente;
	}


	public ControladorVistaOperador() {
		super();
		ventanaState=new InactivoState(this);
		this.listaServidoresEsclavos=new ArrayList<String>();
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
		this.vista.setEstadoDeLaCola(tamCola);
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
			iniciarPrograma();
		}catch (ExcepcionErrorConexion | ExcepcionFinConexion e) {
			JOptionPane.showMessageDialog(null, "ERROR DE CONEXION :(");
			int confirmado = JOptionPane.showConfirmDialog(null,"�Desea Intentar nuevamente?");
				if (JOptionPane.OK_OPTION == confirmado) {
					controladorLogin.mostrarVentana();
					solicitarNumeroBox();
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
		
		this.datosConexion = controladorLogin.getDatosConexion(); //0 ip 1 puerto 2 password
		
		if (this.datosConexion.get(0)=="" || this.datosConexion.get(1)=="" || this.datosConexion.get(1)=="")
			throw new ExcepcionErrorConexion();
		try {
			Integer.parseInt(this.datosConexion.get(1));
        }catch(NumberFormatException e) {
        	throw new ExcepcionErrorConexion();
        }
		
		
		cliente=new TCPCliente(this.datosConexion.get(0),Integer.parseInt(this.datosConexion.get(1))); //Primera conexion

		mensaje= this.datosConexion.get(2) + ";" + "Box" + ";" + Integer.toString(numeroBox);
		
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
			this.datosConexion.set(1,elementos[1]); // Reemplazo los datos de conexion
			
			this.cliente=new TCPCliente(this.datosConexion.get(0),Integer.parseInt(this.datosConexion.get(1)));
			JOptionPane.showMessageDialog(null, "Conexion exitosa :D");
		}
		else {
			throw new ExcepcionErrorConexion();
		}
		
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
		
		
		this.vista=new VistaOperador();
		this.colamensajes=new LinkedBlockingDeque<String>();
		this.gcliente=new GestorCliente(this.getTCPCliente(),this);
		this.enviadorMensajes=new EnviadorMensajes(this.getTCPCliente(),this.colamensajes,this);
		
		setVista(vista);
		setEnviadorMensajes(enviadorMensajes);
		setColamensajes(colamensajes);
		setGcliente(gcliente);
		gcliente.start();
		enviadorMensajes.start();
		vista.setControlador(this);
		vista.setNumeroPuesto(numeroBox);
		vista.abrir();
		
	}


	public synchronized void reintentarConexion() {
		int reintentos=2;
		boolean conectado=false;
		int i=0;
		int vueltas=0;
		boolean conexionPerdida=false;

		if (cliente.estaCerrado()){
			do { 
				while (reintentos>0 && !conectado){
					try {
						System.out.println(reintentos); // DEBUG
						System.out.println("Vueltas"+vueltas); // DEBUG
						vueltas++;
						this.cliente=new TCPCliente(this.datosConexion.get(0),Integer.parseInt(this.datosConexion.get(1)));
						conectado=true;
						System.out.println("Conectado"); // DEBUG
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExcepcionErrorConexion e) {
						reintentos=reintentos-1;
						try {
							Thread.sleep(500);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					System.out.println("Reintentos"+reintentos+"Conectado"+conectado+"Condicion"+(reintentos>0 && !conectado)); // DEBUG
				}
				if (reintentos<=0) {
					if (i<this.listaServidoresEsclavos.size()) {
						this.datosConexion.set(0,listaServidoresEsclavos.get(i));
						i++;
						reintentos=2;
					}
					else{
						conexionPerdida();
						conectado=true;
						conexionPerdida=true;
					}
				}
			}while (!conectado);
			
			if (!gcliente.isInterrupted()) //Interrumpe threads que esten relacionados con las conexiones
				gcliente.interrupt();
			if (!enviadorMensajes.isInterrupted())
				enviadorMensajes.interrupt();
			
			this.gcliente=new GestorCliente(this.getTCPCliente(),this);;
			this.enviadorMensajes= new EnviadorMensajes(this.getTCPCliente(),this.colamensajes,this);
			
			gcliente.start();
			enviadorMensajes.start();
			if (conexionPerdida==false)
				ventanaState.solicitudCancelada();
			else {
				ventanaState=new EsperaState(this);
				ventanaState.solicitudCancelada();
			}
				
		}
		else
			System.out.println("El cliente no esta cerrado"); // DEBUG
		
	}


	public void actualizarEsclavosServidor(String ips) {
		ArrayList<String> ipsActualizadas= new ArrayList<String>(Arrays.asList(ips.split("$")));
		this.listaServidoresEsclavos=ipsActualizadas;
		
	}
	
	public void conexionPerdida() {
		//VentanaLogin auxventana=new VentanaLogin();
		//ControladorLogin auxcontroladorLogin= new ControladorLogin(auxventana);
		//ventana.setControlador(auxcontroladorLogin);
		controladorLogin.mostrarVentana();
		//ArrayList<String> datosNuevaConexion = auxcontroladorLogin.getDatosConexion();

			try {
				intentarConexionConServidor();
			} catch (ExcepcionErrorConexion e) {
				JOptionPane.showMessageDialog(null, "ERROR DE CONEXION :(");
				int confirmado = JOptionPane.showConfirmDialog(null,"�Desea Intentar nuevamente?");
					if (JOptionPane.OK_OPTION == confirmado) {
						conexionPerdida();
					}
					else {
						System.exit(0);
					}
			}catch (ExcepcionFinConexion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		/*this.datosConexion=datosNuevaConexion;
		try {
			this.cliente=new TCPCliente(this.datosConexion.get(0),Integer.parseInt(this.datosConexion.get(1)));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExcepcionErrorConexion e) {
			JOptionPane.showMessageDialog(null, "ERROR DE CONEXION :(");
			int confirmado = JOptionPane.showConfirmDialog(null,"�Desea Intentar nuevamente?");
				if (JOptionPane.OK_OPTION == confirmado) {
					conexionPerdidaReintento(auxcontroladorLogin);
				}
				else {
					System.exit(0);
				}
		}
		JOptionPane.showMessageDialog(null, "Conexion exitosa :D");
		*/
	}
	
	/*private void conexionPerdidaReintento(ControladorLogin auxcontroladorLogin) {
		auxcontroladorLogin.mostrarVentana();
		ArrayList<String> datosNuevaConexion = auxcontroladorLogin.getDatosConexion();
		this.datosConexion=datosNuevaConexion;
		
		try {
			this.cliente=new TCPCliente(this.datosConexion.get(0),Integer.parseInt(this.datosConexion.get(1)));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExcepcionErrorConexion e) {
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
		
	}*/
	
}
