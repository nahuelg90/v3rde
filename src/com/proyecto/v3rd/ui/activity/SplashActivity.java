package com.proyecto.v3rd.ui.activity;


import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.proyecto.android.commons.core.application.container.ApplicationContainer;
import com.proyecto.android.commons.core.service.ServiceError;
import com.proyecto.android.commons.core.service.ServiceExecutionListener;
import com.proyecto.v3rd.R;

import core.domain.CentroDeReciclaje;
import core.domain.V3RDApplication;
import core.service.impl.CentrosDeReciclajeService;
import core.util.AlertDelegate;
import core.util.PopupHandler;

public class SplashActivity extends Activity {
	
	private CentrosDeReciclajeService service;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Display display = getWindowManager().getDefaultDisplay();
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		LayoutInflater inflater = getLayoutInflater();
		ProgressBar bar = (ProgressBar) inflater.inflate(
				R.layout.progressbarlayout, null);
		layoutParams.setMargins(0, display.getHeight() * 2 / 3, 0, 0);
		ll.setLayoutParams(layoutParams);
		ll.addView(bar);
		LinearLayout splashContainer = (LinearLayout) this
				.findViewById(R.id.splashcontainer);
		splashContainer.addView(ll);
		start();
	}
	
	private void start(){
		service = (CentrosDeReciclajeService) ApplicationContainer.getInstance()
				.getComponent(CentrosDeReciclajeService.class);
		service.getCentrosDeReciclaje(new ServiceExecutionListener() {
			
			@Override
			public void onError(ServiceError arg0) {
				serviceNotAvailable(arg0);
			}
			
			@Override
			public void onCallComplete(Object arg0) {
				V3RDApplication.centrosDeReciclaje = (List<CentroDeReciclaje>) arg0;
				startActivity(new Intent(SplashActivity.this, LoginActivity.class));
				finish();
			}
		});
	}
	
	private void serviceNotAvailable(ServiceError error) {
		Log.d("Agenda Service", error.getCause().getMessage());
		PopupHandler
				.getInstance()
				.showAlertPopUp("Error con los servicios",
						SplashActivity.this,
						new AlertDelegate() {

							public void onOk() {
								SplashActivity.this.finish();
							}
						});
	}
}
