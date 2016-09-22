// File: War.java
// Author: Patrick Smith
// Date: Sep 14, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: implements a graphical game of War.

package group3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This class implements the GUI and game logic of the card game War.
 *
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 21, 2016
 */
public class War {
    /**
     * The number of columns on the game board.
     */
    private static final int BOARD_COLS = 3;
    /**
     * The width of a card.
     */
    private static final int CARD_WIDTH = 106;
    /**
     * The height of a card.
     */
    private static final int CARD_HEIGHT = 144;
    /**
     * The width of the remaining cards label.
     */
    private static final int REMAIN_WIDTH = 37;
    /**
     * The height of the remaining cards label.
     */
    private static final int REMAIN_HEIGHT = 33;
    /**
     * Size of remaining cards font.
     */
    private static final int REMAIN_FONT_SIZE = 30;
    /**
     * Keeps track of how many battles the game takes.
     */
    private int playCount = 0;
    /**
     * 
     */
    private int warIndex = 0;
    /**
     * The player's cards.
     */
    private Hand player;
    /**
     * The computer's cards.
     */
    private Hand ai;
    /**
     * The frame that hold the War GUI.
     */
    private JFrame frame;
    /**
     * Additional Frame to hold the Wars.
     */
    private WarFrame warFrame;
    /**
     * The panel that holds the game elements.
     */
    private JPanel panel;
    /**
     * Interior panel used to display the active player discard piles.
     */
    private JPanel gameBoard;
    /**
     * Label used to display the player discard pile.
     */
    private JLabel playerDiscardLabel;
    /**
     * Label used to display the computer discard pile.
     */
    private JLabel aiDiscardLabel;
    /**
     * Empty label at the center of the GUI. Used to space out the game GUI.
     */
    private JLabel blankLabel1;
    /**
     * 
     */
    private boolean bNew;
    /**
     * The remaining cards in the computer hand.
     */
    private JLabel handSizeAI;
    /**
     * The remaining cards in the player hand.
     */
    private JLabel handSizePlayer;
    /**
     * 
     */
    private boolean gameOver;

    /**
     * Constructs a new War object with appropriate graphics and initializes
     * member fields.
     */
    public War() {
        frame = new JFrame("War!");
        warFrame = new WarFrame("It's War!");
        panel = new JPanel(new BorderLayout());
        gameBoard = new JPanel(new GridLayout(0, BOARD_COLS, 2, 2));
        playerDiscardLabel = new JLabel();
        playerDiscardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        aiDiscardLabel = new JLabel();
        aiDiscardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        blankLabel1 = new JLabel("  ");
        gameOver = false;
    } // end constructor

    /**
     * Creates a new deck, shuffles it, and deals all the cards to the player
     * and the computer. Builds the GUI and starts the game.
     */
    public final void begin() {
        Deck deck = new Deck();
        deck.shuffle();
        ai = new Hand();
        player = new Hand();
        // deal cards to the player and computer
        while (deck.deckSize() > 0) {
            Card temp = deck.deal();
            if (deck.deckSize() % 2 == 0) {
                player.addCard(temp);
            } else {
                ai.addCard(temp);
            } // end else
        } // end if
        bNew = false;
        gui();
        playCount = 0;
    } // end method

    /**
     * Runs a test of the War class.
     *
     * @param args
     *            Not used
     */
    public static void main(final String[] args) {
        War test = new War();
        test.begin();
    } // end main

    /**
     * Builds the game GUI.
     */
    public final void gui() {
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);
        JMenuItem menuItem = new JMenuItem("New Game");
        JMenuItem menuItemD = new JMenuItem("How to Play");
        JMenuItem menuItem1 = new JMenuItem("High Scores");
        JMenuItem menuItem2 = new JMenuItem("Return to main menu");

        menu.add(menuItem);
        menu.add(menuItemD);
        menu.add(menuItem1);
        menu.add(menuItem2);

        frame.setJMenuBar(menuBar);
        JButton jbOne = new JButton("Battle");
        jbOne.addActionListener(ae -> {
            if (warFrame.isVisible()) {
                warFrame.setVisible(false);
            }
            if (!gameOver) {
                battle();
                playCount++;
            }
        });

        menuItemD.addActionListener(ae -> {
            directions();
        });
        menuItem2.addActionListener(ae -> {
            frame.dispose();
        });
        JLayeredPane layeredPanePlayer = new JLayeredPane();
        URL imageURL = this.getClass().getClassLoader().getResource("images/cardBack.png");
        ImageIcon cardBacks = new ImageIcon(imageURL);
        JLabel backLabel = new JLabel(cardBacks, JLabel.CENTER);
        backLabel.setSize(CARD_WIDTH, CARD_HEIGHT);
        layeredPanePlayer.setPreferredSize(backLabel.getPreferredSize());
        handSizePlayer = new JLabel(String.valueOf(player.handSize()));
        handSizePlayer.setFont(new Font("Tahoma", Font.PLAIN, REMAIN_FONT_SIZE));
        handSizePlayer.setHorizontalAlignment(SwingConstants.CENTER);
        handSizePlayer.setLocation(CARD_WIDTH - REMAIN_WIDTH, 0);
        handSizePlayer.setSize(REMAIN_WIDTH, REMAIN_HEIGHT);
        handSizePlayer.setBackground(Color.WHITE);
        layeredPanePlayer.add(handSizePlayer, new Integer(1));
        layeredPanePlayer.add(backLabel, new Integer(0));

