package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class WritterLogJson implements IWritterLog {

    @Override
    public void registraevento(String evento) throws IOException {
        JsonObject nuevoRegistro = new JsonObject();
        String[] elem = evento.split("-");
        nuevoRegistro.addProperty("tipo", elem[0]);
        nuevoRegistro.addProperty("dni", elem[1]);
        nuevoRegistro.addProperty("hora", elem[2]);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Leer el contenido actual del archivo
        JsonArray registros;
        try (BufferedReader reader = new BufferedReader(new FileReader(ParametrosDeConexion.getArchivosEscritura()[0]+".json"))) {
            registros = gson.fromJson(reader, JsonArray.class);
        } catch (FileNotFoundException e) {
            registros = new JsonArray();
        }

        // Agregar el nuevo registro al arreglo
        registros.add(nuevoRegistro);

        // Escribir el arreglo actualizado de registros en el archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ParametrosDeConexion.getArchivosEscritura()[0]+".json"))) {
            writer.write(gson.toJson(registros));
        }
    }
}
