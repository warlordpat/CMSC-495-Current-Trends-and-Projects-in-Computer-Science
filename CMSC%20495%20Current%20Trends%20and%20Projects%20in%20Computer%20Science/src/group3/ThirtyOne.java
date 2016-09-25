// File: Blackjack.java
// Author: Alex Burch
// Date: Sep 23, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: implements a graphical game of Thirty-One.

package group3;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * ThirtyOne implements a graphical game of Thirty-One. The game includes
 * the ability to 
 */
public class ThirtyOne extends JPanel {
    /**
     * A green color like a card table.
     */
    private static final Color CARD_TABLE_GREEN = new Color(7, 99, 36);
    /**
     * Set screen width
     */
    private int SCREEN_X = 600;
    /**
     * Set screen height
     */
    private int SCREEN_Y = 600;
    /**
     * X coordinate of the player's hand
     */
    private int HAND_X = 200;
    /**
     * Y coordinate of the player's hand
     */
    private int HAND_Y = 100;
    /**
     * Height of a card
     */
    private int CARD_HEIGHT = 96;
    /**
     * Width of a card
     */
    private int CARD_WIDTH = 72;
    /**
     * The maximum length of the initials for a high score.
     */
    private static final int INITIAL_LENGTH = 3;
    /**
     * The serial number required for serializing the class.
     */
    private static final long serialVersionUID = 510809952979347694L;
    /**
     * The current deck in play.
     */
    private Deck deck;
    /**
     * The AI's Hand.
     */
    private Hand AI;
    /**
     * The player's Hand.
     */
    private Hand player;
    /**
     * The cards in the player's hand
     */
    private Card playerCard1, playerCard2, playerCard3, playerCard4;
    /**
     * The cards in the AI's hand
     */
    private Card aiCard1, aiCard2, aiCard3;
    /**
     * The cards in the center
     */
    private Card centerCard1, centerCard2, centerCard3;
    /**
     * Integers for the center cards' values
     */
    private Integer iCard1, iCard2, iCard3;
    /**
     * Integers for the player's cards' values
     */
    private Integer iPlayer1, iPlayer2, iPlayer3, iPlayer4;
    /**
     * HashMap to get values from ranks
     */
    private static Map<Rank, Integer> values = new HashMap<>();
    static {
        values.put(Rank.ACE, 11);
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
     * Card images for the player's cards
     */
    private ImageIcon playerCardImage1, playerCardImage2, playerCardImage3;
    /**
     * Buttons for each of the player's cards
     */
    private JButton jbPlayerCard1, jbPlayerCard2, jbPlayerCard3, jbPlayerCard4;
    private JButton jbCenterCard1, jbCenterCard2, jbCenterCard3;
    /**
     * Card images for the center cards
     */
    private ImageIcon centerCardImage1, centerCardImage2, centerCardImage3;
    /**
     * Player's hand total
     */
    private int player31;
    /**
     * Integer to represent how close the player is to 31
     */
    private int playerAbs;
    /**
     * AI's hand total
     */
    private int ai31;
    /**
     * Integer to represent how close the AI is to 31
     */
    private int aiAbs;
    /**
     * Integer to show who's turn it is
     */
    private int iTurn;
    /**
     * ThirtyOne High Scores
     */
    private HighScores scores;
    /**
     * Boolean value for player's Spade cards
     */
    private boolean bSpade;
    /**
     * Boolean value for player's Heart cards
     */
    private boolean bHeart;
    /**
     * Boolean value for player's Club cards
     */
    private boolean bClub;
    /**
     * Boolean value for player's Diamond cards
     */
    private boolean bDiamond;
    /**
     * Spades radio buttons for positive and negative
     */
    JRadioButton spadePos, spadeNeg;
    /**
     * Hearts radio buttons for positive and negative
     */
    JRadioButton heartPos, heartNeg;
    /**
     * Clubs radio buttons for positive and negative
     */
    JRadioButton clubPos, clubNeg;
    /**
     * Diamonds radio buttons for positive and negative
     */
    JRadioButton diamondPos, diamondNeg;
    /**
     * ThirtyOne frame
     */
    private JFrame frame; 
    /**
     * Integer to represent the player's current score
     */
    private int playerScore;
    /**
     * Panel for the center cards
     */
    private JPanel CenterPanel;
    /**
     * Panel for the player's cards
     */
    private JPanel PlayerPanel;
    /**
     * Panel for splitting top CenterPanel and PlayerPanel
     */
    private JSplitPane splitPaneHorizontal;
    /**
     * Panel to choose which suit is positive or negative
     */
    private JPanel dialPanel;
    /**
     * Panel for splitting splitPaneHorizontal and dialPanel
     */
    private JSplitPane splitBottom;
    /**
     * Constructs a new ThirtyOne game.
     */
    public ThirtyOne() {
        deck = new Deck();
        AI = new Hand();
        player = new Hand();        
        playerScore = 0;
        String path = System.getProperty("user.home");
        path += File.separator + "CGS";
        File customDir = new File(path);
        File scoreFile = new File(customDir, "ThirtyOne.score");
        if (customDir.exists()) {
            System.out.println(customDir + " already exists");
            // load a score file

            if (scoreFile.exists()) {
                System.out.println(scoreFile + " already exists");
                loadHighScores(scoreFile);
            } else {
                // create new scorefile
                System.out.println(scoreFile + " created");
                scores = new HighScores();
                saveHighScores(scoreFile);
            }

        } else if (customDir.mkdirs()) {
            System.out.println(customDir + " was created");
            System.out.println(scoreFile + " created");
            // Make a blank score file
            scores = new HighScores();
            saveHighScores(scoreFile);
        } else {
            System.out.println(customDir + " was not created");
            // throw an error, why can't we access the user dir?
        }
    } // end constructor

    /**
     * Saves the high scores to a given file.
     *
     * @param scoreFile
     *            The file to save scores to
     */
    private void saveHighScores(final File scoreFile) {
        try (FileOutputStream filestream = new FileOutputStream(scoreFile);
                ObjectOutputStream os = new ObjectOutputStream(filestream);) {
            os.writeObject(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the high scores from a given file.
     *
     * @param scoreFile
     *            The file to load scores from
     */
    private void loadHighScores(final File scoreFile) {
        try (FileInputStream filestream = new FileInputStream(scoreFile);
                ObjectInputStream os = new ObjectInputStream(filestream);) {
            scores = (HighScores) os.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the game
     */
    public final void begin() {
        System.out.println("beginning");
        newGame();
        createGUI();
    }

    /**
     * Creates a new game session.
     */
    final void newGame() {
        remove(deck);
        remove(AI);
        remove(player);
        deck = new Deck();
        AI = new Hand();
        player = new Hand();
        deck.shuffle();
    }

    /**
     * Displays a string version of the current hands.
     *
     * @return A String representation of the current hands
     */
    public final String display() {
        String output = "";
        output += "\nPlayer Hand: " + player + " = " + player31;
        return output;
    }

    /**
     * Resets the game state for a new hand.
     */
    private void reset() {
        
    } // end method
    /**
     * Creates a new initial BlackJack GUI.
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
                saveHighScores(scoreFile);
                System.out.println("Frame is closing");
            }
        });
        createMenu(frame);
        createSplit();
        frame.setContentPane(splitBottom);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    /**
     * Creates the PlayerPanel and adds the player's hand
     */
    private void createPlayerPanel() {
        PlayerPanel = new JPanel();
        playerCard1 = deck.deal();
        player.add(playerCard1);
        iPlayer1 = values.get(playerCard1.getRank());
        playerCard2 = deck.deal();
        player.add(playerCard2);
        iPlayer2 = values.get(playerCard2.getRank());
        playerCard3 = deck.deal();
        player.add(playerCard3);
        iPlayer3 = values.get(playerCard3.getRank());
        playerCardImage1 = new ImageIcon(playerCard1.front);
        playerCardImage2 = new ImageIcon(playerCard2.front);
        playerCardImage3 = new ImageIcon(playerCard3.front);
        jbPlayerCard1 = new JButton(playerCardImage1);
        jbPlayerCard2 = new JButton(playerCardImage2);
        jbPlayerCard3 = new JButton(playerCardImage3);
        jbPlayerCard4 = new JButton();
        jbPlayerCard4.setSize(CARD_WIDTH, CARD_HEIGHT);
        jbPlayerCard4.setVisible(false);
        PlayerPanel.add(jbPlayerCard1);
        PlayerPanel.add(jbPlayerCard2);
        PlayerPanel.add(jbPlayerCard3);
        PlayerPanel.add(jbPlayerCard4);
    }
    /**
     * Creates the CenterPanel and adds the center cards
     */
    private void createCenterPanel() {
        CenterPanel = new JPanel();
        aiCard1 = deck.deal();
        aiCard2 = deck.deal();
        aiCard3 = deck.deal();
        ai31 += values.get(aiCard1.getRank());
        ai31 += values.get(aiCard2.getRank());
        ai31 += values.get(aiCard3.getRank());
        centerCard1 = deck.deal();
        centerCard2 = deck.deal();
        centerCard3 = deck.deal();
        centerCardImage1 = new ImageIcon(centerCard1.front);
        centerCardImage2 = new ImageIcon(centerCard2.front);
        centerCardImage3 = new ImageIcon(centerCard3.front);
        jbCenterCard1 = new JButton(centerCardImage1);
        jbCenterCard2 = new JButton(centerCardImage2);
        jbCenterCard3 = new JButton(centerCardImage3);
        iCard1 = values.get(centerCard1.getRank());
        iCard2 = values.get(centerCard2.getRank());
        iCard3 = values.get(centerCard3.getRank());
        CenterPanel.setBackground(CARD_TABLE_GREEN);
        /**
         * Random number generator to see who goes first
         */
        Random rand = new Random();
        iTurn = rand.nextInt(2) + 1;
        /**
         * Player goes first, add all 3 cards to CenterPanel
         */
        if (iTurn == 1) {
            CenterPanel.add(jbCenterCard1);
            CenterPanel.add(jbCenterCard2);
            CenterPanel.add(jbCenterCard3);
        }
        /**
         * AI goes first, picks the highest card. Add the
         * two remaining center cards to the center
         */
        else {
            if (iCard1 > iCard2) {
                if (iCard1 > iCard3) {
                    ai31 += values.get(centerCard1.getRank());
                    CenterPanel.add(jbCenterCard2);
                    CenterPanel.add(jbCenterCard3);
                } else {
                    ai31 += values.get(centerCard3.getRank());
                    CenterPanel.add(jbCenterCard1);
                    CenterPanel.add(jbCenterCard2);
                }
            } else {
                if (iCard2 > iCard3) {
                    ai31 += values.get(centerCard2.getRank());
                    CenterPanel.add(jbCenterCard1);
                    CenterPanel.add(jbCenterCard3);
                } else {
                    ai31 += values.get(centerCard3.getRank());
                    CenterPanel.add(jbCenterCard1);
                    CenterPanel.add(jbCenterCard2);
                }
            }
        }
        jbCenterCard1.addActionListener(ae -> {
            if (player.handSize() < 4) {
                player.addCard(centerCard1);
                playerCard4 = centerCard1;
                iPlayer4 = values.get(playerCard4.getRank());
                jbPlayerCard4 = new JButton(centerCardImage1);
                jbPlayerCard4.setVisible(true);
                CenterPanel.remove(centerCard1);
            } else {
                JOptionPane.showMessageDialog(null, "You already have 4 cards in your hand");
            }
        });
        jbCenterCard2.addActionListener(ae -> {
            if (player.handSize() < 4) {
                player.addCard(centerCard2);
                playerCard4 = centerCard2;
                iPlayer4 = values.get(playerCard4.getRank());
                jbPlayerCard4 = new JButton(centerCardImage2);
                jbPlayerCard4.setVisible(true);
                CenterPanel.remove(centerCard2);
                playerAbs = 31 - player31;
                playerAbs = Math.abs(playerAbs);
                aiAbs = 31 - ai31;
                aiAbs = Math.abs(aiAbs);
            } else {
                JOptionPane.showMessageDialog(null, "You already have 4 cards in your hand");
            }
        });
        jbCenterCard3.addActionListener(ae -> {
            if (player.handSize() < 4) {
                player.addCard(centerCard3);
                playerCard4 = centerCard3;
                iPlayer4 = values.get(playerCard4.getRank());
                jbPlayerCard4 = new JButton(centerCardImage3);
                jbPlayerCard4.setVisible(true);
                CenterPanel.remove(centerCard3);
                playerAbs = 31 - player31;
                playerAbs = Math.abs(playerAbs);
                aiAbs = 31 - ai31;
                aiAbs = Math.abs(aiAbs);
            } else {
                JOptionPane.showMessageDialog(null, "You already have 4 cards in your hand");
            }
        });
    }
    /**
     * Creates the dialPanel, which houses all of the radio buttons
     * to choose whether a suit is positive or negative
     */
    private void createDialPanel() {
        dialPanel = new JPanel(new GridLayout(0,3));
        JLabel jlSpade = new JLabel("Spade");
        JRadioButton spadePos = new JRadioButton("Positive");
        spadePos.setSelected(true);
        JRadioButton spadeNeg = new JRadioButton("Negative");
        spadeNeg.setSelected(true);
        ButtonGroup spadeGroup = new ButtonGroup();
        spadeGroup.add(spadePos);
        spadeGroup.add(spadeNeg);
        JLabel jlHeart = new JLabel("Heart");
        JRadioButton heartPos = new JRadioButton("Positive");
        heartPos.setSelected(true);
        JRadioButton heartNeg = new JRadioButton("Negative");
        heartNeg.setSelected(true);
        ButtonGroup heartGroup = new ButtonGroup();
        heartGroup.add(heartPos);
        heartGroup.add(heartNeg);
        JLabel jlClub = new JLabel("Club");
        JRadioButton clubPos = new JRadioButton("Positive");
        clubPos.setSelected(true);
        JRadioButton clubNeg = new JRadioButton("Negative");
        clubNeg.setSelected(true);
        ButtonGroup clubGroup = new ButtonGroup();
        clubGroup.add(clubPos);
        clubGroup.add(clubNeg);
        JLabel jlDiamond = new JLabel("Diamond");
        JRadioButton diamondPos = new JRadioButton("Positive");
        diamondPos.setSelected(true);
        JRadioButton diamondNeg = new JRadioButton("Negative");
        diamondNeg.setSelected(true);
        ButtonGroup diamondGroup = new ButtonGroup();
        diamondGroup.add(diamondPos);
        diamondGroup.add(diamondNeg);
        JButton jbCalculate = new JButton ("Calculate");
        dialPanel.add(jlSpade);
        dialPanel.add(spadePos);
        dialPanel.add(spadeNeg);
        dialPanel.add(jlHeart);
        dialPanel.add(heartPos);
        dialPanel.add(heartNeg);
        dialPanel.add(jlClub);
        dialPanel.add(clubPos);
        dialPanel.add(clubNeg);
        dialPanel.add(jlDiamond);
        dialPanel.add(diamondPos);
        dialPanel.add(diamondNeg);
        dialPanel.add(jbCalculate);
        if (spadePos.isSelected())
        {
            bSpade = true;
        }
        else
        {
            bSpade = false;
        }
        if (heartPos.isSelected())
        {
            bHeart = true;
        }
        else
        {
            bHeart = false;
        }
        if (clubPos.isSelected())
        {
            bClub = true;
        }
        else
        {
            bClub = false;
        }
        if (diamondPos.isSelected())
        {
            bDiamond = true;
        }
        else
        {
            bDiamond = false;
        }
        jbCalculate.addActionListener(ae -> {
            results();
        });
    }
    /**
     * Creates the JSplitPane between the CenterPanel and the PlayerPanel
     * and the JSplitPane between the original split and the dialPanel
     */
    private void createSplit() {
        splitPaneHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitBottom = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        createCenterPanel();
        createPlayerPanel();
        createDialPanel();
        splitPaneHorizontal.setTopComponent(CenterPanel);
        splitPaneHorizontal.setBottomComponent(PlayerPanel);
        splitBottom.setTopComponent(splitPaneHorizontal);
        splitBottom.setBottomComponent(dialPanel);
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
        JOptionPane.showMessageDialog(null, "The goal of the game is to get the closest to 31. Whichever player is closer wins. Click one of the center cards to add it to your hand.");
    }

    /**
     * Saves the state of the game to a file.
     */
    private void saveGame() {

        try (FileOutputStream filestream = new FileOutputStream("BlackJack.ser");
                ObjectOutputStream os = new ObjectOutputStream(filestream);) {
            os.writeObject(deck);
            os.writeObject(AI);
            os.writeObject(player);
            os.writeObject(centerCard1);
            os.writeObject(centerCard2);
            os.writeObject(centerCard3);
            os.writeInt(iTurn);
            os.writeBoolean(bSpade);
            os.writeBoolean(bHeart);
            os.writeBoolean(bClub);
            os.writeBoolean(bDiamond);
        } catch (IOException e) {
            e.printStackTrace();
        }

    } // end method

    /**
     * Loads the game state from a file.
     */
    private void loadGame() {
        remove(player);
        remove(AI);
        remove(deck);
        remove(centerCard1);
        remove(centerCard2);
        remove(centerCard3);
        remove(iTurn);
        System.out.println("removed player/AI");
        try (FileInputStream filestream = new FileInputStream("ThirtyOne.ser");
                ObjectInputStream os = new ObjectInputStream(filestream);) {
            deck = (Deck) os.readObject();
            AI = (Hand) os.readObject();
            player = (Hand) os.readObject();
            centerCard1 = (Card) os.readObject();
            centerCard2 = (Card) os.readObject();
            centerCard3 = (Card) os.readObject();
            iTurn = os.readInt();
            bSpade = os.readBoolean();
            if (bSpade) {
                spadePos.setSelected(true);
            } else {
                spadeNeg.setSelected(true);
            }
            bHeart = os.readBoolean();
            if (bHeart) {
                heartPos.setSelected(true);
            } else {
                heartNeg.setSelected(true);
            }
            bClub = os.readBoolean();
            if (bClub) {
                clubPos.setSelected(true);
            } else {
                clubNeg.setSelected(true);
            }
            bDiamond = os.readBoolean();
            if (bDiamond) {
                diamondPos.setSelected(true);
            } else {
                diamondNeg.setSelected(true);
            }
            add(AI);
            add(player);
            add(centerCard1);
            add(centerCard2);
            add(centerCard3);            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    } // end method
    /**
     * Calculates the totals for both players
     */
    private void results() {
        player31 = 0;
        Map<Rank, Integer> values = new HashMap<>();
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
        System.out.println(playerCard1.getRank());
        System.out.println(playerCard2.getRank());
        System.out.println(playerCard3.getRank());
        System.out.println(playerCard4.getRank());
        if (bSpade == true)
        {
            if (playerCard1.getSuit() == Suit.SPADE)
            {
                player31 += iPlayer1;
            }
            if (playerCard2.getSuit() == Suit.SPADE)
            {
                player31 += iPlayer2;
            }
            if (playerCard3.getSuit() == Suit.SPADE)
            {
                player31 += iPlayer3;
            }
            if (playerCard4.getSuit() == Suit.SPADE)
            {
                player31 += iPlayer4;
            }
        }
        else
        {
            if (playerCard1.getSuit() == Suit.SPADE)
            {
                player31 -= iPlayer1;
            }
            if (playerCard2.getSuit() == Suit.SPADE)
            {
                player31 -= iPlayer2;
            }
            if (playerCard3.getSuit() == Suit.SPADE)
            {
                player31 -= iPlayer3;
            }
            if (playerCard4.getSuit() == Suit.SPADE)
            {
                player31 -= iPlayer4;
            }
        }
        if (bHeart == true)
        {
            if (playerCard1.getSuit() == Suit.HEART)
            {
                player31 += iPlayer1;
            }
            if (playerCard2.getSuit() == Suit.HEART)
            {
                player31 += iPlayer2;
            }
            if (playerCard3.getSuit() == Suit.HEART)
            {
                player31 += iPlayer3;
            }
            if (playerCard4.getSuit() == Suit.HEART)
            {
                player31 += iPlayer4;
            }
        }
        else
        {
            if (playerCard1.getSuit() == Suit.HEART)
            {
                player31 -= iPlayer1;
            }
            if (playerCard2.getSuit() == Suit.HEART)
            {
                player31 -= iPlayer2;
            }
            if (playerCard3.getSuit() == Suit.HEART)
            {
                player31 -= iPlayer3;
            }
            if (playerCard4.getSuit() == Suit.HEART)
            {
                player31 -= iPlayer4;
            }
        }
        if (bClub == true)
        {
            if (playerCard1.getSuit() == Suit.CLUB)
            {
                player31 += iPlayer1;
            }
            if (playerCard2.getSuit() == Suit.CLUB)
            {
                player31 += iPlayer2;
            }
            if (playerCard3.getSuit() == Suit.CLUB)
            {
                player31 += iPlayer3;
            }
            if (playerCard4.getSuit() == Suit.CLUB)
            {
                player31 += iPlayer4;
            }
        }
        else
        {
            if (playerCard1.getSuit() == Suit.CLUB)
            {
                player31 -= iPlayer1;
            }
            if (playerCard2.getSuit() == Suit.CLUB)
            {
                player31 -= iPlayer2;
            }
            if (playerCard3.getSuit() == Suit.CLUB)
            {
                player31 -= iPlayer3;
            }
            if (playerCard4.getSuit() == Suit.CLUB)
            {
                player31 -= iPlayer4;
            }
        }
        if (bDiamond == true)
        {
            if (playerCard1.getSuit() == Suit.DIAMOND)
            {
                player31 += iPlayer1;
            }
            if (playerCard2.getSuit() == Suit.DIAMOND)
            {
                player31 += iPlayer2;
            }
            if (playerCard3.getSuit() == Suit.DIAMOND)
            {
                player31 += iPlayer3;
            }
            if (playerCard4.getSuit() == Suit.DIAMOND)
            {
                player31 += iPlayer4;
            }
        }
        else
        {
            if (playerCard1.getSuit() == Suit.DIAMOND)
            {
                player31 -= iPlayer1;
            }
            if (playerCard2.getSuit() == Suit.DIAMOND)
            {
                player31 -= iPlayer2;
            }
            if (playerCard3.getSuit() == Suit.DIAMOND)
            {
                player31 -= iPlayer3;
            }
            if (playerCard4.getSuit() == Suit.DIAMOND)
            {
                player31 -= iPlayer4;
            }
        }
        playerAbs = 31 - player31;
        playerAbs = Math.abs(playerAbs);
        aiAbs = 31 - ai31;
        aiAbs = Math.abs(aiAbs);
        if (playerAbs < aiAbs) {
                    playerScore += 100;
                    Object[] options = { "Yes", "No" };
                    int n = JOptionPane.showOptionDialog(frame,
                            ("You win " + player31 + " to " + ai31 + "!\nWould you like to play again?"),
                            "You Win!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                            options[1]);
                    if (n == JOptionPane.YES_OPTION) {
                        frame.dispose();
                        deck = new Deck();
                        deck.shuffle();
                        begin();
                    } else {
                        if (scores.isHighScore(playerScore)) {
                            String initials = "";
                            while (initials == null || initials.length() > INITIAL_LENGTH || initials.length() == 0) {
                                initials = JOptionPane.showInputDialog(this, "Enter Your Initials:\nMax of three characters",
                                        "New High Score", JOptionPane.INFORMATION_MESSAGE);
                            }
                            HighScore score = new HighScore(initials, (int) playerScore);
                            scores.add(score);
                        }
                        frame.dispose();
                    }
                } else if (playerAbs == aiAbs) {
                    playerScore += 50;
                    Object[] options = { "Yes", "No" };
                    int n = JOptionPane.showOptionDialog(frame,
                            ("You tied at " + player31 + " to " + ai31
                                    + "!\nWould you like to play again?"),
                            "You Tie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                            options[1]);
                    if (n == JOptionPane.YES_OPTION) {
                        frame.dispose();
                        deck = new Deck();
                        deck.shuffle();
                        begin();
                    } else {
                        if (scores.isHighScore(playerScore)) {
                            String initials = "";
                            while (initials == null || initials.length() > INITIAL_LENGTH || initials.length() == 0) {
                                initials = JOptionPane.showInputDialog(this, "Enter Your Initials:\nMax of three characters",
                                        "New High Score", JOptionPane.INFORMATION_MESSAGE);
                            }
                            HighScore score = new HighScore(initials, (int) playerScore);
                            scores.add(score);
                        }
                        frame.dispose();
                    }
                } else {
                    Object[] options = { "Yes", "No" };
                    int n = JOptionPane.showOptionDialog(frame,
                            ("You lose " + player31 + " to " + ai31 + "!\nWould you like to play again?"),
                            "You Lose", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                            options[1]);
                    if (n == JOptionPane.YES_OPTION) {
                        AI.removeAll();
                        player.removeAll();
                        deck = new Deck();
                        deck.shuffle();
                        begin();
                    } else {
                        if (scores.isHighScore(playerScore)) {
                            String initials = "";
                            while (initials == null || initials.length() > INITIAL_LENGTH || initials.length() == 0) {
                                initials = JOptionPane.showInputDialog(this, "Enter Your Initials:\nMax of three characters",
                                        "New High Score", JOptionPane.INFORMATION_MESSAGE);
                            }
                            HighScore score = new HighScore(initials, (int) playerScore);
                            scores.add(score);
                        }
                        frame.dispose();
                    }
                }
    }
    /**
     * Runs a test of BlackJack by itself.
     *
     * @param args
     *            input parameters, not used
     */
    public static void main(final String[] args) {
    } // end method
} // end class