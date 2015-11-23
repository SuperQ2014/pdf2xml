package skyler.tao.pdf2xml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.itextpdf.text.pdf.PdfReader;

@SuppressWarnings("restriction")
public class GetMetadataPdf {

	// private static xmlOutputFile = "output.xml";

	private static final String xmlOutputFile = "output.xml";

	public static void main(String[] args) throws IOException,
			XMLStreamException {
		PdfReader reader = new PdfReader("pdf_metadata.pdf");

		//获取metadata信息
		Map<String, String> info = reader.getInfo();

		// create an XMLOutputFactory
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		// create XMLEventWriter
		XMLEventWriter eventWriter = outputFactory
				.createXMLEventWriter(new FileOutputStream(xmlOutputFile));
		// create an EventFactory
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		// create and write Start Tag
		StartDocument startDocument = eventFactory.createStartDocument();
		eventWriter.add(startDocument);

		// create config open tag
		StartElement configStartElement = eventFactory.createStartElement("",
				"", "metadata");
		eventWriter.add(configStartElement);
		eventWriter.add(end);

		for (Entry<String, String> entry : info.entrySet()) {

			System.out.println("Key = " + entry.getKey() + ", Value = "
					+ entry.getValue());
			createNode(eventWriter, entry.getKey(), entry.getValue());

		}

		eventWriter.add(eventFactory.createEndElement("", "", "metadata"));
		eventWriter.add(end);
		eventWriter.add(eventFactory.createEndDocument());
		eventWriter.close();
	}

	private static void createNode(XMLEventWriter eventWriter, String name,
			String value) throws XMLStreamException {

		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");
		// create Start node
		StartElement sElement = eventFactory.createStartElement("", "", name);
		eventWriter.add(tab);
		eventWriter.add(sElement);
		// create Content
		Characters characters = eventFactory.createCharacters(value);
		eventWriter.add(characters);
		// create End node
		EndElement eElement = eventFactory.createEndElement("", "", name);
		eventWriter.add(eElement);
		eventWriter.add(end);

	}
}
