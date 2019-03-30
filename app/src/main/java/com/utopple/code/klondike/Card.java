package com.utopple.code.klondike;

public class Card{
    public static final int IN_TALON=0;
    public static final int IN_WASTE=1;
    public static final int IN_TABLEAUS=2;
    public static final int IN_FOUNDATIONS=3;

    private char suit;
    private boolean red;
    private int value;
    private boolean faceUp;

    Card(char suit, int value){
        /*
            Suit: (d)iamond, (h)eart, (s)pade, (c)lub
            Value: Ace = 1, 2 = 2, 3 = 3 ... Jack = 11, Queen = 12, King = 13
         */
        this.suit = suit;
        this.value = value;
        if(suit == 'd' || suit == 'h'){
            this.red = true;
        }
        this.faceUp = false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        //  Value
        switch (value){
            case 1:   //  Ace
                stringBuilder.append("Ace");
                break;
            case 2:   //  2
                stringBuilder.append("2");
                break;
            case 3:   //  3
                stringBuilder.append("3");
                break;
            case 4:   //  4
                stringBuilder.append("4");
                break;
            case 5:   //  5
                stringBuilder.append("5");
                break;
            case 6:   //  6
                stringBuilder.append("6");
                break;
            case 7:   //  7
                stringBuilder.append("7");
                break;
            case 8:   //  8
                stringBuilder.append("8");
                break;
            case 9:   //  9
                stringBuilder.append("9");
                break;
            case 10:   //  10
                stringBuilder.append("10");
                break;
            case 11:   //  Jack
                stringBuilder.append("Jack");
                break;
            case 12:   //  Queen
                stringBuilder.append("Queen");
                break;
            case 13:   //  King
                stringBuilder.append("King");
                break;
        }

        //  "of"
        stringBuilder.append(" of ");

        //  Select suit
        switch (suit){
            case 'd':   //    Diamonds
                stringBuilder.append("Diamonds");
                break;
            case 'h':   //    Hearts
                stringBuilder.append("Hearts");
                break;
            case 'c':   //    Clubs
                stringBuilder.append("Clubs");
                break;
            case 's':   //    Spades
                stringBuilder.append("Spades");
                break;
        }

        return stringBuilder.toString();
    }

    //  SETs
    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    //  GETs
    public char getSuit() {
        return suit;
    }
    public int getValue() {
        return value;
    }
    public boolean isRed() {
        return red;
    }
    public boolean isFaceUp() {
        return faceUp;
    }

}
