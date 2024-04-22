package Box;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.FlowLayout;

public class VistaOperador extends JFrame implements IVistaOperador {
	private ControladorVistaOperador controlador;
	private int numeroPuesto;
	private JPanel SolicitarCliente;
	private JButton btnSolicitarCliente;
	private JLabel lblBoxSolicitarCliente;
	private JPanel Esperar;
	private JButton btnCancelarEspere;
	private JLabel lblEspere;
	private JPanel ClienteActual;
	private JLabel lblclienteActual;
	private JPanel botones;
	private JButton btnAusente;
	private JButton btnAtendido;
	private JPanel Ausencia;
	private JButton btnNoAusencia;
	private JButton btnSiAusencia;
	private JLabel lblAusencia;
	private JPanel Atencion;
	private JButton btnNoAtencion;
	private JButton btnSiAtencion;
	private JLabel lblconfirmaLaAtencin;
	private JLabel lblEstadoCola;
	
	public VistaOperador() {
		setTitle("BOX");
		getContentPane().setLayout(new CardLayout(0, 0));
		
		this.SolicitarCliente = new JPanel();
		getContentPane().add(this.SolicitarCliente, "name_75960888418000");
		this.setSize(new Dimension(700, 600));
		
		this.btnSolicitarCliente = new JButton("Solicitar cliente");
		this.btnSolicitarCliente.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
		this.lblBoxSolicitarCliente = new JLabel("Box");
		this.lblBoxSolicitarCliente.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblBoxSolicitarCliente.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GroupLayout gl_solicitarCliente = new GroupLayout(this.SolicitarCliente);
		gl_solicitarCliente.setHorizontalGroup(
			gl_solicitarCliente.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_solicitarCliente.createSequentialGroup()
					.addGroup(gl_solicitarCliente.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_solicitarCliente.createSequentialGroup()
							.addGap(178)
							.addComponent(btnSolicitarCliente, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_solicitarCliente.createSequentialGroup()
							.addGap(575)
							.addComponent(lblBoxSolicitarCliente, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(43, Short.MAX_VALUE))
		);
		gl_solicitarCliente.setVerticalGroup(
			gl_solicitarCliente.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_solicitarCliente.createSequentialGroup()
					.addGap(30)
					.addComponent(lblBoxSolicitarCliente, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addGap(157)
					.addComponent(btnSolicitarCliente, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(304, Short.MAX_VALUE))
		);
		this.SolicitarCliente.setLayout(gl_solicitarCliente);
		
		this.Esperar = new JPanel();
		getContentPane().add(this.Esperar, "name_75989655845200");
		
		this.btnCancelarEspere = new JButton("Cancelar");
		this.btnCancelarEspere.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
		this.lblEspere = new JLabel("Espere...");
		this.lblEspere.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblEspere.setFont(new Font("Tahoma", Font.PLAIN, 40));
		
		this.lblEstadoCola = new JLabel("Estado de la cola: ");
		this.lblEstadoCola.setFont(new Font("Tahoma", Font.PLAIN, 30));
		GroupLayout gl_esperar = new GroupLayout(this.Esperar);
		gl_esperar.setHorizontalGroup(
			gl_esperar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_esperar.createSequentialGroup()
					.addGap(164)
					.addGroup(gl_esperar.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnCancelarEspere, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
						.addComponent(lblEspere, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 370, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(150, Short.MAX_VALUE))
				.addGroup(gl_esperar.createSequentialGroup()
					.addGap(22)
					.addComponent(lblEstadoCola, GroupLayout.PREFERRED_SIZE, 449, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(215, Short.MAX_VALUE))
		);
		gl_esperar.setVerticalGroup(
			gl_esperar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_esperar.createSequentialGroup()
					.addGap(191)
					.addComponent(lblEspere, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnCancelarEspere, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addGap(72)
					.addComponent(lblEstadoCola, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(154, Short.MAX_VALUE))
		);
		this.Esperar.setLayout(gl_esperar);
		
		this.ClienteActual = new JPanel();
		getContentPane().add(this.ClienteActual, "name_76000204426300");
		
		this.lblclienteActual = new JLabel("Cliente");
		this.lblclienteActual.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblclienteActual.setFont(new Font("Tahoma", Font.PLAIN, 40));
		
		this.botones = new JPanel();
		this.botones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		this.btnAusente = new JButton("Ausente");
		this.btnAusente.setFont(new Font("Tahoma", Font.PLAIN, 30));
		this.botones.add(this.btnAusente);
		
		this.btnAtendido = new JButton("Atendido");
		this.btnAtendido.setFont(new Font("Tahoma", Font.PLAIN, 30));
		this.botones.add(this.btnAtendido);
		GroupLayout gl_clienteActual = new GroupLayout(this.ClienteActual);
		gl_clienteActual.setHorizontalGroup(
			gl_clienteActual.createParallelGroup(Alignment.LEADING)
				.addGap(0, 682, Short.MAX_VALUE)
				.addComponent(this.lblclienteActual, GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
				.addComponent(this.botones, GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
		);
		gl_clienteActual.setVerticalGroup(
			gl_clienteActual.createParallelGroup(Alignment.LEADING)
				.addGap(0, 474, Short.MAX_VALUE)
				.addGroup(gl_clienteActual.createSequentialGroup()
					.addComponent(this.lblclienteActual, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(this.botones, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
		);
		this.ClienteActual.setLayout(gl_clienteActual);
		
		this.Ausencia = new JPanel();
		getContentPane().add(this.Ausencia, "name_76009709099300");

		this.btnNoAusencia = new JButton("NO");
		this.btnNoAusencia.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
		this.btnSiAusencia = new JButton("SI");
		this.btnSiAusencia.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
		this.lblAusencia = new JLabel("\u00BFConfirma la ausencia del cliente?");
		this.lblAusencia.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblAusencia.setFont(new Font("Tahoma", Font.PLAIN, 30));
		GroupLayout gl_ausencia = new GroupLayout(this.Ausencia);
		gl_ausencia.setHorizontalGroup(
			gl_ausencia.createParallelGroup(Alignment.LEADING)
				.addGap(0, 682, Short.MAX_VALUE)
				.addGroup(gl_ausencia.createSequentialGroup()
					.addGap(84)
					.addGroup(gl_ausencia.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_ausencia.createSequentialGroup()
							.addComponent(this.btnNoAusencia, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(this.btnSiAusencia, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
						.addComponent(this.lblAusencia, GroupLayout.PREFERRED_SIZE, 521, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(77, Short.MAX_VALUE))
		);
		gl_ausencia.setVerticalGroup(
			gl_ausencia.createParallelGroup(Alignment.LEADING)
				.addGap(0, 474, Short.MAX_VALUE)
				.addGroup(gl_ausencia.createSequentialGroup()
					.addGap(148)
					.addComponent(this.lblAusencia, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(110)
					.addGroup(gl_ausencia.createParallelGroup(Alignment.BASELINE)
						.addComponent(this.btnNoAusencia, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.btnSiAusencia, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(103, Short.MAX_VALUE))
		);
		this.Ausencia.setLayout(gl_ausencia);
		
		this.Atencion = new JPanel();
		getContentPane().add(this.Atencion, "name_76019750221700");
		
		this.btnNoAtencion = new JButton("NO");
		this.btnNoAtencion.setFont(new Font("Tahoma", Font.PLAIN, 30));

		this.btnSiAtencion = new JButton("SI");
		this.btnSiAtencion.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
		this.lblconfirmaLaAtencin = new JLabel("\u00BFConfirma la atenci\u00F3n del cliente?");
		this.lblconfirmaLaAtencin.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblconfirmaLaAtencin.setFont(new Font("Tahoma", Font.PLAIN, 30));
		GroupLayout gl_atencion = new GroupLayout(this.Atencion);
		gl_atencion.setHorizontalGroup(
			gl_atencion.createParallelGroup(Alignment.LEADING)
				.addGap(0, 682, Short.MAX_VALUE)
				.addGroup(gl_atencion.createSequentialGroup()
					.addGap(84)
					.addGroup(gl_atencion.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_atencion.createSequentialGroup()
							.addComponent(this.btnNoAtencion, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(this.btnSiAtencion, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
						.addComponent(this.lblconfirmaLaAtencin, GroupLayout.PREFERRED_SIZE, 521, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(77, Short.MAX_VALUE))
		);
		gl_atencion.setVerticalGroup(
			gl_atencion.createParallelGroup(Alignment.LEADING)
				.addGap(0, 474, Short.MAX_VALUE)
				.addGroup(gl_atencion.createSequentialGroup()
					.addGap(148)
					.addComponent(this.lblconfirmaLaAtencin, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(110)
					.addGroup(gl_atencion.createParallelGroup(Alignment.BASELINE)
						.addComponent(this.btnNoAtencion, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.btnSiAtencion, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(103, Short.MAX_VALUE))
		);
		this.Atencion.setLayout(gl_atencion);
	}


	@Override
	public void habilitarBotonCancelar() {
		this.btnCancelarEspere.setEnabled(true);
		this.btnSolicitarCliente.setEnabled(true);
		
	}

	@Override
	public void deshabilitarBotonCancelar() {
		this.btnCancelarEspere.setEnabled(false);
		this.btnSolicitarCliente.setEnabled(false);
		
	}

	@Override
	public void setNumeroPuesto(int numero) {
		this.numeroPuesto=numero;
		this.lblBoxSolicitarCliente.setText("Box "+this.numeroPuesto);
		this.setTitle("BOX - " + this.numeroPuesto);
		
	}

	@Override
	public void setControlador(ActionListener c) {
		this.btnSolicitarCliente.addActionListener(c);
		this.btnSolicitarCliente.setActionCommand(IVistaOperador.SOLICITARCLIENTE);
		
		this.btnCancelarEspere.addActionListener(c);
		this.btnCancelarEspere.setActionCommand(IVistaOperador.CANCELAR);
		
		this.btnAtendido.addActionListener(c);
		this.btnAtendido.setActionCommand(IVistaOperador.ATENDIDO);
		
		this.btnAusente.addActionListener(c);
		this.btnAusente.setActionCommand(IVistaOperador.AUSENTE);
		
		this.btnNoAusencia.addActionListener(c);
		this.btnNoAusencia.setActionCommand(IVistaOperador.NOAUSENTE);

		this.btnSiAusencia.addActionListener(c);
		this.btnSiAusencia.setActionCommand(IVistaOperador.SIAUSENTE);
		
		this.btnNoAtencion.addActionListener(c);
		this.btnNoAtencion.setActionCommand(IVistaOperador.NOATENDIDO);
		
		this.btnSiAtencion.addActionListener(c);
		this.btnSiAtencion.setActionCommand(IVistaOperador.SIATENDIDO);
	}

	@Override
	public void abrir() {
		setVisible(true);
		this.Atencion.setVisible(false);
		this.Ausencia.setVisible(false);
		this.ClienteActual.setVisible(false);
		this.SolicitarCliente.setVisible(true);
		this.Esperar.setVisible(false);
	}

	@Override
	public void solicitarClienteVentana() {
		this.SolicitarCliente.setVisible(true);
		this.Atencion.setVisible(false);
		this.Ausencia.setVisible(false);
		this.ClienteActual.setVisible(false);
		this.Esperar.setVisible(false);
		this.btnSolicitarCliente.setEnabled(true);
		
	}

	@Override
	public void esperandoVentana() {
		this.Esperar.setVisible(true);
		this.Atencion.setVisible(false);
		this.Ausencia.setVisible(false);
		this.ClienteActual.setVisible(false);
		this.SolicitarCliente.setVisible(false);
		
	}


	@Override
	public void clienteAsignadoVentana(String clienteActual) {
		this.ClienteActual.setVisible(true);
		this.Esperar.setVisible(false);
		this.Atencion.setVisible(false);
		this.Ausencia.setVisible(false);
		this.SolicitarCliente.setVisible(false);
		lblclienteActual.setText("Cliente: " + clienteActual);
		
		
	}


	@Override
	public void confirmacionAusenteVentana() {
		this.Ausencia.setVisible(true);
		this.ClienteActual.setVisible(false);
		this.Esperar.setVisible(false);
		this.Atencion.setVisible(false);
		this.SolicitarCliente.setVisible(false);
		
	}


	@Override
	public void confirmacionAtendidoVentana() {
		this.Atencion.setVisible(true);
		this.ClienteActual.setVisible(false);
		this.Esperar.setVisible(false);
		this.Ausencia.setVisible(false);
		this.SolicitarCliente.setVisible(false);
	}


	@Override
	public void setEstadoDeLaCola(int cant) {
		if (cant<=0) {
			this.lblEstadoCola.setText("Estado cola: Vacia");
		}
		else
			this.lblEstadoCola.setText("Estado cola: Clientes esperando");
	}




	@Override
	public void mostrarError(String e) {
		JOptionPane.showMessageDialog(null, "ERROR :"+e);
		int confirmado = JOptionPane.showConfirmDialog(null,"¿Desea Intentar nuevamente?");
			if (JOptionPane.OK_OPTION == confirmado) {
				this.abrir();		//vuelve a la pantalla de inicio
			}
			else {
				System.exit(0);
			}
		
	}
}