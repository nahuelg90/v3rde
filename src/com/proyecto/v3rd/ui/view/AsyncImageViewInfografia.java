package com.proyecto.v3rd.ui.view;


import android.content.Context;
import android.util.AttributeSet;

import com.proyecto.v3rd.R;

public class AsyncImageViewInfografia extends AsyncImageView {

	public AsyncImageViewInfografia(Context context, AttributeSet attr) {
		super(context, attr);
		this.setDefaultImage(context.getResources().getDrawable(R.drawable.infografia_generica));
	}

}
