package com.proyecto.v3rd.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.proyecto.v3rd.R;

import core.domain.TipoDeProducto;

public class TresRView extends LinearLayout {
	
	private AsyncImageView imagen;
	private TipoDeProducto prod;
	
	public TresRView(Context context, TipoDeProducto prod) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.tresr_view, this,
				true);
		this.prod = prod;
		imagen = (AsyncImageView) this.findViewById(R.id.tresr);
		imagen.setDefaultImage(context.getResources().getDrawable(R.drawable.tresr));
		imagen.loadImage(prod.getTr_url(),imagen.getLayoutParams().width, imagen.getLayoutParams().height);
	}

}
