package group3;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 5, 2016
 */
public class Card extends JComponent {

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
            // TODO Auto-generated catch block
            System.out.println("Error in image load from Card Class!");
            e.printStackTrace();
            throw new Error(e);
        } // end catch
    } // end static method

    /**
     * The rank of the card.
     */
    private Rank rank;
    /**
     * The suit of the card.
     */
    private Suit suit;
    boolean faceUp;
    // URL imageURL;
    // ImageIcon cardBacks;
    BufferedImage back;
    BufferedImage front;
    BufferedImage currentImage;

    /**
     * Creates a Card of the given rank and suit.
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        // imageURL =
        // getClass().getClassLoader().getResource("images/card_backs.png");
        // cardBacks = new ImageIcon(imageURL);
        // int suitRow = suit.ordinal() + 1;
        // int rankCol = rank.ordinal() + 1;
        // URL cardURL = getClass().getClassLoader().getResource("images/" +
        // suitRow * rankCol + ".png");
        // URL cardURL =
        // getClass().getClassLoader().getResource("images/cards_2.png");
        // System.out.println("loading " + suitRow * rankCol + ".png");
        System.out.println("loading " + rank.ordinal() * 72 + ", " + suit.ordinal() * 96 + "");
        back = cardBacks.getSubimage(72, 0, 72, 96);
        front = cards.getSubimage(rank.ordinal() * 72, suit.ordinal() * 96, 72, 96);
        // System.out.println("back: " + back.getWidth() + ", " +
        // back.getHeight());
        // System.out.println("front: " + front.getWidth() + ", " +
        // front.getHeight());
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

    void flip() {
        if (faceUp) {
            currentImage = back;
        } else {
            currentImage = front;
        }
        // System.out.println("currentImage: " + currentImage.getWidth() + ", "
        // + currentImage.getHeight());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // setPreferredSize(new Dimension(currentImage.getWidth(null),
        // currentImage.getHeight(null)));
        faceUp = !faceUp;
        repaint();
    } // end method

    public Dimension getPreferredSize() {
        if (currentImage == null) {
            return new Dimension(100, 100);
        } else {
            return new Dimension(currentImage.getWidth(null), currentImage.getHeight(null));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "" + rank + suit;
    } // end method

    protected void paintComponent(Graphics graphics) {
        super.paintComponents(graphics);
        Graphics g = graphics.create();
        g.drawImage(currentImage, 0, 0, null);
        // g.drawImage(currentImage, 0, 0, currentImage.getWidth(null),
        // currentImage.getHeight(null), null);
        g.dispose();
    } // end method
} // end class