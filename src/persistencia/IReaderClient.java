package persistencia;

import java.io.IOException;

import server.Cliente;

public interface IReaderClient {
	public Cliente getClient(String dni) throws IOException;
}
