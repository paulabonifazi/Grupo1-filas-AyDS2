package estadistico;

public class CalculosEstadisticos {

	public CalculosEstadisticos() {
	}
	
    public static double calcularPromedio(Long[] array) {
	    if (array == null || array.length == 0) {
	        throw new IllegalArgumentException("El array no puede ser nulo o vacío.");
	    }
	    
	    long suma = 0;
	    for (long num : array) {
	        suma += num;
	    }
	    
	    return (double) suma / array.length;
    }
}
