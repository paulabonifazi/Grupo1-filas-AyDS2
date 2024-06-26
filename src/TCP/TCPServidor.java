package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import Excepciones.ExcepcionErrorAlCerrar;
import Excepciones.ExcepcionDeInterrupcion;
import Excepciones.ExcepcionErrorAlAceptar;
import Excepciones.ExcepcionFinConexion;
import Excepciones.ExcepcionFinTimeoutAceptar;
import Excepciones.ExcepcionFinTimeoutLectura;
import Excepciones.ExcepcionLecturaErronea;
import Excepciones.ExcepcionNoHayPuertos;

public class TCPServidor {
	private ServerSocket socket;
	private Socket clienteSocket;
	private BufferedReader in;
	private PrintWriter out;
	
	
	public TCPServidor() throws ExcepcionNoHayPuertos {
		try {
			this.socket= new ServerSocket(0); //puerto automatico
		} catch (IOException e) {
			throw new ExcepcionNoHayPuertos(); //no deberia ocurrir nunca, pero por si a caso
		}
	}
	
	public TCPServidor(int puerto) throws ExcepcionNoHayPuertos {
		try {
			this.socket= new ServerSocket(puerto); //puerto automatico
		} catch (IOException e) {
			throw new ExcepcionNoHayPuertos(); //no deberia ocurrir nunca, pero por si a caso
		}
	}

	public int getPuerto() {
		int puerto=0;
		if (socket!=null){
			puerto=socket.getLocalPort();
		}
		return puerto;
		
	}
	
	public String getIPServidor() {
		InetAddress direccionIP;
		String ip=null;
		try {
			direccionIP = InetAddress.getLocalHost();
			ip= direccionIP.getHostAddress();
		} catch (UnknownHostException e) {
		}
		return ip;
	}
	
	public String getIPCliente() {
		String IP=null;
		if (clienteSocket!=null){
			IP=clienteSocket.getInetAddress().getHostAddress();
		}
		return IP;
	}
	
	public void aceptarConexion(int timeout) throws ExcepcionErrorAlAceptar, ExcepcionFinTimeoutAceptar, ExcepcionDeInterrupcion {
		try {
			socket.setSoTimeout(timeout);
			clienteSocket=socket.accept();
			out = new PrintWriter(clienteSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
		}
		catch (SocketTimeoutException  a) {
				throw new ExcepcionFinTimeoutAceptar();
			}
 		catch (SocketException e) {
			throw new ExcepcionDeInterrupcion();
		}
		catch (IOException e) {
			throw new ExcepcionErrorAlAceptar(); //no deberia haber una razon para que ocurra (no usamos timeouts)
		}
		finally {
	        // Restablecer el timeout a 0
	        try {
	        	if(!socket.isClosed()) //se debe verificar, porque cuando se cierra el servidor, lo que se hace es Serversocket.close
						socket.setSoTimeout(0);
				} catch (SocketException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void cerrarConexion() throws ExcepcionErrorAlCerrar {
		try {
				if (out!= null) {
		            out.close();
		        }
		        if (in != null) {
		            in.close();
		        }
				if (clienteSocket!=null&&!clienteSocket.isClosed()) {
		            clienteSocket.close();
		        }
		        
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExcepcionErrorAlCerrar(); //ocurre cuando ya estaba cerrado el socket
		}
	}
	
	public String recibirmensajeDeCliente(int timeout,Boolean confirmacion) throws ExcepcionFinConexion, ExcepcionFinTimeoutLectura, ExcepcionDeInterrupcion { //precondicion el socket no tiene que estar cerrado y el timeout es positivo
		String mensaje = null;
		try {
			clienteSocket.setSoTimeout(timeout);
			mensaje=in.readLine();		
			if (mensaje==null)
				throw new ExcepcionFinConexion();
			if(confirmacion)
				out.println("Recibido");
		}
		catch (SocketTimeoutException e) {
	        throw new ExcepcionFinTimeoutLectura();
	    } 
		catch (SocketException e) {
			throw new ExcepcionDeInterrupcion();
		}
		catch (IOException e) {
			throw new ExcepcionFinConexion();
		}
		finally {
	        // Restablecer el timeout a 0
	            try {
	            	if(!clienteSocket.isClosed())
	            		clienteSocket.setSoTimeout(0);
				} catch (SocketException e) {
					e.printStackTrace();
				}
		}
		return mensaje;
	}
	
	public void enviarMensajeACliente(String mensaje, Boolean confirmacion) throws ExcepcionLecturaErronea, ExcepcionFinConexion, ExcepcionDeInterrupcion { //confirmacion supone que se tiene liberado el inputSteam
		out.println(mensaje); //NO detecta si se corto la comunicacion
		if(confirmacion) {
			try {
				String respuesta= recibirmensajeDeCliente(0,false);
				if(!respuesta.equals("Recibido")) {
					throw new ExcepcionLecturaErronea(respuesta);
				}
			} catch (ExcepcionFinTimeoutLectura e) {
				//no hace nada porque el timeout es 0
			} 
		}
	}
	
	public void cerrarPuertoServidor() throws ExcepcionErrorAlCerrar {
		try {
			if(socket!=null&&!socket.isClosed())
				this.socket.close();
		} catch (IOException e) {
			throw new ExcepcionErrorAlCerrar(); //ocurre cuando ya estaba cerrado el socket
		}
	}
	
	public boolean validarIPCliente(String IPEntrante) {
		return this.getIPCliente().equals(IPEntrante);
	}
}
