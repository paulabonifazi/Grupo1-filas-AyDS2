package server;

import java.io.IOException;

public interface IReaderConfig {
	public String getConfig() throws IOException; // el formato del return es: "estrategia/xx-xxx-xxxx-xxxx-xxxxx" No importa cual sea el archivo
}
