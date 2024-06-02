package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReaderClientTxt implements IReaderClient {

	 @Override
	    public Cliente getClient(String dnibuscado) throws IOException {
	        String archivoClientes = "C:\\Users\\nicoa\\Downloads\\clientes.txt";

	        try (BufferedReader reader = new BufferedReader(new FileReader(archivoClientes))) {
	            String linea;
	            while ((linea = reader.readLine()) != null) {
	                String[] datosCliente = linea.split("-");
	                if (datosCliente.length == 3) {
	                    String dni = datosCliente[0];
	                    Date fechaNacimiento = null;
	                    try {
	                    	fechaNacimiento = new Date(new SimpleDateFormat("dd/MM/yyyy").parse(datosCliente[1]).getTime());
	                    } catch (ParseException e) {
	                        // Error en el formato de fecha
	                        e.printStackTrace();
	                    }
	                    String grupo = datosCliente[2];
	                    if (dni.equals(dnibuscado)) {
	                        int edad = calcularEdad(fechaNacimiento);
	                        return new Cliente(dni, edad, grupo);
	                    }
	                }
	            }
	        }

	        // Si no se encuentra, se crea el peor cliente
	        return new Cliente(dnibuscado, 0, ParametrosDeConexion.getInstance().getPeorGrupo());
	    }

	    private int calcularEdad(Date fechaNacimiento) {
	        Calendar fechaNac = Calendar.getInstance();
	        fechaNac.setTime(fechaNacimiento);

	        Calendar fechaActual = Calendar.getInstance();

	        int edad = fechaActual.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);

	        if (fechaActual.get(Calendar.MONTH) < fechaNac.get(Calendar.MONTH) ||
	                (fechaActual.get(Calendar.MONTH) == fechaNac.get(Calendar.MONTH) &&
	                        fechaActual.get(Calendar.DAY_OF_MONTH) < fechaNac.get(Calendar.DAY_OF_MONTH))) {
	            edad--;
	        }

	        return edad;
	    }
}
