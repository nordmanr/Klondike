package com.utopple.code.klondike;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

import java.util.Iterator;

@SuppressWarnings("FieldCanBeLocal")
public class TableDraw {
	//public static CardTapLayout moveCard;	// which card we are trying to move (null when none is selected)

	private MainActivity context;
	public TalonArea talonArea;
	public WasteArea wasteArea;
	public TableauArea[] tableauAreas;
	public FoundationArea[] foundationAreas;

	public TableDraw(MainActivity context){
		this.context = context;

		setSizing();	// sizing for cards

		/* Initialize */
		talonArea = new TalonArea(context);
		wasteArea = new WasteArea(context);
		tableauAreas = new TableauArea[7];
		for(int i=0; i<tableauAreas.length; i++){
			tableauAreas[i] = new TableauArea(context);
		}
		foundationAreas = new FoundationArea[4];
		for(int i=0; i<foundationAreas.length; i++){
			foundationAreas[i] = new FoundationArea(context);
		}

		setAlignment();


		// Start game
		restart();
	}


	private void restart(){
		// Create CardTapLayouts from deck	--------------------------------------------------------
		Deck deck = new Deck();
		deck.shuffle();

		CardTappable currentCardTapLayout;

		// Create every card and put it in the Talon
		for(int i=0; i<52; i++){
			currentCardTapLayout = new CardTappable(context, deck.getAllCards()[i]);
			talonArea.push(currentCardTapLayout);
		}

		// setup the talons
		for(int i=0; i<7; i++){
			for(int j=0; j<=i; j++){
				tableauAreas[i].forcePush(talonArea.pop());
			}
			tableauAreas[i].peek().flip();
		}
	}

	public void refillTalon(){
		Iterator<CardTappable> iter = wasteArea.cardTapLayouts.iterator();
		CardTappable current;

		while(iter.hasNext()){
			iter.next();

			talonArea.push(wasteArea.pop());
		}
	}

	private void setSizing(){
		// Sizing	--------------------------------------------------------------------------------
		GLOBAL_VARS.viewWidth = context.getResources().getDisplayMetrics().widthPixels;
		GLOBAL_VARS.viewHeight = context.getResources().getDisplayMetrics().heightPixels;

		GLOBAL_VARS.widthOfCard = GLOBAL_VARS.viewWidth/8;
		GLOBAL_VARS.heightOfCard = ((int) (GLOBAL_VARS.widthOfCard * 1.5));

		GLOBAL_VARS.margin = ((GLOBAL_VARS.viewWidth-7*GLOBAL_VARS.widthOfCard)-dpToPx(context,20))/(7);	// Space between cards
	}
	private void setAlignment(){
		/* Positioning */
		// Talon
		((RelativeLayout) context.findViewById(R.id.loc_talon)).addView(talonArea);
		// Waste
		((RelativeLayout) context.findViewById(R.id.loc_waste)).addView(wasteArea);
		// Tableaus (7)
		((RelativeLayout) context.findViewById(R.id.loc_tableau1)).addView(tableauAreas[0]);
		((RelativeLayout) context.findViewById(R.id.loc_tableau2)).addView(tableauAreas[1]);
		((RelativeLayout) context.findViewById(R.id.loc_tableau3)).addView(tableauAreas[2]);
		((RelativeLayout) context.findViewById(R.id.loc_tableau4)).addView(tableauAreas[3]);
		((RelativeLayout) context.findViewById(R.id.loc_tableau5)).addView(tableauAreas[4]);
		((RelativeLayout) context.findViewById(R.id.loc_tableau6)).addView(tableauAreas[5]);
		((RelativeLayout) context.findViewById(R.id.loc_tableau7)).addView(tableauAreas[6]);
		// Foundations (4)
		((RelativeLayout) context.findViewById(R.id.loc_foundation1)).addView(foundationAreas[0]);
		((RelativeLayout) context.findViewById(R.id.loc_foundation2)).addView(foundationAreas[1]);
		((RelativeLayout) context.findViewById(R.id.loc_foundation3)).addView(foundationAreas[2]);
		((RelativeLayout) context.findViewById(R.id.loc_foundation4)).addView(foundationAreas[3]);
	}

	private int dpToPx(Context context, int dp) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	}
}
