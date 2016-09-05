package group3;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Hand extends JLabel {

    private static Map<Rank, Integer> values = new HashMap<>();
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
    private List<Card> cards;
    private BufferedImage biCardBacks;
    URL imageURL;
    ImageIcon cardBacks;

    /**
     * 
     */
    public Hand() {
        cards = new ArrayList<>();
        setBackground(null);
        setLayout(null);
        imageURL = getClass().getClassLoader().getResource("cardBack.png");
        cardBacks = new ImageIcon(imageURL);
        try {
            biCardBacks = ImageIO.read(imageURL);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Adds a card to the hand (hit).
     * 
     * @param card
     *            The card to add
     */
    void addCard(Card card) {
        card.setBounds(cards.size() * 79, 0, 71, 96);
        cards.add(card);
        
        add(card, 0); // add the card to the hand Label
        System.out.println("card added to hand and label");
        if (cards.size() != 0) {
            setSize(79 + (cards.size()- 1) * 79, 96); // resize the dealer box to the size of the hand
          } else {
            setSize(79, 123);
          }
        repaint();
    } // end method

    /**
     * @return
     */
    int scoreHand() {
        int sum = 0;
        boolean hasAce = false;
        for (Card card : cards) {
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
     * Checks if the hand is busted.
     * 
     * @return true, if busted
     */
    boolean isBusted() {
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
     * 
     */
    public void returnCards() {
        while (cards.size() > 0) {
            cards.remove(0);
        }
    }

    public int handSize() {
        return cards.size();
    }

    public int total() {
        int iTotal = 0;
        for (Card card : cards) {
            iTotal += values.get(card.getRank());
        }
        return iTotal;
    }

    public Card removeCard() {
        return cards.remove(0);
    }

    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        g.setColor(Color.WHITE);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05F));
        g.fillRect(0, 0, 2 * 79, 123);
        g.dispose();
    }
} // end class