// File: ThirtyOne.java
// Author: Patrick Smith
// Date: Oct 10, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: implements a graphical game of Thirty-One.

package group3;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * ThirtyOne implements a graphical game of ThirtyOne. The game includes the
 * ability to draw a card from the middle, discard a card from your hand, and
 * compare your hand against the computer's to see which is closer to
 * Thirty-One.
 *
 * @author Alex Burch
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 1, 2016
 */
public class ThirtyOne extends JPanel implements Game {
    /**
     * The maximum score a hand can have.
     */
    private static final int MAX_SCORE = 31;
    /**
     * The score threshold for the dealer to knock.
     */
    private static final int KNOCK_THRESHOLD = 25;
    /**
     * The number of Cards in a Hand, and in the Window.
     */
    private static final int CARDS_IN_HAND = 3;
    /**
     * A green color like a card game table.
     **/
    private static final Color CARD_TABLE_GREEN = new Color(7, 99, 36);
    /**
     * A bluish-grey color used for text.
     */
    private static final Color LABEL_BLUE_GREY = new Color(40, 55, 67);
    /**
     * The minimum screen width.
     */
    private static final int SCREEN_X = 600;
    /**
     * The minimum screen height.
     */
    private static final int SCREEN_Y = 600;
    /**
     * The height of a Card.
     */
    private static final int CARD_HEIGHT = 96;
    /**
     * The width of a Card.
     */
    private static final int CARD_WIDTH = 72;
    /**
     * The X location of the center label.
     */
    private static final int CENTER_LABEL_X = 25;
    /**
     * The Y location of the center label.
     */
    private static final int CENTER_LABEL_Y = 25;
    /**
     * The size of the font.
     */
    private static final int FONT_SIZE = 16;
    /**
     * The X location of the player's Hand label.
     */
    private static final int PLAYER_HAND_LABEL_X = 25;
    /**
     * The Y location of the player's Hand label.
     */
    private static final int PLAYER_VALUE_LABEL_Y = 450;
    /**
     * The Y location of the center Cards.
     */
    private static final int CENTER_CARD_Y = 100;
    /**
     * The Y location of the player's Cards.
     */
    private static final int PLAYER_CARD_Y = 300;
    /**
     * The X location of the first Card.
     */
    private static final int CARD_X_1 = 150;
    /**
     * The X location of the player's Hand value.
     */
    private static final int PLAYER_HAND_X = 25;
    /**
     * The Y location of the player's Hand value.
     */
    private static final int PLAYER_HAND_Y = 250;
    /**
     * The X location of the player's current score.
     */
    private static final int PLAYER_SCORE_X = 450;
    /**
     * The Y location of the player's current score.
     */
    private static final int PLAYER_SCORE_Y = 450;
    /**
     * The value a player is aiming to reach.
     */
    private static final int WIN_VALUE = 31;
    /**
     * The value awarded to a player if they win.
     */
    private static final int WIN_SCORE = 10;
    /**
     * The value awarded to a player if they tie.
     */
    private static final int TIE_SCORE = 50;
    /**
     * The serial number required for serializing the class.
     */
    private static final long serialVersionUID = 510809952979347694L;
    /**
     * The current deck in play.
     */
    private Deck deck;
    /**
     * The AI's hand.
     */
    private Hand AI;
    /**
     * The player's hand.
     */
    private Hand player;
    /**
     * Integer to see how close the player is to 31.
     */
    private int player31;
    /**
     * Integer to see how close the AI is to 31.
     */
    private int ai31;
    /**
     * The ThirtyOne high scores.
     */
    private HighScores scores;
    /**
     * JLabel to signify the player's hand.
     */
    private JLabel jlPlayerHand;
    /**
     * JLabel to signify the center cards.
     */
    private JLabel jlCenterCards;
    /**
     * JLabel to show the player's current score.
     */
    private JLabel jlPlayerScore;
    /**
     * Integer to show the player's current score.
     */
    private int playerScore;
    /**
     * JLabel to show the total value of the player's hand.
     */
    private JLabel jlPlayerValue;
    /**
     * Integer to represent the total value of the player's hand.
     */
    private int playerHand;
    /**
     * The frame holding this ThirtyOne.
     */
    private JFrame frame;
    /**
     * The center cards in the "Window".
     */
    private Hand center;
    /**
     * True if the someone knocked.
     */
    private boolean knockActive;
    /**
     * Who knocked.
     */
    private String knocker;
    /**
     * Shows who knocked on the GUI.
     */
    private JLabel knockLabel;
    private boolean gameActive;
    /**
     * Map that links Card Rank to values for this Hand. Probably should be
     * overridden in a sub-class for each game that has different value
     * assignments.
     */
    private static Map<Rank, Integer> values = new HashMap<>();
    // Statically assigns the default Rank values.
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

