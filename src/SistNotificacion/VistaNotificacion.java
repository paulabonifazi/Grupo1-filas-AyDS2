package SistNotificacion;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VistaNotificacion extends JFrame implements IVistaMonitor {
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public VistaNotificacion() {
    	setTitle("Tabla DNI y Box");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el modelo de la tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("DNI");
        modeloTabla.addColumn("Box");

        // Crear la tabla con el modelo
        tabla = new JTable(modeloTabla);

        // Crear el panel con GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Estirar en ambas direcciones

        // Agregar la tabla al panel
        gbc.weightx = 1.0; // Peso horizontal
        gbc.weighty = 1.0; // Peso vertical
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(tabla.getTableHeader(), gbc);

        gbc.gridy = 1;
        panel.add(tabla, gbc);

        // Agregar el panel al contenido de la ventana
        getContentPane().add(panel);

        // Inicializar la tabla con 6 filas
        for (int i = 0; i < 6; i++) {
            modeloTabla.addRow(new Object[]{"", ""});
        }

        pack(); // Ajustar el tamaño de la ventana automáticamente
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
    }
    // Método para cambiar el contenido de una fila
    public void cambiarContenidoFila(int fila, String dni, String box) {
        modeloTabla.setValueAt(dni, fila, 0);
        modeloTabla.setValueAt(box, fila, 1);
    }

	@Override
	public void abrir() {
		this.setVisible(true);
		
	}
}
