package com.utopple.code.klondike;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;


public class Table {
    private Deck baseDeck;	// a 52 card deck
	private ArrayList<CardLayout> allCards;
	private Stack<CardLayout> talon;  // Remaining deck
	private Stack<CardLayout>[] foundations;	// Starts with Ace as base, build up
	private ArrayList<CardLayout>[] tableaus;	// Playable space, counts down
	private Stack<CardLayout> waste;  // Flipped from talon can move to foundation or tableau
	private int score;	//	Score this game
	private MainActivity context;	//	Drawing to this context
	private LinearLayout loopTalon;


	private static int viewWidth;		// Width of screen (px)
	private static int viewHeight;		// Height of screen (px)
	private static int widthOfCard = 50;		// Width of each card when drawn (in px)
	private static int heightOfCard = 75;	// Height of each card when drawn (in px)
	private static boolean aCardIsSelected;	// Flag to use when moving a card around
	private static CardLayout moveCard;	// which card we are trying to move
	private static CardLayout toHere;	// And to where we are moving it


	private final static int SCORE_FOUNDATION = 10;			// Score awarded whenever something is added to a Foundation
	private final static int SCORE_TABLEAU = 5;				// Score awarded when Waste to Tableau or flip Tableau card
	private final static int SCORE_OFF_FOUNDATION = -15;	// Score when moving Foundation to Tableau
	private final static int NUM_OF_TABLEAU = 7;			// Amount of tableaus

    Table(MainActivity context){
    	this.context = context;

        baseDeck = new Deck();
        allCards = new ArrayList<>();
        talon = new Stack<>();
        foundations = new Stack[4];
        tableaus = new ArrayList[7];
        waste = new Stack<>();
        score = 0;
        loopTalon = new LinearLayout(context);
        aCardIsSelected = false;

        for(int i=0; i<4; i++){
            foundations[i] = new Stack<CardLayout>();
        }

        for(int i=0; i<NUM_OF_TABLEAU; i++){
            tableaus[i] = new ArrayList<CardLayout>();
        }

		viewWidth = context.getResources().getDisplayMetrics().widthPixels;
		viewHeight = context.getResources().getDisplayMetrics().heightPixels;

		if(viewWidth < viewHeight){
			widthOfCard = viewWidth/(NUM_OF_TABLEAU+1);
			heightOfCard = ((int) (widthOfCard * 1.5));
		}

		start();  // Sets up all the starting stuff
    }

    public void restart(){
        start();
    }
    public void start(){
        /*
            Sets up initial conditions:
            Shuffling deck
            Filling talon
            Empty foundations
            Tableaus set up
            Empty waste
         */


		CardLayout cardLayout;


        //  Shuffle --------------------------------------------------------------------------------
        baseDeck.shuffle(); //  Shuffle

		//	All cards ------------------------------------------------------------------------------
		for(int i=0; i<52; i++){	//	All cards in allCards
			cardLayout = new CardLayout(context);
			cardLayout.setCard(baseDeck.getAllCards()[i]);

			allCards.add(cardLayout);
		}


		//	Talon ----------------------------------------------------------------------------------
		talon.addAll(allCards);	//  add every card to talon

        //  Empty foundations ----------------------------------------------------------------------
        for(int i=0; i<4; i++){
            // for each foundation
            foundations[i].clear();
        }
		//	Placeholders in foundations
		cardLayout = new CardLayout(context);
		cardLayout.setCard(new Card('d',0));
		foundations[0].add(cardLayout);
		allCards.add(cardLayout);
		cardLayout = new CardLayout(context);
		cardLayout.setCard(new Card('h',0));
		foundations[1].add(cardLayout);
		allCards.add(cardLayout);
		cardLayout = new CardLayout(context);
		cardLayout.setCard(new Card('c',0));
		foundations[2].add(cardLayout);
		allCards.add(cardLayout);
		cardLayout = new CardLayout(context);
		cardLayout.setCard(new Card('s',0));
		foundations[3].add(cardLayout);
		allCards.add(cardLayout);

        //  Set tableaus ---------------------------------------------------------------------------
        for(int i=0, faceDownNum=0; i<NUM_OF_TABLEAU; i++, faceDownNum++){
            for(int j=0; j<faceDownNum; j++){   // facedown cards
				cardLayout = talon.pop();

                tableaus[i].add(cardLayout);
            }

			//  faceup card at bottom of each tableau
			cardLayout = talon.pop();
			cardLayout.getCard().setFaceUp(true);

			tableaus[i].add(cardLayout);
        }

        //  Clear the waste ------------------------------------------------------------------------
        waste.clear();

        //	Reset score ----------------------------------------------------------------------------
        score = 0;
    }

