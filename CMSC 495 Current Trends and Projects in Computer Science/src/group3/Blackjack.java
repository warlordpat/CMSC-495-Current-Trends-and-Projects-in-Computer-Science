package group3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.*;
import javax.swing.*;

/**
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 5, 2016
 */
public class Blackjack extends JPanel {
    /**
     * 
     */
    private static final String REMAIN = "Remaining ";
    /**
     * 
     */
    private static final long serialVersionUID = 510809952979347694L;
    /**
     * The current deck in play.
     */
    private Deck deck;
    private Hand dealer, player;
    /**
     * A hand to hold the player's split hand cards.
     */
    private Hand playerSplitHand;
    /**
     * If the hand is still in play.
     */
    boolean handInPlay;
    /**
     * If the hand was split.
     */
    @SuppressWarnings("unused")
    private boolean wasSplit;
    /**
     * If the player's first hand is complete.
     */
    @SuppressWarnings("unused")
    private boolean doneHand1;
    /**
     * If the second hand is still in play.
     */
    @SuppressWarnings("unused")
    private boolean handTwoInPlay;
    private boolean splitTest = false;
    /**
     * The GUI element that displays the number of cards remaining in the deck.
     */
    private JLabel remaining;
    private JButton jbSplit;

    /**
     * 
     */
    public Blackjack() {
        setLayout(null);
        setBackground(new Color(7, 99, 36)); // Card Table Green

    }

    public void begin() {
        System.out.println("beginning");
        patrickGUI();

        newGame();
        deal();
    }

    void newGame() {
        deck = new Deck();
        deck.shuffle();
        deck.setBounds(60, 35, 79, 96); // set the deck size
        remaining = new JLabel(REMAIN + deck.deckSize());
        remaining.setBounds(60 + 72, 30, 80, 50);
        add(remaining);
        System.out.println("Adding Deck");
        add(deck); // add the deck to the play surface

        System.out.println("Adding Dealer");
        dealer = new Hand();
        dealer.setBounds(400, 100, (2 * 79), 96);
        add(dealer);
        player = new Hand();

        handInPlay = false;
        repaint();
    }

    public String display() {
        String output = "";
        if (handInPlay) {
            output += "\nPlayer Hand: " + player;
            List<Card> dealerCards = dealer.getCards();
            String dealerString = "[";
            for (int i = 0; i < dealerCards.size(); i++) {
                if (i == 1) {
                    dealerString += "FACEDOWN";
                } else {
                    dealerString += dealerCards.get(i);
                } // end else
                if (i < dealerCards.size() - 1) {
                    dealerString += ", ";
                } else {
                    dealerString += "]";
                } // end else
            } // end for
            output += "\nDealer Hand: " + dealerString;
        } else {
            output += "Player Hand: " + player + " " + player.scoreHand();
            output += "\nDealer Hand: " + dealer + " " + dealer.scoreHand();
        }
        return output;
    }

    /**
     * Deals out initial hand in BlackJack. Shuffles the deck when the number of
     * Cards left gets low.
     */
    public void deal() {
        if (deck.deckSize() < 10) {
            System.out.println("reshuffling");
            deck = new Deck();
            deck.shuffle();
            JOptionPane.showMessageDialog(this, "Reshuffling...");
        }
        if (!handInPlay) {
            System.out.println("Dealing new hand");
            remove(dealer);
            dealer = new Hand();
            dealer.setBounds(200, 100, (2 * 72), 96); // adds a new dealer hand
                                                      // to the GUI
            add(dealer);

            player = new Hand();
            player.setBounds(200, 300, (2 * 72), 96); // adds a new player hand
                                                      // to the GUI
            add(player);

            {
                addCardFaceUp(player);

                // try {
                // Thread.sleep(200);
                // } catch (InterruptedException e) {
                // e.printStackTrace();
                // }

                addCardFaceUp(dealer);

                // try {
                // Thread.sleep(200);
                // } catch (InterruptedException e) {
                // e.printStackTrace();
                // }

                addCardFaceUp(player);

                // try {
                // Thread.sleep(200);
                // } catch (InterruptedException e) {
                // e.printStackTrace();
                // }

                dealer.addCard(deck.deal());
                updateRemain();
            }
            System.out.println(dealer);
            if (isSplittable()) {
                add(jbSplit);
                jbSplit.setVisible(true);
            } else {
                jbSplit.setVisible(false);
            }
            handInPlay = true;
            repaint();
        }
    }

    /**
     * Adds a card to the given player and flips it face up.
     * 
     * @param player
     *            the player to give the card to
     */
    private void addCardFaceUp(Hand player) {
        Card temp;

        if (splitTest) {
            temp = new Card(Rank.ACE, Suit.CLUB);
        } else {
            temp = deck.deal();
        }
        updateRemain();
        player.addCard(temp);
        temp.flip();
    } // end method

    /**
     * Updates the card remaining in the deck GUI element.
     */
    private void updateRemain() {
        remaining.setText(REMAIN + deck.deckSize());
    }

