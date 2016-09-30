// File: HighScores.java
// Author: Patrick Smith
// Date: Sep 14, 2016
// Course: CMSC 495
// Assignment: Final Project, Group 3
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: Holds high scores for use in the CGS
package group3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The high score list for use in the CGS.
 *
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 14, 2016
 */
public class HighScores implements Serializable {

    /**
     * The maximum number of high scores to save in the list.
     */
    private static final int MAX_SCORES = 10;
    /**
     * The serial number for serialization.
     */
    private static final long serialVersionUID = 7079026133184545339L;
    /**
     * The high scores.
     */
    private ArrayList<HighScore> highscores;

    /**
     * Creates a new empty high score list.
     */
    HighScores() {
        highscores = new ArrayList<>(MAX_SCORES);
    }

    /**
     * Adds a score to the high score list.
     *
     * @param score
     *            the score to add to the list
     * @return true, if the score was a high score and added
     */
    final boolean add(final HighScore score) {
        if (highscores.isEmpty()) {
            // only need to save the current highscore
            highscores.add(score);
            return true;
        } else {
            HighScore lowestHighScore = highscores.get(highscores.size() - 1);
            if (isHighScore(score.getScore())) {
                if (highscores.size() == MAX_SCORES) {
                    highscores.remove(lowestHighScore);
                } // end if
                  // Save new score
                highscores.add(score);
                Collections.sort(highscores, Collections.reverseOrder());
                return true;
            }
            return false;
        }
    } // end method

    /**
     * Formats the high scores as a string.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        String outputScores = "High Scores\n";
        if (highscores.isEmpty()) {
            return outputScores + "\nNo High Scores Yet!";
        }
        for (HighScore highScore : highscores) {
            outputScores += highScore.getInitials() + "               " + highScore.getScore() + "\n";
        } // end for
        return outputScores + "";
    } // end method

    /**
     * Checks if a score qualifies as a high score.
     *
     * @param score
     *            the score to check
     * @return true, if the score qualifies as a high score
     */
    public final boolean isHighScore(final double score) {
        if (highscores.size() == 0) {
            return true;
        }
        HighScore lowestHighScore = highscores.get(highscores.size() - 1);
        return lowestHighScore.getScore() < score || highscores.size() < MAX_SCORES;

    }
} // end class
