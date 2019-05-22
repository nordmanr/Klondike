package com.utopple.code.klondike;

import android.content.Context;
import android.widget.RelativeLayout;

public class Tappable extends RelativeLayout {
	/*	Areas that are tap-able and do stuff when tapped
	* */
	public Tappable(Context context) {
		super(context);

		RelativeLayout.LayoutParams layoutParams = new LayoutParams(GLOBAL_VARS.widthOfCard, (int)(2*GLOBAL_VARS.heightOfCard));
		layoutParams.addRule(ALIGN_START);
		layoutParams.addRule(ALIGN_TOP);
		this.setLayoutParams(layoutParams);

		if(MainActivity.DEBUG_FLAG){
			this.setBackgroundDrawable(DrawableArea.getRandomBorder());
		}
	}
}
