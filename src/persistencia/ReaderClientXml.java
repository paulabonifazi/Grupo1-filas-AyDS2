package persistencia;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import server.Cliente;
import server.*;

public class ReaderClientXml implements IReaderClient {

	@Override
	public Cliente getClient(String dnibuscado) throws IOException {
		String archivoClientes = ParametrosDeConexion.getArchivosLectura()[1] + ".xml";
        try {
            File archivoXml = new File(archivoClientes);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(archivoXml);
            doc.getDocumentElement().normalize();

            NodeList listaClientes = doc.getElementsByTagName("cliente");

            for (int temp = 0; temp < listaClientes.getLength(); temp++) {
                Node nodoCliente = listaClientes.item(temp);
                if (nodoCliente.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoCliente = (Element) nodoCliente;
                    String dni = elementoCliente.getAttribute("DNI");
                    if (dni.equals(dnibuscado)) {
                        // Parsear la fecha de nacimiento
                        String fechaNacimientoStr = elementoCliente.getAttribute("FechaNacimiento");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date fechaNacimiento = sdf.parse(fechaNacimientoStr);
                        
                        // Calcular la edad a partir de la fecha de nacimiento
                        Date fechaActual = new Date();
                        long diff = fechaActual.getTime() - fechaNacimiento.getTime();
                        long edadEnMilisegundos = 1000L * 60 * 60 * 24 * 365;
                        int edad = (int) (diff / edadEnMilisegundos);
                        
                        String grupo = elementoCliente.getAttribute("Grupo");
                        return new Cliente(dnibuscado, edad, grupo);
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | ParseException e) {
            e.printStackTrace();
        }
        // Si no se encuentra, se crea el peor cliente
        return new Cliente(dnibuscado, 0, ParametrosDeConexion.getInstance().getPeorGrupo());
    }
}
