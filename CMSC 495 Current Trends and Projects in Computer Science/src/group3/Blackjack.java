// File: Blackjack.java
// Author: Patrick Smith
// Date: Sep 14, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: implements a graphical game of Blackjack.

package group3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Blackjack implements a graphical game of Blackjack. The game includes the
 * ability to bet, deal, hit, stand, and split the hand.
 *
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 5, 2016
 */
public class Blackjack extends JPanel implements Game {
    /**
     * The size of the panel that holds the betting buttons.
     */
    private static final Dimension BET_PANEL_SIZE = new Dimension(263, 50);
    /**
     * The y offset of the arrow in the label.
     */
    private static final int ARROW_Y_OFFSET = 10;
    /**
     * The height of the arrow tip.
     */
    private static final int ARROW_TIP_HEIGHT = 10;
    /**
     * The width of the arrow tip.
     */
    private static final int ARROW_TIP_WIDTH = 20;
    /**
     * Width of the arrow.
     */
    private static final int ARROW_WIDTH = 75;
    /**
     * Bet panel location.
     */
    private static final Point BET_PANEL_LOC = new Point(375, 480);
    /**
     * Split hand arrow location.
     */
    private static final Point SPLIT_ARROW_LOC = new Point(125, 400);
    /**
     * The x coordinate of the money info.
     */
    private static final int BANK_X = 650;
    /**
     * Cash label location.
     */
    private static final Point CASH_LOC = new Point(BANK_X, 480);
    /**
     * Balance label location.
     */
    private static final Point BALANCE_TEXT_LOC = new Point(BANK_X, 506);
    /**
     * Bet text location.
     */
    private static final Point BET_TEXT_LOC = new Point(BANK_X, 431);
    /**
     * Bet amount location.
     */
    private static final Point BET_LOC = new Point(BANK_X, 445);
    /**
     * The location of the instructions on screen.
     */
    private static final Point INSTRUCTIONS_LOC = new Point(412, 100);
    /**
     * The number of bet denominations.
     */
    private static final int BET_BUTTONS = 4;
    /**
     * Y offset between regular and split hands.
     */
    private static final int SPLIT_HAND_OFFSET = 50;
    /**
     * Y coordinate of the dealer hand.
     */
    private static final int DEALER_Y = 100;
    /**
     * Font size of the initial instructions.
     */
    private static final int INSTRUCTIONS_FONT_SIZE = 30;
    /**
     * Y Spacing between the two hands.
     */
    private static final int HAND_SPACING = 200;
    /**
     * Y coordinate of the button panel.
     */
    private static final int PANEL_Y = 200;
    /**
     * Y coordinate of the dealer hand.
     */
    private static final int HAND_Y = 100;
    /**
     * Size of player cash font.
     */
    private static final int CASH_FONT_SIZE = 16;
    /**
     * Size of player bank info font.
     */
    private static final int BANK_FONT_SIZE = 11;
    /**
     * Size of table stencil font.
     */
    private static final int STENCIL_SIZE = 20;
    /**
     * The x coordinate of the deck.
     */
    private static final int DECK_X = 60;
    /**
     * The y coordinate of the deck.
     */
    private static final int DECK_Y = 35;
    /**
     * The minimum screen height.
     */
    private static final int SCREEN_Y = 600;
    /**
     * The minimum screen width.
     */
    private static final int SCREEN_X = 800;
    /**
     * A green color like a card table.
     */
    private static final Color CARD_TABLE_GREEN = new Color(7, 99, 36);
    /**
     * The threshold at which the dealer stands.
     */
    private static final int DEALER_THRESHOLD = 17;
    /**
     * The size of the square buttons.
     */
    private static final int BUTTON_SIZE = 80;
    /**
     * The font size of most GUI elements.
     */
    private static final int FONT_SIZE = 17;
    /**
     * The y coordinate of the arrow.
     */
    private static final int ARROW_Y = 300;
    /**
     * The x coordinate of the arrow.
     */
    private static final int ARROW_X = 125;
    /**
     * The x coordinate of a hand.
     */
    private static final int HAND_X = 200;
    /**
     * Value label location.
     */
    private static final Point VALUE_LOC = new Point(HAND_X, 275);
    /**
     * Split hand value label location.
     */
    private static final Point SPLIT_VALUE_LOC = new Point(HAND_X, 458);
    /**
     * Table stencil text location.
     */
    private static final Point STENCIL_LOC = new Point(HAND_X, 235);
    /**
     * The height of a card.
     */
    private static final int CARD_HEIGHT = Card.CARD_HEIGHT;
    /**
     * The width of a card.
     */
    private static final int CARD_WIDTH = Card.CARD_WIDTH;
    /**
     * The threshold below which a reshuffle is initiated.
     */
    private static final int RESHUFFLE_THRESHOLD = 10;
    /**
     * The player's starting cash.
     */
    private static final int INITIAL_CASH = 1000;
    /**
     * The text to show in the label for the deck.
     */
    private static final String REMAIN = "Remaining ";
    /**
     * Blackjack pays 3:2, which is 1.5 times more back than bet. This is a
     * total return of 2.5 when the initial bet is included in the count.
     */
    private static final double BLACKJACK_PAYOUT = 2.5;
    /**
     * Winning a normal bet pays the amount bet plus the return of the bet, in
     * effect doubling the bet.
     */
    private static final double NORMAL_PAYOUT = 2.0;
    /**
     * The serial number required for serializing the class.
     */
    private static final long serialVersionUID = 510809952979347694L;
    /**
     * The current deck in play.
     */
    private Deck deck;
    /**
     * The dealer's Hand.
     */
    private Hand dealer;
    /**
     * The player's Hand.
     */
    private Hand player;
    /**
     * The cash in a player's bank account.
     */
    private double playerCash;
    /**
     * The players bet for the hand.
     */
    private Bet playerBetHand1;
    /**
     * The player's bet for a split hand.
     */
    @SuppressWarnings("unused")
    private Bet playerBetHand2;
    /**
     * A hand to hold the player's split hand cards.
     */
    private Hand playerSplitHand;
    /**
     * If the hand is still in play.
     */
    private boolean handOneInPlay;
    /**
     * If the hand was split.
     */
    private boolean wasSplit;
    /**
     * If the player's first hand is complete.
     */
    private boolean doneHand1;
    /**
     * If the second hand is still in play.
     */
    private boolean handTwoInPlay;
    /**
     * If true, always deals a split-able hand for testing purposes.
     */
    private boolean splitTest = true;
    /**
     * The GUI element that displays the number of cards remaining in the deck.
     */
    private JLabel remaining;
    /**
     * The split button.
     */
    private JButton jbSplit;
    /**
     * The panel that holds all the action buttons.
     */
    private JPanel pnlButton;
    /**
     * A panel to hold an arrow that points to the current hand in play.
     */
    private JPanel arrow;
    /**
     * The value of the player's hand.
     */
    private JLabel lblValue1;
    /**
     * The value of the player's split hand.
     */
    private JLabel lblValue2;
    /**
     * The frame holding this Blackjack.
     */
    private JFrame frame;
    /**
     * Holds state of a reloaded game, used for testing.
     */
    private boolean reload;
    /**
     * The Blackjack highscores.
     */
    private HighScores scores;
    /**
     * The panel that holds the betting buttons.
     */
    private JPanel bettingPanel;
    /**
     * The initial gameplay instructions.
     */
    private JLabel lblInstructions;
    /**
     * The deal button.
     */
    private JButton deal;
    /**
     * The player's current bet.
     */
    private JLabel lblBet;
    /**
     * The hit button.
     */
    private JButton hit;
    /**
     * The stand button.
     */
    private JButton stand;
    /**
     * The player's cash in the bank.
     */
    private JLabel lblCash;

