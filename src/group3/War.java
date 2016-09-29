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
import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
public class War implements Game {
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
     * If true, deals cards all but one to one player for end game group3.DEBUGGING.
     */
    private static final boolean END_GAME_DEBUG = false;
    /**
     * Keeps track of how many battles the game takes.
     */
    private int playCount = 0;
    /**
     * How many times you went to war.
     */
    private int warIndex = 0;
    /**
     * The War high scores.
     */
    private HighScores scores;
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
     * The remaining cards in the computer hand.
     */
    private JLabel handSizeAI;
    /**
     * The remaining cards in the player hand.
     */
    private JLabel handSizePlayer;
    /**
     * True, if the game is over.
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
        blankLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        gameOver = false;
        scores = loadOrCreateScores("War");
    } // end constructor

    /**
     * Creates a new deck, shuffles it, and deals all the cards to the player
     * and the computer. Builds the GUI and starts the game.
     */
    public final void begin() {
        newGame();
        gui();
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
        JMenuBar menuBar = createMenuBar();

        frame.setJMenuBar(menuBar);
        JButton jbOne = new JButton("Battle");
        jbOne.addActionListener(ae -> {
            if (warFrame.isVisible()) {
                warFrame.setVisible(false);
            }
            if (!gameOver) {
                playCount++;
                battle();

            }
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
     * Creates the JMenuBar and adds the menu items to it.
     *
     * @return the JMenuBar
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);

        JMenuItem mntmNewGame = new JMenuItem("New Game");
        menu.add(mntmNewGame);
        mntmNewGame.addActionListener(ae -> {
            newGame();
            reset();
        });

        JMenuItem mntmSaveGame = new JMenuItem("Save Game");
        menu.add(mntmSaveGame);
        mntmSaveGame.addActionListener(ae -> {
            saveGame();
        });

        JMenuItem mntmLoadGame = new JMenuItem("Load Game");
        menu.add(mntmLoadGame);
        mntmLoadGame.addActionListener(ae -> {
            loadGame();
        });

        JMenuItem mntmHowToPlay = new JMenuItem("How to Play");
        menu.add(mntmHowToPlay);
        mntmHowToPlay.addActionListener(ae -> {
            directions();
        });

        JMenuItem mntmHighScores = new JMenuItem("High Scores");
        menu.add(mntmHighScores);
        mntmHighScores.addActionListener(ae -> {
            highScores();
        });

        JMenuItem mntmReturnToMain = new JMenuItem("Return to main menu");
        menu.add(mntmReturnToMain);
        mntmReturnToMain.addActionListener(ae -> {
            frame.dispose();
        });
        return menuBar;
    }

    /**
     * Resets the game state for a new game.
     */
    private void reset() {
        playerDiscardLabel.setIcon(null);
        aiDiscardLabel.setIcon(null);
        blankLabel1.setText(null);
        if (warFrame.isVisible()) {
            warFrame.setVisible(false);
        }
        updateHandSizeDisplay();
    }

    /**
     * Creates a new game session.
     */
    private void newGame() {
        Deck deck = new Deck();
        deck.shuffle();
        ai = new Hand();
        player = new Hand();

        if (END_GAME_DEBUG) {
            // deal all cards but one to one player
            boolean winner = true;
            boolean war = true;
            boolean warThree = true;
            if (winner) {
                Card temp;
                while (deck.deckSize() > 1) {
                    temp = deck.deal();
                    System.out.println("Dealing");
                    player.addCard(temp);
                } // end while
                temp = deck.deal();
                ai.addCard(temp);
                if (war) {
                    Card same = ai.getCard(0);
                    player.removeCard();
                    player.addCard(0, new Card(same.getRank(), same.getSuit()));
                    player.addCard(1, new Card(Rank.ACE, same.getSuit()));
                    if (warThree) {
                        ai.addCard(new Card(Rank.ACE, Suit.CLUB));
//                        ai.addCard(new Card(Rank.TWO, Suit.CLUB));
//                        ai.addCard(new Card(Rank.KING, Suit.CLUB));
                    }
                }
            } else {
                Card temp;
                while (deck.deckSize() > 1) {
                    temp = deck.deal();
                    System.out.println("Dealing");
                    ai.addCard(temp);
                } // end while
                temp = deck.deal();
                player.addCard(temp);
                if (war) {
                    Card same = player.getCard(0);
                    ai.removeCard();
                    ai.addCard(0, new Card(same.getRank(), same.getSuit()));
                }
            }
        } else {
            // deal cards to the player and computer
            while (deck.deckSize() > 0) {
                Card temp = deck.deal();
                if (deck.deckSize() % 2 == 0) {
                    player.addCard(temp);
                } else {
                    ai.addCard(temp);
                } // end else
            } // end while
        } // end else
        playCount = 0;
        gameOver = false;
    }

    /**
     * Draws and compares cards from each player. If the cards are the same
     * rank, starts a war. Returns the cards to the hand of the player with the
     * higher ranking card.
     */
    public final void battle() {
        Card playerCard = player.removeCard();
        Card aiCard = ai.removeCard();
        playerDiscardLabel.setIcon(new ImageIcon(playerCard.getFront()));
        aiDiscardLabel.setIcon(new ImageIcon(aiCard.getFront()));
        if (playerCard.getRank().compareTo(aiCard.getRank()) > 0) {
            // player card has a higher rank than ai card
            player.addCard(playerCard);
            player.addCard(aiCard);
            blankLabel1.setText("You won this fight!");
        } else if (playerCard.getRank().compareTo(aiCard.getRank()) < 0) {
            // ai card has a higher rank than player card
            ai.addCard(playerCard);
            ai.addCard(aiCard);
            blankLabel1.setText("You lost this fight!");
        } else {
            // equal, go to war!
            // check to see if there are any cards left for a war
            if (player.handSize() == 0) {
                ai.addCard(playerCard);
                ai.addCard(aiCard);
            } else if (ai.handSize() == 0) {
                player.addCard(playerCard);
                player.addCard(aiCard);
            } else {
                // there are cards, do a war
                if (war()) {
                    player.addCard(playerCard);
                    player.addCard(aiCard);
                } else {
                    ai.addCard(playerCard);
                    ai.addCard(aiCard);
                }
            }
        } // end else
        checkGameOverConditions();
        updateHandSizeDisplay();
    } // end method

    /**
     * Checks for win/loss conditions and displays the appropriate messages.
     */
    private void checkGameOverConditions() {
        if (ai.handSize() == 0) {
            blankLabel1.setText("<html>Game Over" + "<br> You win!</html>");
            gameOver = true;
            if (scores.isHighScore(playCount)) {
                String initials = getInitials(frame);
                HighScore score = new HighScore(initials, playCount);
                scores.add(score);
            }
        } else if (player.handSize() == 0) {
            blankLabel1.setText("<html>Game Over" + "<br>You lose!</html>");
            gameOver = true;
        } else {
            // do we need anything else?
        }
    } // end method

    /**
     * Starts a war when the player and the AI have the same rank card.
     *
     * @return True, if the player won the war
     */
    private boolean war() {
        if (MainCGS.DEBUGGING) {
            System.out.println("Starting hands: player: " + player.handSize() + " ai: " + ai.handSize());
        }
        blankLabel1.setText("WAR!");
        blankLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        boolean whoWon = warFrame.setupGUI(player, ai, 1);
        if (MainCGS.DEBUGGING) {
            System.out.println("Ending hands: player: " + player.handSize() + " ai: " + ai.handSize());
        }
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
                "The player with the higher card wins. " + "\nIn the event of a tie you go to War.\nEach player offers "
                        + "\nthe top 3 cards of their decks, and " + "\nthen the player with the highest "
                        + "\n4th card wins the whole pile."
                        + "\n\nIf a player does not have enough cards for a War, they lose.\nAces are low.");
    } // end method

