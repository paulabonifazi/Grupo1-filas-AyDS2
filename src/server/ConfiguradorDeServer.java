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
	public static IStrategy defineEstrategia() {
		IStrategy rta=null;
		switch(ParametrosDeConexion.getInstance().getEstrategia()) {
			case "Edad":
				rta= new EstrategiaEdad();
				break;
			case "Tiempo":
				rta= new EstrategiaTiempo();
				break;
			case "Grupo":
				rta= new EstrategiaGrupo();
				break;
			default: 
				rta= new EstrategiaTiempo();
				break;
		}
		return rta;
	}
}
