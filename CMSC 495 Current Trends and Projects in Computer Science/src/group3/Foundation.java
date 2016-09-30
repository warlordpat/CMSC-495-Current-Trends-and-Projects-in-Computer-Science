// File: Foundation.java
// Author: Patrick Smith
// Date: Sep 27, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
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
public class Foundation extends SolitairePile {
    public static final Color PLACEHOLDER_COLOR = Color.WHITE;
    public static final AlphaComposite PLACEHOLDER_ALPHA = AlphaComposite.getInstance(3, 0.05F);
    public static final AlphaComposite PLACEHOLDER_ALPHA_HIGHLIGHT = AlphaComposite.getInstance(3, 0.15F);
    private boolean HIGHLIGHT = false;
    private ArrayList<Card> cards = new ArrayList();

    /**
     * 
     */
    public Foundation() {
        // TODO Auto-generated constructor stub
    }

    public boolean isEmpty() {
        return this.cards.isEmpty();
    }

    public Card getTopCard() {
        if (cards.size() > 0) {
            return cards.get(cards.size() - 1);
        }
        return null;
    }

    public boolean isValidMove(Card card) {
        if ((isEmpty()) && (card.getRank() == Rank.ACE)) {
            return true;
        }
        if ((!isEmpty()) && (card.getSuit().equals(getTopCard().getSuit()))
                && (card.getRank().ordinal() == getTopCard().getRank().ordinal() + 1)) {
            return true;
        }
        return false;
    }

    public void addSingleCard(Card card) {
        cards.add(card);
        card.setBounds(0, 0, Card.CARD_WIDTH, Card.CARD_HEIGHT);

        add(card, 0);
    }
    public void addCards(ArrayList<Card> c){}
    public void removeSingleCard(Card card) {
        cards.remove(card);
        remove(card);

        repaint();
    }

    public ArrayList<Card> getAvailableCardsAt(Card card) {
        ArrayList<Card> temp = new ArrayList<>();
        temp.add(card);
        return temp;
    }

    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        if (!this.HIGHLIGHT) {
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
     * @see group3.SolitairePile#removeCards(java.util.ArrayList)
     */
    @Override
    public void removeCards(ArrayList<Card> cards) {
        
    }
}
