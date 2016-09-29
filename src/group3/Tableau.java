// File: Tableau.java
// Author: Patrick Smith
// Date: Sep 27, 2016
// Course: CMSC 495
// Assignment: TODO
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: TODO
package group3;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 27, 2016
 */
public class Tableau extends SolitairePile {
    /**
     * 
     */
    private static final long serialVersionUID = 2600727903176440673L;
    public static final Color PLACEHOLDER_COLOR = Color.WHITE;
    public static final AlphaComposite PLACEHOLDER_ALPHA = AlphaComposite.getInstance(3, 0.05F);
    public static final AlphaComposite PLACEHOLDER_ALPHA_HIGHLIGHT = AlphaComposite.getInstance(3, 0.15F);
    private final int CARD_SPACING = 25;
    private boolean HIGHLIGHT = false;
    private ArrayList<Card> cards = new ArrayList();

    /**
     * 
     */
    public Tableau(Deck deck, int numCards) {
        for (int i = 0; i < numCards; i++) {
            Card tmpCard = deck.deal();
            // System.out.println(tmpCard);

            // System.out.println(tmpCard.getPreferredSize());
            // System.out.println(tmpCard.getSize());

            tmpCard.setBounds(0, i * CARD_SPACING, Card.CARD_WIDTH, Card.CARD_HEIGHT);
            // System.out.println("adding card");
            add(tmpCard, 0);
            cards.add(tmpCard);
            if (i >= numCards - 1) {
                tmpCard.flip();
            }
        }
    }

    public ArrayList<Card> getAvailableCardsAt(Card card) {
        ArrayList<Card> tmp = new ArrayList();
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

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public Card getTopCard() {
        if (cards.size() > 0) {
            return cards.get(cards.size() - 1);
        }
        return null;
    }

    public void trimToSize() {
        System.out.println("Trimming, card size is: " + cards.size());
        if (cards.size() != 0) {
            setSize(Card.CARD_WIDTH, Card.CARD_HEIGHT + cards.size() * CARD_SPACING - CARD_SPACING);
        } else {
            setSize(Card.CARD_WIDTH, Card.CARD_HEIGHT);
        }
    }

    public void addSingleCard(Card card) {
        if (!isEmpty()) {
            int topCardYPosition = (int) getTopCard().getBounds().getY();
            card.setBounds(0, topCardYPosition + CARD_SPACING, Card.CARD_WIDTH, Card.CARD_HEIGHT);
            cards.add(card);
            add(card, 0);
        } else {
            card.setBounds(0, 0, Card.CARD_WIDTH, Card.CARD_HEIGHT);
            cards.add(card);
            add(card);
        }
        trimToSize();
    }

    public void removeCards(ArrayList<Card> c) {
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

    public void removeSingleCard(Card card) {
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

    public boolean isValidMove(Card card) {
        if ((isEmpty()) && (card.getRank().equals(Rank.KING))) {
            return true;
        }
        if ((!isEmpty()) && (card.getRank().ordinal() == getTopCard().getRank().ordinal() - 1)) {
            if (getTopCard().getSuit().equals(card.getSuit())) {
                return false;
            }
            if (((getTopCard().getSuit().equals(Suit.DIAMOND)) || (getTopCard().getSuit().equals(Suit.HEART)))
                    && ((card.getSuit().equals(Suit.CLUB)) || (card.getSuit().equals(Suit.SPADE)))) {
                return true;
            }
            if (((getTopCard().getSuit().equals(Suit.CLUB)) || (getTopCard().getSuit().equals(Suit.SPADE)))
                    && ((card.getSuit().equals(Suit.DIAMOND)) || (card.getSuit().equals(Suit.HEART)))) {
                return true;
            }
        }
        return false;
    }

    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        if (!HIGHLIGHT) {
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

    /* (non-Javadoc)
     * @see group3.SolitairePile#addCards(java.util.ArrayList)
     */
    @Override
    public void addCards(ArrayList<Card> cards) {
        int topCardYPos = 0;
        int i;
        // int i;
        if (!isEmpty()) {
            topCardYPos = (int) getTopCard().getBounds().getY();
            i = 1;
            for (Card card : cards) {
                card.setBounds(0, topCardYPos + i * CARD_SPACING, Card.CARD_WIDTH, Card.CARD_HEIGHT);

                this.cards.add(this.cards.size(), card);
                add(card, 0);
                i++;
            }
        } else {
            this.cards.clear();
            i = 0;
            for (Card card : cards) {
                card.setBounds(0, topCardYPos + i * CARD_SPACING, Card.CARD_WIDTH, Card.CARD_HEIGHT);

                this.cards.add(this.cards.size(), card);
                add(card, 0);
                i++;
            }
        }
        trimToSize();
    }
}
