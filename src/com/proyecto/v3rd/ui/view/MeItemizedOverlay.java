package com.proyecto.v3rd.ui.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MeItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	
	public MeItemizedOverlay(Drawable defaultMarker) {
		super(defaultMarker);
		// TODO Auto-generated constructor stub
	}
	
	public MeItemizedOverlay(Drawable defaultMarker, Context context) {
		  super(boundCenterBottom(defaultMarker));
		  this.mContext = context;
	}

	
	public void addOverlay(OverlayItem overlay) {
	    this.mOverlays.add(overlay);
	    this.populate();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		return this.mOverlays.get(i);
	}

	@Override
	public int size() {
		return this.mOverlays.size();
	}

	@Override
	protected boolean onTap(int index) {
	  return true;
	}
}