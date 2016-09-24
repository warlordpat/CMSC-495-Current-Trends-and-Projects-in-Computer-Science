// File: Bet.java
// Author: Patrick Smith
// Date: Sep 18, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: Holds a bet for betting games.
package group3;

/**
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 18, 2016
 */
public class Bet {

    /**
     * The amount of money currently in the bet.
     */
    private int bet;

    /**
     * Creates a new empty bet.
     */
    public Bet() {
        bet = 0;
    }

    /**
     * Gets the money in the current bet.
     *
     * @return the money the money in the bet
     */
    public final int getMoney() {
        return bet;
    }

    /**
     * Adds money to the current bet.
     *
     * @param bet
     *            the amount of bet to add to the current bet
     */
    public final void add(final int bet) {
        if (bet > 0) {
            this.bet += bet;
        } else {
            throw new IllegalArgumentException("Bets must me positive");
        }
    }

    /**
     * Calculates how much the bet earned the player based on the payout ratio.
     *
     * @param payoutRatio
     *            the ration of bet to payout
     * @return the payout from the bet
     */
    public final double payout(final double payoutRatio) {
        double payout = bet * payoutRatio;
        bet = 0;
        return payout;
    }
}
