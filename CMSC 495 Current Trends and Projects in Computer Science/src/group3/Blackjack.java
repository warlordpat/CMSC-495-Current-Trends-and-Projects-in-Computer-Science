package group3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import javax.swing.*;
import java.awt.GridLayout;

/**
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 5, 2016
 */
public class Blackjack extends JPanel {
    /**
     * 
     */
    private static final String REMAIN = "Remaining ";
    private static final double BLACKJACK_PAYOUT = 2.5;
    private static final double NORMAL_PAYOUT = 2.0;
    /**
     * 
     */
    private static final long serialVersionUID = 510809952979347694L;
    /**
     * The current deck in play.
     */
    private Deck deck;
    private Hand dealer, player;
    double playerCash;
    Bet playerBetHand1;
    Bet playerBetHand2;
    /**
     * A hand to hold the player's split hand cards.
     */
    private Hand playerSplitHand;
    /**
     * If the hand is still in play.
     */
    boolean handOneInPlay;
    /**
     * If the hand was split.
     */
    private boolean wasSplit;
    /**
     * If the player's first hand is complete.
     */
    private boolean doneHand1;
    /**
     * If the second hand is still in play.
     */
    private boolean handTwoInPlay;
    private boolean splitTest = true;
    /**
     * The GUI element that displays the number of cards remaining in the deck.
     */
    private JLabel remaining;
    private JButton jbSplit;
    private JPanel panel;
    private JPanel arrow;
    private JLabel lblValue1;
    private JLabel lblValue2;
    private JFrame frame;
    private boolean reload;
    private HighScores scores;
    private JPanel bettingPanel;
    private JLabel lblInstructions;
    private JButton deal;
    private JLabel lblBet;
    private JButton hit;
    private JButton stand;
    private JLabel lblCash;

    /**
     * 
     */
    public Blackjack() {
        deck = new Deck();
        remaining = new JLabel(REMAIN + deck.deckSize());
        dealer = new Hand();
        playerCash = 1000;
        playerBetHand1 = new Bet();
        arrow = new JPanel() {
            /**
             * 
             */
            private static final long serialVersionUID = -619516541179237701L;

            public void paintComponent(Graphics g) {
                /*
                 * x1 x-position of first point y1 y-position of first point x2
                 * x-position of second point y2 y-position of second point d
                 * the width of the arrow h the height of the arrow
                 */
                int x1 = 0;
                int y1 = 10;
                int x2 = 75;
                int y2 = 10;
                int d = 20;
                int h = 10;
                int dx = x2 - x1, dy = y2 - y1;
                double D = Math.sqrt(dx * dx + dy * dy);
                double xm = D - d, xn = xm, ym = h, yn = -h, x;
                double sin = dy / D, cos = dx / D;

                x = xm * cos - ym * sin + x1;
                ym = xm * sin + ym * cos + y1;
                xm = x;

                x = xn * cos - yn * sin + x1;
                yn = xn * sin + yn * cos + y1;
                xn = x;

                int[] xpoints = { x2, (int) xm, (int) xn };
                int[] ypoints = { y2, (int) ym, (int) yn };

                g.drawLine(x1, y1, x2, y2);
                g.fillPolygon(xpoints, ypoints, 3);
            }
        };
        panel = new JPanel();
        player = new Hand();
        playerSplitHand = new Hand();
        lblValue1 = new JLabel("Value:");
        lblValue2 = new JLabel("Value: ");
        String path = System.getProperty("user.home");
        path += File.separator + "CGS";
        File customDir = new File(path);
        File scoreFile = new File(customDir, "BlackJack.score");
        if (customDir.exists()) {
            System.out.println(customDir + " already exists");
            // load a score file

            if (scoreFile.exists()) {
                System.out.println(scoreFile + " already exists");
                loadHighScores(scoreFile);
            } else {
                // create new scorefile
                System.out.println(scoreFile + " created");
                scores = new HighScores();
                saveHighScores(scoreFile);
            }

        } else if (customDir.mkdirs()) {
            System.out.println(customDir + " was created");
            System.out.println(scoreFile + " created");
            // Make a blank score file
            scores = new HighScores();
            saveHighScores(scoreFile);
        } else {
            System.out.println(customDir + " was not created");
            // throw an error, why can't we access the user dir?
        }

        createGUI();
    }

