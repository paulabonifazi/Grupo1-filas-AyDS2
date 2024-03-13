package front;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class VistaMonitor extends JFrame implements IVistaMonitor {
	
	private JPanel contentPane;
	private JLabel lblEstadoServidor1;
	private JLabel lblEstadoServidor2;
	private JLabel lblEstadoDisplay;
	private JLabel lblEstadoTotem;
	private JLabel lblEstadoPuesto1;
	private JLabel lblEstadoPuesto2;
	private JLabel lblEstadoPuesto3;
	private JLabel lblEstadoPuesto4;
	private JLabel lblEstadoPuesto5;
	private JLabel lblEstadoPuesto6;
	private JLabel lblEstadoPuesto7;
	private JLabel lblEstadoPuesto8;
	
	public VistaMonitor() {
		setTitle("Monitor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblServidor1 = new JLabel("Estado del Servidor 1:");
		panel.add(lblServidor1);
		lblServidor1.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblEstadoServidor1 = new JLabel("");
		panel.add(lblEstadoServidor1);
		lblEstadoServidor1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel_6);
		panel_6.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblServidor2 = new JLabel("Estado del Servidor 2:");
		panel_6.add(lblServidor2);
		lblServidor2.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblEstadoServidor2 = new JLabel("");
		panel_6.add(lblEstadoServidor2);
		lblEstadoServidor2.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblTotem = new JLabel("Estado del Totem:");
		panel_1.add(lblTotem);
		lblTotem.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblEstadoTotem = new JLabel("");
		panel_1.add(lblEstadoTotem);
		lblEstadoTotem.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel_7);
		panel_7.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblDisplay = new JLabel("Estado del Display:");
		panel_7.add(lblDisplay);
		lblDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblEstadoDisplay = new JLabel("");
		panel_7.add(lblEstadoDisplay);
		lblEstadoDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblPuesto1 = new JLabel("Estado del Puesto 1:");
		panel_2.add(lblPuesto1);
		lblPuesto1.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblEstadoPuesto1 = new JLabel("");
		panel_2.add(lblEstadoPuesto1);
		lblEstadoPuesto1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel_8);
		panel_8.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblPuesto2 = new JLabel("Estado del Puesto 2:");
		panel_8.add(lblPuesto2);
		lblPuesto2.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblEstadoPuesto2 = new JLabel("");
		panel_8.add(lblEstadoPuesto2);
		lblEstadoPuesto2.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblPuesto3 = new JLabel("Estado del Puesto 3:");
		panel_3.add(lblPuesto3);
		lblPuesto3.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblEstadoPuesto3 = new JLabel("");
		panel_3.add(lblEstadoPuesto3);
		lblEstadoPuesto3.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel_9);
		panel_9.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblPuesto4 = new JLabel("Estado del Puesto 4:");
		panel_9.add(lblPuesto4);
		lblPuesto4.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblEstadoPuesto4 = new JLabel("");
		panel_9.add(lblEstadoPuesto4);
		lblEstadoPuesto4.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblPuesto5 = new JLabel("Estado del Puesto 5:");
		panel_4.add(lblPuesto5);
		lblPuesto5.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblEstadoPuesto5 = new JLabel("");
		panel_4.add(lblEstadoPuesto5);
		lblEstadoPuesto5.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel_10);
		panel_10.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblPuesto6 = new JLabel("Estado del Puesto 6:");
		panel_10.add(lblPuesto6);
		lblPuesto6.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblEstadoPuesto6 = new JLabel("");
		panel_10.add(lblEstadoPuesto6);
		lblEstadoPuesto6.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblPuesto7 = new JLabel("Estado del Puesto 7:");
		panel_5.add(lblPuesto7);
		lblPuesto7.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblEstadoPuesto7 = new JLabel("");
		panel_5.add(lblEstadoPuesto7);
		lblEstadoPuesto7.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel_11);
		panel_11.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblPuesto8 = new JLabel("Estado del Puesto 8:");
		panel_11.add(lblPuesto8);
		lblPuesto8.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblEstadoPuesto8 = new JLabel("");
		panel_11.add(lblEstadoPuesto8);
		lblEstadoPuesto8.setHorizontalAlignment(SwingConstants.CENTER);
	}

	@Override
	public void abrir() {
		setBounds(1220, 400, 600, 600);
		setVisible(true);
	}

	@Override
	public void setLabelTotem(String label) {
		this.lblEstadoTotem.setText(label);
	}

	@Override
	public void setLabelServidor1(String label) {
		this.lblEstadoServidor1.setText(label);
	}

	@Override
	public void setLabelServidor2(String label) {
		this.lblEstadoServidor2.setText(label);
	}

	@Override
	public void setLabelDisplay(String label) {
		this.lblEstadoDisplay.setText(label);
	}

	@Override
	public void setLabelPuesto1(String label) {
		this.lblEstadoPuesto1.setText(label);
	}

	@Override
	public void setLabelPuesto2(String label) {
		this.lblEstadoPuesto2.setText(label);
	}

	@Override
	public void setLabelPuesto3(String label) {
		this.lblEstadoPuesto3.setText(label);
	}

	@Override
	public void setLabelPuesto4(String label) {
		this.lblEstadoPuesto4.setText(label);
	}

	@Override
	public void setLabelPuesto5(String label) {
		this.lblEstadoPuesto5.setText(label);
	}

	@Override
	public void setLabelPuesto6(String label) {
		this.lblEstadoPuesto6.setText(label);
	}

	@Override
	public void setLabelPuesto7(String label) {
		this.lblEstadoPuesto7.setText(label);
	}

	@Override
	public void setLabelPuesto8(String label) {
		this.lblEstadoPuesto8.setText(label);
	}

}
