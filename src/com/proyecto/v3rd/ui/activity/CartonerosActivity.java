package com.proyecto.v3rd.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.proyecto.v3rd.R;
import com.proyecto.v3rd.ui.view.MapaCentrosView;

import core.domain.CentroDeReciclaje;

public class CartonerosActivity extends MenuActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        List<CentroDeReciclaje> cartoneros = new ArrayList<CentroDeReciclaje>();
        cartoneros.add(new CentroDeReciclaje("Pedro Gonzales \nComentarios o Reclamos a: \nrecolectoresurbanos@caba.com.ar", "Recorre Av. Cordoba del 3000 al 4200", 0, "-34.5965071", "-58.4262069", null));
        cartoneros.add(new CentroDeReciclaje("Maria y Jose Perez\nComentarios o Reclamos a: \nrecolectoresurbanos@caba.com.ar", " Recorre Av. Medrano del 0 al 1000", 0, "-34.5995136", "-58.4204505", null));
        cartoneros.add(new CentroDeReciclaje("Luis y Mario Gomez \nComentarios o Reclamos a: \nrecolectoresurbanos@caba.com.ar", "Recorre Araoz del o al 800", 0, "-34.5978117", "-58.4337690", null));
        cartoneros.add(new CentroDeReciclaje("Manuel Gutierrez, Teodoro Ludica y Carlos Dietrich \nComentarios o Reclamos a: \nrecolectoresurbanos@caba.com.ar", "Recorre Av. Estado de Israel desde el 3800 al 4700", 0, "-34.5997474", "-58.4301478", null));
        ((LinearLayout) this.findViewById(R.id.mapa_activity)).addView(new MapaCentrosView(this, cartoneros));
        
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
}
