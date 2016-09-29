// File: Deck.java
// Author: Patrick Smith
// Date: Sep 5, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: implements a graphical Deck for card games.
package group3;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

/**
 * @author Patrick Smith
 * @version 1.0
 * @since Aug 26, 2016
 */
public class Deck extends JLabel {

    /**
     * The constant alpha to be multiplied with the alpha of the source. alpha
     * must be a floating point number in the inclusive range [0.0, 1.0].
     */
    private static final float ALPHA = 0.05F;
    /**
     * Generated serial ID.
     */
    private static final long serialVersionUID = -9054158652274680167L;
    /**
     * The Cards held in this deck.
     */
    private List<Card> cards;
    /**
     * The Cards held in the Concentration deck.
     */
    ArrayList<Card> concentrationCards;
    /**
     * True if the deck is empty.
     */
    private boolean isEmpty;
    /**
     * A random number generator, used for shuffling.
     */
    private Random rand;
    /**
     * The image of the back of the card.
     */
    private transient BufferedImage cardBack;

    /**
     * Generate a new full, un-shuffled deck. Loads a card back to represent the
     * pile of cards on the playing surface.
     */
    public Deck() {
        setBackground(null);
        setLayout(null);

        rand = new Random();
        cards = new ArrayList<>();
        setPreferredSize(new Dimension(Card.CARD_WIDTH, Card.CARD_HEIGHT));
        setMinimumSize(getPreferredSize());
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            } // end for
        } // end for

        // load card backs from Cards class to reduce image loading error
        // surface.
        cardBack = new Card(Rank.ACE, Suit.CLUB).getBacks().getSubimage(Card.CARD_WIDTH, 0, Card.CARD_WIDTH,
                Card.CARD_HEIGHT);
    } // end method

    /**
     * Serializes the Deck.
     *
     * See readObject() and writeObject() in JComponent for more information
     * about serialization in Swing.
     *
     * @param out
     *            the <code>ObjectOutputStream</code> in which to write
     * @throws IOException
     *             if I/O errors occur while writing to the OutputStream
     */
    private void writeObject(final ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        ImageIO.write(cardBack, "png", out);
    } // end method

    /**
     * Reads back in the Deck from serialization.
     *
     * See readObject() and writeObject() in JComponent for more information
     * about serialization in Swing.
     *
     * @param in
     *            the <code>ObjectInputStream</code> from which to read
     * @throws IOException
     *             if an I/O error occurs
     * @throws ClassNotFoundException
     *             if the class of a serialized object could not be found
     */
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        cardBack = ImageIO.read(in);
    } // end method

    /**
     * Shuffles the deck randomly.
     */
    final void shuffle() {
        if (MainCGS.DEBUGGING) {
            System.out.println("Starting shuffle");
        }
        List<Card> temp = cards;
        cards = new ArrayList<>();
        // Randomly draws a card out of the deck until the temp deck is empty
        // and adds it to the list of cards.
        while (!temp.isEmpty()) {
            int random = rand.nextInt(temp.size());
            Card randomCard = temp.remove(random);
            cards.add(randomCard);
        } // end while
        if (MainCGS.DEBUGGING) {
            System.out.println("end of good shuffle");
            System.out.println("shuffle complete");
        }
    } // end method

    /**
     * Deals the deck for Concentration. Only requires 30 cards
     */
    void ConcentrationShuffle() {

        // Contains 52 cards
        List<Card> temp = cards;
        // cards = new ArrayList<>();
        concentrationCards = new ArrayList<>();
        // Contains 30 cards
        ArrayList<Card> temp30 = new ArrayList<>();
        int i = 0;
        // Draws 15 cards in order, twice
        for (int j = 20; j < 35; j++) {

            temp30.add(i, temp.get(j));
            temp30.add((i + 1), temp.get(j));
            i += 2;
        }

        do {
            int position = 0;
            position = (int) (Math.random() * 30);

            if (temp30.get(position) != null) {
                concentrationCards.add(temp30.get(position));
                temp30.set(position, null);
            }
        } while (concentrationCards.size() < 30);
    }

    Card cardFlip(int i) {
        return cards.get(i);
    }

    /**
     * Deals a card from the deck.
     *
     * @return a Card from the top of the deck
     */
    final Card deal() {
        if (cards.size() == 1) {
            // we are about to remove the last card
            isEmpty = true;
        }
        return cards.remove(0);
    } // end method

    /**
     * Gets a card from the deck without removing it
     * 
     * @return a Card from the top of the deck
     */
    Card concentrationDeal(int i) {
        return concentrationCards.get(i);
    } // end method

    /**
     * Converts a Deck to a String representation.
     *
     * @see java.lang.Object#toString()
     * @return A String representation of a Deck
     */
    public final String toString() {
        return cards.toString();
    } // end method

    /**
     * Checks the current size of the deck.
     *
     * @return the current size of the deck
     */
    public final int deckSize() {
        return cards.size();
    } // end method

    /**
     * Draws the hand based on the size of the Hand.
     *
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     * @param graphics
     *            the Graphics object to protect
     */
    protected final void paintComponent(final Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        if (!isEmpty) {
            g.drawImage(cardBack, 0, 0, null);
        } else {
            g.setColor(Color.WHITE);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ALPHA));
            g.fillRect(0, 0, Card.CARD_WIDTH, Card.CARD_HEIGHT);
        } // end else
        g.dispose();
    } // end method

    /**
     * Adds cards back into the back of the deck.
     *
     * @param cards
     *            An ArrayList of Cards
     */
    public void addCards(ArrayList<Card> addCards) {
        if (addCards != null && addCards.size() > 0) {
            isEmpty = false;
        }
        for (Card card : addCards) {
            cards.add(card);
        }

    }
} // end class
