package com.proyecto.v3rd.ui.activity;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.proyecto.android.commons.core.application.container.ApplicationContainer;
import com.proyecto.android.commons.core.service.ServiceError;
import com.proyecto.android.commons.core.service.ServiceExecutionListener;
import com.proyecto.v3rd.R;
import com.proyecto.v3rd.ui.view.CatlogoListItemView;

import core.domain.Promocion;
import core.service.impl.PromocionesService;
import core.util.AlertDelegate;
import core.util.PopupHandler;

public class CatalogoActivity extends MenuActivity {
	
	private LinearLayout container; 
	private PromocionesService service;
	public ProgressDialog pDialog;
	
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.catalogo);
	        service = (PromocionesService) ApplicationContainer.getInstance()
					.getComponent(PromocionesService.class);
	        findAndInitViews();
	    }

	private void findAndInitViews() {
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Cargando...");
		pDialog.show();
		container = (LinearLayout) this.findViewById(R.id.catalogo_list_content);
		
		service.getPromociones(new ServiceExecutionListener() {
			
			@Override
			public void onError(ServiceError error) {
				pDialog.hide();
				serviceNotAvailable(error);
			}
			
			@Override
			public void onCallComplete(Object parameters) {
				pDialog.hide();
				for(Promocion promo : (List<Promocion>)parameters){
					container.addView(new CatlogoListItemView(CatalogoActivity.this,promo));
				}
			}
		});
	}
	
	public void serviceNotAvailable(ServiceError error) {

		PopupHandler
				.getInstance()
				.showAlertPopUp(
						"Error en los servicios",
						CatalogoActivity.this,
						new AlertDelegate() {

							public void onOk() {
								CatalogoActivity.this.finish();
							}
						});
	}

	@Override
	protected void initLoading() {
		 ProgressDialog loading = new ProgressDialog(this);
 		 loading.setMessage("Cargando...");
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
