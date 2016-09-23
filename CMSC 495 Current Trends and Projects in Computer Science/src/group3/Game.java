// File: Game.java
// Author: Patrick Smith
// Date: Sep 23, 2016
// Course: CMSC 495
// Assignment: TODO
// Platform: Win10 x64 Java build 1.8.0_102
// Purpose: TODO
package group3;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

/**
 * @author Patrick Smith
 * @version 1.0
 * @since Sep 23, 2016
 */
public interface Game {
    /**
     * The maximum length of the initials for a high score.
     */
    int INITIAL_LENGTH = 3;

    /**
     * If a high score exists, loads it, if not creates it.
     *
     * @param name
     *            The name of the game
     */
    default HighScores loadOrCreateScores(String name) {
        String path = System.getProperty("user.home");
        path += File.separator + "CGS";
        File customDir = new File(path);
        File scoreFile = new File(customDir, name + ".score");
        if (customDir.exists()) {
            System.out.println(customDir + " already exists");
            // load a score file

            if (scoreFile.exists()) {
                System.out.println(scoreFile + " already exists");
                return loadHighScores(scoreFile);
            } else {
                // create new scorefile
                System.out.println(scoreFile + " created");
                HighScores scores = new HighScores();
                saveHighScores(scoreFile, scores);
                return scores;
            }

        } else if (customDir.mkdirs()) {
            System.out.println(customDir + " was created");
            System.out.println(scoreFile + " created");
            // Make a blank score file
            HighScores scores = new HighScores();
            saveHighScores(scoreFile, scores);
            return scores;
        } else {
            System.out.println(customDir + " was not created");
            // throw an error, why can't we access the user dir?
            return new HighScores();
        }
    }

    /**
     * Saves the high scores to a given file.
     *
     * @param scoreFile
     *            The file to save scores to
     */
    default void saveHighScores(final File scoreFile, HighScores scores) {
        try (FileOutputStream filestream = new FileOutputStream(scoreFile);
                ObjectOutputStream os = new ObjectOutputStream(filestream);) {
            os.writeObject(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the high scores from a given file.
     *
     * @param scoreFile
     *            The file to load scores from
     */
    default HighScores loadHighScores(final File scoreFile) {
        try (FileInputStream filestream = new FileInputStream(scoreFile);
                ObjectInputStream os = new ObjectInputStream(filestream);) {
            return (HighScores) os.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new HighScores();
    }

    /**
     * Loads the game state from a file.
     */
    void loadGame();

    /**
     * Saves the state of the game to a file.
     */
    void saveGame();

    /**
     * Gets the player's initials for the high score records.
     *
     * @return the initials of the player
     */
    default String getInitials(Component frame) {
        String initials = "";
        while (initials == null || initials.length() > INITIAL_LENGTH || initials.length() == 0) {
            initials = JOptionPane.showInputDialog(frame, "Enter Your Initials:\nMax of three characters",
                    "New High Score", JOptionPane.INFORMATION_MESSAGE);
        }
        return initials;
    } // end method
}
