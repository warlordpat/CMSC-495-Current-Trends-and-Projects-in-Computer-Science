package group3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class Blackjack {
    private Deck deck;
    private Hand dealer, player;
    boolean handInPlay;

    public Blackjack() {
        deck = new Deck();
        deck.shuffle();
        dealer = new Hand();
        player = new Hand();
        handInPlay = false;
    }
    public void begin() {
        gui();
        deal();
    }
    public String display() {
        String output = "";
        if (handInPlay) {
            output += "\nPlayer Hand: " + player;
            List<Card> dealerCards = dealer.getCards();
            String dealerString = "[";
            for (int i = 0; i < dealerCards.size(); i++) {
                if (i == 1) {
                    dealerString += "FACEDOWN";
                } else {
                    dealerString += dealerCards.get(i);
                } // end else
                if (i < dealerCards.size() - 1) {
                    dealerString += ", ";
                } else {
                    dealerString += "]";
                } // end else
            } // end for
            output += "\nDealer Hand: " + dealerString;
        } else {
            output += "Player Hand: " + player + " " + player.scoreHand();
            output += "\nDealer Hand: " + dealer + " " + dealer.scoreHand();
        }
        return output;
    }

    public void deal() {
        if (deck.size() < 8) {
            deck = new Deck();
            deck.shuffle();
            JOptionPane.showMessageDialog(null, "Reshuffling...");
        }
        if (!handInPlay) {
            dealer = new Hand();
            player = new Hand();
            player.addCard(deck.deal());
            player.addCard(deck.deal());
            dealer.addCard(deck.deal());
            dealer.addCard(deck.deal());
            handInPlay = true;
        }
    }

    public void hit() {
        if (handInPlay) {
            player.addCard(deck.deal());
            if (player.isBusted()) {
                JOptionPane.showMessageDialog(null, "Busted! You lose");
                handInPlay = false;
            } // end if
        } // end if
    } // end method

    public void stand() {
        if (handInPlay) {
            while (dealer.scoreHand() <= 17) {
                dealer.addCard(deck.deal());
            }
            handInPlay = false;
            if (dealer.scoreHand() >= player.scoreHand() && !dealer.isBusted()) {
                JOptionPane.showMessageDialog(null, "You lose.");
            } else {
                JOptionPane.showMessageDialog(null, "You win!");
            }
        }
    }

    public boolean isHandInPlay() {
        return handInPlay;
    }
    public void gui() {
        JFrame frame = new JFrame("Black Jack");
        frame.setVisible(true);
        JSplitPane splitPaneHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JPanel TopPanel = new JPanel();
        TopPanel.setSize(600, 400);
        JTextArea jtaDisplay = new JTextArea(30,20);
        jtaDisplay.append(display());
        TopPanel.add(jtaDisplay);
        JPanel BottomPanel = new JPanel(new BorderLayout());
        BottomPanel.setSize(600, 100);
        JButton jbHit = new JButton ("Hit");
        jbHit.addActionListener(ae -> {
            hit();
            jtaDisplay.append(display());
            if (player.isBusted())
            {
                jtaDisplay.append("\nPress 'Deal' to play again");
            }
        });
        JButton jbStand = new JButton ("Stand");
        jbStand.addActionListener(ae -> {
            stand();
            jtaDisplay.append(display());
            jtaDisplay.append("\nPress 'Deal' to play again");
        });
        JButton jbDeal = new JButton ("Deal");
        jbDeal.addActionListener(ae -> {
            deal();
            jtaDisplay.append(display());
        });
        BottomPanel.add(jbDeal, BorderLayout.LINE_START);
        BottomPanel.add(jbHit, BorderLayout.CENTER);
        BottomPanel.add(jbStand, BorderLayout.LINE_END);
        splitPaneHorizontal.setTopComponent(TopPanel);
        splitPaneHorizontal.setBottomComponent(BottomPanel);
        frame.add(splitPaneHorizontal);
        frame.setSize(600, 600);
    }
    public static void main(String[] args) {
        new BlackJack();
    } // end method
} // end class