package prueba;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TXTParser extends AbstractParser{

	
	
	
	public TXTParser(String string) throws FileNotFoundException {
		super(string);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected ArrayList<ClientePOJO> cargarDatos(FileInputStream archivoDatos){
		// TODO Auto-generated method stub
		return null;
	}

}
