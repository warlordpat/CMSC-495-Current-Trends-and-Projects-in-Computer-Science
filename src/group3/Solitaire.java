// File: Solitaire.java
// Author: Patrick Smith
// Date: Sep 27, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: Creates the Solitaire GUI and implements the game logic.

package group3;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.MouseInputAdapter;

/**
 * Creates the Solitaire GUI and implements the game logic.
 *
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 27, 2016
 */
public class Solitaire extends JPanel implements Game {
    /**
     * Generated serial ID.
     */
    private static final long serialVersionUID = 7986273270280525583L;
    /**
     * The current deck in play.
     */
    private Deck deck;
    /**
     * The different piles on the game surface.
     */
    private ArrayList<SolitairePile> piles;

    /**
     * Creates a new Solitaire game.
     */
    public Solitaire() {
        setBackground(
            Blackjack.CARD_TABLE_GREEN);
        setCursor(
            new Cursor(Cursor.HAND_CURSOR));
        piles = new ArrayList<>();
        JFrame frame = new JFrame("Solitaire");
        frame.setDefaultCloseOperation(
            WindowConstants.DISPOSE_ON_CLOSE);
        frame.setTitle(
            "CGS Solitaire");
        frame.getContentPane().setLayout(
            new BorderLayout());
        frame.setPreferredSize(
            new Dimension(Blackjack.SCREEN_X,
                Blackjack.SCREEN_Y));
        frame.setResizable(
            false);
        frame.addWindowListener(
            new WindowAdapter() {
                @Override
                public void windowClosing(final WindowEvent e) {
                    // write out high scores here
                    // String path = System.getProperty("user.home");
                    // path += File.separator + "CGS";
                    // File customDir = new File(path);
                    // File scoreFile = new File(customDir, "Solitaire.score");
                    // saveHighScores(scoreFile, scores);
                    // System.out.println("Frame is closing");
                }
            });
        createMenu(
            frame);
        newGame();
        frame.setContentPane(
            this);

        frame.pack();
        frame.setLocationRelativeTo(
            null);
        frame.setVisible(
            true);

    }

    /**
     * Creates the menu and adds it to a menu.
     *
     * @param frame
     *            the frame the menu is added to
     */
    private void createMenu(final JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(
            menuBar);
        JMenu menu = new JMenu("Menu");
        menuBar.add(
            menu);

        JMenuItem mntmNewGame = new JMenuItem("New Game");
        menu.add(
            mntmNewGame);
        mntmNewGame.addActionListener(
            ae -> {
                // reset();
                newGame();
            });

        JMenuItem mntmSaveGame = new JMenuItem("Save Game");
        menu.add(
            mntmSaveGame);
        mntmSaveGame.addActionListener(
            ae -> {
                saveGame();
            });

        JMenuItem mntmLoadGame = new JMenuItem("Load Game");
        menu.add(
            mntmLoadGame);
        mntmLoadGame.addActionListener(
            ae -> {
                loadGame();
            });

        JMenuItem mntmHowToPlay = new JMenuItem("How to Play");
        menu.add(
            mntmHowToPlay);
        mntmHowToPlay.addActionListener(
            ae -> {
                directions();
            });

        JMenuItem mntmHighScores = new JMenuItem("High Scores");
        menu.add(
            mntmHighScores);
        mntmHighScores.addActionListener(
            ae -> {
                // highScores();
                // 5 points for turning over a card or placing a waste card
                // 15 points for placing a card on the foundation
            });

        JMenuItem mntmReturnToMain = new JMenuItem("Return to main menu");
        menu.add(
            mntmReturnToMain);
        mntmReturnToMain.addActionListener(
            ae -> {
                frame.dispose();
            });
    }

