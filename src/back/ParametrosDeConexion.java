package back;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ParametrosDeConexion {
	private int PuertoLibre; //este atributo lo completa el gestor de conexiones (no el main)
	private String contraseña;
	private boolean finalizar=false;

	public ParametrosDeConexion(String contraseña) {
		this.contraseña=contraseña;
	}
	
	public void agregarConexion(String id, IConexion conexion) {
		arregloConexiones.put(id, conexion);
	}
	
	// Obtener conexión
    public IConexion obtenerConexion(String id) { //devuelve null si no existe Y SOLAMENTE SE PUEDE CONSULTAR (get) xq sino habria que sincronizarlo.
        return arregloConexiones.get(id);
    }
    
 // Obtener todas las conexiones
    public Iterator<IConexion> obtenerTodasLasConexiones() {
        return this.arregloConexiones.values().iterator();
    }
    
    // Verificar si una conexión existe
    public boolean existeConexion(String id) {
        return arregloConexiones.containsKey(id);
    }

	public int getPuertoLibre() {
		return PuertoLibre;
	}

	public void setPuertoLibre(int puertoLibre) {
		PuertoLibre = puertoLibre;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public boolean isFinalizar() {
		return finalizar;
	}

	public void setFinalizar(boolean finalizar) {
		this.finalizar = finalizar;
	}
	
	
}
