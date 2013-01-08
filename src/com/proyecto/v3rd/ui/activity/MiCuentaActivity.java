package com.proyecto.v3rd.ui.activity;

import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.proyecto.android.commons.core.application.container.ApplicationContainer;
import com.proyecto.android.commons.core.service.ServiceError;
import com.proyecto.android.commons.core.service.ServiceExecutionListener;
import com.proyecto.v3rd.R;
import com.proyecto.v3rd.ui.view.CuponListItemView;

import core.domain.Cupon;
import core.domain.UserSession;
import core.domain.Usuario;
import core.service.impl.CuponesService;

public class MiCuentaActivity extends MenuActivity {
	
	private LinearLayout container;
	private CuponesService service;
	private ProgressDialog pDialog;
	private Usuario usuario;
	private TextView tv_nombre;
	private TextView tv_apellido;
	private TextView tv_mail;
	private TextView tv_localidad;
	private TextView tv_puntos;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mi_cuenta);
        service = (CuponesService) ApplicationContainer.getInstance()
				.getComponent(CuponesService.class);
		findAndInitViews();
    }

	private void findAndInitViews() {
		
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Cargando...");
		pDialog.show();
		container = (LinearLayout) this.findViewById(R.id.cupones_usuario);
		tv_nombre = (TextView) this.findViewById(R.id.cuenta_nombre);
		tv_apellido = (TextView) this.findViewById(R.id.cuenta_apellido);
		tv_mail = (TextView) this.findViewById(R.id.cuenta_mail);
		tv_localidad = (TextView) this.findViewById(R.id.cuenta_localidad);
		tv_puntos = (TextView) this.findViewById(R.id.cuenta_puntos);
		usuario = UserSession.getLoggesUser();
		
		tv_nombre.setText(usuario.getNombre());
		tv_apellido.setText(usuario.getApellido());
		tv_mail.setText(usuario.getMail());
		tv_localidad.setText(usuario.getLocalidad());
		tv_puntos.setText(String.valueOf(usuario.getPuntajeDisponible()));
		
		service.getCuponesFrom(String.valueOf(usuario.getIdUsuario()), new ServiceExecutionListener() {
			
			@Override
			public void onError(ServiceError arg0) {
				pDialog.hide();
				servicioNoDisponible();
			}
			
			@Override
			public void onCallComplete(Object arg0) {
				pDialog.hide();
				List<Cupon> cupones = (List<Cupon>)arg0;
				if(cupones.size() == 0){
					container.addView(noCuponesView());
					return;
				}
				for(Cupon cupon : cupones){
					container.addView(new CuponListItemView(MiCuentaActivity.this, cupon));
				}
			}
		});
		
	}
	
	private LinearLayout noCuponesView(){
		return new LinearLayout(this);
	}

	@Override
	protected void initLoading() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void servicioNoDisponible(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 AlertDialog dialog;
		 builder.setMessage("Servicios no disponibles");
		 builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                MiCuentaActivity.this.finish();
            }
        });
	    dialog = builder.create();
	    dialog.show();
	}

}
