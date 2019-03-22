package com.utopple.code.klondike;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class TableDraw {
	private static int viewWidth;		// Width of screen (px)
	private static int viewHeight;		// Height of screen (px)
	private static int widthOfCard = 50;		// Width of each card when drawn (in px)
	private static int heightOfCard = 75;	// Height of each card when drawn (in px)


	private static boolean aCardIsSelected;	// Flag to use when moving a card around between tableau
	private static CardLayout moveCard;	// which card we are trying to move
	private static CardLayout toHere;	// And to where we are moving it


	private Table table;
	private MainActivity context;

	private ArrayList<CardLayout> allCardLayouts;		// All CardLayouts
	private Stack<CardLayout> talonLayouts;  			// CardLayouts for all the cards in the talon
	private Stack<CardLayout>[] foundationLayouts;	// CardLayouts for all the cards in the foundations
	private ArrayList<CardLayout>[] tableauLayouts;	// CardLayouts for all the cards in the tableaus
	private Stack<CardLayout> wasteLayouts;  			// CardLayouts for all the cards in the waste

	TableDraw(Table table, MainActivity context){
		this.table = table;
		this.context = context;

		viewWidth = 0;
		viewHeight = 0;

		allCardLayouts = new ArrayList<>();
		talonLayouts = new Stack<>();
		foundationLayouts = new Stack[4];
		tableauLayouts = new ArrayList[7];
		wasteLayouts = new Stack<>();

		for(int i=0; i<4; i++){
			foundationLayouts[i] = new Stack<CardLayout>();
		}

		for(int i=0; i<Table.NUM_OF_TABLEAU; i++){
			tableauLayouts[i] = new ArrayList<CardLayout>();
		}


	}



	public void draw(){
		Iterator<CardLayout> iter;
		CardLayout cardLayout;
		LinearLayout loopTalon;
		LinearLayout[] emptyCells;
		RelativeLayout.LayoutParams relativeParams;
		int margin, moveHorz, moveVert;

		emptyCells = new LinearLayout[Table.NUM_OF_TABLEAU];


		//----------------------------------------------------------------------------------------//
		//											Sizing										  //
		//----------------------------------------------------------------------------------------//
		viewWidth = context.getResources().getDisplayMetrics().widthPixels;
		viewHeight = context.getResources().getDisplayMetrics().heightPixels;

		if(viewWidth < viewHeight){
			widthOfCard = viewWidth/(Table.NUM_OF_TABLEAU+1);
			heightOfCard = ((int) (widthOfCard * 1.5));
		}

		margin = ((viewWidth-Table.NUM_OF_TABLEAU*widthOfCard)-dpToPx(20))/(Table.NUM_OF_TABLEAU);	// Space between cards


		// CLEAR OLD LAYOUTS -----------------------------------------------------------------------
		iter = allCardLayouts.iterator();
		while (iter.hasNext()){
			cardLayout = iter.next();
			((RelativeLayout)(context).findViewById(R.id.loc_talon)).removeView(cardLayout);
			((RelativeLayout)(context).findViewById(R.id.loc_waste)).removeView(cardLayout);
			((RelativeLayout)(context).findViewById(R.id.loc_tableaus)).removeView(cardLayout);
			((RelativeLayout)(context).findViewById(R.id.loc_foundations)).removeView(cardLayout);
		}


		//----------------------------------------------------------------------------------------//
		//						Set up the layouts from based on the table						  //
		//----------------------------------------------------------------------------------------//

		allCardLayouts.clear();

		// Add layouts for Talon
		talonLayouts.clear();
		for(int i=0; i<table.getTalon().size(); i++){
			cardLayout = new CardLayout(context);
			cardLayout.setCard(table.getTalon().get(i));

			talonLayouts.add(cardLayout);	// talon list
			allCardLayouts.add(cardLayout);	// and master list
		}
		// Loop when talon is empty
		loopTalon = new LinearLayout(context);

		// Add layouts for Waste
		wasteLayouts.clear();
		for(int i=0; i<table.getWaste().size(); i++){
			cardLayout = new CardLayout(context);
			cardLayout.setCard(table.getWaste().get(i));

			wasteLayouts.add(cardLayout);	// waste list
			allCardLayouts.add(cardLayout);	// and master list
		}

		// Add layouts for Tableaus
		for(int i=0; i<Table.NUM_OF_TABLEAU; i++){
			tableauLayouts[i].clear();
			for(int j=0; j<table.getTableaus()[i].size(); j++){
				cardLayout = new CardLayout(context);
				cardLayout.setCard(table.getTableaus()[i].get(j));

				tableauLayouts[i].add(cardLayout);	// tableau list
				allCardLayouts.add(cardLayout);		// and master list
			}
		}


		for(int i=0; i<4; i++){
			foundationLayouts[i].clear();
		}
		//	Placeholders in foundations
		cardLayout = new CardLayout(context);
		cardLayout.setCard(new Card('d',0));
		foundationLayouts[0].add(cardLayout);
		allCardLayouts.add(cardLayout);
		cardLayout = new CardLayout(context);
		cardLayout.setCard(new Card('h',0));
		foundationLayouts[1].add(cardLayout);
		allCardLayouts.add(cardLayout);
		cardLayout = new CardLayout(context);
		cardLayout.setCard(new Card('c',0));
		foundationLayouts[2].add(cardLayout);
		allCardLayouts.add(cardLayout);
		cardLayout = new CardLayout(context);
		cardLayout.setCard(new Card('s',0));
		foundationLayouts[3].add(cardLayout);
		allCardLayouts.add(cardLayout);
		// Add layouts for Foundations
		for(int i=0; i<4; i++){
			for(int j=0; j<table.getFoundations()[i].size(); j++){
				cardLayout = new CardLayout(context);
				cardLayout.setCard(table.getFoundations()[i].get(j));

				foundationLayouts[i].add(cardLayout);	// talon list
				allCardLayouts.add(cardLayout);	// and master list
			}
		}





		//----------------------------------------------------------------------------------------//
		//								Actual drawing to the screen							  //
		//----------------------------------------------------------------------------------------//

		for(int i=0; i<Table.NUM_OF_TABLEAU; i++){
			((RelativeLayout)(context).findViewById(R.id.loc_talon)).removeView(emptyCells[i]);	// remove old
		}

		moveHorz = 0;
		// Underneath tableaus for using "Free cell"
		for(int i=0; i<Table.NUM_OF_TABLEAU; i++){
			emptyCells[i] = new LinearLayout(context);
			relativeParams = new RelativeLayout.LayoutParams(widthOfCard, heightOfCard);
			relativeParams.addRule(RelativeLayout.ALIGN_TOP, R.id.loc_tableaus);
			relativeParams.addRule(RelativeLayout.ALIGN_START, R.id.loc_tableaus);
			relativeParams.leftMargin = moveHorz;
			emptyCells[i].setBackgroundColor(0xffffff00);
			((RelativeLayout)(context).findViewById(R.id.loc_tableaus)).addView(emptyCells[i], relativeParams);	//	add new
			emptyCells[i].setOnClickListener(new emptyCellHandler(table, context, i));	// click listener
			moveHorz+=widthOfCard+margin;
		}

		// Placeholder under Talon to loop when empty
		((RelativeLayout)(context).findViewById(R.id.loc_talon)).removeView(loopTalon);	// remove old
		relativeParams = new RelativeLayout.LayoutParams(widthOfCard, heightOfCard);
		relativeParams.addRule(RelativeLayout.ALIGN_TOP, R.id.loc_talon);
		relativeParams.addRule(RelativeLayout.ALIGN_START, R.id.loc_talon);
		((RelativeLayout)(context).findViewById(R.id.loc_talon)).addView(loopTalon, relativeParams);	//	add new
		loopTalon.setOnClickListener(new loopTalonHandler(table, context));	// click listener


		//	Draw Talon
		iter = talonLayouts.iterator();
		while(iter.hasNext()){
			cardLayout = iter.next();
			cardLayout.drawCard(widthOfCard, heightOfCard);

			relativeParams = new RelativeLayout.LayoutParams(widthOfCard, heightOfCard);
			relativeParams.addRule(RelativeLayout.ALIGN_TOP, R.id.loc_talon);
			relativeParams.addRule(RelativeLayout.ALIGN_START, R.id.loc_talon);

			((RelativeLayout)(context).findViewById(R.id.loc_talon)).addView(cardLayout, relativeParams);

			cardLayout.setOnClickListener(new fromTalonHandler(table,context));
		}


		//	Draw Waste
		iter = wasteLayouts.iterator();
		while(iter.hasNext()){
			cardLayout = iter.next();
			cardLayout.drawCard(widthOfCard, heightOfCard);

			relativeParams = new RelativeLayout.LayoutParams(widthOfCard, heightOfCard);
			relativeParams.addRule(RelativeLayout.ALIGN_TOP, R.id.loc_waste);
			relativeParams.addRule(RelativeLayout.ALIGN_START, R.id.loc_waste);

			((RelativeLayout)(context).findViewById(R.id.loc_waste)).addView(cardLayout, relativeParams);

			cardLayout.setOnClickListener(new fromWasteHandler(table,context));
		}


		moveHorz = 0;
		//	Draw Tableaus
		for(int i=0; i<Table.NUM_OF_TABLEAU; i++){
			iter = tableauLayouts[i].iterator();

			moveVert=0;

			while(iter.hasNext()){
				cardLayout = iter.next();
				cardLayout.drawCard(widthOfCard, heightOfCard);

				relativeParams = new RelativeLayout.LayoutParams(widthOfCard, heightOfCard);

				relativeParams.addRule(RelativeLayout.ALIGN_TOP, R.id.loc_tableaus);
				relativeParams.addRule(RelativeLayout.ALIGN_START, R.id.loc_tableaus);

				relativeParams.leftMargin = moveHorz;
				relativeParams.topMargin = moveVert;

				((RelativeLayout)(context).findViewById(R.id.loc_tableaus)).addView(cardLayout, relativeParams);

				cardLayout.setOnClickListener(new fromTableauHandler(table,context));

				moveVert += dpToPx(20);
			}

			moveHorz+=widthOfCard+margin;
		}


		// Draw Foundations
		moveHorz=0;
		for(int i=0; i<4; i++){
			iter = foundationLayouts[i].iterator();
			while(iter.hasNext()){
				cardLayout = iter.next();
				cardLayout.drawCard(widthOfCard, heightOfCard);

				relativeParams = new RelativeLayout.LayoutParams(widthOfCard, heightOfCard);

				relativeParams.addRule(RelativeLayout.ALIGN_TOP, R.id.loc_foundations);
				relativeParams.addRule(RelativeLayout.ALIGN_LEFT);

				relativeParams.leftMargin = moveHorz;

				((RelativeLayout)(context).findViewById(R.id.loc_foundations)).addView(cardLayout, relativeParams);
			}

			moveHorz+=(widthOfCard+margin);
		}
	}

	public class fromTalonHandler implements View.OnClickListener{
		Table table;
		MainActivity context;

		private fromTalonHandler(Table table, MainActivity context) {
			this.table = table;
			this.context = context;
		}

		@Override
		public void onClick(View v) {
			table.moveToWaste(table.getMoveToWasteAmt());
			draw();
		}
	}

	public class fromTableauHandler implements View.OnClickListener{
		Table table;
		MainActivity context;

		private fromTableauHandler(Table table, MainActivity context) {
			this.table = table;
			this.context = context;
		}

		@Override
		public void onClick(View v) {
			// Card is face down
			if(! ((CardLayout)v).getCard().isFaceUp()){
				return;
			}


			// Try moving to Foundations
			if(table.tryMoveToFoundations(((CardLayout)v).getCard())){
				draw();
				return;
			}

			if(!aCardIsSelected){	//	No card to selected to move yet.  Select one
				moveCard = (CardLayout)v;

				aCardIsSelected = true;

				if(MainActivity.DEBUG_FLAG){
					Log.d("SELECT", moveCard.getCard().toString());
				}

			}else{	//	We have already selected the card, now move it
				toHere = (CardLayout)v;

				aCardIsSelected = false;



				if(!table.moveToTableau(moveCard.getCard(), toHere.getCard())){	// if can't move
					moveCard = toHere;
					aCardIsSelected = true;

					if(MainActivity.DEBUG_FLAG){
						Log.d("SELECT", moveCard.getCard().toString());
					}

				}else{
					if(MainActivity.DEBUG_FLAG) {
						Log.d("LOCATION", toHere.getCard().toString());
					}
					draw();
				}
			}
		}
	}

	public class emptyCellHandler implements View.OnClickListener{
		Table table;
		MainActivity context;
		int col;

		private emptyCellHandler(Table table, MainActivity context, int col) {
			this.table = table;
			this.context = context;
			this.col = col;
		}

		@Override
		public void onClick(View v) {
			if(aCardIsSelected){
				table.moveToTableau(moveCard.getCard(), col);
			}
			draw();
		}
	}

	public class fromWasteHandler implements View.OnClickListener{
		Table table;
		MainActivity context;

		private fromWasteHandler(Table table, MainActivity context) {
			this.table = table;
			this.context = context;
		}

		@Override
		public void onClick(View v) {
			// Try moving to Foundations
			if(table.tryMoveToFoundations(((CardLayout)v).getCard())  &&  ((CardLayout)v).getCard().isFaceUp()){
				draw();
				return;
			}

			// Not able to move to foundation, prep moving to a tableau
			moveCard = (CardLayout)v;
			aCardIsSelected = true;

			// update drawing
			draw();
		}
	}

	public class loopTalonHandler implements View.OnClickListener{
		Table table;
		MainActivity context;

		private loopTalonHandler(Table table, MainActivity context) {
			this.table = table;
			this.context = context;
		}

		@Override
		public void onClick(View v) {
			table.refillTalon();
			draw();
		}
	}



	private int dpToPx(int dp) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	}
}
