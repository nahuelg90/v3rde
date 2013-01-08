package com.proyecto.v3rd.ui.view;

import java.util.List;

public interface ReversableButton {

	public void init(int selectedColor, int notSelectedColor, 
			List<ReversableButton>otherButtons);
	
	public void changeBackgrounds();
	
	public void setSelected();
	
	public void setNotSelected();
}
