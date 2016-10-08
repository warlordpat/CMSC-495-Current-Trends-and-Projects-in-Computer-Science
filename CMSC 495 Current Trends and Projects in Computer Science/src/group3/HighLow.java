// File: HighLow.java
// Author: Patrick Smith
// Author: Christy Gilliland
// Date: Sep 5, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: Implements the High Low guessing game.

package group3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Implements the High Low guessing game.
 *
 * @author Patrick Smith
 * @author thegi
 * @version 1.0
 * @since Sep 5, 2016
 */
public class HighLow extends JFrame implements Game {
    /**
     * Background color that is light and pleasant.
     **/
    private static final Color GAME_TABLE_COLOR = new Color(215, 255, 215);
    /**
     * The space between the rows and columns in the grid.
     */
    private static final int GAPS = 8;
    /**
     * The number of columns in the grid.
     */
    private static final int COLS = 4;
    /**
     * Default generated serial number ID.
     */
    private static final long serialVersionUID = 8210919514573153701L;
    /**
     * The minimum screen height.
     */
    private static final int SCREEN_Y = 600;
    /**
     * The minimum screen width.
     */
    private static final int SCREEN_X = 800;
    /**
     * Font size of the smaller text.
     */
    private static final int SMALL_FONT_SIZE = 24;
    /**
     * Font size of the normal text.
     */
    private static final int NORMAL_FONT_SIZE = 29;
    /**
     * Font size of the larger text.
     */
    private static final int LARGE_FONT_SIZE = 34;
    /**
     * The game score. A point is awarded for each correct guess.
     */
    private int score;
    /**
     * The current Card on the discard pile.
     */
    private Card upCard;
    /**
     * The Hand that holds the discard pile.
     */
    private Hand discard;
    /**
     * Shows the current score.
     */
    private JLabel scoreCtLabel;
    /**
     * The current deck in play.
     */
    private Deck deck;
    /**
     * The JPanel that holds the currently displayed Card.
     */
    private JPanel cardPnl;
    /**
     * The HighLow high scores.
     */
    private HighScores scores;
    /**
     * True, if the game is over.
     */
    private boolean gameOver;
    /**
     * The file location to save the game.
     */
    private File saveFile;
    /**
     * The file location to save the high scores.
     */
    private File scoreFile;

    /**
     * Creates a new HighLow game window and game.
     */
    public HighLow() {
        super("High or Low!");
        scoreCtLabel = new JLabel("", JLabel.LEFT);
        score = 0;
        gameOver = false;
        String path = System.getProperty("user.home");
        path += File.separator + "CGS";
        File customDir = new File(path);
        scoreFile = new File(customDir, "HighLow.score");
        saveFile = new File(customDir, "HighLow.ser");
        deck = new Deck();
        deck.setMinimumSize(deck.getPreferredSize());
        discard = new Hand();
        scores = loadOrCreateScores("HighLow");
        upCard = dealFlipped();
        discard.addCard(upCard);
        if (MainCGS.DEBUGGING) {
            System.out.println(deck);
            System.out.println(upCard);
        }
        createGUI();
    }

    /**
     * Starts the gameplay.
     */
    public final void begin() {
        newGame();
    }

    /**
     * Creates a new game session.
     */
    private void newGame() {
        gameOver = false;
        deck = new Deck();
        deck.shuffle();
        setVisible(true);
    }

    /**
     * Runs a test of HighLow by itself.
     *
     * @param args
     *            input parameters, not used
     */
    public static void main(final String[] args) {
        HighLow test = new HighLow();
        test.begin();
    }

    /**
     * Deals a Card and flips it over.
     *
     * @return The face up card
     */
    private Card dealFlipped() {
        Card temp = deck.deal();
        temp.flip();
        return temp;
    }

