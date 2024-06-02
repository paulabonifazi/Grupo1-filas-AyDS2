package server;

import java.io.IOException;

public class Prueba {
	 public static void main(String[] args) {
		 try {
	            // Crear instancia del lector de clientes de texto
	            IReaderClient readerClient = new ReaderClientTxt();

	            // Obtener un cliente de prueba
	            String dnibuscado = "44667826";
	            Cliente cliente = readerClient.getClient(dnibuscado);

	            // Mostrar el cliente por pantalla}
	            System.out.println("DNI: " + cliente.getDni());
	            System.out.println("Edad: " + cliente.getEdad());
	            System.out.println("Grupo: " + cliente.getGrupo());
	        } catch (IOException e) {
	            System.err.println("Error al leer el archivo de clientes: " + e.getMessage());
	        }
	    }

}
