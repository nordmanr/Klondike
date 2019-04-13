package com.utopple.code.klondike;

import android.content.Context;

public class CardTapLayout extends TapLayout {
	private Card card;
	private CardLayout cardLayout;
	// protected RelativeLayout tapRegion; // this is inherited btw

	public CardTapLayout(Context context) {
		super(context);
		cardLayout = new CardLayout(context);
	}

	public CardTapLayout(Context context, Card card) {
		super(context);
		this.card = card;
		cardLayout = new CardLayout(context, card);
		this.addView(cardLayout);
	}

	public void setCard(Card card) {
		this.card = card;
		this.cardLayout.setCard(card);
	}
	public Card getCard() {
		return card;
	}
}
