

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
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
    private BufferedImage cardBack;

    // below fields will be removed once games convert to functionality in the
    // Hand class.
    static ImageIcon[] cardIcon = new ImageIcon[52];
    static int[] refArray = new int[52];
    private ArrayList<Integer> playerHand = new ArrayList<>();
    private ArrayList<Integer> aiHand = new ArrayList<>();
    private ArrayList<ImageIcon> playerCards = new ArrayList<>();
    private ArrayList<ImageIcon> AICards = new ArrayList<>();

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
        cardBack = Card.getBacks().getSubimage(72, 0, 72, 96);
    } // end method

    /**
     * Shuffles the deck randomly.
     */
    void shuffle() {
        List<Card> temp = cards;
        cards = new ArrayList<>();
        // Randomly draws a card out of the deck until the temp deck is empty
        // and adds it to the list of cards.
        while (!temp.isEmpty()) {
            int random = rand.nextInt(temp.size());
            Card randomCard = temp.remove(random);
            cards.add(randomCard);
        } // end while

        // Deprecated shuffle and image load code. Left in for now to not break
        // anything.
        {
            for (int j = 1; j <= 52; j++) {

                int position = 0;
                do {
                    position = (int) (Math.random() * 52);
                } while (cardIcon[position] != null);

                URL cardURL = this.getClass().getClassLoader().getResource("images/" + j + ".png");
                cardIcon[position] = new ImageIcon(cardURL);
            }
            String refPath = cardIcon[0].toString();
            String filePath = refPath.substring(0, refPath.lastIndexOf("/"));

            for (int ref2 = 0; ref2 < 52; ref2++) {

                for (int ref = 1; ref <= 52; ref++) {

                    if (cardIcon[ref2].toString().equals(filePath + "/" + ref + ".png")) {

                        if (ref == 1 || ref == 14 || ref == 27 || ref == 40) {
                            refArray[ref2] = 14;
                        }
                        if (ref == 2 || ref == 15 || ref == 28 || ref == 41) {
                            refArray[ref2] = 2;
                        }
                        if (ref == 3 || ref == 16 || ref == 29 || ref == 42) {
                            refArray[ref2] = 3;
                        }
                        if (ref == 4 || ref == 17 || ref == 30 || ref == 43) {
                            refArray[ref2] = 4;
                        }
                        if (ref == 5 || ref == 18 || ref == 31 || ref == 44) {
                            refArray[ref2] = 5;
                        }
                        if (ref == 6 || ref == 19 || ref == 32 || ref == 45) {
                            refArray[ref2] = 6;
                        }
                        if (ref == 7 || ref == 20 || ref == 33 || ref == 46) {
                            refArray[ref2] = 7;
                        }
                        if (ref == 8 || ref == 21 || ref == 34 || ref == 47) {
                            refArray[ref2] = 8;
                        }
                        if (ref == 9 || ref == 22 || ref == 35 || ref == 48) {
                            refArray[ref2] = 9;
                        }
                        if (ref == 10 || ref == 23 || ref == 36 || ref == 49) {
                            refArray[ref2] = 10;
                        }
                        if (ref == 11 || ref == 24 || ref == 37 || ref == 50) {
                            refArray[ref2] = 11;
                        }
                        if (ref == 12 || ref == 25 || ref == 38 || ref == 51) {
                            refArray[ref2] = 12;
                        }
                        if (ref == 13 || ref == 26 || ref == 39 || ref == 52) {
                            refArray[ref2] = 13;
                        }
                    }
                }
            }

            for (int iNum = 0; iNum <= 26; iNum++) {
                playerHand.add(deal(iNum));
                playerCards.add(returnCard(iNum));
                // playerRefArray.add(deck.deal(iNum));
                aiHand.add(deal(iNum + 1));
                AICards.add(returnCard(iNum + 1));
                // AIRefArray.add(deck.deal(iNum + 1));
                // player.addCard(deck.deal());
                // ai.addCard(deck.deal());
            }
        } // end deprecated block
    } // end method

    /**
     * Functionality will be moved into Hand class.
     * 
     * @param tempPlayerCard
     *            image of a card
     */
    @Deprecated
    void addPlayerCards(ImageIcon tempPlayerCard) {
        playerCards.add(tempPlayerCard);
    }

    /**
     * Functionality will be moved into Hand class.
     * 
     * @param cardValue
     *            the value of a card
     */
    @Deprecated
    void addPlayerHand(int cardValue) {
        playerHand.add(cardValue);
    }

    /**
     * Functionality will be moved into Hand class.
     * 
     * @param index
     *            the index of the card to remove
     */
    @Deprecated
    void removePlayerCards(int index) {
        playerCards.remove(index);
    }

    /**
     * Functionality will be moved into Hand class.
     * 
     * @param index
     *            the index of the card to remove
     */
    @Deprecated
    void removePlayerHand(int index) {
        playerHand.remove(index);
    }

    /**
     * Functionality will be moved into Hand class.
     * 
     * @param index
     *            the index of the card to get
     * @return
     */
    @Deprecated
    ImageIcon getPlayerCards(int index) {
        return playerCards.get(index);
    }

    /**
     * Functionality will be moved into Hand class.
     * 
     * @param index
     *            the index of the card to get
     * @return
     */
    @Deprecated
    int getPlayerHand(int index) {
        return playerHand.get(index);
    }

    /**
     * Functionality will be moved into Hand class.
     * 
     * @param tempPlayerCard
     */
    @Deprecated
    void addAICards(ImageIcon tempPlayerCard) {
        AICards.add(tempPlayerCard);
    }

    /**
     * Functionality will be moved into Hand class.
     * 
     * @param cardValue
     */
    @Deprecated
    void addAIHand(int cardValue) {
        aiHand.add(cardValue);
    }

    /**
     * Functionality will be moved into Hand class.
     * 
     * @param index
     */
    @Deprecated
    void removeAICards(int index) {
        AICards.remove(index);
    }

    /**
     * Functionality will be moved into Hand class.
     * 
     * @param index
     *            the index of the card to remove
     */
    @Deprecated
    void removeAIHand(int index) {
        aiHand.remove(index);
    }

    /**
     * Functionality will be moved into Hand class.
     * 
     * @param index
     *            the index of the card to get
     * @return
     */
    @Deprecated
    int getAIHand(int index) {
        return aiHand.get(index);
    }

    /**
     * Functionality will be moved into Hand class.
     * 
     * @param index
     *            the index of the card to get
     * @return
     */
    @Deprecated
    ImageIcon getAICards(int index) {
        return AICards.get(index);
    }

    /**
     * Deals a card from the deck. Functionality will be moved into Hand class.
     * 
     * @return a Card from the top of the deck
     */
    @Deprecated
    int deal(int index) {
        return refArray[index];
    } // end method

    /**
     * Deals a card from the deck.
     * 
     * @return a Card from the top of the deck
     */
    Card deal() {
        return cards.remove(0);
    } // end method

    /**
     * Returns a card image of the card at the given array location.
     * 
     * @param index
     *            the array index
     * @return an image of the card at that index
     */
    @Deprecated
    ImageIcon returnCard(int index) {
        return cardIcon[index];
    }

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