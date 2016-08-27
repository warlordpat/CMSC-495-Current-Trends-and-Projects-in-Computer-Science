// File: Card.java
// Author: Patrick Smith
// Date: Aug 26, 2016
// Course: CMSC 495
// Assignment: TODO
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: TODO
package blackjack;

/**
 * This class represents a playing card.
 * 
 * @author Patrick Smith
 * @version 1.0
 * @since Aug 26, 2016
 */
public class Card {

    /**
     * The rank of the card.
     */
    private Rank rank;
    /**
     * The suit of the card.
     */
    private Suit suit;

    /**
     * Creates a Card of the given rank and suit.
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    } // end constructor

    /**
     * Gets the suit of the card.
     * 
     * @return the suit
     */
    public Suit getSuit() {
        return suit;
    } // end method

    /**
     * Gets the rank of the card.
     * 
     * @return the rank
     */
    public Rank getRank() {
        return rank;
    } // end method

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "" + rank + suit;
    } // end method
} // end class
