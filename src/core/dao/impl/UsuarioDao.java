package core.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Formatter;
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

import core.domain.Cupon;
import core.domain.Usuario;
import core.exception.CreateDocumentBuilderException;
import core.exception.ParserException;
import core.exception.ServerFailedException;

public class UsuarioDao extends AbstractDao<Usuario> {
	
	public Usuario logearUsuario(String idUsuario, String password){
		Usuario user = null;
		StringBuilder url = new StringBuilder();
		Formatter formatter = new Formatter(url, Locale.FRENCH);
		formatter.format(configuration.getValue("webservice.login"), idUsuario, password);
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
				return user;
			}
			NodeList items = root.getElementsByTagName("usuario");

			if(items.getLength() == 0){
				return new Usuario(-1, "", "", null, "", "", 0);
			}
			
			
			for (int i = 0; i < items.getLength(); i++){
				user = getUsuario((Element) items.item(i));
			}

		} catch (ParserConfigurationException e) {
			throw new CreateDocumentBuilderException(e);
		} catch (SAXException e) {
			throw new ParserException(e);
		} catch (IOException e) {
			throw new ServerFailedException(e);
		}
		return user;
	}
	
	public Usuario registrarUsuario(Usuario usuario, String password){
		StringBuilder url = new StringBuilder();
		Formatter formatter = new Formatter(url, Locale.US);
		formatter.format(configuration.getValue("webservice.register"),
				usuario.getNombre(),
				usuario.getApellido(),
				usuario.getMail(),
				password);
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(this.getExternalResource(
					url.toString(), 60000));
			Element root = dom.getDocumentElement();

			if (root == null){
				usuario.setIdUsuario(-1);
				return usuario;
			}
			NodeList items = root.getElementsByTagName("idUsuario");

			if(items.getLength() == 0){
				return new Usuario(-1, "", "", null, "", "", 0);
			}
			
			
			
			usuario.setIdUsuario(Integer.parseInt(((Element)items.item(0)).getChildNodes().item(0).getNodeValue()));
			

		} catch (ParserConfigurationException e) {
			throw new CreateDocumentBuilderException(e);
		} catch (SAXException e) {
			throw new ParserException(e);
		} catch (IOException e) {
			throw new ServerFailedException(e);
		}
		return usuario;
	}

	
	private Usuario getUsuario(Element ei) {
		String idUsuario = "";
		String apellido = "";
		String mail = "";
		String cupones = "";
		String nombre = "";
		String localidad = "";
		String puntajeDisponible = "";
		
		
		Node current = null;

		current = ei.getElementsByTagName("UsuarioId").item(0).getChildNodes()
				.item(0);
		if (current != null){
			idUsuario = current.getNodeValue();
		}
		
		current = ei.getElementsByTagName("UsuarioApellido").item(0).getChildNodes()
		.item(0);
		if (current != null){
			apellido = current.getNodeValue();
		}

		current = ei.getElementsByTagName("UsuarioMail").item(0).getChildNodes()
		.item(0);
		if (current != null){
			mail = current.getNodeValue();
		}
		
		current = ei.getElementsByTagName("UsuarioNombre").item(0).getChildNodes()
				.item(0);
				if (current != null){
					nombre = current.getNodeValue();
				}

		current = ei.getElementsByTagName("UsuarioLocalidad").item(0).getChildNodes()
				.item(0);
				if (current != null){
					localidad = current.getNodeValue();
		}
		
		current = ei.getElementsByTagName("UsuarioPuntosDisponibles").item(0).getChildNodes()
				.item(0);
				if (current != null){
					puntajeDisponible = current.getNodeValue();
		}
		
				
		return new Usuario(Integer.parseInt(idUsuario), apellido, mail, new ArrayList<Cupon>(), nombre, localidad, Integer.parseInt(puntajeDisponible));
	}
}
