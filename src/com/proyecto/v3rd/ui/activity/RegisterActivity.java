package com.proyecto.v3rd.ui.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.proyecto.android.commons.core.application.container.ApplicationContainer;
import com.proyecto.android.commons.core.service.ServiceError;
import com.proyecto.android.commons.core.service.ServiceExecutionListener;
import com.proyecto.v3rd.R;

import core.domain.Cupon;
import core.domain.UserSession;
import core.domain.Usuario;
import core.service.impl.UsuarioService;

public class RegisterActivity extends V3rdActivity {
	
	private Spinner spinnerLocalidades;
	private ProgressDialog pDialog;
	private ImageButton aceptar;
	private ImageButton cancelar;
	private EditText nombre;
	private EditText apellido;
	private EditText mail;
	private EditText pass;
	private EditText pass2;
	private UsuarioService service;
	private Activity act;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        service = (UsuarioService) ApplicationContainer.getInstance()
				.getComponent(UsuarioService.class);
        findAndInitViews();
    }
	
	private void findAndInitViews() {
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Cargando...");
		aceptar = (ImageButton) findViewById(R.id.btn_registro_aceptar);
		cancelar = (ImageButton) findViewById(R.id.btn_registro_cancelar);
		nombre = (EditText) findViewById(R.id.et_register_nombre);
		apellido = (EditText) findViewById(R.id.et_register_apellido);
		mail = (EditText) findViewById(R.id.et_register_mail);
		pass = (EditText) findViewById(R.id.et_register_pass);
		pass2 = (EditText) findViewById(R.id.et_register_pass2);
		
		cancelar.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RegisterActivity.this.finish();
			}
		});
		
		aceptar.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(nombre.getText().toString().equals("") || apellido.getText().toString().equals("") || mail.getText().toString().equals("") || pass.getText().toString().equals("")){
					mostrarMensaje("Todos los campos son obligatorios");
					return;
				}
				String mailS = mail.getText().toString();
				if(!(mailS.contains("@") && mailS.contains("."))){
					mostrarMensaje("Debe ser una dirección de correo válida.");
					return;
				}
				if(!pass.getText().toString().equals(pass2.getText().toString())){
					mostrarMensaje("Las contraseñas deben coincidir");
					return;
				}
				if(pass.getText().length() < 4){
					mostrarMensaje("La contraseña debe tener un mínimo de 4 caracteres");
					return;
				}
				pDialog.show();
				Usuario nuevoUsuario = new Usuario(0, apellido.getText().toString(), mailS, new ArrayList<Cupon>(), nombre.getText().toString(), spinnerLocalidades.getSelectedItem().toString(), 0);
				service.registrarUsuario(nuevoUsuario, pass.getText().toString() , new ServiceExecutionListener() {
					
					@Override
					public void onError(ServiceError arg0) {
						pDialog.hide();
						servicioNoDisponible();
					}
					
					@Override
					public void onCallComplete(Object arg0) {
						pDialog.hide();
						Usuario user = (Usuario)arg0;
						if(user.getIdUsuario() != -1){
							UserSession.LogUser((Usuario)arg0);
							Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							RegisterActivity.this.startActivity(intent);
							RegisterActivity.this.finish();
						} else {
							mostrarMensaje("Usuario ya existente");
						}
					}
				});
			}
		});
		spinnerLocalidades  = (Spinner) findViewById(R.id.register_spinner);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.localidades_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerLocalidades.setAdapter(adapter);
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
                RegisterActivity.this.finish();
            }
        });
	    dialog = builder.create();
	    dialog.show();
	}
	
	
	
}
