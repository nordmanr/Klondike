package com.utopple.code.klondike;

import android.content.Context;
import android.widget.RelativeLayout;

public class CardTappable extends Tappable {
	private Card card;
	private CardLayout cardLayout;
	// protected RelativeLayout tapRegion; // this is inherited btw

	public CardTappable(Context context) {
		super(context);
		cardLayout = new CardLayout(context);
	}

	public CardTappable(Context context, Card card) {
		super(context);
		this.card = card;
		cardLayout = new CardLayout(context, card);
		this.addView(cardLayout);

		tapRegion.setBackgroundDrawable(DrawableArea.getRandomBorder());

		this.addView(tapRegion);
		RelativeLayout.LayoutParams layoutParams = new LayoutParams(GLOBAL_VARS.widthOfCard, GLOBAL_VARS.heightOfCard);
		layoutParams.addRule(ALIGN_START);
		layoutParams.addRule(ALIGN_TOP);
		tapRegion.setLayoutParams(layoutParams);


	}

	public void setCard(Card card) {
		this.card = card;
		this.cardLayout.setCard(card);
	}
	public Card getCard() {
		return card;
	}
}
