
package group3;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Alex
 */
public class ThirtyOne {
    private Deck deck;
    private Hand ai, player;
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
    private Card playerCard1, playerCard2, playerCard3, aiCard1, aiCard2, aiCard3;
    private ImageIcon playerCardImage1, playerCardImage2, playerCardImage3;
    private JButton jbPlayerCard1, jbPlayerCard2, jbPlayerCard3;
    private int player31;
    private int ai31;
    JFrame frame;
    boolean bSpade = true;
    boolean bHeart = true; 
    boolean bClub = true;
    boolean bDiamond = true;
    
    public ThirtyOne() {
        
        frame = new JFrame("Thirty-One");
        deck = new Deck();
        deck.shuffle();
        ai = new Hand();
        player = new Hand();
    }

    public void begin() {
        frame = new JFrame("Thirty-One");
        gui();
        deal();
    }

    public void deal() {
        if (deck.deckSize() < 8) {
            deck = new Deck();
            deck.shuffle();
            JOptionPane.showMessageDialog(null, "Reshuffling...");
        }
        ai = new Hand();
        player = new Hand();
        playerCard1 = deck.deal();
        playerCard2 = deck.deal();
        playerCard3 = deck.deal();
        player.addCard(playerCard1);
        player.addCard(playerCard2);
        player.addCard(playerCard3);
        playerCardImage1 = new ImageIcon(playerCard1.front);
        playerCardImage2 = new ImageIcon(playerCard2.front);
        playerCardImage3 = new ImageIcon(playerCard3.front);
        jbPlayerCard1.setIcon(playerCardImage1);
        jbPlayerCard2.setIcon(playerCardImage2);
        jbPlayerCard3.setIcon(playerCardImage3);
        aiCard1 = deck.deal();
        aiCard2 = deck.deal();
        aiCard3 = deck.deal();
        ai.addCard(aiCard1);
        ai.addCard(aiCard2);
        ai.addCard(aiCard3);
    }

