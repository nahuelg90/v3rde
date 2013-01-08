package com.proyecto.v3rd.ui.view;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.proyecto.v3rd.R;

import core.domain.CentroDeReciclaje;

public class MapaCentrosView extends LinearLayout {
	
	private  Context context;
	private MapView mapa;
	private MapController mapController;
	private LocationManager lManager;
	private String provider;
	private LocationListener locationListener;
	private boolean enabled ;
	List<Overlay> mapOverlays;
	private MeItemizedOverlay meOverlay;
	private Drawable meMarker;
	private List<CentroDeReciclaje> centros;
	
	public MapaCentrosView(Context context, List<CentroDeReciclaje> centros) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.mapa_centros_view, this,
				true);
		this.context = context;
		this.centros = centros;
		findAndInitViews();
	}

	private void findAndInitViews() {
		meMarker = context.getResources().getDrawable(R.drawable.marker_me);
		mapa = (MapView) this.findViewById(R.id.mapa_inforgrafia);
		mapa.setBuiltInZoomControls(true);
		mapController = mapa.getController();
		
		mapOverlays = mapa.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
		CentrosItemizedOverlay itemizedoverlay = new CentrosItemizedOverlay(drawable, mapa);
		
		for(CentroDeReciclaje centro : centros){
			double latitud = Double.parseDouble(centro.getLatitud());
			double longitud = Double.parseDouble(centro.getLongitud());
			GeoPoint nPoint = new GeoPoint((int)(latitud * 1e6), (int)( longitud *1e6));
			OverlayItem overlayitem = new OverlayItem(nPoint, centro.getDescripcion(), centro.getDireccion());
			itemizedoverlay.addOverlay(overlayitem);
			
		}
		
		mapOverlays.add(itemizedoverlay);
		
		lManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		 locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		    	actualizarPosicion(location);
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		  };
	  Criteria criteria = new Criteria();
	  provider = lManager.getBestProvider(criteria, false); 	  
	  enabled = lManager
			  .isProviderEnabled(provider);
			if (!enabled) {
				 final AlertDialog dialog;
				 AlertDialog.Builder builder = new AlertDialog.Builder(context);
			        builder.setMessage("La geolocalización está desactivada, desea activarla?")
			               .setPositiveButton("Si", new DialogInterface.OnClickListener() {
			                   public void onClick(DialogInterface dialog, int id) {
			                	   Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			                	   context.startActivity(intent);
			                   }
			               })
			               .setNegativeButton("No", new DialogInterface.OnClickListener() {
			                   public void onClick(DialogInterface dialog, int id) {
			                       dialog.dismiss();
			                   }
			               });
			       dialog = builder.create();
			       dialog.show();
			} else {
			    lManager.requestLocationUpdates(provider, 0, 0, locationListener);
			    actualizarPosicion(lManager.getLastKnownLocation(provider));
			}
	}
	
	  public void onResume() {
		  
		lManager.requestLocationUpdates(provider, 400, 1, locationListener);
	    if(!enabled){
			
			actualizarPosicion(lManager.getLastKnownLocation(provider));
		 } 
	  }
	  
	  private void actualizarPosicion(Location location){
		    if(meOverlay != null){
		    	mapOverlays.remove(meOverlay);
		    }
		    meOverlay = new MeItemizedOverlay(meMarker, context);
			GeoPoint pointMe = new GeoPoint((int)(location.getLatitude()* 1e6), (int)(location.getLongitude() *1e6));
			OverlayItem overlayMe = new OverlayItem(pointMe, "", "");
			meOverlay.addOverlay(overlayMe);
			mapOverlays.add(meOverlay);
			mapController.animateTo(pointMe);
			mapController.setZoom(17);
	  }
	
	

}
