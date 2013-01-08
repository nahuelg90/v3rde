package com.proyecto.v3rd.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.proyecto.v3rd.R;

import core.domain.Cupon;

public class CuponListItemView extends LinearLayout {
	
	private Context context;
	private Cupon cupon;
	private AsyncImageView logoComerciante;
	private TextView tvTitulo;
	private TextView tvDescripcion;
	private TextView tvCodigo;
	

	public CuponListItemView(Context context, Cupon cupon) {
		super(context);
		this.context = context;
		this.cupon = cupon;
		LayoutInflater.from(context).inflate(R.layout.cupon_list_item, this,
				true);
		findAndInitViews();
	}

	private void findAndInitViews() {

		logoComerciante = (AsyncImageView) this.findViewById(R.id.cupon_logo_comerciante);
		logoComerciante.setDefaultImage(context.getResources().getDrawable(R.drawable.logo_home));
		tvTitulo = (TextView) this.findViewById(R.id.tv_cupon_titulo);
		tvDescripcion = (TextView) this.findViewById(R.id.tv_cupon_descripcion);
		tvCodigo = (TextView) this.findViewById(R.id.tv_cupon_codigo);
		
		logoComerciante.loadImage(this.cupon.getImagen_url(), logoComerciante.getLayoutParams().width, logoComerciante.getLayoutParams().height);
		
		tvDescripcion.setText(this.cupon.getDescripcion());
		tvCodigo.setText("Código: "+String.valueOf(this.cupon.getNroCupon()));
		tvTitulo.setText(this.cupon.getTitulo());
	}
}
