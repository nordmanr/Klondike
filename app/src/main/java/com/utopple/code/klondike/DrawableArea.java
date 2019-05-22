package com.utopple.code.klondike;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.Stack;

public class DrawableArea extends RelativeLayout {
	protected Stack<CardTappable> cardTapLayouts;

	public DrawableArea(Context context){
		super(context);
		cardTapLayouts = new Stack<>();

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

		cardTapLayouts = new Stack<>();
	}

	public CardTappable pop(){
		CardTappable popped = cardTapLayouts.pop();
		this.removeView(popped);

		return popped;
	}

	public void push(CardTappable pushing){
		this.addView(pushing);
		cardTapLayouts.push(pushing);
	}

	public CardTappable peek(){
		return cardTapLayouts.peek();
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
