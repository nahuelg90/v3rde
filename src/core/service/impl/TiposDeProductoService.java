package core.service.impl;

import java.util.concurrent.Callable;

import com.proyecto.android.commons.core.service.AbstractService;
import com.proyecto.android.commons.core.service.ServiceExecutionListener;

import core.dao.impl.TipoDeProductoDao;

public class TiposDeProductoService extends AbstractService {

private TipoDeProductoDao tipoDao;
	
	public void getInfografia(final String codigo_barras, final String idUsuario, ServiceExecutionListener listener) {
		run(new Callable<Object>() {
				
				public Object call() throws Exception {
					
					return tipoDao.getInfografia(codigo_barras, idUsuario);
				}
			}, listener);
		
	}

	public void setTipoDeProductoDao(TipoDeProductoDao tipoDao) {
		this.tipoDao = tipoDao;
	}

	public TipoDeProductoDao getTipoDeProductoDao() {
		return tipoDao;
	}
}
