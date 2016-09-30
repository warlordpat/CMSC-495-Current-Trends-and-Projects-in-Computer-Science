// File: Concentration.java
// Author: Christy Gilliland
// Date: Sep 28, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_91-b14
// Purpose: implements a graphical game of Concentration.
// Notes: Concentration uses a different size deck than other classes, and does not depends on rank.
//          Concentration builds and manages its own deck within this class.

package group3;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import javax.swing.*;

/**
 * Concentration implements a graphical game of Concentration.
 */
public class Concentration extends JFrame implements MouseListener, ActionListener, Game {
    /**
     * Generated Serial ID. For Serialization.
     */
    private static final long serialVersionUID = -8467046280350561242L;
    /**
     * The width of a card.
     */
    private static final int CARD_WIDTH = Card.CARD_WIDTH;
    /**
     * The height of a card.
     */
    private static final int CARD_HEIGHT = 144;
    /**
     * Label font
     */
    private final Font F = new Font("Arial", Font.PLAIN, 22);
    /**
     * The file location to save the high SCORES.
     */
    private final File SCORE_FILE;
    /**
     * Container for deck.
     */
    private final JPanel PANEL;
    /**
     * Container for score labels.
     */
    private final JPanel SCORE_PANEL;
    /**
     * Container to hold panel and SCORE_PANEL.
     */
    private final JPanel MAIN_PANEL;
    /**
     * Reference array for the cards dealt.
     */
    private final JLabel[] CARD_LABELS;
    /**
     * Used to control the delay before returning a card to face-down position.
     */
    private final Timer T;
    /**
     * The Concentration High SCORES
     */
    private final HighScores SCORES;
    /**
     * Possible values: 0, 1, 2. When = 2, checks for a match.
     */
    private int test;
    /**
     * The value of the first selected card of each match.
     */
    private int selectedCard;
    /**
     * The value of the second selected card of each match.
     */
    private int secondSelectedCard;
    /**
     * Used by MousePressed event to save the index of the selected card.
     */
    private int index;
    /**
     * The number of matches (out of 30) made so far.
     */
    private int matches;
    /**
     * The number of attempts. One attempt is defined as two flipped cards.
     */
    private int attempts;
    /**
     * The time the game was started. Used to display total time at the end of a
     * game.
     */
    private long timeStart;
    /**
     * The time the game was finished. Used to display total time at the end of
     * a game.
     */
    private long timeEnd;
    /**
     * Saves the elapsed time of a saved game so it can be added when game is
     * resumed.
     */
    private long elapsedTime = 0;
    /**
     * Remains true until first pair is flipped, then is assigned false. When
        firstCard = false, the SCORE_PANEL becomes visible.
     */
    private boolean firstCard;
    /**
     * Used by the loadGame method to determine if a card had already been
     * matched.
     */
    private Boolean[] visibleBooleanLabels;
    /**
     * The label to display # of attempts and final score.
     */
    private JLabel scoreLabel;
    /**
     * Temporary card object used to populate reference array.
     */
    private Card temp;
    /**
     * The current deck in play.
     */
    private Deck deck;


    /**
     * Constructs a new Concentration game.
     */
    public Concentration() {

        super("Concentration!");

        test = 0;
        selectedCard = 0;
        secondSelectedCard = 0;
        index = 0;
        matches = 0;
        attempts = 0;
        firstCard = true;
        deck = new Deck();
        scoreLabel = new JLabel();
        PANEL = new JPanel();
        MAIN_PANEL = new JPanel();
        SCORE_PANEL = new JPanel();
        CARD_LABELS = new JLabel[30];

        T = new Timer(2000, this);

        String path = System.getProperty("user.home");
        path += File.separator + "CGS";
        File customDir = new File(path);
        SCORE_FILE = new File(customDir, "HighLow.score");

        SCORES = loadOrCreateScores("Concentration");
        setVisible(false);
        createGUI();
    }

    /**
     * Creates a new game session.
     */
    final void newGame() {

        deck.ConcentrationShuffle();
        createReferenceArray();

        if (MainCGS.DEBUGGING) {
            System.out.println("Creating new Game");
        }

        if (MainCGS.DEBUGGING) {
            System.out.println("Concentration deck after shuffle: " + deck.concentrationCards);
        }

    }

    /**
     * Starts the game play.
     */
    public final void begin() {

        newGame();
        setVisible(true);

    }

    /**
     * Concentration main
     * 
     * @param args
     *            input parameters, not used
     * 
     */
    public static void main(String[] args) {

        Concentration concentration = new Concentration();
        concentration.begin();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        T.start();
        T.stop();

    }