    public void gui() {
        Random rand = new Random();
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
        menuItem.addActionListener(ae -> {
            frame.dispose();
            deck = new Deck();
            deck.shuffle();
            begin();
        });
        menuItemD.addActionListener(ae -> {
            Directions();
        });
        menuItem1.addActionListener(ae -> {
            // add in High Score code
        });
        menuItem2.addActionListener(ae -> {
            frame.dispose();
        });
        frame.setJMenuBar(menuBar);
        JSplitPane splitPaneHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JPanel TopPanel = new JPanel(new BorderLayout());
        JPanel BottomPanel = new JPanel(new BorderLayout());
        Card Card1 = deck.deal();
        Card Card2 = deck.deal();
        Card Card3 = deck.deal();
        ImageIcon back = new ImageIcon(Card1.back);
        jbPlayerCard1 = new JButton(back);
        jbPlayerCard2 = new JButton(back);
        jbPlayerCard3 = new JButton(back);
        BottomPanel.add(jbPlayerCard1, BorderLayout.LINE_START);
        BottomPanel.add(jbPlayerCard2, BorderLayout.CENTER);
        BottomPanel.add(jbPlayerCard3, BorderLayout.LINE_END);
        int iCard1 = values.get(Card1.getRank());
        int iCard2 = values.get(Card2.getRank());
        int iCard3 = values.get(Card3.getRank());
        ImageIcon CenterCard1 = new ImageIcon(Card1.front);
        ImageIcon CenterCard2 = new ImageIcon(Card2.front);
        ImageIcon CenterCard3 = new ImageIcon(Card3.front);
        JButton jbCard1 = new JButton(CenterCard1);
        JButton jbCard2 = new JButton(CenterCard2);
        JButton jbCard3 = new JButton(CenterCard3);
        if ((rand.nextInt(2) + 1) == 1) {
            TopPanel.add(jbCard1, BorderLayout.LINE_START);
            TopPanel.add(jbCard2, BorderLayout.CENTER);
            TopPanel.add(jbCard3, BorderLayout.LINE_END);
        } // player goes first
        else {
            if (iCard1 > iCard2) {
                if (iCard1 > iCard3) {
                    ai.addCard(Card1);
                    TopPanel.add(jbCard2, BorderLayout.LINE_START);
                    TopPanel.add(jbCard3, BorderLayout.LINE_END);
                } else {
                    ai.addCard(Card3);
                    TopPanel.add(jbCard1, BorderLayout.LINE_START);
                    TopPanel.add(jbCard2, BorderLayout.LINE_END);
                }
            } else {
                ai.addCard(Card2);
                TopPanel.add(jbCard1, BorderLayout.LINE_START);
                TopPanel.add(jbCard3, BorderLayout.LINE_END);
            }
        } // ai goes first
        jbCard1.addActionListener(ae -> {
            if (player.handSize() < 4) {
                player.addCard(Card1);
                TopPanel.remove(jbCard1);
                player31 = 31 - player.total();
                player31 = Math.abs(player31);
                ai31 = 31 - ai.total();
                ai31 = Math.abs(ai31);
            } else {
                JOptionPane.showMessageDialog(null, "You already have 4 cards in your hand");
            }
        });
        jbCard2.addActionListener(ae -> {
            if (player.handSize() < 4) {
                player.addCard(Card2);
                TopPanel.remove(jbCard2);
                player31 = 31 - player.total();
                player31 = Math.abs(player31);
                ai31 = 31 - ai.total();
                ai31 = Math.abs(ai31);
            } else {
                JOptionPane.showMessageDialog(null, "You already have 4 cards in your hand");
            }
        });
        jbCard3.addActionListener(ae -> {
            if (player.handSize() < 4) {
                player.addCard(Card3);
                TopPanel.remove(jbCard3);
                player31 = 31 - player.total();
                player31 = Math.abs(player31);
                ai31 = 31 - ai.total();
                ai31 = Math.abs(ai31);
            } else {
                JOptionPane.showMessageDialog(null, "You already have 4 cards in your hand");
            }
        });
        JSplitPane split1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JSplitPane split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JSplitPane splitBottom = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JPanel dialPanel = new JPanel(new GridLayout(0,3));
        JLabel jlSpade = new JLabel("Spade");
        JRadioButton spadePos = new JRadioButton("Positive");
        spadePos.setSelected(true);
        JRadioButton spadeNeg = new JRadioButton("Negative");
        spadeNeg.setSelected(true);
        ButtonGroup spadeGroup = new ButtonGroup();
        spadeGroup.add(spadePos);
        spadeGroup.add(spadeNeg);
        JLabel jlHeart = new JLabel("Heart");
        JRadioButton heartPos = new JRadioButton("Positive");
        heartPos.setSelected(true);
        JRadioButton heartNeg = new JRadioButton("Negative");
        heartNeg.setSelected(true);
        ButtonGroup heartGroup = new ButtonGroup();
        heartGroup.add(heartPos);
        heartGroup.add(heartNeg);
        JLabel jlClub = new JLabel("Club");
        JRadioButton clubPos = new JRadioButton("Positive");
        clubPos.setSelected(true);
        JRadioButton clubNeg = new JRadioButton("Negative");
        clubNeg.setSelected(true);
        ButtonGroup clubGroup = new ButtonGroup();
        clubGroup.add(clubPos);
        clubGroup.add(clubNeg);
        JLabel jlDiamond = new JLabel("Diamond");
        JRadioButton diamondPos = new JRadioButton("Positive");
        diamondPos.setSelected(true);
        JRadioButton diamondNeg = new JRadioButton("Negative");
        diamondNeg.setSelected(true);
        ButtonGroup diamondGroup = new ButtonGroup();
        diamondGroup.add(diamondPos);
        diamondGroup.add(diamondNeg);
        JButton jbCalculate = new JButton ("Calculate");
        dialPanel.add(jlSpade);
        dialPanel.add(spadePos);
        dialPanel.add(spadeNeg);
        dialPanel.add(jlHeart);
        dialPanel.add(heartPos);
        dialPanel.add(heartNeg);
        dialPanel.add(jlClub);
        dialPanel.add(clubPos);
        dialPanel.add(clubNeg);
        dialPanel.add(jlDiamond);
        dialPanel.add(diamondPos);
        dialPanel.add(diamondNeg);
        dialPanel.add(jbCalculate);
        if (spadePos.isSelected())
        {
            bSpade = true;
        }
        else
        {
            bSpade = false;
        }
        if (heartPos.isSelected())
        {
            bHeart = true;
        }
        else
        {
            bHeart = false;
        }
        if (clubPos.isSelected())
        {
            bClub = true;
        }
        else
        {
            bClub = false;
        }
        if (diamondPos.isSelected())
        {
            bDiamond = true;
        }
        else
        {
            bDiamond = false;
        }
        jbCalculate.addActionListener(ae -> {
            results();
        });
        JLabel jlCenter = new JLabel("Center Cards");
        JLabel jlPlayer = new JLabel("Player Hand");
        JPanel centerPanel = new JPanel();
        JPanel playerPanel = new JPanel();
        centerPanel.add(jlCenter);
        playerPanel.add(jlPlayer);
        split1.setTopComponent(centerPanel);
        split1.setBottomComponent(TopPanel);
        split2.setTopComponent(playerPanel);
        split2.setBottomComponent(BottomPanel);
        splitBottom.setTopComponent(split2);
        splitBottom.setBottomComponent(dialPanel);
        splitPaneHorizontal.setTopComponent(split1);
        splitPaneHorizontal.setBottomComponent(splitBottom);
        frame.add(splitPaneHorizontal);
        frame.pack();
    }