    public void flipCards(int amount){
		// moves 'amount' of cards from talon to waste]

    	CardLayout cardLayout;

    	for(int i=0; i<amount; i++){
    		if(!talon.empty()){	// if not empty
				cardLayout = talon.pop();
				cardLayout.getCard().setFaceUp(true);

    			waste.push(cardLayout);
			}
		}
	}

	public void refillTalon(){
    	// Moves all cards from waste to talon

		CardLayout cardLayout;

    	while(!waste.empty()){	// if not empty
			cardLayout = waste.pop();
			cardLayout.getCard().setFaceUp(false);

    		talon.push(cardLayout);
		}
	}

	public void setScore(int score) {
		this.score = score;
	}
	public void addScore(int score) {
		this.score += score;
	}
	public int getScore() {
		return score;
	}

	public boolean moveToTableau(CardLayout from, CardLayout to){
    	/*
    		PARAMS:	Card from:	card to try and move
    				Card to:	moving to here
    		RETURN:	boolean:	if successfully moved

    		Moves a card to location if possible, otherwise return false

    		ONLY HANDLES MOVING INTO A TABLEAU
    	 */


    	final int SECTION = 0;
    	final int COLUMN = 1;
    	final int INDEX = 2;



		int[] locationOfFrom, locationOfTo;	//	Describe location of each
		List<CardLayout> subList;
		CardLayout cardLayout;

		locationOfFrom = findCard(from);
		locationOfTo = findCard(to);


		//TODO:  What if moving to 'free cell'?  Must account for that before everything else



    	if(from.getCard().isRed()==to.getCard().isRed()){
    		// Both are same color
			// Not valid
			return false;
		}
		if(to.getCard().getValue()-1 != from.getCard().getValue()){
    		// Card is not next in order for location
			// Not valid
			return false;
		}
		if(!to.getCard().isFaceUp() || !from.getCard().isFaceUp()){
    		// Either card is not face up
			// Not valid
			return false;
		}
		if(locationOfTo[SECTION] == CardLayout.IN_TALON){
			// Can't move to Talon
			// Not valid
			return false;
		}
		if(locationOfTo[SECTION] == CardLayout.IN_WASTE){
    		// Can't move to Waste
			// Not valid
			return false;
		}
		if(locationOfTo[SECTION] == CardLayout.IN_FOUNDATIONS){
    		// Can't move to Foundation
			// Not valid
			return false;
		}
		if(Arrays.equals(locationOfFrom, locationOfTo)){
    		// Moving to the place you are moving from
			// Not valid
			return false;
		}


		//------------------------------------------------------------------------------------------
		// Move is valid
		// Now do it

		switch (locationOfFrom[SECTION]){
			case CardLayout.IN_TALON:	//	FROM TALON
				// Can't move directly from Talon!
				// Call move from Talon to Waste
				flipCards(3);
				break;
			case CardLayout.IN_WASTE:	//	FROM WASTE
				//	Remove
				waste.remove(from);

				tableaus[locationOfTo[COLUMN]].add(from);
				break;
			case CardLayout.IN_TABLEAUS:	//	FROM TABLEAU
				//	Remove
				tableaus[locationOfFrom[COLUMN]].remove(from);
				tableaus[locationOfTo[COLUMN]].add(from);

				while(locationOfFrom[INDEX] < tableaus[locationOfFrom[COLUMN]].size()){
					cardLayout = tableaus[locationOfFrom[COLUMN]].remove(locationOfFrom[INDEX]);
					tableaus[locationOfTo[COLUMN]].add(cardLayout);
				}

				try{
					tableaus[locationOfFrom[COLUMN]].get(tableaus[locationOfFrom[COLUMN]].size()-1).getCard().setFaceUp(true);
				}catch (Exception e){
					// Exception b/c out of bounds
				}

				break;
			case CardLayout.IN_FOUNDATIONS:	//	FROM FOUNDATIONS
				//	Remove
				foundations[locationOfFrom[COLUMN]].remove(from);

				tableaus[locationOfTo[COLUMN]].add(from);

				break;
		}

		// Success
		return true;
	}

