// File: Concentration.java
// Author: Christy Gilliland
// Date: Sep 20, 2016
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
import java.util.Arrays;
import javax.swing.*;

/**
 *
 * @author thegi
 */
public class Concentration extends JFrame implements MouseListener, ActionListener, Game {
    /**
     * Generated Serial ID. For Serialization.
     */
    private static final long serialVersionUID = -8467046280350561242L;
    /**
     * The maximum length of the initials for a high score.
     */
    private static final int INITIAL_LENGTH = 3;
    private static final int CARD_WIDTH = 106;
    private static final int CARD_HEIGHT = 144;
    int test;
    int selectedCard;
    int secondSelectedCard;
    int index;
    int matches;
    int attempts;
    long timeStart;
    long timeEnd;
    boolean firstCard;
    JPanel panel;
    JPanel mainPanel;
    JPanel scorePanel;
    JLabel[] cardLabels;
    JLabel scoreLabel;
    Card temp;
    Timer t;
    private HighScores scores;
    /**
     * The current deck in play.
     */
    private final Deck deck;
    /**
     * The file location to save the game.
     */
    private File saveFile;
    /**
     * The file location to save the high scores.
     */
    private File scoreFile;

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
        panel = new JPanel();
        mainPanel = new JPanel();
        scorePanel = new JPanel();
        cardLabels = new JLabel[30];
        scoreLabel = new JLabel();
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
        if (group3.DEBUGGING) {
            System.out.println("Creating new Game");
        }
        deck.ConcentrationShuffle();
        if (group3.DEBUGGING) {
            System.out.println("Concentration deck after shuffle: " + deck.concentrationCards);
        }
        createReferenceArray();
    }

    /**
     * Starts the game play.
     */
    public final void begin() {
        newGame();
        setVisible(true);
    }

    public static void main(String[] args) {
        Concentration concentration = new Concentration();
        concentration.begin();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        t.start();
        t.stop();
    }

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
            attempts += 1;
            scoreLabel.setText("Attempts: " + attempts);

            if (deck.concentrationDeal(secondSelectedCard).toString() == null
                    ? deck.concentrationDeal(selectedCard).toString() == null
                    : deck.concentrationDeal(secondSelectedCard).toString()
                            .equals(deck.concentrationDeal(selectedCard).toString())) {

                if (selectedCard != secondSelectedCard) {
                    cardLabels[selectedCard].setEnabled(false);
                    cardLabels[secondSelectedCard].setEnabled(false);
                    matches += 1;
                } else {
                    temp = deck.concentrationDeal(selectedCard);
                    cardLabels[selectedCard].setIcon(new ImageIcon(temp.back));

                    temp = deck.concentrationDeal(secondSelectedCard);
                    cardLabels[secondSelectedCard].setIcon(new ImageIcon(temp.back));
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
                    cardLabels[selectedCard].setIcon(new ImageIcon(temp.back));

                    temp = deck.concentrationDeal(secondSelectedCard);
                    cardLabels[secondSelectedCard].setIcon(new ImageIcon(temp.back));

                    test = 0;
                });
            }
        }
        if (matches == 15) {
            timeEnd = System.currentTimeMillis();
            gameWon();
        }
    }

    public void gameWon() {
        scoreLabel.setText("Congratulations!  You finished this game in " + ((timeEnd - timeStart) / 1000)
                + " seconds and " + attempts + " attempts!");
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

    public void createReferenceArray() {
        // cardLabels = new JLabel[30];
        if (group3.DEBUGGING) {
            System.out.println("Components on panel before new game: " + panel.getComponentCount());
        }
        panel.removeAll();
        for (int i = 0; i < 30; i++) {
            JLabel cardLabel = new JLabel();

            temp = deck.concentrationDeal(i);
            temp.setSize(CARD_WIDTH, CARD_HEIGHT);
            cardLabel.setIcon(new ImageIcon(temp.back));
            cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
            cardLabel.setVerticalAlignment(SwingConstants.CENTER);
            cardLabel.addMouseListener(this);
            cardLabels[i] = cardLabel;
            panel.add(cardLabels[i]);
        }
        panel.revalidate();
        panel.repaint();
        if (group3.DEBUGGING) {
            System.out.println("Components on panel after new game: " + panel.getComponentCount());
        }
    }

    public final void createGUI() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        // deck.ConcentrationShuffle();
        panel.setLayout(new GridLayout(5, 6, 0, 0));
        panel.setPreferredSize(new Dimension(800, 700));
        scorePanel.setPreferredSize(new Dimension(800, 35));

        Font f = new Font("Arial", Font.PLAIN, 22);
        scoreLabel.setFont(f);
        scorePanel.add(scoreLabel);
        setSize(900, 900);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                // write out high scores here
                saveHighScores(scoreFile, scores);
                if (group3.DEBUGGING) {
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
        //
        // JMenuItem mntmSaveGame = new JMenuItem("Save Game");
        // mnMenu.add(mntmSaveGame);
        // mntmSaveGame.addActionListener(ae -> {
        // saveGame();
        // });
        //
        // JMenuItem mntmLoadGame = new JMenuItem("Load Game");
        // mnMenu.add(mntmLoadGame);
        // mntmLoadGame.addActionListener(ae -> {
        // loadGame();
        // });
        //
        // JMenuItem mntmHowToPlay = new JMenuItem("How to Play");
        // mnMenu.add(mntmHowToPlay);
        // mntmHowToPlay.addActionListener(ae -> {
        // directions();
        // });

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
     * 
     */
    private void reset() {
        // TODO Auto-generated method stub
        firstCard = true;

        test = 0;
        selectedCard = 0;
        secondSelectedCard = 0;
        index = 0;
        matches = 0;
        attempts = 0;

    }

    /**
     * Shows the high scores in a dialog box.
     */
    private void highScores() {
        JOptionPane.showMessageDialog(this, scores);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (group3.DEBUGGING) {
            System.out.println("Who initiated the MouseEvent: " + e.getComponent());
        }
        index = Arrays.asList(cardLabels).indexOf(e.getSource());
        System.out.println(index);
        temp = deck.concentrationDeal(index);
        cardLabels[index].setIcon(new ImageIcon(temp.front));
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

    /*
     * (non-Javadoc)
     * 
     * @see group3.Game#loadGame()
     */
    @Override
    public void loadGame() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see group3.Game#saveGame()
     */
    @Override
    public void saveGame() {
        // TODO Auto-generated method stub

    }
}
