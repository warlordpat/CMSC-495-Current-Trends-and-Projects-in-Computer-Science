// File: Foundation.java
// Author: Patrick Smith
// Date: Sep 27, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: Implements a Foundation.
package group3;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * A Foundation is one of four piles on which a whole suit or sequence must be
 * built up. In most Solitaire games, the four aces are the bottom card or base
 * of the foundations. The foundation piles are hearts, diamonds, spades, and
 * clubs.
 *
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 27, 2016
 */
public class Foundation extends SolitairePile {
    /**
     * Generated Serial ID.
     */
    private static final long serialVersionUID = -7913221816907529320L;
    /**
     * The Color of an empty Foundation.
     */
    public static final Color PLACEHOLDER_COLOR = Color.WHITE;
    /**
     * Alpha Blending of 5% for the place holder color and background.
     */
    public static final AlphaComposite PLACEHOLDER_ALPHA =
            AlphaComposite.getInstance(3, 0.05F);
    /**
     * Alpha Blending of 15% for the place holder color and background.
     */
    public static final AlphaComposite PLACEHOLDER_ALPHA_HIGHLIGHT =
            AlphaComposite.getInstance(3, 0.15F);
    /**
     * Whether to highlight the Foundation by increasing the Alpha blending.
     */
    private boolean highlight = false;
    /**
     * The List of Cards in the Foundation.
     */
    private ArrayList<Card> cards;

    /**
     * Creates a new, empty Foundation.
     */
    public Foundation() {
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
        if (!highlight) {
            g.setColor(PLACEHOLDER_COLOR);
            g.setComposite(PLACEHOLDER_ALPHA);
            g.fillRect(0, 0, Card.CARD_WIDTH, Card.CARD_HEIGHT);
        } else {
            g.setColor(PLACEHOLDER_COLOR);
            g.setComposite(PLACEHOLDER_ALPHA_HIGHLIGHT);
            g.fillRect(0, 0, Card.CARD_WIDTH, Card.CARD_HEIGHT);
        }
        g.dispose();
    }

    /**
     * Checks to see if the Foundation is empty.
     *
     * @return true, if empty
     */
    public final boolean isEmpty() {
        return cards.isEmpty();
    }

    /*
     * (non-Javadoc)
     *
     * @see group3.SolitairePile#getTopCard()
     */
    @Override
    public final Card getTopCard() {
        if (cards.size() > 0) {
            return cards.get(cards.size() - 1);
        }
        return null;
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
        cards.add(card);
        card.setBounds(0, 0, Card.CARD_WIDTH, Card.CARD_HEIGHT);

        add(card, 0);
    }

    /*
     * (non-Javadoc)
     *
     * @see group3.SolitairePile#removeSingleCard(group3.Card)
     */
    @Override
    public final void removeSingleCard(final Card card) {
        cards.remove(card);
        remove(card);

        repaint();
    }

    /*
     * (non-Javadoc)
     *
     * @see group3.SolitairePile#addCards(java.util.ArrayList)
     */
    @Override
    public void addCards(final ArrayList<Card> c) {
    }

    /*
     * (non-Javadoc)
     *
     * @see group3.SolitairePile#removeCards(java.util.ArrayList)
     */
    @Override
    public void removeCards(final ArrayList<Card> theCards) {

    }

    /*
     * (non-Javadoc)
     *
     * @see group3.SolitairePile#isValidMove(group3.Card)
     */
    @Override
    public final boolean isValidMove(final Card card) {
        if ((isEmpty()) && (card.getRank() == Rank.ACE)) {
            return true;
        }
        if ((!isEmpty()) && (card.getSuit().equals(getTopCard().getSuit()))
                && (card.getRank().ordinal() == getTopCard().getRank().ordinal()
                        + 1)) {
            return true;
        }
        return false;
    }
}
