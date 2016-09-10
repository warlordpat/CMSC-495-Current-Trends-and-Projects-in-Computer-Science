// File: WarFrame.java
// Author: Patrick Smith
// Date: Sep 9, 2016
// Course: CMSC 495
// Assignment: TODO
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: TODO
package group3;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

/**
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 9, 2016
 */
public class WarFrame extends JFrame {
    JLabel warLabel1;
    JLabel warLabel2;
    JLabel warLabel3;
    JLabel warLabel4;
    JPanel warPanel;
    private JLabel warPlayerLabel1;
    private JLabel warPlayerLabel2;
    private JLabel warPlayerLabel3;
    private JLabel breakPlayerLabel;
    private JLabel warAILabel1;
    private JLabel warAILabel2;
    private JLabel warAILabel3;
    private JLabel breakAILabel;
    private JPanel topPanel;
    private JPanel statusPanel;
    private JLabel level;
    private JLabel levelNum;
    private JLabel spacer;

    /**
     * 
     */
    public WarFrame(String title) {
        super(title);
        warPanel = new JPanel(new GridLayout(0, 8, 15, 0));
        Dimension cardSize = new Dimension(72, 96);

        topPanel = new JPanel();
        getContentPane().add(topPanel, BorderLayout.NORTH);
        topPanel.setLayout(new GridLayout(0, 8, 15, 0));

        JLabel p1Label = new JLabel("W", SwingConstants.CENTER);
        topPanel.add(p1Label);
        p1Label.setPreferredSize(p1Label.getSize());
        p1Label.setVerticalAlignment(SwingConstants.BOTTOM);
        JLabel p2Label = new JLabel("A", SwingConstants.CENTER);
        topPanel.add(p2Label);
        p2Label.setVerticalAlignment(SwingConstants.BOTTOM);
        JLabel p3Label = new JLabel("R", SwingConstants.CENTER);
        topPanel.add(p3Label);
        p3Label.setVerticalAlignment(SwingConstants.BOTTOM);
        JLabel p4Label = new JLabel("Draw!", SwingConstants.CENTER);
        topPanel.add(p4Label);
        p4Label.setVerticalAlignment(SwingConstants.BOTTOM);
        JLabel a4Label = new JLabel("Draw!", SwingConstants.CENTER);
        topPanel.add(a4Label);
        a4Label.setVerticalAlignment(SwingConstants.BOTTOM);
        JLabel a3Label = new JLabel("R", SwingConstants.CENTER);
        topPanel.add(a3Label);
        a3Label.setVerticalAlignment(SwingConstants.BOTTOM);
        JLabel a2Label = new JLabel("A", SwingConstants.CENTER);
        topPanel.add(a2Label);
        a2Label.setVerticalAlignment(SwingConstants.BOTTOM);
        JLabel a1Label = new JLabel("W", SwingConstants.CENTER);
        topPanel.add(a1Label);
        a1Label.setVerticalAlignment(SwingConstants.BOTTOM);
        warPlayerLabel1 = new JLabel("", SwingConstants.CENTER);
        warPlayerLabel1.setPreferredSize(cardSize);
        warPlayerLabel2 = new JLabel("", SwingConstants.CENTER);
        warPlayerLabel3 = new JLabel("", SwingConstants.CENTER);
        breakPlayerLabel = new JLabel("", SwingConstants.CENTER);
        warAILabel1 = new JLabel("", SwingConstants.CENTER);
        warAILabel2 = new JLabel("", SwingConstants.CENTER);
        warAILabel3 = new JLabel("", SwingConstants.CENTER);
        breakAILabel = new JLabel("", SwingConstants.CENTER);
        warPanel.add(warPlayerLabel1);
        warPanel.add(warPlayerLabel2);
        warPanel.add(warPlayerLabel3);
        warPanel.add(breakPlayerLabel);
        warPanel.add(breakAILabel);
        warPanel.add(warAILabel3);
        warPanel.add(warAILabel2);
        warPanel.add(warAILabel1);
        getContentPane().add(warPanel);

        statusPanel = new JPanel();
        getContentPane().add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        level = new JLabel("War ");
        level.setHorizontalAlignment(SwingConstants.RIGHT);
        statusPanel.add(level);

        levelNum = new JLabel("");
        statusPanel.add(levelNum);

        spacer = new JLabel("");
        spacer.setPreferredSize(new Dimension(190, 0));
        statusPanel.add(spacer);
        warLabel1 = new JLabel(" ");
        warLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        statusPanel.add(warLabel1);
        warLabel1.setText("PREPARE ");
        warLabel2 = new JLabel(" ");
        statusPanel.add(warLabel2);
        warLabel2.setText("FOR ");
        warLabel3 = new JLabel(" ");
        statusPanel.add(warLabel3);
        warLabel3.setText("ANOTHER ");
        warLabel4 = new JLabel(" ");
        statusPanel.add(warLabel4);
        warLabel4.setText("BATTLE!");
        pack();

    }

