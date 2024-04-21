package estadistico;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class Ventana extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField IPTxt;
    private JTextField PuertoTxt;
    private JTextField ContraTxt;

    public Ventana() {
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
        
        JPanel Estadisticas = new JPanel();
        contentPane.add(Estadisticas, "name_120102293624800");
        Estadisticas.setLayout(new BorderLayout(0, 0));
        
        JPanel PanelActualizar = new JPanel();
        Estadisticas.add(PanelActualizar, BorderLayout.SOUTH);
        
        JButton ActualizaBtn = new JButton("Actualizar");
        PanelActualizar.add(ActualizaBtn);
        
        JPanel PanelEstadisticos = new JPanel();
        Estadisticas.add(PanelEstadisticos, BorderLayout.CENTER);
        PanelEstadisticos.setLayout(new GridLayout(0, 2, 0, 0));
        
        JPanel panelTiempos = new JPanel();
        PanelEstadisticos.add(panelTiempos);
        panelTiempos.setLayout(new GridLayout(0, 1, 0, 0));
        
        JPanel panel_2 = new JPanel();
        panelTiempos.add(panel_2);
        panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel lblNewLabel = new JLabel("T. Espera Prom:            ");
        panel_2.add(lblNewLabel);
        
        JTextArea textArea = new JTextArea();
        textArea.setColumns(11);
        panel_2.add(textArea);
        
        JPanel panel_3 = new JPanel();
        panelTiempos.add(panel_3);
        
        JLabel lblNewLabel_1 = new JLabel("T. Solicitud Prom:          ");
        panel_3.add(lblNewLabel_1);
        
        JTextArea textArea_1 = new JTextArea();
        textArea_1.setColumns(11);
        panel_3.add(textArea_1);
        
        JPanel panel_4 = new JPanel();
        panelTiempos.add(panel_4);
        
        JLabel lblNewLabel_2 = new JLabel("T. Atencion Promedio:    ");
        panel_4.add(lblNewLabel_2);
        
        JTextArea textArea_2 = new JTextArea();
        textArea_2.setColumns(11);
        panel_4.add(textArea_2);
        
        JPanel PanelCant = new JPanel();
        PanelEstadisticos.add(PanelCant);
        PanelCant.setLayout(new GridLayout(4, 1, 0, 0));
        
        JPanel PanelCantVacio = new JPanel();
        PanelCant.add(PanelCantVacio);
        
        JPanel PanelCantAtenciones = new JPanel();
        PanelCant.add(PanelCantAtenciones);
        
        JLabel CantAtencionesLbl = new JLabel("Cant. Atenciones:          ");
        PanelCantAtenciones.add(CantAtencionesLbl);
        
        JTextArea textArea_3 = new JTextArea();
        textArea_3.setColumns(11);
        PanelCantAtenciones.add(textArea_3);
        
        JPanel panel_8 = new JPanel();
        PanelCant.add(panel_8);
        
        JLabel lblNewLabel_5 = new JLabel("Cant. clientes en espera:");
        panel_8.add(lblNewLabel_5);
        
        JTextArea textArea_4 = new JTextArea();
        textArea_4.setColumns(11);
        panel_8.add(textArea_4);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Ventana ventana = new Ventana();
                ventana.setVisible(true);
            }
        });
    }
}