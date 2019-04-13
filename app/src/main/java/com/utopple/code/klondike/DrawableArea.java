package com.utopple.code.klondike;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.utopple.code.klondike.CardTapLayout;
import com.utopple.code.klondike.MainActivity;
import com.utopple.code.klondike.oldCode.TableDrawer;

import java.util.Stack;

public class DrawableArea extends RelativeLayout {
	protected Stack<CardTapLayout> cardTapLayouts;

	public DrawableArea(Context context){
		super(context);

		// sizing ViewGroup.LayoutParams.WRAP_CONTENT
		RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(GLOBAL_VARS.widthOfCard, ViewGroup.LayoutParams.WRAP_CONTENT);
		this.setMinimumHeight(GLOBAL_VARS.heightOfCard);

		if(MainActivity.DEBUG_FLAG){
			this.setBackgroundColor(genRandomColor());
		}

		this.setLayoutParams(relativeParams);


		setId(generateViewId());

		cardTapLayouts = new Stack<>();
	}
	public DrawableArea(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);

		cardTapLayouts = new Stack<>();
	}

	public void addCardLayout(CardTapLayout cardTapLayout){
		this.addView(cardTapLayout);
		cardTapLayouts.add(cardTapLayout);
	}

	public void removeCardLayout(CardTapLayout cardTapLayout){
		this.removeView(cardTapLayout);
		cardTapLayouts.remove(cardTapLayout);
	}

	public static int genRandomColor(){
		return (int)(Math.random()*0xffffff + 0xff000000);
	}

}