    /**
     * Saves the value of the selected card. If it is the second selected card,
     * compares to see if it's a match. If it is, the cards are disabled. If
     * they do not match, the cards are again placed facedown.
     */
    public void checkForMatch() {

        if (firstCard == true) {

            timeStart = System.currentTimeMillis();
            firstCard = false;

        }
        if (test == 0) {

            selectedCard = index;

        } else {

            secondSelectedCard = index;

        }

        test += 1;
        if (test == 2) {

            attempts += 1; // Every time 2 cards are selected, attempts is
                           // increased by 1 for scoring purposes.
            scoreLabel.setText("Attempts: " + attempts);

            if (deck.concentrationDeal(secondSelectedCard).toString() == null
                    ? deck.concentrationDeal(selectedCard).toString() == null
                    : deck.concentrationDeal(secondSelectedCard).toString()
                            .equals(deck.concentrationDeal(selectedCard).toString())) {

                if (selectedCard != secondSelectedCard) { // checks to make sure
                                                          // the user didn't
                                                          // click the same card
                                                          // twice.

                    CARD_LABELS[selectedCard].setEnabled(false);
                    CARD_LABELS[secondSelectedCard].setEnabled(false);
                    visibleBooleanLabels[selectedCard] = false;
                    visibleBooleanLabels[secondSelectedCard] = false;
                    matches += 1;

                } else {

                    temp = deck.concentrationDeal(selectedCard);
                    CARD_LABELS[selectedCard].setIcon(new ImageIcon(temp.getBack()));

                    temp = deck.concentrationDeal(secondSelectedCard);
                    CARD_LABELS[secondSelectedCard].setIcon(new ImageIcon(temp.getBack()));

                }

                test = 0;

            } else {
                // use lambda expression to create runnable to invoke later
                SwingUtilities.invokeLater(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                    temp = deck.concentrationDeal(selectedCard);
                    CARD_LABELS[selectedCard].setIcon(new ImageIcon(temp.getBack()));

                    temp = deck.concentrationDeal(secondSelectedCard);
                    CARD_LABELS[secondSelectedCard].setIcon(new ImageIcon(temp.getBack()));

                    test = 0;
                });
            }
        }