    /**
     * 
     */
    private void saveHighScores(File scoreFile) {
        try (FileOutputStream filestream = new FileOutputStream(scoreFile);
                ObjectOutputStream os = new ObjectOutputStream(filestream);) {
            os.writeObject(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param scoreFile
     */
    private void loadHighScores(File scoreFile) {
        try (FileInputStream filestream = new FileInputStream(scoreFile);
                ObjectInputStream os = new ObjectInputStream(filestream);) {
            scores = (HighScores) os.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void begin() {
        System.out.println("beginning");
        newGame();
        // deal();
    }

    void newGame() {
        deck.shuffle();
        handOneInPlay = false;
        remove(player);
        remove(dealer);
        // repaint();
    }

    public String display() {
        String output = "";
        if (handOneInPlay) {
            output += "\nPlayer Hand: " + player;
            List<Card> dealerCards = dealer.getCards();
            String dealerString = "[";
            for (int i = 0; i < dealerCards.size(); i++) {
                if (i == 1) {
                    dealerString += "FACEDOWN";
                } else {
                    dealerString += dealerCards.get(i);
                } // end else
                if (i < dealerCards.size() - 1) {
                    dealerString += ", ";
                } else {
                    dealerString += "]";
                } // end else
            } // end for
            output += "\nDealer Hand: " + dealerString;
        } else {
            output += "Player Hand: " + player + " " + player.scoreHand();
            output += "\nDealer Hand: " + dealer + " " + dealer.scoreHand();
        }
        return output;
    }

    /**
     * Deals out initial hand in BlackJack. Shuffles the deck when the number of
     * Cards left gets low.
     */
    public void deal() {
        bettingPanel.setVisible(false);
        // for (Component button: bettingPanel.getComponents()) {
        // button.setVisible(false);
        // }
        lblInstructions.setVisible(false);
        if (deck.deckSize() < 10) {
            System.out.println("reshuffling");
            deck = new Deck();
            deck.shuffle();
            JOptionPane.showMessageDialog(this, "Reshuffling...");
        }
        if (!handOneInPlay) {
            System.out.println("Dealing new hand");
            remove(dealer);
            dealer = new Hand();
            dealer.setBounds(200, 100, (2 * 72), 96); // adds a new dealer hand
                                                      // to the GUI
            add(dealer);

            player = new Hand();
            player.setBounds(200, 300, (2 * 72), 96); // adds a new player hand
                                                      // to the GUI
            add(player);

            {
                addCardFaceUp(player);

                // try {
                // Thread.sleep(200);
                // } catch (InterruptedException e) {
                // e.printStackTrace();
                // }

                addCardFaceUp(dealer);

                // try {
                // Thread.sleep(200);
                // } catch (InterruptedException e) {
                // e.printStackTrace();
                // }

                addCardFaceUp(player);

                // try {
                // Thread.sleep(200);
                // } catch (InterruptedException e) {
                // e.printStackTrace();
                // }

                dealer.addCard(deck.deal());
            }
            System.out.println(dealer);
            if (isSplittable()) {
                panel.add(jbSplit);
                jbSplit.setVisible(true);
            } else {
                jbSplit.setVisible(false);
            }
            handOneInPlay = true;
            hit.setVisible(true);
            stand.setVisible(true);
            updateRemain();
            repaint();
        }
    }

    /**
     * Adds a card to the given player and flips it face up.
     * 
     * @param player
     *            the player to give the card to
     */
    private void addCardFaceUp(Hand player) {
        Card temp;

        if (splitTest) {
            temp = new Card(Rank.KING, Suit.CLUB);
        } else {
            temp = deck.deal();
        }
        updateRemain();
        player.addCard(temp);
        temp.flip();
    } // end method

    /**
     * Updates the card remaining in the deck GUI element.
     */
    private void updateRemain() {
        remaining.setText(REMAIN + deck.deckSize());
        if (!lblValue1.isVisible()) {
            lblValue1.setVisible(true);
        }

        if (handOneInPlay) {
            lblValue1.setText("Value: " + player.scoreHand());
        } else if (player.isBusted()) {
            lblValue1.setText("Value: " + player.scoreHand() + " Busted");
            lblValue1.setSize(150, 14);
        }
        if (handTwoInPlay) {
            lblValue2.setText("Value: " + playerSplitHand.scoreHand());
            if (!lblValue2.isVisible()) {
                lblValue2.setVisible(true);
            }
        } else {

        }

    }

    public void hit() {
        if (isSplittable()) {
            System.out.println("is splittable, but hit");
            jbSplit.setVisible(false);
        }
        if (deck.deckSize() == 0) {
            throw new Error("Deck Out of Cards!");
        }
        System.out.println("hitting");
        if (handOneInPlay) {
            Card card = deck.deal();
            updateRemain();
            card.flip();
            System.out.println(card);
            player.addCard(card);
            updateRemain();
            // try {
            // Thread.sleep(200);
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }

            System.out.println("Bust checking");
            if (player.isBusted() && !handTwoInPlay) {
                handOneInPlay = false;
                checkWinConditions();
            } // end if
            else if (player.isBusted()) {
                System.out.println("Busted Hand One");
                shiftToHandTwo();
            }
        } // end if
          // move on to hand two if hand one is done
        else if (handTwoInPlay) {
            Card card = deck.deal();
            updateRemain();
            card.flip();
            // System.out.println(card);
            playerSplitHand.addCard(card);
            updateRemain();
            System.out.println("Bust checking");
            // both hands busted
            if (playerSplitHand.isBusted() && player.isBusted()) {
                handOneInPlay = false;
                checkWinConditions();
            } // end if
              // only hand two busted
            else if (playerSplitHand.isBusted()) {
                // dealer's turn
                dealerPlays();
            }
        } // end else if
    } // end method

    /**
     * 
     */
    private void shiftToHandTwo() {
        handOneInPlay = false;
        arrow.setLocation(125, 400);
    }

    public void stand() {
        if (isSplittable()) {
            System.out.println("is splittable, but stood");
            jbSplit.setVisible(false);
        }
        // stand only matters if the hand is still in play
        if (handOneInPlay && !handTwoInPlay) {
            handOneInPlay = false;
            dealerPlays();
        } else if (handOneInPlay && handTwoInPlay) {
            shiftToHandTwo();
        } else if (handTwoInPlay) {
            System.out.println("standing on two!");
            dealerPlays();
        } // end else if
    } // end stand

    /**
     * 
     */
    private void dealerPlays() {
        dealer.getCard(1).flip();
        while (dealer.scoreHand() <= 17) {
            Card card = deck.deal();
            updateRemain();
            dealer.addCard(card);
            card.flip();
        }
        checkWinConditions();
    } // end method

    /**
     * 
     */
    private void checkWinConditions() {
        int answer;

        // bust checking
        if (player.isBusted() && !handTwoInPlay) {
            System.out.println("Getting busted answer (only one hand in play)");
            answer = loseQuestion(player);
        } // end if
        else if (playerSplitHand.isBusted() && player.isBusted()) {
            System.out.println("Busted Both Hands");
            answer = loseQuestion(player);
        } else {

            boolean loseHandOne = dealer.scoreHand() >= player.scoreHand();
            boolean loseHandTwo = dealer.scoreHand() >= playerSplitHand.scoreHand();

            if (dealer.isBusted()) {

                answer = winQuestion();
            }
            // one hand or both is not busted, otherwise we shouldn't get here
            else if (player.isBusted()) {
                // if hand 1 is busted, but not hand two
                if (loseHandTwo) {
                    answer = loseQuestion(playerSplitHand);
                } else {
                    answer = winQuestion();
                }
            } else if (playerSplitHand.isBusted()) {
                // if hand 2 is busted but not hand one
                if (loseHandOne) {
                    answer = loseQuestion(player);
                } else {
                    answer = winQuestion();
                }
            } else {
                // if neither hand is busted
                if (loseHandOne && loseHandTwo) {
                    // lost both hands
                    answer = loseQuestion(player);
                } else if (!loseHandOne && loseHandTwo) {
                    // won hand one, lost hand two
                    answer = winQuestion();
                } else if (loseHandOne && !loseHandTwo) {
                    // lost hand one, won hand two
                    answer = winQuestion();
                } else {
                    // won both hands
                    answer = winQuestion();
                }
            }

        }
        if (answer == JOptionPane.YES_OPTION) {
            reset();
        } else {
            // check to see if we qualify for a highscore
            System.out.println("Checking for a high score");
            if (scores.isHighScore(playerCash)) {
                String initials = "";
                while (initials.length() > 3 || initials.length() == 0) {
                    JOptionPane.showInputDialog(this, "Enter Your Initials:\nMax of three characters", "New High Score",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                HighScore score = new HighScore(initials, (int) playerCash);
            }
        }
    }

    /**
     * @return
     */
    private int winQuestion() {
        if (player.isBlackjack()) {
            playerCash += playerBetHand1.payout(BLACKJACK_PAYOUT);
        } else {
            playerCash += playerBetHand1.payout(NORMAL_PAYOUT);
        }
        return JOptionPane.showConfirmDialog(this, "You win this hand\nDeal again?", "Deal again?",
                JOptionPane.YES_NO_OPTION);
    }

    /**
     * @return
     */
    private int loseQuestion(Hand hand) {
        playerCash += playerBetHand1.payout(0.0);
        String message = "";
        if (hand.isBusted()) {
            message = "Busted! You lose this hand\nDeal again?";
        } else {
            message = "You lose this hand\nDeal again?";
        }
        return JOptionPane.showConfirmDialog(this, message, "Deal again?", JOptionPane.YES_NO_OPTION);
    }

    /**
     * 
     */
    private void reset() {
        System.out.println("Answered yes");
        remove(player);
        remove(dealer);
        if (handTwoInPlay) {
            remove(playerSplitHand);
            lblValue2.setVisible(false);
            handTwoInPlay = false;
            arrow.setLocation(125, 300);
            arrow.setVisible(false);
        }
        System.out.println("removed player/dealer");

        System.out.println("making deal");
        lblValue1.setVisible(false);
        hit.setVisible(false);
        stand.setVisible(false);
        deal.setVisible(true);
        bettingPanel.setVisible(true);
        lblBet.setText("$" + playerBetHand1.getMoney());
        lblCash.setText("$" + playerCash);
        System.out.println("finished deal");
        revalidate();
        repaint();
    } // end method

    /**
     * Determines if the players hand can be split.
     * 
     * @return yes, if the players hand can be split
     */
    boolean isSplittable() {
        if (player.getCards().size() == 2 && player.getCard(0).getRank().equals(player.getCard(1).getRank())) {
            return true;
        }
        return false;
    } // end method

    /**
     * Splits the player's hand into two separate hands.
     */
    void split() {
        wasSplit = true;
        playerSplitHand = new Hand();
        handTwoInPlay = true;
        playerSplitHand.addCard(player.removeCard());
        // now add the new hand to the GUI
        playerSplitHand.setBounds(200, 351, (1 * 72), 96);        
        add(playerSplitHand);
        setComponentZOrder(playerSplitHand, 0);
        jbSplit.setVisible(false);
        arrow.setVisible(true);
        updateRemain();
        revalidate();
        repaint();
    } // end method

    public boolean isHandInPlay() {
        return handOneInPlay;
    }

    /**
     * New BlackJack GUI
     */
    public void createGUI() {
        setLayout(null);
        setBackground(new Color(7, 99, 36)); // Card Table Green
        deck.setBounds(60, 35, 79, 96); // set the deck size
        // System.out.println("Adding Deck");
        add(deck);
        remaining.setBounds(60 + 72, 30, 80, 50);
        add(remaining);
        dealer.setBounds(200, 100, (2 * 72), 96);
        // System.out.println("Adding Dealer");
        add(dealer);
        panel.setBorder(null);
        panel.setFocusable(false);
        panel.setBounds(60, 200, 80, 320);
        add(panel);
        arrow.setBounds(125, 300, 75, 40);
        add(arrow);
        arrow.setVisible(false);
        player.setBounds(200, 300, (2 * 72), 96);
        add(player);

        lblCash = new JLabel("$" + playerCash);
        lblCash.setHorizontalAlignment(SwingConstants.CENTER);
        lblCash.setBackground(Color.WHITE);
        lblCash.setForeground(Color.BLACK);
        lblCash.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblCash.setBounds(647, 479, 103, 23);
        add(lblCash);

        JLabel lblBalance = new JLabel("CURRENT BALANCE");
        lblBalance.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblBalance.setHorizontalAlignment(SwingConstants.CENTER);
        lblBalance.setForeground(Color.WHITE);
        lblBalance.setBounds(647, 506, 103, 14);
        add(lblBalance);

        JLabel lblBetBalance = new JLabel("BET");
        lblBetBalance.setHorizontalAlignment(SwingConstants.CENTER);
        lblBetBalance.setForeground(Color.WHITE);
        lblBetBalance.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblBetBalance.setBounds(647, 431, 103, 14);
        add(lblBetBalance);

        lblBet = new JLabel("$0");
        lblBet.setHorizontalAlignment(SwingConstants.CENTER);
        lblBet.setForeground(Color.BLACK);
        lblBet.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblBet.setBackground(Color.WHITE);
        lblBet.setBounds(647, 445, 103, 23);
        add(lblBet);

        lblInstructions = new JLabel("<html>Place Your Bet!<br>Table minimum: $5</html>");
        lblInstructions.setFont(new Font("Serif", Font.PLAIN, 30));
        lblInstructions.setBounds(412, 100, 238, 66);
        add(lblInstructions);
        playerSplitHand.setBounds(200, 351, (1 * 72), 96);
        add(playerSplitHand);
        lblValue1.setVisible(false);
        lblValue1.setFont(new Font("Courier Bold", Font.BOLD, 17));
        lblValue1.setBounds(200, 275, 75, 14);
        add(lblValue1);
        lblValue2.setVisible(false);

        JLabel lblPayout = new JLabel("BLACKJACK PAYS 3 TO 2");
        lblPayout.setForeground(Color.LIGHT_GRAY);
        lblPayout.setFont(new Font("Serif", Font.PLAIN, 20));
        lblPayout.setBounds(200, 235, 268, 40);
        add(lblPayout);

        bettingPanel = new JPanel();
        bettingPanel.setOpaque(false);
        bettingPanel.setBounds(374, 479, 263, 50);
        add(bettingPanel);
        bettingPanel.setLayout(new GridLayout(0, 4, 0, 0));

        JButton btnOne = new JButton("1");
        btnOne.setForeground(Color.WHITE);
        btnOne.setBackground(Color.BLUE);
        bettingPanel.add(btnOne);
        btnOne.addActionListener(al -> {
            if (playerCash >= 1) {
                playerBetHand1.add(1);
                playerCash -= 1;
                lblCash.setText("$" + playerCash);
                lblBet.setText("$" + playerBetHand1.getMoney());
            }
            System.out.println("finished one button processing");
        });
        JButton btnFive = new JButton("5");
        btnFive.setForeground(Color.WHITE);
        btnFive.setBackground(Color.RED);
        bettingPanel.add(btnFive);
        btnFive.addActionListener(al -> {
            if (playerCash >= 5) {
                playerBetHand1.add(5);
                playerCash -= 5;
                lblCash.setText("$" + playerCash);
                lblBet.setText("$" + playerBetHand1.getMoney());
            }
            System.out.println("finished five button processing");
        });
        JButton btnTwentyFive = new JButton("25");
        btnTwentyFive.setForeground(Color.WHITE);
        btnTwentyFive.setBackground(Color.ORANGE);
        bettingPanel.add(btnTwentyFive);
        btnTwentyFive.addActionListener(al -> {
            if (playerCash >= 25) {
                playerBetHand1.add(25);
                playerCash -= 25;
                lblCash.setText("$" + playerCash);
                lblBet.setText("$" + playerBetHand1.getMoney());
            }
            System.out.println("finished twenty five button processing");
        });
        JButton btnHundred = new JButton("100");
        btnHundred.setForeground(Color.WHITE);
        btnHundred.setBackground(Color.BLACK);
        bettingPanel.add(btnHundred);
        btnHundred.addActionListener(al -> {
            if (playerCash >= 100) {
                playerBetHand1.add(100);
                playerCash -= 100;
                lblCash.setText("$" + playerCash);
                lblBet.setText("$" + playerBetHand1.getMoney());
            }
            System.out.println("finished one hundred button processing");
        });

        lblValue2.setFont(new Font("Courier Bold", Font.BOLD, 17));
        lblValue2.setBounds(200, 458, 75, 14);
        add(lblValue2);

        frame = new JFrame("Black Jack");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setTitle("CGS BlackJack");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setResizable(false);

        createMenu(frame);
        frame.setContentPane(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        int BUTTON_SIZE = 80;
        panel.setLayout(null);
        panel.setBackground(new Color(7, 99, 36)); // Card Table Green
        createButtons(BUTTON_SIZE);
    }

    /**
     * @param BUTTON_SIZE
     */
    private void createButtons(int BUTTON_SIZE) {
        hit = new JButton("Hit");
        hit.setPreferredSize(new Dimension(60, 60));
        hit.setFont(new Font("Courier Bold", Font.BOLD, 17));
        // hit.setBackground(new Color(192, 108, 108));
        hit.setBounds(0, 0, BUTTON_SIZE, BUTTON_SIZE);
        panel.add(hit);
        hit.addActionListener(al -> {
            hit();
            System.out.println("finished hit button processing");
        });
        hit.setVisible(false);

        if (reload) {
            stand = new JButton("Stand 2");
        } else {
            stand = new JButton("Stand");
        }
        stand.setFont(new Font("Courier Bold", Font.BOLD, 17));
        // stand.setBackground(new Color(192, 108, 108));
        stand.setBounds(0, BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE);
        panel.add(stand);
        stand.addActionListener(al -> {
            stand();
            System.out.println("finished stand button processing");
        });
        stand.setVisible(false);

        deal = new JButton("Deal");
        deal.setBounds(0, 240, 80, 80);
        panel.add(deal);
        deal.setPreferredSize(new Dimension(60, 60));
        deal.setFont(new Font("Dialog", Font.BOLD, 17));
        deal.addActionListener(al -> {
            if (playerBetHand1.getMoney() > 0) {
                deal();
                deal.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "You must bet at lease the minimum bet!", "Must Bet",
                        JOptionPane.ERROR_MESSAGE);
            }
            System.out.println("finished deal button processing");

        });

        if (reload) {
            jbSplit = new JButton("Split 2");
        } else {
            jbSplit = new JButton("Split");
        }
        jbSplit.setFont(new Font("Courier Bold", Font.BOLD, 17));
        // split.setBackground(new Color(192, 108, 108));
        jbSplit.setBounds(0, 2 * BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE);
        jbSplit.addActionListener(al -> {
            if (isSplittable()) {
                split();
            }
            System.out.println("finished split button processing");
        });
    }

    /**
     * @param frame
     */
    private void createMenu(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnMenu = new JMenu("Menu");
        menuBar.add(mnMenu);

        JMenuItem mntmNewGame = new JMenuItem("New Game");
        mnMenu.add(mntmNewGame);
        mntmNewGame.addActionListener(ae -> {
            newGame();
            reset();
        });

        JMenuItem mntmSaveGame = new JMenuItem("Save Game");
        mnMenu.add(mntmSaveGame);
        mntmSaveGame.addActionListener(ae -> {
            saveGame();
        });

        JMenuItem mntmLoadGame = new JMenuItem("Load Game");
        mnMenu.add(mntmLoadGame);
        mntmLoadGame.addActionListener(ae -> {
            loadGame();
        });

        JMenuItem mntmHowToPlay = new JMenuItem("How to Play");
        mnMenu.add(mntmHowToPlay);
        mntmHowToPlay.addActionListener(ae -> {
            directions();
        });

        JMenuItem mntmHighScores = new JMenuItem("High Scores");
        mnMenu.add(mntmHighScores);
        mntmHighScores.addActionListener(ae -> {
            highScores();
        });

        JMenuItem mntmReturnToMain = new JMenuItem("Return to Main Menu");
        mntmReturnToMain.addActionListener(ae -> {
            frame.dispose();
        });
        mnMenu.add(mntmReturnToMain);
    }

    /**
     * 
     */
    private void highScores() {
        // TODO Auto-generated method stub
        JOptionPane.showMessageDialog(frame, scores);
    }

    /**
     * Shows the game play directions to the user.
     */
    private void directions() {
        JOptionPane.showMessageDialog(null,
                "The objective of Blackjack is to beat the dealer.  You can beat the dealer in one of the following ways:"
                        + "\n  Get 21 points on the first two cards (called a \"blackjack\" or \"natural\"), without a dealer blackjack"
                        + "\n  Reach a final score higher than the dealer without exceeding 21"
                        + "\n  Let the dealer draw additional cards until his or her hand exceeds 21"
                        + "\nThe dealer wins ties"
                        + "\nYou have the option to take an additional card \"hit\" or to not \"stand\""
                        + "\nIf your first two cards are the same rank, you can choose to split the hand"
                        + "\nSplit hands are played separately"
                        + "\nFace cards count as ten, and Aces count as 11 or 1.");
    }

    /**
     * Saves the state of the game to a file.
     */
    private void saveGame() {

        try (FileOutputStream filestream = new FileOutputStream("BlackJack.ser");
                ObjectOutputStream os = new ObjectOutputStream(filestream);) {
            os.writeObject(deck);
            os.writeObject(dealer);
            os.writeObject(player);
            os.writeObject(playerSplitHand);
            os.writeBoolean(handOneInPlay);
            os.writeBoolean(wasSplit);
            os.writeBoolean(doneHand1);
            os.writeBoolean(handTwoInPlay);
            os.writeObject(remaining);
            // os.writeObject(jbSplit);
            // os.writeObject(panel);
            // os.writeObject(arrow);
            os.writeObject(lblValue1);
            os.writeObject(lblValue2);
        } catch (IOException e) {
            e.printStackTrace();
        }

    } // end method

    private void loadGame() {
        remove(player);
        remove(dealer);
        remove(lblValue1);
        remove(lblValue2);
        remove(remaining);
        if (handTwoInPlay) {
            remove(playerSplitHand);
            lblValue2.setVisible(false);
            handTwoInPlay = false;
            arrow.setLocation(125, 300);
            arrow.setVisible(false);
        }
        System.out.println("removed player/dealer");
        revalidate();
        repaint();
        try (FileInputStream filestream = new FileInputStream("BlackJack.ser");
                ObjectInputStream os = new ObjectInputStream(filestream);) {
            deck = (Deck) os.readObject();
            dealer = (Hand) os.readObject();
            player = (Hand) os.readObject();
            playerSplitHand = (Hand) os.readObject();
            boolean b1 = os.readBoolean();
            boolean b2 = os.readBoolean();
            boolean b3 = os.readBoolean();
            boolean b4 = os.readBoolean();
            remaining = (JLabel) os.readObject();
            // jbSplit =
            // JButton test = (JButton) os.readObject();
            // // panel =
            // JPanel tests1 = (JPanel) os.readObject();
            // // arrow =
            // JPanel tests2 = (JPanel) os.readObject();
            lblValue1 = (JLabel) os.readObject();
            lblValue2 = (JLabel) os.readObject();

            System.out.println("loaded: " + "\n handOneInPlay " + b1 + "\n wasSplit      " + b2 + "\n doneHand1     "
                    + b3 + "\n handTwoInPlay " + b4 + "\n" + lblValue1.getText() + lblValue2.getText()
                    + isSplittable());

            handOneInPlay = b1;
            wasSplit = b2;
            doneHand1 = b3;
            handTwoInPlay = b4;
            add(dealer);
            add(player);
            add(remaining);
            add(lblValue1);
            add(lblValue2);
            if (isSplittable()) {
                panel.add(jbSplit);
                System.out.println("adding split button");
                jbSplit.setVisible(true);
            } else {
                jbSplit.setVisible(false);
            }
            if (wasSplit) {
                playerSplitHand.setBounds(200, 300 + 100, (1 * 72), 96);
                add(playerSplitHand);
                jbSplit.setVisible(false);
                arrow.setVisible(true);
            }
            updateRemain();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    } // end method

    /**
     * Runs a test of BlackJack by itself.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Blackjack blackjack = new Blackjack();
        blackjack.begin();
    } // end method
} // end class