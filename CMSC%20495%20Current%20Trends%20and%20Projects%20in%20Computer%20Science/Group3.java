package group3;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class Group3 {
    Blackjack bj = new Blackjack();
    public Group3() {
        gui();
    }
    public static void main(String[] args) {
        new Group3();
    }
    public void gui() {
        JFrame frame = new JFrame("Card Games");
        frame.setVisible(true);
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JSplitPane topSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JPanel TitlePanel = new JPanel(new BorderLayout());
        JLabel jlTitle = new JLabel();
        jlTitle.setText("The P.A.C.E. Card Game Suite");
        Font font1 = new Font("Monotype Corsiva", Font.BOLD, 30);
        jlTitle.setFont(font1);
        TitlePanel.add(jlTitle);
        topSplit.setTopComponent(TitlePanel);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JPanel TopPanel = new JPanel(new GridLayout());
        TopPanel.setBorder(new EmptyBorder(5,5,5,5));
        JButton jbWar = new JButton("War!");
        JButton jbConcentration = new JButton("Concentration");
        JButton jbBlackJack = new JButton("Black Jack");
        jbWar.setBackground(new Color(175, 25, 50));
        jbConcentration.setBackground(new Color(175, 50, 130));
        jbBlackJack.setBackground(new Color(75, 100, 150));
        jbWar.addActionListener(ae -> {
            War.begin();
        });
        jbBlackJack.addActionListener(ae -> {
            bj.begin();
        });
        TopPanel.add(jbWar, BorderLayout.LINE_START);
        TopPanel.add(jbConcentration, BorderLayout.CENTER);
        TopPanel.add(jbBlackJack, BorderLayout.LINE_END);
        TopPanel.setPreferredSize(new Dimension(500, 95));
        JPanel BottomPanel = new JPanel(new GridLayout());
        BottomPanel.setBorder(new EmptyBorder(5,5,5,5));
        JButton jbThirtyOne = new JButton("Thirty-One");
        JButton jbIDoubtIt = new JButton("I Doubt It");
        jbThirtyOne.setBackground(new Color(100, 125, 175));
        jbIDoubtIt.setBackground(new Color(50, 150, 100));
        BottomPanel.add(jbThirtyOne, BorderLayout.LINE_START);
        BottomPanel.add(jbIDoubtIt, BorderLayout.LINE_END);
        splitPane.setTopComponent(TopPanel);
        splitPane.setBottomComponent(BottomPanel);
        topSplit.setBottomComponent(splitPane);
        frame.add(topSplit, BorderLayout.CENTER);
        frame.setSize(500, 300);
    }
}

