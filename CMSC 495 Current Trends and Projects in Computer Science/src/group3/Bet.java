// File: Pot.java
// Author: Patrick Smith
// Date: Sep 18, 2016
// Course: CMSC 495
// Assignment: TODO
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: TODO
package group3;

/**
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 18, 2016
 */
public class Bet {

    private int bet;
    @SuppressWarnings("unused")
    private double pays;

    /**
     * 
     */
    public Bet() {
        bet = 0;
        pays = 3.0/2.0;
    }

    /**
     * @return the money
     */
    public int getMoney() {
        return bet;
    }

    public void add(int bet) {
        if (bet > 0) {
            this.bet += bet;
        } else {
            throw new IllegalArgumentException("Bets must me positive");
        }
    }

    public double payout(double payoutRatio) {
        double payout = bet * payoutRatio;
        bet = 0;
        return payout;
    }

}
