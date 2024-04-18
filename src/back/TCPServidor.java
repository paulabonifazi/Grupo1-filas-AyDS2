package back;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

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

	public int getPuerto() {
		return socket.getLocalPort();
	}
	
	public void aceptarConexion() throws ExcepcionErrorAlAceptar {
		try {
			clienteSocket=socket.accept();
			out = new PrintWriter(clienteSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
		} catch (IOException e) {
			throw new ExcepcionErrorAlAceptar(); //no deberia haber una razon para que ocurra (no usamos timeouts)
		}
	}
	
	public void cerrarConexion() throws ExcecionErrorAlCerrar {
		try {
				if (clienteSocket != null) {
		            clienteSocket.close();
		        }
		        if (out != null) {
		            out.close();
		        }
		        if (in != null) {
		            in.close();
		        }
		} catch (IOException e) {
			throw new ExcecionErrorAlCerrar(); //ocurre cuando ya estaba cerrado el socket
		}
	}
	
	public String recibirmensajeDeCliente(int timeout,Boolean confirmacion) throws ExcepcionFinConexion, ExcepcionFinTimeoutLectura { //precondicion el socket no tiene que estar cerrado y el timeout es positivo
		String mensaje = null;
		try {
			clienteSocket.setSoTimeout(timeout);
			mensaje=in.readLine();		
			if(confirmacion)
				out.println("Recibido");
		} 
		catch (SocketTimeoutException e) {
	        throw new ExcepcionFinTimeoutLectura();
	    } 
		catch (SocketException a) {
			//puede saltar error al definir el timeout... esto ocurriria si el socket estuviera cerrado, pero como es precondicion no se soluciona 
		}
		catch (IOException e) {
			throw new ExcepcionFinConexion();
		}
		finally {
	        // Restablecer el timeout a 0
	        try {
	            clienteSocket.setSoTimeout(0);
	        } catch (SocketException e) {
	            // Manejar el error no es necesario
	        }
		}
		return mensaje;
	}
	
	public void enviarMensajeACliente(String mensaje, Boolean confirmacion) throws ExcepcionLecturaErronea, ExcepcionFinConexion { //confirmacion supone que se tiene liberado el inputSteam
		out.println(mensaje); //NO detecta si se corto la comunicacion
		if(confirmacion) {
			try {
				String respuesta= recibirmensajeDeCliente(0,false);
				if(!respuesta.equals("Recibido")) {
					throw new ExcepcionLecturaErronea(respuesta);
				}
			} catch (ExcepcionFinConexion e) {
				throw new ExcepcionFinConexion();
			} catch (ExcepcionFinTimeoutLectura e) {
				//no hace nada porque el timeout es 0
			} 
		}
	}
	
}