    /**
     * Constructs a new Blackjack game.
     */
    public Blackjack() {
        deck = new Deck();
        remaining = new JLabel(REMAIN + deck.deckSize());
        dealer = new Hand();
        playerCash = INITIAL_CASH;
        playerBetHand1 = new Bet();
        arrow = new JPanel() {
            /**
             * Serial number for the anonymous class arrow.
             */
            private static final long serialVersionUID = -619516541179237701L;

            public void paintComponent(final Graphics g) {
                int x1 = 0; // x1 x-position of first point
                int y1 = ARROW_Y_OFFSET; // y1 y-position of first point
                int x2 = ARROW_WIDTH; // x2 x-position of second point
                int y2 = ARROW_Y_OFFSET; // y2 y-position of second point
                int d = ARROW_TIP_WIDTH; // d the width of the arrow
                int h = ARROW_TIP_HEIGHT; // h the height of the arrow
                int dx = x2 - x1; // the change in x of the arrow
                int dy = y2 - y1; // the change in y of the arrow
                double stem = Math.sqrt(dx * dx + dy * dy); // the length of the
                                                            // arrow stem
                double xm = stem - d, xn = xm, ym = h, yn = -h, x;
                double sin = dy / stem, cos = dx / stem;

                x = xm * cos - ym * sin + x1;
                ym = xm * sin + ym * cos + y1;
                xm = x;

                x = xn * cos - yn * sin + x1;
                yn = xn * sin + yn * cos + y1;
                xn = x;

                int[] xpoints = { x2, (int) xm, (int) xn };
                int[] ypoints = { y2, (int) ym, (int) yn };

                g.drawLine(x1, y1, x2, y2);
                g.fillPolygon(xpoints, ypoints, xpoints.length);
            }
        };
        pnlButton = new JPanel();
        player = new Hand();
        playerSplitHand = new Hand();
        lblValue1 = new JLabel("Value:");
        lblValue2 = new JLabel("Value: ");
        scores = loadOrCreateScores("Blackjack");

        createGUI();
    } // end constructor