        JLabel backLabel2 = new JLabel(cardBacks, JLabel.CENTER);
        JLayeredPane layeredPaneAI = new JLayeredPane();
        backLabel2.setSize(CARD_WIDTH, CARD_HEIGHT);
        layeredPaneAI.setPreferredSize(backLabel.getPreferredSize());
        handSizeAI = new JLabel(String.valueOf(ai.handSize()));
        handSizeAI.setFont(new Font("Tahoma", Font.PLAIN, REMAIN_FONT_SIZE));
        handSizeAI.setHorizontalAlignment(SwingConstants.CENTER);
        // handSizeAI.setLocation(0, 0);
        handSizeAI.setSize(REMAIN_WIDTH, REMAIN_HEIGHT);
        handSizeAI.setBackground(Color.WHITE);
        layeredPaneAI.add(handSizeAI, new Integer(1));
        layeredPaneAI.add(backLabel2, new Integer(0));

        JLabel topLabel = new JLabel("      Player Cards                                          "
                + "                                                                         Computer Cards   ");

        panel.add(topLabel, BorderLayout.NORTH);
        panel.add(layeredPanePlayer, BorderLayout.WEST);
        panel.add(layeredPaneAI, BorderLayout.EAST);
        gameBoard.add(playerDiscardLabel);
        gameBoard.add(blankLabel1);
        gameBoard.add(aiDiscardLabel);
        panel.add(gameBoard, BorderLayout.CENTER);
        panel.add(jbOne, BorderLayout.SOUTH);
        frame.getContentPane().add(panel);
        frame.pack();
    } // end method

    /**
     * Draws and compares cards from each player. If the cards are the same
     * rank, starts a war. Returns the cards to the hand of the player with the
     * higher ranking card.
     */
    public final void battle() {
        Card playerCard = player.removeCard();
        Card aiCard = ai.removeCard();
        playerDiscardLabel.setIcon(new ImageIcon(playerCard.front));
//        playerDiscardLabel.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        aiDiscardLabel.setIcon(new ImageIcon(aiCard.front));
        if (playerCard.getRank().compareTo(aiCard.getRank()) > 0) {
            // player card has a higher rank than ai card
            player.addCard(playerCard);
            player.addCard(aiCard);
            updateHandSizeDisplay();
            if (ai.handSize() == 0) {
                blankLabel1.setText("Game Over/nYou win!");
                gameOver = true;
            } else {
                blankLabel1.setText("You won this fight!");
            }
        } else if (playerCard.getRank().compareTo(aiCard.getRank()) < 0) {
            // ai card has a higher rank than player card
            ai.addCard(playerCard);
            ai.addCard(aiCard);
            updateHandSizeDisplay();
            if (player.handSize() == 0) {
                blankLabel1.setText("Game Over/nYou lose!");
                gameOver = true;
            } else {
                blankLabel1.setText("You lost this fight!");
            } // end else
        } else {
            // equal, go to war!
            // check to see if there are any cards left for a war
            if (player.handSize() == 0) {
                blankLabel1.setText("Game Over/nYou lose!");
                gameOver = true;
                ai.addCard(playerCard);
                ai.addCard(aiCard);
                updateHandSizeDisplay();
                return;
            } else if (ai.handSize() == 0) {
                blankLabel1.setText("Game Over/nYou win!");
                gameOver = true;
                player.addCard(playerCard);
                player.addCard(aiCard);
                updateHandSizeDisplay();
                return;
            }
            if (war()) {
                player.addCard(playerCard);
                player.addCard(aiCard);
            } else {
                ai.addCard(playerCard);
                ai.addCard(aiCard);
            }
            updateHandSizeDisplay();
        } // end else
    } // end method

    /**
     * Starts a war when the player and the AI have the same rank card.
     *
     * @return True, if the player won the war
     */
    private boolean war() {
        System.out.println("Starting hands: player: " + player.handSize() + " ai: " + ai.handSize());
        blankLabel1.setText("WAR!");
        boolean whoWon = warFrame.setupGUI(player, ai, 1);
        System.out.println("Ending hands: player: " + player.handSize() + " ai: " + ai.handSize());
        return whoWon;
    } // end method

    /**
     * Updates the players' hand sizes in the GUI.
     */
    private void updateHandSizeDisplay() {
        handSizePlayer.setText(Integer.toString(player.handSize()));
        handSizeAI.setText(Integer.toString(ai.handSize()));
    }

    /**
     * Shows the game play directions to the user.
     */
    public final void directions() {
        JOptionPane.showMessageDialog(null,
                "The player with the higher card wins. " + "\nIn the event of a tie, each player offers "
                        + "\nthe top 3 cards of their decks, and " + "\nthen the player with the highest "
                        + "\n4th card wins the whole pile.");
    } // end method
} // end class
