package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WritterLogTxt implements IWritterLog {

	@Override
    public void registraevento(String evento) throws IOException {
        String archivoLog = ParametrosDeConexion.getArchivosEscritura()[0] + ".txt";
        
        // Escribir el evento en el archivo de texto
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoLog, true))) {
            writer.write(evento);
            writer.newLine();
        }
    }
}
