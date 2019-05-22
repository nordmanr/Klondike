package com.utopple.code.klondike;

import android.content.Context;
import android.widget.RelativeLayout;

public class CardTappable extends RelativeLayout {
	private Card card;
	private CardLayout cardLayout;
	private Tappable cardTapRegion;

	public CardTappable(Context context) {
		// Don't use this method
		super(context);
		cardLayout = new CardLayout(context);
		cardTapRegion = new Tappable(context);
	}

	public CardTappable(Context context, Card card) {
		super(context);
		this.card = card;
		cardLayout = new CardLayout(context, card);
		cardTapRegion = new Tappable(context);
		this.addView(cardLayout);
		this.addView(cardTapRegion);
	}

	public void setCard(Card card) {
		this.card = card;
		this.cardLayout.setCard(card);
	}
	public Card getCard() {
		return card;
	}

	public void flip(){
		cardLayout.flip();
	}

	public void resizeTapRegion(){
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)cardTapRegion.getLayoutParams();
		layoutParams.height=GLOBAL_VARS.viewHeight;
	}
}
