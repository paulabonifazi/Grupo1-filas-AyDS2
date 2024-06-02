package interfaces;

import java.io.IOException;

public interface IRegistro {
	public void registrar(String DNI)  throws InterruptedException, IOException; //la idea es que desde el totem se llame a este metodo y se le devuelva un resultado con string y que a partir de este, cambie la ventana
}
