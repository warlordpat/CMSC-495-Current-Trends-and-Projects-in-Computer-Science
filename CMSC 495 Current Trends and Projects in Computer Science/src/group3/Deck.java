package group3;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author Patrick Smith
 * @version 1.0
 * @since Aug 26, 2016
 */
public class Deck extends JLabel {

    private List<Card> cards;
    private boolean isEmpty;
    Random rand;

    URL imageURL;
    ImageIcon cardBacks;
    static ImageIcon[] cardIcon = new ImageIcon[52];
    static int[] refArray = new int[52];
    private ArrayList<Integer> playerHand = new ArrayList<>();
    private ArrayList<Integer> aiHand = new ArrayList<>();
    private ArrayList<ImageIcon> playerCards = new ArrayList<>();
    private ArrayList<ImageIcon> AICards = new ArrayList<>();
    private BufferedImage biCardBacks;

    /**
     * Generate a new full, un-shuffled deck.
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
        // System.out.println(System.getProperty("user.dir"));
        // System.out.println(System.getProperty("java.class.path"));
        imageURL = getClass().getClassLoader().getResource("cardBack.png");
        cardBacks = new ImageIcon(imageURL);
        try {
            biCardBacks = ImageIO.read(imageURL);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    } // end method

    /**
     * Shuffles the deck.
     */
    void addPlayerCards(ImageIcon tempPlayerCard) {
        playerCards.add(tempPlayerCard);
    }

    void addPlayerHand(int cardValue) {
        playerHand.add(cardValue);
    }

    void removePlayerCards(int index) {
        playerCards.remove(index);
    }

    void removePlayerHand(int index) {
        playerHand.remove(index);
    }

    ImageIcon getPlayerCards(int index) {
        return playerCards.get(index);
    }

    int getPlayerHand(int index) {
        return playerHand.get(index);
    }

    void addAICards(ImageIcon tempPlayerCard) {
        AICards.add(tempPlayerCard);
    }

    void addAIHand(int cardValue) {
        aiHand.add(cardValue);
    }

    void removeAICards(int index) {
        AICards.remove(index);
    }

    void removeAIHand(int index) {
        aiHand.remove(index);
    }

    int getAIHand(int index) {
        return aiHand.get(index);
    }

    ImageIcon getAICards(int index) {
        return AICards.get(index);
    }

    void shuffle() {
        List<Card> temp = cards;
        cards = new ArrayList<>();
        while (!temp.isEmpty()) {
            int random = rand.nextInt(temp.size());
            Card randomCard = temp.remove(random);
            cards.add(randomCard);
        }

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

    } // end method

    /**
     * Deals a card from the deck.
     * 
     * @return a Card from the top of the deck
     */
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

    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        if (!isEmpty) {
            g.drawImage(biCardBacks, 0, 0, null);
        } else {
            g.setColor(Color.WHITE);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05F));
            g.fillRect(0, 0, 79, 123);
        }
        g.dispose();
    }
} // end class