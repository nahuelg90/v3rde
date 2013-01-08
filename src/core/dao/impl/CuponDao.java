package core.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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
import core.domain.Cupon;
import core.exception.CreateDocumentBuilderException;
import core.exception.ParserException;
import core.exception.ServerFailedException;

public class CuponDao extends AbstractDao<Cupon> {

	public List<Cupon> getAllCuponesFrom(String idUsuario){
			List<Cupon> cupones = new ArrayList<Cupon>();
			StringBuilder url = new StringBuilder();
			Formatter formatter = new Formatter(url, Locale.FRENCH);
			formatter.format(configuration.getValue("webservice.getcupones"), idUsuario);
			
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
					return cupones;
				}
				NodeList items = root.getElementsByTagName("cupon");

				for (int i = 0; i < items.getLength(); i++){
					cupones.add(getCupon((Element) items.item(i)));
				}

			} catch (ParserConfigurationException e) {
				throw new CreateDocumentBuilderException(e);
			} catch (SAXException e) {
				throw new ParserException(e);
			} catch (IOException e) {
				throw new ServerFailedException(e);
			}
			return cupones;
		}
		
		private Cupon getCupon(Element ei) {
			String id = "";
			String desc = "";
			String img = "";
			String titulo = "";
			
			
			Node current = null;

			current = ei.getElementsByTagName("CanjeCuponNumero").item(0).getChildNodes()
					.item(0);
			if (current != null){
				id = current.getNodeValue();
			}
			
			current = ei.getElementsByTagName("PromocionDescripcion").item(0).getChildNodes()
			.item(0);
			if (current != null){
				desc = current.getNodeValue();
			}

			current = ei.getElementsByTagName("ComercianteLogoURL").item(0).getChildNodes()
			.item(0);
			if (current != null){
				img = current.getNodeValue();
			}
			
			current = ei.getElementsByTagName("PromocionTitulo").item(0).getChildNodes()
					.item(0);
					if (current != null){
						titulo = current.getNodeValue();
					}
			
			return new Cupon(desc, img, Integer.parseInt(id), titulo);
		}

		public Boolean realizarCanje(String idUsuario, String idPromocion) {
			StringBuilder url = new StringBuilder();
			Formatter formatter = new Formatter(url, Locale.US);
			formatter.format(configuration.getValue("webservice.canjecupones"), idUsuario, idPromocion);
			
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document dom = builder.parse(this.getExternalResource(
						url.toString(), 60000));
				Element root = dom.getDocumentElement();

				if (root == null){
					return false;
				}
				NodeList items = root.getElementsByTagName("cupon");

			} catch (ParserConfigurationException e) {
				throw new CreateDocumentBuilderException(e);
			} catch (SAXException e) {
				throw new ParserException(e);
			} catch (IOException e) {
				throw new ServerFailedException(e);
			}
			
			return true;
		}
		
}
