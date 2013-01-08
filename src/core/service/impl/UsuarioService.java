package core.service.impl;

import java.util.concurrent.Callable;

import com.proyecto.android.commons.core.service.AbstractService;
import com.proyecto.android.commons.core.service.ServiceExecutionListener;

import core.dao.impl.UsuarioDao;
import core.domain.Usuario;



public class UsuarioService extends AbstractService {

private UsuarioDao usuarioDao;
	
	public void logearUsuario( final String idUsuario, final String password, ServiceExecutionListener listener) {
		run(new Callable<Object>() {
				
				public Object call() throws Exception {
					
					return usuarioDao.logearUsuario(idUsuario, password);
				}
			}, listener);
		
	}

	
	public void registrarUsuario( final Usuario usuario, final String password, ServiceExecutionListener listener) {
		run(new Callable<Object>() {
				
				public Object call() throws Exception {
					
					return usuarioDao.registrarUsuario(usuario, password);
				}
			}, listener);
		
	}
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}
}