    /**
     * Shows the high scores in a dialog box.
     */
    private void highScores() {
        JOptionPane.showMessageDialog(frame, scores);
    }

    /**
     * Saves the state of the game to a file.
     */
    public final void saveGame() {
        if (MainCGS.DEBUGGING) {
            System.out.println("Saving Game");
        }
        try (FileOutputStream filestream = new FileOutputStream("War.ser");
                ObjectOutputStream os = new ObjectOutputStream(filestream);) {
            os.writeObject(blankLabel1);
            os.writeObject(ai);
            os.writeObject(player);
            os.writeObject(aiDiscardLabel);
            os.writeObject(playerDiscardLabel);
            os.writeObject(handSizeAI);
            os.writeObject(handSizePlayer);
            os.writeBoolean(gameOver);
            os.writeInt(playCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (MainCGS.DEBUGGING) {
            String playerDisplayCard;
            String aiDisplayCard;
            if (blankLabel1.getText().startsWith("You won")) {
                // player won
                playerDisplayCard = player.getCards().get(player.getCards().size() - 2).toString();
                aiDisplayCard = player.getCards().get(player.getCards().size() - 1).toString();
            } else {
                playerDisplayCard = ai.getCards().get(ai.getCards().size() - 2).toString();
                aiDisplayCard = ai.getCards().get(ai.getCards().size() - 1).toString();
            }
            System.out.println("Game Saved" + "\nsaved: " + "\n gameOver " + gameOver + "\n playCount   " + playCount
                    + "\n Player hand " + player + "\n AI Hand " + ai + "\n Message: " + blankLabel1.getText()
                    + "\n Player cards: " + handSizePlayer.getText() + "\n AI Cards: " + handSizeAI.getText()
                    + "\n Display Cards:" + "\n  player: " + playerDisplayCard + "\n  AI: " + aiDisplayCard);
        }
    } // end method

    /**
     * Loads the game state from a file.
     */
    public final void loadGame() {
        // check if war output matters (it doesn't)
        if (MainCGS.DEBUGGING) {
            System.out.println("Loading Game");
        }
        gameBoard.remove(playerDiscardLabel);
        gameBoard.remove(blankLabel1);
        gameBoard.remove(aiDiscardLabel);

        JLayeredPane layeredPanePlayer = (JLayeredPane) handSizePlayer.getParent();
        layeredPanePlayer.remove(handSizePlayer);

        JLayeredPane layeredPaneAI = (JLayeredPane) handSizeAI.getParent();
        layeredPaneAI.remove(handSizeAI);

        if (MainCGS.DEBUGGING) {
            System.out.println("removed player/dealer");
        }
        frame.revalidate();
        frame.repaint();
        try (FileInputStream filestream = new FileInputStream("War.ser");
                ObjectInputStream os = new ObjectInputStream(filestream);) {
            blankLabel1 = (JLabel) os.readObject();
            ai = (Hand) os.readObject();
            player = (Hand) os.readObject();
            aiDiscardLabel = (JLabel) os.readObject();
            playerDiscardLabel = (JLabel) os.readObject();
            handSizeAI = (JLabel) os.readObject();
            handSizePlayer = (JLabel) os.readObject();
            gameOver = os.readBoolean();
            playCount = os.readInt();

            if (MainCGS.DEBUGGING) {
                String playerDisplayCard;
                String aiDisplayCard;
                if (blankLabel1.getText().startsWith("You won")) {
                    // player won
                    playerDisplayCard = player.getCards().get(player.getCards().size() - 2).toString();
                    aiDisplayCard = player.getCards().get(player.getCards().size() - 1).toString();
                } else {
                    playerDisplayCard = ai.getCards().get(ai.getCards().size() - 2).toString();
                    aiDisplayCard = ai.getCards().get(ai.getCards().size() - 1).toString();
                }

                System.out.println("loaded: " + "\n gameOver " + gameOver + "\n playCount   " + playCount
                        + "\n Player hand " + player + "\n AI Hand " + ai + "\n Message: " + blankLabel1.getText()
                        + "\n Player cards: " + handSizePlayer.getText() + "\n AI Cards: " + handSizeAI.getText()
                        + "\n Display Cards:" + "\n  player: " + playerDisplayCard + "\n  AI: " + aiDisplayCard);
            }
            gameBoard.add(playerDiscardLabel);
            gameBoard.add(blankLabel1);
            gameBoard.add(aiDiscardLabel);
            layeredPanePlayer.add(handSizePlayer, new Integer(1));
            layeredPaneAI.add(handSizeAI, new Integer(1));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (MainCGS.DEBUGGING) {
            System.out.println("Game Loaded");
        }
    } // end method
} // end class
