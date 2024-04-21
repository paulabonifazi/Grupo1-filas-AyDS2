package estadistico;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorLogin implements ActionListener {

    private IVistaLogin vista;
    private GestorConexionEstadistico modelo;

    public ControladorLogin(IVistaLogin vista, GestorConexionEstadistico modelo) {
        this.vista = vista;
        this.modelo = modelo;
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