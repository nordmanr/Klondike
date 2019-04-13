package com.utopple.code.klondike;

import android.content.Context;

public class CardTapLayout extends TapLayout {
	private Card card;
	private CardLayout cardLayout;

	public CardTapLayout(Context context) {
		super(context);
		cardLayout = new CardLayout(context);
	}


	public void setCard(Card card) {
		this.card = card;
		this.cardLayout.setCard(card);
	}
	public Card getCard() {
		return card;
	}

	public void draw(){

	}
}
