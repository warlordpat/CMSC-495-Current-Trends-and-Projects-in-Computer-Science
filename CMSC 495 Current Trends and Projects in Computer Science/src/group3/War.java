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
    JFrame frame = new JFrame("War!");
    JFrame warFrame = new JFrame("It's War!");
    JPanel panel = new JPanel(new BorderLayout());
    JPanel gameBoard = new JPanel(new GridLayout(0, 3, 2, 2));
    JPanel warPanel = new JPanel(new GridLayout(0, 8, 15, 0));
    JLabel warLabel1 = new JLabel(" ");
    JLabel warLabel2 = new JLabel(" ");
    JLabel warLabel3 = new JLabel(" ");
    JLabel warLabel4 = new JLabel(" ");
    JLabel playerDiscardLabel = new JLabel("  Player Cards   ");
    JLabel aiDiscardLabel = new JLabel("  Computer Cards  ");
    JLabel blankLabel1 = new JLabel("  ");
    JLabel blankLabel2 = new JLabel("  ");
    JLabel blankLabel3 = new JLabel("  ");
    boolean bNew;

    public War() {

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
            Battle();
            playCount++;
        });

        JPanel pDisplay = new JPanel(new BorderLayout());
        menuItemD.addActionListener(ae -> {
            Directions();
        });
        JLabel backLabel = new JLabel(cardBacks);
        JLabel backLabel2 = new JLabel(cardBacks);

        JLabel blankLabel2 = new JLabel(" ");
        JLabel blankLabel3 = new JLabel(" ");
        JLabel blankLabel4 = new JLabel(" ");
        JLabel blankLabel5 = new JLabel(" ");
        JLabel topLabel = new JLabel("      Player Cards                                          "
                + "                                                                         Computer Cards   ");
        // panel.add(blankLabel1);
        // panel.add(blankLabel2);
        // panel.add(blankLabel3);
        panel.add(topLabel, BorderLayout.NORTH);
        // panel.add(player, BorderLayout.WEST);
        panel.add(backLabel, BorderLayout.WEST);
        // panel.add(blankLabel1, BorderLayout.CENTER);
        panel.add(backLabel2, BorderLayout.EAST);
        gameBoard.add(playerDiscardLabel);
        gameBoard.add(blankLabel1);
        gameBoard.add(aiDiscardLabel);
        panel.add(gameBoard, BorderLayout.CENTER);
        // panel.add(blankLabel4);
        // panel.add(blankLabel5);
        panel.add(jbOne, BorderLayout.SOUTH);
        warFrame.add(warPanel);
        frame.add(panel);
        frame.pack();
    }

    public void Battle() {

        JLabel warPlayerLabel1 = new JLabel("", SwingConstants.CENTER);
        JLabel warPlayerLabel2 = new JLabel("", SwingConstants.CENTER);
        JLabel warPlayerLabel3 = new JLabel("", SwingConstants.CENTER);
        JLabel breakPlayerLabel = new JLabel("", SwingConstants.CENTER);
        JLabel warAILabel1 = new JLabel("", SwingConstants.CENTER);
        JLabel warAILabel2 = new JLabel("", SwingConstants.CENTER);
        JLabel warAILabel3 = new JLabel("", SwingConstants.CENTER);
        JLabel breakAILabel = new JLabel("", SwingConstants.CENTER);

        Card playerCard = player.removeCard();
        Card aiCard = ai.removeCard();
        playerDiscardLabel.setIcon(new ImageIcon(playerCard.front));
        aiDiscardLabel.setIcon(new ImageIcon(aiCard.front));

        gameBoard.add(aiDiscardLabel);

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
            warLabel1.setText("YOU ");
            warLabel2.setText("LOST ");
            warLabel3.setText("THIS ");
            warLabel4.setText("BATTLE!");
        } else {
            // equal, go to war!
            blankLabel1.setText("WAR!");
            warLabel1.setText("PREPARE ");
            warLabel2.setText("FOR ");
            warLabel3.setText("ANOTHER ");
            warLabel4.setText("BATTLE!");
            JLabel p1Label = new JLabel("Player Card 1", SwingConstants.CENTER);
            JLabel p2Label = new JLabel("Player Card 2", SwingConstants.CENTER);
            JLabel p3Label = new JLabel("Player Card 3", SwingConstants.CENTER);
            JLabel p4Label = new JLabel("Player Card 4", SwingConstants.CENTER);
            JLabel a1Label = new JLabel("AI Card 1", SwingConstants.CENTER);
            JLabel a2Label = new JLabel("AI Card 2", SwingConstants.CENTER);
            JLabel a3Label = new JLabel("AI Card 3", SwingConstants.CENTER);
            JLabel a4Label = new JLabel("AI Card 4", SwingConstants.CENTER);
            p1Label.setVerticalAlignment(SwingConstants.BOTTOM);
            p2Label.setVerticalAlignment(SwingConstants.BOTTOM);
            p3Label.setVerticalAlignment(SwingConstants.BOTTOM);
            p4Label.setVerticalAlignment(SwingConstants.BOTTOM);
            a1Label.setVerticalAlignment(SwingConstants.BOTTOM);
            a2Label.setVerticalAlignment(SwingConstants.BOTTOM);
            a3Label.setVerticalAlignment(SwingConstants.BOTTOM);
            a4Label.setVerticalAlignment(SwingConstants.BOTTOM);
            Card[] playerWar = new Card[4];
            for (int i = 0; i < playerWar.length; i++) {
                playerWar[i] = player.removeCard();
            } // end for
            Card[] aiWar = new Card[4];
            for (int i = 0; i < aiWar.length; i++) {
                aiWar[i] = ai.removeCard();
            } // end for
            warPlayerLabel1.setIcon(new ImageIcon(playerWar[0].front));
            warPlayerLabel2.setIcon(new ImageIcon(playerWar[1].front));
            warPlayerLabel3.setIcon(new ImageIcon(playerWar[2].front));
            breakPlayerLabel.setIcon(new ImageIcon(playerWar[3].front));
            warAILabel1.setIcon(new ImageIcon(aiWar[0].front));
            warAILabel2.setIcon(new ImageIcon(aiWar[1].front));
            warAILabel3.setIcon(new ImageIcon(aiWar[2].front));
            breakAILabel.setIcon(new ImageIcon(aiWar[3].front));

            warPanel.add(p1Label);
            warPanel.add(p2Label);
            warPanel.add(p3Label);
            warPanel.add(p4Label);
            warPanel.add(a4Label);
            warPanel.add(a3Label);
            warPanel.add(a2Label);
            warPanel.add(a1Label);
            warPanel.add(warPlayerLabel1);
            warPanel.add(warPlayerLabel2);
            warPanel.add(warPlayerLabel3);
            warPanel.add(breakPlayerLabel);
            warPanel.add(breakAILabel);
            warPanel.add(warAILabel3);
            warPanel.add(warAILabel2);
            warPanel.add(warAILabel1);
            warPanel.add(blankLabel2);
            warPanel.add(blankLabel3);
            warPanel.add(warLabel1);
            warPanel.add(warLabel2);
            warPanel.add(warLabel3);
            warPanel.add(warLabel4);
            warFrame.add(warPanel);
            warFrame.pack();
            warFrame.setVisible(true);
            warFrame.setFocusable(true);

            if (playerWar[3].getRank().compareTo(aiWar[3].getRank()) > 0) {
                warLabel1.setText("YOU ");
                warLabel2.setText("WON ");
                warLabel3.setText("THIS ");
                warLabel4.setText("BATTLE!");
                for (int i = 0; i < playerWar.length; i++) {
                    player.addCard(playerWar[i]);
                } // end for
                for (int i = 0; i < aiWar.length; i++) {
                    player.addCard(playerWar[i]);
                } // end for
            }
            // TODO Add two other war conditions
            // TODO Put war into its own method
            // TODO add hand size checks to ensure hand doesn't get empty in a war
            // TODO ensure additional wars overwrite old display, not add additional display

        }

    }

    public void Directions() {
        JOptionPane.showMessageDialog(null,
                "The player with the higher card wins. \nIn the event of a tie, each player offers \nthe top 3 cards of their decks, and \nthen the player with the highest \n4th card wins the whole pile.");
    }
}