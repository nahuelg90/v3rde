package core.service.impl;

import java.util.concurrent.Callable;

import com.proyecto.android.commons.core.service.AbstractService;
import com.proyecto.android.commons.core.service.ServiceExecutionListener;

import core.dao.impl.CentroDeReciclajeDao;

public class CentrosDeReciclajeService extends AbstractService {
private CentroDeReciclajeDao centrosDao;
	
	public void getCentrosDeReciclaje(ServiceExecutionListener listener) {
		run(new Callable<Object>() {
				
				public Object call() throws Exception {
					
					return centrosDao.getAllCentrosDeReciclaje();
				}
			}, listener);
		
	}

	public void setCentroDeReciclajeDao(CentroDeReciclajeDao centrosDao) {
		this.centrosDao = centrosDao;
	}

	public CentroDeReciclajeDao getCentroDeReciclajeDao() {
		return centrosDao;
	}
}
