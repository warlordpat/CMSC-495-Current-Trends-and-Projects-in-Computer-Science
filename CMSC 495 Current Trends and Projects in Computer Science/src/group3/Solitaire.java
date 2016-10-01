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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
     * The Y coordinate of the row of Tableaus.
     */
    private static final int TABLEAU_Y = 178;
    /**
     * The X coordinate of the first Foundation.
     */
    private static final int FOUNDATION_X = 357;
    /**
     * Spacing between the Deck and the Waste.
     */
    private static final int WASTE_SPACE = 100;
    /**
     * Vertical Card spacing in Tableaus.
     */
    private static final int CARD_SPACING = Tableau.CARD_SPACING;
    /**
     * Horizontal spacing between Piles.
     */
    private static final int PILE_SPACING = 20;
    /**
     * The number of Tableaus.
     */
    private static final int NUM_TABLEAUS = 7;
    /**
     * The number of Foundations.
     */
    private static final int NUM_FOUNDATIONS = 4;
    /**
     * The Y coordinate of the top row of cards.
     */
    private static final int TOP_ROW_Y = 35;
    /**
     * The X coordinate of the Deck.
     */
    private static final int DECK_X = 60;
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
     * The discard pile. Also called the Waste.
     */
    private Waste waste;
    /**
     * The Deck's MouseAdapter.
     */
    private MouseAdapter deckMa;
    /**
     * The Solitaire highscores.
     */
    private HighScores scores;
    /**
     * The current score.
     */
    private int score;

    /**
     * Creates a new Solitaire game.
     */
    public Solitaire() {
        setBackground(Blackjack.CARD_TABLE_GREEN);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        scores = loadOrCreateScores("Solitaire");
        piles = new ArrayList<>();
        JFrame frame = new JFrame("Solitaire");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setTitle("CGS Solitaire");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setPreferredSize(
            new Dimension(Blackjack.SCREEN_X, Blackjack.SCREEN_Y));
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                // write out high scores here
                String path = System.getProperty("user.home");
                path += File.separator + "CGS";
                File customDir = new File(path);
                File scoreFile = new File(customDir, "Solitaire.score");
                saveHighScores(scoreFile, scores);
                if (MainCGS.DEBUGGING) {
                    System.out.println("Frame is closing");
                }
            }
        });
        createMenu(frame);

        frame.setContentPane(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    /**
     * Creates the menu and adds it to a menu.
     *
     * @param frame
     *            the frame the menu is added to
     */
    private void createMenu(final JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);

        JMenuItem mntmNewGame = new JMenuItem("New Game");
        menu.add(mntmNewGame);
        mntmNewGame.addActionListener(ae -> {
            // reset();
            newGame();
        });

        JMenuItem mntmSaveGame = new JMenuItem("Save Game");
        menu.add(mntmSaveGame);
        mntmSaveGame.addActionListener(ae -> {
            saveGame();
        });

        JMenuItem mntmLoadGame = new JMenuItem("Load Game");
        menu.add(mntmLoadGame);
        mntmLoadGame.addActionListener(ae -> {
            loadGame();
        });

        JMenuItem mntmHowToPlay = new JMenuItem("How to Play");
        menu.add(mntmHowToPlay);
        mntmHowToPlay.addActionListener(ae -> {
            directions();
        });

        JMenuItem mntmHighScores = new JMenuItem("High Scores");
        menu.add(mntmHighScores);
        mntmHighScores.addActionListener(ae -> {
            highScores();
            // 5 points for turning over a card or placing a waste card
            // 15 points for placing a card on the foundation
        });

        JMenuItem mntmReturnToMain = new JMenuItem("Return to main menu");
        menu.add(mntmReturnToMain);
        mntmReturnToMain.addActionListener(ae -> {
            frame.dispose();
        });
    }

    /**
     * Starts the gameplay.
     */
    public final void begin() {
        if (MainCGS.DEBUGGING) {
            System.out.println("beginning");
        }
        newGame();
        SwingUtilities.getRoot(this).setVisible(true);
        // deal();
    }

    /**
     * Creates a new game session.
     */
    public final void newGame() {
        removeAll();
        score = 0;
        setLayout(null);
        Mouse mh = new Mouse();
        deck = new Deck();
        deck.shuffle();
        deck.setBounds(DECK_X, TOP_ROW_Y, Card.CARD_WIDTH, Card.CARD_HEIGHT);
        add(deck);
        piles.clear();
        waste = new Waste();
        waste.setBounds(DECK_X + WASTE_SPACE, TOP_ROW_Y, Card.CARD_WIDTH,
            Card.CARD_HEIGHT);
        waste.addMouseListener(mh);
        add(waste);
        piles.add(waste);
        deckMa = new MouseAdapter() {
            public void mouseClicked(final MouseEvent e) {
                System.out.println("Clicked on the deck");
                if (deck.deckSize() > 0) {
                    Card temp = deck.deal();
                    System.out.println("deck size is " + deck.deckSize());
                    if (!temp.isFaceUp()) {
                        temp.flip();
                    }
                    waste.addSingleCard(temp);
                    System.out.println("adding " + temp);
                    deck.repaint();
                } else {
                    waste.returnCards(deck);
                    deck.repaint();
                }
            }
        };
        deck.addMouseListener(deckMa);

        Foundation foundationToAdd = null;
        for (int i = 0; i < NUM_FOUNDATIONS; i++) {
            foundationToAdd = new Foundation();
            foundationToAdd.setBounds(
                FOUNDATION_X + (PILE_SPACING * i) + (i * Card.CARD_WIDTH),
                TOP_ROW_Y, Card.CARD_WIDTH, Card.CARD_HEIGHT);

            // if (i == 0 && false) {
            // Card temp = new Card(Rank.ACE, Suit.HEART);
            // temp.flip();
            // foundationToAdd.addSingleCard(temp);
            // }
            foundationToAdd.addMouseListener(mh);
            foundationToAdd.addMouseMotionListener(mh);
            add(foundationToAdd);
            piles.add(foundationToAdd);
        }

        Tableau toAdd = null;
        for (int i = 0; i < NUM_TABLEAUS; i++) {
            // int i = 1;
            // System.out.println("Adding tableau");
            toAdd = new Tableau(deck, i + 1);
            toAdd.setBounds(DECK_X + (PILE_SPACING * i) + (i * Card.CARD_WIDTH),
                TABLEAU_Y, Card.CARD_WIDTH,
                (i * CARD_SPACING) + Card.CARD_HEIGHT);

            toAdd.addMouseListener(mh);
            toAdd.addMouseMotionListener(mh);

            add(toAdd);
            piles.add(toAdd);
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
        solitaire.begin();
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
        /**
         * Score earned for moving a card to the Foundation.
         */
        private static final int FOUNDATION_MOVE_SCORE = 15;
        /**
         * The source pile selected by the first click.
         */
        private SolitairePile sourcePile = null;
        /**
         * The current user location on the GUI.
         */
        private Point p;
        /**
         * Used to transform the point into a different coordinate system.
         */
        @SuppressWarnings("unused")
        private Point pp;
        /**
         * Tracks the number of clicks the user made.
         */
        private int clicks = 0;
        /**
         * The destination pile selected by a second click.
         */
        private SolitairePile destinationPile;
        /**
         * The card the user last clicked on.
         */
        private Card clickedCard;
        /**
         * A temporary list of cards. Used when moving multiple cards from one
         * Tableau to another.
         */
        private ArrayList<Card> tempList;

        /*
         * (non-Javadoc) Updated where the cursor is on screen.
         *
         * @see
         * java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseMoved(final MouseEvent e) {
            p = e.getPoint();
            pp = SwingUtilities.convertPoint(sourcePile, e.getPoint(),
                Solitaire.this);
            // System.out.println("Point at " + p + " to point " + pp);
        }

        /*
         * (non-Javadoc) One click selects a card. The next click selects where
         * to put it. If it is a valid move, it is moved.
         *
         * @see
         * java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
         */
        @Override
        public void mousePressed(final MouseEvent e) {

            if (e.getButton() != MouseEvent.BUTTON1) {
                return;
            }

            // pp = SwingUtilities.convertPoint(this.sourcePile, this.p,
            // Solitaire.this);

            if (clicks % 2 == 0) {
                sourcePile = ((SolitairePile) e.getSource());
                if (sourcePile.getComponentAt(p) instanceof Card) {
                    clickedCard = (Card) sourcePile.getComponentAt(p);
                }
                if (MainCGS.DEBUGGING) {
                    System.out.println("first click");
                    System.out.println("Clicked on: " + sourcePile);
                }
                if (clickedCard != null && clickedCard.isFaceUp()) {
                    if (MainCGS.DEBUGGING) {
                        System.out.println("Clicked on " + clickedCard);
                    }
                    // save all the cards available to move, ie all face up
                    // cards from here down.
                    tempList = sourcePile.getAvailableCardsAt(clickedCard);
                    clicks += 1;
                    Solitaire.this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                    if (MainCGS.DEBUGGING) {
                        System.out.println("temp list is " + tempList);
                    }
                }
            } else {
                // clicks = 1
                if (MainCGS.DEBUGGING) {
                    System.out.println("second click");
                }
                destinationPile = (SolitairePile) e.getSource();
                if (clickedCard != null
                        && destinationPile.isValidMove(clickedCard)
                        && tempList.size() == 1) {
                    if (MainCGS.DEBUGGING) {
                        System.out.println("valid move");
                        System.out.println("removing a card");
                        System.out.println("Removing from: " + sourcePile);
                    }
                    sourcePile.removeSingleCard(clickedCard);
                    sourcePile.repaint();
                    destinationPile.addSingleCard(clickedCard);
                    if (sourcePile instanceof Waste) {
                        if (destinationPile instanceof Foundation) {
                            score += FOUNDATION_MOVE_SCORE;
                        } else {
                            score += 5;
                        }
                    }
                } else if (clickedCard != null
                        && destinationPile.isValidMove(clickedCard)
                        && tempList.size() > 1
                        && destinationPile instanceof Tableau) {
                    if (MainCGS.DEBUGGING) {
                        System.out.println("valid move");
                        System.out.println("removing mulitple cards");
                        System.out.println("Removing from: " + sourcePile);
                        System.out.println("Removing cards: " + tempList);
                    }
                    sourcePile.removeCards(tempList);
                    sourcePile.repaint();
                    destinationPile.addCards(tempList);
                    if (sourcePile instanceof Tableau) {
                        if (!((Tableau) sourcePile).isEmpty()) {
                            // maybe a new card was turned?
                        }
                    }
                }
                clicks = 0;
                Solitaire.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        /*
         * (non-Javadoc) Checks if the user double clicked. If they did, and the
         * clicked card can go on a foundation, put it there.
         *
         * @see
         * java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseClicked(final MouseEvent e) {
            int clickCount = e.getClickCount();
            // System.out.println("Clicked on: " + sourcePile);
            // destinationPile =
            // ((SolitairePile)Solitaire.this.getComponentAt(this.pp));
            // System.out.println("Unclicked on: " + destinationPile);
            if (MainCGS.DEBUGGING) {
                System.out.println("double clicked on " + clickedCard);
            }
            sourcePile = ((SolitairePile) e.getSource());
            if (sourcePile.getComponentAt(p) instanceof Card) {
                clickedCard = (Card) sourcePile.getComponentAt(p);
            } else {
                clickedCard = null;
            } // end else
            if (clickCount == 2 && clickedCard != null) {
                for (SolitairePile stack : Solitaire.this.piles) {
                    if (((stack instanceof Foundation))
                            && (stack.isValidMove(clickedCard))
                            && (sourcePile.getTopCard().equals(clickedCard))) {
                        score += FOUNDATION_MOVE_SCORE;
                        stack.addSingleCard(clickedCard);
                        sourcePile.removeSingleCard(clickedCard);
                        return;
                    } // end if
                } // end for
            } // end if
        } // end method

        /*
         * (non-Javadoc)
         *
         * @see
         * java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
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
    @SuppressWarnings("unchecked")
    @Override
    public final void loadGame() {
        // check if war output matters (it doesn't)
        if (MainCGS.DEBUGGING) {
            System.out.println("Loading Game");
        }
        removeAll();

        if (MainCGS.DEBUGGING) {
            System.out.println("removed piles and deck");
        }
        revalidate();
        repaint();
        try (FileInputStream filestream = new FileInputStream("Solitaire.ser");
                ObjectInputStream os = new ObjectInputStream(filestream);) {
            deck = (Deck) os.readObject();
            piles = (ArrayList<SolitairePile>) os.readObject();

            if (MainCGS.DEBUGGING) {
                System.out.println(
                    "loaded: " + "\n deck " + deck + "\n piles   " + piles);
            }
            deck.addMouseListener(deckMa);
            add(deck);
            Mouse mh = new Mouse();
            for (SolitairePile p : piles) {
                // System.out.println(p);
                if (p instanceof Waste) {
                    waste = (Waste) p;
                }
                p.addMouseListener(mh);
                if (p instanceof Foundation || p instanceof Tableau) {
                    p.addMouseMotionListener(mh);
                }
                add(p);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (MainCGS.DEBUGGING) {
            System.out.println("Game Loaded");
            System.out.println("" + deck.getMouseListeners());
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see group3.Game#saveGame()
     */
    @Override
    public final void saveGame() {
        if (MainCGS.DEBUGGING) {
            System.out.println("Saving Game");
        }
        try (FileOutputStream filestream =
                new FileOutputStream("Solitaire.ser");
                ObjectOutputStream os = new ObjectOutputStream(filestream);) {
            os.writeObject(deck);
            os.writeObject(piles);
            deck.addMouseListener(deck.getMouseListeners()[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (MainCGS.DEBUGGING) {
            System.out.println("Game Saved" + "\nsaved: " + "\n deck " + deck
                    + "\n piles   " + piles);
            System.out.println("" + deck.getMouseListeners());
        }
    }

    /**
     * Shows the game play directions to the user.
     */
    private void directions() {
        JOptionPane.showMessageDialog(this,
            "     The first objective is to release and play into position certain cards to build up each "
                    + "\nfoundation, in sequence and in suit, from the ace through the king. The ultimate objective"
                    + "\n is to create a stack of cards from low to high in each of the four foundation poles in "
                    + "\nthe upper-right corner. "
                    + "\n    Each pile can contain only one suit."
                    + " Aces are low and kings are high. The four foundation "
                    + "\npiles must begin with aces and end with kings. "
                    + "\n    Below the foundation piles, you can move cards from one column to another. Cards in "
                    + "\ncolumns must be places in descending order and must alternate between black and red. For "
                    + "\nexample, you can put a red 7 on a black 8."
                    + "\n    You can also move sequential runs of cards between columns. Just click the deepest "
                    + "\ncard in the run and click on another column."
                    + "\n    If you ever have an empty column, you can place a king there or any sequential stack"
                    + "\nwith a king at its head. "
                    + "\n    If you get stuck, you can click on the deck in the upper-left corner to draw more cards."
                    + "\nIf you exhause the deck, you can deal it again by clicking the empty deck space."
                    + "\n    Move cards by clicking on the card you want to move, then clicking the pile you want"
                    + "\nto move it to.  You can also move a card to a foundation by double clicking on it. To cancel"
                    + "\na move, just click anywhere.  The cursur will change to show when you are in the middle"
                    + "\n of a move.");
    }

    /**
     * Shows the high scores in a dialog box.
     */
    private void highScores() {
        JOptionPane.showMessageDialog(this, scores);
    }
}
