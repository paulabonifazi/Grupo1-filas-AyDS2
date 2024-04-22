package SistNotificacion;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class VentanaLogin extends JFrame implements IVistaLogin {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField IPTxt;
    private JTextField PuertoTxt;
    private JTextField ContraTxt;
    ControladorLogin controlador;
    private String ip,password,port;
    
    public VentanaLogin() {
        this.ip=null;
        this.password=null;
        this.port=null;
    }
    
    public void initComponents() {
    	JTextField ip = new JTextField();
        JTextField password = new JTextField();
        JTextField port = new JTextField();
        Object[] message = {
            "IP:", ip,
            "Password:", password,
            "Port:", port
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Enter IP, Password, and Port", JOptionPane.OK_CANCEL_OPTION);
        this.ip=null;
        this.password=null;
        this.port=null;
        
        if (option == JOptionPane.OK_OPTION) {
            this.ip=ip.getText();
            this.password=password.getText();
            this.port=port.getText();
            controlador.ejecucionFinalizada();
        }
        else {
            this.ip="";
            this.password="";
            this.port="";
            controlador.ejecucionCancelada();
        }

    }

	@Override
	public void errorIngreso(String motivo) {
		JOptionPane.showMessageDialog(null, motivo);
		initComponents();
	}

	@Override
	public String getIP() {
		return ip;
	}

	@Override
	public String getPuerto() {
		return port;
	}

	@Override
	public String getContrasenia() {
		return password;
	}

	@Override
	public void setControlador(ControladorLogin c) {
		this.controlador=c;
	}
	
	
}