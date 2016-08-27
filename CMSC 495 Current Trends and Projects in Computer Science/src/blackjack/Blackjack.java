// File: Blackjack.java
// Author: Patrick Smith
// Date: Aug 26, 2016
// Course: CMSC 451
// Assignment: 
// Platform: Win8.1 x64 Java build 1.8.0_91
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
    boolean handInPlay;

    /**
     * 
     */
    public Blackjack() {
        deck = new Deck();
        deck.shuffle();
        dealer = new Hand();
        player = new Hand();
        handInPlay = false;
    }

    void display() {
        if (handInPlay) {
            System.out.println("Player Hand: " + player);
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
            System.out.println("Dealer Hand" + dealerString);
        } else {
            System.out.println("Player Hand: " + player + " " + player.scoreHand());
            System.out.println("Dealer Hand: " + dealer + " " + dealer.scoreHand());
        }
    }

    void deal() {
        if (deck.size() < 8) {
            deck = new Deck();
            deck.shuffle();
            System.out.println("\nReshuffling\n");
        }
        if (!handInPlay) {
            dealer = new Hand();
            player = new Hand();
            player.addCard(deck.deal());
            player.addCard(deck.deal());
            dealer.addCard(deck.deal());
            dealer.addCard(deck.deal());
            handInPlay = true;
        }
    }

    void hit() {
        if (handInPlay) {
            player.addCard(deck.deal());
            if (player.isBusted()) {
                System.out.println("Busted! You lose");
                handInPlay = false;
            } // end if
        } // end if
    } // end method

    void stand() {
        if (handInPlay) {
            while (dealer.scoreHand() <= 17) {
                dealer.addCard(deck.deal());
            }
            handInPlay = false;
            if (dealer.scoreHand() >= player.scoreHand() && !dealer.isBusted()) {
                System.out.println("You lose");
            } else {
                System.out.println("You win");
            }
        }
    }

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
            if (game.isHandInPlay()) {
                System.out.print("(H)it or (S)tand? ");
            } else {
                System.out.print("(D)eal another hand or (q)uit? ");
            }
            if (stdin.hasNext()) {
                String move = stdin.next();
                switch (move) {
                case "h":
                case "H":
                    if (game.isHandInPlay()) {
                        game.hit();
                    }
                    break;
                case "s":
                case "S":
                    if (game.isHandInPlay()) {
                        game.stand();
                    }
                    break;
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
        } // end while
    } // end method
} // end class
