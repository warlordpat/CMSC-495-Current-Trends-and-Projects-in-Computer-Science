// File: Card.java
// Author: Patrick Smith
// Date: Sep 5, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: implements a graphical Card to use in the CGS.
package group3;

//TODO Hide fields?
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
     * The maximum length of a Card String representation. Used to pad toString
     * output to line up names when printed to the console.
     */
    private static final int MAX_LEN = 10;
    /**
     * Amount of time to pause between card movements, in ms.
     */
    private static final int PAUSE_TIME = 50;
    /**
     * Generated serial ID.
     */
    private static final long serialVersionUID = -2835610009194889206L;
    /**
     * The height of a card, 96 px.
     */
    public static final int CARD_HEIGHT = 96;
    /**
     * The width of a card, 72 px.
     */
    public static final int CARD_WIDTH = 72;

    /**
     * Loads a sprite map statically to reduce memory usage of the cards.
     *
     * @param location
     *            the location to load the picture
     * @return the BufferedImage of the picture
     */
    static BufferedImage readImage(final String location) {
        // System.out.println("Loading in image from Card Class!");
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
    public final BufferedImage getBacks() {
        return readImage("images/card_backs.png");
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
    private boolean faceUp;
    // URL imageURL;
    // ImageIcon cardBacks;
    /**
     * The image of the back of the card.
     */
    private transient BufferedImage back;
    /**
     * The image of the front of the card.
     */
    private transient BufferedImage front;
    /**
     * The image currently displayed on screen.
     */
    private transient BufferedImage currentImage;

    /**
     * Creates a Card of the given rank and suit and loads the image for the
     * card.
     *
     * @param rank
     *            the rank of the Card
     * @param suit
     *            the suit of the Card
     */
    public Card(final Rank rank, final Suit suit) {
        this.rank = rank;
        this.suit = suit;
        setSize(CARD_WIDTH, CARD_HEIGHT);
        // System.out.println("loading " + rank.ordinal() * 72 + ", " +
        // suit.ordinal() * 96 + "");
        back = getBacks().getSubimage(CARD_WIDTH, 0, CARD_WIDTH, CARD_HEIGHT);
        front = readImage("images/cards_2.png").getSubimage(rank.ordinal() * CARD_WIDTH, suit.ordinal() * CARD_HEIGHT,
                CARD_WIDTH, CARD_HEIGHT);
        currentImage = back;
        faceUp = false;
    } // end constructor

    /**
     * Gets the suit of the card.
     *
     * @return the suit
     */
    public final Suit getSuit() {
        return suit;
    } // end method

    /**
     * Gets the rank of the card.
     *
     * @return the rank
     */
    public final Rank getRank() {
        return rank;
    } // end method

    /**
     * Flips the card over on screen.
     */
    final void flip() {
        if (faceUp) {
            currentImage = back;
        } else {
            currentImage = front;
        }
        // System.out.println("currentImage: " + currentImage.getWidth() + ", "
        // + currentImage.getHeight());
        try {
            Thread.sleep(PAUSE_TIME); // pause so you can see the flip
        } catch (InterruptedException e) {
            e.printStackTrace();
        } // end catch
        faceUp = !faceUp;
        repaint(); // update the screen
    } // end method

    /**
     * Serializes the Card.
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
        // ImageIO.write(cards, "png", out);
        // ImageIO.write(cardBacks, "png", out);
        // ImageIO.write(back, "png", out);
        // ImageIO.write(front, "png", out);
        // ImageIO.write(currentImage, "png", out);
    } // end method

    /**
     * Reads back in the Card from serialization.
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
        // cards = ImageIO.read(in);
        // cardBacks = ImageIO.read(in);
        back = getBacks().getSubimage(CARD_WIDTH, 0, CARD_WIDTH, CARD_HEIGHT);
        front = readImage("images/cards_2.png").getSubimage(rank.ordinal() * CARD_WIDTH, suit.ordinal() * CARD_HEIGHT,
                CARD_WIDTH, CARD_HEIGHT);
        if (faceUp) {
            currentImage = front;
        } else {
            currentImage = back;
        }

        // currentImage = ImageIO.read(in);
    } // end method

    /*
     * @ (non-Javadoc)
     *
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public final Dimension getPreferredSize() {
        if (currentImage == null) {
            return new Dimension(CARD_WIDTH, CARD_HEIGHT);
        } else {
            return new Dimension(currentImage.getWidth(null), currentImage.getHeight(null));
        }
    } // end method

    /*
     * (non-Javadoc) Pads the output to the max Card string length for
     * view-ability.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        String temp = "" + rank + suit;
        int len = temp.length();
        if (len < MAX_LEN) {
            int pad = MAX_LEN - len;
            for (int i = 0; i < pad; i++) {
                temp += " ";
            } // end for
        } // end if
        return temp;
    } // end method

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected final void paintComponent(final Graphics graphics) {
        super.paintComponents(graphics);
        Graphics g = graphics.create();
        g.drawImage(currentImage, 0, 0, null);
        g.dispose();
    } // end method

    /**
     * Find out if the Card is face up.
     *
     * @return true, if the Card is face up
     */
    public final boolean isFaceUp() {
        return faceUp;
    }

    /**
     * Gets a BufferedImage of the Card back.
     *
     * @return The Card back
     */
    public final BufferedImage getBack() {
        return back;
    }

    /**
     * Gets a BufferedImage of the Card front.
     *
     * @return The Card front
     */
    public final BufferedImage getFront() {
        return front;
    }
} // end class
