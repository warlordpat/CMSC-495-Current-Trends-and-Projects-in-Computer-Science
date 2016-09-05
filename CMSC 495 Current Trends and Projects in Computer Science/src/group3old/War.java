package group3old;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class War {
    private Deck deck;
    private Hand ai, player, war;
    private Card playerCard, aiCard;
    JTextArea jta;
    JFrame frame = new JFrame("War!");
    boolean bNew;
    private static Map<Rank, Integer> values = new HashMap<>();
    static {
        values.put(Rank.ACE, 11);
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
    public War() {
        deck = new Deck();
        deck.shuffle();
        ai = new Hand();
        player = new Hand();
        war = new Hand();
        jta = new JTextArea(50,50);
        bNew = false;
    }
    
    public void begin() {
        player.returnCards();
        ai.returnCards();
        gui();
        Split();
    }
    public void gui() {
        frame.setVisible(true);
        frame.setSize(900, 600);
        JPanel panel = new JPanel(new BorderLayout());
        JPanel BottomPanel = new JPanel(new BorderLayout());
        BottomPanel.setBorder(new EmptyBorder(5,5,5,5));
        JButton jbOne = new JButton("One Battle");
        jbOne.addActionListener(ae -> {
            Battle();
        });
        JButton jbTen = new JButton("Ten Battles");
        jbTen.addActionListener(ae -> {
            for (int iCount = 0; iCount < 10; iCount++)
            {
                Battle();
                if (player.handSize() == 0 || ai.handSize() == 0)
                {
                    iCount = 10;
                }
            }
        });
        JButton jbAll = new JButton("All Battles");
        jbAll.addActionListener(ae -> {
            while (bNew == false)
            {
                Battle();
            }
            jta.setText(null);
            bNew = false;
        });
        JScrollPane TopPanel = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        BottomPanel.add(jbOne, BorderLayout.LINE_START);
        BottomPanel.add(jbTen, BorderLayout.CENTER);
        BottomPanel.add(jbAll, BorderLayout.LINE_END);
        JPanel pDisplay = new JPanel(new BorderLayout());
        JButton jbDisplay = new JButton ("Display");
        jbDisplay.addActionListener(ae -> {        
            Display();
        });
        JButton jbDirections = new JButton ("Directions");
        jbDirections.addActionListener(ae -> {
            Directions();
        });
        pDisplay.add(jbDisplay, BorderLayout.WEST);
        pDisplay.add(jbDirections, BorderLayout.EAST);
        JSplitPane splitPaneHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPaneHorizontal.setTopComponent(TopPanel);
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setTopComponent(BottomPanel);
        split.setBottomComponent(pDisplay);
        splitPaneHorizontal.setBottomComponent(split);
        frame.add(splitPaneHorizontal, BorderLayout.CENTER);
        frame.pack();
    }
    public void Split() {
        for (int iNum = 0; iNum < 26; iNum++)
        {
            player.addCard(deck.deal());
            ai.addCard(deck.deal());
        }
    }
    public void Battle() {
        playerCard = player.removeCard();
        aiCard = ai.removeCard();
        if (values.get(playerCard.getRank()) > values.get(aiCard.getRank()))
        {
            player.addCard(playerCard);
            player.addCard(aiCard);
            while (war.handSize() > 0)
            {
                player.addCard(war.removeCard());
            }
            jta.append("Your " + playerCard.getRank() + " beats their " + aiCard.getRank() + ". ");
            Display();            
            if (ai.handSize() == 0)
            {
                Object[] options = {"Yes", "No"};
                int n = JOptionPane.showOptionDialog(frame, "You won the war.\nDo you want to play again?", "You Won", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                if (n == JOptionPane.YES_OPTION)
                {
                    bNew = true;
                    player.returnCards();
                    ai.returnCards();
                    frame.dispose();
                    deck = new Deck();
                    deck.shuffle();
                    begin();
                }
                else
                {
                    frame.dispose();
                }
            }
        }
        else if (values.get(playerCard.getRank()) < values.get(aiCard.getRank()))
        {
            ai.addCard(aiCard);
            ai.addCard(playerCard);
            while (war.handSize() > 0)
            {
                ai.addCard(war.removeCard());
            }
            jta.append("Their " + aiCard.getRank() + " beats your " + playerCard.getRank() + ". ");
            Display();            
            if (player.handSize() == 0)
            {
                Object[] options = {"Yes", "No"};
                int n = JOptionPane.showOptionDialog(frame, "You lost the war.\nDo you want to play again?", "You Lost", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                if (n == JOptionPane.YES_OPTION)
                {
                    bNew = true;
                    player.returnCards();
                    ai.returnCards();
                    frame.dispose();
                    deck = new Deck();
                    deck.shuffle();
                    begin();
                }
                else
                {
                    frame.dispose();
                }
            }
        }
        else
        {
            jta.append("Your " + playerCard.getRank() + " ties their " + aiCard.getRank() + ". WAR!!! ");
            Display();
            war.addCard(playerCard);
            war.addCard(aiCard);
            if (player.handSize() > 1)
            {
                war.addCard(player.removeCard());
            }
            if (player.handSize() > 1)
            {
                war.addCard(player.removeCard());
            }
            if (player.handSize() > 1)
            {
                war.addCard(player.removeCard());
            }
            if (ai.handSize() > 1)
            {
                war.addCard(ai.removeCard());
            }
            if (ai.handSize() > 1)
            {
                war.addCard(ai.removeCard());
            }
            if (ai.handSize() > 1)
            {
                war.addCard(ai.removeCard());
            }
            Battle();
        }
    }
    public void Display() {
        jta.append("[" + player.handSize() + " vs " + ai.handSize() + "]\n");
    }
    public void Directions() {
        JOptionPane.showMessageDialog(null, "The player with the higher card wins. \nIn the event of a tie, each player offers \nthe top 3 cards of their decks, and \nthen the player with the highest \n4th card wins the whole pile.");
    }
}