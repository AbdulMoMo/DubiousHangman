import java.util.*;

/**
 * Subclass of HangmanManager that makes the game more evil by forcing failure with one guess left.
 */
public class DubiousHangmanManager extends HangmanManager {


    /**
     * Creates new DubiousHangmanManager and calls parent constructor.
     *
     * @param dictionary of words to guess from
     * @param length of word to guess
     * @param guesses that player is granted
     */
    public DubiousHangmanManager (Collection<String> dictionary, int length, int guesses) {
        super(dictionary, length, guesses);
    }

    /**
     * Auto-fails user if guesses left < 1 by setting the word list to the first word that does
     * not include the user's guess.
     *
     * @param guess that user provided
     * @return number of times the user's guess matches the new pattern.
     */
    @Override
    public int record (char guess) {
        String tempWord;
        if (guessesLeft() == 1) {
            for (String word : words()) {
                tempWord = word;
                if (!tempWord.contains(guess + "")) {
                    words().clear();
                    words().add(tempWord);
                    break;
                }
            }
        }
        return super.record(guess);
    }

}
