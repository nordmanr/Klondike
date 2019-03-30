package com.utopple.code.klondike;

import android.content.Context;
import android.widget.RelativeLayout;

import java.util.Iterator;

public class TableauArea extends DrawableArea {
	public TableauArea(Context context) {
		super(context);
	}

	public void draw() {
		Iterator<CardLayout> iter;
		CardLayout currentCardLayout;
		LayoutParams relativeParams;
		int moveVert;

		iter = cardLayouts.iterator();
		moveVert = 0;

		while (iter.hasNext()) {
			// get next
			currentCardLayout = iter.next();

			// set up card
			currentCardLayout.drawCard(TableDrawer.widthOfCard, TableDrawer.heightOfCard);
			// postioning
			relativeParams = new RelativeLayout.LayoutParams(TableDrawer.widthOfCard, TableDrawer.heightOfCard);
			relativeParams.addRule(RelativeLayout.ALIGN_TOP);
			relativeParams.addRule(RelativeLayout.ALIGN_START);
			relativeParams.topMargin = moveVert;

			// display card
			addView(currentCardLayout, relativeParams);

			// adjust for next card
			moveVert += (TableDrawer.heightOfCard / 5);  // show top fifth of cards beneath top card
		}
	}

	public void updateDraw() {
		removeAllViews();
		draw();
	}
}