    public void hit() {
        System.out.println("hitting");
        if (handInPlay) {
            Card card = deck.deal();
            updateRemain();
            card.flip();
            System.out.println(card);
            player.addCard(card);

            // try {
            // Thread.sleep(200);
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }

            // repaint();

            int answer = -1;
            System.out.println("Bust checking");
            if (player.isBusted()) {
                System.out.println("Getting answer");
                answer = JOptionPane.showConfirmDialog(this, "Busted! You lose this hand\nDeal again?", "Deal again?",
                        JOptionPane.YES_NO_OPTION);
                System.out.println("");
                handInPlay = false;
            } // end if
            if (answer == JOptionPane.YES_OPTION) {
                reset();
            }
        } // end if
    } // end method

    public void stand() {
        // stand only matters if the hand is still in play
        if (handInPlay) {
            dealer.getCard(1).flip();
            while (dealer.scoreHand() <= 17) {
                Card card = deck.deal();
                updateRemain();
                dealer.addCard(card);
                card.flip();
            }
            handInPlay = false;
            int answer;
            if (dealer.scoreHand() >= player.scoreHand() && !dealer.isBusted()) {
                answer = JOptionPane.showConfirmDialog(this, "You lose this hand\nDeal again?", "Deal again?",
                        JOptionPane.YES_NO_OPTION);
            } else {
                answer = JOptionPane.showConfirmDialog(this, "You win this hand\nDeal again?", "Deal again?",
                        JOptionPane.YES_NO_OPTION);
            }
            if (answer == JOptionPane.YES_OPTION) {
                reset();
            }
        }
    }

    /**
     * 
     */
    private void reset() {
        System.out.println("Answered yes");
        remove(player);
        remove(dealer);
        if (handTwoInPlay) {
            remove(playerSplitHand);
        }
        System.out.println("removed player/dealer");
        revalidate();
        repaint();
        System.out.println("making deal");
        deal();
        System.out.println("finished deal");
    } // end method

    /**
     * Determines if the players hand can be split.
     * 
     * @return yes, if the players hand can be split
     */
    boolean isSplittable() {
        if (player.getCards().size() == 2 && player.getCard(0).getRank().equals(player.getCard(1).getRank())) {
            return true;
        }
        return false;
    } // end method

    /**
     * Splits the player's hand into two separate hands.
     */
    void split() {
        wasSplit = true;
        playerSplitHand = new Hand();
        handTwoInPlay = true;
        playerSplitHand.addCard(player.removeCard());
        // now add the new hand to the GUI
        playerSplitHand.setBounds(200, 300 + 100, (1 * 72), 96);
        add(playerSplitHand);
        revalidate();
        repaint();
    } // end method

    public boolean isHandInPlay() {
        return handInPlay;
    }

    /**
     * New BlackJack GUI
     */
    public void patrickGUI() {
        JFrame frame = new JFrame("Black Jack");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setTitle("CGS BlackJack");
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setResizable(false);
        frame.setContentPane(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        int BUTTON_SIZE = 80;
        {
            JButton hit = new JButton("Hit");
            hit.setFont(new Font("Courier Bold", Font.BOLD, 17));
            // hit.setBackground(new Color(192, 108, 108));
            hit.setBounds(60, 200, BUTTON_SIZE, BUTTON_SIZE);
            add(hit);
            hit.addActionListener(al -> {
                hit();
                System.out.println("finished hit button processing");
            });
        }

        {
            JButton stand = new JButton("Stand");
            stand.setFont(new Font("Courier Bold", Font.BOLD, 17));
            // stand.setBackground(new Color(192, 108, 108));
            stand.setBounds(60, 200 + BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE);
            add(stand);
            stand.addActionListener(al -> {
                stand();
                System.out.println("finished stand button processing");
            });
        }
        {
            jbSplit = new JButton("Split");
            jbSplit.setFont(new Font("Courier Bold", Font.BOLD, 17));
            // split.setBackground(new Color(192, 108, 108));
            jbSplit.setBounds(60, 200 + 2 * BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE);
            // add(split);
            jbSplit.addActionListener(al -> {
                if (isSplittable()) {
                    split();
                    remove(jbSplit);
                }
                System.out.println("finished split button processing");
            });
        }

    }

    public void gui() {
        JFrame frame = new JFrame("Black Jack");
        frame.setVisible(true);
        JSplitPane splitPaneHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JPanel TopPanel = new JPanel();
        TopPanel.setSize(600, 400);
        JTextArea jtaDisplay = new JTextArea(30, 20);
        jtaDisplay.append(display());
        TopPanel.add(jtaDisplay);
        JPanel BottomPanel = new JPanel(new BorderLayout());
        BottomPanel.setSize(600, 100);
        JButton jbHit = new JButton("Hit");
        jbHit.addActionListener(ae -> {
            hit();
            jtaDisplay.append(display());
            if (player.isBusted()) {
                jtaDisplay.append("\nPress 'Deal' to play again");
            }
        });
        JButton jbStand = new JButton("Stand");
        jbStand.addActionListener(ae -> {
            stand();
            jtaDisplay.append(display());
            jtaDisplay.append("\nPress 'Deal' to play again");
        });
        JButton jbDeal = new JButton("Deal");
        jbDeal.addActionListener(ae -> {
            deal();
            jtaDisplay.append(display());
        });
        BottomPanel.add(jbDeal, BorderLayout.LINE_START);
        BottomPanel.add(jbHit, BorderLayout.CENTER);
        BottomPanel.add(jbStand, BorderLayout.LINE_END);
        splitPaneHorizontal.setTopComponent(TopPanel);
        splitPaneHorizontal.setBottomComponent(BottomPanel);
        frame.add(splitPaneHorizontal);
        frame.setSize(600, 600);
    }

    /**
     * Runs a test of BlackJack by itself.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Blackjack blackjack = new Blackjack();
        blackjack.begin();
    } // end method
} // end class