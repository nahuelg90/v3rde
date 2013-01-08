package core.service.impl;

import java.util.concurrent.Callable;

import com.proyecto.android.commons.core.service.AbstractService;
import com.proyecto.android.commons.core.service.ServiceExecutionListener;

import core.dao.impl.PromocionDao;

public class PromocionesService extends AbstractService {
	
	private PromocionDao promocionDao;
	
	public void getPromociones(ServiceExecutionListener listener) {
		run(new Callable<Object>() {
				
				public Object call() throws Exception {
					
					return promocionDao.getAllPromociones();
				}
			}, listener);
		
	}

	public void setPromocionDao(PromocionDao promocionDao) {
		this.promocionDao = promocionDao;
	}

	public PromocionDao getPromocionDao() {
		return promocionDao;
	}

}
