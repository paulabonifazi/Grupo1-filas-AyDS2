package server;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ReaderClientJson implements IReaderClient {

	@Override
	public Cliente getClient(String dnibuscado) throws IOException {
		// Leer el archivo JSON
        try (Reader reader = new FileReader(ParametrosDeConexion.getArchivosLectura()[1]+".json")) {
            JsonObject rootObject = JsonParser.parseReader(reader).getAsJsonObject();

            // Obtener el arreglo "clientes"
            JsonArray clientesArray = rootObject.getAsJsonArray("clientes");

            // Recorrer los clientes y buscar por DNI
            for (int i = 0; i < clientesArray.size(); i++) {
                JsonObject clienteObject = clientesArray.get(i).getAsJsonObject();
                String dni = clienteObject.get("DNI").getAsString();
                if (dni.equals(dnibuscado)) {
                    int edad = clienteObject.get("Edad").getAsInt();
                    String grupo = clienteObject.get("Grupo").getAsString();
                    return new Cliente(dnibuscado, edad, grupo);
                }
            }
        }
        //si no se encuentra se crea el peor cliente
        return new Cliente(dnibuscado,0,ParametrosDeConexion.getInstance().getPeorGrupo());
    }
}
