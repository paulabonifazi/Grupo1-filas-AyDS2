package server;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ReaderConfigJson implements IReaderConfig {
	
	public ReaderConfigJson() {
		super();
	}
	
		@Override
		public String getConfig() throws IOException{
			String archivoConfiguracion = ParametrosDeConexion.getArchivosLectura()[0]+".json";
	        String estrategia = "";
	        String grupos = "";
	        String rta=null;
	        try (Reader reader = new FileReader(archivoConfiguracion)) {
	            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
	            JsonObject estrategiaObject = jsonObject.getAsJsonObject("configuracion").getAsJsonObject("estrategia");
	            estrategia = estrategiaObject.get("tipo").getAsString();
	            JsonArray gruposArray = estrategiaObject.getAsJsonArray("grupos");
	            StringBuilder gruposBuilder = new StringBuilder();
	            for (int i = 0; i < gruposArray.size(); i++) {
	                gruposBuilder.append(gruposArray.get(i).getAsString());
	                if (i < gruposArray.size() - 1) {
	                    gruposBuilder.append("-");
	                }
	            }
	            grupos = gruposBuilder.toString();
	            rta=estrategia+"/"+grupos;
	        
	        // Devolver la estrategia y los grupos como un arreglo de Strings
	        // return new String[]{estrategia, grupos};
			return rta;
		}
	}
}
