package highLow;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;

/**
 *
 * @author thegi
 */
public class HighLow extends JFrame implements ActionListener {

    URL imageURL = this.getClass().getClassLoader().getResource("cardBack.png");
    ImageIcon cardBacks = new ImageIcon(imageURL);
    static ImageIcon[] cards = new ImageIcon[52];
    static int[] refArray = new int [52];
    static int score = 0;
    static int index = 0;   
    JPanel panel = new JPanel(new GridLayout(0, 4, 8, 8));
    JLabel upCard = new JLabel(cards[0]);
    JLabel scoreCtLabel = new JLabel("", JLabel.LEFT);    
    
    public HighLow() {
        System.out.println(System.getProperty("java.class.path"));
        createGUI();
    }
    
    public void begin() {
        
        createGUI();
        createRefArray();
        
    }

    public static void main(String[] args) {
        
        new HighLow();
        
    }

    public static void createRefArray() {
        
        String refPath = cards[0].toString();
        String filePath = refPath.substring(0,refPath.lastIndexOf("/"));
        
        for (int ref2 = 0; ref2 < 52; ref2++) {
            
            for (int ref = 1; ref <= 52; ref ++) {
             
                if (cards[ref2].toString().equals(filePath + "/" + ref + ".png")) {
                 
                    if (ref == 1 || ref == 14 || ref == 27 || ref == 40) { 
                    
                        refArray[ref2] = 1;
                 
                    }
                
                    if (ref == 2 || ref == 15 || ref == 28 || ref == 41) { 

                        refArray[ref2] = 2;

                    }

                    if (ref == 3 || ref == 16 || ref == 29 || ref == 42) { 

                        refArray[ref2] = 3;

                    }

                    if (ref == 4 || ref == 17 || ref == 30 || ref == 43) { 

                        refArray[ref2] = 4;

                    }

                    if (ref == 5 || ref == 18 || ref == 31 || ref == 44) { 

                        refArray[ref2] = 5;

                    }     

                    if (ref == 6 || ref == 19 || ref == 32 || ref == 45) { 

                        refArray[ref2] = 6;

                    }                 

                    if (ref == 7 || ref == 20 || ref == 33 || ref == 46) { 

                        refArray[ref2] = 7;

                    } 

                    if (ref == 8 || ref == 21 || ref == 34 || ref == 47) { 

                        refArray[ref2] = 8;

                    }

                    if (ref == 9 || ref == 22 || ref == 35 || ref == 48) { 

                        refArray[ref2] = 9;

                    }

                    if (ref == 10 || ref == 23 || ref == 36 || ref == 49) { 

                        refArray[ref2] = 10;

                    }  

                    if (ref == 11 || ref == 24 || ref == 37 || ref == 50) { 

                        refArray[ref2] = 11;

                    }

                    if (ref == 12 || ref == 25 || ref == 38 || ref == 51) { 

                        refArray[ref2] = 12;

                    }

                    if (ref == 13 || ref == 26 || ref == 39 || ref == 52) { 

                        refArray[ref2] = 13;                

                    }
                }
            }
        }  
    }
    
    public void actionPerformed(ActionEvent e) {
        
        if (index < 51) {
                      
            System.out.println(e.getActionCommand());
            switch (e.getActionCommand()) {
                case "New Game":
                    break;
                case "Higher!":
                    index += 1;
                    upCard.setIcon(cards[index]);
                    if (refArray[index] == refArray[index - 1]) {
                    
                        System.out.println("Yes!");
                        score += 1;
                        scoreCtLabel.setText(String.valueOf(score));
                        
                    
                    }
                    else if (refArray[index] > refArray[index - 1]) {

                        System.out.println("Yes!");
                        score += 1;
                        scoreCtLabel.setText(String.valueOf(score));

                    }
                    else if (refArray[index] < refArray[index - 1]){

                        System.out.println("No!");

                    }
                    break;
                case "Lower!":
                    index += 1;
                    upCard.setIcon(cards[index]);
                    if (refArray[index] == refArray[index - 1]) {

                        System.out.println("Yes!");
                        score += 1;
                        scoreCtLabel.setText(String.valueOf(score));

                    }
                    else if (refArray[index] < refArray[index - 1]) {

                        System.out.println("Yes!");
                        score += 1;
                        scoreCtLabel.setText(String.valueOf(score));

                    }
                    else if (refArray[index] > refArray[index - 1]){

                        System.out.println("No!");

                    }
                    break;
            }                 
       }       
    }
    
    private void createGUI() {
        
        for (int j = 1; j <= 13; j++) {

            int position = 0;
            do {
                position = (int)(Math.random()*52);
            }
            while (cards[position] != null);
            
            URL cardURL = this.getClass().getClassLoader().getResource(j + ".png");
            cards[position] = new ImageIcon(cardURL);

        }  

        JFrame frame = new JFrame("High or Low!");
        frame.setVisible(true);
        frame.setSize(900, 900);
        frame.setResizable(false);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu ("Menu");
        menuBar.add(menu);
        JMenuItem menuItem = new JMenuItem("New Game");
        JMenuItem menuItem1 = new JMenuItem("High Scores");
        JMenuItem menuItem2 = new JMenuItem("Return to main menu");
        menuItem.addActionListener(this);
        menuItem1.addActionListener(this);
        menuItem2.addActionListener(this);
        menu.add(menuItem);
        menu.add(menuItem1);
        menu.add(menuItem2);
                
        JLabel backLabel = new JLabel(cardBacks);
        JLabel highLabel = new JLabel("HIGHER", JLabel.CENTER);
        highLabel.setFont(new Font("Courier Bold", Font.BOLD, 34));
        JLabel lowLabel = new JLabel("LOWER", JLabel.CENTER);
        lowLabel.setFont(new Font("Courier Bold", Font.BOLD, 24));
        JLabel orLabel = new JLabel("OR", JLabel.CENTER);
        orLabel.setFont(new Font("Courier Bold", Font.BOLD, 29));
        JLabel blankLabel = new JLabel(" ? ", JLabel.CENTER);
        blankLabel.setFont(new Font("Courier Bold", Font.BOLD, 29));
        JLabel blankLabel1 = new JLabel(" ");
        JLabel scoreLabel = new JLabel("SCORE: ", JLabel.RIGHT);
        scoreLabel.setFont(new Font("Courier Bold", Font.BOLD, 24));
        JButton highButton = new JButton("Higher!");
        highButton.setFont(new Font("Courier Bold", Font.BOLD, 24));
        highButton.addActionListener((ActionListener) this);
        JButton lowButton = new JButton("Lower!");
        lowButton.setFont(new Font("Courier Bold", Font.BOLD, 24));
        scoreCtLabel.setFont(new Font("Courier Bold", Font.BOLD, 24));
        scoreCtLabel.setText(String.valueOf(score));
        lowButton.addActionListener((ActionListener) this);
        upCard.setIcon(cards[0]);
        panel.add(highLabel);
        panel.add(orLabel);
        panel.add(lowLabel);
        panel.add(blankLabel);
        panel.add(lowButton);
        panel.add(backLabel);
        panel.add(upCard);
        panel.add(highButton);
        panel.add(blankLabel1);
        panel.add(scoreLabel);
        panel.add(scoreCtLabel);
        frame.setJMenuBar(menuBar);
        frame.add(panel);
        frame.pack();
    }
    
}
