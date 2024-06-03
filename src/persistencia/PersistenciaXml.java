package persistencia;

import server.IWritterLog;

public class PersistenciaXml implements AbstractFactoryPersistencia {
	
	
	public PersistenciaXml() {
		super();
	}

	@Override
	public IReaderConfig getReaderConfig() {
		return new ReaderConfigXml();
	}

	@Override
	public IReaderClient getReaderClient() {
		return new ReaderClientXml();
	}

	@Override
	public IWritterLog getWritterLog() {
		return new WritterLogXml();
	}

}
