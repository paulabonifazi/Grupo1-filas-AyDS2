package server;

import java.io.IOException;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReaderConfigXml implements IReaderConfig {
	
	public ReaderConfigXml() {
		super();
	}
	
	@Override
    public String getConfig() throws IOException {
        String archivoConfiguracion = ParametrosDeConexion.getArchivosLectura()[0]+".xml";
        String estrategia = "";
        String grupos = "";
        try {
            File archivoXml = new File(archivoConfiguracion);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(archivoXml);
            doc.getDocumentElement().normalize();

            // Obtener el nodo raíz
            Element raiz = doc.getDocumentElement();

            // Obtener el nodo 'estrategia'
            NodeList estrategiaList = raiz.getElementsByTagName("estrategia");
            Node estrategiaNode = estrategiaList.item(0);
            if (estrategiaNode.getNodeType() == Node.ELEMENT_NODE) {
                Element estrategiaElement = (Element) estrategiaNode;
                estrategia = estrategiaElement.getAttribute("tipo");
                NodeList gruposList = estrategiaElement.getElementsByTagName("grupo");
                StringBuilder gruposBuilder = new StringBuilder();
                for (int i = 0; i < gruposList.getLength(); i++) {
                    Node grupoNode = gruposList.item(i);
                    if (grupoNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element grupoElement = (Element) grupoNode;
                        gruposBuilder.append(grupoElement.getTextContent());
                        if (i < gruposList.getLength() - 1) {
                            gruposBuilder.append("-");
                        }
                    }
                }
                grupos = gruposBuilder.toString();
            }
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

        // Devolver la estrategia y los grupos como un arreglo de Strings
        return estrategia + "/" + grupos;
    }

}
