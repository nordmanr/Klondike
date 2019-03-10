package com.utopple.code.klondike;

public class Deck {
    private Card[] allCards;
    Deck(){
        allCards = new Card[52];

        // Fill with standard 52 card, 13 of each suit
        for(int i=0; i<13; i++){
            // 0(ace) to 12(king)
            allCards[i] = new Card('d', (char)(i+1));   // actually using 1 to 13 as Ace to King for Card Object, so +1
            allCards[i+13] = new Card('h', (char)(i+1));
            allCards[i+26] = new Card('s', (char)(i+1));
            allCards[i+39] = new Card('c', (char)(i+1));
        }
    }

    // Random shuffle method
    public void shuffle(){
        int random;
        Card temp;

        for(int i=0; i<allCards.length; i++){
            random = (int)(Math.random()*52);  // random number from 0 to 51

            // the swap
            temp = allCards[random];
            allCards[random] = allCards[i];
            allCards[i] = temp;
        }
    }

    public Card[] getAllCards() {
        return allCards;
    }
}
