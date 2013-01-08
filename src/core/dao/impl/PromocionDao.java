package core.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.proyecto.android.commons.core.dao.AbstractDao;

import core.domain.Promocion;
import core.exception.CreateDocumentBuilderException;
import core.exception.ParserException;
import core.exception.ServerFailedException;

public class PromocionDao extends AbstractDao {

	public List<Promocion> getAllPromociones(){
		StringBuilder url = new StringBuilder();
		Formatter formatter = new Formatter(url, Locale.FRENCH);
		formatter.format(configuration.getValue("webservice.getAllPromociones"));
		List<Promocion> promociones = new ArrayList<Promocion>();
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream ist = this.getExternalResource(url.toString(), 60000);
			Reader reader = new InputStreamReader(ist,"UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			
			
			Document dom = builder.parse(is);
			Element root = dom.getDocumentElement();

			if (root == null){
				return promociones;
			}
			NodeList items = root.getElementsByTagName("promocion");

			for (int i = 0; i < items.getLength(); i++){
				promociones.add(getPromocion((Element) items.item(i)));
			}

		} catch (ParserConfigurationException e) {
			throw new CreateDocumentBuilderException(e);
		} catch (SAXException e) {
			throw new ParserException(e);
		} catch (IOException e) {
			throw new ServerFailedException(e);
		}
		
		return promociones;
	}
	
	private Promocion getPromocion(Element ei) {
		String id = "";
		String titulo = "";
		String desc = "";
		String desde = "";
		String hasta = "";
		String puntos = "";
		String img = ""; 
		
		
		Node current = null;

		current = ei.getElementsByTagName("ComercianteLogoURL").item(0).getChildNodes()
				.item(0);
		if (current != null){
			img = current.getNodeValue();
		}
		
		current = ei.getElementsByTagName("PromocionId").item(0).getChildNodes()
				.item(0);
		if (current != null){
			id = current.getNodeValue();
		}
		
		current = ei.getElementsByTagName("PromocionTitulo").item(0).getChildNodes()
		.item(0);
		if (current != null){
			titulo = current.getNodeValue();
		}

		current = ei.getElementsByTagName("PromocionDescripcion").item(0).getChildNodes()
		.item(0);
		if (current != null){
			desc = current.getNodeValue();
		}
		
		current = ei.getElementsByTagName("PromocionFechaDesde").item(0).getChildNodes()
				.item(0);
				if (current != null){
					desde = current.getNodeValue();
				}
				
		current = ei.getElementsByTagName("PromocionFechaHasta").item(0).getChildNodes()
				.item(0);
				if (current != null){
					hasta = current.getNodeValue();
			}
		
		current = ei.getElementsByTagName("PromocionPuntosNecesarios").item(0).getChildNodes()
				.item(0);
				if (current != null){
					puntos = current.getNodeValue();
				}
		
		return new Promocion(Integer.parseInt(id), titulo, desc+" Válido del "+desde+" al "+hasta, puntos, img);
	}
}
