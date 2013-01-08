package core.service.impl;

import java.util.concurrent.Callable;

import com.proyecto.android.commons.core.service.AbstractService;
import com.proyecto.android.commons.core.service.ServiceExecutionListener;

import core.dao.impl.CuponDao;

public class CuponesService extends AbstractService {
private CuponDao cuponDao;
	
	public void getCuponesFrom(final String idUsuario, ServiceExecutionListener listener) {
		run(new Callable<Object>() {
				
				public Object call() throws Exception {
					
					return cuponDao.getAllCuponesFrom(idUsuario);
				}
			}, listener);
		
	}
	
	public void realizarCanje(final String idUsuario, final String idPromocion, ServiceExecutionListener listener) {
		run(new Callable<Object>() {
				
				public Object call() throws Exception {
					
					return cuponDao.realizarCanje(idUsuario, idPromocion);
				}
			}, listener);
		
	}

	public void setCuponDao(CuponDao cuponDao) {
		this.cuponDao = cuponDao;
	}

	public CuponDao getCuponDao() {
		return cuponDao;
	}
}
