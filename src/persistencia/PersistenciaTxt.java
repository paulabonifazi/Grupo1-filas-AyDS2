package persistencia;

import server.IWritterLog;

public class PersistenciaTxt implements AbstractFactoryPersistencia{

	public PersistenciaTxt() {
		super();
	}

	@Override
	public IReaderConfig getReaderConfig() {
		return new ReaderConfigTxt();
	}

	@Override
	public IReaderClient getReaderClient() {
		return new ReaderClientTxt();
	}

	@Override
	public IWritterLog getWritterLog() {
		// TODO Auto-generated method stub
		return new WritterLogTxt();
	}

}
