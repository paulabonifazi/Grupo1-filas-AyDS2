package estadistico;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements ActionListener {

    private IVista vista;
    private GestorConexionEstadistico modelo;

    public Controlador(IVista vista, GestorConexionEstadistico modelo) {
        this.vista = vista;
        this.modelo = modelo;

        // Configurar el action listener en la vista
        this.vista.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Ingresar":
                String contra = vista.getContrasenia();
                String ip = vista.getIP();
                String puerto = vista.getPuerto();
               
                
                if (true) {
                    vista.entraSV(); 
                } else {
                    vista.errorIngreso("Credenciales incorrectas"); 
                }
                break;
        }
    }

}