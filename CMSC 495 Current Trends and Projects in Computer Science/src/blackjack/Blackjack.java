// File: Blackjack.java
// Author: Patrick Smith
// Date: Aug 26, 2016
// Course: CMSC 495
// Assignment: TODO
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: TODO
package blackjack;

import java.util.List;
import java.util.Scanner;

/**
 * @author Patrick Smith
 * @version 1.0
 * @since Aug 26, 2016
 */
public class Blackjack {
    private Deck deck;
    private Hand dealer, player;
    private boolean handInPlay;
    private boolean split;
    private Hand playerSplitHand;
    private boolean doneHand1;
    private boolean handTwoInPlay;

    /**
     * 
     */
    public Blackjack() {
        deck = new Deck();
        deck.shuffle();
        dealer = new Hand();
        player = new Hand();
        handInPlay = false;
        handTwoInPlay = false;
        split = false;
    }

    void display() {
        if (handInPlay || handTwoInPlay) {
            if (split) {
                System.out.println("Player Hand 1: " + player);
                System.out.println("Player Hand 2: " + playerSplitHand);
            } else {
                System.out.println("Player Hand: " + player);
            }
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
            System.out.println("Dealer Hand: " + dealerString);
        } else {
            if (!split) {
                System.out.println("Player Hand: " + player + " " + player.scoreHand());
                System.out.println("Dealer Hand: " + dealer + " " + dealer.scoreHand());
            } else {
                System.out.println("Player Hand 1: " + player + " " + player.scoreHand());
                System.out.println("Player Hand 2: " + playerSplitHand + " " + playerSplitHand.scoreHand());
                System.out.println("Dealer Hand: " + dealer + " " + dealer.scoreHand());
                split = false;
            }
        }
    }

    void deal() {
        if (deck.size() < 8) {
            deck = new Deck();
            deck.shuffle();
            System.out.println("\nReshuffling\n");
        } // end if
        if (!handInPlay) {
            dealer = new Hand();
            player = new Hand();
            // player.addCard(deck.deal());
            player.addCard(new Card(Rank.ACE, Suit.CLUB));
            player.addCard(new Card(Rank.ACE, Suit.DIAMOND));
            dealer.addCard(deck.deal());
            // player.addCard(deck.deal());
            dealer.addCard(deck.deal());
            handInPlay = true;
        } // end if
    } // end method

    boolean isSplittable() {
        if (player.getCards().size() == 2 && player.getCard(0).getRank().equals(player.getCard(1).getRank())) {
            return true;
        }
        return false;
    }

    void hitOne() {
        if (handInPlay) {
            player.addCard(deck.deal());
            if (player.isBusted() && !split) {
                System.out.println("Busted! You lose");
                handInPlay = false;
            } else if (player.isBusted() && split) {
                System.out.println("Busted Hand One!");
                standOne();
            } // end else
        } // end if
    } // end method

    /**
     * 
     */
    private void hitTwo() {
        if (handTwoInPlay) {
            playerSplitHand.addCard(deck.deal());
            if (playerSplitHand.isBusted() && player.isBusted()) {
                System.out.println("Busted! You lose");
                handTwoInPlay = false;
            } else if (playerSplitHand.isBusted() && !player.isBusted()) {
                standTwo();
            } // end else
        } // end if

    }

    void standOne() {
        if (handInPlay) {
            handInPlay = false;
            doneHand1 = true;
            if (handTwoInPlay) {
                System.out.println("Play Hand Two");
            } else {
                while (dealer.scoreHand() <= 17) {
                    dealer.addCard(deck.deal());
                }
                if (dealer.scoreHand() >= player.scoreHand() && !dealer.isBusted()) {
                    System.out.println("You lose");
                } else {
                    System.out.println("You win");
                }
            }
        }
    }

