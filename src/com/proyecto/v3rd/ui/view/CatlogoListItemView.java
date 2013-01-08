package com.proyecto.v3rd.ui.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.proyecto.android.commons.core.application.container.ApplicationContainer;
import com.proyecto.android.commons.core.service.ServiceError;
import com.proyecto.android.commons.core.service.ServiceExecutionListener;
import com.proyecto.v3rd.R;
import com.proyecto.v3rd.ui.activity.CatalogoActivity;

import core.domain.Promocion;
import core.domain.UserSession;
import core.domain.Usuario;
import core.service.impl.CuponesService;

public class CatlogoListItemView extends LinearLayout {

	private CatalogoActivity context;
	private Promocion promo;
	private Button btnCanjear;
	private AsyncImageView logoComerciante;
	private TextView tvTitulo;
	private TextView tvDescripcion;
	private TextView tvPuntos;
	private CuponesService service;
	
	public CatlogoListItemView(CatalogoActivity context, Promocion promo) {
		super(context);
		this.context = context;
		this.promo = promo;
		LayoutInflater.from(context).inflate(R.layout.catalogo_list_item, this,
				true);
		 service = (CuponesService) ApplicationContainer.getInstance()
					.getComponent(CuponesService.class);
		findAndInitViews();
	}

	private void findAndInitViews() {
		
		btnCanjear = (Button) this.findViewById(R.id.btn_canjear);
		logoComerciante = (AsyncImageView) this.findViewById(R.id.logo_comerciante);
		logoComerciante.setDefaultImage(context.getResources().getDrawable(R.drawable.logo_home));
		tvTitulo = (TextView) this.findViewById(R.id.tv_titulo);
		tvDescripcion = (TextView) this.findViewById(R.id.tv_descripcion);
		tvPuntos = (TextView) this.findViewById(R.id.tv_puntosnecesarios);
		
		
		btnCanjear.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(UserSession.IsUserLooged()){
					Usuario user = UserSession.getLoggesUser();
					if(user.getPuntajeDisponible() >= Integer.parseInt(promo.getPuntosNecesarios())){
						service.realizarCanje(String.valueOf(user.getIdUsuario()), String.valueOf(promo.getIdPromocion()), new ServiceExecutionListener() {
							
							@Override
							public void onError(ServiceError arg0) {
								context.pDialog.hide();
								context.serviceNotAvailable(arg0);
							}
							
							@Override
							public void onCallComplete(Object arg0) {
								context.pDialog.hide();
								if((Boolean) arg0){
									mostrarMensaje("Canje realizado con éxtio!");
									UserSession.RestarPuntos(Integer.parseInt(promo.getPuntosNecesarios()));
								} else {
									mostrarMensaje("Error al realizar el canje.");
								}
							}
						});
						
					} else {
						mostrarMensaje("No dispone de los puntos necesarios para realizar el canje.");
					}
				} else {
					mostrarMensaje("Debes estar logueado para poder canjear tus puntos.");
				}
				
			}
		});
		
		logoComerciante.loadImage(this.promo.getImagen_url(), logoComerciante.getLayoutParams().width, logoComerciante.getLayoutParams().height);
		
		tvDescripcion.setText(this.promo.getDescripcion());
		tvPuntos.setText("Puntos: "+this.promo.getPuntosNecesarios());
		tvTitulo.setText(this.promo.getTitulo());
	}
	
	private void mostrarMensaje(String msg){
		 AlertDialog.Builder builder = new AlertDialog.Builder(context);
		 AlertDialog dialog;
		 builder.setMessage(msg);
		 builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
	    dialog = builder.create();
	    dialog.show();
	}

}