        if (matches == 15) { // 15 matches = end of game

            timeEnd = System.currentTimeMillis();
            gameWon();

        }
    }

    /**
     * Displays final score and time. Checks if new score is a high score.
     */
    public void gameWon() {

        scoreLabel.setText("Congratulations!  You finished this game in "
                + (((timeEnd - timeStart) + elapsedTime) / 1000) + " seconds and " + attempts + " attempts!");

        if (SCORES.isHighScore(attempts)) {
            String initials = "";
            while (initials == null || initials.length() > INITIAL_LENGTH || initials.length() == 0) {
                initials = JOptionPane.showInputDialog(this, "Enter Your Initials:\nMax of three characters",
                        "New High Score", JOptionPane.INFORMATION_MESSAGE);
            }
            HighScore score = new HighScore(initials, attempts);
            SCORES.add(score);
        }
    }

    /**
     * Stores the value for each card in an array based on their position on the
     * board. Recreates the array for new games, but not for loaded games.
     */
    public void createReferenceArray() {

        visibleBooleanLabels = new Boolean[30];

        if (MainCGS.DEBUGGING) {

            System.out.println("Components on panel before new game: " + PANEL.getComponentCount());

        }
        scoreLabel.setText(null);
        SCORE_PANEL.removeAll();
        SCORE_PANEL.add(scoreLabel);
        PANEL.removeAll();

        for (int i = 0; i < 30; i++) {

            JLabel cardLabel = new JLabel();

            temp = deck.concentrationDeal(i);
            temp.setSize(CARD_WIDTH, CARD_HEIGHT);
            cardLabel.setIcon(new ImageIcon(temp.getBack()));
            cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
            cardLabel.setVerticalAlignment(SwingConstants.CENTER);
            cardLabel.addMouseListener(this);
            CARD_LABELS[i] = cardLabel;
            visibleBooleanLabels[i] = true;
            PANEL.add(CARD_LABELS[i]);

        }

        SCORE_PANEL.revalidate();
        SCORE_PANEL.repaint();
        PANEL.revalidate();
        PANEL.repaint();

        if (MainCGS.DEBUGGING) {
            System.out.println("Components on panel after new game: " + PANEL.getComponentCount());
        }
    }

    /**
     * Creates the primary GUI for Concentration.
     */
    public final void createGUI() {

        MAIN_PANEL.setLayout(new BoxLayout(MAIN_PANEL, BoxLayout.Y_AXIS));
        PANEL.setLayout(new GridLayout(5, 6, 0, 0));
        PANEL.setPreferredSize(new Dimension(800, 700));
        SCORE_PANEL.setPreferredSize(new Dimension(800, 35));

        scoreLabel.setFont(F);
        setSize(900, 900);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                // write out high SCORES here
                saveHighScores(SCORE_FILE, SCORES);
                if (MainCGS.DEBUGGING) {
                    System.out.println("Frame is closing");
                }
            }
        });

        createMenu(this);
        MAIN_PANEL.add(SCORE_PANEL);
        MAIN_PANEL.add(PANEL);        
        add(MAIN_PANEL);
        pack();

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
     * Resets fields for a new game.
     */
    private void reset() {

        firstCard = true;

        test = 0;
        selectedCard = 0;
        secondSelectedCard = 0;
        index = 0;
        matches = 0;
        attempts = 0;

        JOptionPane.showMessageDialog(MAIN_PANEL, "A new game has been started.", "Concentration",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows the high SCORES in a dialog box.
     */
    private void highScores() {
        JOptionPane.showMessageDialog(this, SCORES);
    }

    /**
     * Saves the state of the game to a file. (non-Javadoc)
     * 
     * @see group3.Game#saveGame()
     */
    @Override
    public void saveGame() {

        elapsedTime = (timeEnd - timeStart);

        try (FileOutputStream filestream = new FileOutputStream("Concentration.ser");
                ObjectOutputStream os = new ObjectOutputStream(filestream);) {
            os.writeObject(deck);
            os.writeObject(visibleBooleanLabels);
            os.writeInt(test);
            os.writeInt(selectedCard);
            os.writeInt(secondSelectedCard);
            os.writeInt(index);
            os.writeInt(matches);
            os.writeInt(attempts);
            os.writeLong(elapsedTime);
            os.writeBoolean(firstCard);
            os.writeObject(scoreLabel);
            os.writeObject(temp);

            JOptionPane.showMessageDialog(MAIN_PANEL, "Your game has been saved.", "Concentration",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    } // end method

    /**
     * Loads the game state from a file. (non-Javadoc)
     * 
     * @see group3.Game#loadGame()
     */
    @Override
    public void loadGame() {

        scoreLabel.setText(null);
        SCORE_PANEL.removeAll();
        PANEL.removeAll();
        MAIN_PANEL.removeAll();
        revalidate();
        repaint();

        try (FileInputStream filestream = new FileInputStream("Concentration.ser");
                ObjectInputStream os = new ObjectInputStream(filestream);) {

            deck = (Deck) os.readObject();
            visibleBooleanLabels = (Boolean[]) os.readObject();
            test = (int) os.readInt();
            selectedCard = (int) os.readInt();
            secondSelectedCard = (int) os.readInt();
            index = (int) os.readInt();
            matches = (int) os.readInt();
            attempts = (int) os.readInt();
            elapsedTime = (long) os.readLong();
            firstCard = os.readBoolean();
            scoreLabel = (JLabel) os.readObject();
            temp = (Card) os.readObject();
            os.close();
            // Rebuild the reference array from saved deck:
            for (int i = 0; i < 30; i++) {
                JLabel cardLabel = new JLabel();

                temp = deck.concentrationDeal(i);
                temp.setSize(CARD_WIDTH, CARD_HEIGHT);
                if (visibleBooleanLabels[i] == false) {

                    cardLabel.setIcon(new ImageIcon(temp.getFront()));
                    cardLabel.setEnabled(false);

                } else {

                    cardLabel.setIcon(new ImageIcon(temp.getBack()));

                }

                cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
                cardLabel.setVerticalAlignment(SwingConstants.CENTER);
                cardLabel.addMouseListener(this);
                CARD_LABELS[i] = cardLabel;
                PANEL.add(CARD_LABELS[i]);
            }

            SCORE_PANEL.add(scoreLabel);
            SCORE_PANEL.revalidate();
            SCORE_PANEL.repaint();
            PANEL.revalidate();
            PANEL.repaint();
            MAIN_PANEL.add(SCORE_PANEL);
            MAIN_PANEL.add(PANEL);
            MAIN_PANEL.revalidate();
            MAIN_PANEL.repaint();
            add(MAIN_PANEL);

            JOptionPane.showMessageDialog(MAIN_PANEL, "Your game has been loaded.", "Concentration",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    } // end method

    /**
     * Shows the game play directions to the user.
     */
    private void directions() {
        JOptionPane.showMessageDialog(MAIN_PANEL,
                "Concentration is played with 30 cards (15 pairs). To play, click on any two cards."
                        + "  If the two cards match, they will be highlighted in gray.  If they do not, they will flip back over. "
                        + "  Continue until you have matched all 15 pairs."
                        + "  See if you can beat your high score by matching all pairs in the least number of moves!",
                "Concentration", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (MainCGS.DEBUGGING) {

            System.out.println("Who initiated the MouseEvent: " + e.getComponent());

        }

        index = Arrays.asList(CARD_LABELS).indexOf(e.getSource());
        temp = deck.concentrationDeal(index);
        CARD_LABELS[index].setIcon(new ImageIcon(temp.getFront()));
        T.start();
        T.stop();
        checkForMatch();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

}
