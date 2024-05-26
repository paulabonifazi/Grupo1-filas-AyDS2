package prueba;


import org.json.*;

import java.io.*;
import java.util.ArrayList;


public class JSONParser extends AbstractParser{

	
	public JSONParser(String string) throws FileNotFoundException {
		super(string);
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