    /**
     * Constructs a new ThirtyOne game.
     */
    public ThirtyOne() {
        deck = new Deck();
        AI = new Hand();
        player = new Hand();
        center = new Hand();
        jlPlayerHand = new JLabel("Player's Hand");
        jlCenterCards = new JLabel("Center Cards");
        jlPlayerScore = new JLabel("Player's Score: " + playerScore);
        playerScore = 0;
        scores = loadOrCreateScores("ThirtyOne");
        createGUI();
    } // end constructor

    /**
     * Starts the game play.
     */
    public final void begin() {
        System.out.println("beginning");
        newGame();
    }

    /**
     * Creates a new game and shuffles the deck.
     */
    final void newGame() {
        remove(player);
        remove(center);
        remove(deck);
        Mouse mh = new Mouse();
        deck = new Deck();
        AI = new Hand();
        center = new Hand();
        player = new Hand();
        knockActive = false;
        deck.setLocation(0, CENTER_CARD_Y);
        deck.setSize(CARD_WIDTH, CARD_HEIGHT);
        deck.shuffle();
        deck.addMouseListener(mh);
        add(deck);
        player.addMouseListener(mh);
        player.addMouseMotionListener(mh);
        add(player);
        player.setLocation(CARD_X_1, PLAYER_CARD_Y);
        center.addMouseListener(mh);
        center.addMouseMotionListener(mh);
        add(center);
        center.setLocation(CARD_X_1, CENTER_CARD_Y);
        knockLabel.setVisible(false);
        dealCards();
        playerHand = player.thirtyOneTotal();
        jlPlayerValue.setText("Value: " + String.valueOf(playerHand));
        System.out
            .println("Old preferred size " + jlPlayerScore.getPreferredSize());
        jlPlayerScore.setText("Player's Score: " + playerScore);
        System.out
            .println("New preferred size " + jlPlayerScore.getPreferredSize());
        jlPlayerScore.setSize(jlPlayerScore.getPreferredSize());
        revalidate();
        repaint();
        gameActive = true;
    }

    /**
     * Resets the game state as if the game just was opened.
     */
    private void reset() {
        remove(player);
        remove(center);
        deck = new Deck();
        AI = new Hand();
        center = new Hand();
        player = new Hand();
        playerScore = 0;
        knockActive = false;
        System.out
            .println("Old preferred size " + jlPlayerScore.getPreferredSize());
        jlPlayerScore.setText("Player's Score: " + playerScore);
        System.out
            .println("New preferred size " + jlPlayerScore.getPreferredSize());
        jlPlayerScore.setSize(jlPlayerScore.getPreferredSize());
    }

