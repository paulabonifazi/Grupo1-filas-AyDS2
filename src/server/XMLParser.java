package prueba;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class XMLParser extends AbstractParser{

	public XMLParser(String string) throws FileNotFoundException {
		super(string);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ArrayList<ClientePOJO> cargarDatos(FileInputStream archivoDatos){
		ArrayList<ClientePOJO> clientes=null;
		JSONTokener tokenes=new JSONTokener(archivoDatos);
		JSONArray arrayClientes= new JSONArray(tokenes);
		clientes = new ArrayList<ClientePOJO>();

		for (int i = 0; i < arrayClientes.length(); i++) {
			JSONObject jsonObject = arrayClientes.getJSONObject(i);
	        String nombre = jsonObject.getString("nombre");
	        String fechaDeNacimiento = jsonObject.getString("fecha_de_nacimiento");
	        String membresia = jsonObject.getString("membresia"); //Si se agregan mas campos en el JSON agregar un CAMPO=jsonObject.getString(NOMBRE DEL CAMPO)

	        ClientePOJO client = new ClientePOJO(nombre,fechaDeNacimiento,membresia);
	        clientes.add(client);
	    }
		
		return clientes;
	}

}
