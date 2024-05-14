package SistNotificacion;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;

public class VistaNotificacion extends JFrame implements IVistaMonitor {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JLabel dniLabel;
    private JLabel boxLabel;

    public VistaNotificacion() {
        setTitle("TVLlamado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Crear el modelo de la tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("DNI");
        modeloTabla.addColumn("Box");

        // Crear la tabla con el modelo
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Tahoma", Font.PLAIN, 60));

        // Crear un panel para la tabla
        JPanel panel = new JPanel(new BorderLayout());

        // Panel para los labels DNI y BOX
        JPanel labelPanel = new JPanel(new GridLayout(1, 2));
        labelPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        dniLabel = new JLabel("DNI", SwingConstants.CENTER);
        dniLabel.setOpaque(true);
        dniLabel.setBackground(Color.CYAN);
        boxLabel = new JLabel("BOX", SwingConstants.CENTER);
        boxLabel.setOpaque(true);
        boxLabel.setBackground(Color.CYAN);

        labelPanel.add(dniLabel);
        labelPanel.add(boxLabel);

        panel.add(labelPanel, BorderLayout.NORTH);
        panel.add(tabla, BorderLayout.CENTER);

        getContentPane().add(panel, BorderLayout.CENTER);

        // Inicializar la tabla con 6 filas
        for (int i = 0; i < 6; i++) {
            modeloTabla.addRow(new Object[]{"", ""});
        }

        // Configurar el renderizador para la primera fila
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row == 0) { // Solo la primera fila
                    c.setBackground(Color.CYAN); // Color celeste
                } else {
                    c.setBackground(Color.WHITE); // Color blanco para otras filas
                }
                return c;
            }
        });

        // Ajustar el tamaño de las columnas y filas cuando se redimensiona la ventana
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ajustarTamanoTabla();
                ajustarTamanoLabels();
            }
        });

        pack(); // Ajustar el tamaño de la ventana automáticamente
        ajustarTamanoVentana(); // Ajustar la ventana para que encaje exactamente con la tabla
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
    }

 // Método para ajustar el tamaño de las columnas, filas y fuente de los elementos de la tabla
    private void ajustarTamanoTabla() {
        int totalWidth = tabla.getWidth();
        int totalHeight = tabla.getHeight();
        int numColumns = tabla.getColumnCount();
        int numRows = tabla.getRowCount();

        // Obtener el tamaño vertical ocupado por los JLabel de Box y DNI
        int labelHeight = dniLabel.getHeight();

        // Calcular el espacio vertical disponible para las filas
        int availableHeight = totalHeight - labelHeight;

        // Calcular el alto preferido para cada fila
        int rowHeight = Math.max(availableHeight / numRows, 1); // Asegurarse de que la altura no sea menor que 1

        // Asignar el alto calculado a cada fila
        for (int i = 0; i < numRows; i++) {
            tabla.setRowHeight(i, rowHeight);
        }

        // Calcular el espacio vertical restante
        int remainingHeight = availableHeight - (rowHeight * numRows);

        // Ajustar la altura de la última fila si es necesario
        int lastRowHeight = Math.max(rowHeight + remainingHeight, 1); // Asegurarse de que la altura no sea menor que 1
        tabla.setRowHeight(numRows - 1, lastRowHeight);

        // Calcular el tamaño de fuente para ajustarse al ancho de la tabla
        float fontSize = totalWidth / 20f; // Puedes ajustar el factor según tus preferencias

        // Establecer el tamaño de la fuente para cada celda
        for (int i = 0; i < numColumns; i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(new FontTableCellRenderer(new Font("Tahoma", Font.PLAIN, (int) fontSize)));
        }
    }

    // Clase interna para renderizar las celdas con un tamaño de fuente específico
    class FontTableCellRenderer extends DefaultTableCellRenderer {
        private Font font;

        public FontTableCellRenderer(Font font) {
            this.font = font;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            c.setFont(font);
            return c;
        }
    }

    // Método para ajustar el tamaño de los labels
    private void ajustarTamanoLabels() {
        int totalWidth = tabla.getWidth();
        dniLabel.setFont(dniLabel.getFont().deriveFont(Font.BOLD, totalWidth / 20f));
        boxLabel.setFont(boxLabel.getFont().deriveFont(Font.BOLD, totalWidth / 20f));
    }

    // Método para ajustar el tamaño de la ventana para que se ajuste exactamente a la tabla
    private void ajustarTamanoVentana() {
        int totalWidth = tabla.getPreferredSize().width;
        int totalHeight = tabla.getPreferredSize().height + dniLabel.getPreferredSize().height;
        setPreferredSize(new Dimension(totalWidth, totalHeight));
        pack(); // Ajustar el tamaño de la ventana automáticamente
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