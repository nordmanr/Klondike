package com.utopple.code.klondike.oldCode;

import android.content.Context;
import android.widget.RelativeLayout;

import com.utopple.code.klondike.CardTapLayout;
import com.utopple.code.klondike.DrawableArea;
import com.utopple.code.klondike.TableauStack;

import java.util.Iterator;

public class TableauArea extends DrawableArea {
	private TableauStack cardTapLayouts;

	public TableauArea(Context context) {
		super(context);
		cardTapLayouts = new TableauStack();

		/* Create cards */
		Iterator<CardTapLayout> iter;
		CardTapLayout currentCard;
		LayoutParams relativeParams;
		int moveVert;

		iter = this.cardTapLayouts.iterator();
		moveVert = 0;

		while (iter.hasNext()) {
			// get next
			currentCard = iter.next();

			// set up card
	//		currentCard.draw(TableDrawer.widthOfCard, TableDrawer.heightOfCard);
			// postioning
			relativeParams = new RelativeLayout.LayoutParams(TableDrawer.widthOfCard, TableDrawer.heightOfCard);
			relativeParams.addRule(RelativeLayout.ALIGN_TOP);
			relativeParams.addRule(RelativeLayout.ALIGN_START);
			relativeParams.topMargin = moveVert;

			// display card
			addView(currentCard, relativeParams);

			// adjust for next card
			moveVert += (TableDrawer.heightOfCard / 5);  // show top fifth of cards beneath top card
		}
	}

	public void addCard(CardTapLayout cardTapLayout){
		cardTapLayouts.push(cardTapLayout);
	}
}