package com.proyecto.v3rd.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

import com.proyecto.android.commons.core.application.container.ApplicationContainer;
import com.proyecto.android.commons.core.service.ServiceError;
import com.proyecto.android.commons.core.service.ServiceExecutionListener;
import com.proyecto.v3rd.R;
import com.proyecto.v3rd.ui.view.MapaCentrosView;
import com.proyecto.v3rd.ui.view.ProductDetailView;
import com.proyecto.v3rd.ui.view.ReversableButton;
import com.proyecto.v3rd.ui.view.ReverseButton;
import com.proyecto.v3rd.ui.view.ReverseImageButton;
import com.proyecto.v3rd.ui.view.TresRView;

import core.domain.CentroDeReciclaje;
import core.domain.TipoDeProducto;
import core.domain.UserSession;
import core.domain.V3RDApplication;
import core.service.impl.TiposDeProductoService;

public class DetalleProductoActivity extends MenuActivity {
	
	ReverseButton btn_infografia;
	ReverseButton btn_3r;
	ReverseImageButton centros_de_reciclaje;
	ViewFlipper flipper;
	private MapaCentrosView mc;
	private TresRView tr;
	private ProductDetailView pd;
	private TiposDeProductoService service;
	private ProgressDialog pDialog;
	private String codigo_barras;
	private TipoDeProducto prod;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        Intent intent = this.getIntent();
        codigo_barras = intent.getStringExtra("RESULT");
		service = (TiposDeProductoService) ApplicationContainer.getInstance()
				.getComponent(TiposDeProductoService.class);
		findAndInitViews();
    }

	private void findAndInitViews() {
		// TODO Auto-generated method stub
		this.initLoading();
		btn_infografia = (ReverseButton) findViewById(R.id.btn_infografia);
		btn_3r = (ReverseButton)findViewById(R.id.btn_3r);
		centros_de_reciclaje = (ReverseImageButton)findViewById(R.id.btn_producto_reciclaje);
		
		
		int selectedColor = R.color.verde_background;
		int notSelectedColor = R.color.verde_fondo_botones;
		
		List<ReversableButton> buttons = new ArrayList<ReversableButton>();
		buttons.add(btn_infografia);
		buttons.add(btn_3r);
		buttons.add(centros_de_reciclaje);
		
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Cargando...");
		pDialog.show();
		
		
		
		
		for(ReversableButton rev : buttons){
			rev.init(selectedColor, notSelectedColor, buttons);
		}
		
		btn_infografia.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(flipper.getCurrentView() != pd){
					btn_infografia.changeBackgrounds();
					flipper.setDisplayedChild(flipper.indexOfChild(pd));
				}
			}
		});
		
		btn_3r.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(tr == null){
					tr = new TresRView(DetalleProductoActivity.this, prod);
					flipper.addView(tr);
				}
				if(flipper.getCurrentView() != tr){
					btn_3r.changeBackgrounds();
					flipper.setDisplayedChild(flipper.indexOfChild(tr));
				}
			}
		});

		centros_de_reciclaje.setOnClickListener(new ImageButton.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mc == null){
					List<CentroDeReciclaje> centros;
					if(prod.getIdTipoDeProducto() != -1){
						//buscar los centros que atienden el tipo
						centros = V3RDApplication.getCentroReciclajeByTipoProducto(prod.getIdTipoDeProducto());
					} else {
						centros = V3RDApplication.centrosDeReciclaje;
					}
					
					mc = new MapaCentrosView(DetalleProductoActivity.this, centros);
					flipper.addView(mc);
				}
				if(flipper.getCurrentView() != mc){
					centros_de_reciclaje.changeBackgrounds();
					flipper.setDisplayedChild(flipper.indexOfChild(mc));
				}
			}
		});
		
		String idUsuario = "-1";
		if(UserSession.IsUserLooged()){
			idUsuario = String.valueOf(UserSession.getLoggesUser().getIdUsuario());
		}
		
		service.getInfografia(codigo_barras, idUsuario, new ServiceExecutionListener() {
			
			@Override
			public void onError(ServiceError arg0) {
				pDialog.hide();
				servicioNoDisponible();
			}
			
			@Override
			public void onCallComplete(Object arg0) {
				prod = (TipoDeProducto) arg0;
				flipper = (ViewFlipper) findViewById(R.id.flipper_detail);
				pd = new ProductDetailView(DetalleProductoActivity.this,DetalleProductoActivity.this, prod);
				mc = null; 
				tr = null; 
				
				flipper.addView(pd);
				pDialog.hide();
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
		return false;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(mc!= null){
			mc.onResume();
		}
	}
	
	private void servicioNoDisponible(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 AlertDialog dialog;
		 builder.setMessage("Servicios no disponibles");
		 builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                DetalleProductoActivity.this.finish();
            }
        });
	    dialog = builder.create();
	    dialog.show();
	}
}
