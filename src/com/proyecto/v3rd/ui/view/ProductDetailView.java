package com.proyecto.v3rd.ui.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.android.Facebook;
import com.proyecto.v3rd.R;
import com.proyecto.v3rd.ui.activity.FacebookActivity;

import core.domain.TipoDeProducto;

public class ProductDetailView extends RelativeLayout {
	
	private static Facebook fb;
	private AsyncImageView infografia;
	private ImageButton shareFacebook;
	private ImageButton shareMail;
	private Context context;
	private FacebookActivity fActivity;
	private TipoDeProducto p;

	public ProductDetailView(Context context, FacebookActivity activity, TipoDeProducto p) {
		super(context);
		this.fActivity = activity;
		this.context = context;
		this.p = p;
		LayoutInflater.from(context).inflate(R.layout.product_detail_view, this,
				true);
		findAndInitViews();
	}

	private void findAndInitViews() {
		 
		infografia = (AsyncImageViewInfografia)findViewById(R.id.i_infografia);
		if (p.getIdTipoDeProducto() != -1){
			infografia.loadImage(p.getInfografia_url(), infografia.getLayoutParams().width, infografia.getLayoutParams().height);
		} else {
			infografia.loadImage("",  infografia.getLayoutParams().width, infografia.getLayoutParams().height);
		}
		shareFacebook = (ImageButton)findViewById(R.id.share_facebook);

        
		shareFacebook.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				fActivity.setConnection();
				fActivity.getID();
				fActivity.postOnWall("Aprendé a tratar la basura", p.getInfografia_url());
			}
		});
		
		shareMail = (ImageButton)findViewById(R.id.share_mail);
		shareMail.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				enviarEmail();
			}
		});
		
	}
	
	private void enviarEmail(){
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_SUBJECT, "Aprendé a tratar la basura - V3RD");
		i.putExtra(Intent.EXTRA_TEXT   , "Utilizá nuestra aplicación para saber donde tirar la basura y mejorar el medio ambiente. Reciclá, Reducí y Reutilizá.\nMirá la imagen de este link, para saber como tratar el producto: "+p.getInfografia_url());
		try {
			fActivity.startActivity(Intent.createChooser(i, "Enviar mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(fActivity, "No hay clientes de email instalados.", Toast.LENGTH_SHORT).show();
		}
	}

}
