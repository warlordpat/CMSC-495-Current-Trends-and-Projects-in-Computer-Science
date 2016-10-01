// File: Waste.java
// Author: Patrick Smith
// Date: Sep 27, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: Implements a Waste or discard pile in Solitaire.
package group3;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Represents a Waste or discard pile in Solitaire.
 *
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 27, 2016
 */
public class Waste extends SolitairePile {
    /**
     * Generated Serial ID.
     */
    private static final long serialVersionUID = 3046202092127038810L;
    /**
     * The Color of an empty Waste.
     */
    public static final Color PLACEHOLDER_COLOR = Color.WHITE;
    /**
     * Alpha Blending of 5% for the place holder color and background.
     */
    public static final AlphaComposite PLACEHOLDER_ALPHA =
            AlphaComposite.getInstance(3, 0.05F);
    /**
     * The List of Cards in the Waste.
     */
    private ArrayList<Card> cards;

    /**
     * Creates a new, empty Waste.
     */
    public Waste() {
        cards = new ArrayList<>();
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected final void paintComponent(final Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        g.setColor(PLACEHOLDER_COLOR);
        g.setComposite(PLACEHOLDER_ALPHA);
        g.fillRect(0, 0, Card.CARD_WIDTH, Card.CARD_HEIGHT);

        g.dispose();
    }

    /*
     * (non-Javadoc)
     *
     * @see group3.SolitairePile#getTopCard()
     */
    @Override
    public final Card getTopCard() {
        return cards.get(cards.size() - 1);
    }

    /*
     * (non-Javadoc)
     *
     * @see group3.SolitairePile#getAvailableCardsAt(group3.Card)
     */
    @Override
    public final ArrayList<Card> getAvailableCardsAt(final Card card) {
        ArrayList<Card> temp = new ArrayList<>();
        temp.add(card);
        return temp;
    }

    /*
     * (non-Javadoc)
     *
     * @see group3.SolitairePile#addSingleCard(group3.Card)
     */
    @Override
    public final void addSingleCard(final Card card) {
        add(card, 0);
        cards.add(card);
    }

    /*
     * (non-Javadoc)
     *
     * @see group3.SolitairePile#removeSingleCard(group3.Card)
     */
    @Override
    public final void removeSingleCard(final Card card) {
        remove(card);
        cards.remove(card);
        repaint();
    }

    /*
     * (non-Javadoc)
     *
     * @see group3.SolitairePile#addCards(java.util.ArrayList)
     */
    @Override
    public void addCards(final ArrayList<Card> theCards) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see group3.SolitairePile#removeCards(java.util.ArrayList)
     */
    @Override
    public void removeCards(final ArrayList<Card> theCards) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see group3.SolitairePile#isValidMove(group3.Card)
     */
    @Override
    public final boolean isValidMove(final Card paramCard) {
        return false;
    }

    /**
     * Returns all the Cards in the Waste back to the Deck.
     *
     * @param deck
     *            the Deck to return the Cards to
     */
    public final void returnCards(final Deck deck) {
        removeAll();
        deck.addCards(cards);
        cards.clear();
        repaint();
    }
}
