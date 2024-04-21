package Totem;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Vista extends JFrame implements IVistaRegistro,KeyListener {

    private static final long serialVersionUID = 1L;
    private JPanel cardPanel;
    private CardLayout cardLayout;
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
    private JLabel lblDisplay;
    
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
    private JTextArea textArea;

    public Vista() {
        setTitle("Tótem");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        setContentPane(cardPanel);

        panelPrincipal = new JPanel();
        panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelPrincipal.setLayout(new GridLayout(6, 0, 0, 0));
        configurarPanelPrincipal();
        configurarPanelActual();

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

        setActionCommands();
    }

    private void configurarPanelPrincipal() {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
                "Ingrese su DNI", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        
        textArea = new JTextArea();
        textArea.setRows(4);
        textArea.setColumns(100);
        panelTitulo.add(textArea);

        lblDisplay = new JLabel("");
        lblDisplay.setFont(new Font("Tahoma", Font.PLAIN, 28));
        lblDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        panelTitulo.add(lblDisplay);

        panelPrincipal.add(panelTitulo);

        JPanel panelN1 = new JPanel();
        panelN1.setLayout(new GridLayout(1, 3, 0, 0));
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
        panelN2.setLayout(new GridLayout(1, 3, 0, 0));
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
        panelN3.setLayout(new GridLayout(1, 3, 0, 0));
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
        panelN4.setLayout(new GridLayout(0, 3, 0, 0));
        btn0 = new JButton("0");
        btn0.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelN4.add(btn0);
        panelPrincipal.add(panelN4);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(1, 2, 0, 0));
        btnEliminar = new JButton("<-");
        btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panelInferior.add(btnEliminar);
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setEnabled(false);
        btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 15));
        panelInferior.add(btnAceptar);
        panelPrincipal.add(panelInferior);
    }

    private void configurarPanelActual() {
        // Aquí puedes agregar los componentes de tu ventana actual
        // Por ejemplo:
        // JLabel labelActual = new JLabel("Esta es la ventana actual");
        // panelActual.add(labelActual);
        // Añade los componentes que necesites para tu ventana actual
    }

    @Override
    public void setActionListener(ActionListener c) {
        btn0.addActionListener(c);
        btn1.addActionListener(c);
        btn2.addActionListener(c);
        btn3.addActionListener(c);
        btn4.addActionListener(c);
        btn5.addActionListener(c);
        btn6.addActionListener(c);
        btn7.addActionListener(c);
        btn8.addActionListener(c);
        btn9.addActionListener(c);
        btnAceptar.addActionListener(c);
        btnEliminar.addActionListener(c);
    }

    public void setActionCommands() {
        btn0.setActionCommand(IVistaRegistro.CERO);
        btn1.setActionCommand(IVistaRegistro.UNO);
        btn2.setActionCommand(IVistaRegistro.DOS);
        btn3.setActionCommand(IVistaRegistro.TRES);
        btn4.setActionCommand(IVistaRegistro.CUATRO);
        btn5.setActionCommand(IVistaRegistro.CINCO);
        btn6.setActionCommand(IVistaRegistro.SEIS);
        btn7.setActionCommand(IVistaRegistro.SIETE);
        btn8.setActionCommand(IVistaRegistro.OCHO);
        btn9.setActionCommand(IVistaRegistro.NUEVE);
        btnAceptar.setActionCommand(IVistaRegistro.ENVIAR);
        btnEliminar.setActionCommand(IVistaRegistro.BACKSPACE);
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
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
		setBounds(100, 100, 800, 600);
		this.Cartas.show(this.cardPanel, "Principal");
		
	}

	@Override
	public void salirSV() {
		setBounds(100, 100, 450, 300);
		this.Cartas.show(this.cardPanel, "Login");
		
	}
	

	private void confirmaDNI() {
	    int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de continuar con este DNI?", "Confirmación de DNI", JOptionPane.YES_NO_OPTION);

	    if (opcion == JOptionPane.YES_OPTION) {
	        // Acción a realizar si se presiona "Sí" o "Aceptar"
	        System.out.println("Se presionó 'Sí' para continuar con el DNI.");
	        // Aquí puedes agregar el código que quieres ejecutar cuando se confirma la acción
	        // Por ejemplo, puedes llamar a un método para procesar el DNI
	    } else {
	        // Acción a realizar si se presiona "No" o "Cancelar"
	        System.out.println("Se presionó 'No'. Cancelando la operación.");
	        // Aquí puedes agregar el código que quieres ejecutar cuando se cancela la acción
	    }
	}

	@Override
	public void muestraRtado() {
		// TODO Auto-generated method stub
		
	}
}