    /**
     * Creates a new game session.
     */
    public final void newGame() {
        removeAll();
        setLayout(
            null);
        Mouse mh = new Mouse();
        deck = new Deck();
        deck.shuffle();
        deck.setBounds(
            60, 35, Card.CARD_WIDTH, Card.CARD_HEIGHT);
        add(
            deck);
        piles.clear();
        Waste waste = new Waste(deck);
        waste.setBounds(
            160, 35, Card.CARD_WIDTH, Card.CARD_HEIGHT);
        waste.addMouseListener(
            mh);
        add(
            waste);
        piles.add(
            waste);
        deck.addMouseListener(
            new MouseAdapter() {
                public void mouseClicked(final MouseEvent e) {
                    System.out.println(
                        "Clicked on the deck");
                    if (deck.deckSize() > 0) {
                        Card temp = deck.deal();
                        System.out.println(
                            "deck size is " +
                                deck.deckSize());
                        if (!temp.isFaceUp()) {
                            temp.flip();
                        }
                        waste.addSingleCard(
                            temp);
                        System.out.println(
                            "adding " +
                                temp);
                        deck.repaint();
                    } else {
                        waste.returnCards();
                        deck.repaint();
                    }
                }
            });

        Foundation foundationToAdd = null;
        for (int i = 0; i < 4; i++) {
            foundationToAdd = new Foundation();
            foundationToAdd.setBounds(
                357 +
                    20 *
                        i
                    +
                    i *
                        Card.CARD_WIDTH,
                35,
                Card.CARD_WIDTH, Card.CARD_HEIGHT);

            // if (i == 0 && false) {
            // Card temp = new Card(Rank.ACE, Suit.HEART);
            // temp.flip();
            // foundationToAdd.addSingleCard(temp);
            // }
            foundationToAdd.addMouseListener(
                mh);
            foundationToAdd.addMouseMotionListener(
                mh);
            add(
                foundationToAdd);
            piles.add(
                foundationToAdd);
        }

        Tableau toAdd = null;
        for (int i = 0; i < 7; i++) {
            // int i = 1;
            // System.out.println("Adding tableau");
            toAdd = new Tableau(deck, i +
                1);
            toAdd.setBounds(
                60 +
                    (20 *
                        i)
                    +
                    (i *
                        Card.CARD_WIDTH),
                178,
                Card.CARD_WIDTH, (i *
                    25) +
                    Card.CARD_HEIGHT);

            toAdd.addMouseListener(
                mh);
            toAdd.addMouseMotionListener(
                mh);

            add(
                toAdd);
            piles.add(
                toAdd);
        }
        repaint();
    }

    /**
     * Runs a test of Solitaire by itself.
     *
     * @param args
     *            input parameters, not used
     */
    public static void main(final String[] args) {
        Solitaire solitaire = new Solitaire();
        // solitaire.newGame();
    } // end method

    /**
     * Internal class that implements the MouseHandler for the piles. Used to
     * move cards between the different piles and change the mouse state to show
     * the user whether they are in select or move state.
     *
     * @author Patrick Smith
     * @version 1.0
     * @since Sep 27, 2016
     */
    private class Mouse extends MouseInputAdapter {
        private SolitairePile sourcePile = null;
        private Point p;
        private Point pp;
        private int presses = 0;
        private int clicks = 0;
        private SolitairePile destinationPile;
        private Card clickedCard;
        private ArrayList<Card> tempList;

        public void mouseMoved(final MouseEvent e) {
            p = e.getPoint();
            pp = SwingUtilities.convertPoint(
                sourcePile, e.getPoint(),
                Solitaire.this);
            // System.out.println("Point at " + p + " to point " + pp);
        }

