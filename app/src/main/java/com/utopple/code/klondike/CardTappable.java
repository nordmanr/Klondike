package com.utopple.code.klondike;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class CardTappable extends RelativeLayout {
	/*	Areas that are tap-able and do stuff when tapped
	* */
	private int type;


	// Previous card tapped
	static private CardRegion selectedCardRegion = null;



	public CardTappable(Context context) {
		super(context);

		RelativeLayout.LayoutParams layoutParams = new LayoutParams(GLOBAL_VARS.widthOfCard, (int)(2*GLOBAL_VARS.heightOfCard));
		layoutParams.addRule(ALIGN_START);
		layoutParams.addRule(ALIGN_TOP);
		this.setLayoutParams(layoutParams);

		if(MainActivity.DEBUG_FLAG){
			this.setBackgroundDrawable(DrawableArea.getRandomBorder());
		}

		setOnClickListener(new CardListener());
	}



	public class CardListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			CardRegion secondSelected;
			List<CardRegion> moving;

			if(selectedCardRegion == null) {
				selectedCardRegion = ((CardRegion) (v.getParent()));
			}else{
				secondSelected = ((CardRegion) (v.getParent()));

				moving = ((TableauArea)(selectedCardRegion.getParent())).popTo(selectedCardRegion);

				selectedCardRegion = null;

				Toast.makeText(getContext(), "Moving size: "+moving.size(), Toast.LENGTH_SHORT).show();


				((TableauArea)(secondSelected.getParent())).pushAll(moving);

			}
		}
	}
}
