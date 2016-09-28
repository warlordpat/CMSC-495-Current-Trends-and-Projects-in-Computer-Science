// File: Hand.java
// Author: Patrick Smith
// Date: Sep 5, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: implements a graphical Hand for card games.
package group3;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

/**
 * The Hand class represents a player's hand in a card game, has the ability to
 * hold Cards, and resizes itself to allow all cards to be placed on the GUI by
 * default.
 *
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 5, 2016
 */
public class Hand extends JLabel {
    /**
     * The spacing between cards.
     */
    private static final int SPACER = 3;
    /**
     * The constant alpha to be multiplied with the alpha of the source. alpha
     * must be a floating point number in the inclusive range [0.0, 1.0].
     */
    private static final float ALPHA = 0.05F;
    /**
     * The high value of an Ace.
     */
    private static final int ACE_10_VALUE = 10;
    /**
     * The score that represents the max in Blackjack.
     */
    private static final int BLACKJACK = 21;
    /**
     * Generated serial ID.
     */
    private static final long serialVersionUID = -732699907221858844L;
    /**
     * Map that links Card Rank to values for this Hand. Probably should be
     * overridden in a sub-class for each game that has different value
     * assignments.
     */
    private static Map<Rank, Integer> values = new HashMap<>();
    // Statically assigns the default Rank values.
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
    /**
     * Map that links Card Rank to values for this Hand. Probably should be
     * overridden in a sub-class for each game that has different value
     * assignments. Differs from values because Aces are worth 11.
     */
    private static Map<Rank, Integer> thirtyOneValues = new HashMap<>();
    // Statically assigns the default Rank values.
    static {
        thirtyOneValues.put(Rank.TWO, 2);
        thirtyOneValues.put(Rank.THREE, 3);
        thirtyOneValues.put(Rank.FOUR, 4);
        thirtyOneValues.put(Rank.FIVE, 5);
        thirtyOneValues.put(Rank.SIX, 6);
        thirtyOneValues.put(Rank.SEVEN, 7);
        thirtyOneValues.put(Rank.EIGHT, 8);
        thirtyOneValues.put(Rank.NINE, 9);
        thirtyOneValues.put(Rank.TEN, 10);
        thirtyOneValues.put(Rank.JACK, 10);
        thirtyOneValues.put(Rank.QUEEN, 10);
        thirtyOneValues.put(Rank.KING, 10);
        thirtyOneValues.put(Rank.ACE, 11);
    }
    /**
     * A List of the Cards in the Hand.
     */
    private List<Card> cards;

    /**
     * Creates a new, empty Hand. The Hand has the ability to hold Cards, and
     * resizes itself to allow all cards to be placed on the GUI by default.
     */
    public Hand() {
        super();
        cards = new ArrayList<>();
        setBackground(null);
        setLayout(null);
        // load card backs from Cards class to reduce image loading error
        // surface.
        // cardBack = Card.getBacks().getSubimage(72, 0, 72, 96);
        this.setPreferredSize(new Dimension(Card.CARD_WIDTH, Card.CARD_HEIGHT));
    } // end constructor

    /**
     * Adds a card to the hand (i.e a hit in BlackJack or just dealing a Card).
     *
     * @param card
     *            The card to add
     */
    final void addCard(final Card card) {
        // moves the card to the proper offset in the hand
        card.setBounds(cards.size() * (Card.CARD_WIDTH + SPACER), 0, Card.CARD_WIDTH, Card.CARD_HEIGHT);
        cards.add(card); // add the card to the Hand's List
        add(card, 0); // add the card to the hand JLabel
        // System.out.println("card added to hand and label");
        resize();
        repaint(); // force the GUI to update when a card is added
    } // end method

    /**
     * Resizes the Hand. Use when the number of Cards in the hand changes.
     */
    private void resize() {
        int index = 0;
        for (Card card : cards) {
            card.setBounds(index * (Card.CARD_WIDTH + SPACER), 0, Card.CARD_WIDTH, Card.CARD_HEIGHT);
            index++;
        }
        if (cards.size() != 0) {
            // resize the hand JComponent to the size of the hand
            setSize((SPACER * cards.size()) + (cards.size() * Card.CARD_WIDTH), Card.CARD_HEIGHT);
        } else {
            setSize(Card.CARD_WIDTH, Card.CARD_HEIGHT); // default size of a
                                                        // single card.
        }
    }

