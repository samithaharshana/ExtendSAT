package satanalyzer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.driver.v1.Session;
import org.neo4j.graphdb.GraphDatabaseService;
import org.openide.util.Exceptions;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.project.traceability.common.PropertyFile;

import it.uniroma1.dis.wsngroup.gexf4j.core.EdgeType;
import it.uniroma1.dis.wsngroup.gexf4j.core.Gexf;
import it.uniroma1.dis.wsngroup.gexf4j.core.Mode;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.GexfImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.StaxGraphWriter;



public class GraphFileGenerator {

	 public void generateGraphFile(Session session) {
	        System.out.println("Gexf graph " + graphDb.toString());
	        this.graphDb = graphDb;
	        Gexf gexf = new GexfImpl();
	        gexf.setVisualization(true);
	        graph = gexf.getGraph();
	        graph.setDefaultEdgeType(EdgeType.DIRECTED).setMode(Mode.DYNAMIC);

	        //Transaction tx = graphDb.beginTx();
	        engine = new ExecutionEngine(graphDb);

	        addNodes();
	        addEdges();

	        StaxGraphWriter graphWriter = new StaxGraphWriter();
	        File f = new File(PropertyFile.getGeneratedGexfFilePath());

	        Writer out;
	        try {
	            out = new FileWriter(f, false);
	            graphWriter.writeToStream(gexf, out, "UTF-8");
	            System.out.println(f.getAbsolutePath());

	            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	            Document doc = (Document) dBuilder.parse(f);
	            doc.getDocumentElement().normalize();

	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            Transformer transformer = transformerFactory.newTransformer();

	            DOMSource source = new DOMSource(doc);
	            StreamResult stream = new StreamResult(new File(PropertyFile.getGeneratedGexfFilePath()).getPath());
	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	            transformer.transform(source, stream);

	        } catch (ParserConfigurationException | SAXException | IOException ex) {
	            Exceptions.printStackTrace(ex);
	        } catch (TransformerConfigurationException ex) {
	            Exceptions.printStackTrace(ex);
	        } catch (TransformerException ex) {
	            Exceptions.printStackTrace(ex);
	        }

	    }
	
}
