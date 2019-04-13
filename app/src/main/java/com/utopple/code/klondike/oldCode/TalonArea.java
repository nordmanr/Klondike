package com.utopple.code.klondike.oldCode;

import android.content.Context;
import android.widget.RelativeLayout;

import com.utopple.code.klondike.CardLayout;
import com.utopple.code.klondike.DrawableArea;

public class TalonArea extends DrawableArea {
	public TalonArea(Context context) {
		super(context);

		for(int i=0; i<this.cardTapLayouts.size(); i++){
			this.addView(cardTapLayouts.get(i));
		}
	}


}
