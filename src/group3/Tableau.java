// File: Tableau.java
// Author: Patrick Smith
// Date: Sep 27, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: Implements a Tableau.
package group3;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * The Tableaus are the Seven piles that make up the main table in Solitaire.
 *
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 27, 2016
 */
public class Tableau extends SolitairePile {
    /**
     * Generated Serial ID.
     */
    private static final long serialVersionUID = 2600727903176440673L;
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
    public static final AlphaComposite PLACEHOLDER_ALPHA_HIGHLIGHT =
            AlphaComposite.getInstance(3, 0.15F);
    /**
     * Vertical spacing of Cards in Tableaus.
     */
    static final int CARD_SPACING = 25;
    /**
     * Whether to highlight the Foundation by increasing the Alpha blending.
     */
    private boolean highlight = false;
    /**
     * The List of Cards in the Tableau.
     */
    private ArrayList<Card> cards;

    /**
     * Creates a new Tableau and fills it with a given number of Cards from the
     * provided Deck.
     *
     * @param deck
     *            the Deck to deal Cards from
     * @param numCards
     *            the number of Cards to deal
     */
    public Tableau(final Deck deck, final int numCards) {
        cards = new ArrayList<>();
        for (int i = 0; i < numCards; i++) {
            Card tmpCard = deck.deal();
            // System.out.println(tmpCard);
            // System.out.println(tmpCard.getPreferredSize());
            // System.out.println(tmpCard.getSize());
            tmpCard.setBounds(0, i * CARD_SPACING, Card.CARD_WIDTH,
                Card.CARD_HEIGHT);
            // System.out.println("adding card");
            add(tmpCard, 0);
            cards.add(tmpCard);
            if (i >= numCards - 1) {
                tmpCard.flip();
            }
        }
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
        ArrayList<Card> tmp = new ArrayList<>();
        if (!card.isFaceUp()) {
            return null;
        }
        int cardIndex = cards.lastIndexOf(card);
        // returns -1 if the card is not in the list
        if (cardIndex > -1) {
            for (int i = 0; i < cards.size() - cardIndex; i++) {
                tmp.add(cards.get(cardIndex + i));
            }
        }
        return tmp;
    }

    /*
     * (non-Javadoc)
     *
     * @see group3.SolitairePile#addSingleCard(group3.Card)
     */
    @Override
    public final void addSingleCard(final Card card) {
        if (!isEmpty()) {
            int topCardYPosition = (int) getTopCard().getBounds().getY();
            card.setBounds(0, topCardYPosition + CARD_SPACING, Card.CARD_WIDTH,
                Card.CARD_HEIGHT);
            cards.add(card);
            add(card, 0);
        } else {
            card.setBounds(0, 0, Card.CARD_WIDTH, Card.CARD_HEIGHT);
            cards.add(card);
            add(card);
        }
        trimToSize();
    }

    /*
     * (non-Javadoc)
     *
     * @see group3.SolitairePile#removeSingleCard(group3.Card)
     */
    @Override
    public final void removeSingleCard(final Card card) {
        System.out.println("In remove Single card");
        cards.remove(card);
        cards.trimToSize();
        remove(card);
        if (cards.size() > 0) {
            if (!getTopCard().isFaceUp()) {
                getTopCard().flip();
                System.out.println("flipped " + getTopCard());
            }
        }
        trimToSize();
        repaint();
        System.out.println("Card removed");
    }
    /*
     * (non-Javadoc)
     *
     * @see group3.SolitairePile#addCards(java.util.ArrayList)
     */
    @Override
    public final void addCards(final ArrayList<Card> theCards) {
        int topCardYPos = 0;
        int i;
        // int i;
        if (!isEmpty()) {
            topCardYPos = (int) getTopCard().getBounds().getY();
            i = 1;
            for (Card card : theCards) {
                card.setBounds(0, topCardYPos + i * CARD_SPACING,
                    Card.CARD_WIDTH, Card.CARD_HEIGHT);

                cards.add(cards.size(), card);
                add(card, 0);
                i++;
            }
        } else {
            cards.clear();
            i = 0;
            for (Card card : theCards) {
                card.setBounds(0, topCardYPos + i * CARD_SPACING,
                    Card.CARD_WIDTH, Card.CARD_HEIGHT);

                cards.add(cards.size(), card);
                add(card, 0);
                i++;
            }
        }
        trimToSize();
    }
    /*
     * (non-Javadoc)
     *
     * @see group3.SolitairePile#removeCards(java.util.ArrayList)
     */
    @Override
    public final void removeCards(final ArrayList<Card> c) {
        cards.removeAll(c);
        cards.trimToSize();
        for (Card card : c) {
            remove(card);
        }
        if (cards.size() > 0) {
            if (!getTopCard().isFaceUp()) {
                getTopCard().flip();
                System.out.println("flipped " + getTopCard());
            }
        }
        trimToSize();
    }
    /*
     * (non-Javadoc)
     *
     * @see group3.SolitairePile#isValidMove(group3.Card)
     */
    @Override
    public final boolean isValidMove(final Card card) {
        if ((isEmpty()) && (card.getRank().equals(Rank.KING))) {
            return true;
        }
        if ((!isEmpty()) && (card.getRank()
            .ordinal() == getTopCard().getRank().ordinal() - 1)) {
            if (getTopCard().getSuit().equals(card.getSuit())) {
                return false;
            }
            if (((getTopCard().getSuit().equals(Suit.DIAMOND))
                    || (getTopCard().getSuit().equals(Suit.HEART)))
                    && ((card.getSuit().equals(Suit.CLUB))
                            || (card.getSuit().equals(Suit.SPADE)))) {
                return true;
            }
            if (((getTopCard().getSuit().equals(Suit.CLUB))
                    || (getTopCard().getSuit().equals(Suit.SPADE)))
                    && ((card.getSuit().equals(Suit.DIAMOND))
                            || (card.getSuit().equals(Suit.HEART)))) {
                return true;
            }
        }
        return false;
    }
    /**
     * Finds out if the Tableau is empty.
     *
     * @return true, if the Tableau is empty
     */
    public final boolean isEmpty() {
        return cards.isEmpty();
    }
    /**
     * Trims the size of the Tableau on the GUI to the current size of the
     * overlapping Cards.
     */
    public final void trimToSize() {
        if (MainCGS.DEBUGGING) {
            System.out.println("Trimming, card size is: " + cards.size());
        }
        if (cards.size() != 0) {
            setSize(Card.CARD_WIDTH,
                Card.CARD_HEIGHT + cards.size() * CARD_SPACING - CARD_SPACING);
        } else {
            setSize(Card.CARD_WIDTH, Card.CARD_HEIGHT);
        }
    }
}
