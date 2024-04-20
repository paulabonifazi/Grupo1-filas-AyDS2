package Box;

import java.awt.event.ActionListener;

public interface IVistaOperador {
	
	static final String SOLICITARCLIENTE = "Solicitar Cliente";
	static final String CANCELAR = "Cancelar";
	static final String AUSENTE = "Ausente";
	static final String SIAUSENTE = "SiAusente";
	static final String NOAUSENTE = "NoAusente";
	static final String ATENDIDO = "Atendido";
	static final String SIATENDIDO = "SiAtendido";
	static final String NOATENDIDO = "NoAtendido";

	void setClienteActual(String clienteActual);
	void habilitarBotonAtendido();
	void deshabilitarBotonAtendido();
	void habilitarBotonLlamar();
	void deshabilitarBotonLlamar(); 
	void habilitarBotonAusente();
	void deshabilitarBotonAusente(); 
	public void habilitarBotonCancelar();
	public void deshabilitarBotonCancelar();
	void dispose();
	void setNumeroPuesto(int numero);
	void setControlador(ControladorVistaOperador c);
	void abrir();
	
}