    /**
     * Creates a new initial BlackJack GUI.
     */
    private void createGUI() {
        setPreferredSize(new Dimension(SCREEN_X, SCREEN_Y));
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createMenu(this);

        JLabel highLabel = new JLabel("HIGHER", JLabel.CENTER);
        highLabel.setFont(new Font("Courier Bold", Font.BOLD, LARGE_FONT_SIZE));

        JLabel lowLabel = new JLabel("LOWER", JLabel.CENTER);
        lowLabel.setFont(new Font("Courier Bold", Font.BOLD, SMALL_FONT_SIZE));

        JLabel orLabel = new JLabel("OR", JLabel.CENTER);
        orLabel.setFont(new Font("Courier Bold", Font.BOLD, NORMAL_FONT_SIZE));

        JLabel blankLabel = new JLabel(" ? ", JLabel.CENTER);
        blankLabel
            .setFont(new Font("Courier Bold", Font.BOLD, NORMAL_FONT_SIZE));
        JLabel blankLabel1 = new JLabel(" ");

        JLabel scoreLabel = new JLabel("SCORE: ", JLabel.RIGHT);
        scoreLabel
            .setFont(new Font("Courier Bold", Font.BOLD, SMALL_FONT_SIZE));

        JButton highButton = new JButton("Higher!");
        highButton
            .setFont(new Font("Courier Bold", Font.BOLD, SMALL_FONT_SIZE));
        highButton.addActionListener(al -> {
            if (checkGameOverConditions()) {
                return;
            }
            Rank previousRank = dealNextCard();
            if (upCard.getRank().ordinal() > previousRank.ordinal()) {
                score += 1;
                scoreCtLabel.setText(String.valueOf(score));
            }
            if (MainCGS.DEBUGGING) {
                System.out.println("finished Higher button processing");
            }
        });

        JButton lowButton = new JButton("Lower!");
        lowButton.setFont(new Font("Courier Bold", Font.BOLD, SMALL_FONT_SIZE));
        lowButton.addActionListener(al -> {
            if (checkGameOverConditions()) {
                return;
            }
            Rank previousRank = dealNextCard();
            if (upCard.getRank().ordinal() < previousRank.ordinal()) {
                score += 1;
                scoreCtLabel.setText(String.valueOf(score));
            }
            if (MainCGS.DEBUGGING) {
                System.out.println("finished Lower button processing");
            }
        });

        scoreCtLabel
            .setFont(new Font("Courier Bold", Font.BOLD, SMALL_FONT_SIZE));
        scoreCtLabel.setText(String.valueOf(score));

        JPanel panel;
        panel = new JPanel(new GridLayout(0, COLS, GAPS, GAPS));
        panel.setBackground(GAME_TABLE_COLOR);
        panel.add(highLabel);
        panel.add(orLabel);
        panel.add(lowLabel);
        panel.add(blankLabel);
        panel.add(lowButton);

        JPanel deckPnl = new JPanel();
        deckPnl.setBackground(GAME_TABLE_COLOR);
        deckPnl.setLayout(new GridBagLayout());
        deckPnl.add(deck);

        cardPnl = new JPanel();
        cardPnl.setBackground(GAME_TABLE_COLOR);
        cardPnl.setLayout(new GridBagLayout());
        cardPnl.add(upCard);

        panel.add(deckPnl);
        panel.add(cardPnl);
        panel.add(highButton);
        panel.add(blankLabel1);
        panel.add(scoreLabel);
        panel.add(scoreCtLabel);

        getContentPane().add(panel);
        pack();
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(final WindowEvent e) {
                // write out high scores here
                saveHighScores(scoreFile, scores);
                if (MainCGS.DEBUGGING) {
                    System.out.println("Frame is closing");
                }
            }
        });
    }

    /**
     * Checks for win/loss conditions and displays the appropriate messages.
     *
     * @return True, if the game is over
     */
    private boolean checkGameOverConditions() {
        if (gameOver) {
            return true;
        }
        if (deck.deckSize() == 0) {
            gameOver = true;
            if (scores.isHighScore(score)) {
                String initials = getInitials(this);
                HighScore highScore = new HighScore(initials, score);
                scores.add(highScore);
            }
            // display game over
            return true;
        }
        return false;
    } // end method

    /**
     * Deals the next Card to the discard spot.
     *
     * @return the Rank of the previous Card
     */
    private Rank dealNextCard() {
        Rank previousRank = upCard.getRank();
        upCard = dealFlipped();
        discard.addCard(upCard);

        cardPnl.removeAll();
        cardPnl.add(upCard);
        cardPnl.revalidate();
        cardPnl.repaint();
        if (MainCGS.DEBUGGING) {
            System.out.println(
                "Card: " + upCard + " remaining deck: " + deck.deckSize());
        }
        return previousRank;
    }

    /**
     * Creates the menu and adds it to a menu.
     *
     * @param frame
     *            the frame the menu is added to
     */
    private void createMenu(final JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);
        JMenuItem menuItem = new JMenuItem("New Game");
        menu.add(menuItem);
        menuItem.addActionListener(ae -> {
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

        JMenuItem menuItem1 = new JMenuItem("High Scores");
        menu.add(menuItem1);
        menuItem1.addActionListener(ae -> {
            highScores();
        });

        JMenuItem mntmReturnToMain = new JMenuItem("Return to main menu");
        menu.add(mntmReturnToMain);
        mntmReturnToMain.addActionListener(ae -> {
            dispose();
        });

        frame.setJMenuBar(menuBar);
    }

    /**
     * Shows the high scores in a dialog box.
     */
    private void highScores() {
        JOptionPane.showMessageDialog(this, scores);
    }

    /**
     * Resets the game state for a new deck.
     */
    private void reset() {
        score = 0;
        scoreCtLabel.setText(String.valueOf(score));
        discard = new Hand();
        dealNextCard();
    }

    /*
     * (non-Javadoc)
     *
     * @see group3.Game#loadGame()
     */
    @Override
    public final void loadGame() {
        if (MainCGS.DEBUGGING) {
            System.out.println("Loading Game");
        }
        try (FileInputStream filestream = new FileInputStream(saveFile);
                ObjectInputStream os = new ObjectInputStream(filestream);) {
            upCard = (Card) os.readObject();
            discard = (Hand) os.readObject();
            deck = (Deck) os.readObject();
            gameOver = os.readBoolean();
            score = os.readInt();

            if (MainCGS.DEBUGGING) {
                System.out.println("loaded: " + "\n gameOver " + gameOver
                        + "\n score   " + score + "\n discard pile " + discard
                        + "\n Deck " + deck + "\n displayed card: " + upCard);
            }
            cardPnl.removeAll();
            cardPnl.add(upCard);
            cardPnl.revalidate();
            cardPnl.repaint();
            scoreCtLabel.setText(String.valueOf(score));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (MainCGS.DEBUGGING) {
            System.out.println("Game Loaded");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see group3.Game#saveGame()
     */
    @Override
    public final void saveGame() {
        if (MainCGS.DEBUGGING) {
            System.out.println("Saving Game");
        }
        try (FileOutputStream filestream = new FileOutputStream(saveFile);
                ObjectOutputStream os = new ObjectOutputStream(filestream);) {
            os.writeObject(upCard);
            os.writeObject(discard);
            os.writeObject(deck);
            os.writeBoolean(gameOver);
            os.writeInt(score);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (MainCGS.DEBUGGING) {
            System.out
                .println("Game Saved" + "\nsaved: " + "\n gameOver " + gameOver
                        + "\n score   " + score + "\n discard pile " + discard
                        + "\n Deck " + deck + "\n displayed card: " + upCard);
        }
    }
} // end class
