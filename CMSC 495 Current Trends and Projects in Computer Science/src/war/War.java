// File: War.java
// Author: Alexander Burch
// Date: Aug 26, 2016
// Course: CMSC 495
// Assignment: TODO
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: TODO
package war;


import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Alexander Burch
 * @version 1.0
 * @since Aug 26, 2016
 */
public class War extends JPanel {
    ArrayList PlayerHand = new ArrayList();
    ArrayList AIHand = new ArrayList();
    ArrayList aWar = new ArrayList();
    int Deck[];
    boolean bOver = false;
    public War() {
        Deck = new int[52];
        for (int iCount = 0; iCount < 52; iCount++)
        {
            Deck[iCount] = (iCount/4)+2;
        }
        Shuffle();
        Split();
        gui();
    }
    
    public static void main(String[] args) {
        new War();
    }
    public void gui() {
        JFrame frame = new JFrame("War!");
        frame.setVisible(true);
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        pDisplay.add(jbDisplay);
        JSplitPane splitPaneHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPaneHorizontal.setTopComponent(TopPanel);
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setTopComponent(BottomPanel);
        split.setBottomComponent(pDisplay);
        splitPaneHorizontal.setBottomComponent(split);
        frame.add(splitPaneHorizontal, BorderLayout.CENTER);
        frame.pack();
    }
    public void Shuffle() {
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
    public void Split() {
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
    public void Battle(int iPlayer, int iAI, ArrayList<Integer> War, JTextArea jta) {
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
    public void Display() {
        JOptionPane.showMessageDialog(null, "Player: " + PlayerHand.size() + "\nAI: " + AIHand.size());
    }
}


