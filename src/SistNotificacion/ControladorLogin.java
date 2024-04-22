package SistNotificacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControladorLogin {

    private IVistaLogin vista;
    private String ip,password,port;
    
    public ControladorLogin(IVistaLogin vista) {
        this.vista = vista;
    }

    public void mostrarVentana() {
    	vista.initComponents();
    }

	public void ejecucionFinalizada() {
        this.ip=vista.getIP();
        this.password=vista.getContrasenia();
        this.port=vista.getPuerto();
	}

	public void ejecucionCancelada() {
		this.ip=null;
        this.password=null;
        this.port=null;
	}

	public ArrayList<String> getDatosConexion() {
		ArrayList<String> listadatos=new ArrayList<String>() ;
		listadatos.add(0,ip);
		listadatos.add(1,port);
		listadatos.add(2,password);
		return listadatos;
	}

}