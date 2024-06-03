package persistencia;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.Cliente;
import server.*;

public class ReaderClientJson implements IReaderClient {

	@Override
	public Cliente getClient(String dnibuscado) throws IOException {
		// Leer el archivo JSON
        try (Reader reader = new FileReader(ParametrosDeConexion.getArchivosLectura()[1] + ".json")) {
            JsonObject rootObject = JsonParser.parseReader(reader).getAsJsonObject();

            // Obtener el arreglo "clientes"
            JsonArray clientesArray = rootObject.getAsJsonArray("clientes");

            // Recorrer los clientes y buscar por DNI
            for (int i = 0; i < clientesArray.size(); i++) {
                JsonObject clienteObject = clientesArray.get(i).getAsJsonObject();
                String dni = clienteObject.get("DNI").getAsString();
                if (dni.equals(dnibuscado)) {
                    String fechaNacimientoString = clienteObject.get("FechaNacimiento").getAsString();
                    java.sql.Date fechaNacimientoSql = java.sql.Date.valueOf(fechaNacimientoString);
                    java.util.Date fechaNacimiento = new java.util.Date(fechaNacimientoSql.getTime());
                    int edad = calcularEdad(fechaNacimiento);
                    String grupo = clienteObject.get("Grupo").getAsString();
                    return new Cliente(dnibuscado, edad, grupo);
                }
            }
        }
        // Si no se encuentra, se crea el peor cliente
        return new Cliente(dnibuscado, 0, ParametrosDeConexion.getInstance().getPeorGrupo());
    }

    private int calcularEdad(java.util.Date fechaNacimiento) {
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