    /**
     * 
     */
    private void standTwo() {
        if (handTwoInPlay) {
            handTwoInPlay = false;
        }
        while (dealer.scoreHand() <= 17) {
            dealer.addCard(deck.deal());
        }
        if (dealer.isBusted() && (!player.isBusted() || !playerSplitHand.isBusted())) {
            System.out.println("Dealer busted, you win");
        } else if (player.isBusted() && playerSplitHand.isBusted()) {
            // Both hands busted
            System.out.println("You lose, busted both hands");
        } else if (player.isBusted() && dealer.scoreHand() >= playerSplitHand.scoreHand()) {
            // 1st hand busted, dealer better than 2nd hand
            System.out.println("You lose hand 2, busted hand 1");
        } else if (playerSplitHand.isBusted() && dealer.scoreHand() >= player.scoreHand()) {
            // 2nd hand busted, dealer better than 1st hand
            System.out.println("You lose hand 1, busted hand 2");
        } else if (dealer.scoreHand() >= player.scoreHand() && dealer.scoreHand() >= playerSplitHand.scoreHand()) {
            // dealer better than both hands
            System.out.println("You lose");
        } else if (dealer.scoreHand() >= player.scoreHand() && dealer.scoreHand() < playerSplitHand.scoreHand()) {
            // dealer better than hand 1, less than 2
            System.out.println("You win Hand 1, You lose Hand 2");
        } else if (dealer.scoreHand() < player.scoreHand() && dealer.scoreHand() >= playerSplitHand.scoreHand()) {
            System.out.println("You win Hand 2, You lose Hand 1");
        } else {
            // neither hand busted, dealer worse than both hands
            System.out.println("You win both hands");
        }
    }

    /**
     * @return
     */
    private boolean isHandTwoInPlay() {
        return handTwoInPlay;
    }

    /**
     * 
     */
    void split() {
        split = true;
        playerSplitHand = new Hand();
        handTwoInPlay = true;
        playerSplitHand.addCard(player.removeCard(0));
    } // end method

    boolean isHandInPlay() {
        return handInPlay;
    }

    public static void main(String[] args) {
        Blackjack game = new Blackjack();
        game.deal();
        Scanner stdin;
        stdin = new Scanner(System.in);
        while (true) {
            game.display();
            if (game.isHandInPlay() || game.isHandTwoInPlay()) {
                if (game.isSplittable() && !game.split) {
                    System.out.print("(H)it, (S)tand, or S(p)lit? ");
                    if (stdin.hasNext()) {
                        String move = stdin.next();
                        switch (move) {
                        case "h":
                        case "H":
                            if (game.isHandInPlay()) {
                                game.hitOne();
                            }
                            break;
                        case "s":
                        case "S":
                            if (game.isHandInPlay()) {
                                game.standOne();
                            }
                            break;
                        case "p":
                        case "P":
                            if (game.isHandInPlay()) {
                                game.split();
                            }
                            break;
                        } // end switch
                    } // end if
                } else if (game.split && game.isHandTwoInPlay()) {
                    if (game.doneHand1) {
                        System.out.print("(H)it or (S)tand on Hand 2? ");
                        if (stdin.hasNext()) {
                            String move = stdin.next();
                            switch (move) {
                            case "h":
                            case "H":
                                if (game.isHandTwoInPlay()) {
                                    game.hitTwo();
                                }
                                break;
                            case "s":
                            case "S":
                                if (game.isHandTwoInPlay()) {
                                    game.standTwo();
                                }
                                break;
                            } // end switch
                        } // end if
                    } else if (game.isHandInPlay()) {
                        System.out.print("(H)it or (S)tand on Hand 1? ");

                        if (stdin.hasNext()) {
                            String move = stdin.next();
                            switch (move) {
                            case "h":
                            case "H":
                                if (game.isHandInPlay()) {
                                    game.hitOne();
                                }
                                break;
                            case "s":
                            case "S":
                                if (game.isHandInPlay()) {
                                    game.standOne();
                                }
                                break;
                            } // end switch
                        }
                    }
                } else {
                    System.out.print("(H)it or (S)tand? ");
                    if (stdin.hasNext()) {
                        String move = stdin.next();
                        switch (move) {
                        case "h":
                        case "H":
                            if (game.isHandInPlay()) {
                                game.hitOne();
                            }
                            break;
                        case "s":
                        case "S":
                            if (game.isHandInPlay()) {
                                game.standOne();
                            }
                            break;
                        } // end switch
                    }
                }
            } else {
                System.out.print("(D)eal another hand or (q)uit? ");
                if (stdin.hasNext()) {
                    String move = stdin.next();
                    switch (move) {
                    case "d":
                    case "D":
                        if (!game.isHandInPlay()) {
                            game.deal();
                        }
                        break;
                    case "q":
                    case "Q":
                        if (!game.isHandInPlay()) {
                            stdin.close();
                            return;
                        }
                        break;
                    } // end switch
                } // end if
            }

        } // end while

    } // end method

} // end class
