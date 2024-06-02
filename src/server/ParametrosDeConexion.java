package server;

import java.util.ArrayList;

public class ParametrosDeConexion {
	private static final String[] ESTRATEGIAS= {"Edad","Tiempo","Grupo"};
	private static final String[] EXTENSIONES = {".txt",".json",".xml"};
    private static final String[] ARCHIVOSLECTURA = {"C:\\Users\\nicoa\\Downloads\\configuracion", "C:\\Users\\nicoa\\Downloads\\clientes"};
    private static final String[] ARCHIVOSESCRITURA = {"C:\\Users\\nicoa\\Downloads\\log"};
	private static ParametrosDeConexion Singleton= new ParametrosDeConexion();
	private int PuertoLibre; 
	private String IP; 
	private String contraseña;
	private String estrategia;
	private ArrayList<String> grupos=new ArrayList<String>();

	private ParametrosDeConexion() {
	}

	public static ParametrosDeConexion getInstance() {
		return Singleton;
	}
	
	public int getPuertoLibre() { //lo utiliza el controlador del servidor (main) para mostrarlo por pantalla
		return PuertoLibre;
	}

	public void setPuertoLibre(int puertoLibre) { //lo utiliza el gestor de conexiones para setear el puerto que utiliza
		PuertoLibre = puertoLibre;
	}

	public String getContraseña() { //lo utiliza el controlador del servidor (main) para mostrarlo por pantalla
		return contraseña;
	}

	public void setContraseña(String contraseña) { //lo utiliza el controlador del servidor (main) para cambiar el codigo que debe ingresar en la comunicacion para permitirse la conexion (lo interpreta el gestor de conexiones)
		this.contraseña = contraseña;
	}


	public String getIP() {//lo utiliza el controlador del servidor (main) para mostrarlo por pantalla
		return IP;
	}

	public void setIP(String iP) {//lo utiliza el gestor de conexiones para setear la IP del servidor
		IP = iP;
	}
	
	public String getEstrategia() {
		return this.estrategia;
	}
	
	public int getGrupoindex(String name) {
		return this.grupos.indexOf(name);
	}
	
	public String getPeorGrupo() {
		if(grupos.size()>0 && this.estrategia.equals(ParametrosDeConexion.ESTRATEGIAS[2])) {
			return this.grupos.get(grupos.size()-1);
		}
		else
			return  null;
	}
	
	
	
	public String estado() {
		return this.contraseña+","+this.estrategia+","+estructuraGrupo();
	}
	
	
	private String estructuraGrupo() {
		String rta="";
		int i=0;
		while(i<this.grupos.size()) {
			rta+=grupos.get(i);
			i++;
			if(i<this.grupos.size()) {
				rta+="-";
			}
		}
		return rta;
	}
	
	public void parse(String parametros) {
		String[] elem=parametros.split(",");
		if(elem.length==3) {
			this.contraseña=elem[0];
			this.estrategia=elem[1];
			String[] auxG= elem[2].split("-");
			int i=0;
			this.grupos=new ArrayList<String>();
			while(i<auxG.length) {
				this.grupos.add(auxG[i]);
				i++;
			}
		}
	}
	
	public static String[] getExtensiones() {
		return EXTENSIONES;
	}

	public static String[] getArchivosLectura() {
		return ARCHIVOSLECTURA;
	}
	
	public static String[] getArchivosEscritura() {
		return ARCHIVOSESCRITURA;
	}
	
	public static String[] getEstrategias() {
		return ESTRATEGIAS;
	}
	

	public void defineEstrategia(String cadena) throws EstrategiaInexistente { //grupos= "x-xx-xxx-xxxx-xxxx"
		String[] aux=cadena.split("/");
			this.estrategia=aux[0];
			if(validaEstrategia(this.estrategia)) {
				if(aux.length>=2) {
					String[] auxG= aux[1].split("-");
					int i=0;
					this.grupos=new ArrayList<String>();
					while(i<auxG.length) {
						this.grupos.add(auxG[i]);
						i++;
					}
				}
			}
			else {
				throw new EstrategiaInexistente();
			}
		
	}

	private boolean validaEstrategia(String estrategia) {
		boolean rta=false;
		int i=0;
		String [] aux= ESTRATEGIAS;
		while(i<aux.length && !estrategia.equals(aux[i])) {
			i++;
		}
		if(i<aux.length) {
			rta=true;
		}
		return rta;
	}
}
