package server;

import java.io.IOException;

public interface IReaderClient {
	public Cliente getClient(String dni) throws IOException;
}
