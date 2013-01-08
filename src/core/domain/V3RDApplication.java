package core.domain;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.core.Persister;

import android.app.Application;
import android.content.Context;

import com.facebook.android.Facebook;
import com.proyecto.android.commons.core.application.container.ApplicationContainer;
import com.proyecto.android.commons.core.application.container.Container;
import com.proyecto.android.commons.core.application.core.Configuration;
import com.proyecto.android.commons.core.service.executor.AndroidServiceExecutionStrategy;
import com.proyecto.android.commons.core.web.provider.AndroidWebServiceProvider;

import core.dao.impl.CentroDeReciclajeDao;
import core.dao.impl.CuponDao;
import core.dao.impl.PromocionDao;
import core.dao.impl.TipoDeProductoDao;
import core.dao.impl.UsuarioDao;
import core.service.impl.CentrosDeReciclajeService;
import core.service.impl.CuponesService;
import core.service.impl.ImageLoader;
import core.service.impl.PromocionesService;
import core.service.impl.TiposDeProductoService;
import core.service.impl.UsuarioService;

/**
 * @author Nahuel
 * @version 1.0
 * @created 27-Sep-2012 3:12:10 PM
 */
public class V3RDApplication extends Application{

	private static final String URL_PROPERTIES = "url.properties";
	
	public static List<CentroDeReciclaje> centrosDeReciclaje;
	public static List<Promocion> promociones;
	public CentroDeReciclaje m_CentroDeReciclaje;
	public Promocion m_Promocion;
	public Usuario m_Usuario;
	
	
	private PromocionesService promocionService;
	/** Keep global info about the application */
	private static Context appContext; 




	public void onCreate() {
		super.onCreate();
		
		Container container = ApplicationContainer.getInstance();
		
		// add simple parser implementation
		// as a singleton
		container.addSingletonComponent(Persister.class);
		
		// Configuration properties
		container.addSingletonComponent(new Configuration(URL_PROPERTIES));
		
		// Webservice provider: use android implementation
		container.addSingletonComponent(AndroidWebServiceProvider.class);
		
		// image async loader
		container.addComponent(ImageLoader.class);
		
		
		// Execute service in Android's thread-pool
		// container.addComponent(AndroidServiceExecutionStrategy.class);
		container.addComponent(AndroidServiceExecutionStrategy.class);
		
		// post processor
		appContext = this.getApplicationContext();
		
		this.setUp(container);
		
	}
	

	/**
	 * Add custom beans
	 * 
	 * @param container
	 */
	protected void setUp(Container container) {
		
		container.addComponent(PromocionesService.class);
		
		container.addComponent(PromocionDao.class);
		
		container.addComponent(UsuarioService.class);
		
		container.addComponent(UsuarioDao.class);
		
		container.addComponent(CentrosDeReciclajeService.class);
		
		container.addComponent(CentroDeReciclajeDao.class);
		
		container.addComponent(CuponesService.class);
		
		container.addComponent(CuponDao.class);
			
		container.addComponent(TiposDeProductoService.class);
		
		container.addComponent(TipoDeProductoDao.class);
		
		
		Facebook facebook = new Facebook("458802534140209");
		container.addSingletonComponent(facebook);
	}
	
	
	public PromocionesService getPromocionesService(){
		return this.promocionService;
	}
	
	/**
	 * Get context object.
	 * 
	 * @return
	 */
	public static V3RDApplication getAppContext() {

		return (V3RDApplication) appContext;
	}
	
	
	public V3RDApplication(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param IdTipoProducto
	 */
	public static List<CentroDeReciclaje> getCentroReciclajeByTipoProducto(int IdTipoProducto){
		List<CentroDeReciclaje> centros = new ArrayList<CentroDeReciclaje>();
		for(CentroDeReciclaje centro : centrosDeReciclaje){
			if(centro.atiendeTipoDeProducto(IdTipoProducto)){
				centros.add(centro);
			}
		}

		return centros;	
	}

}