package com.utopple.code.klondike;

import android.content.Context;

public class TalonArea extends DrawableArea {
	public TalonArea(Context context) {
		super(context);
	}

	public CardTapLayout pop(){
		return super.pop();
	}

	public void push(CardTapLayout pushing){
		super.push(pushing);
		alignView(pushing);
	}

	private void alignView(CardTapLayout toAlign){
		LayoutParams layoutParams = new LayoutParams(GLOBAL_VARS.widthOfCard, GLOBAL_VARS.heightOfCard);
		layoutParams.addRule(ALIGN_PARENT_START);
		layoutParams.addRule(ALIGN_PARENT_LEFT);

		toAlign.setLayoutParams(layoutParams);
	}

}
