package Box;

import java.awt.event.ActionListener;

public interface IVistaOperador {
	
	static final String SOLICITARCLIENTE = "Solicitar cliente";
	static final String CANCELAR = "Cancelar";
	static final String AUSENTE = "Ausente";
	static final String SIAUSENTE = "Siausente";
	static final String NOAUSENTE = "Noausente";
	static final String ATENDIDO = "Atendido";
	static final String SIATENDIDO = "Siatendido";
	static final String NOATENDIDO = "Noatendido";
	
	public void abrir();
	public void setNumeroPuesto(int numero);
	public void setControlador(ActionListener c); //Ejecutan 1 vez

	public void solicitarClienteVentana(); // xxxVentana -> cambiar a la ventana xxx
	public void esperandoVentana();
	public void deshabilitarBotonCancelar();
	public void habilitarBotonCancelar();
	public void clienteAsignadoVentana(String clienteActual);
	public void confirmacionAusenteVentana();
	public void confirmacionAtendidoVentana();
	public void setEstadoDeLaCola(int cant);
	public void mostrarError(String e); //Ejecutan n veces

	

	
}