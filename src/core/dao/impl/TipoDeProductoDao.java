package core.dao.impl;

import java.io.IOException;
import java.io.StringReader;
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

import core.domain.CentroDeReciclaje;
import core.domain.TipoDeProducto;
import core.domain.UserSession;
import core.exception.CreateDocumentBuilderException;
import core.exception.ParserException;
import core.exception.ServerFailedException;

public class TipoDeProductoDao extends AbstractDao<TipoDeProducto> {
	
	public TipoDeProducto getInfografia(String codigoBarras, String idUsuario){
		TipoDeProducto prod = null;
		StringBuilder url = new StringBuilder();
		Formatter formatter = new Formatter(url, Locale.US);
		formatter.format(configuration.getValue("webservice.getInfografia"),
				codigoBarras,
				idUsuario);
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(this.getExternalResource(
					url.toString(), 60000));
			Element root = dom.getDocumentElement();

			if (root == null){
				return prod;
			}
			
			NodeList items = root.getElementsByTagName("tipo");
			
			if(items.getLength() == 0){
				return new TipoDeProducto("", -1, "", "");
			}

			for (int i = 0; i < items.getLength(); i++){
				prod = getCentroDeReciclaje((Element) items.item(i));
			}

		} catch (ParserConfigurationException e) {
			throw new CreateDocumentBuilderException(e);
		} catch (SAXException e) {
			throw new ParserException(e);
		} catch (IOException e) {
			throw new ServerFailedException(e);
		}
		return prod;
	}
	
	private TipoDeProducto getCentroDeReciclaje(Element ei) {
		String id = "";
		String nombre = "";
		String info = "";
		String tresr = "";
		
		Node current = null;

		current = ei.getElementsByTagName("TipoProductoId").item(0).getChildNodes()
				.item(0);
		if (current != null){
			id = current.getNodeValue();
		}
		
		current = ei.getElementsByTagName("TipoProductoDescripcion").item(0).getChildNodes()
		.item(0);
		if (current != null){
			nombre = current.getNodeValue();
		}

		current = ei.getElementsByTagName("TipoProductoInfografiaURL").item(0).getChildNodes()
		.item(0);
		if (current != null){
			info = current.getNodeValue();
		}
		
		current = ei.getElementsByTagName("TipoProducto3RURL").item(0).getChildNodes()
				.item(0);
				if (current != null){
					tresr = current.getNodeValue();
				}
		
		if(UserSession.IsUserLooged()){
			current = ei.getElementsByTagName("Puntos").item(0).getChildNodes()
			.item(0);
			if (current != null){
				UserSession.SumarPuntos(Integer.parseInt(current.getNodeValue()));
			}
		}
		return new TipoDeProducto(tresr, Integer.parseInt(id), info, nombre);
	}
}
