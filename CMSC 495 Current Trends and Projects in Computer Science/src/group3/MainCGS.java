// File: MainCGS.java
// Author: Patrick Smith
//         Alex Burch
// Date: Sep 5, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: implements a main menu for a CGS.
package group3;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The main menu GUI for the CGS.
 *
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 5, 2016
 */
public class MainCGS extends JPanel {
    /**
     * A generated Serial ID.
     */
    private static final long serialVersionUID = -1491473781472704497L;
    /**
     * A light aquamarine color
     */
    private Color LIGHT_AQUA = new Color(109, 225, 178);
    /**
     * Font used for the title
     */
    private Font MONOTYPE_CORSIVA = new Font("Monotype Corsiva", Font.BOLD, 30);
    /**
     * Font used for the buttons
     */
    private Font SERIF = new Font(Font.SERIF, Font.ITALIC + Font.BOLD, 20);
    /**
     * The X location of the buttons
     */
    private int BUTTON_X = 200;
    /**
     * The Y location of the War button
     */
    private int WAR_Y = 165;
    /**
     * The Y location of the Blackjack button
     */
    private int BLACKJACK_Y = 215;
    /**
     * The Y location of the ThirtyOne button
     */
    private int THIRTYONE_Y = 265;
    /**
     * The Y location of the Concentration button
     */
    private int CONCENTRATION_Y = 315;
    /**
     * The Y location of the HighLow button
     */
    private int HIGHLOW_Y = 365;
    /**
     * The Y location of the Solitaire button
     */
    private int SOLITAIRE_Y = 415;
    /**
     * The width of buttons
     */
    private int BUTTON_WIDTH = 200;
    /**
     * The height of buttons
     */
    private int BUTTON_HEIGHT = 50;
    /**
     * The X location of the Title label
     */
    private int TITLE_X = 30;
    /**
     * The Y location of the Title label
     */
    private int TITLE_Y = 30;
    /**
     * The width of the Title label
     */
    private int TITLE_WIDTH = 500;
    /**
     * The height of the Title label
     */
    private int TITLE_HEIGHT = 50;
    /**
     * The X location of the Suits image label
     */
    private int SUITS_X = 0;
    /**
     * The Y location of the Suits image label
     */
    private int SUITS_Y = 0;
    /**
     * The width of the Suits image label
     */
    private int SUITS_WIDTH = 600;
    /**
     * The height of the Suits image label
     */
    private int SUITS_HEIGHT = 600;
    /**
     * The width of the frame
     */
    private int FRAME_WIDTH = 625;
    /**
     * The height of the frame
     */
    private int FRAME_HEIGHT = 650;
    /**
     * The frame holding the main card game suite
     */
    private JFrame frame = null;
    /**
     * The BufferedImage for the Suits image
     */
    private BufferedImage Suits = null;
    /**
     * The ImageIcon for the Suits image
     */
    private ImageIcon iSuits = null;
    /**
     * The Suits label
     */
    private JLabel jlSuits = null;
    /**
     * The Title label
     */
    private JLabel jlTitle = null;
    /**
     * The button that starts an instance of War
     */
    private JButton jbWar = null;
    /**
     * The button that starts an instance of Blackjack
     */
    private JButton jbBlackjack = null;
    /**
     * The button that starts an instance of ThirtyOne
     */
    private JButton jbThirtyOne = null;
    /**
     * The button that starts an instance of Concentration
     */
    private JButton jbConcentration = null;
    /**
     * The button that starts an instance of HighLow
     */
    private JButton jbHighLow = null;
    /**
     * The button that starts an instance of Solitaire
     */
    private JButton jbSolitaire = null;

