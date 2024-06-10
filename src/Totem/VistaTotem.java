package Totem;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class VistaTotem extends JFrame implements IVistaRegistro,KeyListener,MouseListener {

    private static final long serialVersionUID = 1L;
    private JPanel cardPanel;
    private CardLayout cartas;
    private JPanel panelPrincipal;
    private JButton btn0;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JButton btn5;
    private JButton btn6;
    private JButton btn7;
    private JButton btn8;
    private JButton btn9;
    private JButton btnAceptar;
    private JButton btnEliminar;
    
    private JTextField IPTxt;
    private JTextField PuertoTxt;
    private JTextField ContraTxt;
    private JButton RegistroBtn;
    private JTextArea txtrIngreseSuDni;
    private String DNI="";
    private ActionListener actionlistener;
    private JPanel panel_1;
    private JPanel panel_2;
    private JPanel panel_3;
    private JPanel panel_4;
    private JPanel panel_5;
    
    private javax.swing.Timer inactivityTimer;

    public VistaTotem() {
        setTitle("Tótem");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cartas = new CardLayout();
        cardPanel = new JPanel(cartas);
        setContentPane(cardPanel);
        
        panelPrincipal = new JPanel();
        panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelPrincipal.setLayout(new GridLayout(6, 0, 0, 0));
        configurarPanelPrincipal();

        cardPanel.add(panelPrincipal, "Principal");
        JPanel Login = new JPanel();
        Login.setLayout(new GridLayout(0, 1, 0, 10)); // Espaciado vertical
        cardPanel.add(Login, "Login");

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
        
        this.salirSV();
    }

    private void configurarPanelPrincipal() {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Solicite su turno:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelTitulo.setLayout(new GridLayout(1, 1, 0, 0));
        
        txtrIngreseSuDni = new JTextArea();
        txtrIngreseSuDni.setText("Ingrese su DNI");
        txtrIngreseSuDni.setFont(new Font("Arial Black", Font.PLAIN, 50));
        txtrIngreseSuDni.setRows(4);
        txtrIngreseSuDni.setColumns(70);
        panelTitulo.add(txtrIngreseSuDni);

        panelPrincipal.add(panelTitulo);

        JPanel panelN1 = new JPanel();
        panelN1.setLayout(new GridLayout(0, 5, 0, 0));
        
        panel_1 = new JPanel();
        panelN1.add(panel_1);
        btn1 = new JButton("1");
        btn1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelN1.add(btn1);
        btn2 = new JButton("2");
        btn2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelN1.add(btn2);
        btn3 = new JButton("3");
        btn3.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelN1.add(btn3);
        panelPrincipal.add(panelN1);

        JPanel panelN2 = new JPanel();
        panelN2.setLayout(new GridLayout(0, 5, 0, 0));
        
        panel_2 = new JPanel();
        panelN2.add(panel_2);
        btn4 = new JButton("4");
        btn4.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelN2.add(btn4);
        btn5 = new JButton("5");
        btn5.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelN2.add(btn5);
        btn6 = new JButton("6");
        btn6.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelN2.add(btn6);
        panelPrincipal.add(panelN2);

        JPanel panelN3 = new JPanel();
        panelN3.setLayout(new GridLayout(0, 5, 0, 0));
        
        panel_3 = new JPanel();
        panelN3.add(panel_3);
        btn7 = new JButton("7");
        btn7.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelN3.add(btn7);
        btn8 = new JButton("8");
        btn8.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelN3.add(btn8);
        btn9 = new JButton("9");
        btn9.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelN3.add(btn9);
        panelPrincipal.add(panelN3);

        JPanel panelN4 = new JPanel();
        panelN4.setLayout(new GridLayout(0, 5, 0, 0));
        
        panel_4 = new JPanel();
        panelN4.add(panel_4);
        btn0 = new JButton("0");
        btn0.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelN4.add(btn0);
        panelPrincipal.add(panelN4);
        btnEliminar = new JButton("<-");
        panelN4.add(btnEliminar);
        btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnAceptar = new JButton("Aceptar");
        panelN4.add(btnAceptar);
        btnAceptar.setEnabled(false);
        btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnAceptar.setActionCommand("ENVIAR");
        btnAceptar.setEnabled(false);
        btnAceptar.addMouseListener(this);
        btnEliminar.addMouseListener(this);
        
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(0, 5, 0, 0));
        
        panel_5 = new JPanel();
        panelInferior.add(panel_5);
        panelPrincipal.add(panelInferior);
        
        
        btn0.addMouseListener(this);
        btn1.addMouseListener(this);
        btn2.addMouseListener(this);
        btn3.addMouseListener(this);
        btn4.addMouseListener(this);
        btn5.addMouseListener(this);
        btn6.addMouseListener(this);
        btn7.addMouseListener(this);
        btn8.addMouseListener(this);
        btn9.addMouseListener(this);
        
        
    }

    @Override
    public void setActionListener(ActionListener c) {
        this.actionlistener=c;
        this.RegistroBtn.addActionListener(c);
        inactivityTimer = new javax.swing.Timer(5000, c);
        inactivityTimer.setActionCommand("TEMPORIZADOR");
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	private void reiniciarTemporizador() {
        if (inactivityTimer.isRunning()) {
            inactivityTimer.restart();
        } else {
            inactivityTimer.start();
        }
    }
	
	private void pausarTemporizador() {
        if (inactivityTimer!=null&& inactivityTimer.isRunning()) {
            inactivityTimer.stop();
        } 
    }
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
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

	public void entraSV() {
		setBounds(100, 100, 800, 800);
		this.cartas.show(this.cardPanel, "Principal");
		reiniciarTemporizador();
	}

	@Override
	public void salirSV() {
		this.pausarTemporizador();
		setBounds(100, 100, 450, 300);
		this.cartas.show(this.cardPanel, "Login");
		
	}
	

	private void confirmaDNI() {
        
        // Establecer el tamaño de fuente para el mensaje y los botones
        Font font = new Font("Arial", Font.PLAIN, 70);
        UIManager.put("OptionPane.messageFont", font);
        
        font = new Font("Arial", Font.PLAIN, 80);
        // Establecer el tamaño de fuente y el espacio entre botones
        UIManager.put("OptionPane.buttonFont", font);

        int opcion = JOptionPane.showConfirmDialog(
            null,
            "¿Estás seguro de continuar con este DNI?",
            "Confirmación de DNI",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            this.pausarTemporizador();
        	ActionEvent evento=new ActionEvent(opcion, 0, "ENVIAR");
            this.actionlistener.actionPerformed(evento);
        } else {
        	
        }
    }


	
	public void muestraRtado(String mensaje) {
        // Establecer la fuente para el mensaje principal
        Font fontPrincipal = new Font("Arial", Font.PLAIN, 70);

        // Crear JLabel para el mensaje principal
        JLabel labelPrincipal = new JLabel(mensaje);
        labelPrincipal.setFont(fontPrincipal);
        labelPrincipal.setHorizontalAlignment(SwingConstants.CENTER);

        // Establecer la fuente para el mensaje secundario
        Font fontSecundario = new Font("Arial", Font.PLAIN, 40);

        // Crear JLabel para el mensaje secundario
        JLabel labelSecundario = new JLabel("Espere a ser atendido");
        labelSecundario.setFont(fontSecundario);
        labelSecundario.setHorizontalAlignment(SwingConstants.CENTER);

        // Crear panel para contener ambos JLabels
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(labelPrincipal);
        panel.add(labelSecundario);

        // Mostrar el JOptionPane con el panel personalizado
        JOptionPane.showConfirmDialog(
            null,
            panel,
            "Mensaje",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        this.reiniciarTemporizador();
    }
	@Override
	public void errorIngreso(String motivo) {
	    // Establecer la fuente para el mensaje de error
	    Font fontMensajeError = new Font("Arial", Font.PLAIN, 12);

	    // Crear JLabel para el mensaje de error
	    JLabel labelMensaje = new JLabel(motivo);
	    labelMensaje.setFont(fontMensajeError);
	    labelMensaje.setHorizontalAlignment(SwingConstants.CENTER);

	    // Crear panel para contener el JLabel
	    JPanel panel = new JPanel(new GridLayout(1, 1));
	    panel.add(labelMensaje);

	    // Personalizar el tamaño del botón "Aceptar"
	    UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 12));
	    UIManager.put("OptionPane.buttonPadding", 10); // Espacio alrededor del botón
	    
	    // Mostrar el JOptionPane con el panel personalizado y un botón "Aceptar"
	    @SuppressWarnings("unused")
		int option = JOptionPane.showOptionDialog(
	        this,
	        panel,
	        "Error",
	        JOptionPane.DEFAULT_OPTION,
	        JOptionPane.ERROR_MESSAGE,
	        null,
	        new String[]{"Aceptar"},
	        "Aceptar"
	    );

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

	@Override
	public String getDNI() {
		return this.DNI;
	}

	@Override
	public void resetearDNI() {
		this.DNI="";
		this.txtrIngreseSuDni.setText("Ingrese su DNI");
		this.btnAceptar.setEnabled(false);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Object objt=e.getSource();
		 if (objt == this.btn0) {
			 this.DNI+="0";
			 this.txtrIngreseSuDni.setText(DNI);
		 }else if(objt==this.btn1) {
			 this.DNI+="1";
			 this.txtrIngreseSuDni.setText(DNI);
		 }else if(objt==this.btn2) {
			 this.DNI+="2";
			 this.txtrIngreseSuDni.setText(DNI);
		 }else if(objt==this.btn3) {
			 this.DNI+="3";
			 this.txtrIngreseSuDni.setText(DNI);
		 }else if(objt==this.btn4) {
			 this.DNI+="4";
			 this.txtrIngreseSuDni.setText(DNI);
		 }else if(objt==this.btn5) {
			 this.DNI+="5";
			 this.txtrIngreseSuDni.setText(DNI);
		 }else if(objt==this.btn6) {
			 this.DNI+="6";
			 this.txtrIngreseSuDni.setText(DNI);
		 }else if(objt==this.btn7) {
			 this.DNI+="7";
			 this.txtrIngreseSuDni.setText(DNI);
		 }else if(objt==this.btn8) {
			 this.DNI+="8";
			 this.txtrIngreseSuDni.setText(DNI);
		 }else if(objt==this.btn9) {
			 this.DNI+="9";
			 this.txtrIngreseSuDni.setText(DNI);
		 }else if(objt==this.btnEliminar) {
			 if (DNI!=null && DNI.length() > 0) {
			        DNI= DNI.substring(0, DNI.length() - 1);
			    }
			 this.txtrIngreseSuDni.setText(DNI);
		 }
		 else if(objt==this.btnAceptar && btnAceptar.isEnabled()) {
			 confirmaDNI();
		 }
		 if(!DNI.isBlank()  && DNI.length()==8) {
			 this.btnAceptar.setEnabled(true);
		 }else if(DNI.isBlank()) {
			 this.txtrIngreseSuDni.setText("Ingrese su DNI");
		 }
		 else
			 this.btnAceptar.setEnabled(false);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}