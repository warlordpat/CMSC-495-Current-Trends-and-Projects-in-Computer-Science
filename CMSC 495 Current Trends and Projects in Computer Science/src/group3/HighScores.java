// File: HighScores.java
// Author: Patrick Smith
// Date: Sep 14, 2016
// Course: CMSC 495
// Assignment: TODO
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: TODO
package group3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 14, 2016
 */
public class HighScores implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 7079026133184545339L;
    private ArrayList<HighScore> highscores;

    HighScores() {
        highscores = new ArrayList<>(10);
    }

    void add(HighScore score) {
        if (highscores.isEmpty()) {
            // only need to save the current highscore
            highscores.add(score);
        } else {
            HighScore lowestHighScore = highscores.get(highscores.size() - 1);
            if (lowestHighScore.getScore() < score.getScore() || highscores.size() < 10) {
                if (highscores.size() == 10) {
                    highscores.remove(lowestHighScore);
                } // end if
                // Save new score
                highscores.add(score);
                Collections.sort(highscores, Collections.reverseOrder());
            }
        }
    } // end method
    
    public String toString() {
        String outputScores = "High Scores\n";
        if (highscores.isEmpty()) {
            return outputScores + "\nNo High Scores Yet!";
        }
        for (HighScore highScore : highscores) {
            outputScores += highScore.getInitials() + "\t" + highScore.getScore() + "\n";
        } // end for
        return outputScores;
    } // end method
} // end class
