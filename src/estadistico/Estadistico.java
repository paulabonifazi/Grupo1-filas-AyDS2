package estadistico;

import javax.swing.SwingUtilities;

public class Estadistico {

	public Estadistico() {
		// TODO Auto-generated constructor stub
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