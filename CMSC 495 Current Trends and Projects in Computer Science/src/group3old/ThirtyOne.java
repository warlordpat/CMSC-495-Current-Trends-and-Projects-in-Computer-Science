/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group3old;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.lang.*;

/**
 *
 * @author Alex
 */
public class ThirtyOne {
    private Deck deck;
    private Hand ai, player;
    JTextArea jtaHand;
    private static Map<Rank, Integer> values = new HashMap<>();
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
        deck.shuffle();
        ai = new Hand();
        player = new Hand();
        jtaHand = new JTextArea();
    }
    public void begin() {
        gui();
        deal();
    }
    public void deal() {
        if (deck.size() < 8) {
            deck = new Deck();
            deck.shuffle();
            JOptionPane.showMessageDialog(null, "Reshuffling...");
        }
        ai = new Hand();
        player = new Hand();
        player.addCard(deck.deal());
        player.addCard(deck.deal());
        player.addCard(deck.deal());
        ai.addCard(deck.deal());
        ai.addCard(deck.deal());
        ai.addCard(deck.deal());
        jtaHand.setText(display());
    }
    public void gui() {
        Random rand = new Random();
        JFrame frame = new JFrame("Thirty-One");
        frame.setVisible(true);
        JSplitPane splitPaneHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JPanel TopPanel = new JPanel(new BorderLayout());
        JPanel BottomPanel = new JPanel();
        jtaHand.setText(display());
        BottomPanel.add(jtaHand);
        Card Card1 = deck.deal();
        Card Card2 = deck.deal();
        Card Card3 = deck.deal();
        int iCard1 = values.get(Card1.getRank());
        int iCard2 = values.get(Card2.getRank());
        int iCard3 = values.get(Card3.getRank());
        JButton jbCard1 = new JButton(Card1.toString());
        JButton jbCard2 = new JButton(Card2.toString());
        JButton jbCard3 = new JButton(Card3.toString());
        if ((rand.nextInt(2)+1) == 1)
        {
            TopPanel.add(jbCard1, BorderLayout.LINE_START);
            TopPanel.add(jbCard2, BorderLayout.CENTER);
            TopPanel.add(jbCard3, BorderLayout.LINE_END);
        }//player goes first
        else
        {
            if (iCard1 > iCard2)
            {
                if (iCard1 > iCard3)
                {
                    ai.addCard(Card1);
                    TopPanel.add(jbCard2, BorderLayout.LINE_START);
                    TopPanel.add(jbCard3, BorderLayout.LINE_END);
                }
                else
                {
                    ai.addCard(Card3);
                    TopPanel.add(jbCard1, BorderLayout.LINE_START);
                    TopPanel.add(jbCard2, BorderLayout.LINE_END);
                }
            }
            else
            {
                ai.addCard(Card2);
                TopPanel.add(jbCard1, BorderLayout.LINE_START);
                TopPanel.add(jbCard3, BorderLayout.LINE_END);
            }
        }//ai goes first
        jbCard1.addActionListener(ae -> {
            if (player.handSize() < 4)
            {
                player.addCard(Card1);
                TopPanel.remove(jbCard1);
                jtaHand.setText(display());
                if (player.total() > ai.total())
                {
                    Object[] options = {"Yes", "No"};
                    int n = JOptionPane.showOptionDialog(frame, ("You win " + player.total() + " to " + ai.total() + "!\nWould you like to play again?"), "You Win!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (n == JOptionPane.YES_OPTION)
                    {
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
                else if (player.total() == ai.total())
                {
                    Object[] options = {"Yes", "No"};
                    int n = JOptionPane.showOptionDialog(frame, ("You tied at " + player.total() + " to " + ai.total() + "!\nWould you like to play again?"), "You Tie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (n == JOptionPane.YES_OPTION)
                    {
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
                else
                {
                    Object[] options = {"Yes", "No"};
                    int n = JOptionPane.showOptionDialog(frame, ("You lose " + player.total() + " to " + ai.total() + "!\nWould you like to play again?"), "You Lose", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (n == JOptionPane.YES_OPTION)
                    {
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
                JOptionPane.showMessageDialog(null, "You already have 4 cards in your hand");
            }
        });
        jbCard2.addActionListener(ae -> {
            if (player.handSize() < 4)
            {
                player.addCard(Card2);
                TopPanel.remove(jbCard2);
                jtaHand.setText(display());
                if (player.total() > ai.total())
                {
                    Object[] options = {"Yes", "No"};
                    int n = JOptionPane.showOptionDialog(frame, ("You win " + player.total() + " to " + ai.total() + "!\nWould you like to play again?"), "You Win!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (n == JOptionPane.YES_OPTION)
                    {
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
                else if (player.total() == ai.total())
                {
                    Object[] options = {"Yes", "No"};
                    int n = JOptionPane.showOptionDialog(frame, ("You tied at " + player.total() + " to " + ai.total() + "!\nWould you like to play again?"), "You Tie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (n == JOptionPane.YES_OPTION)
                    {
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
                else
                {
                    Object[] options = {"Yes", "No"};
                    int n = JOptionPane.showOptionDialog(frame, ("You lose " + player.total() + " to " + ai.total() + "!\nWould you like to play again?"), "You Lose", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (n == JOptionPane.YES_OPTION)
                    {
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
                JOptionPane.showMessageDialog(null, "You already have 4 cards in your hand");
            }
        });
        jbCard3.addActionListener(ae -> {
            if (player.handSize() < 4)
            {
                player.addCard(Card3);
                TopPanel.remove(jbCard3);
                jtaHand.setText(display());
                if (player.total() > ai.total())
                {
                    Object[] options = {"Yes", "No"};
                    int n = JOptionPane.showOptionDialog(frame, ("You win " + player.total() + " to " + ai.total() + "!\nWould you like to play again?"), "You Win!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (n == JOptionPane.YES_OPTION)
                    {
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
                else if (player.total() == ai.total())
                {
                    Object[] options = {"Yes", "No"};
                    int n = JOptionPane.showOptionDialog(frame, ("You tied at " + player.total() + " to " + ai.total() + "!\nWould you like to play again?"), "You Tie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (n == JOptionPane.YES_OPTION)
                    {
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
                else
                {
                    Object[] options = {"Yes", "No"};
                    int n = JOptionPane.showOptionDialog(frame, ("You lose " + player.total() + " to " + ai.total() + "!\nWould you like to play again?"), "You Lose", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (n == JOptionPane.YES_OPTION)
                    {
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
                JOptionPane.showMessageDialog(null, "You already have 4 cards in your hand");
            }
        });
        splitPaneHorizontal.setTopComponent(TopPanel);
        splitPaneHorizontal.setBottomComponent(BottomPanel);
        frame.add(splitPaneHorizontal);
        frame.setSize(500, 300);
    }
    public String display() {
        return player.toString();
    }
}
