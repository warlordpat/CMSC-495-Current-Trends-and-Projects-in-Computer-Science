// File: Waste.java
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
public class Waste extends SolitairePile {
    public static final Color PLACEHOLDER_COLOR = Color.WHITE;
    public static final AlphaComposite PLACEHOLDER_ALPHA = AlphaComposite.getInstance(3, 0.05F);
    private ArrayList<Card> cards = new ArrayList();
    private Deck deck;

    /**
     * 
     */
    public Waste(Deck deck) {
        this.deck = deck;

    }

    protected void paintComponent(Graphics graphics) {
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
    public Card getTopCard() {
        return cards.get(cards.size() - 1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see group3.SolitairePile#addSingleCard(group3.Card)
     */
    @Override
    public void addSingleCard(Card card) {
        add(card, 0);
        cards.add(card);
    }

    /*
     * (non-Javadoc)
     * 
     * @see group3.SolitairePile#removeSingleCard(group3.Card)
     */
    @Override
    public void removeSingleCard(Card card) {
        remove(card);
        cards.remove(card);
        repaint();
    }

    /*
     * (non-Javadoc)
     * 
     * @see group3.SolitairePile#isValidMove(group3.Card)
     */
    @Override
    public boolean isValidMove(Card paramCard) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see group3.SolitairePile#getAvailableCardsAt(group3.Card)
     */
    @Override
    public ArrayList<Card> getAvailableCardsAt(Card card) {
        ArrayList<Card> temp = new ArrayList<>();
        temp.add(card);
        return temp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see group3.SolitairePile#addCards(java.util.ArrayList)
     */
    @Override
    public void addCards(ArrayList<Card> cards) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see group3.SolitairePile#removeCards(java.util.ArrayList)
     */
    @Override
    public void removeCards(ArrayList<Card> cards) {
        // TODO Auto-generated method stub

    }

    public void returnCards() {
        removeAll();
        deck.addCards(cards);
        cards.clear();
        repaint();
    }
}
