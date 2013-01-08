package com.proyecto.v3rd.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.zxing.client.android.CaptureActivity;
import com.proyecto.v3rd.R;

import core.domain.UserSession;

public class HomeActivity extends MenuActivity {
	
	private ImageButton b_capturar;
	private ImageButton b_centros;
	private ImageButton b_ingresar;
	private ImageButton b_catalogo;
	private ImageButton b_cuenta;
	private ImageButton b_recolectores;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.home);
	        findAndInitViews();
	    }

	private void findAndInitViews() {
		this.initLoading();
		b_capturar = (ImageButton) this.findViewById(R.id.btn_capturar);
		b_capturar.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				HomeActivity.this.startActivity(new Intent(HomeActivity.this, CaptureActivity.class));
			}
		});
		
		b_cuenta = (ImageButton) this.findViewById(R.id.btn_mi_cuenta);
		b_cuenta.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				if(UserSession.IsUserLooged()){
					HomeActivity.this.startActivity(new Intent(HomeActivity.this, MiCuentaActivity.class));
				}
				else {
					AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
					 AlertDialog dialog;
					 builder.setMessage("Debe estar logueado para seguir");
					 builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			             public void onClick(DialogInterface dialog, int id) {
			                 dialog.dismiss();
			             }
			         });
				    dialog = builder.create();
				    dialog.show();
				}
			}
		});
		
		b_centros = (ImageButton) this.findViewById(R.id.btn_centros_reciclaje); 
		b_centros.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				HomeActivity.this.startActivity(new Intent(HomeActivity.this, CentrosActivity.class));
			}
		});
		
		
		b_ingresar = (ImageButton) this.findViewById(R.id.btn_ingresar_codigo); 
		b_ingresar.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				final Dialog dialog = new Dialog(HomeActivity.this);
				
				dialog.setContentView(R.layout.enter_code_dialog);
				dialog.setTitle("Ingrese el código de barras");
				
				Button accept = (Button) dialog.findViewById(R.id.btn_codigo_aceptar);
				Button cancel = (Button) dialog.findViewById(R.id.btn_codigo_cancelar);
				final EditText codigo_barras = (EditText) dialog.findViewById(R.id.et_codigo_barras);
				
				accept.setOnClickListener(new Button.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(HomeActivity.this, DetalleProductoActivity.class);
						intent.putExtra("RESULT", codigo_barras.getText().toString());
						HomeActivity.this.startActivity(intent);
					}
				});
				
				cancel.setOnClickListener(new Button.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				
				dialog.show();
			}
		});
		
		b_catalogo = (ImageButton) this.findViewById(R.id.btn_catalogo);
		b_catalogo.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				HomeActivity.this.startActivity(new Intent(HomeActivity.this, CatalogoActivity.class));
			}
		});
		
		b_recolectores = (ImageButton) this.findViewById(R.id.btn_recolectores);
		b_recolectores.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				HomeActivity.this.startActivity(new Intent(HomeActivity.this, CartonerosActivity.class));
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
