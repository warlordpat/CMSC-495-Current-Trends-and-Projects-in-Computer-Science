package group3;

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