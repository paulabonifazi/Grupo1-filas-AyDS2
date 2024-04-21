package estadistico;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class VentanaLogin extends JFrame implements IVistaLogin {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField IPTxt;
    private JTextField PuertoTxt;
    private JTextField ContraTxt;

    public VentanaLogin() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20)); // Añadido margen
        setContentPane(contentPane);
        contentPane.setLayout(new CardLayout(0, 0));

        JPanel Login = new JPanel();
        Login.setLayout(new GridLayout(0, 1, 0, 10)); // Espaciado vertical
        contentPane.add(Login, "name_115909304371000");

        // Panel para IP
        JPanel PanelIP = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Alineación izquierda
        Login.add(PanelIP);

        JLabel IpLb = new JLabel("IP:             ");
        PanelIP.add(IpLb);

        IPTxt = new JTextField();
        IPTxt.setPreferredSize(new Dimension(300, 20)); // Tamaño constante
        PanelIP.add(IPTxt);
        IPTxt.setColumns(30);

        // Panel para Puerto
        JPanel PanelPuerto = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Alineación izquierda
        Login.add(PanelPuerto);

        JLabel PuertoLb = new JLabel("Puerto:       ");
        PanelPuerto.add(PuertoLb);

        PuertoTxt = new JTextField();
        PuertoTxt.setPreferredSize(new Dimension(300, 20)); // Tamaño constante
        PanelPuerto.add(PuertoTxt);
        PuertoTxt.setColumns(30);

        // Panel para Contraseña
        JPanel PanelContra = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Alineación izquierda
        Login.add(PanelContra);

        JLabel ContraLb = new JLabel("Contraseña:");
        PanelContra.add(ContraLb);

        ContraTxt = new JTextField();
        ContraTxt.setPreferredSize(new Dimension(300, 20)); // Tamaño constante
        PanelContra.add(ContraTxt);
        ContraTxt.setColumns(30);

        // Panel para el botón Registrarse
        JPanel PanelReg = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Alineación central
        Login.add(PanelReg);

        JButton RegistroBtn = new JButton("Registrarse");
        RegistroBtn.setPreferredSize(new Dimension(120, 30)); // Tamaño fijo
        PanelReg.add(RegistroBtn);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VentanaLogin ventana = new VentanaLogin();
                ventana.setVisible(true);
            }
        });
    }
}