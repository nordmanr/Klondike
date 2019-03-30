package com.utopple.code.klondike;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class Table {
    private Deck baseDeck;	// a 52 card deck
	private ArrayList<Card> allCards;
	private Stack<Card> talon;  // Remaining deck
	private Stack<Card>[] foundations;	// Starts with Ace as base, build up
	private ArrayList<Card>[] tableaus;	// Playable space, counts down
	private Stack<Card> waste;  // Flipped from talon can move to foundation or tableau
	private int score;	//	Score this game
	private int moveToWasteAmt;
	private boolean movedFromWaste;


	private final static int SCORE_FOUNDATION = 10;			// Score awarded whenever something is added to a Foundation
	private final static int SCORE_TABLEAU = 5;				// Score awarded when Waste to Tableau or flip Tableau card
	private final static int SCORE_OFF_FOUNDATION = -15;	// Score when moving Foundation to Tableau
	public final static int NUM_OF_TABLEAU = 7;			// Amount of tableaus

    Table(){
        baseDeck = new Deck();
        allCards = new ArrayList<>();
        talon = new Stack<>();
        foundations = new Stack[4];
        tableaus = new ArrayList[7];
        waste = new Stack<>();
        score = 0;
        moveToWasteAmt = 3;
		movedFromWaste = false;

        for(int i=0; i<4; i++){
            foundations[i] = new Stack<Card>();
        }

        for(int i=0; i<NUM_OF_TABLEAU; i++){
            tableaus[i] = new ArrayList<Card>();
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


		Card card;


        //  Shuffle --------------------------------------------------------------------------------
        baseDeck.shuffle(); //  Shuffle

		//	All cards ------------------------------------------------------------------------------
		for(int i=0; i<52; i++){	//	All cards in allCards
			card = baseDeck.getAllCards()[i];

			allCards.add(card);
		}


		//	Talon ----------------------------------------------------------------------------------
		talon.addAll(allCards);	//  add every card to talon

        //  Empty foundations ----------------------------------------------------------------------
        for(int i=0; i<4; i++){
            // for each foundation
            foundations[i].clear();
        }


        //  Set tableaus ---------------------------------------------------------------------------
        for(int i=0, faceDownNum=0; i<NUM_OF_TABLEAU; i++, faceDownNum++){
            for(int j=0; j<faceDownNum; j++){   // facedown cards
				card = talon.pop();

                tableaus[i].add(card);
            }

			//  faceup card at bottom of each tableau
			card = talon.pop();
			card.setFaceUp(true);

			tableaus[i].add(card);
        }

        //  Clear the waste ------------------------------------------------------------------------
        waste.clear();

        //	Reset score ----------------------------------------------------------------------------
        score = 0;


        //	Reset amount moved into Tableau at once ------------------------------------------------
		moveToWasteAmt = 3;

	}

    public void moveToWaste(int amount){
		// moves 'amount' of cards from talon to waste]

    	Card card;

    	for(int i=0; i<amount; i++){
    		if(!talon.empty()){	// if not empty
				card = talon.pop();
				card.setFaceUp(true);

    			waste.push(card);
			}
		}
	}

	public void refillTalon(){
    	// Moves all cards from waste to talon

		Card card;

    	while(!waste.empty()){	// if not empty
			card = waste.pop();
			card.setFaceUp(false);

    		talon.push(card);
		}


		if(!movedFromWaste){
    		moveToWasteAmt--;
    		if(moveToWasteAmt<1){
				moveToWasteAmt=1;
			}
		}
		movedFromWaste = false;
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


	public boolean checkWin(){
    	boolean hasWon = false;

    	if(foundations[0].size()+foundations[1].size()+foundations[2].size()+foundations[3].size() == 52){
    		/* 52 cards in foundations */
    		hasWon = true;
		}

		return hasWon;
	}

	public int[] findCard(Card card){
    	/*
    		Returns an int[2] describing the location of card
    			[0]	->	Which part of the table it is in: talon, waste, tableau, or foundation
    			[1]	->	in tableau or foundation, which column
    			[2]	->	index of card

    	 */

    	// talon
		if(talon.contains(card)){
			return new int[]{0,-1,-1};
		}
		// waste
		if(waste.contains(card)){
			return new int[]{1,-1,-1};
		}
		// tableaus
		for(int i=0; i<7; i++){
			if(tableaus[i].contains(card)){
				return new int[]{2,i,tableaus[i].indexOf(card)};
			}
		}
		// foundations
		for(int i=0; i<4; i++){
			if(foundations[i].contains(card)){
				return new int[]{3,i,foundations[i].indexOf(card)};
			}
		}

		return new int[]{-1,-1,-1};
	}

	public ArrayList<Card>[] getTableaus() {
		return tableaus;
	}
	public Stack<Card> getTalon() {
		return talon;
	}
	public Stack<Card> getWaste() {
		return waste;
	}
	public Stack<Card>[] getFoundations() {
		return foundations;
	}
	public ArrayList<Card> getAllCards() {
		return allCards;
	}
	public int getMoveToWasteAmt() {
		return moveToWasteAmt;
	}
}
