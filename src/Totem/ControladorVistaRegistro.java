package Totem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
@SuppressWarnings("deprecation")
public class ControladorVistaRegistro implements ActionListener,Observer {

    private IVistaRegistro vista;
    private GestorConexionTotem modelo;

   
	public ControladorVistaRegistro() {
		this.modelo=new GestorConexionTotem();
		this.modelo.addObserver(this);
        VistaTotem ventana = new VistaTotem();
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
            case "ENVIAR":
            	this.modelo.registrar(vista.getDNI());
            	break;
            case "TEMPORIZADOR":
            	this.modelo.validarConexion();
            	break;
        }
    }
    
    @Override
    public void update(Observable o, Object arg1) {
    	if(arg1.toString().contentEquals("Entra")) {
    		vista.entraSV();
    	}
    	else  if (arg1.toString().contentEquals("Contrasenia")){
    		this.vista.errorIngreso("Error: contraseña invalida");
    	}
    	else if(arg1.toString().contentEquals("Conexion")) {
    		this.vista.errorIngreso("Error: se produjo un problema en la conexión.\nVerifique la IP o el puerto e intentelo de nuevo");
    		this.vista.resetearDNI();
    		this.vista.salirSV();
    	}
    	else if(arg1.toString().contentEquals("Exito")) {
    		vista.muestraRtado("Registro exitoso");
    		vista.resetearDNI();
    	}
    	else if(arg1.toString().contentEquals("DniRepetido")) {
    		vista.muestraRtado("DNI ya registrado");
    		vista.resetearDNI();
    	}
    }

}