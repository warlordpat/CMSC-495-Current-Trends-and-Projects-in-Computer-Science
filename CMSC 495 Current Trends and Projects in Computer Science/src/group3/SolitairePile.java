// File: SolitairePile.java
// Author: Patrick Smith
// Date: Sep 27, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: TODO
package group3;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JLayeredPane;

/**
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 27, 2016
 */
public abstract class SolitairePile extends JLayeredPane {

    /**
     * 
     */
    private static final long serialVersionUID = 7097803801509131323L;

    /**
     * 
     */
    public SolitairePile() {
        // TODO Auto-generated constructor stub
    }

    public final void transform(Point pp, Point p) {
        setLocation(pp.x - p.x, pp.y - p.y);
    }

    public abstract Card getTopCard();

    public abstract ArrayList<Card> getAvailableCardsAt(Card card);

    public abstract void addSingleCard(Card card);

    public abstract void removeSingleCard(Card card);

    public abstract void addCards(ArrayList<Card> cards);

    public abstract void removeCards(ArrayList<Card> cards);

    public abstract boolean isValidMove(Card card);
}
