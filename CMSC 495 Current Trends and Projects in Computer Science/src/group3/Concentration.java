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
     * Possible values: 0, 1, 2. When = 2, checks for a match.
     */
    int test;
    /**
     * The value of the first selected card.
     */
    int selectedCard;
    /**
     * The value of the second selected card.
     */
    int secondSelectedCard;
    /**
     * Used by MousePressed event to save the index of the selected card.
     */
    int index;
    /**
     * The number of matches (out of 30) made so far.
     */
    int matches;
    /**
     * The number of attempts. One attempt is defined as two flipped cards.
     */
    int attempts;
    /**
     * The time the game was started. Used to display total time at the end of a
     * game.
     */
    long timeStart;
    /**
     * The time the game was finished. Used to display total time at the end of
     * a game.
     */
    long timeEnd;
    /**
     * Saves the elapsed time of a saved game so it can be added when game is
     * resumed.
     */
    long elapsedTime = 0;
    /**
     * Remains true until first pair is flipped, then is assigned false. When
     * firstCard = false, the scorePanel becomes visible.
     */
    boolean firstCard;
    /**
     * Container for deck.
     */
    JPanel panel;
    /**
     * Container for score labels.
     */
    JPanel scorePanel;
    /**
     * Container to hold panel and scorePanel.
     */
    JPanel mainPanel;
    /**
     * Reference array for the cards dealt.
     */
    JLabel[] cardLabels;
    /**
     * Used by the loadGame method to determine if a card had already been
     * matched.
     */
    Boolean[] visibleBooleanLabels;
    /**
     * The label to display # of attempts and final score.
     */
    JLabel scoreLabel;
    /**
     * Temporary card object used to populate reference array.
     */
    Card temp;
    /**
     * Used to control the delay before returning a card to face-down position.
     */
    Timer t;
    /**
     * The Concentration High scores
     */
    private HighScores scores;
    /**
     * The current deck in play.
     */
    private Deck deck;
    /**
     * The file location to save the game.
     */
    private File saveFile;
    /**
     * The file location to save the high scores.
     */
    private File scoreFile;

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
        panel = new JPanel();
        mainPanel = new JPanel();
        scorePanel = new JPanel();
        cardLabels = new JLabel[30];

        t = new Timer(2000, this);

        String path = System.getProperty("user.home");
        path += File.separator + "CGS";
        File customDir = new File(path);
        scoreFile = new File(customDir, "HighLow.score");
        saveFile = new File(customDir, "HighLow.ser");

        scores = loadOrCreateScores("Concentration");
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

        t.start();
        t.stop();

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

                    cardLabels[selectedCard].setEnabled(false);
                    cardLabels[secondSelectedCard].setEnabled(false);
                    visibleBooleanLabels[selectedCard] = false;
                    visibleBooleanLabels[secondSelectedCard] = false;
                    matches += 1;

                } else {

                    temp = deck.concentrationDeal(selectedCard);
                    cardLabels[selectedCard].setIcon(new ImageIcon(temp.getBack()));

                    temp = deck.concentrationDeal(secondSelectedCard);
                    cardLabels[secondSelectedCard].setIcon(new ImageIcon(temp.getBack()));

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
                    cardLabels[selectedCard].setIcon(new ImageIcon(temp.getBack()));

                    temp = deck.concentrationDeal(secondSelectedCard);
                    cardLabels[secondSelectedCard].setIcon(new ImageIcon(temp.getBack()));

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

        if (scores.isHighScore(attempts)) {
            String initials = "";
            while (initials == null || initials.length() > INITIAL_LENGTH || initials.length() == 0) {
                initials = JOptionPane.showInputDialog(this, "Enter Your Initials:\nMax of three characters",
                        "New High Score", JOptionPane.INFORMATION_MESSAGE);
            }
            HighScore score = new HighScore(initials, attempts);
            scores.add(score);
        }
    }

    /**
     * Stores the value for each card in an array based on their position on the
     * board. Recreates the array for new games, but not for loaded games.
     */
    public void createReferenceArray() {

        visibleBooleanLabels = new Boolean[30];

        if (MainCGS.DEBUGGING) {

            System.out.println("Components on panel before new game: " + panel.getComponentCount());

        }
        scoreLabel.setText(null);
        scorePanel.removeAll();
        scorePanel.add(scoreLabel);
        panel.removeAll();

        for (int i = 0; i < 30; i++) {

            JLabel cardLabel = new JLabel();

            temp = deck.concentrationDeal(i);
            temp.setSize(CARD_WIDTH, CARD_HEIGHT);
            cardLabel.setIcon(new ImageIcon(temp.getBack()));
            cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
            cardLabel.setVerticalAlignment(SwingConstants.CENTER);
            cardLabel.addMouseListener(this);
            cardLabels[i] = cardLabel;
            visibleBooleanLabels[i] = true;
            panel.add(cardLabels[i]);

        }

        scorePanel.revalidate();
        scorePanel.repaint();
        panel.revalidate();
        panel.repaint();

        if (MainCGS.DEBUGGING) {
            System.out.println("Components on panel after new game: " + panel.getComponentCount());
        }
    }

    /**
     * Creates the primary GUI for Concentration.
     */
    public final void createGUI() {

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        panel.setLayout(new GridLayout(5, 6, 0, 0));
        panel.setPreferredSize(new Dimension(800, 700));
        scorePanel.setPreferredSize(new Dimension(800, 35));

        Font f = new Font("Arial", Font.PLAIN, 22);
        scoreLabel.setFont(f);
        setSize(900, 900);
        setResizable(false);

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

        createMenu(this);
        mainPanel.add(scorePanel);
        mainPanel.add(panel);
        add(mainPanel);
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

        JOptionPane.showMessageDialog(mainPanel, "A new game has been started.", "Concentration",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows the high scores in a dialog box.
     */
    private void highScores() {
        JOptionPane.showMessageDialog(this, scores);
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

            JOptionPane.showMessageDialog(mainPanel, "Your game has been saved.", "Concentration",
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
        scorePanel.removeAll();
        panel.removeAll();
        mainPanel.removeAll();
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
                cardLabels[i] = cardLabel;
                panel.add(cardLabels[i]);
            }

            scorePanel.add(scoreLabel);
            scorePanel.revalidate();
            scorePanel.repaint();
            panel.revalidate();
            panel.repaint();
            mainPanel.add(scorePanel);
            mainPanel.add(panel);
            mainPanel.revalidate();
            mainPanel.repaint();
            add(mainPanel);

            JOptionPane.showMessageDialog(mainPanel, "Your game has been loaded.", "Concentration",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    } // end method

    /**
     * Shows the game play directions to the user.
     */
    private void directions() {
        JOptionPane.showMessageDialog(mainPanel,
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

        index = Arrays.asList(cardLabels).indexOf(e.getSource());
        temp = deck.concentrationDeal(index);
        cardLabels[index].setIcon(new ImageIcon(temp.getFront()));
        t.start();
        t.stop();
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