	private boolean tryMoveToFoundations(CardLayout card){
		final int SECTION = 0;
		final int COLUMN = 1;
		final int INDEX = 2;

		int[] location;

		location = findCard(card);

		// ACES
		if(card.getCard().getValue() == 1){
			// Remove from old location
			switch (location[SECTION]){
				case 0:	//	Talon
					talon.remove(card);
					break;
				case 1:	//	Waste
					waste.remove(card);
					break;
				case 2:	//	Tableaus
					tableaus[location[COLUMN]].remove(card);
					break;
				case 3:	//	Foundations
					foundations[location[COLUMN]].remove(card);
					break;
			}

			// Flip new top card
			try{
				tableaus[location[COLUMN]].get(tableaus[location[COLUMN]].size()-1).getCard().setFaceUp(true);
			}catch (Exception e){
				// Exception b/c out of bounds
			}

			// Move it!
			switch (card.getCard().getSuit()){
				case 'd':
					foundations[0].add(card);
					break;
				case 'h':
					foundations[1].add(card);
					break;
				case 'c':
					foundations[2].add(card);
					break;
				case 's':
					foundations[3].add(card);
					break;
			}

			// face up
			card.getCard().setFaceUp(true);

			return true;
		}


		return false;
	}

	private int[] findCard(CardLayout card){
    	/*
    		Returns an int[2] describing the location of card
    			[0]	->	Which part of the table it is in: talon, waste, tableau, or foundation
    			[1]	->	in tableau or foundation, which column
    			[2]	->	index of card

    	 */


    	// In talon
		if(talon.contains(card)){
			return new int[]{CardLayout.IN_TALON, -1, talon.indexOf(card)};
		}

		// In waste
		if(waste.contains(card)){
			return new int[]{CardLayout.IN_WASTE, -1, waste.indexOf(card)};
		}

		// In tableaus
		for(int i=0; i<NUM_OF_TABLEAU; i++){
			if(tableaus[i].contains(card)){
				return new int[]{CardLayout.IN_TABLEAUS, i, tableaus[i].indexOf(card)};
			}
		}

		// In foundations
		for(int i=0; i<NUM_OF_TABLEAU; i++){
			if(foundations[i].contains(card)){
				return new int[]{CardLayout.IN_FOUNDATIONS, i, foundations[i].indexOf(card)};
			}
		}

		return new int[]{-1,-1,-1};	//	Fail to find
	}


	public ArrayList<CardLayout>[] getTableaus() {
		return tableaus;
	}
	public Stack<CardLayout> getTalon() {
		return talon;
	}
	public Stack<CardLayout> getWaste() {
		return waste;
	}
	public Stack<CardLayout>[] getFoundations() {
		return foundations;
	}

