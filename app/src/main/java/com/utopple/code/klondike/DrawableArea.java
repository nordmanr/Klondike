package com.utopple.code.klondike;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.Stack;

public class DrawableArea extends RelativeLayout {
	protected Stack<CardRegion> cardRegions;

	public DrawableArea(Context context){
		super(context);
		cardRegions = new Stack<>();

		LayoutParams relativeParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		this.setMinimumHeight(GLOBAL_VARS.heightOfCard);
		this.setMinimumWidth(GLOBAL_VARS.widthOfCard);

		if(MainActivity.DEBUG_FLAG){
			this.setBackgroundColor(genRandomColor());
		}

		this.setLayoutParams(relativeParams);


		setId(generateViewId());
	}
	public DrawableArea(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);

		cardRegions = new Stack<>();
	}

	public CardRegion pop(){
		CardRegion popped = cardRegions.pop();
		this.removeView(popped);

		return popped;
	}

	public void push(CardRegion pushing){
		this.addView(pushing);
		cardRegions.push(pushing);
	}

	public CardRegion peek(){
		return cardRegions.peek();
	}

	public boolean contains(CardRegion cardRegion){
		return cardRegions.contains(cardRegion);
	}

	private void alignView(CardRegion toAlign){
		LayoutParams layoutParams = new LayoutParams(GLOBAL_VARS.widthOfCard, GLOBAL_VARS.heightOfCard);
		layoutParams.addRule(ALIGN_PARENT_START);
		layoutParams.addRule(ALIGN_PARENT_LEFT);

		toAlign.setLayoutParams(layoutParams);
	}

	public static int genRandomColor(){
		return (int)(Math.random()*0xffffff + 0xff000000);
	}

	public static GradientDrawable getRandomBorder(){
		GradientDrawable gd = new GradientDrawable();
		gd.setColor(0x00000000);
		gd.setStroke(10, genRandomColor());

		return gd;
	}

}
