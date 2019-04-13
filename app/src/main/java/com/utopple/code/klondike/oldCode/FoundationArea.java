package com.utopple.code.klondike.oldCode;

import android.content.Context;
import android.widget.RelativeLayout;

import com.utopple.code.klondike.CardLayout;
import com.utopple.code.klondike.DrawableArea;

public class FoundationArea extends DrawableArea {
	public FoundationArea(Context context) {
		super(context);
	}

	public void draw() {
		CardLayout currentCardLayout;
		LayoutParams relativeParams;

		// if there are cards in the talon
		if(! cardTapLayouts.isEmpty()){
			currentCardLayout = cardTapLayouts.peek();

			// set up card
			currentCardLayout.drawCard(TableDrawer.widthOfCard, TableDrawer.heightOfCard);
			// positioning
			relativeParams = new RelativeLayout.LayoutParams(TableDrawer.widthOfCard, TableDrawer.heightOfCard);
			relativeParams.addRule(RelativeLayout.ALIGN_TOP);
			relativeParams.addRule(RelativeLayout.ALIGN_START);

			// display card
			addView(currentCardLayout, relativeParams);
		}
	}

	public void updateDraw() {
		removeAllViews();
		draw();
	}
}