	public void draw(){
		Iterator<CardLayout> iter;
		CardLayout cardLayout;
		RelativeLayout.LayoutParams relativeParams;
		int moveHorz = 0;
		int moveVert = 0;
		int margin = ((viewWidth-NUM_OF_TABLEAU*widthOfCard)-dpToPx(20))/(NUM_OF_TABLEAU);

		// CLEAR
		iter = allCards.iterator();
		while (iter.hasNext()){
			cardLayout = iter.next();
			((RelativeLayout)(context).findViewById(R.id.loc_talon)).removeView(cardLayout);
			((RelativeLayout)(context).findViewById(R.id.loc_waste)).removeView(cardLayout);
			((RelativeLayout)(context).findViewById(R.id.loc_tableaus)).removeView(cardLayout);
			((RelativeLayout)(context).findViewById(R.id.loc_foundations)).removeView(cardLayout);
		}

		// Placeholder under Talon to loop when empty
		((RelativeLayout)(context).findViewById(R.id.loc_talon)).removeView(loopTalon);
		relativeParams = new RelativeLayout.LayoutParams(widthOfCard, heightOfCard);
		relativeParams.addRule(RelativeLayout.ALIGN_TOP, R.id.loc_talon);
		relativeParams.addRule(RelativeLayout.ALIGN_START, R.id.loc_talon);
		((RelativeLayout)(context).findViewById(R.id.loc_talon)).addView(loopTalon, relativeParams);
		loopTalon.setOnClickListener(new loopTalonHandler(this));


		//	Draw Talon
		iter = talon.iterator();
		while(iter.hasNext()){
			cardLayout = iter.next();
			cardLayout.drawCard(widthOfCard, heightOfCard);

			relativeParams = new RelativeLayout.LayoutParams(widthOfCard, heightOfCard);
			relativeParams.addRule(RelativeLayout.ALIGN_TOP, R.id.loc_talon);
			relativeParams.addRule(RelativeLayout.ALIGN_START, R.id.loc_talon);

			((RelativeLayout)(context).findViewById(R.id.loc_talon)).addView(cardLayout, relativeParams);

			cardLayout.setOnClickListener(new fromTalonHandler(this));
		}


		//	Draw Waste
		iter = waste.iterator();
		while(iter.hasNext()){
			cardLayout = iter.next();
			cardLayout.drawCard(widthOfCard, heightOfCard);

			relativeParams = new RelativeLayout.LayoutParams(widthOfCard, heightOfCard);
			relativeParams.addRule(RelativeLayout.ALIGN_TOP, R.id.loc_waste);
			relativeParams.addRule(RelativeLayout.ALIGN_START, R.id.loc_waste);

			((RelativeLayout)(context).findViewById(R.id.loc_waste)).addView(cardLayout, relativeParams);

			cardLayout.setOnClickListener(new fromTableauHandler(this));
		}


		//	Draw Tableaus
		for(int i=0; i<NUM_OF_TABLEAU; i++){
			iter = tableaus[i].iterator();

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

				cardLayout.setOnClickListener(new fromTableauHandler(this));

				moveVert += dpToPx(20);
			}

			moveHorz+=widthOfCard+margin;
		}


		// Draw Foundations
		moveHorz=0;
		for(int i=0; i<4; i++){
			iter = foundations[i].iterator();
			while(iter.hasNext()){
				cardLayout = iter.next();
				cardLayout.drawCard(widthOfCard, heightOfCard);

				relativeParams = new RelativeLayout.LayoutParams(widthOfCard, heightOfCard);

				relativeParams.addRule(RelativeLayout.ALIGN_TOP, R.id.loc_foundations);
				relativeParams.addRule(RelativeLayout.ALIGN_LEFT);

				relativeParams.leftMargin = moveHorz;

				((RelativeLayout)(context).findViewById(R.id.loc_foundations)).addView(cardLayout, relativeParams);

				cardLayout.setOnClickListener(new fromTableauHandler(this));

			}

			moveHorz+=(widthOfCard+margin);
		}
    }

	private int dpToPx(int dp) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	}

	public class fromTableauHandler implements View.OnClickListener{
    	Table table;

    	public fromTableauHandler(Table table) {
    		this.table = table;
    	}

		@Override
		public void onClick(View v) {
    		if(tryMoveToFoundations((CardLayout)v)  &&  ((CardLayout)v).getCard().isFaceUp()){
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



				if(!moveToTableau(moveCard, toHere)){
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


			if(MainActivity.DEBUG_FLAG){
				Log.d("CARD_PRESS", ""+v.getId());
			}
		}
	}

	public class fromTalonHandler implements View.OnClickListener{
		Table table;

		public fromTalonHandler(Table table) {
			this.table = table;
		}

		@Override
		public void onClick(View v) {
			flipCards(3);
			draw();
		}
	}

	public class loopTalonHandler implements View.OnClickListener{
		Table table;

		public loopTalonHandler(Table table) {
			this.table = table;
		}

		@Override
		public void onClick(View v) {
			refillTalon();
			draw();
		}
	}



}