    /**
     * 
     */
    public static final boolean DEBUGGING = false;

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
        // splash.setVisible(true);
        splashThread.run();
        new MainCGS();
    }

    /**
     * Creates the GUI.
     */
    public final void gui() {
        setLayout(null);
        setBackground(LIGHT_AQUA);
        Suits = null;
        try {
            Suits = ImageIO.read(
                getClass().getClassLoader().getResource("images/Suits.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        iSuits = new ImageIcon(Suits);
        jlSuits = new JLabel(iSuits);
        jlTitle = new JLabel("The P.A.C.E. Card Game Suite", JLabel.CENTER);
        jlTitle.setFont(MONOTYPE_CORSIVA);
        jbWar = new JButton("War");
        jbWar.setPreferredSize(null);
        jbWar.setBackground(LIGHT_AQUA);
        jbWar.setFont(SERIF);
        jbBlackjack = new JButton("Blackjack");
        jbBlackjack.setPreferredSize(null);
        jbBlackjack.setBackground(LIGHT_AQUA);
        jbBlackjack.setFont(SERIF);
        jbThirtyOne = new JButton("Thirty-One");
        jbThirtyOne.setPreferredSize(null);
        jbThirtyOne.setBackground(LIGHT_AQUA);
        jbThirtyOne.setFont(SERIF);
        jbConcentration = new JButton("Concentration");
        jbConcentration.setPreferredSize(null);
        jbConcentration.setBackground(LIGHT_AQUA);
        jbConcentration.setFont(SERIF);
        jbHighLow = new JButton("High or Low");
        jbHighLow.setPreferredSize(null);
        jbHighLow.setBackground(LIGHT_AQUA);
        jbHighLow.setFont(SERIF);
        jbSolitaire = new JButton("Solitaire");
        jbSolitaire.setPreferredSize(null);
        jbSolitaire.setBackground(LIGHT_AQUA);
        jbSolitaire.setFont(SERIF);
        jbWar.setBounds(BUTTON_X, WAR_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        jbBlackjack.setBounds(BUTTON_X, BLACKJACK_Y, BUTTON_WIDTH,
            BUTTON_HEIGHT);
        jbThirtyOne.setBounds(BUTTON_X, THIRTYONE_Y, BUTTON_WIDTH,
            BUTTON_HEIGHT);
        jbConcentration.setBounds(BUTTON_X, CONCENTRATION_Y, BUTTON_WIDTH,
            BUTTON_HEIGHT);
        jbHighLow.setBounds(BUTTON_X, HIGHLOW_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        jbSolitaire.setBounds(BUTTON_X, SOLITAIRE_Y, BUTTON_WIDTH,
            BUTTON_HEIGHT);
        jlTitle.setBounds(TITLE_X, TITLE_Y, TITLE_WIDTH, TITLE_HEIGHT);
        jlSuits.setBounds(SUITS_X, SUITS_Y, SUITS_WIDTH, SUITS_HEIGHT);
        add(jbWar);
        add(jbBlackjack);
        add(jbThirtyOne);
        add(jbConcentration);
        add(jbHighLow);
        add(jbSolitaire);
        add(jlTitle);
        add(jlSuits);
        frame = new JFrame("Card Games");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setLocationRelativeTo(null);
        createMenu(frame);

        jbWar.addActionListener(ae -> {
            War war = new War();
            war.begin();
        });
        jbBlackjack.addActionListener(ae -> {
            Blackjack bj = new Blackjack();
            bj.begin();
        });
        jbConcentration.addActionListener(ae -> {
            Concentration concentration = new Concentration();
            concentration.begin();
        });
        jbThirtyOne.addActionListener(ae -> {
            ThirtyOne thirtyone = new ThirtyOne();
            thirtyone.begin();
        });
        jbHighLow.addActionListener(ae -> {
            HighLow highLow = new HighLow();
            highLow.begin();
        });
        jbSolitaire.addActionListener(ae -> {
            Solitaire solitaire = new Solitaire();
            solitaire.begin();
        });
        frame.setVisible(true);
    }

    /**
     * Creates the menu for the main card game suite
     * 
     * @param theFrame
     */
    private void createMenu(final JFrame theFrame) {
        JMenuBar menuBar = new JMenuBar();
        theFrame.setJMenuBar(menuBar);
        JMenu mnMenu = new JMenu("Menu");
        menuBar.add(mnMenu);
        JMenuItem mntmAbout = new JMenuItem("About");
        mnMenu.add(mntmAbout);
        mntmAbout.addActionListener(ae -> {
            about();
        });
        JMenuItem mntmUserGuide = new JMenuItem("User Guide");
        mnMenu.add(mntmUserGuide);
        mntmUserGuide.addActionListener(ae -> {
            userGuide();
        });
        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(ae -> {
            frame.dispose();
        });
    }

    /**
     * Creates a new message pane stating the authors's names and each of the
     * games' information
     */
    private void about() {
        JOptionPane.showMessageDialog(null,
            "The P.A.C.E. Card Game Suite is brought to you by Patrick Smith, Alex Burch, Christy Gilliland and Eric Freberger."
                    + "\nThe six games included in the card game suite are Blackjack, Concentration, High or Low, Solitaire, Thirty-One, and War."
                    + "\nThe goal of Blackjack is to have closest hand value to 21 without going over."
                    + "\nThe goal of Concentration is to match up each of the pairs of cards."
                    + "\nThe goal of High or Low is the guess whether a card is higher or lower than another card."
                    + "\nThe goal of Solitaire is to pile up all 52 cards in 4 different piles according to suit."
                    + "\nThe goal of Thirty-One is to have the closest hand value to 31."
                    + "\nThe goal of War is to gather all of the cards by comparing your cards' values to another player's cards.");
    }

    /**
     * Opens the User's Guide
     */
    private void userGuide() {
        // String myPath = System.getProperty("java.class.path");
        if (Desktop.isDesktopSupported()) {
            try {
                String path = "/User's Guide.pdf";
                // JOptionPane.showMessageDialog(null, path);
                String tempFile = "myFile";
                Path tempOutput = Files.createTempFile(tempFile, ".pdf");
                tempOutput.toFile().deleteOnExit();
                System.out.println("tempOutput: " + tempOutput);
                // InputStream is = new FileInputStream(path);
                InputStream is = getClass().getResourceAsStream(path);
                // System.out.println(myPath);
                System.out.println("is " + is);
                // long how =
                Files.copy(is, tempOutput, StandardCopyOption.REPLACE_EXISTING);
                // JOptionPane.showMessageDialog(null, how);
                Desktop.getDesktop().open(tempOutput.toFile());
            } catch (IOException | IllegalArgumentException ex) {
                // no application registered for PDFs
                System.out.println("NO PDF READER INSTALLED");
            }
        }
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
            Thread.sleep(1000);
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
            splashImage = ImageIO.read(
                getClass().getClassLoader().getResource("images/splash.png"));
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
