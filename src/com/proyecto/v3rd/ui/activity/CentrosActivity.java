package com.proyecto.v3rd.ui.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.proyecto.v3rd.R;
import com.proyecto.v3rd.ui.view.MapaCentrosView;

import core.domain.V3RDApplication;

public class CentrosActivity extends MenuActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        ((LinearLayout) this.findViewById(R.id.mapa_activity)).addView(new MapaCentrosView(this, V3RDApplication.centrosDeReciclaje));
        
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
	
	
	
	
	/*
	private  Context context;
	private MapView mapa;
	private MapController mapController;
	private LocationManager lManager;
	private String provider;
	private LocationListener locationListener;
	private boolean enabled ;
	private GeoPoint point;
	List<Overlay> mapOverlays;
	private MeItemizedOverlay meOverlay;
	private Drawable meMarker;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
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
		
		point = new GeoPoint(19240000,-99120000);
		OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!", "I'm in Mexico City!");
		itemizedoverlay.addOverlay(overlayitem);
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
				mapController.animateTo(point);
				mapController.setZoom(17);
			}
		
	}
	
	  @Override
	  public void onResume() {
		  
		lManager.requestLocationUpdates(provider, 400, 1, locationListener);
	    if(!enabled){
			mapController.animateTo(point);
			mapController.setZoom(17);
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
	
*/
}