    /**
     * Scores the Hand based on the value Map. Should be overridden in a
     * sub-class for each game. Right now calculates score based on BlackJack
     * rules.
     *
     * @return the score of the Hand.
     */
    final int scoreHand() {
        // System.out.println("in scoring");
        int sum = 0;
        boolean hasAce = false;
        for (Card card : cards) {
            // System.out.println("iterating over cards");
            if (card.getRank().equals(Rank.ACE)) {
                // have an ace
                hasAce = true;
            } // end if
            sum += values.get(card.getRank());
        } // end for
        if (!hasAce) {
            return sum;
        } else {
            if (sum + ACE_10_VALUE <= BLACKJACK) {
                return sum + ACE_10_VALUE;
            } else {
                return sum;
            }
        } // end else
    } // end method

    /**
     * Checks if the hand is busted. Will move to a BlackJackHand sub-class
     *
     * @return true, if busted
     */
    final boolean isBusted() {
        System.out.println("scoring hand");
        return !(scoreHand() <= BLACKJACK);
    } // end method

    /**
     * Converts a Hand to a String representation.
     *
     * @see java.lang.Object#toString()
     * @return A String representation of a Hand
     */
    public final String toString() {
        return cards.toString();
    } // end method

    /**
     * Gets the list of cards in the hand.
     *
     * @return the list of cards
     */
    final List<Card> getCards() {
        return cards;
    }

    /**
     * Gets a card at the given index.
     *
     * @param index
     *            the index of the Card
     * @return the Card at that index
     */
    final Card getCard(final int index) {
        return cards.get(index);
    } // end method

    /**
     * Removes (discards) all cards. Does not do anything with the card.
     */
    public final void returnCards() {
        while (cards.size() > 0) {
            cards.remove(0);
        }
        resize();
    } // end method

    /**
     * Gets the number of Cards in a Hand.
     *
     * @return the number of Cards in the Hand
     */
    public final int handSize() {
        return cards.size();
    } // end method

    /**
     * Removes a specific Card from the Hand and returns it to the caller.
     *
     * @return the Card removed from the Hand
     */
    public final Card returnCard(Card card) {
        for (int iNum = 0; iNum < cards.size(); iNum++)
        {
            if (cards.get(iNum) == card)
            {
                cards.remove(iNum);
                resize();
                return card;
            }
        }
        return null;
    } // end method

    /**
     * Returns the total of a hand with Aces worth 11
     */
    public final int thirtyOneTotal() {
        int iTotal = 0;
        for (Card card : cards) {
            iTotal += thirtyOneValues.get(card.getRank());
        }
        return iTotal;
    } // end method
    
    /**
     * Totals the value of the cards in the Hand. More generic than current
     * scoreHand method.
     *
     * @return the total value of the Cards in the Hand
     */
    public final int total() {
        int iTotal = 0;
        for (Card card : cards) {
            iTotal += values.get(card.getRank());
        }
        return iTotal;
    } // end method

    /**
     * Removes a Card from the Hand and returns it to the caller.
     *
     * @return the Card removed from the Hand
     */
    public final Card removeCard() {
        Card card = cards.remove(0);
        resize();
        return card;
    } // end method

    /**
     * Checks to see if a hand is has Blackjack.
     *
     * @return true, if the hand has a value of 21 and only two cards
     */
    public final boolean isBlackjack() {
        return handSize() == 2 && scoreHand() == BLACKJACK;
    }

    /**
     * Draws the hand based on the size of the Hand.
     *
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     * @param graphics
     *            the Graphics object to protect
     */
    protected final void paintComponent(final Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        g.setColor(Color.WHITE);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ALPHA));
        g.fillRect(0, 0, handSize() * Card.CARD_WIDTH, Card.CARD_HEIGHT);
        g.dispose();
    } // end method

    /**
     * Inserts the specified Card at the specified position in this hand. Shifts
     * the Card currently at that position (if any) and any subsequent elements
     * to the right (adds one to their indices). Used mostly for debugging
     * Hands.
     *
     * @param index
     *            index at which the specified Card is to be inserted
     * @param card
     *            Card to be inserted
     */
    public final void addCard(final int index, final Card card) {
        cards.add(index, card);
    }
} // end class