package server;

public class ConfiguradorDeServer {
	
	public static AbstractFactoryPersistencia definePersistencia(String tipo) {
		AbstractFactoryPersistencia rta=null;
		switch(tipo) {
			case ".txt":
				rta= new PersistenciaTxt();
				break;
			case ".json":
				rta=new PersistenciaJson();
				break;
			case ".xml":
				rta=new PersistenciaXml();
				break;
		}
		return rta;
	}
}
