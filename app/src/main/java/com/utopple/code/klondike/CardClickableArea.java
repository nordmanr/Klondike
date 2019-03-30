package com.utopple.code.klondike;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class CardClickableArea extends ClickableArea {
	public CardClickableArea(Context context){
		super(context);
	}
	public CardClickableArea(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public void initializeClickable(TableDrawer tableDrawer, CardLayout cardLayout){
		this.setOnClickListener(new CardClickableArea.CardHandler(tableDrawer, cardLayout));

	}

	public class CardHandler implements View.OnClickListener {
	/*
		Handler for the cards in general
	 */

		private TableDrawer tableDrawer;
		private CardLayout cardLayout;

		CardHandler(TableDrawer tableDrawer, CardLayout cardLayout) {
			this.tableDrawer = tableDrawer;
			this.cardLayout = cardLayout;
		}

		@Override
		public void onClick(View v) {
			if (TableDrawer.moveCard != null) {
				TableDrawer.moveCard = cardLayout;
			}else{
				// move it
			}

			if(MainActivity.DEBUG_FLAG){
				Toast.makeText(getContext(), "Card press\t"+cardLayout.getCard().toString(), Toast.LENGTH_SHORT).show();
			}
		}
	}
}
