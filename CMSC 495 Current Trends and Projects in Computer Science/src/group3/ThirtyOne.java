// File: ThirtyOne.java
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


public class ThirtyOne extends JPanel implements Game {
    private static final Color CARD_TABLE_GREEN = new Color(7, 99, 36);
    private static final Color LABEL_BLUE_GREY = new Color(40, 55, 67);
    private int SCREEN_X = 600;
    private int SCREEN_Y = 600;
    private int HAND_X = 200;
    private int HAND_Y = 200;
    private int CARD_HEIGHT = 96; 
    private int CARD_WIDTH = 72;
    private static final int INITIAL_LENGTH = 3;
    private static final long serialVersionUID = 510809952979347694L;
    private Deck deck;
    private Hand AI;
    private Hand player;
    private Card playerCard1, playerCard2, playerCard3, playerCard4;
    private Card aiCard1, aiCard2, aiCard3, aiCard4;
    private Card centerCard1, centerCard2, centerCard3;
    private ImageIcon playerCardImage1, playerCardImage2, playerCardImage3;
    private ImageIcon centerCardImage1, centerCardImage2, centerCardImage3;
    private int player31;
    private int ai31;
    private int iTurn;
    private Random rand = new Random();
    private HighScores scores;
    private JLabel jlPlayer;
    private JLabel jlCenter;
    private JLabel jlPlayerCard1, jlPlayerCard2, jlPlayerCard3, jlPlayerCard4;
    private JLabel jlPlayerScore;
    private JButton jbCenterCard1, jbCenterCard2, jbCenterCard3;
    private int playerScore;
    private JFrame frame;
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
    public ThirtyOne() {
        deck = new Deck();
        AI = new Hand();
        player = new Hand();
        player31 = 0;
        ai31 = 0;
        iTurn = 0;
        jlPlayer = new JLabel("Player's Hand");
        jlCenter = new JLabel("Center Cards");
        jlPlayerCard4 = null;
        jlPlayerScore = new JLabel("Player's Score: " + playerScore);
        playerScore = 0;
        scores = loadOrCreateScores("ThirtyOne");
        createGUI();
    }
    public final void begin() {
        System.out.println("beginning");
        newGame();
    }
    final void newGame() {
        deck.shuffle();
    }
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
    private void highScores() {
        JOptionPane.showMessageDialog(frame, scores);
    }
    private void directions() {
        JOptionPane.showMessageDialog(null, "The objective of Thirty-One is to be the closer player to 31.\nEach player starts with 3 cards. They take turns adding 1 \ncard to their hands out of 3 from the center. The player \ncloser to 31 wins the game.");
    } 
    private void setCards() {
        player.removeAll();
        AI.removeAll();
        try {
            remove(jlPlayerCard1);
            remove(jlPlayerCard2);
            remove(jlPlayerCard3);
        } catch (Exception e) {}
        setLayout(null);
        setBackground(CARD_TABLE_GREEN);
        jlCenter.setLocation(25, 25);
        jlCenter.setForeground(LABEL_BLUE_GREY);
        jlCenter.setFont(new Font("Tahoma", Font.PLAIN, 16));
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
        iTurn = rand.nextInt(2)+1;
        if (iTurn == 1)
        {
            if (values.get(centerCard1.getRank()) > values.get(centerCard2.getRank()))
            {
                if (values.get(centerCard1.getRank()) > values.get(centerCard3.getRank()))
                {
                    AI.addCard(centerCard1);
                    jbCenterCard1.setVisible(false);
                }
                else
                {
                    AI.addCard(centerCard3);
                    jbCenterCard3.setVisible(false);
                }
            }
            else
            {
                if (values.get(centerCard2.getRank()) > values.get(centerCard3.getRank()))
                {
                    AI.addCard(centerCard2);
                    jbCenterCard2.setVisible(false);
                }
                else
                {
                    AI.addCard(centerCard3);
                    jbCenterCard3.setVisible(false);
                }
            }
        }
        jbCenterCard1.setBounds(150, 100, CARD_WIDTH, CARD_HEIGHT);
        add(jbCenterCard1);
        jbCenterCard2.setBounds(250, 100, CARD_WIDTH, CARD_HEIGHT);
        add(jbCenterCard2);
        jbCenterCard3.setBounds(350, 100, CARD_WIDTH, CARD_HEIGHT);
        add(jbCenterCard3);
        jlPlayer.setLocation(25, 250);
        jlPlayer.setForeground(LABEL_BLUE_GREY);
        jlPlayer.setFont(new Font("Tahoma", Font.PLAIN, 16));
        jlPlayer.setSize(jlPlayer.getPreferredSize());
        add(jlPlayer);
        player.setLocation(150,  300);
        add(player);
        jlPlayerScore.setLocation(450, 450);
        jlPlayerScore.setForeground(LABEL_BLUE_GREY);
        jlPlayerScore.setFont(new Font("Tahoma", Font.PLAIN, 16));
        jlPlayerScore.setSize(jlPlayerScore.getPreferredSize());
        add(jlPlayerScore);
        revalidate();
        repaint();
    }
    private void cardButtons() {
        jbCenterCard1.addActionListener(ae -> {
            if (player.handSize() < 4) {
                player.addCard(centerCard1);
                centerCard1.flip();
                jbCenterCard1.setVisible(false);
                revalidate();
                repaint();
                results();
            }
        });
        jbCenterCard2.addActionListener(ae -> {
            if (player.handSize() < 4) {
                player.addCard(centerCard2);
                centerCard2.flip();
                jbCenterCard2.setVisible(false);
                revalidate();
                repaint();
                results();
            }
        });
        jbCenterCard3.addActionListener(ae -> {
            if (player.handSize() < 4) {
                player.addCard(centerCard3);
                centerCard3.flip();
                jbCenterCard3.setVisible(false);
                revalidate();
                repaint();
                results();
            }
        });
    }
    public void saveGame() {
        System.out.println("Saving game");
        try (FileOutputStream fileStream = new FileOutputStream("ThirtyOne.ser");
                ObjectOutputStream os = new ObjectOutputStream(fileStream);) {
            os.writeObject(deck);
            os.writeObject(AI);
            os.writeObject(player);
            os.writeObject(centerCard1);
            os.writeObject(centerCard2);
            os.writeObject(centerCard3);
            os.writeInt(iTurn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadGame() {
        System.out.println("Loading game");
        remove(deck);
        remove(AI);
        remove(player);
        remove(centerCard1);
        remove(centerCard2);
        remove(centerCard3);
        try (FileInputStream fileStream = new FileInputStream("ThirtyOne.ser");
                ObjectInputStream os = new ObjectInputStream(fileStream);) {
            deck = (Deck) os.readObject();
            AI = (Hand) os.readObject();
            player = (Hand) os.readObject();
            centerCard1 = (Card) os.readObject();
            centerCard2 = (Card) os.readObject();
            centerCard3 = (Card) os.readObject();
            centerCardImage1 = new ImageIcon(centerCard1.getFront());
            centerCardImage2 = new ImageIcon(centerCard2.getFront());
            centerCardImage3 = new ImageIcon(centerCard3.getFront());
            jbCenterCard1 = new JButton(centerCardImage1);
            jbCenterCard2 = new JButton(centerCardImage2);
            jbCenterCard3 = new JButton(centerCardImage3);
            add(jbCenterCard1);
            add(jbCenterCard2);
            add(jbCenterCard3);
            add(player);
            revalidate();
            repaint();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
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
    private void results() {
        System.out.println(player.total());
        player31 = 31 - player.total();
        player31 = Math.abs(player31);
        ai31 = 31 - AI.total();
        ai31 = Math.abs(ai31);
        if (player31 < ai31)
        {
            playerScore += 100;
            Object[] options = { "Yes", "No" };
                    int n = JOptionPane.showOptionDialog(frame,
                            ("You win " + player.total() + " to " + AI.total() + "!\nWould you like to play again?"),
                            "You Win!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                            options[1]);
                    if (n == JOptionPane.YES_OPTION) {
                        reset();
                    } else {
                        if (scores.isHighScore(playerScore)) {
                        String initials = getInitials(this);
                        HighScore score = new HighScore(initials, (int) playerScore);
                        scores.add(score);
            }
                    }
        }
        else if (player31 == ai31)
        {
            playerScore += 50;
            Object[] options = { "Yes", "No" };
                    int n = JOptionPane.showOptionDialog(frame,
                            ("You tie " + player.total() + " to " + AI.total() + "!\nWould you like to play again?"),
                            "You tie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                            options[1]);
                    if (n == JOptionPane.YES_OPTION) {
                        reset();
                    } else {
                        if (scores.isHighScore(playerScore)) {
                        String initials = getInitials(this);
                        HighScore score = new HighScore(initials, (int) playerScore);
                        scores.add(score);
            }
                    }
        }
        else
        {
            Object[] options = { "Yes", "No" };
                    int n = JOptionPane.showOptionDialog(frame,
                            ("You lose " + player.total() + " to " + AI.total() + "!\nWould you like to play again?"),
                            "You lose", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                            options[1]);
                    if (n == JOptionPane.YES_OPTION) {
                        reset();
                    } else {
                        if (scores.isHighScore(playerScore)) {
                        String initials = getInitials(this);
                        HighScore score = new HighScore(initials, (int) playerScore);
                        scores.add(score);
            }
                    }
        }
    }
    public static void main(final String[] args) {
        ThirtyOne thirtyone = new ThirtyOne();
        thirtyone.begin();
    }
}