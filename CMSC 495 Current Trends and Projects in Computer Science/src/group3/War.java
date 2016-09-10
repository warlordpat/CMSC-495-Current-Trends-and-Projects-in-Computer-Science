package group3;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class War {

    URL imageURL = this.getClass().getClassLoader().getResource("images/cardBack.png");
    ImageIcon cardBacks = new ImageIcon(imageURL);
    private Deck deck;
    private int playCount = 0;
    private int warIndex = 0;
    private Hand ai, player;
    private Card playerCard, aiCard;
    JTextArea jta;
    JFrame frame;
    WarFrame warFrame;
    JPanel panel;
    JPanel gameBoard;

    JLabel playerDiscardLabel;
    JLabel aiDiscardLabel;
    JLabel blankLabel1;
    JLabel blankLabel2;
    JLabel blankLabel3;
    boolean bNew;
    private JLabel handSizeAI;
    private JLabel handSizePlayer;
    private boolean gameOver;

    public War() {
        frame = new JFrame("War!");
        warFrame = new WarFrame("It's War!");
        panel = new JPanel(new BorderLayout());
        gameBoard = new JPanel(new GridLayout(0, 3, 2, 2));
        playerDiscardLabel = new JLabel();
        playerDiscardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        aiDiscardLabel = new JLabel();
        aiDiscardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        blankLabel1 = new JLabel("  ");
        blankLabel2 = new JLabel("  ");
        blankLabel3 = new JLabel("  ");
        gameOver = false;
    }

    public void begin() {
        deck = new Deck();
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
        jta = new JTextArea(50, 50);
        bNew = false;
        gui();
        playCount = 0;
    }

    public static void main(String[] args) {
        War test = new War();
        test.begin();
    }

    public void gui() {
        frame.setVisible(true);
        frame.setSize(900, 900);
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
            Directions();
        });

        JLayeredPane layeredPanePlayer = new JLayeredPane();
        JLabel backLabel = new JLabel(cardBacks, JLabel.CENTER);
        backLabel.setSize(106, 144);
        layeredPanePlayer.setPreferredSize(backLabel.getPreferredSize());
        handSizePlayer = new JLabel("26");
        handSizePlayer.setFont(new Font("Tahoma", Font.PLAIN, 30));
        handSizePlayer.setHorizontalAlignment(SwingConstants.CENTER);
        handSizePlayer.setLocation(69, 0);
        handSizePlayer.setSize(37, 33);
        handSizePlayer.setBackground(Color.WHITE);
        layeredPanePlayer.add(handSizePlayer, new Integer(1));
        layeredPanePlayer.add(backLabel, new Integer(0));

        JLabel backLabel2 = new JLabel(cardBacks, JLabel.CENTER);
        JLayeredPane layeredPaneAI = new JLayeredPane();
        backLabel2.setSize(106, 144);
        layeredPaneAI.setPreferredSize(backLabel.getPreferredSize());
        handSizeAI = new JLabel("26");
        handSizeAI.setFont(new Font("Tahoma", Font.PLAIN, 30));
        handSizeAI.setHorizontalAlignment(SwingConstants.CENTER);
        handSizeAI.setLocation(0, 0);
        handSizeAI.setSize(37, 33);
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
    }

    public void battle() {
        Card playerCard = player.removeCard();
        Card aiCard = ai.removeCard();
        playerDiscardLabel.setIcon(new ImageIcon(playerCard.front));
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
            }else if (ai.handSize() == 0) {
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
     * 
     */
    private boolean war() {
        System.out.println("Starting hands: player: " + player.handSize() + " ai: " + ai.handSize());
        // blankLabel1.setText("You lost this fight!");
        // warLabel1.setText("YOU ");
        // warLabel2.setText("LOST ");
        // warLabel3.setText("THIS ");
        // warLabel4.setText("BATTLE!");
        blankLabel1.setText("WAR!");
        boolean whoWon = warFrame.setupGUI(player, ai, 1);
        System.out.println("Ending hands: player: " + player.handSize() + " ai: " + ai.handSize());
        return whoWon;
    } // end method

    /**
     * 
     */
    private void updateHandSizeDisplay() {
        handSizePlayer.setText(Integer.toString(player.handSize()));
        handSizeAI.setText(Integer.toString(ai.handSize()));
    }

    public void Directions() {
        JOptionPane.showMessageDialog(null,
                "The player with the higher card wins. \nIn the event of a tie, each player offers \nthe top 3 cards of their decks, and \nthen the player with the highest \n4th card wins the whole pile.");
    }
}