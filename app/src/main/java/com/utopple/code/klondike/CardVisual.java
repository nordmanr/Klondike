package com.utopple.code.klondike;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CardVisual extends LinearLayout {
	private Card card;
	TextView label1, label2;
	ImageView suitImage;

	public CardVisual(Context context) {
		super(context);
	}

	public CardVisual(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public CardVisual(Context context, Card card){
		super(context);
		this.card = card;

		initLayout(GLOBAL_VARS.widthOfCard, GLOBAL_VARS.heightOfCard);
	}

	public void setCard(Card card) {
		this.card = card;
	}
	public Card getCard() {
		return card;
	}

	public void initLayout(int widthOfCard, int heightOfCard){
		LinearLayout.LayoutParams suitLayout, cardLayout;

		label1 = new TextView(this.getContext());
		label2 = new TextView(this.getContext());
		suitImage = new ImageView(this.getContext());
		suitLayout = new LinearLayout.LayoutParams(widthOfCard, (int)(heightOfCard *.45));
		cardLayout = new LinearLayout.LayoutParams(widthOfCard, heightOfCard);


		label1.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);	//	Align
		label1.setGravity(Gravity.END);
		label1.setWidth(widthOfCard);	//	width

		switch (card.getValue()){	//	label
			case 0:
				label1.setText("");
				label2.setText("");
				break;
			case 1:
				label1.setText("A ");
				label2.setText(" A");
				break;
			case 11:
				label1.setText("J ");
				label2.setText(" J");
				break;
			case 12:
				label1.setText("Q ");
				label2.setText(" Q");
				break;
			case 13:
				label1.setText("K ");
				label2.setText(" K");
				break;
			default:
				label1.setText(String.valueOf(card.getValue()).concat(" "));
				label2.setText(" ".concat(String.valueOf(card.getValue())));
				break;
		}

		label2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);	//	Align
		label2.setGravity(Gravity.START);
		label2.setWidth(widthOfCard);	//	width

		//color
		if(card.isRed()){
			label1.setTextColor(0xffff0000);
			label2.setTextColor(0xffff0000);
		}

		switch (card.getSuit()){	//	suit image
			case 'd':
				suitImage.setImageResource(R.drawable.diamond);
				break;
			case 'h':
				suitImage.setImageResource(R.drawable.heart);
				break;
			case 's':
				suitImage.setImageResource(R.drawable.spade);
				break;
			case 'c':
				suitImage.setImageResource(R.drawable.club);
				break;
			case 'p':
				suitImage.setImageResource(R.drawable.reset);
				break;
		}
		suitImage.setLayoutParams(suitLayout);
		suitImage.setForegroundGravity(Gravity.CENTER_HORIZONTAL);
		suitImage.setScaleType(ImageView.ScaleType.FIT_CENTER);

		// Add to view
		if(card.isFaceUp() || MainActivity.DEBUG_FLAG){
			this.addView(label1);
			this.addView(suitImage);
			this.addView(label2);
			this.setBackgroundResource(R.drawable.front_background);
		}else{
			this.setBackgroundResource(R.drawable.back_background);
		}

		this.setOrientation(LinearLayout.VERTICAL);

		this.setLayoutParams(cardLayout);
	}

	public void flip(){
		if(card.isFaceUp()){
			card.setFaceUp(false);
			this.removeView(label1);
			this.removeView(label2);
			this.removeView(suitImage);
			this.setBackgroundResource(R.drawable.back_background);
		}else{
			card.setFaceUp(true);
			this.addView(label1);
			this.addView(suitImage);
			this.addView(label2);
			this.setBackgroundResource(R.drawable.front_background);
		}
	}
}
