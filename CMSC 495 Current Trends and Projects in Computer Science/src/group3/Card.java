package group3;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * The Card class represents a playing card. It extends JComponent so it can be
 * displayed directly to the screen. It offers the ability to query its suit,
 * rank, and allows the user to flip the card over. Card images are loaded from
 * a sprite map statically to save memory and speed up card creation.
 * 
 * @author Patrick Smith
 * @version 1.1
 * @since Sep 5, 2016
 */
public class Card extends JComponent {

    /**
     * Generated serial ID.
     */
    private static final long serialVersionUID = -2835610009194889206L;
    /**
     * Statically load the cards, so the card sprite map is only in memory once.
     */
    private static BufferedImage cards = readImage("images/cards_2.png");
    /**
     * Statically load the backs, so the back is only in memory once.
     */
    private static BufferedImage cardBacks = readImage("images/card_backs.png");

    /**
     * Loads a sprite map statically to reduce memory usage of the cards.
     *
     * @param location
     *            the location to load the picture
     * @return the BufferedImage of the picture
     */
    static BufferedImage readImage(String location) {
        System.out.println("Loading in image from Card Class!");
        try {
            return ImageIO.read(Card.class.getClassLoader().getResource(location));
        } catch (IOException e) {
            System.out.println("Error in image load from Card Class!");
            e.printStackTrace();
            throw new Error(e);
        } // end catch
    } // end static method

    /**
     * Gets a reference to the card Back sprite map.
     * 
     * @return the image of card backs.
     */
    public static BufferedImage getBacks() {
        return cardBacks;
    } // end method

    /**
     * The rank of the card.
     */
    private Rank rank;
    /**
     * The suit of the card.
     */
    private Suit suit;
    /**
     * If the card is face up or face down.
     */
    boolean faceUp;
    // URL imageURL;
    // ImageIcon cardBacks;
    /**
     * The image of the back of the card.
     */
    BufferedImage back;
    /**
     * The image of the front of the card.
     */
    BufferedImage front;
    /**
     * The image currently displayed on screen.
     */
    BufferedImage currentImage;

    /**
     * Creates a Card of the given rank and suit and loads the image for the
     * card.
     * 
     * @param rank
     *            the rank of the Card
     * @param suit
     *            the suit of the Card
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        // System.out.println("loading " + rank.ordinal() * 72 + ", " +
        // suit.ordinal() * 96 + "");
        back = cardBacks.getSubimage(72, 0, 72, 96);
        front = cards.getSubimage(rank.ordinal() * 72, suit.ordinal() * 96, 72, 96);
        currentImage = back;
        faceUp = false;
    } // end constructor

    /**
     * Gets the suit of the card.
     * 
     * @return the suit
     */
    public Suit getSuit() {
        return suit;
    } // end method

    /**
     * Gets the rank of the card.
     * 
     * @return the rank
     */
    public Rank getRank() {
        return rank;
    } // end method

    /**
     * Flips the card over on screen.
     */
    void flip() {
        if (faceUp) {
            currentImage = back;
        } else {
            currentImage = front;
        }
        // System.out.println("currentImage: " + currentImage.getWidth() + ", "
        // + currentImage.getHeight());
        try {
            Thread.sleep(100); // pause so you can see the flip
        } catch (InterruptedException e) {
            e.printStackTrace();
        } // end catch
        faceUp = !faceUp;
        repaint(); // update the screen
    } // end method

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#getPreferredSize()
     */
    public Dimension getPreferredSize() {
        if (currentImage == null) {
            return new Dimension(72, 96);
        } else {
            return new Dimension(currentImage.getWidth(null), currentImage.getHeight(null));
        }
    } // end method

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "" + rank + suit;
    } // end method

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    protected void paintComponent(Graphics graphics) {
        super.paintComponents(graphics);
        Graphics g = graphics.create();
        g.drawImage(currentImage, 0, 0, null);
        g.dispose();
    } // end method
} // end class