package server;
import org.json.*;

import java.io.*;
import java.util.ArrayList;


public class JSONParser extends AbstractParser{
	private final String JSON_FILE;
	
	
	public JSONParser(String jSON_FILE) {
		super();
		JSON_FILE = jSON_FILE;
	}


	@Override
	public ArrayList<ClientePOJO> cargarListaDeClientes(){
		ArrayList<ClientePOJO> clientes=null;
		try {
			JSONTokener tokenes=new JSONTokener(new FileInputStream(new File(JSON_FILE)));
			JSONArray arrayClientes= new JSONArray(tokenes);
			clientes = new ArrayList<ClientePOJO>();
			
			
			for (int i = 0; i < arrayClientes.length(); i++) {
	            JSONObject jsonObject = arrayClientes.getJSONObject(i);
	            String nombre = jsonObject.getString("nombre");
	            String fechaDeNacimiento = jsonObject.getString("fecha_de_nacimiento");
	            String membresia = jsonObject.getString("membresia");

	            ClientePOJO client = new ClientePOJO(nombre,fechaDeNacimiento,membresia);
	            clientes.add(client);
	        }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clientes;
	}	
	
}
