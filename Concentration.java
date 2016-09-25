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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class Concentration extends JFrame implements MouseListener, ActionListener {

    int test = 0;
    int selectedCard = 0;
    int secondSelectedCard = 0;
    int index = 0;
    int matches = 0;
    int attempts = 0;
    long timeStart;
    long timeEnd;
    boolean firstCard = true;
    private static final int CARD_WIDTH = 106;
    private static final int CARD_HEIGHT = 144;
    JFrame frame;
    JPanel panel = new JPanel();
    JPanel mainPanel = new JPanel(); 
    JPanel scorePanel = new JPanel();
    JLabel[] cardLabels = new JLabel[30];
    JLabel scoreLabel = new JLabel();
    Card temp;
    Timer t = new Timer(2000, this);
    private HighScores scores;
        /**
     * The maximum length of the initials for a high score.
     */
    private static final int INITIAL_LENGTH = 3;
        /**
     * The current deck in play.
     */
    private final Deck deck;
    
    public Concentration() {
        
        deck = new Deck();
      
        String path = System.getProperty("user.home");
        path += File.separator + "CGS";
        File customDir = new File(path);
        File scoreFile = new File(customDir, "Concentration.score");
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
        }

        createGUI();  

    }

        /**
     * Creates a new game session.
     */
    final void newGame() {
  
        deck.ConcentrationShuffle();
        createReferenceArray();
    }
    
        /**
     * Starts the game play.
     */
    public final void begin() {
        
        newGame();
  
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
            
            if (deck.concentrationDeal(secondSelectedCard).toString() == null ? deck.concentrationDeal(selectedCard).toString() == null
                    : deck.concentrationDeal(secondSelectedCard).toString().equals(deck.concentrationDeal(selectedCard).toString())) {
            
                if (selectedCard != secondSelectedCard) {

                    cardLabels[selectedCard].setEnabled(false);
                    cardLabels[secondSelectedCard].setEnabled(false);   
                    matches += 1;
                    
                } 
            
                else {
                    
                    temp = deck.concentrationDeal(selectedCard);
                    cardLabels[selectedCard].setIcon(new ImageIcon(temp.back));
                    
                    temp = deck.concentrationDeal(secondSelectedCard);
                    cardLabels[secondSelectedCard].setIcon(new ImageIcon(temp.back));
                    
                    
                }
                test = 0;
            } 
            
            else {
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
        
        scoreLabel.setText("Congratulations!  You finished this game in " + ((timeEnd - timeStart) / 1000) + " seconds and " + attempts + " attempts!");
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
        //cardLabels = new JLabel[30];
        
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

    }
    
    public final void createGUI() {

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//        deck.ConcentrationShuffle();
        panel.setLayout(new GridLayout(5,6,0,0));
        panel.setPreferredSize(new Dimension(800, 700));
        scorePanel.setPreferredSize(new Dimension(800, 35));

        Font f = new Font("Arial", Font.PLAIN, 22);
        scoreLabel.setFont(f);
        scorePanel.add(scoreLabel);
        frame = new JFrame("Concentration!");
        frame.setVisible(true);
        frame.setSize(900, 900);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(final WindowEvent e) {
                // write out high scores here
                String path = System.getProperty("user.home");
                path += File.separator + "CGS";
                File customDir = new File(path);
                File scoreFile = new File(customDir, "Concentration.score");
                saveHighScores(scoreFile);
            }
        });
        createMenu(frame);
        mainPanel.add(scorePanel);
        mainPanel.add(panel);
        frame.add(mainPanel);
        frame.pack();
        
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
            //reset();
        });
//
//        JMenuItem mntmSaveGame = new JMenuItem("Save Game");
//        mnMenu.add(mntmSaveGame);
//        mntmSaveGame.addActionListener(ae -> {
//            saveGame();
//        });
//
//        JMenuItem mntmLoadGame = new JMenuItem("Load Game");
//        mnMenu.add(mntmLoadGame);
//        mntmLoadGame.addActionListener(ae -> {
//            loadGame();
//        });
//
//        JMenuItem mntmHowToPlay = new JMenuItem("How to Play");
//        mnMenu.add(mntmHowToPlay);
//        mntmHowToPlay.addActionListener(ae -> {
//            directions();
//        });

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
     * Shows the high scores in a dialog box.
     */
    private void highScores() {
        JOptionPane.showMessageDialog(frame, scores);
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
}
