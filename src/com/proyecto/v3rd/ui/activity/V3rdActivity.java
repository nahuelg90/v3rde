package com.proyecto.v3rd.ui.activity;


import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.android.maps.MapActivity;

public abstract class V3rdActivity extends MapActivity {
	
	protected ProgressDialog loading;
    /** Called when the activity is first created. */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLoading();
    }
    
    protected abstract void initLoading();
}