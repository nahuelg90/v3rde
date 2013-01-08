package com.proyecto.v3rd.ui.view;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class ReverseImageButton extends ImageButton implements ReversableButton{
	
	public ReverseImageButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	public ReverseImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	protected int selectedColor;
	protected int notSelectedColor;
	protected List<ReversableButton> otherButtons;
	protected Context context;


	public void init(int selectedColor, int notSelectedColor, 
			List<ReversableButton>otherButtons)
	{
		this.selectedColor = selectedColor;
		this.notSelectedColor = notSelectedColor;
		this.otherButtons = otherButtons;
	}
	
	public void changeBackgrounds()
	{
		this.setSelected();
		for(ReversableButton button : otherButtons){
			if (button != this){
				button.setNotSelected();
			}
		}
	}
	
	public void setSelected()
	{
		this.setBackgroundColor(context.getApplicationContext().
				getResources().getColor(selectedColor));
		
		this.setEnabled(false);
	}
	
	public void setNotSelected()
	{
		this.setBackgroundColor(context.getApplicationContext().
				getResources().getColor(notSelectedColor));
		this.setEnabled(true);
	}

}
