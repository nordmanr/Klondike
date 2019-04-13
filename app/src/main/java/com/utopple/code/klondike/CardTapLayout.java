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


	public void setCard(Card card) {
		this.card = card;
		this.cardLayout.setCard(card);
	}
	public Card getCard() {
		return card;
	}

	public void draw(int widthCard, int heightCard, int widthTap, int heightTap){
		cardLayout.drawCard(widthCard, heightCard);
	}
}
