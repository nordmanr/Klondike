package com.utopple.code.klondike;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class ClickableArea extends RelativeLayout {

	public ClickableArea(Context context){
		super(context);
		setId(generateViewId());
		this.setBackground(genRandomColorBorder());
	}
	public ClickableArea(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public static GradientDrawable genRandomColorBorder(){
		GradientDrawable gd = new GradientDrawable();
		gd.setColor(0x00000000); // Changes this drawbale to use a single color instead of a gradient
		gd.setCornerRadius(0);
		gd.setStroke(10, DrawableArea.genRandomColor());

		return gd;
	}
}
