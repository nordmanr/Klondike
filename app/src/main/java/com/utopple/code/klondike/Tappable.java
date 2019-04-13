package com.utopple.code.klondike;

import android.content.Context;
import android.widget.RelativeLayout;

public class Tappable extends RelativeLayout {
	protected RelativeLayout tapRegion;

	/*	Areas that are tap-able and do stuff when tapped
	* */
	public Tappable(Context context) {
		super(context);
		tapRegion = new RelativeLayout(context);
	}
}