        public void mousePressed(final MouseEvent e) {

            if (e.getButton() != MouseEvent.BUTTON1) {
                return;
            }

            // pp = SwingUtilities.convertPoint(this.sourcePile, this.p,
            // Solitaire.this);

            if (clicks %
                2 == 0) {

                sourcePile = ((SolitairePile) e.getSource());
                if (sourcePile.getComponentAt(
                    p) instanceof Card) {
                    clickedCard = (Card) sourcePile.getComponentAt(
                        p);
                }
                System.out.println(
                    "first click");
                System.out.println(
                    "Clicked on: " +
                        sourcePile);
                if (clickedCard != null &&
                    clickedCard.isFaceUp()) {
                    System.out.println(
                        "Clicked on " +
                            clickedCard);
                    // save all the cards available to move, ie all face up
                    // cards from here down.
                    tempList = sourcePile.getAvailableCardsAt(
                        clickedCard);
                    clicks += 1;
                    Solitaire.this.setCursor(
                        new Cursor(Cursor.MOVE_CURSOR));
                    System.out.println(
                        "temp list is " +
                            tempList);
                }
            } else {
                // clicks = 1
                System.out.println(
                    "second click");
                destinationPile = (SolitairePile) e.getSource();
                if (clickedCard != null &&
                    destinationPile.isValidMove(
                        clickedCard)
                    &&
                    tempList.size() == 1) {
                    System.out.println(
                        "valid move");
                    System.out.println(
                        "removing a card");
                    System.out.println(
                        "Removing from: " +
                            sourcePile);
                    sourcePile.removeSingleCard(
                        clickedCard);
                    sourcePile.repaint();
                    destinationPile.addSingleCard(
                        clickedCard);
                } else if (clickedCard != null &&
                    destinationPile.isValidMove(
                        clickedCard)
                    &&
                    tempList.size() > 1 &&
                    destinationPile instanceof Tableau) {
                    System.out.println(
                        "valid move");
                    System.out.println(
                        "removing mulitple cards");
                    System.out.println(
                        "Removing from: " +
                            sourcePile);
                    System.out.println(
                        "Removing cards: " +
                            tempList);
                    sourcePile.removeCards(
                        tempList);
                    sourcePile.repaint();
                    destinationPile.addCards(
                        tempList);
                }
                clicks = 0;
                Solitaire.this.setCursor(
                    new Cursor(Cursor.HAND_CURSOR));
            }
        }

        public void mouseClicked(final MouseEvent e) {
            int clickCount = e.getClickCount();
            // System.out.println("Clicked on: " + sourcePile);
            // destinationPile =
            // ((SolitairePile)Solitaire.this.getComponentAt(this.pp));
            // System.out.println("Unclicked on: " + destinationPile);
            System.out.println(
                "double clicked on " +
                    clickedCard);
            sourcePile = ((SolitairePile) e.getSource());
            if (sourcePile.getComponentAt(
                p) instanceof Card) {
                clickedCard = (Card) sourcePile.getComponentAt(
                    p);
            } else {
                clickedCard = null;
            }
            if (clickCount == 2 &&
                clickedCard != null) {
                for (SolitairePile stack : Solitaire.this.piles) {
                    if (((stack instanceof Foundation)) &&
                        (stack.isValidMove(
                            clickedCard))
                        &&
                        (sourcePile.getTopCard().equals(
                            clickedCard))) {
                        stack.addSingleCard(
                            clickedCard);
                        sourcePile.removeSingleCard(
                            clickedCard);
                        return;
                    }
                }
            }

        }

        public void mouseReleased(final MouseEvent e) {
            // destinationPile =
            // ((SolitairePile)Solitaire.this.getComponentAt(this.pp));
            // System.out.println("Unclicked on: " + destinationPile);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see group3.Game#loadGame()
     */
    @Override
    public void loadGame() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see group3.Game#saveGame()
     */
    @Override
    public void saveGame() {
        // TODO Auto-generated method stub

    }

    /**
     * Shows the game play directions to the user.
     */
    private void directions() {
        JOptionPane.showMessageDialog(
            this,
            "     The first objective is to release and play into position certain cards to build up each "
                +
                "\nfoundation, in sequence and in suit, from the ace through the king. The ultimate objective"
                +
                "\n is to create a stack of cards from low to high in each of the four foundation poles in "
                +
                "\nthe upper-right corner. " +
                "\n    Each pile can contain only one suit. Aces are low and kings are high. The four foundation "
                +
                "\npiles must begin with aces and end with kings. " +
                "\n    Below the foundation piles, you can move cards from one column to another. Cards in "
                +
                "\ncolumns must be places in descending order and must alternate between black and red. For "
                +
                "\nexample, you can put a red 7 on a black 8." +
                "\n    You can also move sequential runs of cards between columns. Just click the deepest "
                +
                "\ncard in the run and click on another column." +
                "\n    If you ever have an empty column, you can place a king there or any sequential stack"
                +
                "\nwith a king at its head. " +
                "\n    If you get stuck, you can click on the deck in the upper-left corner to draw more cards."
                +
                "\nIf you exhause the deck, you can deal it again by clicking the empty deck space."
                +
                "\n    Move cards by clicking on the card you want to move, then clicking the pile you want"
                +
                "\nto move it to.  You can also move a card to a foundation by double clicking on it. To cancel"
                +
                "\na move, just click anywhere.  The cursur will change to show when you are in the middle"
                +
                "\n of a move.");
    }
}
