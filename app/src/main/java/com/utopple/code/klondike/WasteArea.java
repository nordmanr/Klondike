package com.utopple.code.klondike;

import android.content.Context;
import android.widget.RelativeLayout;

import java.util.Iterator;

public class WasteArea extends DrawableArea {
	public WasteArea(Context context) {
		super(context);
	}

	public void draw() {
		CardLayout currentCardLayout;
		LayoutParams relativeParams;

		// if there are cards in the talon
		if(! cardLayouts.isEmpty()){
			currentCardLayout = cardLayouts.peek();

			// set up card
			currentCardLayout.drawCard(TableDrawer.widthOfCard, TableDrawer.heightOfCard);
			// positioning
			relativeParams = new RelativeLayout.LayoutParams(TableDrawer.widthOfCard, TableDrawer.heightOfCard);
			relativeParams.addRule(RelativeLayout.ALIGN_TOP);
			relativeParams.addRule(RelativeLayout.ALIGN_START);

			// display card
			addView(currentCardLayout, relativeParams);
			currentCardLayout.setOnClickListener(new CardLayoutHandler());
		}
	}

	public void updateDraw() {
		removeAllViews();
		draw();
	}
}