    /**
     * Creates a new initial ThirtyOne GUI.
     */
    public final void createGUI() {
        frame = new JFrame("Thirty-One");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setTitle("CGS Thirty-One");
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
                File scoreFile = new File(customDir, "ThirtyOne.score");
                saveHighScores(scoreFile, scores);
                if (MainCGS.DEBUGGING) {
                    System.out.println("Frame is closing");
                }
            }
        });
        createMenu(frame);
        frame.setContentPane(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        JButton knock = new JButton("Knock");
        knock.setPreferredSize(new Dimension(CARD_WIDTH, CARD_WIDTH));
        knock.setFont(new Font("Courier Bold", Font.BOLD, FONT_SIZE));
        // hit.setBackground(new Color(192, 108, 108));
        knock.setBounds(0, PLAYER_CARD_Y, CARD_HEIGHT, CARD_HEIGHT);
        add(knock);
        knock.addActionListener(al -> {
            knocker = "PLAYER";
            knock();
            if (MainCGS.DEBUGGING) {
                System.out.println("finished knock button processing");
            }
        });
        knock.setVisible(true);

        deck.setLocation(0, CENTER_CARD_Y);
        deck.setSize(CARD_WIDTH, CARD_HEIGHT);
        add(deck);

        setLayout(null);
        setBackground(CARD_TABLE_GREEN);
        jlCenterCards.setLocation(CENTER_LABEL_X, CENTER_LABEL_Y);
        jlCenterCards.setForeground(LABEL_BLUE_GREY);
        jlCenterCards.setFont(new Font("Tahoma", Font.PLAIN, FONT_SIZE));
        jlCenterCards.setSize(jlCenterCards.getPreferredSize());
        add(jlCenterCards);

        jlPlayerValue = new JLabel();
        jlPlayerValue.setLocation(PLAYER_HAND_LABEL_X, PLAYER_VALUE_LABEL_Y);
        jlPlayerValue.setForeground(LABEL_BLUE_GREY);
        jlPlayerValue.setFont(new Font("Tahoma", Font.PLAIN, FONT_SIZE));
        jlPlayerValue.setSize(jlCenterCards.getPreferredSize());
        add(jlPlayerValue);

        jlPlayerHand.setLocation(PLAYER_HAND_X, PLAYER_HAND_Y);
        jlPlayerHand.setForeground(LABEL_BLUE_GREY);
        jlPlayerHand.setFont(new Font("Tahoma", Font.PLAIN, FONT_SIZE));
        jlPlayerHand.setSize(jlPlayerHand.getPreferredSize());
        add(jlPlayerHand);

        jlPlayerScore.setLocation(PLAYER_SCORE_X, PLAYER_VALUE_LABEL_Y);
        jlPlayerScore.setForeground(LABEL_BLUE_GREY);
        jlPlayerScore.setFont(new Font("Tahoma", Font.PLAIN, FONT_SIZE));
        jlPlayerScore.setSize(jlPlayerScore.getPreferredSize());
        add(jlPlayerScore);

        knockLabel = new JLabel("Someone " + " Knocked");
        knockLabel.setLocation(CARD_X_1, PLAYER_HAND_Y);
        knockLabel.setForeground(LABEL_BLUE_GREY);
        knockLabel.setFont(new Font("Tahoma", Font.PLAIN, FONT_SIZE));
        knockLabel.setSize(knockLabel.getPreferredSize());
        add(knockLabel);
        knockLabel.setVisible(false);
    }

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
            reset();
            newGame();
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
        mnMenu.add(mntmReturnToMain);
        mntmReturnToMain.addActionListener(ae -> {
            theFrame.dispose();
        });
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
        JOptionPane.showMessageDialog(null,
            "The objective of Thirty-One The goal is to obtain a hand that"
                    + " totals 31 in cards of one suit; or to have a hand at the "
                    + "showdown whose count in one suit is higher than that of any "
                    + "other player. An Ace counts 11 points, face cards count 10 "
                    + "points, and all other cards count their face value. On each "
                    + "turn, a player may take one card from the widow and replace it "
                    + "with one card from his hand (face up). Players take turns, "
                    + "clockwise around the table, until one player is satisfied that "
                    + "the card values he holds will likely beat the other players. He"
                    + " indicates this by \"knocking\" on the table. All other players"
                    + " then get one more turn to exchange cards. Then there is a "
                    + "showdown in which the players reveal their hands and compare "
                    + "values. The player with the highest total value of cards of "
                    + "the same suit wins. Any time a player holds exactly 31, he may "
                    + "\"knock\" immediately, and he wins the pot. If a player knocks "
                    + "before the first round of exchanges have begun, the showdown "
                    + "occurs immediately with no exchange of cards.");
    }

    /**
     * Deals a new hand of cards.
     */
    private void dealCards() {
        deck.shuffle();
        for (int i = 0; i < CARDS_IN_HAND; i++) {
            Card temp = deck.deal();
            temp.flip();
            player.addCard(temp);

            temp = deck.deal();
            temp.flip();
            AI.addCard(temp);
        }
        for (int i = 0; i < CARDS_IN_HAND; i++) {
            Card temp = deck.deal();
            temp.flip();
            center.addCard(temp);
        }
        revalidate();
        repaint();
    }

    /**
     * Saves the state of the game to a file.
     */
    public final void saveGame() {
        System.out.println("Saving game");
        try (FileOutputStream fileStream =
                new FileOutputStream("ThirtyOne.ser");
                ObjectOutputStream os = new ObjectOutputStream(fileStream);) {
            os.writeObject(deck);
            os.writeObject(AI);
            os.writeObject(player);
            os.writeObject(center);
            os.writeInt(playerScore);
            os.writeBoolean(knockActive);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the game state from a file.
     */
    public final void loadGame() {
        try (FileInputStream fileStream = new FileInputStream("ThirtyOne.ser");
                ObjectInputStream os = new ObjectInputStream(fileStream);) {
            System.out.println("Loading game");
            remove(deck);
            remove(AI);
            remove(player);
            remove(center);
            Mouse mh = new Mouse();
            deck = (Deck) os.readObject();
            deck.setLocation(0, CENTER_CARD_Y);
            deck.setSize(CARD_WIDTH, CARD_HEIGHT);
            deck.addMouseListener(mh);
            add(deck);
            AI = (Hand) os.readObject();

            player = (Hand) os.readObject();
            player.setLocation(CARD_X_1, PLAYER_CARD_Y);
            player.addMouseListener(mh);
            player.addMouseMotionListener(mh);
            add(player);

            center = (Hand) os.readObject();
            center.setLocation(CARD_X_1, CENTER_CARD_Y);
            center.addMouseListener(mh);
            center.addMouseMotionListener(mh);
            add(center);

            playerScore = os.readInt();
            knockActive = os.readBoolean();
            knockLabel.setVisible(knockActive);
            jlPlayerValue
                .setText("Value: " + String.valueOf(player.thirtyOneTotal()));
            jlPlayerScore.setText("Player's Score: " + playerScore);
            jlPlayerScore.setSize(jlPlayerScore.getPreferredSize());
            revalidate();
            repaint();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                "No Save file found!\nHave you saved the game yet?",
                "No Save File", JOptionPane.WARNING_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Decides which player wins, sees if the player wants to play again, and
     * starts a new game or checks to see if they made the high score.
     */
    @SuppressWarnings("unused")
    private void results() {
        remove(jlPlayerValue);
        playerHand = player.thirtyOneTotal();
        jlPlayerValue.setText("Value: " + String.valueOf(playerHand));
        add(jlPlayerValue);
        System.out.println(player.thirtyOneTotal());
        player31 = WIN_VALUE - player.thirtyOneTotal();
        player31 = Math.abs(player31);
        ai31 = WIN_VALUE - AI.thirtyOneTotal();
        ai31 = Math.abs(ai31);
        if (player31 < ai31) {
            playerScore += WIN_SCORE;
            Object[] options = {"Yes", "No" };
            int n = JOptionPane.showOptionDialog(frame,
                ("You win " + player.thirtyOneTotal() + " to "
                        + AI.thirtyOneTotal()
                        + "!\nWould you like to play again?"),
                "You Win!", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (n == JOptionPane.YES_OPTION) {
                reset();
            } else {
                if (scores.isHighScore(playerScore)) {
                    String initials = getInitials(this);
                    HighScore score =
                            new HighScore(initials, (int) playerScore);
                    scores.add(score);
                }
            }
        } else if (player31 == ai31) {
            playerScore += TIE_SCORE;
            Object[] options = {"Yes", "No" };
            int n = JOptionPane.showOptionDialog(frame,
                ("You tie " + player.thirtyOneTotal() + " to "
                        + AI.thirtyOneTotal()
                        + "!\nWould you like to play again?"),
                "You tie", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (n == JOptionPane.YES_OPTION) {
                reset();
            } else {
                if (scores.isHighScore(playerScore)) {
                    String initials = getInitials(this);
                    HighScore score =
                            new HighScore(initials, (int) playerScore);
                    scores.add(score);
                }
            }
        } else {
            Object[] options = {"Yes", "No" };
            int n = JOptionPane.showOptionDialog(frame,
                ("You lose " + player.thirtyOneTotal() + " to "
                        + AI.thirtyOneTotal()
                        + "!\nWould you like to play again?"),
                "You lose", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (n == JOptionPane.YES_OPTION) {
                reset();
            } else {
                if (scores.isHighScore(playerScore)) {
                    String initials = getInitials(this);
                    HighScore score =
                            new HighScore(initials, (int) playerScore);
                    scores.add(score);
                }
            }
        }
    }

    /**
     * Makes the dealer's moves.
     */
    private void playDealerTurn() {
        System.out.println("Playing dealer's hand " + AI);
        int score = 0;
        int maxScore = 0;
        Suit maxSuit = null;
        HashMap<Suit, Integer> dict = new HashMap<>();
        for (Suit suit : Suit.values()) {
            score = 0;
            for (Card card : AI.getCards()) {
                if (card.getSuit().equals(suit)) {
                    score += values.get(card.getRank());
                }
            }
            dict.put(suit, score);
            if (score > maxScore) {
                maxScore = score;
                maxSuit = suit;
            }
            System.out.println(suit + " " + score);
        }
        System.out.println("Dealer's best is " + maxSuit + maxScore);

        // select a card for discard
        Card discard = null;
        int discardValue = MAX_SCORE;
        Card sameSuitDiscard = null;
        int sameSuitValue = MAX_SCORE;
        for (Card card : AI.getCards()) {
            int value = values.get(card.getRank());
            // discard the lowest value card that is not the maxSuit
            if (!card.getSuit().equals(maxSuit) && value < discardValue) {
                discard = card;
                discardValue = value;
            } else if (discard == null && value < sameSuitValue) {
                // this selects the lowest of the maxSuit
                sameSuitDiscard = card;
                sameSuitValue = value;
            }
        }

        if (discard == null && sameSuitDiscard != null) {
            // discard the lowest value card of this suit
            discard = sameSuitDiscard;
        }
        System.out.println("selected to discard: " + discard);

        int bestValue = 0;
        Card bestCard = null;
        for (Card card : center.getCards()) {
            if (card.getSuit().equals(maxSuit)) {
                int value = values.get(card.getRank());
                if (value > bestValue && value > sameSuitValue) {
                    // only select a card if it is better than others in the
                    // center and better than what is already in the hand
                    bestCard = card;
                    bestValue = value;
                }
            }
        }

        if (bestCard == null) {
            // draw a card from the deck and shift the window
            System.out.println("drawing a new card");
            Card draw = deck.deal();
            draw.flip();
            bestCard = draw;
            center.removeCard();
            center.add(bestCard);
        }
        System.out.println("selected center card " + bestCard);
        System.out.println("trading " + discard + " for " + bestCard);

        AI.returnCard(discard);
        center.addCard(discard);
        center.returnCard(bestCard);
        AI.addCard(bestCard);
        AI.repaint();
        center.repaint();
        if (AI.thirtyOneTotal() > KNOCK_THRESHOLD) {
            knocker = "AI";
            knock();
        }
        if (knockActive && knocker.equals("PLAYER")) {
            // end game, score
            System.out.println("Game over");
            scoreGame();
        }
    }

    /**
     * Scores the game and finds out if the player wants to play again.
     */
    private void scoreGame() {
        int aiHandScore = AI.thirtyOneTotal();
        int playerHandScore = player.thirtyOneTotal();
        // show dealer cards
        AI.setLocation(CARD_X_1, PLAYER_SCORE_Y);
        add(AI);

        int answer;
        if (playerHandScore > aiHandScore) {
            // win
            playerScore += WIN_SCORE;
            answer = JOptionPane.showConfirmDialog(this,
                ("You win " + playerHandScore + " to " + aiHandScore
                        + "!\nWould you like to play again?"),
                "Play Again?", JOptionPane.YES_NO_OPTION);
        } else if (playerHandScore < aiHandScore) {
            // lose
            answer = JOptionPane.showConfirmDialog(this,
                ("You lose " + playerHandScore + " to " + aiHandScore
                        + "!\nWould you like to play again?"),
                "Play Again?", JOptionPane.YES_NO_OPTION);
        } else {
            // tie
            answer = JOptionPane.showConfirmDialog(this,
                ("You tied " + playerHandScore + " to " + aiHandScore
                        + "!\nWould you like to play again?"),
                "Play Again?", JOptionPane.YES_NO_OPTION);
        }
        if (answer == JOptionPane.YES_OPTION) {
            gameActive = false;
            remove(AI);
            knockActive = false;
            knockLabel.setVisible(false);
            revalidate();
            repaint();
            newGame();
        } else {
            if (scores.isHighScore(playerScore)) {
                String initials = getInitials(this);
                HighScore score = new HighScore(initials, (int) playerScore);
                scores.add(score);
            }
            gameActive = false;
        }
    }

    /**
     * Ends the players turn, calls for scoring.
     */
    private void knock() {
        if (!gameActive) {
            return;
        }
        knockActive = true;
        if (knocker.equals("AI")) {
            // alert player the dealer knocked
            System.out.println("Dealer knocked");
            knockLabel.setText("Dealer" + " Knocked");
            knockLabel.setVisible(true);
            repaint();
        } else {
            // player knocked
            System.out.println("Player knocked");
            knockLabel.setText("Player" + " Knocked");
            knockLabel.setVisible(true);
            revalidate();
            repaint();
            playDealerTurn();
        }
    }

    /**
     * Internal class that implements the MouseHandler for the piles. Used to
     * move cards between the different piles and change the mouse state to show
     * the user whether they are in select or move state.
     *
     * @author Patrick Smith
     * @version 1.0
     * @since Oct 9, 2016
     */
    private class Mouse extends MouseInputAdapter {
        /**
         * The sleep time used to simulate the dealer thinking.
         */
        private static final int AI_THINK_DELAY = 500;
        /**
         * Score earned for moving a card to the Foundation.
         */
        @SuppressWarnings("unused")
        private static final int FOUNDATION_MOVE_SCORE = 15;
        /**
         * The source pile selected by the first click.
         */
        private Hand source = null;
        /**
         * The current user location on the GUI.
         */
        private Point p;
        /**
         * Used to transform the point into a different coordinate system.
         */
        @SuppressWarnings("unused")
        private Point pp;
        /**
         * Tracks the number of clicks the user made.
         */
        private int clicks = 0;
        /**
         * The destination pile selected by a second click.
         */
        private Hand destination;
        /**
         * The card the user last clicked on.
         */
        private Card clickedCard;
        /**
         * A temporary list of cards. Used when moving multiple cards from one
         * Tableau to another.
         */
        private ArrayList<Card> tempList;
        /**
         * The new card that was dealt.
         */
        private Card dealt;

        /*
         * (non-Javadoc) Updated where the cursor is on screen.
         *
         * @see
         * java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseMoved(final MouseEvent e) {
            p = e.getPoint();
        }

        /*
         * (non-Javadoc) One click selects a card. The next click selects where
         * to put it. If it is a valid move, it is moved.
         *
         * @see
         * java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
         */
        @Override
        public void mousePressed(final MouseEvent e) {

            if (e.getButton() != MouseEvent.BUTTON1) {
                return;
            }

            // pp = SwingUtilities.convertPoint(this.sourcePile, this.p,
            // Solitaire.this);

            if (clicks % 2 == 0) {
                if (knockActive && knocker.equals("PLAYER")) {
                    return;
                }
                if (e.getSource() instanceof Hand) {
                    source = ((Hand) e.getSource());
                    if (source.getComponentAt(p) instanceof Card) {
                        clickedCard = (Card) source.getComponentAt(p);
                        if (ThirtyOne.this.center.getCards()
                            .contains(clickedCard)) {
                            System.out.println("Clicked on center");
                        } else {
                            clickedCard = null;
                        }
                    }
                    if (MainCGS.DEBUGGING) {
                        System.out.println("first click");
                        // System.out.println("Clicked on: " + source);
                    }
                    if (clickedCard != null) {
                        if (MainCGS.DEBUGGING) {
                            System.out.println("Clicked on " + clickedCard);
                        }
                        clicks += 1;
                        ThirtyOne.this
                            .setCursor(new Cursor(Cursor.MOVE_CURSOR));
                        if (MainCGS.DEBUGGING) {
                            System.out.println("temp list is " + tempList);
                        }
                    }
                } else if (e.getSource() instanceof Deck) {
                    // clicked on deck, add card to hand
                    source = null;
                    dealt = ThirtyOne.this.deck.deal();
                    if (deck.deckSize() == 0) {
                        deck.removeMouseListener(this);
                        deck.repaint();
                    }
                    dealt.flip();
                    ThirtyOne.this.player.addCard(dealt);
                    ThirtyOne.this.player.repaint();

                    clicks += 1;
                    ThirtyOne.this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                }
            } else {
                // clicks = 1
                if (knockActive && knocker.equals("PLAYER")) {
                    return;
                }
                if (MainCGS.DEBUGGING) {
                    System.out.println("second click");
                }
                if (!(e.getSource() instanceof Hand)) {
                    // ignore multiple clicks on the deck
                    return;
                }
                destination = (Hand) e.getSource();
                if (clickedCard != null
                        && destination.getComponentAt(p) instanceof Card
                        && ThirtyOne.this.player.getCards()
                            .contains(destination.getComponentAt(p))) {
                    Card destCard = (Card) destination.getComponentAt(p);
                    if (MainCGS.DEBUGGING) {
                        System.out.println("removing a card");
                        System.out.println("Removing from: " + source);
                        System.out.println("Swapping with " + destCard);
                    }
                    System.out.println("clicked card " + clickedCard);
                    source.returnCard(clickedCard);
                    destination.addCard(clickedCard);
                    destination.returnCard(destCard);
                    source.addCard(destCard);
                    source.repaint();
                    destination.repaint();
                    playerHand = player.thirtyOneTotal();
                    jlPlayerValue
                        .setText("Value: " + String.valueOf(playerHand));
                    clicks = 0;
                    ThirtyOne.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    clickedCard = null;
                    playDealerTurn();
                } else if (clickedCard == null && dealt != null) {
                    // dealt a card
                    if (destination.getComponentAt(p) instanceof Card
                            && ThirtyOne.this.player.getCards()
                                .contains(destination.getComponentAt(p))) {
                        // select a card from the hand (out of the four now
                        // present)
                        Card discard = (Card) destination.getComponentAt(p);
                        destination.returnCard(discard);
                        center.removeCard();
                        center.addCard(discard);
                        center.repaint();
                        playerHand = player.thirtyOneTotal();
                        jlPlayerValue
                            .setText("Value: " + String.valueOf(playerHand));
                        System.out.println(
                            "Dealt a " + dealt + " discarding " + discard);
                        clicks = 0;
                        ThirtyOne.this
                            .setCursor(new Cursor(Cursor.HAND_CURSOR));
                        dealt = null;
                        if (knockActive && knocker.equals("AI")) {
                            // game over, score game
                            scoreGame();
                        }
                        try {
                            Thread.sleep(AI_THINK_DELAY); // simulate dealer
                                                          // thinking
                            playDealerTurn();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

            }
        }

        /*
         * (non-Javadoc) Checks if the user double clicked. If they did, and the
         * clicked card can go on a foundation, put it there.
         *
         * @see
         * java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseClicked(final MouseEvent e) {
            @SuppressWarnings("unused")
            int clickCount = e.getClickCount();
        } // end method
    }

    /**
     * Runs a test of ThirtyOne by itself.
     *
     * @param args
     *            input parameters, not used
     */
    public static void main(final String[] args) {
        ThirtyOne thirtyone = new ThirtyOne();
        thirtyone.begin();
    }
}
