package estadistico;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame implements IVista {

	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal;
    private CardLayout cardLayout;
    private JTextField txtContrasenia;
    private JTextField txtIP;
    private JTextField txtPuerto;
    private ActionListener actionListener;

    public VentanaPrincipal() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250); 

    
        panelPrincipal = new JPanel();
        cardLayout = new CardLayout();
        panelPrincipal.setLayout(cardLayout);

      
        JPanel panelLogin = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        txtContrasenia = new JTextField(20);
        panelLogin.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        panelLogin.add(txtContrasenia, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        txtIP = new JTextField(20);
        panelLogin.add(new JLabel("IP:"), gbc);
        gbc.gridx = 1;
        panelLogin.add(txtIP, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        txtPuerto = new JTextField(20);
        panelLogin.add(new JLabel("Puerto:"), gbc);
        gbc.gridx = 1;
        panelLogin.add(txtPuerto, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnIngresar = new JButton("Ingresar");
        panelLogin.add(btnIngresar, gbc);

        
        JPanel panelExito = new JPanel();
        panelExito.add(new JLabel("Inicio de sesión exitoso"));

        
        JPanel panelError = new JPanel();
        panelError.add(new JLabel("Inicio de sesión fallido"));

        
        panelPrincipal.add(panelLogin, "Login");
        panelPrincipal.add(panelExito, "Exito");
        panelPrincipal.add(panelError, "Error");

        
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actionListener != null) {
                    actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Ingresar"));
                }
            }
        });

        
        add(panelPrincipal, BorderLayout.CENTER);
    }

    @Override
    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    public void entraSV() {
        cardLayout.show(panelPrincipal, "Exito");
    }

    @Override
    public void salirSV() {
        cardLayout.show(panelPrincipal, "Login");
    }

    @Override
    public void errorIngreso(String motivo) {
        JLabel lblError = new JLabel("Error de inicio de sesión: " + motivo);
        lblError.setForeground(Color.RED); 
        panelPrincipal.add(lblError, "Error"); 
        cardLayout.show(panelPrincipal, "Error");
    }

    @Override
    public void actualiza(int tamCola, int cantAtendidos, long TPromEsp, long TPromAtn, long TPromLlam) {
        JOptionPane.showMessageDialog(this,
                "Tamaño de la cola: " + tamCola + "\n" +
                "Cantidad atendidos: " + cantAtendidos + "\n" +
                "Tiempo promedio de espera: " + TPromEsp + " ms\n" +
                "Tiempo promedio de atención: " + TPromAtn + " ms\n" +
                "Tiempo promedio de llamada: " + TPromLlam + " ms",
                "Actualización de datos", JOptionPane.INFORMATION_MESSAGE);
    }
    

    @Override
    public String getContrasenia() {
        return txtContrasenia.getText();
    }

    @Override
    public String getIP() {
        return txtIP.getText();
    }

    @Override
    public String getPuerto() {
        return txtPuerto.getText();
    }


}