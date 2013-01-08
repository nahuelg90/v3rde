package com.proyecto.v3rd.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.proyecto.android.commons.core.application.container.ApplicationContainer;
import com.proyecto.android.commons.core.service.ServiceError;
import com.proyecto.android.commons.core.service.ServiceExecutionListener;
import com.proyecto.v3rd.R;

import core.domain.UserSession;
import core.domain.Usuario;
import core.service.impl.UsuarioService;

public class LoginActivity extends V3rdActivity {

	private EditText mail;
	private EditText password;
	private ImageButton ingresar;
	private ImageButton registrar;
	private ImageButton invitado;
	private UsuarioService service;
	private ProgressDialog pDialog;
	
	
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.login);
	        findAndInitViews();
	    }
	 
	 	public void findAndInitViews(){
	 		this.initLoading();
	 		service = (UsuarioService) ApplicationContainer.getInstance()
					.getComponent(UsuarioService.class);
	 		mail = (EditText) this.findViewById(R.id.et_usuario);
	 		password = (EditText) this.findViewById(R.id.et_password);
	 		ingresar = (ImageButton) this.findViewById(R.id.btn_ingresar);
	 		registrar = (ImageButton) this.findViewById(R.id.btn_registro);
	 		invitado = (ImageButton) this.findViewById(R.id.btn_invitado);
	 		pDialog = new ProgressDialog(this);
	 		pDialog.setMessage("Cargando...");
	 		
	 		invitado.setOnClickListener(new ImageButton.OnClickListener() {
				public void onClick(View v) {
					LoginActivity.this.startActivity(new Intent(LoginActivity.this, HomeActivity.class));
				}
			});
	 		
	 		registrar.setOnClickListener(new ImageButton.OnClickListener() {
				public void onClick(View v) {
					LoginActivity.this.startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
					
				}
			});
	 		
	 		ingresar.setOnClickListener(new ImageButton.OnClickListener() {
				public void onClick(View v) {
					pDialog.show();
					service.logearUsuario(mail.getText().toString(), password.getText().toString(), new ServiceExecutionListener() {
						
						@Override
						public void onError(ServiceError arg0) {
							pDialog.hide();
							servicioNoDisponible();
						}
						
						@Override
						public void onCallComplete(Object arg0) {
							Usuario user = (Usuario)arg0;
							if(user.getIdUsuario() != -1){
								UserSession.LogUser(user);
								LoginActivity.this.startActivity(new Intent(LoginActivity.this, HomeActivity.class));
								LoginActivity.this.finish();
							} else {
								pDialog.hide();
								mostrarMensaje("Usuario o contraseña incorrectos.");
							}
						}
					});
					
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
		
		private void mostrarMensaje(String msg){
			 AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
		
		private void servicioNoDisponible(){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			 AlertDialog dialog;
			 builder.setMessage("Servicios no disponibles");
			 builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	                dialog.dismiss();
	                LoginActivity.this.finish();
	            }
	        });
		    dialog = builder.create();
		    dialog.show();
		}
		
}
