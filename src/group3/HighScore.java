// File: HighScore.java
// Author: Patrick Smith
// Date: Sep 14, 2016
// Course: CMSC 495
// Assignment: TODO
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: Represents an individual high score with score and initials.
package group3;

import java.io.Serializable;

/**
 * Holds a High score.
 *
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 14, 2016
 */
public class HighScore implements Comparable<HighScore>, Serializable {

    /**
     * The serial number for serialization.
     */
    private static final long serialVersionUID = 490653992209453033L;
    /**
     * The score.
     */
    private final double score;
    /**
     * The initials of the person with the high score.
     */
    private final String initials;

    /**
     * Creates a HighScore.
     *
     * @param initials
     *            the initials
     * @param score
     *            the score
     */
    public HighScore(final String initials, final double score) {
        this.initials = initials;
        this.score = score;
    } // end constructor

    /**
     * @return the score
     */
    public final double getScore() {
        return score;
    } // end method

    /**
     * @return the initials
     */
    public final String getInitials() {
        return initials;
    } // end method

    @Override
    public final int compareTo(final HighScore h) {
        if (this.score < h.score) {
            return -1;
        } else if (this.score > h.score) {
            return 1;
        } else {
            // they are equal, check initials
            return this.initials.compareTo(h.initials);
        } // end else
    } // end method

    @Override
    public final String toString() {
        return initials + ", " + score;
    } // end method
} // end class
