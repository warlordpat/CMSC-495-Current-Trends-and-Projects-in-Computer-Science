// File: ThirtyOne.java
// Author: Alex Burch
// Date: Sep 23, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: implements a graphical game of Thirty-One.

package group3;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * ThirtyOne implements a graphical game of ThirtyOne. The game includes the
 * ability to draw a card from the middle, discard a card from your hand, and
 * compare your hand against the computer's to see which is closer to
 * Thirty-One.
 *
 * @author Alex Burch
 * @version 1.0
 * @since Sep 1, 2016
 */
public class ThirtyOne extends JPanel implements Game {
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
    private static final int PLAYER_HAND_LABEL_Y = 450;
    /**
     * The number of options for the random number generator.
     */
    private static final int RANDOM_OPTION = 2;
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
     * The X location of the second Card.
     */
    private static final int CARD_X_2 = 250;
    /**
     * The X location of the third Card.
     */
    private static final int CARD_X_3 = 350;
    /**
     * The X location of the fourth Card.
     */
    private static final int CARD_X_4 = 450;
    /**
     * The X location of the player's Hand value.
     */
    private static final int PLAYER_VALUE_X = 25;
    /**
     * The Y location of the player's Hand value.
     */
    private static final int PLAYER_VALUE_Y = 250;
    /**
     * The X location of the player's current score.
     */
    private static final int PLAYER_SCORE_X = 450;
    /**
     * The Y location of the player's current score.
     */
    private static final int PLAYER_SCORE_Y = 450;
    /**
     * The maximum number of Cards in the Hand.
     */
    private static final int MAX_HAND = 4;
    /**
     * The minimum number of Cards in the Hand.
     */
    private static final int MIN_HAND = 3;
    /**
     * The value a player is aiming to reach.
     */
    private static final int WIN_VALUE = 31;
    /**
     * The value awarded to a player if they win.
     */
    private static final int WIN_SCORE = 100;
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
     * The cards in the player's hand.
     */
    private Card playerCard1, playerCard2, playerCard3, playerCard4;
    /**
     * The cards in the AI's hand.
     */
    private Card aiCard1, aiCard2, aiCard3, aiCard4;
    /**
     * The cards in the center.
     */
    private Card centerCard1, centerCard2, centerCard3;
    /**
     * Integer to see how close the player is to 31.
     */
    private int player31;
    /**
     * Integer to see how close the AI is to 31.
     */
    private int ai31;
    /**
     * Integer to see who goes first.
     */
    private int iTurn;
    /**
     * Random number generator to see who goes first.
     */
    private Random rand = new Random();
    /**
     * The ThirtyOne high scores.
     */
    private HighScores scores;
    /**
     * JLabel to signify the player's hand.
     */
    private JLabel jlPlayer;
    /**
     * JLabel to signify the center cards.
     */
    private JLabel jlCenter;
    /**
     * JButtons for the cards in the player's hand.
     */
    private JButton jbPlayerCard1, jbPlayerCard2, jbPlayerCard3, jbPlayerCard4;
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
    private JLabel jlPlayerHand;
    /**
     * Integer to represent the total value of the player's hand.
     */
    private int playerHand;
    /**
     * JButtons for the cards in the center.
     */
    private JButton jbCenterCard1, jbCenterCard2, jbCenterCard3;
    /**
     * The frame holding this ThirtyOne.
     */
    private JFrame frame;
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
        player31 = 0;
        ai31 = 0;
        iTurn = 0;
        jlPlayer = new JLabel("Player's Hand");
        jlCenter = new JLabel("Center Cards");
        jbPlayerCard4 = null;
        jlPlayerScore = new JLabel("Player's Score: " + playerScore);
        jlPlayerHand = new JLabel();
        playerScore = 0;
        playerHand = 0;
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
        deck = new Deck();
        deck.shuffle();
        revalidate();
        repaint();
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
                System.out.println("Frame is closing");
            }
        });
        createMenu(frame);
        frame.setContentPane(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        setCards();
        cardButtons();
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
            "The objective of Thirty-One is to be the closer player to 31."
                    + "\nEach player starts with 3 cards. They take turns adding 1 "
                    + "\ncard to their hands out of 3 from the center. The player "
                    + "\ncloser to 31 wins the game.");
    }

    /**
     * Creates the buttons on the GUI and decides which player goes first.
     */
    private void setCards() {
        player.removeAll();
        AI.removeAll();
        try {
            remove(jbPlayerCard1);
            remove(jbPlayerCard2);
            remove(jbPlayerCard3);
        } catch (Exception e) {
        }
        setLayout(null);
        setBackground(CARD_TABLE_GREEN);
        jlCenter.setLocation(CENTER_LABEL_X, CENTER_LABEL_Y);
        jlCenter.setForeground(LABEL_BLUE_GREY);
        jlCenter.setFont(new Font("Tahoma", Font.PLAIN, FONT_SIZE));
        jlCenter.setSize(jlCenter.getPreferredSize());
        add(jlCenter);
        deck.shuffle();
        playerCard1 = deck.deal();
        playerCard1.flip();
        player.addCard(playerCard1);
        aiCard1 = deck.deal();
        AI.addCard(aiCard1);
        playerCard2 = deck.deal();
        playerCard2.flip();
        player.addCard(playerCard2);
        aiCard2 = deck.deal();
        AI.addCard(aiCard2);
        playerCard3 = deck.deal();
        playerCard3.flip();
        player.addCard(playerCard3);
        aiCard3 = deck.deal();
        AI.addCard(aiCard3);
        centerCard1 = deck.deal();
        centerCard2 = deck.deal();
        centerCard3 = deck.deal();
        jbCenterCard1 = new JButton(new ImageIcon(centerCard1.getFront()));
        jbCenterCard2 = new JButton(new ImageIcon(centerCard2.getFront()));
        jbCenterCard3 = new JButton(new ImageIcon(centerCard3.getFront()));
        jbPlayerCard1 = new JButton(new ImageIcon(playerCard1.getFront()));
        jbPlayerCard2 = new JButton(new ImageIcon(playerCard2.getFront()));
        jbPlayerCard3 = new JButton(new ImageIcon(playerCard3.getFront()));
        playerHand = player.thirtyOneTotal();
        jlPlayerHand.setText("Value: " + String.valueOf(playerHand));
        jlPlayerHand.setLocation(PLAYER_HAND_LABEL_X, PLAYER_HAND_LABEL_Y);
        jlPlayerHand.setForeground(LABEL_BLUE_GREY);
        jlPlayerHand.setFont(new Font("Tahoma", Font.PLAIN, FONT_SIZE));
        jlPlayerHand.setSize(jlCenter.getPreferredSize());
        add(jlPlayerHand);
        jbPlayerCard4 = null;
        iTurn = rand.nextInt(RANDOM_OPTION);
        revalidate();
        repaint();
        if (iTurn == 0) {
            if (values.get(centerCard1.getRank()) > values
                .get(centerCard2.getRank())) {
                if (values.get(centerCard1.getRank()) > values
                    .get(centerCard3.getRank())) {
                    AI.addCard(centerCard1);
                    aiCard4 = centerCard1;
                    jbCenterCard1.setVisible(false);
                } else {
                    AI.addCard(centerCard3);
                    aiCard4 = centerCard3;
                    jbCenterCard3.setVisible(false);
                }
            } else {
                if (values.get(centerCard2.getRank()) > values
                    .get(centerCard3.getRank())) {
                    AI.addCard(centerCard2);
                    aiCard4 = centerCard2;
                    jbCenterCard2.setVisible(false);
                } else {
                    AI.addCard(centerCard3);
                    aiCard4 = centerCard3;
                    jbCenterCard3.setVisible(false);
                }
            }
            if (values.get(aiCard1.getRank()) < values.get(aiCard2.getRank())) {
                if (values.get(aiCard1.getRank()) < values
                    .get(aiCard3.getRank())) {
                    if (values.get(aiCard1.getRank()) < values
                        .get(aiCard4.getRank())) {
                        if (!jbCenterCard1.isVisible()) {
                            jbCenterCard1 = new JButton(
                                new ImageIcon(aiCard1.getFront()));
                            jbCenterCard1.setVisible(true);
                        } else if (!jbCenterCard2.isVisible()) {
                            jbCenterCard2 = new JButton(
                                new ImageIcon(aiCard1.getFront()));
                            jbCenterCard2.setVisible(true);
                        } else {
                            jbCenterCard3 = new JButton(
                                new ImageIcon(aiCard1.getFront()));
                            jbCenterCard3.setVisible(true);
                        }
                    } else {

                        if (!jbCenterCard1.isVisible()) {
                            jbCenterCard1 = new JButton(
                                new ImageIcon(aiCard4.getFront()));
                            jbCenterCard1.setVisible(true);
                        } else if (!jbCenterCard2.isVisible()) {
                            jbCenterCard2 = new JButton(
                                new ImageIcon(aiCard4.getFront()));
                            jbCenterCard2.setVisible(true);
                        } else {
                            jbCenterCard3 = new JButton(
                                new ImageIcon(aiCard4.getFront()));
                            jbCenterCard3.setVisible(true);
                        }
                    }
                } else {
                    if (values.get(aiCard3.getRank()) < values
                        .get(aiCard4.getRank())) {
                        if (!jbCenterCard1.isVisible()) {
                            jbCenterCard1 = new JButton(
                                new ImageIcon(aiCard3.getFront()));
                            jbCenterCard1.setVisible(true);
                        } else if (!jbCenterCard2.isVisible()) {
                            jbCenterCard2 = new JButton(
                                new ImageIcon(aiCard3.getFront()));
                            jbCenterCard2.setVisible(true);
                        } else {
                            jbCenterCard3 = new JButton(
                                new ImageIcon(aiCard3.getFront()));
                            jbCenterCard3.setVisible(true);
                        }
                    } else {
                        if (!jbCenterCard1.isVisible()) {
                            jbCenterCard1 = new JButton(
                                new ImageIcon(aiCard4.getFront()));
                            jbCenterCard1.setVisible(true);
                        } else if (!jbCenterCard2.isVisible()) {
                            jbCenterCard2 = new JButton(
                                new ImageIcon(aiCard4.getFront()));
                            jbCenterCard2.setVisible(true);
                        } else {
                            jbCenterCard3 = new JButton(
                                new ImageIcon(aiCard4.getFront()));
                            jbCenterCard3.setVisible(true);
                        }
                    }
                }
            } else {
                if (values.get(aiCard2.getRank()) < values
                    .get(aiCard3.getRank())) {
                    if (values.get(aiCard2.getRank()) < values
                        .get(aiCard4.getRank())) {
                        if (!jbCenterCard1.isVisible()) {
                            jbCenterCard1 = new JButton(
                                new ImageIcon(aiCard2.getFront()));
                            jbCenterCard1.setVisible(true);
                        } else if (!jbCenterCard2.isVisible()) {
                            jbCenterCard2 = new JButton(
                                new ImageIcon(aiCard2.getFront()));
                            jbCenterCard2.setVisible(true);
                        } else {
                            jbCenterCard3 = new JButton(
                                new ImageIcon(aiCard2.getFront()));
                            jbCenterCard3.setVisible(true);
                        }
                    } else {
                        if (!jbCenterCard1.isVisible()) {
                            jbCenterCard1 = new JButton(
                                new ImageIcon(aiCard4.getFront()));
                            jbCenterCard1.setVisible(true);
                        } else if (!jbCenterCard2.isVisible()) {
                            jbCenterCard2 = new JButton(
                                new ImageIcon(aiCard4.getFront()));
                            jbCenterCard2.setVisible(true);
                        } else {
                            jbCenterCard3 = new JButton(
                                new ImageIcon(aiCard4.getFront()));
                            jbCenterCard3.setVisible(true);
                        }
                    }
                } else {
                    if (values.get(aiCard3.getRank()) < values
                        .get(aiCard4.getRank())) {
                        if (!jbCenterCard1.isVisible()) {
                            jbCenterCard1 = new JButton(
                                new ImageIcon(aiCard3.getFront()));
                            jbCenterCard1.setVisible(true);
                        } else if (!jbCenterCard2.isVisible()) {
                            jbCenterCard2 = new JButton(
                                new ImageIcon(aiCard3.getFront()));
                            jbCenterCard2.setVisible(true);
                        } else {
                            jbCenterCard3 = new JButton(
                                new ImageIcon(aiCard3.getFront()));
                            jbCenterCard3.setVisible(true);
                        }
                    } else {
                        if (!jbCenterCard1.isVisible()) {
                            jbCenterCard1 = new JButton(
                                new ImageIcon(aiCard4.getFront()));
                            jbCenterCard1.setVisible(true);
                        } else if (!jbCenterCard2.isVisible()) {
                            jbCenterCard2 = new JButton(
                                new ImageIcon(aiCard4.getFront()));
                            jbCenterCard2.setVisible(true);
                        } else {
                            jbCenterCard3 = new JButton(
                                new ImageIcon(aiCard4.getFront()));
                            jbCenterCard3.setVisible(true);
                        }
                    }
                }
            }
        }
        jbCenterCard1.setBounds(CARD_X_1, CENTER_CARD_Y, CARD_WIDTH,
            CARD_HEIGHT);
        add(jbCenterCard1);
        jbCenterCard2.setBounds(CARD_X_2, CENTER_CARD_Y, CARD_WIDTH,
            CARD_HEIGHT);
        add(jbCenterCard2);
        jbCenterCard3.setBounds(CARD_X_3, CENTER_CARD_Y, CARD_WIDTH,
            CARD_HEIGHT);
        add(jbCenterCard3);
        jlPlayer.setLocation(PLAYER_VALUE_X, PLAYER_VALUE_Y);
        jlPlayer.setForeground(LABEL_BLUE_GREY);
        jlPlayer.setFont(new Font("Tahoma", Font.PLAIN, FONT_SIZE));
        jlPlayer.setSize(jlPlayer.getPreferredSize());
        add(jlPlayer);
        jbPlayerCard1.setBounds(CARD_X_1, PLAYER_CARD_Y, CARD_WIDTH,
            CARD_HEIGHT);
        add(jbPlayerCard1);
        jbPlayerCard2.setBounds(CARD_X_2, PLAYER_CARD_Y, CARD_WIDTH,
            CARD_HEIGHT);
        add(jbPlayerCard2);
        jbPlayerCard3.setBounds(CARD_X_3, PLAYER_CARD_Y, CARD_WIDTH,
            CARD_HEIGHT);
        add(jbPlayerCard3);
        jlPlayerScore.setLocation(PLAYER_SCORE_X, PLAYER_SCORE_Y);
        jlPlayerScore.setForeground(LABEL_BLUE_GREY);
        jlPlayerScore.setFont(new Font("Tahoma", Font.PLAIN, FONT_SIZE));
        jlPlayerScore.setSize(jlPlayerScore.getPreferredSize());
        add(jlPlayerScore);
        revalidate();
        repaint();
    }

    /**
     * Action listeners for each of the center buttons.
     */
    private void cardButtons() {
        jbCenterCard1.addActionListener(ae -> {
            if (player.handSize() < MAX_HAND) {
                playerCard4 = centerCard1;
                player.addCard(playerCard4);
                jbPlayerCard4 =
                        new JButton(new ImageIcon(playerCard4.getFront()));
                jbPlayerCard4.setBounds(CARD_X_4, PLAYER_CARD_Y, CARD_WIDTH,
                    CARD_HEIGHT);
                add(jbPlayerCard4);
                remove(jlPlayerHand);
                playerHand = player.thirtyOneTotal();
                jlPlayerHand.setText("Value: " + String.valueOf(playerHand));
                add(jlPlayerHand);
                centerCard1.flip();
                jbCenterCard1.setVisible(false);
                revalidate();
                repaint();
                switchCards();
            }
        });
        jbCenterCard2.addActionListener(ae -> {
            if (player.handSize() < MAX_HAND) {
                playerCard4 = centerCard2;
                player.addCard(playerCard4);
                jbPlayerCard4 =
                        new JButton(new ImageIcon(playerCard4.getFront()));
                jbPlayerCard4.setBounds(CARD_X_4, PLAYER_CARD_Y, CARD_WIDTH,
                    CARD_HEIGHT);
                add(jbPlayerCard4);
                remove(jlPlayerHand);
                playerHand = player.thirtyOneTotal();
                jlPlayerHand.setText("Value: " + String.valueOf(playerHand));
                add(jlPlayerHand);
                centerCard2.flip();
                jbCenterCard2.setVisible(false);
                revalidate();
                repaint();
                switchCards();
            }
        });
        jbCenterCard3.addActionListener(ae -> {
            if (player.handSize() < MAX_HAND) {
                playerCard4 = centerCard3;
                player.addCard(playerCard4);
                jbPlayerCard4 =
                        new JButton(new ImageIcon(playerCard4.getFront()));
                jbPlayerCard4.setBounds(CARD_X_4, PLAYER_CARD_Y, CARD_WIDTH,
                    CARD_HEIGHT);
                add(jbPlayerCard4);
                remove(jlPlayerHand);
                playerHand = player.thirtyOneTotal();
                jlPlayerHand.setText("Value: " + String.valueOf(playerHand));
                add(jlPlayerHand);
                centerCard3.flip();
                jbCenterCard3.setVisible(false);
                revalidate();
                repaint();
                switchCards();
            }
        });
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
            os.writeObject(centerCard1);
            os.writeObject(centerCard2);
            os.writeObject(centerCard3);
            os.writeInt(iTurn);
            os.writeInt(playerHand);
            os.writeInt(playerScore);
            os.writeObject(jlPlayerHand);
            os.writeObject(jlPlayerScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the game state from a file.
     */
    public final void loadGame() {
        System.out.println("Loading game");
        remove(deck);
        remove(AI);
        remove(player);
        remove(centerCard1);
        remove(centerCard2);
        remove(centerCard3);
        remove(jbCenterCard1);
        remove(jbCenterCard2);
        remove(jbCenterCard3);
        remove(jbPlayerCard1);
        remove(jbPlayerCard2);
        remove(jbPlayerCard3);
        remove(jlPlayerHand);
        remove(jlPlayerScore);
        try (FileInputStream fileStream = new FileInputStream("ThirtyOne.ser");
                ObjectInputStream os = new ObjectInputStream(fileStream);) {
            deck = (Deck) os.readObject();
            AI = (Hand) os.readObject();
            player = (Hand) os.readObject();
            centerCard1 = (Card) os.readObject();
            centerCard2 = (Card) os.readObject();
            centerCard3 = (Card) os.readObject();
            jbCenterCard1 = new JButton(new ImageIcon(centerCard1.getFront()));
            jbCenterCard1.setBounds(CARD_X_1, CENTER_CARD_Y, CARD_WIDTH,
                CARD_HEIGHT);
            add(jbCenterCard1);
            jbCenterCard2 = new JButton(new ImageIcon(centerCard2.getFront()));
            jbCenterCard2.setBounds(CARD_X_2, CENTER_CARD_Y, CARD_WIDTH,
                CARD_HEIGHT);
            add(jbCenterCard2);
            jbCenterCard3 = new JButton(new ImageIcon(centerCard3.getFront()));
            jbCenterCard3.setBounds(CARD_X_3, CENTER_CARD_Y, CARD_WIDTH,
                CARD_HEIGHT);
            add(jbCenterCard3);
            jbPlayerCard1 =
                    new JButton(new ImageIcon(player.getCard(0).getFront()));
            jbPlayerCard1.setBounds(CARD_X_1, PLAYER_CARD_Y, CARD_WIDTH,
                CARD_HEIGHT);
            add(jbPlayerCard1);
            jbPlayerCard2 =
                    new JButton(new ImageIcon(player.getCard(1).getFront()));
            jbPlayerCard2.setBounds(CARD_X_2, PLAYER_CARD_Y, CARD_WIDTH,
                CARD_HEIGHT);
            add(jbPlayerCard2);
            jbPlayerCard3 =
                    new JButton(new ImageIcon(player.getCard(2).getFront()));
            jbPlayerCard3.setBounds(CARD_X_3, PLAYER_CARD_Y, CARD_WIDTH,
                CARD_HEIGHT);
            add(jbPlayerCard3);
            iTurn = os.readInt();
            playerHand = os.readInt();
            playerScore = os.readInt();
            jlPlayerHand.setText("Value: " + String.valueOf(playerHand));
            add(jlPlayerHand);
            jlPlayerScore.setText("Player's Score: " + playerScore);
            add(jlPlayerScore);
            revalidate();
            repaint();
            cardButtons();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                "No Save file found!\nHave you saved the game yet?",
                "No Save File", JOptionPane.WARNING_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Resets the game state for a new hand.
     */
    private void reset() {
        remove(deck);
        remove(AI);
        remove(player);
        remove(centerCard1);
        remove(centerCard2);
        remove(centerCard3);
        remove(jbCenterCard1);
        remove(jbCenterCard2);
        remove(jbCenterCard3);
        remove(jbPlayerCard1);
        remove(jbPlayerCard2);
        remove(jbPlayerCard3);
        remove(jbPlayerCard4);
        remove(jlPlayerScore);
        deck = new Deck();
        AI = new Hand();
        player = new Hand();
        jlPlayerScore = new JLabel("Player's Score: " + playerScore);
        add(jlPlayerScore);
        newGame();
        setCards();
        cardButtons();
        revalidate();
        repaint();
    }

    /**
     * Decides which player wins, sees if the player wants to play again, and
     * starts a new game or checks to see if they made the high score.
     */
    private void results() {
        remove(jlPlayerHand);
        playerHand = player.thirtyOneTotal();
        jlPlayerHand.setText("Value: " + String.valueOf(playerHand));
        add(jlPlayerHand);
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
     * Allows both players to discard a card to the center.
     */
    private void switchCards() {
        JOptionPane.showMessageDialog(null,
            "Choose a card to return to the center");
        jbPlayerCard1.addActionListener(ae -> {
            if (player.handSize() > MIN_HAND) {
                if (!jbCenterCard1.isVisible()) {
                    jbCenterCard1 = jbPlayerCard1;
                    player.returnCard(playerCard1);
                    jbCenterCard1.setVisible(true);

                } else if (!jbCenterCard2.isVisible()) {
                    jbCenterCard2 = jbPlayerCard1;
                    player.returnCard(playerCard1);
                    jbCenterCard2.setVisible(true);
                } else {
                    jbCenterCard3 = jbPlayerCard1;
                    player.returnCard(playerCard1);
                    jbCenterCard3.setVisible(true);
                }
                remove(jbPlayerCard1);
                jbPlayerCard2.setBounds(CARD_X_1, PLAYER_CARD_Y, CARD_WIDTH,
                    CARD_HEIGHT);
                jbPlayerCard3.setBounds(CARD_X_2, PLAYER_CARD_Y, CARD_WIDTH,
                    CARD_HEIGHT);
                jbPlayerCard4.setBounds(CARD_X_3, PLAYER_CARD_Y, CARD_WIDTH,
                    CARD_HEIGHT);
                revalidate();
                repaint();
                results();
            } else {
                JOptionPane.showMessageDialog(null,
                    "You don't have enough cards");
            }
        });
        jbPlayerCard2.addActionListener(ae -> {
            if (player.handSize() > MIN_HAND) {
                if (!jbCenterCard1.isVisible()) {
                    jbCenterCard1 = jbPlayerCard2;
                    player.returnCard(playerCard2);
                    jbPlayerCard2.setVisible(false);
                    jbCenterCard1.setVisible(true);
                } else if (!jbCenterCard2.isVisible()) {
                    jbCenterCard2 = jbPlayerCard2;
                    player.returnCard(playerCard2);
                    jbPlayerCard2.setVisible(false);
                    jbCenterCard2.setVisible(true);
                } else {
                    jbCenterCard3 = jbPlayerCard2;
                    player.returnCard(playerCard2);
                    jbPlayerCard2.setVisible(false);
                    jbCenterCard3.setVisible(true);
                }
                jbPlayerCard1.setBounds(CARD_X_1, PLAYER_CARD_Y, CARD_WIDTH,
                    CARD_HEIGHT);
                remove(jbPlayerCard2);
                jbPlayerCard3.setBounds(CARD_X_2, PLAYER_CARD_Y, CARD_WIDTH,
                    CARD_HEIGHT);
                jbPlayerCard4.setBounds(CARD_X_3, PLAYER_CARD_Y, CARD_WIDTH,
                    CARD_HEIGHT);
                revalidate();
                repaint();
                results();
            } else {
                JOptionPane.showMessageDialog(null,
                    "You don't have enough cards");
            }
        });
        jbPlayerCard3.addActionListener(ae -> {
            if (player.handSize() > MIN_HAND) {
                if (!jbCenterCard1.isVisible()) {
                    jbCenterCard1 = jbPlayerCard3;
                    player.returnCard(playerCard3);
                    jbPlayerCard3.setVisible(false);
                    jbCenterCard1.setVisible(true);
                } else if (!jbCenterCard2.isVisible()) {
                    jbCenterCard2 = jbPlayerCard3;
                    player.returnCard(playerCard3);
                    jbPlayerCard3.setVisible(false);
                    jbCenterCard2.setVisible(true);
                } else {
                    jbCenterCard3 = jbPlayerCard3;
                    player.returnCard(playerCard3);
                    jbPlayerCard3.setVisible(false);
                    jbCenterCard3.setVisible(true);
                }
                jbPlayerCard1.setBounds(CARD_X_1, PLAYER_CARD_Y, CARD_WIDTH,
                    CARD_HEIGHT);
                jbPlayerCard2.setBounds(CARD_X_2, PLAYER_CARD_Y, CARD_WIDTH,
                    CARD_HEIGHT);
                remove(jbPlayerCard3);
                jbPlayerCard4.setBounds(CARD_X_3, PLAYER_CARD_Y, CARD_WIDTH,
                    CARD_HEIGHT);
                revalidate();
                repaint();
                results();
            } else {
                JOptionPane.showMessageDialog(null,
                    "You don't have enough cards");
            }
        });
        jbPlayerCard4.addActionListener(ae -> {
            if (player.handSize() > MIN_HAND) {
                if (!jbCenterCard1.isVisible()) {
                    jbCenterCard1 = jbPlayerCard4;
                    player.returnCard(playerCard4);
                    jbPlayerCard4.setVisible(false);
                    jbCenterCard1.setVisible(true);
                } else if (!jbCenterCard2.isVisible()) {
                    jbCenterCard2 = jbPlayerCard4;
                    player.returnCard(playerCard4);
                    jbPlayerCard4.setVisible(false);
                    jbCenterCard2.setVisible(true);
                } else {
                    jbCenterCard3 = jbPlayerCard4;
                    player.returnCard(playerCard4);
                    jbPlayerCard4.setVisible(false);
                    jbCenterCard3.setVisible(true);
                }
                remove(jbPlayerCard4);
                revalidate();
                repaint();
                if (iTurn == 1) {
                    if (values.get(centerCard1.getRank()) > values
                        .get(centerCard2.getRank())) {
                        if (values.get(centerCard1.getRank()) > values
                            .get(centerCard3.getRank())) {
                            AI.addCard(centerCard1);
                            aiCard4 = centerCard1;
                            jbCenterCard1.setVisible(false);
                        } else {
                            AI.addCard(centerCard3);
                            aiCard4 = centerCard3;
                            jbCenterCard3.setVisible(false);
                        }
                    } else {
                        if (values.get(centerCard2.getRank()) > values
                            .get(centerCard3.getRank())) {
                            AI.addCard(centerCard2);
                            aiCard4 = centerCard2;
                            jbCenterCard2.setVisible(false);
                        } else {
                            AI.addCard(centerCard3);
                            aiCard4 = centerCard3;
                            jbCenterCard3.setVisible(false);
                        }
                    }
                    if (values.get(aiCard1.getRank()) < values
                        .get(aiCard2.getRank())) {
                        if (values.get(aiCard1.getRank()) < values
                            .get(aiCard3.getRank())) {
                            if (values.get(aiCard1.getRank()) < values
                                .get(aiCard4.getRank())) {
                                if (!jbCenterCard1.isVisible()) {
                                    jbCenterCard1 = new JButton(
                                        new ImageIcon(aiCard1.getFront()));
                                    jbCenterCard1.setVisible(true);
                                } else if (!jbCenterCard2.isVisible()) {
                                    jbCenterCard2 = new JButton(
                                        new ImageIcon(aiCard1.getFront()));
                                    jbCenterCard2.setVisible(true);
                                } else {
                                    jbCenterCard3 = new JButton(
                                        new ImageIcon(aiCard1.getFront()));
                                    jbCenterCard3.setVisible(true);
                                }
                            } else {

                                if (!jbCenterCard1.isVisible()) {
                                    jbCenterCard1 = new JButton(
                                        new ImageIcon(aiCard4.getFront()));
                                    jbCenterCard1.setVisible(true);
                                } else if (!jbCenterCard2.isVisible()) {
                                    jbCenterCard2 = new JButton(
                                        new ImageIcon(aiCard4.getFront()));
                                    jbCenterCard2.setVisible(true);
                                } else {
                                    jbCenterCard3 = new JButton(
                                        new ImageIcon(aiCard4.getFront()));
                                    jbCenterCard3.setVisible(true);
                                }
                            }
                        } else {
                            if (values.get(aiCard3.getRank()) < values
                                .get(aiCard4.getRank())) {
                                if (!jbCenterCard1.isVisible()) {
                                    jbCenterCard1 = new JButton(
                                        new ImageIcon(aiCard3.getFront()));
                                    jbCenterCard1.setVisible(true);
                                } else if (!jbCenterCard2.isVisible()) {
                                    jbCenterCard2 = new JButton(
                                        new ImageIcon(aiCard3.getFront()));
                                    jbCenterCard2.setVisible(true);
                                } else {
                                    jbCenterCard3 = new JButton(
                                        new ImageIcon(aiCard3.getFront()));
                                    jbCenterCard3.setVisible(true);
                                }
                            } else {
                                if (!jbCenterCard1.isVisible()) {
                                    jbCenterCard1 = new JButton(
                                        new ImageIcon(aiCard4.getFront()));
                                    jbCenterCard1.setVisible(true);
                                } else if (!jbCenterCard2.isVisible()) {
                                    jbCenterCard2 = new JButton(
                                        new ImageIcon(aiCard4.getFront()));
                                    jbCenterCard2.setVisible(true);
                                } else {
                                    jbCenterCard3 = new JButton(
                                        new ImageIcon(aiCard4.getFront()));
                                    jbCenterCard3.setVisible(true);
                                }
                            }
                        }
                    } else {
                        if (values.get(aiCard2.getRank()) < values
                            .get(aiCard3.getRank())) {
                            if (values.get(aiCard2.getRank()) < values
                                .get(aiCard4.getRank())) {
                                if (!jbCenterCard1.isVisible()) {
                                    jbCenterCard1 = new JButton(
                                        new ImageIcon(aiCard2.getFront()));
                                    jbCenterCard1.setVisible(true);
                                } else if (!jbCenterCard2.isVisible()) {
                                    jbCenterCard2 = new JButton(
                                        new ImageIcon(aiCard2.getFront()));
                                    jbCenterCard2.setVisible(true);
                                } else {
                                    jbCenterCard3 = new JButton(
                                        new ImageIcon(aiCard2.getFront()));
                                    jbCenterCard3.setVisible(true);
                                }
                            } else {
                                if (!jbCenterCard1.isVisible()) {
                                    jbCenterCard1 = new JButton(
                                        new ImageIcon(aiCard4.getFront()));
                                    jbCenterCard1.setVisible(true);
                                } else if (!jbCenterCard2.isVisible()) {
                                    jbCenterCard2 = new JButton(
                                        new ImageIcon(aiCard4.getFront()));
                                    jbCenterCard2.setVisible(true);
                                } else {
                                    jbCenterCard3 = new JButton(
                                        new ImageIcon(aiCard4.getFront()));
                                    jbCenterCard3.setVisible(true);
                                }
                            }
                        } else {
                            if (values.get(aiCard3.getRank()) < values
                                .get(aiCard4.getRank())) {
                                if (!jbCenterCard1.isVisible()) {
                                    jbCenterCard1 = new JButton(
                                        new ImageIcon(aiCard3.getFront()));
                                    jbCenterCard1.setVisible(true);
                                } else if (!jbCenterCard2.isVisible()) {
                                    jbCenterCard2 = new JButton(
                                        new ImageIcon(aiCard3.getFront()));
                                    jbCenterCard2.setVisible(true);
                                } else {
                                    jbCenterCard3 = new JButton(
                                        new ImageIcon(aiCard3.getFront()));
                                    jbCenterCard3.setVisible(true);
                                }
                            } else {
                                if (!jbCenterCard1.isVisible()) {
                                    jbCenterCard1 = new JButton(
                                        new ImageIcon(aiCard4.getFront()));
                                    jbCenterCard1.setVisible(true);
                                } else if (!jbCenterCard2.isVisible()) {
                                    jbCenterCard2 = new JButton(
                                        new ImageIcon(aiCard4.getFront()));
                                    jbCenterCard2.setVisible(true);
                                } else {
                                    jbCenterCard3 = new JButton(
                                        new ImageIcon(aiCard4.getFront()));
                                    jbCenterCard3.setVisible(true);
                                }
                            }
                        }
                    }
                }
                results();
            } else {
                JOptionPane.showMessageDialog(null,
                    "You don't have enough cards");
            }
        });
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
