package group3;

import java.util.*;

/**
 * @author Patrick Smith
 * @version 1.0
 * @since Aug 26, 2016
 */
public class Deck {

    private List<Card> cards;
    Random rand;

    /**
     * Generate a new full, unshuffled deck.
     */
    public Deck() {
        rand = new Random();
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            } // end for
        } // end for
    } // end method

    /**
     * Shuffles the deck.
     */
    void shuffle() {
        List<Card> temp = cards;
        cards = new ArrayList<>();
        while (!temp.isEmpty()) {
            int random = rand.nextInt(temp.size());
            Card randomCard = temp.remove(random);
            cards.add(randomCard);
        }
    } // end method

    /**
     * Deals a card from the deck.
     * 
     * @return a Card from the top of the deck
     */
    Card deal() {
        return cards.remove(0);
    } // end method

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return cards.toString();
    } // end method

    /**
     * Checks the current size of the deck.
     * 
     * @return the current size of the deck
     */
    public int size() {
        return cards.size();
    } // end method
} // end class