    /**
     * Starts the gameplay.
     */
    public final void begin() {
        System.out.println("beginning");
        newGame();
        // deal();
    }

    /**
     * Creates a new game session.
     */
    final void newGame() {
        deck.shuffle();
        handOneInPlay = false;
        remove(player);
        remove(dealer);
        // repaint();
    }

    /**
     * Displays a string version of the current hands.
     *
     * @return A String representation of the current hands
     */
    public final String display() {
        String output = "";
        if (handOneInPlay) {
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
    public final void deal() {
        bettingPanel.setVisible(false);
        // for (Component button: bettingPanel.getComponents()) {
        // button.setVisible(false);
        // }
        lblInstructions.setVisible(false);
        if (deck.deckSize() < RESHUFFLE_THRESHOLD) {
            System.out.println("reshuffling");
            deck = new Deck();
            deck.shuffle();
            JOptionPane.showMessageDialog(this, "Reshuffling...");
        }
        if (!handOneInPlay) {
            System.out.println("Dealing new hand");
            remove(dealer);
            // adds a new dealer hand to the GUI
            dealer = new Hand();
            dealer.setBounds(HAND_X, HAND_Y, (2 * CARD_WIDTH), CARD_HEIGHT);
            add(dealer);
            // adds a new player hand to the GUI
            player = new Hand();
            player.setBounds(HAND_X, DEALER_Y + HAND_SPACING, (2 * CARD_WIDTH), CARD_HEIGHT);
            add(player);

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

            System.out.println(dealer);
            if (isSplittable()) {
                pnlButton.add(jbSplit);
                jbSplit.setVisible(true);
            } else {
                jbSplit.setVisible(false);
            }
            handOneInPlay = true;
            hit.setVisible(true);
            stand.setVisible(true);
            updateRemain();
            repaint();
        }
    }

    /**
     * Adds a card to the given player and flips it face up.
     *
     * @param playerHand
     *            the player to give the card to
     */
    private void addCardFaceUp(final Hand playerHand) {
        Card temp;

        if (splitTest) {
            temp = new Card(Rank.KING, Suit.CLUB);
        } else {
            temp = deck.deal();
        }
        updateRemain();
        playerHand.addCard(temp);
        temp.flip();
    } // end method

    /**
     * Updates the card remaining in the deck GUI element.
     */
    private void updateRemain() {
        remaining.setText(REMAIN + deck.deckSize());
        if (!lblValue1.isVisible()) {
            lblValue1.setVisible(true);
        }

        if (handOneInPlay) {
            lblValue1.setText("Value: " + player.scoreHand());
            lblValue1.setSize(lblValue1.getPreferredSize());
        } else if (player.isBusted()) {
            lblValue1.setText("Value: " + player.scoreHand() + " Busted");
            lblValue1.setSize(lblValue1.getPreferredSize());
        }
        if (handTwoInPlay) {
            lblValue2.setText("Value: " + playerSplitHand.scoreHand());
            lblValue2.setSize(lblValue2.getPreferredSize());
            if (!lblValue2.isVisible()) {
                lblValue2.setVisible(true);
            }
        } else {
            // something might go here!
        }
    }

    /**
     * Deals the hand in play a new card, then checks for a bust.
     */
    public final void hit() {
        if (isSplittable()) {
            System.out.println("is splittable, but hit");
            jbSplit.setVisible(false);
        }
        if (deck.deckSize() == 0) {
            throw new Error("Deck Out of Cards!");
        }
        System.out.println("hitting");
        if (handOneInPlay) {
            Card card = deck.deal();
            updateRemain();
            card.flip();
            System.out.println(card);
            player.addCard(card);
            updateRemain();
            // try {
            // Thread.sleep(200);
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }

            System.out.println("Bust checking");
            if (player.isBusted() && !handTwoInPlay) {
                handOneInPlay = false;
                checkWinConditions();
            } else if (player.isBusted()) {
                System.out.println("Busted Hand One");
                shiftToHandTwo();
            }
        } else if (handTwoInPlay) {
            // move on to hand two if hand one is done
            Card card = deck.deal();
            updateRemain();
            card.flip();
            // System.out.println(card);
            playerSplitHand.addCard(card);
            updateRemain();
            System.out.println("Bust checking");
            // both hands busted
            if (playerSplitHand.isBusted() && player.isBusted()) {
                handOneInPlay = false;
                checkWinConditions();
            } else if (playerSplitHand.isBusted()) {
                // only hand two busted
                // dealer's turn
                dealerPlays();
            } // end else if
        } // end else if
    } // end method

    /**
     * Changes game state to shift play to hand two, and moves the arrow to
     * point to the second hand. *
     */
    private void shiftToHandTwo() {
        handOneInPlay = false;
        arrow.setLocation(SPLIT_ARROW_LOC);
    }

    /**
     * Finalizes the player's hand, and allows the dealer to play.
     */
    public final void stand() {
        if (isSplittable()) {
            System.out.println("is splittable, but stood");
            jbSplit.setVisible(false);
        }
        // stand only matters if the hand is still in play
        if (handOneInPlay && !handTwoInPlay) {
            handOneInPlay = false;
            dealerPlays();
        } else if (handOneInPlay && handTwoInPlay) {
            shiftToHandTwo();
        } else if (handTwoInPlay) {
            System.out.println("standing on two!");
            dealerPlays();
        } // end else if
    } // end stand

    /**
     * Plays the dealer's hand. Hits until 16, stands at 17.
     */
    private void dealerPlays() {
        dealer.getCard(1).flip();
        while (dealer.scoreHand() < DEALER_THRESHOLD) {
            Card card = deck.deal();
            updateRemain();
            dealer.addCard(card);
            card.flip();
        }
        checkWinConditions();
    } // end method

    /**
     * Checks to see the result of the game. Either the player busted, the
     * dealer busted, or one of the player's hands is higher or lower then the
     * dealer's hand. Reports the state to the player and awards or discards the
     * bet.
     */
    private void checkWinConditions() {
        int answer;

        // bust checking
        if (player.isBusted() && !handTwoInPlay) {
            System.out.println("Getting busted answer (only one hand in play)");
            answer = loseQuestion(player);
        } else if (playerSplitHand.isBusted() && player.isBusted()) {
            System.out.println("Busted Both Hands");
            answer = loseQuestion(player);
        } else {

            boolean loseHandOne = dealer.scoreHand() >= player.scoreHand();
            boolean loseHandTwo = dealer.scoreHand() >= playerSplitHand.scoreHand();
            System.out.println("Hand one: " + loseHandOne + " Hand Two: " + loseHandTwo);
            if (dealer.isBusted()) {

                answer = winQuestion();
            } else if (player.isBusted()) {
                // one hand or both is not busted, otherwise we shouldn't get
                // here
                // if hand 1 is busted, but not hand two
                if (loseHandTwo) {
                    answer = loseQuestion(playerSplitHand);
                } else {
                    answer = winQuestion();
                }
            } else if (playerSplitHand.isBusted()) {
                // if hand 2 is busted but not hand one
                if (loseHandOne) {
                    answer = loseQuestion(player);
                } else {
                    answer = winQuestion();
                }
            } else {
                // if neither hand is busted
                if (loseHandOne && loseHandTwo) {
                    // lost both hands
                    answer = loseQuestion(player);
                } else if (!loseHandOne && loseHandTwo) {
                    // won hand one, lost hand two
                    answer = winQuestion();
                } else if (loseHandOne && !loseHandTwo) {
                    // lost hand one, won hand two
                    answer = winQuestion();
                } else {
                    // won both hands
                    answer = winQuestion();
                }
            }
        }
        if (answer == JOptionPane.YES_OPTION) {
            reset();
        } else {
            // check to see if we qualify for a highscore
            lblBet.setText("$" + playerBetHand1.getMoney());
            lblCash.setText("$" + playerCash);
            System.out.println("Checking for a high score");

            if (scores.isHighScore(playerCash)) {
                String initials = getInitials(this);
                HighScore score = new HighScore(initials, (int) playerCash);
                scores.add(score);
            }
        }
    }

    /**
     * Asks the player if they would like to play again for a winning hand.
     *
     * @return JOptionPane.YES_OPTION or JOptionPane.NO_OPTION
     */
    private int winQuestion() {
        if (player.isBlackjack()) {
            playerCash += playerBetHand1.payout(BLACKJACK_PAYOUT);
        } else {
            playerCash += playerBetHand1.payout(NORMAL_PAYOUT);
        }
        return JOptionPane.showConfirmDialog(this, "You win this hand\nDeal again?", "Deal again?",
                JOptionPane.YES_NO_OPTION);
    }

    /**
     * Asks the player if they would like to play again for a losing hand. Also
     * checks to see if the hand busted, and adjusts the message accordingly.
     *
     * @param hand
     *            the hand to check for busts
     * @return JOptionPane.YES_OPTION or JOptionPane.NO_OPTION
     */
    private int loseQuestion(final Hand hand) {
        playerCash += playerBetHand1.payout(0.0);
        String message = "";
        if (hand.isBusted()) {
            message = "Busted! You lose this hand\nDeal again?";
        } else {
            message = "You lose this hand\nDeal again?";
        }
        return JOptionPane.showConfirmDialog(this, message, "Deal again?", JOptionPane.YES_NO_OPTION);
    }

    /**
     * Resets the game state for a new hand.
     */
    private void reset() {
        System.out.println("Answered yes");
        remove(player);
        remove(dealer);
        if (handTwoInPlay) {
            remove(playerSplitHand);
            lblValue2.setVisible(false);
            handTwoInPlay = false;
            arrow.setLocation(ARROW_X, ARROW_Y);
            arrow.setVisible(false);
        }
        System.out.println("removed player/dealer");

        System.out.println("making deal");
        lblValue1.setVisible(false);
        hit.setVisible(false);
        stand.setVisible(false);
        deal.setVisible(true);
        bettingPanel.setVisible(true);
        lblBet.setText("$" + playerBetHand1.getMoney());
        lblCash.setText("$" + playerCash);
        System.out.println("finished deal");
        revalidate();
        repaint();
    } // end method

    /**
     * Determines if the players hand can be split.
     *
     * @return yes, if the players hand can be split
     */
    final boolean isSplittable() {
        if (player.getCards().size() == 2 && player.getCard(0).getRank().equals(player.getCard(1).getRank())) {
            return true;
        }
        return false;
    } // end method

    /**
     * Splits the player's hand into two separate hands.
     */
    final void split() {
        wasSplit = true;
        playerSplitHand = new Hand();
        handTwoInPlay = true;
        playerSplitHand.addCard(player.removeCard());
        // now add the new hand to the GUI
        playerSplitHand.setBounds(HAND_X, HAND_Y + HAND_SPACING + SPLIT_HAND_OFFSET, CARD_WIDTH, CARD_HEIGHT);
        add(playerSplitHand);
        setComponentZOrder(playerSplitHand, 0);
        jbSplit.setVisible(false);
        arrow.setVisible(true);
        updateRemain();
        revalidate();
        repaint();
    } // end method

    /**
     * Checks to see if the player's hand is in play.
     *
     * @return true, if the player's hand is in play
     */
    public final boolean isHandInPlay() {
        return handOneInPlay;
    }

    /**
     * Creates a new initial BlackJack GUI.
     */
    public final void createGUI() {
        setLayout(null);
        setBackground(CARD_TABLE_GREEN);
        deck.setBounds(DECK_X, DECK_Y, CARD_WIDTH, CARD_HEIGHT); // set the deck
        // size
        // System.out.println("Adding Deck");
        add(deck);
        remaining.setLocation(DECK_X + CARD_WIDTH, DECK_Y);
        remaining.setSize(remaining.getPreferredSize());
        add(remaining);
        dealer.setBounds(HAND_X, DEALER_Y, (2 * CARD_WIDTH), CARD_HEIGHT);
        // System.out.println("Adding Dealer");
        add(dealer);
        pnlButton.setBorder(null);
        pnlButton.setFocusable(false);
        pnlButton.setBounds(DECK_X, PANEL_Y, BUTTON_SIZE, BET_BUTTONS * BUTTON_SIZE);
        add(pnlButton);
        arrow.setLocation(ARROW_X, ARROW_Y);
        arrow.setSize(ARROW_WIDTH, 2 * ARROW_TIP_WIDTH);
        add(arrow);

        arrow.setVisible(false);
        player.setBounds(HAND_X, DEALER_Y + HAND_SPACING, (2 * CARD_WIDTH), CARD_HEIGHT);
        add(player);

        lblCash = new JLabel("$" + playerCash);
        lblCash.setHorizontalAlignment(SwingConstants.CENTER);
        lblCash.setBackground(Color.WHITE);
        lblCash.setForeground(Color.BLACK);
        lblCash.setFont(new Font("Tahoma", Font.PLAIN, CASH_FONT_SIZE));
        lblCash.setLocation(CASH_LOC);
        lblCash.setSize(lblCash.getPreferredSize());
        add(lblCash);

        JLabel lblBalance = new JLabel("CURRENT BALANCE");
        lblBalance.setFont(new Font("Tahoma", Font.PLAIN, BANK_FONT_SIZE));
        lblBalance.setHorizontalAlignment(SwingConstants.CENTER);
        lblBalance.setForeground(Color.WHITE);
        lblBalance.setLocation(BALANCE_TEXT_LOC);
        lblBalance.setSize(lblBalance.getPreferredSize());
        add(lblBalance);

        JLabel lblBetBalance = new JLabel("BET");
        lblBetBalance.setHorizontalAlignment(SwingConstants.CENTER);
        lblBetBalance.setForeground(Color.WHITE);
        lblBetBalance.setFont(new Font("Tahoma", Font.PLAIN, BANK_FONT_SIZE));
        lblBetBalance.setLocation(BET_TEXT_LOC);
        lblBetBalance.setSize(lblBetBalance.getPreferredSize());
        add(lblBetBalance);

        lblBet = new JLabel("$0");
        lblBet.setHorizontalAlignment(SwingConstants.CENTER);
        lblBet.setForeground(Color.BLACK);
        lblBet.setFont(new Font("Tahoma", Font.PLAIN, CASH_FONT_SIZE));
        lblBet.setBackground(Color.WHITE);
        lblBet.setLocation(BET_LOC);
        lblBet.setSize(lblBet.getPreferredSize());
        add(lblBet);

        lblInstructions = new JLabel("<html>Place Your Bet!<br>Table minimum: $5</html>");
        lblInstructions.setFont(new Font("Serif", Font.PLAIN, INSTRUCTIONS_FONT_SIZE));
        lblInstructions.setLocation(INSTRUCTIONS_LOC);
        lblInstructions.setSize(lblInstructions.getPreferredSize());
        add(lblInstructions);

        playerSplitHand.setBounds(HAND_X, DEALER_Y + HAND_SPACING + SPLIT_HAND_OFFSET, CARD_WIDTH, CARD_HEIGHT);
        add(playerSplitHand);
        lblValue1.setVisible(false);
        lblValue1.setFont(new Font("Courier Bold", Font.BOLD, FONT_SIZE));
        lblValue1.setLocation(VALUE_LOC);
        lblValue1.setSize(lblValue1.getPreferredSize());

        add(lblValue1);

        JLabel lblPayout = new JLabel("BLACKJACK PAYS 3 TO 2");
        lblPayout.setForeground(Color.LIGHT_GRAY);
        lblPayout.setFont(new Font("Serif", Font.PLAIN, STENCIL_SIZE));
        lblPayout.setLocation(STENCIL_LOC);
        lblPayout.setSize(lblPayout.getPreferredSize());
        add(lblPayout);

        createBettingPanel();

        lblValue2.setVisible(false);
        lblValue2.setFont(new Font("Courier Bold", Font.BOLD, FONT_SIZE));
        lblValue2.setLocation(SPLIT_VALUE_LOC);
        lblValue2.setSize(lblValue2.getPreferredSize());
        add(lblValue2);

        frame = new JFrame("Black Jack");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setTitle("CGS BlackJack");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(SCREEN_X, SCREEN_Y));
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(final WindowEvent e) {
                // write out high scores here
                String path = System.getProperty("user.home");
                path += File.separator + "CGS";
                File customDir = new File(path);
                File scoreFile = new File(customDir, "BlackJack.score");
                saveHighScores(scoreFile, scores);
                System.out.println("Frame is closing");
            }
        });
        createMenu(frame);
        frame.setContentPane(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        pnlButton.setLayout(null);
        pnlButton.setBackground(CARD_TABLE_GREEN);
        createButtons(BUTTON_SIZE);
    }

    /**
     * Creates the betting panel to hold the bet denomination buttons.
     */
    private void createBettingPanel() {
        bettingPanel = new JPanel();
        bettingPanel.setOpaque(false);
        bettingPanel.setLocation(BET_PANEL_LOC);
        bettingPanel.setSize(BET_PANEL_SIZE);
        add(bettingPanel);
        bettingPanel.setLayout(new GridLayout(0, BET_BUTTONS, 0, 0));

        JButton btnOne = new JButton("1");
        btnOne.setForeground(Color.WHITE);
        btnOne.setBackground(Color.BLUE);
        bettingPanel.add(btnOne);
        btnOne.addActionListener(al -> {
            if (playerCash >= 1) {
                playerBetHand1.add(1);
                playerCash -= 1;
                lblCash.setText("$" + playerCash);
                lblBet.setText("$" + playerBetHand1.getMoney());
            }
            System.out.println("finished one button processing");
        });
        JButton btnFive = new JButton("5");
        btnFive.setForeground(Color.WHITE);
        btnFive.setBackground(Color.RED);
        bettingPanel.add(btnFive);
        btnFive.addActionListener(al -> {
            int betFive = 5;
            if (playerCash >= betFive) {
                playerBetHand1.add(betFive);
                playerCash -= betFive;
                lblCash.setText("$" + playerCash);
                lblBet.setText("$" + playerBetHand1.getMoney());
            }
            System.out.println("finished five button processing");
        });
        JButton btnTwentyFive = new JButton("25");
        btnTwentyFive.setForeground(Color.WHITE);
        btnTwentyFive.setBackground(Color.ORANGE);
        bettingPanel.add(btnTwentyFive);
        btnTwentyFive.addActionListener(al -> {
            int betTwentyFive = 25;
            if (playerCash >= betTwentyFive) {
                playerBetHand1.add(betTwentyFive);
                playerCash -= betTwentyFive;
                lblCash.setText("$" + playerCash);
                lblBet.setText("$" + playerBetHand1.getMoney());
            }
            System.out.println("finished twenty five button processing");
        });
        JButton btnHundred = new JButton("100");
        btnHundred.setForeground(Color.WHITE);
        btnHundred.setBackground(Color.BLACK);
        bettingPanel.add(btnHundred);
        btnHundred.addActionListener(al -> {
            int betHundred = 100;
            if (playerCash >= betHundred) {
                playerBetHand1.add(betHundred);
                playerCash -= betHundred;
                lblCash.setText("$" + playerCash);
                lblBet.setText("$" + playerBetHand1.getMoney());
            }
            System.out.println("finished one hundred button processing");
        });
    }

    /**
     * Creates the action buttons.
     *
     * @param size
     *            The size of the square button.
     */
    private void createButtons(final int size) {
        hit = new JButton("Hit");
        hit.setPreferredSize(new Dimension(size, size));
        hit.setFont(new Font("Courier Bold", Font.BOLD, FONT_SIZE));
        // hit.setBackground(new Color(192, 108, 108));
        hit.setBounds(0, 0, size, size);
        pnlButton.add(hit);
        hit.addActionListener(al -> {
            hit();
            System.out.println("finished hit button processing");
        });
        hit.setVisible(false);

        if (reload) {
            stand = new JButton("Stand 2");
        } else {
            stand = new JButton("Stand");
        }
        stand.setFont(new Font("Courier Bold", Font.BOLD, FONT_SIZE));
        // stand.setBackground(new Color(192, 108, 108));
        stand.setBounds(0, size, size, size);
        pnlButton.add(stand);
        stand.addActionListener(al -> {
            stand();
            System.out.println("finished stand button processing");
        });
        stand.setVisible(false);

        deal = new JButton("Deal");
        // deal.setLocation(0, 240);
        deal.setSize(size, size);
        pnlButton.add(deal);
        deal.setFont(new Font("Dialog", Font.BOLD, FONT_SIZE));
        deal.addActionListener(al -> {
            if (playerBetHand1.getMoney() > 0) {
                deal();
                deal.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "You must bet at lease the minimum bet!", "Must Bet",
                        JOptionPane.ERROR_MESSAGE);
            }
            System.out.println("finished deal button processing");
        });

        if (reload) {
            jbSplit = new JButton("Split 2");
        } else {
            jbSplit = new JButton("Split");
        }
        jbSplit.setFont(new Font("Courier Bold", Font.BOLD, FONT_SIZE));
        // split.setBackground(new Color(192, 108, 108));
        jbSplit.setBounds(0, 2 * size, size, size);
        jbSplit.addActionListener(al -> {
            if (isSplittable()) {
                split();
            }
            System.out.println("finished split button processing");
        });
    } // end method

    /**
     * Creates the menu and adds it to a menu.
     *
     * @param theFrame
     *            the frame the menu is added to
     */
    private void createMenu(final JFrame theFrame) {
        JMenuBar menuBar = new JMenuBar();
        theFrame.setJMenuBar(menuBar);

        JMenu mnMenu = new JMenu("Menu");
        menuBar.add(mnMenu);

        JMenuItem mntmNewGame = new JMenuItem("New Game");
        mnMenu.add(mntmNewGame);
        mntmNewGame.addActionListener(ae -> {
            newGame();
            reset();
        });

        JMenuItem mntmSaveGame = new JMenuItem("Save Game");
        mnMenu.add(mntmSaveGame);
        mntmSaveGame.addActionListener(ae -> {
            saveGame();
        });

        JMenuItem mntmLoadGame = new JMenuItem("Load Game");
        mnMenu.add(mntmLoadGame);
        mntmLoadGame.addActionListener(ae -> {
            loadGame();
        });

        JMenuItem mntmHowToPlay = new JMenuItem("How to Play");
        mnMenu.add(mntmHowToPlay);
        mntmHowToPlay.addActionListener(ae -> {
            directions();
        });

        JMenuItem mntmHighScores = new JMenuItem("High Scores");
        mnMenu.add(mntmHighScores);
        mntmHighScores.addActionListener(ae -> {
            highScores();
        });

        JMenuItem mntmReturnToMain = new JMenuItem("Return to Main Menu");
        mntmReturnToMain.addActionListener(ae -> {
            theFrame.dispose();
        });
        mnMenu.add(mntmReturnToMain);
    }

    /**
     * Shows the high scores in a dialog box.
     */
    private void highScores() {
        JOptionPane.showMessageDialog(frame, scores);
    }

    /**
     * Shows the game play directions to the user.
     */
    private void directions() {
        JOptionPane.showMessageDialog(null, "The objective of Blackjack is to beat the dealer."
                + "  You can beat the dealer in one of the following ways:"
                + "\n  Get 21 points on the first two cards (called a \"blackjack\" or \"natural\"),"
                + " without a dealer blackjack" + "\n  Reach a final score higher than the dealer without exceeding 21"
                + "\n  Let the dealer draw additional cards until his or her hand exceeds 21" + "\nThe dealer wins ties"
                + "\nYou have the option to take an additional card \"hit\" or to not \"stand\""
                + "\nIf your first two cards are the same rank, you can choose to split the hand"
                + "\nSplit hands are played separately" + "\nFace cards count as ten, and Aces count as 11 or 1.");
    }

    /**
     * Saves the state of the game to a file.
     */
    public void saveGame() {

        try (FileOutputStream filestream = new FileOutputStream("BlackJack.ser");
                ObjectOutputStream os = new ObjectOutputStream(filestream);) {
            os.writeObject(deck);
            os.writeObject(dealer);
            os.writeObject(player);
            os.writeObject(playerSplitHand);
            os.writeBoolean(handOneInPlay);
            os.writeBoolean(wasSplit);
            os.writeBoolean(doneHand1);
            os.writeBoolean(handTwoInPlay);
            os.writeObject(remaining);
            // os.writeObject(jbSplit);
            // os.writeObject(panel);
            // os.writeObject(arrow);
            os.writeObject(lblValue1);
            os.writeObject(lblValue2);
        } catch (IOException e) {
            e.printStackTrace();
        }

    } // end method

    /**
     * Loads the game state from a file.
     */
    public void loadGame() {
        remove(player);
        remove(dealer);
        remove(lblValue1);
        remove(lblValue2);
        remove(remaining);
        if (handTwoInPlay) {
            remove(playerSplitHand);
            lblValue2.setVisible(false);
            handTwoInPlay = false;
            arrow.setLocation(ARROW_X, ARROW_Y);
            arrow.setVisible(false);
        }
        System.out.println("removed player/dealer");
        revalidate();
        repaint();
        try (FileInputStream filestream = new FileInputStream("BlackJack.ser");
                ObjectInputStream os = new ObjectInputStream(filestream);) {
            deck = (Deck) os.readObject();
            dealer = (Hand) os.readObject();
            player = (Hand) os.readObject();
            playerSplitHand = (Hand) os.readObject();
            boolean b1 = os.readBoolean();
            boolean b2 = os.readBoolean();
            boolean b3 = os.readBoolean();
            boolean b4 = os.readBoolean();
            remaining = (JLabel) os.readObject();
            // jbSplit =
            // JButton test = (JButton) os.readObject();
            // // panel =
            // JPanel tests1 = (JPanel) os.readObject();
            // // arrow =
            // JPanel tests2 = (JPanel) os.readObject();
            lblValue1 = (JLabel) os.readObject();
            lblValue2 = (JLabel) os.readObject();

            System.out.println("loaded: " + "\n handOneInPlay " + b1 + "\n wasSplit      " + b2 + "\n doneHand1     "
                    + b3 + "\n handTwoInPlay " + b4 + "\n" + lblValue1.getText() + lblValue2.getText()
                    + isSplittable());

            handOneInPlay = b1;
            wasSplit = b2;
            doneHand1 = b3;
            handTwoInPlay = b4;
            add(dealer);
            add(player);
            add(remaining);
            add(lblValue1);
            add(lblValue2);
            if (handOneInPlay || handTwoInPlay) {
                deal.setVisible(false);
                bettingPanel.setVisible(false);
                hit.setVisible(true);
                stand.setVisible(true);
            }
            if (isSplittable()) {
                pnlButton.add(jbSplit);
                System.out.println("adding split button");
                jbSplit.setVisible(true);
            } else {
                jbSplit.setVisible(false);
            }
            if (wasSplit) {
                playerSplitHand.setBounds(HAND_X, HAND_Y + HAND_SPACING + SPLIT_HAND_OFFSET, CARD_WIDTH, CARD_HEIGHT);
                add(playerSplitHand);
                jbSplit.setVisible(false);
                arrow.setVisible(true);
            }
            updateRemain();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    } // end method

    /**
     * Runs a test of BlackJack by itself.
     *
     * @param args
     *            input parameters, not used
     */
    public static void main(final String[] args) {
        Blackjack blackjack = new Blackjack();
        blackjack.begin();
    } // end method
} // end class
