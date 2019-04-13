package com.utopple.code.klondike;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.Stack;

public class DrawableArea extends RelativeLayout {
	protected Stack<CardTapLayout> cardTapLayouts;

	public DrawableArea(Context context){
		super(context);
		cardTapLayouts = new Stack<>();

		// sizing ViewGroup.LayoutParams.WRAP_CONTENT
		RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(GLOBAL_VARS.widthOfCard, ViewGroup.LayoutParams.WRAP_CONTENT);
		this.setMinimumHeight(GLOBAL_VARS.heightOfCard);

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

	public CardTapLayout pop(){
		CardTapLayout popped = cardTapLayouts.pop();
		this.removeView(popped);

		return popped;
	}

	public void push(CardTapLayout pushing){
		this.addView(pushing);
		cardTapLayouts.push(pushing);
	}

	public static int genRandomColor(){
		return (int)(Math.random()*0xffffff + 0xff000000);
	}

}
