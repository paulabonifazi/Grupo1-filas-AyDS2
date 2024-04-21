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
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Vista extends JFrame implements IVistaRegistro,KeyListener,MouseListener {

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

    public Vista() {
        setTitle("Tótem");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cartas = new CardLayout();
        cardPanel = new JPanel(cartas);
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
        
        this.entraSV();

    }

    private void configurarPanelPrincipal() {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Solicite su turno:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelTitulo.setLayout(new GridLayout(1, 1, 0, 0));
        
        txtrIngreseSuDni = new JTextArea();
        txtrIngreseSuDni.setText("Ingrese su DNI");
        txtrIngreseSuDni.setFont(new Font("Arial Black", Font.PLAIN, 50));
        txtrIngreseSuDni.setRows(4);
        txtrIngreseSuDni.setColumns(90);
        panelTitulo.add(txtrIngreseSuDni);

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
        btnAceptar.setActionCommand("ENVIAR");
        btnAceptar.setEnabled(false);
        panelInferior.add(btnAceptar);
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
        btnEliminar.addMouseListener(this);
        
        
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
        this.btnAceptar.addActionListener(c);
        this.RegistroBtn.addActionListener(c);
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
		this.cartas.show(this.cardPanel, "Principal");
		
	}

	@Override
	public void salirSV() {
		setBounds(100, 100, 450, 300);
		this.cartas.show(this.cardPanel, "Login");
		
	}
	

	@SuppressWarnings("unused")
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
	public void errorIngreso(String motivo) {
		JOptionPane.showMessageDialog(this, motivo, "Error", JOptionPane.ERROR_MESSAGE); 
	}
	
	@Override
	public void muestraRtado() {
		// TODO Auto-generated method stub
		
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
		 if(!DNI.isBlank()  && DNI.length()==8) {
			 this.btnAceptar.setEnabled(true);
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