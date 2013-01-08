package com.proyecto.v3rd.ui.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.proyecto.v3rd.R;

import core.domain.UserSession;
import core.domain.V3RDApplication;



public abstract class MenuActivity extends FacebookActivity{
	
	public boolean onCreateOptionsMenu(Menu menu) {
		if(UserSession.IsUserLooged()){
		    MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.menu, menu);
		}
	    return true;
	}

	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()){
	        case R.id.menu_sesion_item:
	        	this.cerrarSesion();
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}


	private void cerrarSesion() {
		if(UserSession.IsUserLooged()){
			UserSession.LogOut();
			Intent intent = new Intent(this, LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(intent);
			this.finish();
		}
	}

}
