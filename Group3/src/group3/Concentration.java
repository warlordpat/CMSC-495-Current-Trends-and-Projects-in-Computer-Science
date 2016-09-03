package group3;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Arrays;
import javax.swing.*;

/**
 *
 * @author thegi
 */
public class Concentration extends JFrame implements ActionListener {

    URL imageURL = this.getClass().getClassLoader().getResource("cardBack.png");
    URL cardURL = this.getClass().getClassLoader().getResource("card_matched.png");
    ImageIcon cardBacks = new ImageIcon(imageURL);
    ImageIcon[] cards = new ImageIcon[26];
    int test = 0;
    int selectedCard = 0;
    int secondSelectedCard = 0;
    int index = 0;
    Timer t = new Timer (2000,this); 
    JButton btn1 = new JButton(cardBacks);
    JButton btn2 = new JButton(cardBacks);
    JButton btn3 = new JButton(cardBacks);
    JButton btn4 = new JButton(cardBacks);
    JButton btn5 = new JButton(cardBacks);
    JButton btn6 = new JButton(cardBacks);
    JButton btn7 = new JButton(cardBacks);
    JButton btn8 = new JButton(cardBacks);
    JButton btn9 = new JButton(cardBacks);
    JButton btn10 = new JButton(cardBacks);
    JButton btn11 = new JButton(cardBacks);
    JButton btn12 = new JButton(cardBacks);
    JButton btn13 = new JButton(cardBacks);
    JButton btn14 = new JButton(cardBacks);
    JButton btn15 = new JButton(cardBacks);
    JButton btn16 = new JButton(cardBacks);
    JButton btn17 = new JButton(cardBacks);
    JButton btn18 = new JButton(cardBacks);
    JButton btn19 = new JButton(cardBacks);
    JButton btn20 = new JButton(cardBacks);
    JButton btn21 = new JButton(cardBacks);
    JButton btn22 = new JButton(cardBacks);
    JButton btn23 = new JButton(cardBacks);
    JButton btn24 = new JButton(cardBacks);
    JButton btn25 = new JButton(cardBacks);
    JButton btn26 = new JButton(cardBacks);
    JButton[] buttons = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16, btn17,
                            btn18, btn19, btn20, btn21, btn22, btn23, btn24, btn25, btn26};

    
    
    public Concentration() {
                 
//        createGUI();
                
    }
    
    public static void main(String[] args) {          
        
       new Concentration();
        
    }
//    private void delay() {
//    
//        
//         t.start();
//         t.setRepeats(false);
//        
//    }
     @Override
        public void actionPerformed(ActionEvent e){
                       
            index = Arrays.asList(buttons).indexOf(e.getSource());
           
            
            System.out.println("test" + test);

            buttons[index].setIcon(cards[index]);
            t.start();
            t.stop();
            checkForMatch();
        
    }
        
    public void checkForMatch() {
                 
            System.out.println("index" + index);
            if (test == 0){
                    
                selectedCard = index; 
               
            }
            else {
                
                secondSelectedCard = index;
                
                //buttons[secondSelectedCard].setIcon(cards[secondSelectedCard] );
                //delay();
            }
	    
System.out.println("selected" + selectedCard);

        test += 1;
             
        
        if (test == 2) {
       
        System.out.println("second:" + cards[secondSelectedCard]);
        System.out.println("first" + cards[selectedCard]);
         
            if (cards[secondSelectedCard].toString() == null ? cards[selectedCard].toString() == null : cards[secondSelectedCard].toString().equals(cards[selectedCard].toString())) {
                if (selectedCard != secondSelectedCard) {

                    buttons[selectedCard].setEnabled(false);
                    buttons[secondSelectedCard].setEnabled(false);
                }
                else {
                    buttons[selectedCard].setIcon(cardBacks);
                }
                test = 0; 
            } 
            else {

                buttons[selectedCard].setIcon(cardBacks);
                buttons[secondSelectedCard].setIcon(cardBacks);
                test = 0;
            }
        }        
    }
    
    public void createGUI() {
        
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j < 14; j++) {
            
                int position = 0;
                do {
                    position = (int)(Math.random()*26);
                }
                while (cards[position] != null);
                /// look at cardURL
                URL cardURL = this.getClass().getClassLoader().getResource(j + ".png");
                cards[position] = new ImageIcon(cardURL);
            
            }  
        }
        
       
        JFrame frame = new JFrame("Concentration!");
        frame.setVisible(true);
        frame.setSize(900, 900);
        frame.setResizable(false);
        JPanel panel = new JPanel(new GridLayout(5,6));
                
        
        for (int i = 0; i <26; i++) {
            
            buttons[i].addActionListener(this);
            
            panel.add(buttons[i]);

        }  

        frame.add(panel);
        frame.pack();
    }
}
