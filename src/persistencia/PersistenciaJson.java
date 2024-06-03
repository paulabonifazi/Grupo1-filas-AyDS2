package persistencia;

import server.IWritterLog;

public class PersistenciaJson implements AbstractFactoryPersistencia {
	
	public PersistenciaJson() {
		super();
	}

	@Override
	public IReaderConfig getReaderConfig() {
		return new ReaderConfigJson();
	}

	@Override
	public IReaderClient getReaderClient() {
		return new ReaderClientJson();
	}

	@Override
	public IWritterLog getWritterLog() {
		return new WritterLogJson();
	}

}
