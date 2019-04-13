package com.utopple.code.klondike;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.Stack;

public class DrawableArea extends RelativeLayout {
	protected Stack<CardLayout> cardLayouts;

	public DrawableArea(Context context){
		super(context);

		// sizing ViewGroup.LayoutParams.WRAP_CONTENT
		RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(TableDrawer.widthOfCard, ViewGroup.LayoutParams.WRAP_CONTENT);
		this.setMinimumHeight(TableDrawer.heightOfCard);

		if(MainActivity.DEBUG_FLAG){
			this.setBackgroundColor(genRandomColor());
		}

		this.setLayoutParams(relativeParams);


		setId(generateViewId());

		cardLayouts = new Stack<>();
	}
	public DrawableArea(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);

		cardLayouts = new Stack<>();
	}

	public void addCardLayout(CardLayout cardLayout){
		cardLayouts.add(cardLayout);
	}

	public void removeCardLayout(CardLayout cardLayout){
		cardLayouts.remove(cardLayout);
	}

	public CardLayout pop(){
		return cardLayouts.pop();
	}
	public CardLayout peek(){
		return cardLayouts.peek();
	}

	public static int genRandomColor(){
		return (int)(Math.random()*0xffffff + 0xff000000);
	}

	public static GradientDrawable genRandomColorBorder(){
		GradientDrawable gd = new GradientDrawable();
		gd.setColor(0x00000000); // Changes this drawbale to use a single color instead of a gradient
		gd.setCornerRadius(0);
		gd.setStroke(10, genRandomColor());

		return gd;
	}

}
