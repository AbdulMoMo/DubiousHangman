package Tests;

import main.DubiousHangmanManager;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for DubiousHangmanManager
 */
public class DubiousHangmanManagerTest {

    private DubiousHangmanManager dubiousHangman;


    /**
     * Initializes DubiousHangmanManger for testing functionality and exception cases.
     * @param dictionaryFile of dictionary to use
     * @param length of word to guess
     * @param guesses (wrong) that user has before failing
     * @throws FileNotFoundException if dictionary file does not exist
     */
    public void setUp(String dictionaryFile, int length, int guesses) throws FileNotFoundException {
        Scanner input = new Scanner(new File(dictionaryFile));
        List<String> dictionary = new ArrayList<>();
        while (input.hasNext())
            dictionary.add(input.next().toLowerCase());
        this.dubiousHangman = new DubiousHangmanManager(dictionary, length, guesses);
    }

    /**
     * Checks if record() forces failure if user has one guess left
     * @throws FileNotFoundException if dictionary file does not exist
     */
    @Test
    public void ifGuessesLeftEqualtoOnethenRecordForcesFailure() throws FileNotFoundException {
        setUp("dictionary", 5, 2);
        this.dubiousHangman.record('a');
        this.dubiousHangman.record('b');
        assertEquals(this.dubiousHangman.words().size(), 1);
        assertFalse(this.dubiousHangman.words().iterator().next().contains("b"));
    }

    /**
     * Chceks if words() returns an immutable set
     * @throws FileNotFoundException if dictionary file does not exist
     */
    @Test
    public void wordsReturnsImmutableSet() throws FileNotFoundException {
        setUp("dictionary", 5, 5);
        assertThrows(UnsupportedOperationException.class,
                () -> this.dubiousHangman.words().add("aeee"));
    }

    /**
     * Checks if guesses() returns an immutable set
     * @throws FileNotFoundException if dictionary file does not exist
     */
    @Test
    public void guessesReturnsImmutableSet() throws FileNotFoundException {
        setUp("dictionary", 5, 5);
        assertThrows(UnsupportedOperationException.class,
                () -> this.dubiousHangman.guesses().add('a'));
    }

}