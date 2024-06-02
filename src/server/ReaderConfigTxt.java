package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReaderConfigTxt implements IReaderConfig {
	
	public ReaderConfigTxt() {
		super();
	}
	
	@Override
    public String getConfig() throws IOException {
        String archivoConfiguracion = ParametrosDeConexion.getArchivosLectura()[0] + ".txt" ;
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoConfiguracion))) {
            String linea;
            int numeroLinea = 1;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea);
                contenido.append(numeroLinea == 1 ? "" : "-"); // No agrega el guión antes de la primera línea
                if (numeroLinea == 1) {
                    contenido.append("/");
                }
                numeroLinea++;
            }
        }
        return contenido.toString();
    }

}
