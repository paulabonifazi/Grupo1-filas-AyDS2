package Totem;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class VistaRegistro extends JFrame implements IVistaRegistro, ActionListener {
 
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

	
	public VistaRegistro() {
		setTitle("T\u00F3tem");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelPrincipal);
		panelPrincipal.setLayout(new GridLayout(6, 0, 0, 0));

		JPanel panelTitulo = new JPanel();
		FlowLayout fl_panelTitulo = (FlowLayout) panelTitulo.getLayout();
		fl_panelTitulo.setVgap(0);
		fl_panelTitulo.setHgap(0);
		panelTitulo.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Ingrese su DNI", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelPrincipal.add(panelTitulo);

		lblDisplay = new JLabel("");
		panelTitulo.add(lblDisplay);
		lblDisplay.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblDisplay.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panelN1 = new JPanel();
		panelPrincipal.add(panelN1);
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

		JPanel panelN2 = new JPanel();
		panelPrincipal.add(panelN2);
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

		JPanel panelN3 = new JPanel();
		panelPrincipal.add(panelN3);
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

		JPanel panelN4 = new JPanel();
		panelPrincipal.add(panelN4);
		panelN4.setLayout(new GridLayout(0, 3, 0, 0));

		JLabel lblVacia = new JLabel("");
		panelN4.add(lblVacia);

		btn0 = new JButton("0");
		btn0.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelN4.add(btn0);

		JPanel panelInferior = new JPanel();
		panelPrincipal.add(panelInferior);
		panelInferior.setLayout(new GridLayout(1, 2, 0, 0));

		btnEliminar = new JButton("<-");
		panelInferior.add(btnEliminar);
		btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 20));

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setEnabled(false);
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelInferior.add(btnAceptar);

		setActionCommands();
	}

	
	@Override
	public void setActionListener(ActionListener c) {
		this.btn0.addActionListener(c);
		this.btn1.addActionListener(c);
		this.btn2.addActionListener(c);
		this.btn3.addActionListener(c);
		this.btn4.addActionListener(c);
		this.btn5.addActionListener(c);
		this.btn6.addActionListener(c);
		this.btn7.addActionListener(c);
		this.btn8.addActionListener(c);
		this.btn9.addActionListener(c);
		this.btnAceptar.addActionListener(c);
		this.btnEliminar.addActionListener(c);
	}

	
	@Override
	public void setActionCommands() {
		this.btn0.setActionCommand(IVistaRegistro.CERO);
		this.btn1.setActionCommand(IVistaRegistro.UNO);
		this.btn2.setActionCommand(IVistaRegistro.DOS);
		this.btn3.setActionCommand(IVistaRegistro.TRES);
		this.btn4.setActionCommand(IVistaRegistro.CUATRO);
		this.btn5.setActionCommand(IVistaRegistro.CINCO);
		this.btn6.setActionCommand(IVistaRegistro.SEIS);
		this.btn7.setActionCommand(IVistaRegistro.SIETE);
		this.btn8.setActionCommand(IVistaRegistro.OCHO);
		this.btn9.setActionCommand(IVistaRegistro.NUEVE);
		this.btnAceptar.setActionCommand(IVistaRegistro.ENVIAR);
		this.btnEliminar.setActionCommand(IVistaRegistro.BACKSPACE);
	}

	
	@Override
	public void abrir() {
		setBounds(100, 500, 463, 450);
		setVisible(true);
	}

	
	@Override
	public String getLabelDisplay() {
		return this.lblDisplay.getText();
	}

	
	@Override
	public void setLabelDisplay(String texto) {
		this.lblDisplay.setText(texto);
	}

	
	@Override
	public void habilitarEnvio() {
		this.btnAceptar.setEnabled(true);
	}

	
	@Override
	public void deshabilitarEnvio() {
		this.btnAceptar.setEnabled(false);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}