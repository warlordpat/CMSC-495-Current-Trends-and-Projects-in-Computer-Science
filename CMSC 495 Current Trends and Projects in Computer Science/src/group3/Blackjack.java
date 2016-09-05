package group3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.synth.SynthSeparatorUI;

public class Blackjack extends JPanel {
    private Deck deck;
    private Hand dealer, player;
    boolean handInPlay;

    public Blackjack() {
        setLayout(null);
        setBackground(new Color(7, 99, 36)); // Card Table Green
        
    }

    public void begin() {
        System.out.println("beginning");
        patrickGUI();
        newGame();
        deal();
    }

    void newGame() {
        deck = new Deck();
        deck.shuffle();
        deck.setBounds(60, 35, 79, 123); // set the deck size
        System.out.println("Adding Deck");
        add(deck); // add the deck to the play surface
        
        System.out.println("Adding Dealer");
        dealer = new Hand();
        dealer.setBounds(400, 100, (2 * 79), 123);
        add(dealer);
        player = new Hand();
        
        handInPlay = false;
        repaint();
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
        if (deck.deckSize() < 8) {
            deck = new Deck();
            deck.shuffle();
            JOptionPane.showMessageDialog(null, "Reshuffling...");
        }
        if (!handInPlay) {
            System.out.println("Dealing new hand");
            remove(dealer);
            dealer = new Hand();
            dealer.setBounds(400, 100, (2* 79), 123); // adds a new dealer hand to the GUI
            add(dealer);
            
            player = new Hand();
            player.setBounds(400, 400, (2* 79), 123); // adds a new dealer hand to the GUI
            add(player);
            
            Card temp = deck.deal();
            temp.flip();
            player.addCard(temp);
            player.addCard(deck.deal());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            dealer.addCard(deck.deal());
            dealer.addCard(deck.deal());
            System.out.println(dealer);
            temp.flip();
            handInPlay = true;
            repaint();
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

    public void patrickGUI() {
        JFrame frame = new JFrame("Black Jack");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setTitle("CGS BlackJack");
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setResizable(false);
        frame.setContentPane(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void gui() {
        JFrame frame = new JFrame("Black Jack");
        frame.setVisible(true);
        JSplitPane splitPaneHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JPanel TopPanel = new JPanel();
        TopPanel.setSize(600, 400);
        JTextArea jtaDisplay = new JTextArea(30, 20);
        jtaDisplay.append(display());
        TopPanel.add(jtaDisplay);
        JPanel BottomPanel = new JPanel(new BorderLayout());
        BottomPanel.setSize(600, 100);
        JButton jbHit = new JButton("Hit");
        jbHit.addActionListener(ae -> {
            hit();
            jtaDisplay.append(display());
            if (player.isBusted()) {
                jtaDisplay.append("\nPress 'Deal' to play again");
            }
        });
        JButton jbStand = new JButton("Stand");
        jbStand.addActionListener(ae -> {
            stand();
            jtaDisplay.append(display());
            jtaDisplay.append("\nPress 'Deal' to play again");
        });
        JButton jbDeal = new JButton("Deal");
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
        Blackjack blackjack = new Blackjack();
        blackjack.begin();
    } // end method
} // end class