    public void Directions() {
        JOptionPane.showMessageDialog(null,
                "The goal of the game is to get the closest to 31. Whichever player is closer wins. Click one of the center cards to add it to your hand.");
    }
    
    public void results() {
        int iSum = 0;
        Card temp1 = player.getCard(0);
        Card temp2 = player.getCard(1);
        Card temp3 = player.getCard(2);
        Card temp4 = player.getCard(3);
        Map<Rank, Integer> values = new HashMap<>();
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
        if (bSpade == true)
        {
            if (temp1.getSuit() == Suit.SPADE)
            {
                iSum += values.get(temp1.getRank());
            }
            if (temp2.getSuit() == Suit.SPADE)
            {
                iSum += values.get(temp2.getRank());
            }
            if (temp3.getSuit() == Suit.SPADE)
            {
                iSum += values.get(temp3.getRank());
            }
            if (temp4.getSuit() == Suit.SPADE)
            {
                iSum += values.get(temp4.getRank());
            }
        }
        else
        {
            if (temp1.getSuit() == Suit.SPADE)
            {
                iSum -= values.get(temp1.getRank());
            }
            if (temp2.getSuit() == Suit.SPADE)
            {
                iSum -= values.get(temp2.getRank());
            }
            if (temp3.getSuit() == Suit.SPADE)
            {
                iSum -= values.get(temp3.getRank());
            }
            if (temp4.getSuit() == Suit.SPADE)
            {
                iSum -= values.get(temp4.getRank());
            }
        }
        if (bHeart == true)
        {
            if (temp1.getSuit() == Suit.HEART)
            {
                iSum += values.get(temp1.getRank());
            }
            if (temp2.getSuit() == Suit.HEART)
            {
                iSum += values.get(temp2.getRank());
            }
            if (temp3.getSuit() == Suit.HEART)
            {
                iSum += values.get(temp3.getRank());
            }
            if (temp4.getSuit() == Suit.HEART)
            {
                iSum += values.get(temp4.getRank());
            }
        }
        else
        {
            if (temp1.getSuit() == Suit.HEART)
            {
                iSum -= values.get(temp1.getRank());
            }
            if (temp2.getSuit() == Suit.HEART)
            {
                iSum -= values.get(temp2.getRank());
            }
            if (temp3.getSuit() == Suit.HEART)
            {
                iSum -= values.get(temp3.getRank());
            }
            if (temp4.getSuit() == Suit.HEART)
            {
                iSum -= values.get(temp4.getRank());
            }
        }
        if (bClub == true)
        {
            if (temp1.getSuit() == Suit.CLUB)
            {
                iSum += values.get(temp1.getRank());
            }
            if (temp2.getSuit() == Suit.CLUB)
            {
                iSum += values.get(temp2.getRank());
            }
            if (temp3.getSuit() == Suit.CLUB)
            {
                iSum += values.get(temp3.getRank());
            }
            if (temp4.getSuit() == Suit.CLUB)
            {
                iSum += values.get(temp4.getRank());
            }
        }
        else
        {
            if (temp1.getSuit() == Suit.CLUB)
            {
                iSum -= values.get(temp1.getRank());
            }
            if (temp2.getSuit() == Suit.CLUB)
            {
                iSum -= values.get(temp2.getRank());
            }
            if (temp3.getSuit() == Suit.CLUB)
            {
                iSum -= values.get(temp3.getRank());
            }
            if (temp4.getSuit() == Suit.CLUB)
            {
                iSum -= values.get(temp4.getRank());
            }
        }
        if (bDiamond == true)
        {
            if (temp1.getSuit() == Suit.DIAMOND)
            {
                iSum += values.get(temp1.getRank());
            }
            if (temp2.getSuit() == Suit.DIAMOND)
            {
                iSum += values.get(temp2.getRank());
            }
            if (temp3.getSuit() == Suit.DIAMOND)
            {
                iSum += values.get(temp3.getRank());
            }
            if (temp4.getSuit() == Suit.DIAMOND)
            {
                iSum += values.get(temp4.getRank());
            }
        }
        else
        {
            if (temp1.getSuit() == Suit.DIAMOND)
            {
                iSum -= values.get(temp1.getRank());
            }
            if (temp2.getSuit() == Suit.DIAMOND)
            {
                iSum -= values.get(temp2.getRank());
            }
            if (temp3.getSuit() == Suit.DIAMOND)
            {
                iSum -= values.get(temp3.getRank());
            }
            if (temp4.getSuit() == Suit.DIAMOND)
            {
                iSum -= values.get(temp4.getRank());
            }
        }
        if (iSum > ai.total()) {
                    Object[] options = { "Yes", "No" };
                    int n = JOptionPane.showOptionDialog(frame,
                            ("You win " + player.total() + " to " + ai.total() + "!\nWould you like to play again?"),
                            "You Win!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                            options[1]);
                    if (n == JOptionPane.YES_OPTION) {
                        frame.dispose();
                        deck = new Deck();
                        deck.shuffle();
                        begin();
                    } else {
                        frame.dispose();
                    }
                } else if (iSum == ai.total()) {
                    Object[] options = { "Yes", "No" };
                    int n = JOptionPane.showOptionDialog(frame,
                            ("You tied at " + player.total() + " to " + ai.total()
                                    + "!\nWould you like to play again?"),
                            "You Tie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                            options[1]);
                    if (n == JOptionPane.YES_OPTION) {
                        frame.dispose();
                        deck = new Deck();
                        deck.shuffle();
                        begin();
                    } else {
                        frame.dispose();
                    }
                } else {
                    Object[] options = { "Yes", "No" };
                    int n = JOptionPane.showOptionDialog(frame,
                            ("You lose " + player.total() + " to " + ai.total() + "!\nWould you like to play again?"),
                            "You Lose", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                            options[1]);
                    if (n == JOptionPane.YES_OPTION) {
                        frame.dispose();
                        deck = new Deck();
                        deck.shuffle();
                        begin();
                    } else {
                        frame.dispose();
                    }
                }
    }
}