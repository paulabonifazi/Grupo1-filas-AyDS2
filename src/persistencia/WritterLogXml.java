package persistencia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import server.IWritterLog;
import server.*;

public class WritterLogXml implements IWritterLog{

	@Override
    public void registraevento(String evento) throws IOException {
		String filePath = ParametrosDeConexion.getArchivosEscritura()[0]+".xml";
	    boolean archivoExiste = existeArchivo(filePath);

	    if (archivoExiste) {
	        try {
	            // Leer y parsear el archivo existente
	            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	            Document doc = dBuilder.parse(new File(filePath));

	            // Crear un nuevo elemento registro
	            String[] elem = evento.split("-");
	            Element nuevoRegistro = doc.createElement("registro");
	            nuevoRegistro.setAttribute("tipo", elem[0]);
	            nuevoRegistro.setAttribute("dni", elem[1]);
	            nuevoRegistro.setAttribute("hora", elem[2]);

	            // Añadir el nuevo registro al nodo raíz
	            Node root = doc.getDocumentElement();
	            root.appendChild(nuevoRegistro);
	            root.appendChild(doc.createTextNode("\n"));
	         // Guardar los cambios en el archivo
	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            Transformer transformer = transformerFactory.newTransformer();
	            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");
	            DOMSource source = new DOMSource(doc);
	            StreamResult result = new StreamResult(new File(filePath));
	            transformer.transform(source, result);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	    	// Si el archivo no existe, creamos uno nuevo y escribimos el primer registro
	        try (Writer writer = new FileWriter(filePath)) {
	            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
	            XMLStreamWriter xmlWriter = outputFactory.createXMLStreamWriter(writer);

	            xmlWriter.writeStartDocument();
	            xmlWriter.writeCharacters(System.lineSeparator()); // Agregar salto de línea
	            xmlWriter.writeStartElement("registros");
	            xmlWriter.writeCharacters(System.lineSeparator()); // Agregar salto de línea

	            // Escribir un nuevo registro en el archivo XML
	            String[] elem = evento.split("-");
	            xmlWriter.writeStartElement("registro");
	            xmlWriter.writeAttribute("tipo", elem[0]);
	            xmlWriter.writeAttribute("dni", elem[1]);
	            xmlWriter.writeAttribute("hora", elem[2]);
	            xmlWriter.writeEndElement();
	            xmlWriter.writeCharacters(System.lineSeparator()); // Agregar salto de línea

	            // Escribir la etiqueta de cierre </registros>
	            xmlWriter.writeEndElement();
	            xmlWriter.writeCharacters(System.lineSeparator()); // Agregar salto de línea
	            xmlWriter.writeEndDocument();

	            xmlWriter.flush();
	        } catch (XMLStreamException e) {
	            e.printStackTrace();
	        }
	    }
	}


    private static boolean existeArchivo(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
}