    boolean setupGUI(Hand player, Hand ai, int warLevel) {
        boolean whoWon;
        int warSize = 4;
        if (player.handSize() < 4) {
            // not enough cards
            warSize = player.handSize();
        }
        if (ai.handSize() < 4) {
            // not enough cards
            warSize = ai.handSize();
        }
        Card[] playerWar = new Card[warSize];
        for (int i = 0; i < playerWar.length; i++) {
            System.out.println("Removing player card");
            playerWar[i] = player.removeCard();
        } // end for
        Card[] aiWar = new Card[warSize];
        for (int i = 0; i < aiWar.length; i++) {
            System.out.println("Removing ai card");
            aiWar[i] = ai.removeCard();
        } // end for
        levelNum.setText(Integer.toString(warLevel));
        for (int i = 0; i < playerWar.length; i++) {
            switch (i) {
            case 0:
                warPlayerLabel1.setIcon(new ImageIcon(playerWar[0].front));
                break;
            case 1:
                warPlayerLabel2.setIcon(new ImageIcon(playerWar[1].front));
                break;
            case 2:
                warPlayerLabel3.setIcon(new ImageIcon(playerWar[2].front));
                break;
            case 3:
                breakPlayerLabel.setIcon(new ImageIcon(playerWar[3].front));
                break;
            }
        }

        for (int i = 0; i < aiWar.length; i++) {
            switch (i) {
            case 0:
                warAILabel1.setIcon(new ImageIcon(aiWar[0].front));
                break;
            case 1:
                warAILabel2.setIcon(new ImageIcon(aiWar[1].front));
                break;
            case 2:
                warAILabel3.setIcon(new ImageIcon(aiWar[2].front));
                break;
            case 3:
                breakAILabel.setIcon(new ImageIcon(aiWar[3].front));
                break;
            }
        }

        int cardComparison = playerWar[playerWar.length - 1].getRank().compareTo(aiWar[aiWar.length - 1].getRank());
        if (cardComparison > 0) {
            warLabel1.setText("YOU ");
            warLabel2.setText("WON ");
            warLabel3.setText("THIS ");
            warLabel4.setText("BATTLE!");
            returnCardsTo(player, playerWar, aiWar);
            whoWon = true;
        } else if (cardComparison < 0) {
            warLabel1.setText("YOU ");
            warLabel2.setText("LOST");
            warLabel3.setText("THIS ");
            warLabel4.setText("BATTLE!");
            returnCardsTo(ai, playerWar, aiWar);
            whoWon = false;
        } else {
            // double war!
            System.out.println("double war");
            whoWon = setupGUI(player, ai, warLevel + 1);
            if (whoWon) {
                returnCardsTo(player, playerWar, aiWar);
            } else {
                returnCardsTo(ai, playerWar, aiWar);
            }
        }
        // TODO add hand size checks to ensure hand doesn't get empty in a
        // war

        setVisible(true);
        setFocusable(true);
        return whoWon;
    }

    /**
     * @param winner
     * @param playerWar
     * @param aiWar
     */
    private void returnCardsTo(Hand winner, Card[] playerWar, Card[] aiWar) {
        for (int i = 0; i < playerWar.length; i++) {
            winner.addCard(playerWar[i]);
        } // end for
        for (int i = 0; i < aiWar.length; i++) {
            winner.addCard(aiWar[i]);
        } // end for
    }
}
