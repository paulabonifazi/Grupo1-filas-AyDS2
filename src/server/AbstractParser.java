package prueba;

import java.io.*;
import java.util.ArrayList;

public abstract class AbstractParser {
	private final String FILE_DATA;
	private FileInputStream archivo;
	
	public AbstractParser(String string) throws FileNotFoundException {
		this.FILE_DATA=string;

		archivo=new FileInputStream(new File(FILE_DATA));

	}
	
	public ArrayList<ClientePOJO> cargarListaDeClientes(){
		return cargarDatos(archivo);
		
	}
	
	protected abstract ArrayList<ClientePOJO> cargarDatos(FileInputStream archivoDatos);
	
	
	
}
