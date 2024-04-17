package back;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class Conexiones {
	private ConcurrentHashMap<String, IConexion> arregloConexiones;

	public Conexiones() {
		this.arregloConexiones= new ConcurrentHashMap<String, IConexion>();
	}
	
	public void agregarConexion(String id, IConexion conexion) {
		arregloConexiones.put(id, conexion);
	}
	
	// Obtener conexión
    public IConexion obtenerConexion(String id) { //devuelve null si no existe
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
}
