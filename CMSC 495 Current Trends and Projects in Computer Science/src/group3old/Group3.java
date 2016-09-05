package group3old;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class Group3 {
    Blackjack bj = new Blackjack();
    ThirtyOne thirtyone = new ThirtyOne();
    War war = new War();
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
        jbWar.setBackground(new Color(157, 57, 57));
        jbConcentration.setBackground(new Color(143, 80, 162));
        jbBlackJack.setBackground(new Color(119, 126, 228));
        jbWar.addActionListener(ae -> {
            war = new War();
            war.begin();
        });
        jbBlackJack.addActionListener(ae -> {
            bj = new Blackjack();
            bj.begin();
        });
        TopPanel.add(jbWar, BorderLayout.LINE_START);
        TopPanel.add(jbConcentration, BorderLayout.CENTER);
        TopPanel.add(jbBlackJack, BorderLayout.LINE_END);
        TopPanel.setPreferredSize(new Dimension(500, 95));
        JPanel BottomPanel = new JPanel(new GridLayout());
        BottomPanel.setBorder(new EmptyBorder(5,5,5,5));
        JButton jbThirtyOne = new JButton("Thirty-One");
        jbThirtyOne.addActionListener(ae -> {
            thirtyone = new ThirtyOne();
            thirtyone.begin();
        });
        JButton jbIDoubtIt = new JButton("High or Low");
        jbThirtyOne.setBackground(new Color(100, 193, 181));
        jbIDoubtIt.setBackground(new Color(42, 148, 70));
        BottomPanel.add(jbThirtyOne, BorderLayout.LINE_START);
        BottomPanel.add(jbIDoubtIt, BorderLayout.LINE_END);
        splitPane.setTopComponent(TopPanel);
        splitPane.setBottomComponent(BottomPanel);
        topSplit.setBottomComponent(splitPane);
        frame.add(topSplit, BorderLayout.CENTER);
        frame.setSize(500, 300);
    }
}
