package core.dao.impl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.proyecto.android.commons.core.dao.AbstractDao;

import core.domain.CentroDeReciclaje;
import core.exception.CreateDocumentBuilderException;
import core.exception.ParserException;
import core.exception.ServerFailedException;

public class CentroDeReciclajeDao extends AbstractDao<CentroDeReciclaje> {
	
	public List<CentroDeReciclaje> getAllCentrosDeReciclaje(){
		
		List<CentroDeReciclaje> centros = new ArrayList<CentroDeReciclaje>();
		StringBuilder url = new StringBuilder();
		Formatter formatter = new Formatter(url, Locale.US);
		formatter.format(configuration.getValue("webservice.getCentros"));
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(this.getExternalResource(
					url.toString(), 60000));
			Element root = dom.getDocumentElement();

			if (root == null){
				return centros;
			}
			NodeList items = root.getElementsByTagName("centro");

			for (int i = 0; i < items.getLength(); i++){
				centros.add(getCentroDeReciclaje((Element) items.item(i)));
			}

		} catch (ParserConfigurationException e) {
			throw new CreateDocumentBuilderException(e);
		} catch (SAXException e) {
			throw new ParserException(e);
		} catch (IOException e) {
			throw new ServerFailedException(e);
		}
		return centros;
	}
	
	private CentroDeReciclaje getCentroDeReciclaje(Element ei) {
		String id = "";
		String desc = "";
		String lat = "";
		String longitud = "";
		String direccion = "";
		List<Integer> ids = new ArrayList<Integer>();
		
		Node current = null;

		current = ei.getElementsByTagName("CentroReciclajeId").item(0).getChildNodes()
				.item(0);
		if (current != null){
			id = current.getNodeValue();
		}
		
		current = ei.getElementsByTagName("CentroReciclajeNombre").item(0).getChildNodes()
		.item(0);
		if (current != null){
			desc = current.getNodeValue();
		}

		current = ei.getElementsByTagName("CentroReciclajeLatitud").item(0).getChildNodes()
		.item(0);
		if (current != null){
			lat = current.getNodeValue();
		}
		
		current = ei.getElementsByTagName("CentroReciclajeLongitud").item(0).getChildNodes()
		.item(0);
		if (current != null){
			longitud = current.getNodeValue();
		}
		
		current = ei.getElementsByTagName("CentroReciclajeTipo").item(0).getChildNodes()
		.item(0);
		if (current != null){
			direccion = current.getNodeValue();
		}
		
		NodeList items = ei.getElementsByTagName("tiposproducto");
		
		if (items != null && items.getLength() != 0){
			items = items.item(0).getChildNodes();
			for (int i = 0; i < items.getLength(); i++){
				ids.add(getId((Element) items.item(i)));
			}
		}
		
		return new CentroDeReciclaje(desc, direccion, Integer.parseInt(id), lat, longitud, ids);
	}

	private Integer getId(Element item) {
		Node current =item.getChildNodes().item(0); 
		if (current != null){
			return Integer.parseInt(current.getNodeValue());
		}
		
		return 0;
	}
	
} 
