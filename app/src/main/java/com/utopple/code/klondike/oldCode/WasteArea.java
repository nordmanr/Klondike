package com.utopple.code.klondike.oldCode;

import android.content.Context;
import android.widget.RelativeLayout;

import com.utopple.code.klondike.CardLayout;
import com.utopple.code.klondike.CardTapLayout;
import com.utopple.code.klondike.DrawableArea;
import com.utopple.code.klondike.GLOBAL_VARS;


public class WasteArea extends DrawableArea {
	public WasteArea(Context context) {
		super(context);
	}

	public void addCardLayout(CardTapLayout cardTapLayout){
		super.addCardLayout(cardTapLayout);

		LayoutParams layoutParams = new LayoutParams(GLOBAL_VARS.widthOfCard, GLOBAL_VARS.heightOfCard);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

		cardTapLayout.setLayoutParams(layoutParams);
	}

	public void removeCardLayout(CardTapLayout cardTapLayout){
		super.removeCardLayout(cardTapLayout);
	}
}
