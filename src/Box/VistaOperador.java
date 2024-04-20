package Box;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;

public class VistaOperador extends JFrame implements IVistaOperador, ActionListener {
	
	private JPanel mainPanel;
	private int numeroPuesto;
	private JLabel lblDisplay;
	private JButton btnEliminar;
	private JButton btnLlamar;
	
	public VistaOperador() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);
		mainPanel.setLayout(new GridLayout(2, 0, 0, 0));

		lblDisplay = new JLabel("DNI cliente actual");
		lblDisplay.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		mainPanel.add(lblDisplay);

		JPanel panelBotones = new JPanel();
		mainPanel.add(panelBotones);
		panelBotones.setLayout(new GridLayout(0, 2, 0, 0));

		btnEliminar = new JButton("Cliente ausente");
		btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelBotones.add(btnEliminar);

		btnLlamar = new JButton("Siguiente atenci\u00F3n");
		this.btnLlamar.addActionListener(this);
		btnLlamar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelBotones.add(btnLlamar);

		setActionCommands();
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			
				VistaOperador vista = (VistaOperador) e.getSource();

				int res = JOptionPane.showConfirmDialog(vista, "¿Esta seguro que quiere cerrar el puesto de trabajo?",
						"Cerrar Puesto", JOptionPane.YES_NO_OPTION);

				if (res == JOptionPane.YES_OPTION) {
					vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
			}
		});
		
	}
	
	@Override
	public int getNumeroPuesto() {
		return this.numeroPuesto;
	}

	
	private void setActionCommands() {
		this.btnEliminar.setActionCommand(IVistaOperador.ELIMINAR);
		this.btnLlamar.setActionCommand(IVistaOperador.LLAMAR);
	}

	
	@Override
	public void setActionListener(ActionListener c) {
		this.btnLlamar.addActionListener(c);
		this.btnEliminar.addActionListener(c);
	}

	
	@Override
	public void abrir() {
		if (this.numeroPuesto != 0) {
			setBounds(1200, 100, 450, 300);
			setVisible(true);
		} else
			this.dispose();
	}

	@Override
	public String toString() {
		return "Puesto : " + this.numeroPuesto;
	}

	
	@Override
	public void setDisplay(String clienteActual) {
		this.lblDisplay.setText(clienteActual);
	}

	
	@Override
	public void dispose() {
		super.dispose();
	}

	
	@Override
	public void habilitarBotonEliminar() {
		this.btnEliminar.setEnabled(true);
	}

	
	@Override
	public void deshabilitarBotonEliminar() {
		this.btnEliminar.setEnabled(false);
	}

	@Override
	public void setNumeroPuesto(int numero) {
		this.numeroPuesto = numero;
		this.setTitle("Puesto de trabajo numero " + String.valueOf(this.numeroPuesto));
	}

	public void actionPerformed(ActionEvent e) {
		
	}
}