class War {
    static ArrayList PlayerHand = new ArrayList();
    static ArrayList AIHand = new ArrayList();
    static ArrayList aWar = new ArrayList();
    static boolean bOver = false;
    static int Deck[];
    public static void begin() {
        Deck = new int[52];
        for (int iCount = 0; iCount < 52; iCount++)
        {
            Deck[iCount] = (iCount/4)+2;
        }
        Shuffle();
        Split();
        gui();
    }
    public static void gui() {
        JFrame frame = new JFrame("War!");
        frame.setVisible(true);
        frame.setSize(900, 600);
        JTextArea jta = new JTextArea(50, 50);
        JPanel panel = new JPanel(new BorderLayout());
        JPanel BottomPanel = new JPanel(new BorderLayout());
        BottomPanel.setBorder(new EmptyBorder(5,5,5,5));
        JButton jbOne = new JButton("One Battle");
        jbOne.addActionListener(ae -> {
            Battle((int)PlayerHand.get(0), (int)AIHand.get(0), aWar, jta);
        });
        JButton jbTen = new JButton("Ten Battles");
        jbTen.addActionListener(ae -> {
            for (int iCount = 0; iCount < 10; iCount++)
            {
                Battle((int)PlayerHand.get(0), (int)AIHand.get(0), aWar, jta);
                if (bOver == true)
                {
                    iCount = 10;
                }
            }
        });
        JButton jbAll = new JButton("All Battles");
        jbAll.addActionListener(ae -> {
            jta.setText(null);
            while (!PlayerHand.isEmpty() && !AIHand.isEmpty())
            {
                Battle((int)PlayerHand.get(0), (int)AIHand.get(0), aWar, jta);
            }
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
    public static void Shuffle() {
        Random rand = new Random();
        int iNew = 0;
        int iTemp = 0;
        for (int iCount = 0; iCount < 52; iCount++) 
        {
            iNew = rand.nextInt(52);
            iTemp = Deck[iCount];
            Deck[iCount] = Deck[iNew];
            Deck[iNew] = iTemp;
        }
    }
    public static void Split() {
        for (int iNum = 0; iNum < Deck.length; iNum++)
        {
            if (iNum%2 == 1)
            {
                PlayerHand.add(Deck[iNum]);
            }
            else
            {
                AIHand.add(Deck[iNum]);
            }
        }
    }
    public static void Battle(int iPlayer, int iAI, ArrayList<Integer> War, JTextArea jta) {
        if (iPlayer > iAI)
        {
            PlayerHand.add(iPlayer);
            PlayerHand.add(iAI);
            for (int iNum = 0; iNum < War.size(); iNum++)
            {
                PlayerHand.add(War.get(iNum));
            }
            War.clear();
            if (iPlayer < 11 && iAI < 11)
            {
                jta.append("Your " + iPlayer + " beats their " + iAI + "\n");
            }
            else if (iPlayer == 11)
            {
                jta.append("Your Jack beats their " + iAI + "\n");
            }
            else if (iPlayer == 12)
            {
                if (iAI < 11)
                {
                    jta.append("Your Queen beats their " + iAI + "\n");
                }
                else
                {
                    jta.append("Your Queen beats their Jack\n");
                }
            }
            else if (iPlayer == 13)
            {
                if (iAI < 11)
                {
                    jta.append("Your King beats their " + iAI + "\n");
                }
                else if (iAI == 11)
                {
                    jta.append("Your King beats their Jack\n");
                }
                else
                {
                    jta.append("Your King beats their Queen\n");
                }
            }
            else
            {
                if (iAI < 11)
                {
                    jta.append("Your Ace beats their " + iAI + "\n");
                }
                else if (iAI == 11)
                {
                    jta.append("Your Ace beats their Jack\n");
                }
                else if (iAI == 12)
                {
                    jta.append("Your Ace beats their Queen\n");
                }
                else
                {
                    jta.append("Your Ace beats their King\n");
                }
            }
            PlayerHand.remove(0);
            AIHand.remove(0);
            if (AIHand.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "You won the war!");
            }
        }
        else if (iAI > iPlayer)
        {
            AIHand.add(iAI);
            AIHand.add(iPlayer);
            for (int iNum = 0; iNum < War.size(); iNum++)
            {
                AIHand.add(War.get(iNum));
            }
            War.clear();
            if (iAI < 11 && iPlayer < 11)
            {
                jta.append("Their " + iAI + " beats your " + iPlayer + "\n");
            }
            else if (iAI == 11)
            {
                jta.append("Their Jack beats their " + iPlayer + "\n");
            }
            else if (iAI == 12)
            {
                if (iPlayer < 11)
                {
                    jta.append("Their Queen beats their " + iPlayer + "\n");
                }
                else
                {
                    jta.append("Their Queen beats your Jack\n");
                }
            }
            else if (iAI == 13)
            {
                if (iPlayer < 11)
                {
                    jta.append("Their King beats their " + iPlayer + "\n");
                }
                else if (iPlayer == 11)
                {
                    jta.append("Their King beats your Jack\n");
                }
                else
                {
                    jta.append("Their King beats your Queen\n");
                }
            }
            else
            {
                if (iPlayer < 11)
                {
                    jta.append("Their Ace beats your " + iPlayer + "\n");
                }
                else if (iPlayer == 11)
                {
                    jta.append("Their Ace beats your Jack\n");
                }
                else if (iPlayer == 12)
                {
                    jta.append("Their Ace beats your Queen\n");
                }
                else
                {
                    jta.append("Their Ace beats your King\n");
                }
            }
            PlayerHand.remove(0);
            AIHand.remove(0);
            if (PlayerHand.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "You lost the war.");
            }
        }
        else
        {
            if (PlayerHand.size() > 1)
            {
                War.add((int)PlayerHand.get(0));
                PlayerHand.remove(0);
            }
            if (PlayerHand.size() > 1)
            {
                War.add((int)PlayerHand.get(0));
                PlayerHand.remove(0);
            }
            if (PlayerHand.size() > 1)
            {
                War.add((int)PlayerHand.get(0));
                PlayerHand.remove(0);
            }
            if (AIHand.size() > 1)
            {
                War.add((int)AIHand.get(0));
                AIHand.remove(0);
            }
            if (AIHand.size() > 1)
            {
                War.add((int)AIHand.get(0));
                AIHand.remove(0);
            }
            if (AIHand.size() > 1)
            {
                War.add((int)AIHand.get(0));
                AIHand.remove(0);
            }
            Battle((int)PlayerHand.get(0), (int)AIHand.get(0), War, jta);
        }
    }
    public static void Display() {
        JOptionPane.showMessageDialog(null, "Player: " + PlayerHand.size() + "\nAI: " + AIHand.size());
    }
    public static void Directions() {
        JOptionPane.showMessageDialog(null, "The player with the higher card wins. \nIn the event of a tie, each player offers \nthe top 3 cards of their decks, and \nthen the player with the highest \n4th card wins the whole pile.");
    }
}

class BlackJack {
    
}