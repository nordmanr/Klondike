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

	public void moveToTableau(Card card, int column){
		final int GET_SECTION = 0;
		final int GET_COLUMN = 1;
		final int GET_INDEX = 2;

    	int[] location;

    	location = findCard(card);

		if(!card.isFaceUp()){
			// Card is not face up
			// Not valid
			return;
		}
		if(location[GET_SECTION] == Card.IN_WASTE){
			// Removed from waste, set flag
			movedFromWaste = true;
		}

		while(location[GET_INDEX] < tableaus[location[GET_COLUMN]].size()){
			card = tableaus[location[GET_COLUMN]].remove(location[GET_INDEX]);
			tableaus[column].add(card);
		}

		try{
			tableaus[location[GET_COLUMN]].get(tableaus[location[GET_COLUMN]].size()-1).setFaceUp(true);
		}catch (Exception e){
			// Exception b/c out of bounds
		}

		return;



	}

	public boolean moveToTableau(Card from, Card to){
    	/*
    		PARAMS:	Card from:	card to try and move
    				Card to:	moving to here
    		RETURN:	boolean:	if successfully moved

    		Moves a card to location if possible, otherwise return false

    		ONLY HANDLES MOVING INTO A TABLEAU
    	 */


    	final int GET_SECTION = 0;
    	final int GET_COLUMN = 1;
    	final int GET_INDEX = 2;



		int[] locationOfFrom, locationOfTo;	//	Describe location of each
		Card card;

		locationOfFrom = findCard(from);
		locationOfTo = findCard(to);


    	if(from.isRed()==to.isRed()){
    		// Both are same color
			// Not valid
			return false;
		}
		if(to.getValue()-1 != from.getValue()){
    		// Card is not next in order for location
			// Not valid
			return false;
		}
		if(!to.isFaceUp() || !from.isFaceUp()){
    		// Either card is not face up
			// Not valid
			return false;
		}
		if(locationOfTo[GET_SECTION] == Card.IN_TALON){
			// Can't move to Talon
			// Not valid
			return false;
		}
		if(locationOfTo[GET_SECTION] == Card.IN_WASTE){
    		// Can't move to Waste
			// Not valid
			return false;
		}
		if(locationOfTo[GET_SECTION] == Card.IN_FOUNDATIONS){
    		// Can't move to Foundation
			// Not valid
			return false;
		}
		if(Arrays.equals(locationOfFrom, locationOfTo)){
    		// Moving to the place you are moving from
			// Not valid
			return false;
		}
		if(locationOfFrom[GET_SECTION] == Card.IN_WASTE){
    		movedFromWaste = true;
		}


		//------------------------------------------------------------------------------------------
		// Move is valid
		// Now do it

		switch (locationOfFrom[GET_SECTION]){
			case Card.IN_TALON:	//	FROM TALON
				// Can't move directly from Talon!
				// Call move from Talon to Waste
				moveToWaste(3);
				break;
			case Card.IN_WASTE:	//	FROM WASTE
				//	Remove
				waste.remove(from);

				tableaus[locationOfTo[GET_COLUMN]].add(from);
				break;
			case Card.IN_TABLEAUS:	//	FROM TABLEAU
				//	Remove
				tableaus[locationOfFrom[GET_COLUMN]].remove(from);
				tableaus[locationOfTo[GET_COLUMN]].add(from);

				// Move entire sublist, card selected and below...
				while(locationOfFrom[GET_INDEX] < tableaus[locationOfFrom[GET_COLUMN]].size()){
					card = tableaus[locationOfFrom[GET_COLUMN]].remove(locationOfFrom[GET_INDEX]);
					tableaus[locationOfTo[GET_COLUMN]].add(card);
				}

				try{
					tableaus[locationOfFrom[GET_COLUMN]].get(tableaus[locationOfFrom[GET_COLUMN]].size()-1).setFaceUp(true);
				}catch (Exception e){
					// Exception b/c out of bounds
				}

				break;
			case Card.IN_FOUNDATIONS:	//	FROM FOUNDATIONS
				//	Remove
				foundations[locationOfFrom[GET_COLUMN]].remove(from);

				tableaus[locationOfTo[GET_COLUMN]].add(from);

				break;
		}

		// Success
		return true;
	}

	public boolean tryMoveToFoundations(Card card){
		final int GET_SECTION = 0;		// Just some tags for readability
		final int GET_COLUMN = 1;
		final int GET_INDEX = 2;

		int[] location;
		boolean movedIt;

		location = findCard(card);
		movedIt = false;

		if(location[GET_SECTION] == Card.IN_TABLEAUS){
			if(tableaus[location[GET_COLUMN]].size() != location[GET_INDEX]+1){
				// Is it at the bottom of a tableau?
				// if not
				return false;
			}
		}

		switch (card.getSuit()) {
			case 'd':
				// IF: Next in column or Ace
				if (card.getValue()==1) {
					// Move it
					foundations[0].add(card);
					// Set flag to do other needed stuff
					movedIt = true;
				}else{
					try {
						if(card.getValue() == foundations[0].get(foundations[0].size() - 1).getValue() + 1){
							// Move it
							foundations[0].add(card);
							// Set flag to do other needed stuff
							movedIt = true;
						}
					}catch (ArrayIndexOutOfBoundsException e){
						// Empty and not 1
					}
				}
				break;
			case 'h':
				// IF: Next in column or Ace
				if (card.getValue()==1) {
					// Move it
					foundations[1].add(card);
					// Set flag to do other needed stuff
					movedIt = true;
				}else{
					try {
						if(card.getValue() == foundations[1].get(foundations[1].size() - 1).getValue() + 1){
							// Move it
							foundations[1].add(card);
							// Set flag to do other needed stuff
							movedIt = true;
						}
					}catch (ArrayIndexOutOfBoundsException e){
						// Empty and not 1
					}
				}
				break;
			case 'c':
				// IF: Next in column or Ace
				if (card.getValue()==1) {
					// Move it
					foundations[2].add(card);
					// Set flag to do other needed stuff
					movedIt = true;
				}else{
					try {
						if(card.getValue() == foundations[2].get(foundations[2].size() - 1).getValue() + 1){
							// Move it
							foundations[2].add(card);
							// Set flag to do other needed stuff
							movedIt = true;
						}
					}catch (ArrayIndexOutOfBoundsException e){
						// Empty and not 1
					}
				}
				break;
			case 's':
				// IF: Next in column or Ace
				if (card.getValue()==1) {
					// Move it
					foundations[3].add(card);
					// Set flag to do other needed stuff
					movedIt = true;
				}else{
					try {
						if(card.getValue() == foundations[3].get(foundations[3].size() - 1).getValue() + 1){
							// Move it
							foundations[3].add(card);
							// Set flag to do other needed stuff
							movedIt = true;
						}
					}catch (ArrayIndexOutOfBoundsException e){
						// Empty and not 1
					}
				}
				break;
		}


		if(location[GET_SECTION] == Card.IN_WASTE){
			movedFromWaste = true;
		}

		if(movedIt){
			// Remove it
			if(location[GET_SECTION] == Card.IN_TABLEAUS) {
				tableaus[location[GET_COLUMN]].remove(card);
			}else if(location[GET_SECTION] == Card.IN_WASTE){
				waste.remove(card);
			}
			// Flip new top of column in tableau
			try{
				tableaus[location[GET_COLUMN]].get(tableaus[location[GET_COLUMN]].size()-1).setFaceUp(true);
			}catch (Exception e){
				// Exception b/c out of bounds
				// Not big deal, column is just now empty
			}
			// face up
			card.setFaceUp(true);

			return true;
		}else{
			return false;
		}
	}

	public int[] findCard(Card card){
    	/*
    		Returns an int[2] describing the location of card
    			[0]	->	Which part of the table it is in: talon, waste, tableau, or foundation
    			[1]	->	in tableau or foundation, which column
    			[2]	->	index of card

    	 */


    	// In talon
		if(talon.contains(card)){
			return new int[]{Card.IN_TALON, -1, talon.indexOf(card)};
		}

		// In waste
		if(waste.contains(card)){
			return new int[]{Card.IN_WASTE, -1, waste.indexOf(card)};
		}

		// In tableaus
		for(int i=0; i<NUM_OF_TABLEAU; i++){
			if(tableaus[i].contains(card)){
				return new int[]{Card.IN_TABLEAUS, i, tableaus[i].indexOf(card)};
			}
		}

		// In foundations
		for(int i=0; i<NUM_OF_TABLEAU; i++){
			if(foundations[i].contains(card)){
				return new int[]{Card.IN_FOUNDATIONS, i, foundations[i].indexOf(card)};
			}
		}

		return new int[]{-1,-1,-1};	//	Fail to find
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
