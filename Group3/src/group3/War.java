package group3;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class War {
    
    URL imageURL = this.getClass().getClassLoader().getResource("cardBack.png");
    ImageIcon cardBacks = new ImageIcon(imageURL);
    static ImageIcon[] cards = new ImageIcon[52];
    static int[] refArray = new int [52];
//    private ArrayList<Integer> playerHand = new ArrayList<>();
//    private ArrayList<Integer> aiHand = new ArrayList<>();
    private ArrayList<Integer> warPlayer = new ArrayList<>();
    private ArrayList<Integer> warAI = new ArrayList<>();
//    private ArrayList<ImageIcon> playerCards = new ArrayList<>();
//    private ArrayList<ImageIcon> AICards = new ArrayList<>();
    private ArrayList<ImageIcon> warPlayerCards = new ArrayList<>();
    private ArrayList<ImageIcon> warAICards = new ArrayList<>();
//    private ArrayList<Integer> playerRefArray = new ArrayList<Integer>();
//    private ArrayList<Integer> AIRefArray = new ArrayList<Integer>();
    
//    private Stack playerStack = new Stack();
//    private Stack aiStack = new Stack();
    private Deck deck;
    private int playCount = 0;
    private int warIndex = 0;
    //private Hand ai, player, war;
    private Card playerCard, aiCard;
    JTextArea jta;
    JFrame frame = new JFrame("War!");
    JFrame warFrame = new JFrame("It's War!");
    JPanel panel = new JPanel(new BorderLayout());
    JPanel gameBoard = new JPanel(new GridLayout(0,3,2,2));
    JPanel warPanel = new JPanel(new GridLayout(0,8,15,0));
    JLabel warLabel1 = new JLabel(" ");
    JLabel warLabel2 = new JLabel(" ");
    JLabel warLabel3 = new JLabel(" ");
    JLabel warLabel4 = new JLabel(" ");
    JLabel playerDiscardLabel = new JLabel("  Player Cards   ");
    JLabel aiDiscardLabel = new JLabel("  Computer Cards  ");
    JLabel blankLabel1 = new JLabel("  ");
    JLabel blankLabel2 = new JLabel("  ");
    JLabel blankLabel3 = new JLabel("  ");
    boolean bNew;
    
//    Replaced with createRefArray method.  Using graphics, value of the cards must be determined by preset name.
//    private static Map<Rank, Integer> values = new HashMap<>();
//    static {
//        values.put(Rank.ACE, 11);
//        values.put(Rank.TWO, 2);
//        values.put(Rank.THREE, 3);
//        values.put(Rank.FOUR, 4);
//        values.put(Rank.FIVE, 5);
//        values.put(Rank.SIX, 6);
//        values.put(Rank.SEVEN, 7);
//        values.put(Rank.EIGHT, 8);
//        values.put(Rank.NINE, 9);
//        values.put(Rank.TEN, 10);
//        values.put(Rank.JACK, 10);
//        values.put(Rank.QUEEN, 10);
//        values.put(Rank.KING, 10);
//    }
    
    public War() {

    }
    
    public void begin() {
                deck = new Deck();
        deck.shuffle();
//        ai = new Hand();
//        player = new Hand();
//        war = new Hand();
        jta = new JTextArea(50,50);
        bNew = false;
//        player.returnCards();
//        ai.returnCards();
        //Split();
        gui();
        playCount = 0;
        
    }
    
    public static void main(String[] args) {
        
        new War();
        
    }
    
    public void Split() {
        
        
    }
    public static void createRefArray() {
        
//        String refPath = cards[0].toString();
//        String filePath = refPath.substring(0,refPath.lastIndexOf("/"));
//        
//        for (int ref2 = 0; ref2 < 52; ref2++) {
//            
//            for (int ref = 1; ref <= 52; ref ++) {
//             
//                if (cards[ref2].toString().equals(filePath + "/" + ref + ".png")) {
//                 
//                    if (ref == 1 || ref == 14 || ref == 27 || ref == 40) { 
//                    
//                        refArray[ref2] = 1;
//                 
//                    }
//                
//                    if (ref == 2 || ref == 15 || ref == 28 || ref == 41) { 
//
//                        refArray[ref2] = 2;
//
//                    }
//
//                    if (ref == 3 || ref == 16 || ref == 29 || ref == 42) { 
//
//                        refArray[ref2] = 3;
//
//                    }
//
//                    if (ref == 4 || ref == 17 || ref == 30 || ref == 43) { 
//
//                        refArray[ref2] = 4;
//
//                    }
//
//                    if (ref == 5 || ref == 18 || ref == 31 || ref == 44) { 
//
//                        refArray[ref2] = 5;
//
//                    }     
//
//                    if (ref == 6 || ref == 19 || ref == 32 || ref == 45) { 
//
//                        refArray[ref2] = 6;
//
//                    }                 
//
//                    if (ref == 7 || ref == 20 || ref == 33 || ref == 46) { 
//
//                        refArray[ref2] = 7;
//
//                    } 
//
//                    if (ref == 8 || ref == 21 || ref == 34 || ref == 47) { 
//
//                        refArray[ref2] = 8;
//
//                    }
//
//                    if (ref == 9 || ref == 22 || ref == 35 || ref == 48) { 
//
//                        refArray[ref2] = 9;
//
//                    }
//
//                    if (ref == 10 || ref == 23 || ref == 36 || ref == 49) { 
//
//                        refArray[ref2] = 10;
//
//                    }  
//
//                    if (ref == 11 || ref == 24 || ref == 37 || ref == 50) { 
//
//                        refArray[ref2] = 11;
//
//                    }
//
//                    if (ref == 12 || ref == 25 || ref == 38 || ref == 51) { 
//
//                        refArray[ref2] = 12;
//
//                    }
//
//                    if (ref == 13 || ref == 26 || ref == 39 || ref == 52) { 
//
//                        refArray[ref2] = 13;                
//
//                    }
//                }
//            }
//        }  
    }
    
    public void gui() {
        
        frame.setVisible(true);
        frame.setSize(900, 900);
        frame.setResizable(false);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu ("Menu");
        menuBar.add(menu);
        JMenuItem menuItem = new JMenuItem("New Game");
        JMenuItem menuItemD = new JMenuItem("How to Play");
        JMenuItem menuItem1 = new JMenuItem("High Scores");
        JMenuItem menuItem2 = new JMenuItem("Return to main menu");
//        menuItem.addActionListener(this);
//        menuItem1.addActionListener(this);
//        menuItem2.addActionListener(this);
        menu.add(menuItem);
        menu.add(menuItemD);
        menu.add(menuItem1);
        menu.add(menuItem2);
//        frame.setVisible(true);
//        frame.setSize(900, 600);
//        JPanel panel = new JPanel(new BorderLayout());
//        JPanel BottomPanel = new JPanel(new BorderLayout());
//        BottomPanel.setBorder(new EmptyBorder(5,5,5,5));
        frame.setJMenuBar(menuBar);
        JButton jbOne = new JButton("Battle");
        jbOne.addActionListener(ae -> {
            Battle();
            playCount++;
        });
//        JButton jbTen = new JButton("Ten Battles");
//        jbTen.addActionListener(ae -> {
//            for (int iCount = 0; iCount < 10; iCount++)
//            {
//                Battle();
//                if (player.handSize() == 0 || ai.handSize() == 0)
//                {
//                    iCount = 10;
//                }
//            }
//        });
//        JButton jbAll = new JButton("All Battles");
//        jbAll.addActionListener(ae -> {
//            while (bNew == false)
//            {
//                Battle();
//            }
//            jta.setText(null);
//            bNew = false;
//        });
        //JScrollPane TopPanel = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        
//        BottomPanel.add(jbTen, BorderLayout.CENTER);
//        BottomPanel.add(jbAll, BorderLayout.LINE_END);
        JPanel pDisplay = new JPanel(new BorderLayout());
        //JButton jbDisplay = new JButton ("Display");
        //jbDisplay.addActionListener(ae -> {        
//            Display();
        //});
        //JButton jbDirections = new JButton ("Directions");
        menuItemD.addActionListener(ae -> {
            Directions();
        });
        //panel.add(jbDirections, BorderLayout.NORTH);
                JLabel backLabel = new JLabel(cardBacks);
        JLabel backLabel2 = new JLabel(cardBacks);
        
        JLabel blankLabel2 = new JLabel(" ");
        JLabel blankLabel3 = new JLabel(" ");
        JLabel blankLabel4 = new JLabel(" ");
        JLabel blankLabel5 = new JLabel(" ");
        JLabel topLabel = new JLabel("      Player Cards                                          "
                + "                                                                         Computer Cards   ");
//        panel.add(blankLabel1);
//        panel.add(blankLabel2);
//        panel.add(blankLabel3);
        panel.add(topLabel, BorderLayout.NORTH);
      panel.add(backLabel, BorderLayout.WEST);
//      panel.add(blankLabel1, BorderLayout.CENTER);
      panel.add(backLabel2, BorderLayout.EAST);
      gameBoard.add(playerDiscardLabel);
      gameBoard.add(blankLabel1);
      gameBoard.add(aiDiscardLabel);
        panel.add(gameBoard, BorderLayout.CENTER);
//      panel.add(blankLabel4);
//        panel.add(blankLabel5);
      panel.add(jbOne, BorderLayout.SOUTH);
        warFrame.add(warPanel);
        frame.add(panel);
        
        frame.pack();
    }

    public void Battle() {
//////        playerCard = player.removeCard();
//        aiCard = ai.removeCard();

        
        
        JLabel warPlayerLabel1 = new JLabel("",SwingConstants.CENTER);
        JLabel warPlayerLabel2 = new JLabel("",SwingConstants.CENTER);
        JLabel warPlayerLabel3 = new JLabel("",SwingConstants.CENTER);
        JLabel breakPlayerLabel = new JLabel("",SwingConstants.CENTER);
        JLabel warAILabel1 = new JLabel("",SwingConstants.CENTER);
        JLabel warAILabel2 = new JLabel("",SwingConstants.CENTER);
        JLabel warAILabel3 = new JLabel("",SwingConstants.CENTER);
        JLabel breakAILabel = new JLabel("",SwingConstants.CENTER);
        
        
        ImageIcon tempPlayerCard = deck.getPlayerCards(playCount);
        ImageIcon tempAICard = deck.getAICards(playCount);
       System.out.println(deck.getPlayerHand(playCount));
       System.out.println(deck.getAIHand(playCount));
//        System.out.println(playerHand.get(playCount));
//        System.out.println(aiHand.get(playCount));
        
        playerDiscardLabel.setIcon(tempPlayerCard);
        aiDiscardLabel.setIcon(tempAICard);
      
        gameBoard.add(aiDiscardLabel);
//        panel.add(playerDiscardLabel, BorderLayout.CENTER);
//        panel.add(aiDiscardLabel, BorderLayout.CENTER);
        
        if (deck.getPlayerHand(playCount) > deck.getAIHand(playCount))
        {  
            
            deck.addPlayerCards(tempPlayerCard);
            deck.addPlayerCards(tempAICard);
            deck.addPlayerHand(deck.getPlayerHand(playCount));
            deck.addPlayerHand(deck.getAIHand(playCount));
            deck.removePlayerHand(playCount);
            deck.removePlayerCards(playCount);
            deck.removeAICards(playCount);
            deck.removeAIHand(playCount);

            blankLabel1.setText("You won this fight!");

            
//            if (ai.handSize() == 0)
//            {
//                Object[] options = {"Yes", "No"};
//                int n = JOptionPane.showOptionDialog(frame, "You won the war.\nDo you want to play again?", "You Won", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
//                if (n == JOptionPane.YES_OPTION)
//                {
//                    bNew = true;
//                    player.returnCards();
//                    ai.returnCards();
//                    frame.dispose();
//                    deck = new Deck();
//                    deck.shuffle();
//                    begin();
//                }
//                else
//                {
//                    frame.dispose();
//                }
//            }
        }
        else if (deck.getPlayerHand(playCount) < deck.getAIHand(playCount))
        {
            
            deck.addAICards(tempPlayerCard);
            deck.addAICards(tempAICard);
            deck.addAIHand(deck.getPlayerHand(playCount));
            deck.addAIHand(deck.getAIHand(playCount));
            deck.removePlayerHand(playCount);
            deck.removePlayerCards(playCount);
            deck.removeAICards(playCount);
            deck.removeAIHand(playCount);
            
            blankLabel1.setText("You lost this fight!");
            warLabel1.setText("YOU ");
            warLabel2.setText("LOST ");
            warLabel3.setText("THIS ");
            warLabel4.setText("BATTLE!");
//            if (playe r.handSize() == 0)
//            {
//                Object[] options = {"Yes", "No"};
//                int n = JOptionPane.showOptionDialog(frame, "You lost the war.\nDo you want to play again?", "You Lost", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
//                if (n == JOptionPane.YES_OPTION)
//                {
//                    bNew = true;
//                    player.returnCards();
//                    ai.returnCards();
//                    frame.dispose();
//                    deck = new Deck();
//                    deck.shuffle();
//                    begin();
//                }
//                else
//                {
//                    frame.dispose();
//                }
//            }
        }
        else
        {   
            blankLabel1.setText("WAR!");
            warLabel1.setText("PREPARE ");
            warLabel2.setText("FOR ");
            warLabel3.setText("ANOTHER ");
            warLabel4.setText("BATTLE!");
            JLabel p1Label = new JLabel("Player Card 1", SwingConstants.CENTER);
            JLabel p2Label = new JLabel("Player Card 2", SwingConstants.CENTER);
            JLabel p3Label = new JLabel("Player Card 3", SwingConstants.CENTER);
            JLabel p4Label = new JLabel("Player Card 4", SwingConstants.CENTER);
            JLabel a1Label = new JLabel("AI Card 1", SwingConstants.CENTER);
            JLabel a2Label = new JLabel("AI Card 2", SwingConstants.CENTER);
            JLabel a3Label = new JLabel("AI Card 3", SwingConstants.CENTER);
            JLabel a4Label = new JLabel("AI Card 4", SwingConstants.CENTER);
            p1Label.setVerticalAlignment(SwingConstants.BOTTOM);
            p2Label.setVerticalAlignment(SwingConstants.BOTTOM);
            p3Label.setVerticalAlignment(SwingConstants.BOTTOM);
            p4Label.setVerticalAlignment(SwingConstants.BOTTOM);
            a1Label.setVerticalAlignment(SwingConstants.BOTTOM);
            a2Label.setVerticalAlignment(SwingConstants.BOTTOM);
            a3Label.setVerticalAlignment(SwingConstants.BOTTOM);
            a4Label.setVerticalAlignment(SwingConstants.BOTTOM);
            warPlayerLabel1.setIcon(deck.getPlayerCards(playCount + 1));
            warPlayerLabel2.setIcon(deck.getPlayerCards(playCount + 2));
            warPlayerLabel3.setIcon(deck.getPlayerCards(playCount + 3));
            breakPlayerLabel.setIcon(deck.getPlayerCards(playCount + 4));
            warAILabel1.setIcon(deck.getAICards(playCount + 1));
            warAILabel2.setIcon(deck.getAICards(playCount + 2));
            warAILabel3.setIcon(deck.getAICards(playCount + 3));
            breakAILabel.setIcon(deck.getAICards(playCount + 4));
//            warIndex = playCount;
//            playCount = playCount + 4;
            warPanel.add(p1Label);
            warPanel.add(p2Label);
            warPanel.add(p3Label);
            warPanel.add(p4Label);
            warPanel.add(a4Label);
            warPanel.add(a3Label);
            warPanel.add(a2Label);
            warPanel.add(a1Label);
            warPanel.add(warPlayerLabel1);
            warPanel.add(warPlayerLabel2);
            warPanel.add(warPlayerLabel3);
            warPanel.add(breakPlayerLabel);
            warPanel.add(breakAILabel);
            warPanel.add(warAILabel3);
            warPanel.add(warAILabel2);
            warPanel.add(warAILabel1);                                    
            warPanel.add(blankLabel2);
            warPanel.add(blankLabel3);
            warPanel.add(warLabel1);
            warPanel.add(warLabel2);
            warPanel.add(warLabel3);
            warPanel.add(warLabel4);
            warFrame.add(warPanel);
            warFrame.pack();
            warFrame.setVisible(true);
            warFrame.setFocusable(true);
            
            if(deck.getPlayerHand(playCount + 4) > deck.getAIHand(playCount+4)) {
              System.out.println("hi " + deck.getPlayerHand(playCount + 4));
              System.out.println("hi " + deck.getAIHand(playCount+4));
                warLabel1.setText("YOU ");
                warLabel2.setText("WON ");
                warLabel3.setText("THIS ");
                warLabel4.setText("BATTLE!");
            
                deck.addPlayerCards(deck.getPlayerCards(playCount));
                deck.addPlayerCards(deck.getPlayerCards(playCount + 1));
                deck.addPlayerCards(deck.getPlayerCards(playCount + 2));
                deck.addPlayerCards(deck.getPlayerCards(playCount + 3));
                deck.addPlayerCards(deck.getPlayerCards(playCount + 4));
                deck.addPlayerCards(deck.getAICards(playCount));
                deck.addPlayerCards(deck.getAICards(playCount + 1));
                deck.addPlayerCards(deck.getAICards(playCount + 2));
                deck.addPlayerCards(deck.getAICards(playCount + 3));
                deck.addPlayerCards(deck.getAICards(playCount + 4));
                deck.addPlayerHand(deck.getPlayerHand(playCount));
                deck.addPlayerHand(deck.getPlayerHand(playCount + 1));
                deck.addPlayerHand(deck.getPlayerHand(playCount + 2));
                deck.addPlayerHand(deck.getPlayerHand(playCount + 3));
                deck.addPlayerHand(deck.getPlayerHand(playCount + 4));
                deck.addPlayerHand(deck.getAIHand(playCount));
                deck.addPlayerHand(deck.getAIHand(playCount + 1));
                deck.addPlayerHand(deck.getAIHand(playCount + 2));
                deck.addPlayerHand(deck.getAIHand(playCount + 3));
                deck.addPlayerHand(deck.getAIHand(playCount + 4));
                deck.removePlayerHand(playCount);
                deck.removePlayerHand(playCount + 1);
                deck.removePlayerHand(playCount + 2);
                deck.removePlayerHand(playCount + 3);
                deck.removePlayerHand(playCount + 4);
                deck.removePlayerCards(playCount);
                deck.removePlayerCards(playCount + 1);
                deck.removePlayerCards(playCount + 2);
                deck.removePlayerCards(playCount + 3);
                deck.removePlayerCards(playCount + 4);
                deck.removeAICards(playCount);
                deck.removeAICards(playCount + 1);
                deck.removeAICards(playCount + 2);
                deck.removeAICards(playCount + 3);
                deck.removeAICards(playCount + 4);
                deck.removeAIHand(playCount);
                deck.removeAIHand(playCount + 1);
                deck.removeAIHand(playCount + 2);
                deck.removeAIHand(playCount + 3);
                deck.removeAIHand(playCount + 4);
                
            }
            //Battle();
//            warPlayerLabel1.setIcon();
//            warPlayerLabel2.setIcon(playerCards.remove(playCount + 2));
//            warPlayerLabel3.setIcon(playerCards.remove(playCount + 3));
//            breakPlayerLabel.setIcon(playerCards.remove(playCount +4));
//            warAILabel1.setIcon(AICards.remove(playCount + 1));
//            warAILabel2.setIcon(AICards.remove(playCount + 2));
//            warAILabel3.setIcon(AICards.remove(playCount + 3));
//            breakAILabel.setIcon(AICards.remove(playCount + 4));
            
//            playerCards.add(tempPlayerCard);
//            playerHand.add(playerHand.remove(playCount));
//           
            
        }
        
    }
//    public void Display() {
//        jta.append("[" + player.handSize() + " vs " + ai.handSize() + "]\n");
//    }
    public void Directions() {
        JOptionPane.showMessageDialog(null, "The player with the higher card wins. \nIn the event of a tie, each player offers \nthe top 3 cards of their decks, and \nthen the player with the highest \n4th card wins the whole pile.");
    }
}