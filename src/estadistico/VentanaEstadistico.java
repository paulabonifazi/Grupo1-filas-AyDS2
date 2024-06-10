package estadistico;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;

public class VentanaEstadistico extends JFrame implements KeyListener,IVistaEstadistico {

    private static final long serialVersionUID = 1L;
    private JPanel Ventana;
    private JTextField IPTxt;
    private JTextField PuertoTxt;
    private JTextField ContraTxt;
    private JButton RegistroBtn;
    private JTextArea TpromEspTxt;
    private JTextArea ClientesTxt;
    private JTextArea TpromSoliTxt;
    private JTextArea TpromAtnTxt;
    private JTextArea AtencionesTxt;
    private CardLayout Cartas;
    private JButton ActualizaBtn;

    public VentanaEstadistico() {
        initComponents();
    }

    private void initComponents() {
    	setTitle("Estadistico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        Ventana = new JPanel();
        Ventana.setBorder(new EmptyBorder(20, 20, 20, 20)); // Añadido margen
        setContentPane(Ventana);
        this.Cartas=new CardLayout();
        Ventana.setLayout(this.Cartas);

        JPanel Login = new JPanel();
        Login.setLayout(new GridLayout(0, 1, 0, 10)); // Espaciado vertical
        Ventana.add(Login, "Login");

        // Panel para IP
        JPanel PanelIP = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Alineación izquierda
        Login.add(PanelIP);

        JLabel IpLb = new JLabel("IP:             ");
        PanelIP.add(IpLb);

        IPTxt = new JTextField();
        IPTxt.addKeyListener(this);
        IPTxt.setPreferredSize(new Dimension(300, 20)); // Tamaño constante
        PanelIP.add(IPTxt);
        IPTxt.setColumns(30);

        // Panel para Puerto
        JPanel PanelPuerto = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Alineación izquierda
        Login.add(PanelPuerto);

        JLabel PuertoLb = new JLabel("Puerto:       ");
        PanelPuerto.add(PuertoLb);

        PuertoTxt = new JTextField();
        PuertoTxt.addKeyListener(this);
        PuertoTxt.setPreferredSize(new Dimension(300, 20)); // Tamaño constante
        PanelPuerto.add(PuertoTxt);
        PuertoTxt.setColumns(30);

        // Panel para Contraseña
        JPanel PanelContra = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Alineación izquierda
        Login.add(PanelContra);

        JLabel ContraLb = new JLabel("Contraseña:");
        PanelContra.add(ContraLb);

        ContraTxt = new JTextField();
        ContraTxt.addKeyListener(this);
        ContraTxt.setPreferredSize(new Dimension(300, 20)); // Tamaño constante
        PanelContra.add(ContraTxt);
        ContraTxt.setColumns(30);

        // Panel para el botón Registrarse
        JPanel PanelReg = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Alineación central
        Login.add(PanelReg);

        RegistroBtn = new JButton("Registrarse");
        RegistroBtn.setPreferredSize(new Dimension(120, 30)); // Tamaño fijo
        RegistroBtn.setEnabled(false);
        RegistroBtn.setActionCommand("REGISTRAR");
        PanelReg.add(RegistroBtn);
        
        JPanel Estadisticas = new JPanel();
        Ventana.add(Estadisticas, "Estadisticas");
        Estadisticas.setLayout(new BorderLayout(0, 0));
        
        JPanel PanelActualizar = new JPanel();
        Estadisticas.add(PanelActualizar, BorderLayout.SOUTH);
        
        ActualizaBtn = new JButton("Actualizar");
        ActualizaBtn.setActionCommand("ACTUALIZAR");
        PanelActualizar.add(ActualizaBtn);
        
        JPanel PanelEstadisticos = new JPanel();
        Estadisticas.add(PanelEstadisticos, BorderLayout.CENTER);
        PanelEstadisticos.setLayout(new GridLayout(0, 2, 0, 0));
        
        JPanel panelTiempos = new JPanel();
        PanelEstadisticos.add(panelTiempos);
        panelTiempos.setLayout(new GridLayout(0, 1, 0, 0));
        
        JPanel panelTEsp = new JPanel();
        panelTiempos.add(panelTEsp);
        panelTEsp.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel TpromEspLbl = new JLabel("T. Espera Prom:            ");
        panelTEsp.add(TpromEspLbl);
        
        TpromEspTxt = new JTextArea();
        TpromEspTxt.setColumns(11);
        panelTEsp.add(TpromEspTxt);
        
        JPanel panelTSoli = new JPanel();
        panelTiempos.add(panelTSoli);
        
        JLabel TpromSoliLbl = new JLabel("T. Solicitud Prom:          ");
        panelTSoli.add(TpromSoliLbl);
        
        TpromSoliTxt = new JTextArea();
        TpromSoliTxt.setColumns(11);
        panelTSoli.add(TpromSoliTxt);
        
        JPanel panelTAtn = new JPanel();
        panelTiempos.add(panelTAtn);
        
        JLabel TpromAtnLbl = new JLabel("T. Atencion Promedio:    ");
        panelTAtn.add(TpromAtnLbl);
        
        TpromAtnTxt = new JTextArea();
        TpromAtnTxt.setColumns(11);
        panelTAtn.add(TpromAtnTxt);
        
        JPanel PanelCant = new JPanel();
        PanelEstadisticos.add(PanelCant);
        PanelCant.setLayout(new GridLayout(4, 1, 0, 0));
        
        JPanel PanelCantVacio = new JPanel();
        PanelCant.add(PanelCantVacio);
        
        JPanel PanelCantAtenciones = new JPanel();
        PanelCant.add(PanelCantAtenciones);
        
        JLabel AtencionesLbl = new JLabel("Cant. Atenciones:          ");
        PanelCantAtenciones.add(AtencionesLbl);
        
        AtencionesTxt = new JTextArea();
        AtencionesTxt.setColumns(11);
        PanelCantAtenciones.add(AtencionesTxt);
        
        JPanel panel_8 = new JPanel();
        PanelCant.add(panel_8);
        
        JLabel ClientesLbl = new JLabel("Cant. clientes en espera:");
        panel_8.add(ClientesLbl);
        
        ClientesTxt = new JTextArea();
        ClientesTxt.setColumns(11);
        panel_8.add(ClientesTxt);
    }

	public void keyPressed(KeyEvent e) {
	}
	public void keyReleased(KeyEvent e) {
		int puerto=0;
		try {
			puerto=Integer.parseInt(this.PuertoTxt.getText());
		}
		catch(NumberFormatException a){
			//nada
		}
		if(puerto>0 && !this.ContraTxt.getText().isBlank()&&!this.ContraTxt.getText().isEmpty() &&!this.IPTxt.getText().isBlank()&&!this.IPTxt.getText().isEmpty()) {
			this.RegistroBtn.setEnabled(true);
		}
		else
			this.RegistroBtn.setEnabled(false);
	}
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		this.RegistroBtn.addActionListener(actionListener);
		this.ActualizaBtn.addActionListener(actionListener);
	}

	@Override
	public void entraSV() {
		this.Cartas.show(Ventana, "Estadisticas");
	}

	@Override
	public void salirSV() {
		this.Cartas.show(Ventana, "Login");
	}

	@Override
	public void errorIngreso(String motivo) {
		JOptionPane.showMessageDialog(this, motivo, "Error", JOptionPane.ERROR_MESSAGE); 
	}

	@Override
	public void actualiza(String cantAtendidos,String TPromEsp,String TPromLlam,String TPromAtn,String tamCola) {
		this.TpromAtnTxt.setText(TPromAtn+" seg.");
		this.TpromEspTxt.setText(TPromEsp+" seg.");
		this.TpromSoliTxt.setText(TPromLlam+" seg.");
		this.ClientesTxt.setText(tamCola+" clientes");
		this.AtencionesTxt.setText(cantAtendidos+" atenciones");
	}

	@Override
	public String getIP() {
		return this.IPTxt.getText();
	}

	@Override
	public int getPuerto() {
		return Integer.parseInt(this.PuertoTxt.getText());
	}

	@Override
	public String getContrasenia() {
		return this.ContraTxt.getText();
	}
}