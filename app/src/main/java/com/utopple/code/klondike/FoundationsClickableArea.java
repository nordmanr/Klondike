package com.utopple.code.klondike;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import java.util.EmptyStackException;

public class FoundationsClickableArea extends ClickableArea {
	public FoundationsClickableArea(Context context){
		super(context);
	}
	public FoundationsClickableArea(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public void initializeClickable(TableDrawer tableDrawer, int col){
		this.setOnClickListener(new FoundationsHandler(tableDrawer, col));
	}

	public class FoundationsHandler implements View.OnClickListener {
	/*
		Handler for the Talon
	 */

		private TableDrawer tableDrawer;
		private int col;

		FoundationsHandler(TableDrawer tableDrawer, int col) {
			this.tableDrawer = tableDrawer;
			this.col = col;
		}

		@Override
		public void onClick(View v) {
			// none selected
			if(TableDrawer.moveCard == null){
				try {
					TableDrawer.moveCard = tableDrawer.getFoundationAreas()[col].peek();
				}catch (EmptyStackException e){
					if(MainActivity.DEBUG_FLAG){
						Toast.makeText(getContext(), "Foundation press: "+e.toString(), Toast.LENGTH_SHORT).show();
					}
				}
				return;
			}

			// a card is already selected
			// get location of it
			int[] location = tableDrawer.getTable().findCard(TableDrawer.moveCard.getCard());

			// if wrong suit
			if(TableDrawer.moveCard.getCard().getSuit() != tableDrawer.getFoundationAreas()[col].peek().getCard().getSuit()){
				try {
					TableDrawer.moveCard = tableDrawer.getFoundationAreas()[col].peek();
				}catch (EmptyStackException e) {
					if (MainActivity.DEBUG_FLAG) {
						Toast.makeText(getContext(), "Foundation press: " + e.toString(), Toast.LENGTH_SHORT).show();
					}
				}
					return;
			}

			// not next in foundation
			if(TableDrawer.moveCard.getCard().getValue() != tableDrawer.getFoundationAreas()[col].peek().getCard().getValue() + 1){
				try {
					TableDrawer.moveCard = tableDrawer.getFoundationAreas()[col].peek();
				}catch (EmptyStackException e) {
					if (MainActivity.DEBUG_FLAG) {
						Toast.makeText(getContext(), "Foundation press: " + e.toString(), Toast.LENGTH_SHORT).show();
					}
				}
				return;
			}

			// move it
			tableDrawer.getFoundationAreas()[col].addCardLayout(TableDrawer.moveCard);

			switch (location[0]){
				case 0: // talon
					tableDrawer.getTalonArea().removeCardLayout(TableDrawer.moveCard);
					tableDrawer.getTalonArea().updateDraw();
					break;
				case 1: // waste
					tableDrawer.getWasteArea().removeCardLayout(TableDrawer.moveCard);
					tableDrawer.getWasteArea().updateDraw();
					break;
				case 2: // tableau
					tableDrawer.getTableauAreas()[location[1]].removeCardLayout(TableDrawer.moveCard);
					tableDrawer.getTableauAreas()[location[1]].updateDraw();
					break;
				case 3: // foundations
					tableDrawer.getFoundationAreas()[location[1]].removeCardLayout(TableDrawer.moveCard);
					break;
			}

			tableDrawer.getFoundationAreas()[location[1]].updateDraw();

			if(MainActivity.DEBUG_FLAG){
				Toast.makeText(getContext(), "Foundation press: "+col, Toast.LENGTH_SHORT).show();
			}
		}
	}
}
