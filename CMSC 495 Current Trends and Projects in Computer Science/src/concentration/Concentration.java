package concentration;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;

public class Concentration extends JFrame implements ActionListener {
    URL imageURL = this.getClass().getClassLoader().getResource("cardBack.png");
    URL cardURL = getClass().getClassLoader().getResource("card_matched.png");
    ImageIcon cardBacks = new ImageIcon(this.imageURL);
    ImageIcon[] cards = new ImageIcon[26];
    int test = 0;
    int selectedCard = 0;
    int secondSelectedCard = 0;
    int index = 0;
    Timer t = new Timer(2000, this);
    JButton btn1 = new JButton(this.cardBacks);
    JButton btn2 = new JButton(this.cardBacks);
    JButton btn3 = new JButton(this.cardBacks);
    JButton btn4 = new JButton(this.cardBacks);
    JButton btn5 = new JButton(this.cardBacks);
    JButton btn6 = new JButton(this.cardBacks);
    JButton btn7 = new JButton(this.cardBacks);
    JButton btn8 = new JButton(this.cardBacks);
    JButton btn9 = new JButton(this.cardBacks);
    JButton btn10 = new JButton(this.cardBacks);
    JButton btn11 = new JButton(this.cardBacks);
    JButton btn12 = new JButton(this.cardBacks);
    JButton btn13 = new JButton(this.cardBacks);
    JButton btn14 = new JButton(this.cardBacks);
    JButton btn15 = new JButton(this.cardBacks);
    JButton btn16 = new JButton(this.cardBacks);
    JButton btn17 = new JButton(this.cardBacks);
    JButton btn18 = new JButton(this.cardBacks);
    JButton btn19 = new JButton(this.cardBacks);
    JButton btn20 = new JButton(this.cardBacks);
    JButton btn21 = new JButton(this.cardBacks);
    JButton btn22 = new JButton(this.cardBacks);
    JButton btn23 = new JButton(this.cardBacks);
    JButton btn24 = new JButton(this.cardBacks);
    JButton btn25 = new JButton(this.cardBacks);
    JButton btn26 = new JButton(this.cardBacks);
    JButton[] buttons = { this.btn1, this.btn2, this.btn3, this.btn4, this.btn5, this.btn6, this.btn7, this.btn8,
            this.btn9, this.btn10, this.btn11, this.btn12, this.btn13, this.btn14, this.btn15, this.btn16, this.btn17,
            this.btn18, this.btn19, this.btn20, this.btn21, this.btn22, this.btn23, this.btn24, this.btn25,
            this.btn26 };

    public Concentration() {
        imageURL = this.getClass().getClassLoader().getResource("cardBack.png");
        System.out.println(imageURL);
        cardURL = getClass().getClassLoader().getResource("card_matched.png");
        cardBacks = new ImageIcon(this.imageURL);
        cards = new ImageIcon[26];
        createGUI();
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(System.getProperty("java.class.path"));
        new Concentration();
    }

    public void actionPerformed(ActionEvent e) {
        index = Arrays.asList(buttons).indexOf(e.getSource());

        System.out.println("test" + test);

        buttons[index].setIcon(cards[index]);
        this.t.start();
        this.t.stop();
        checkForMatch();
    }

    public void checkForMatch() {
        System.out.println("index" + this.index);
        if (this.test == 0) {
            this.selectedCard = this.index;
        } else {
            this.secondSelectedCard = this.index;
        }
        System.out.println("selected" + this.selectedCard);

        this.test += 1;
        if (this.test == 2) {
            System.out.println("second:" + this.cards[this.secondSelectedCard]);
            System.out.println("first" + this.cards[this.selectedCard]);
            if (this.cards[this.secondSelectedCard].toString() == null
                    ? this.cards[this.selectedCard].toString() == null
                    : this.cards[this.secondSelectedCard].toString().equals(this.cards[this.selectedCard].toString())) {
                if (this.selectedCard != this.secondSelectedCard) {
                    this.buttons[this.selectedCard].setEnabled(false);
                    this.buttons[this.secondSelectedCard].setEnabled(false);
                } else {
                    this.buttons[this.selectedCard].setIcon(this.cardBacks);
                }
                this.test = 0;
            } else {
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        buttons[selectedCard].setIcon(cardBacks);
                        buttons[secondSelectedCard].setIcon(cardBacks);
                        test = 0;
                    }

                });

            }
        }
    }

    public void createGUI() {
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j < 14; j++) {
                int position = 0;
                do {
                    position = (int) (Math.random() * 26.0D);
                } while (this.cards[position] != null);
                URL cardURL = getClass().getClassLoader().getResource(j + ".png");
                this.cards[position] = new ImageIcon(cardURL);
            }
        }
        JFrame frame = new JFrame("Concentration!");
        frame.setVisible(true);
        frame.setSize(900, 900);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(3);
        JPanel panel = new JPanel(new GridLayout(5, 6));
        for (int i = 0; i < 26; i++) {
            this.buttons[i].addActionListener(this);

            panel.add(this.buttons[i]);
        }
        frame.add(panel);
        frame.pack();
    }
}
