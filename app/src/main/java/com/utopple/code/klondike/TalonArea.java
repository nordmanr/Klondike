package com.utopple.code.klondike;

import android.content.Context;
import android.widget.RelativeLayout;

import com.utopple.code.klondike.CardLayout;
import com.utopple.code.klondike.DrawableArea;

public class TalonArea extends DrawableArea {
	public TalonArea(Context context) {
		super(context);
	}

	public void addCardLayout(CardTapLayout cardTapLayout){
		this.addView(cardTapLayout);
		cardTapLayouts.push(cardTapLayout);
		alignView(cardTapLayout);
	}

	public void removeCardLayout(CardTapLayout cardTapLayout){
		this.removeView(cardTapLayout);
		cardTapLayouts.remove(cardTapLayout);
		alignView(cardTapLayout);
	}

	public CardTapLayout pop(){
		CardTapLayout popped = cardTapLayouts.pop();
		this.removeView(popped);

		return popped;
	}

	public void push(CardTapLayout pushing){
		this.addView(pushing);
		cardTapLayouts.push(pushing);
		alignView(pushing);
	}

	private void alignView(CardTapLayout toAlign){
		LayoutParams layoutParams = new LayoutParams(GLOBAL_VARS.widthOfCard, GLOBAL_VARS.heightOfCard);
		layoutParams.addRule(ALIGN_PARENT_START);
		layoutParams.addRule(ALIGN_PARENT_LEFT);

		toAlign.setLayoutParams(layoutParams);
	}

}
