package group3;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class group3 {

    Blackjack bj = new Blackjack();
    ThirtyOne thirtyone = new ThirtyOne();
    War war = new War();
    Concentration concentration = new Concentration();
    HighLow highLow = new HighLow();

    public group3() {
        gui();
    }

    public static void main(String[] args) {
        new group3();
    }

    public void gui() {

        JFrame frame = new JFrame("Card Games");
        frame.setVisible(true);
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JSplitPane topSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JPanel TitlePanel = new JPanel(new BorderLayout());
        JLabel jlTitle = new JLabel("The P.A.C.E. Card Game Suite", JLabel.CENTER);
        Font font1 = new Font("Monotype Corsiva", Font.BOLD, 30);
        jlTitle.setFont(font1);
        TitlePanel.add(jlTitle);
        topSplit.setTopComponent(TitlePanel);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JPanel TopPanel = new JPanel(new GridLayout());
        TopPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JButton jbWar = new JButton("War!");
        jbWar.setFont(new Font("Courier Bold", Font.BOLD, 17));
        JButton jbConcentration = new JButton("Concentration");
        jbConcentration.setFont(new Font("Courier Bold", Font.BOLD, 17));
        JButton jbBlackJack = new JButton("Black Jack");
        jbBlackJack.setFont(new Font("Courier Bold", Font.BOLD, 17));
        jbWar.setBackground(new Color(192, 108, 108));
        jbConcentration.setBackground(new Color(243, 240, 132));
        jbBlackJack.setBackground(new Color(119, 126, 228));
        jbWar.addActionListener(ae -> {
            war = new War();
            war.begin();
        });
        jbBlackJack.addActionListener(ae -> {
            // bj = new Blackjack();
            // bj.begin();
        });
        jbConcentration.addActionListener(ae -> {
            concentration = new Concentration();
            concentration.createGUI();
        });
        TopPanel.add(jbWar, BorderLayout.LINE_START);
        TopPanel.add(jbConcentration, BorderLayout.CENTER);
        TopPanel.add(jbBlackJack, BorderLayout.LINE_END);
        TopPanel.setPreferredSize(new Dimension(500, 95));

        JPanel BottomPanel = new JPanel(new GridLayout());
        BottomPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JButton jbThirtyOne = new JButton("Thirty-One");
        jbThirtyOne.setFont(new Font("Courier Bold", Font.BOLD, 17));
        jbThirtyOne.addActionListener(ae -> {
            // thirtyone = new ThirtyOne();
            // thirtyone.begin();
        });
        JButton jbHighLow = new JButton("High or Low");
        jbHighLow.setFont(new Font("Courier Bold", Font.BOLD, 17));
        jbHighLow.addActionListener(ae -> {
            highLow = new HighLow();
            highLow.begin();
        });
        jbThirtyOne.setBackground(new Color(100, 193, 181));
        jbHighLow.setBackground(new Color(42, 148, 70));
        BottomPanel.add(jbThirtyOne, BorderLayout.LINE_START);
        BottomPanel.add(jbHighLow, BorderLayout.LINE_END);

        splitPane.setTopComponent(TopPanel);
        splitPane.setBottomComponent(BottomPanel);
        topSplit.setBottomComponent(splitPane);
        frame.add(topSplit, BorderLayout.CENTER);
        frame.setSize(500, 300);

    }
}
