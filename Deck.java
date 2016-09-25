package group3;

import java.awt.AlphaComposite;
import java.awt.Color;
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
    private ArrayList<Card> concentrationCards;
    /**
     * True if the deck is empty.
     */
    private boolean isEmpty;
    /**
     * A random number generator, used for shuffling.
     */
    private Random rand;
    /**
     * The image of the back of the card
     */
    transient private BufferedImage cardBack;

    // below fields will be removed once games convert to functionality in the
    // Hand class.
//    static ImageIcon[] cardIcon = new ImageIcon[52];
//    static int[] refArray = new int[52];
//    private ArrayList<Integer> playerHand = new ArrayList<>();
//    private ArrayList<Integer> aiHand = new ArrayList<>();
//    private ArrayList<ImageIcon> playerCards = new ArrayList<>();
//    private ArrayList<ImageIcon> AICards = new ArrayList<>();

    /**
     * Generate a new full, un-shuffled deck. Loads a card back to represent the
     * pile of cards on the playing surface.
     */
    public Deck() {
        setBackground(null);
        setLayout(null);

        rand = new Random();
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            } // end for
        } // end for

        // load card backs from Cards class to reduce image loading error
        // surface.
        cardBack = new Card(Rank.ACE, Suit.CLUB).getBacks().getSubimage(72, 0, 72, 96);
    } // end method

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        ImageIO.write(cardBack, "png", out);
    } // end method
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        cardBack = ImageIO.read(in);
    } // end method
    
    
    /**
     * Shuffles the deck randomly.
     */
    void shuffle() {
        System.out.println("Starting shuffle");
        List<Card> temp = cards;
        cards = new ArrayList<>();
        // Randomly draws a card out of the deck until the temp deck is empty
        // and adds it to the list of cards.
        while (!temp.isEmpty()) {
            int random = rand.nextInt(temp.size());
            Card randomCard = temp.remove(random);
            cards.add(randomCard);
        } // end while
        System.out.println("end of good shuffle");
        
    }
    /**
     * Deals the deck for Concentration. Only requires 30 cards
     */
    void ConcentrationShuffle() {
        
        // Contains 52 cards
        List<Card> temp = cards;
        //cards = new ArrayList<>();
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
        } while (concentrationCards.size() <30);
    }
       
    Card cardFlip(int i) {
        
        return cards.get(i);
        
    }
       
    /**
     * Deals a card from the deck.
     * 
     * @return a Card from the top of the deck
     */
    Card deal() {
        
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
//     * Returns a card image of the card at the given array location.
//     * 
//     * @param index
//     *            the array index
//     * @return an image of the card at that index
//     */
//    @Deprecated
//    ImageIcon returnCard(int index) {
//        return cardIcon[index];
//    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return cards.toString();
    } // end method

    /**
     * Checks the current size of the deck.
     * 
     * @return the current size of the deck
     */
    public int deckSize() {
        return cards.size();
    } // end method

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        if (!isEmpty) {
            g.drawImage(cardBack, 0, 0, null);
        } else {
            g.setColor(Color.WHITE);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05F));
            g.fillRect(0, 0, 79, 123);
        } // end else
        g.dispose();
    } // end method
} // end class