package group3;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

public class group3 {

    Blackjack bj;
     ThirtyOne thirtyone = new ThirtyOne();
    War war = new War();
    Concentration concentration = new Concentration();
    HighLow highLow = new HighLow();

    public group3() {
        gui();
    }

    public static void main(String[] args) {
        // System.out.println(System.getProperty("user.dir"));
        // System.out.println(System.getProperty("java.class.path"));

        Splash splash = new Splash();
        Thread splashThread = new Thread(splash);
        splashThread.start();
//        splash.setVisible(true);
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
             bj = new Blackjack();
             bj.begin();
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
             thirtyone = new ThirtyOne();
             thirtyone.begin();
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

class Splash extends JFrame implements Runnable {

    /**
     * 
     */
    private static final long serialVersionUID = -4976891352425837661L;

    /**
     * 
     */
    public Splash() {
        setDefaultCloseOperation(2);

        setUndecorated(true);
        setFocusable(true);
        setLayout(null);
        setBackground(null);
        setSize(500, 300);
        SplashScreenPanel splashPanel = new SplashScreenPanel();
        add(splashPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dispose();
    } // end method
}

class SplashScreenPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 8798177549631000111L;
    BufferedImage splashImage;

    /**
     * 
     */
    public SplashScreenPanel() {
        setSize(500, 300);
        // System.out.println(System.getProperty("java.class.path"));
        // System.out.println(" Path:
        // \""+getClass().getClassLoader().getResource(".")+"\"");
        try {
            splashImage = ImageIO.read(getClass().getClassLoader().getResource("images/splash.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        // if (splashImage != null) {
        g.drawImage(splashImage, null, 0, 0);
        // }
        g.dispose();
    }
}