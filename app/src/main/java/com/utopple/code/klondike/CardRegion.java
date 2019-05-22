package com.utopple.code.klondike;

import android.content.Context;
import android.widget.RelativeLayout;

public class CardRegion extends RelativeLayout {
	private Card card;
	private CardVisual cardVisual;
	private CardTappable cardTappable;

	public CardRegion(Context context) {
		// Don't use this method
		super(context);
		cardVisual = new CardVisual(context);
		cardTappable = new CardTappable(context);
	}

	public CardRegion(Context context, Card card) {
		super(context);
		this.card = card;
		cardVisual = new CardVisual(context, card);
		cardTappable = new CardTappable(context);
		this.addView(cardVisual);
		this.addView(cardTappable);
	}

	public void setCard(Card card) {
		this.card = card;
		this.cardVisual.setCard(card);
	}
	public Card getCard() {
		return card;
	}

	public void flip(){
		cardVisual.flip();
	}

	public void resizeTapRegion(){
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) cardTappable.getLayoutParams();
		layoutParams.height=GLOBAL_VARS.viewHeight;
	}
}
