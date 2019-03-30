package com.utopple.code.klondike;

import android.view.View;
import android.view.View.OnClickListener;

public class CardLayoutHandler implements OnClickListener {
	/*
			General handler for all cards

		 */

	public CardLayoutHandler() {
	}

	@Override
	public void onClick(View v) {
		if(TableDraw.aCardIsSelected){
			//moveCardLayout(moveCard, (CardLayout)v);
			TableDraw.aCardIsSelected = false;
		}else{
			TableDraw.moveCard = (CardLayout)v;
			TableDraw.aCardIsSelected = true;
		}
	}
}
