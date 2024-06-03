package persistencia;

import server.IWritterLog;

public interface AbstractFactoryPersistencia {
	public IReaderConfig getReaderConfig(); //lo tiene el server cuando arranca
	public IReaderClient getReaderClient(); //lo tiene cada gestordeTotem
	public IWritterLog getWritterLog(); //lo tiene un thread que persiste
}
