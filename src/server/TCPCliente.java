package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPCliente {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;

	public TCPCliente(String IP, int puerto) throws ExcepcionErrorConexion { 
			try {
				socket= new Socket(IP,puerto);
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				throw new ExcepcionErrorConexion();
			}
	}

	public void cerrarConexion() throws ExcecionErrorAlCerrar {
		try {
				if ( socket != null) {
		            socket.close();
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
	
	public String recibirmensajeDeServidor(Boolean confirmacion) throws ExcepcionFinConexion { //precondicion el socket no tiene que estar cerrado y el timeout es positivo
		String mensaje = null;
		try {
			mensaje=in.readLine();
			if(confirmacion)
				out.println("Recibido");
		}
		catch (IOException e) {
			throw new ExcepcionFinConexion();
		}
		return mensaje;
	}

	public void enviarMensajeAlSerivor(String mensaje, Boolean confirmacion ) throws ExcepcionLecturaErronea, ExcepcionFinConexion { ////confirmacion supone que se tiene liberado el inputSteam
		out.println(mensaje); //NO detecta si se corto la comunicacion
		if(confirmacion) {
			try {
				String respuesta= recibirmensajeDeServidor(false);
				if(!respuesta.equals("Recibido")) {
					throw new ExcepcionLecturaErronea(respuesta);
				}
			} catch (ExcepcionFinConexion e) {
				throw new ExcepcionFinConexion();
			} 
		}
	}

}
