package com.utopple.code.klondike;

import android.content.Context;
import android.widget.RelativeLayout;

public class TapLayout extends RelativeLayout {
	protected RelativeLayout tapRegion;

	/*	Areas that are tap-able and do stuff when tapped
	* */
	public TapLayout(Context context) {
		super(context);
		tapRegion = new RelativeLayout(context);
	}
}
