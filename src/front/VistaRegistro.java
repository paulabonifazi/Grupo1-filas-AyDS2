package front;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class VistaRegistro extends JFrame implements IVistaRegistro, ActionListener {
	private JPanel panel;
	private JTextField txtIngreseDni;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JButton btnNewButton_5;
	private JButton btnNewButton_6;
	private JButton btnNewButton_7;
	private JButton btnNewButton_8;
	private JButton btnNewButton_9;
	private JButton btnNewButton_0;
	public VistaRegistro() {
		
		this.panel = new JPanel();
		getContentPane().add(this.panel, BorderLayout.CENTER);
		this.panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		this.txtIngreseDni = new JTextField();
		this.txtIngreseDni.setText("Ingrese DNI");
		this.panel.add(this.txtIngreseDni);
		this.txtIngreseDni.setColumns(10);
		
		this.btnNewButton_1 = new JButton("1");
		this.panel.add(this.btnNewButton_1);
		
		this.btnNewButton_2 = new JButton("2");
		this.btnNewButton_2.addActionListener(this);
		this.panel.add(this.btnNewButton_2);
		
		this.btnNewButton_3 = new JButton("3");
		this.panel.add(this.btnNewButton_3);
		
		this.btnNewButton_4 = new JButton("4");
		this.panel.add(this.btnNewButton_4);
		
		this.btnNewButton_5 = new JButton("5");
		this.panel.add(this.btnNewButton_5);
		
		this.btnNewButton_6 = new JButton("6");
		this.panel.add(this.btnNewButton_6);
		
		this.btnNewButton_7 = new JButton("7");
		this.panel.add(this.btnNewButton_7);
		
		this.btnNewButton_8 = new JButton("8");
		this.panel.add(this.btnNewButton_8);
		
		this.btnNewButton_9 = new JButton("9");
		this.panel.add(this.btnNewButton_9);
		
		this.btnNewButton_0 = new JButton("0");
		this.panel.add(this.btnNewButton_0);
	}

	public void actionPerformed(ActionEvent e) {
	}
}
