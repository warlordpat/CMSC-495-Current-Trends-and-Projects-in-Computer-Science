package group3;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.*;
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
     * A List of the Cards in the Hand.
     */
    private List<Card> cards;
    /**
     * The image of the back of the card
     */
    @SuppressWarnings("unused")
    private BufferedImage cardBack;

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
        cardBack = Card.getBacks().getSubimage(72, 0, 72, 96);
        this.setPreferredSize(new Dimension(72, 96));
    } // end constructor

    /**
     * Adds a card to the hand (i.e a hit in BlackJack or just dealing a Card).
     * 
     * @param card
     *            The card to add
     */
    void addCard(Card card) {
        // moves the card to the proper offset in the hand
        card.setBounds(cards.size() * 75, 0, 72, 96);
        cards.add(card); // add the card to the Hand's List
        add(card, 0); // add the card to the hand JLabel
//        System.out.println("card added to hand and label");
        resize();
        repaint(); // force the GUI to update when a card is added
    } // end method

    /**
     * Resizes the Hand. Use when the number of Cards in the hand changes.
     */
    private void resize() {
        int index = 0;
        for (Card card : cards) {
            card.setBounds(index * 75, 0, 72, 96);
            index++;
        }
        if (cards.size() != 0) {
            // resize the hand JComponent to the size of the hand
            setSize(3 * cards.size() + cards.size() * 72, 96);
        } else {
            setSize(72, 96); // default size of a single card.
        }
    }

    /**
     * Scores the Hand based on the value Map. Should be overridden in a
     * sub-class for each game. Right now calculates score based on BlackJack
     * rules.
     * 
     * @return the score of the Hand.
     */
    int scoreHand() {
//        System.out.println("in scoring");
        int sum = 0;
        boolean hasAce = false;
        for (Card card : cards) {
//            System.out.println("iterating over cards");
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
     * Checks if the hand is busted. Will move to a BlackJackHand sub-class
     * 
     * @return true, if busted
     */
    boolean isBusted() {
        System.out.println("scoring hand");
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
     * Gets a card at the given index.
     * 
     * @param index
     *            the index of the Card
     * @return the Card at that index
     */
    Card getCard(int index) {
        return cards.get(index);
    } // end method

    /**
     * Removes (discards) all cards. Does not do anything with the card.
     */
    public void returnCards() {
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
    public int handSize() {
        return cards.size();
    } // end method

    /**
     * Totals the value of the cards in the Hand. More generic than current
     * scoreHand method.
     * 
     * @return the total value of the Cards in the Hand
     */
    public int total() {
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
    public Card removeCard() {
        Card card = cards.remove(0);
        resize();
        return card;
    } // end method

    /*
     * (non-Javadoc) Draws the hand based on the size of the Hand.
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        g.setColor(Color.WHITE);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05F));
        g.fillRect(0, 0, handSize() * 72, 96);
        g.dispose();
    } // end method
} // end class