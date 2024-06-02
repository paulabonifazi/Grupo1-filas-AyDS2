package server;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
                        int edad = Integer.parseInt(elementoCliente.getAttribute("Edad"));
                        String grupo = elementoCliente.getAttribute("Grupo");
                        return new Cliente(dnibuscado, edad, grupo);
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        // Si no se encuentra, se crea el peor cliente
        return new Cliente(dnibuscado, 0, ParametrosDeConexion.getInstance().getPeorGrupo());
    }
}
