// File: SolitairePile.java
// Author: Patrick Smith
// Date: Sep 27, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: Common interface for all card piles in Solitaire.
package group3;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JLayeredPane;

/**
 * Common interface for all card piles in Solitaire. Contains methods needed to
 * manipulate Piles.
 *
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 27, 2016
 */
public abstract class SolitairePile extends JLayeredPane {

    /**
     * The generated Serial ID.
     */
    private static final long serialVersionUID = 7097803801509131323L;

    /**
     * Moves a pile using an offset point.
     *
     * @param pp
     *            the offset point
     * @param p
     *            the current point
     */
    public final void transform(final Point pp, final Point p) {
        setLocation(pp.x - p.x, pp.y - p.y);
    }

    /**
     * Gets the top Card on the Pile.
     *
     * @return the top Card
     */
    public abstract Card getTopCard();

    /**
     * Gets a list of the Cards available to move, based on the location of
     * given Card.
     *
     * @param card
     *            the Card the user clicked on
     * @return the list of Cards that can be moved
     */
    public abstract ArrayList<Card> getAvailableCardsAt(Card card);

    /**
     * Adds a Card to the pile.
     *
     * @param card
     *            the Card to add
     */
    public abstract void addSingleCard(Card card);

    /**
     * Removes a Card from the pile.
     *
     * @param card
     *            the Card to remove
     */
    public abstract void removeSingleCard(Card card);

    /**
     * Adds a list of Cards to the pile.
     *
     * @param cards
     *            the Cards to add
     */
    public abstract void addCards(ArrayList<Card> cards);

    /**
     * Removes a list of Cards from the pile.
     *
     * @param cards
     *            the Cards to remove
     */
    public abstract void removeCards(ArrayList<Card> cards);

    /**
     * Checks if adding the Card to this pile is a valid move.
     *
     * @param card
     *            the Card we want to add
     * @return true, if the move is valid
     */
    public abstract boolean isValidMove(Card card);
}
