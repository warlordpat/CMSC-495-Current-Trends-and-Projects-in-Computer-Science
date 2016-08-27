// File: Hand.java
// Author: Patrick Smith
// Date: Aug 26, 2016
// Course: CMSC 495
// Assignment: TODO
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: TODO
package blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Patrick Smith
 * @version 1.0
 * @since Aug 26, 2016
 */
public class Hand {

    private static Map<Rank, Integer> values = new HashMap<>();
    static {
        values.put(Rank.ACE, 1);
        values.put(Rank.TWO, 2);
        values.put(Rank.THREE, 3);
        values.put(Rank.FOUR, 4);
        values.put(Rank.FIVE, 5);
        values.put(Rank.SIX, 6);
        values.put(Rank.SEVEN, 7);
        values.put(Rank.EIGHT, 8);
        values.put(Rank.NINE, 9);
        values.put(Rank.TEN, 10);
        values.put(Rank.JACK, 10);
        values.put(Rank.QUEEN, 10);
        values.put(Rank.KING, 10);
    }
    private List<Card> cards;

    /**
     * 
     */
    public Hand() {
        cards = new ArrayList<>();
    }

    /**
     * Adds a card to the hand (hit).
     * 
     * @param card
     *            The card to add
     */
    void addCard(Card card) {
        cards.add(card);
    } // end method

    /**
     * @return
     */
    int scoreHand() {
        int sum = 0;
        boolean hasAce = false;
        for (Card card : cards) {
            if (card.getRank().equals(Rank.ACE)) {
                // have an ace
                hasAce = true;
            } // end if
            sum += values.get(card.getRank());
        } // end for
        if (!hasAce) {
            return sum;
        } else {
            if (sum + 10 <= 21) {
                return sum + 10;
            } else
                return sum;
        } // end else
    } // end method

    /**
     * Checks if the hand is busted.
     * 
     * @return true, if busted
     */
    boolean isBusted() {
        if (scoreHand() <= 21) {
            return false;
        } else {
            return true;
        } // end else
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
     * Gets the list of cards in the hand.
     * 
     * @return the list of cards
     */
    List<Card> getCards() {
        return cards;
    }

    /**
     * Gets the card at the given position number. The Card stays in the hand.
     *
     * @param positionNumber
     *            The position of the Card in the hand
     * @return the Card
     */
    Card getCard(int positionNumber) {
        if (positionNumber < 0 || positionNumber >= cards.size()) {
            throw new IndexOutOfBoundsException();
        }
        return cards.get(positionNumber);
    }

    /**
     * Removes the Cards in the Hand. Used to return cards to a Deck.
     */
    public List<Card> returnCards() {
        List<Card> temp = new ArrayList<Card>(cards);
        cards = new ArrayList<>();
        return temp;
    } // end method

    /**
     * Removes a Card from the Hand and returns it to the caller.
     * 
     * @param position
     *            the position of the card in the Hand
     * @return the Card removed from the Hand
     */
    Card removeCard(int position) {
        if (position < 0 || position >= cards.size()) {
            throw new IndexOutOfBoundsException();
        } // end if
        return cards.remove(position);
    } // end method
} // end class
