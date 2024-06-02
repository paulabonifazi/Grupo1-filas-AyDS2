package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReaderClientTxt implements IReaderClient {

	@Override
	public Cliente getClient(String dnibuscado) throws IOException {
		String archivoClientes = "C:\\Users\\nicoa\\Downloads\\clientes.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(archivoClientes))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datosCliente = linea.split("-");
                if (datosCliente.length == 3) {
                    String dni = datosCliente[0];
                    int edad = Integer.parseInt(datosCliente[1]);
                    String grupo = datosCliente[2];
                    if (dni.equals(dnibuscado)) {
                        return new Cliente(dni, edad, grupo);
                    }
                }
            }
        }

        // Si no se encuentra, se crea el peor cliente
        return new Cliente(dnibuscado, 0, ParametrosDeConexion.getInstance().getPeorGrupo());
    }
}
