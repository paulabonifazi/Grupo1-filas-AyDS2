package estadistico;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
@SuppressWarnings("deprecation")
public class Controlador implements ActionListener,Observer {

    private IVista vista;
    private GestorConexionEstadistico modelo;

   
	public Controlador() {
		this.modelo=new GestorConexionEstadistico();
		this.modelo.addObserver(this);
        Ventana ventana = new Ventana();
		this.vista = ventana;
        this.vista.setActionListener(this);
        ventana.setVisible(true);
       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "REGISTRAR":
                String contra = vista.getContrasenia();
                String ip = vista.getIP();
                int puerto = vista.getPuerto();
                this.modelo.loginSV(contra, ip, puerto);
            break;
            case "ACTUALIZAR":
            	break;
        }
    }
    
    @Override
    public void update(Observable o, Object arg1) {
    	if(arg1.toString().contentEquals("Exito")) {
    		this.vista.entraSV();
    	}
    	else  if (arg1.toString().contentEquals("Contrasenia")){
    		this.vista.errorIngreso("Error: contraseña invalida");
    	}
    	else if(arg1.toString().contentEquals("Conexion")) {
    		this.vista.errorIngreso("Error: se produjo un problema en la conexión.\nVerifique la IP o el puerto e intentelo de nuevo");
    	}
    	
    }

}