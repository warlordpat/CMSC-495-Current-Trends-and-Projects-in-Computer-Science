// File: MainCGS.java
// Author: Patrick Smith
//         Alex Birch
// Date: Sep 5, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: implements a main menu for a CGS.
package group3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

/**
 * The main menu GUI for the CGS.
 *
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 5, 2016
 */
public class MainCGS {
    // TODO Remove magic numbers
    /**
     * If true, prints debugging messages.
     */
    public static final boolean DEBUGGING = true;

    /**
     * Constructs a new main program object.
     */
    public MainCGS() {
        gui();
    }

    /**
     * Creates and displays the main GUI.
     *
     * @param args
     *            not used.
     */
    public static void main(final String[] args) {
        // System.out.println(System.getProperty("user.dir"));
        // System.out.println(System.getProperty("java.class.path"));

        Splash splash = new Splash();
        Thread splashThread = new Thread(splash);
        splashThread.start();
        // splash.setVisible(true);
        new MainCGS();

    }

    /**
     * Creates the GUI.
     */
    public final void gui() {

        JFrame frame = new JFrame("Card Games");
        frame.setVisible(true);
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JSplitPane topSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel jlTitle = new JLabel("The P.A.C.E. Card Game Suite", JLabel.CENTER);
        Font font1 = new Font("Monotype Corsiva", Font.BOLD, 30);
        jlTitle.setFont(font1);
        titlePanel.add(jlTitle);
        topSplit.setTopComponent(titlePanel);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JPanel topPanel = new JPanel(new GridLayout());
        topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

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
            War war = new War();
            war.begin();
        });
        jbBlackJack.addActionListener(ae -> {
            Blackjack bj = new Blackjack();
            bj.begin();
        });
        jbConcentration.addActionListener(ae -> {
            Concentration concentration = new Concentration();
            concentration.begin();
        });
        topPanel.add(jbWar, BorderLayout.LINE_START);
        topPanel.add(jbConcentration, BorderLayout.CENTER);
        topPanel.add(jbBlackJack, BorderLayout.LINE_END);
        topPanel.setPreferredSize(new Dimension(500, 95));

        JPanel bottomPanel = new JPanel(new GridLayout());
        bottomPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JButton jbThirtyOne = new JButton("Thirty-One");
        jbThirtyOne.setFont(new Font("Courier Bold", Font.BOLD, 17));
        jbThirtyOne.addActionListener(ae -> {
            ThirtyOne thirtyone = new ThirtyOne();
            thirtyone.begin();
        });
        JButton jbHighLow = new JButton("High or Low");
        jbHighLow.setFont(new Font("Courier Bold", Font.BOLD, 17));
        jbHighLow.addActionListener(ae -> {
            HighLow highLow = new HighLow();
            highLow.begin();
        });
        jbThirtyOne.setBackground(new Color(100, 193, 181));
        jbHighLow.setBackground(new Color(42, 148, 70));
        bottomPanel.add(jbThirtyOne, BorderLayout.LINE_START);
        bottomPanel.add(jbHighLow, BorderLayout.LINE_END);

        splitPane.setTopComponent(topPanel);
        splitPane.setBottomComponent(bottomPanel);
        topSplit.setBottomComponent(splitPane);
        frame.add(topSplit, BorderLayout.CENTER);
        frame.setSize(500, 300);

    }
}

/**
 * Implements a splash screen that displays an image.
 *
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 5, 2016
 */
class Splash extends JFrame implements Runnable {

    /**
     * The generated serial ID.
     */
    private static final long serialVersionUID = -4976891352425837661L;

    /**
     * Creates and resizes the splash screen and its panel.
     */
    Splash() {
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
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dispose();
    } // end method
}

/**
 * A panel for the splash screen that draws an image to the panel.
 *
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 5, 2016
 */
class SplashScreenPanel extends JPanel {
    /**
     * The generated serial ID.
     */
    private static final long serialVersionUID = 8798177549631000111L;
    /**
     * Holds the loaded image for display.
     */
    private BufferedImage splashImage;

    /**
     * Loads the image to the panel.
     */
    SplashScreenPanel() {
        setSize(500, 300);
        // System.out.println(System.getProperty("java.class.path"));
        // System.out.println(" Path:
        // \""+getClass().getClassLoader().getResource(".")+"\"");
        try {
            splashImage = ImageIO.read(getClass().getClassLoader().getResource("images/splash.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(final Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.drawImage(splashImage, null, 0, 0);
        // }
        g.dispose();
    }
}
