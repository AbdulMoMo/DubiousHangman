package Tests;

import main.HangmanManager;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for HangmanManager
 */
public class HangmanManagerTest {

    private HangmanManager hangman;


    /**
     * Initializes HangmanManger for testing functionality and exception cases.
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
        this.hangman = new HangmanManager(dictionary, length, guesses);
    }

    /**
     * Checks if pattern() throws an IllegalStateException if words is empty
     * @throws FileNotFoundException if dictionary file does not exist
     */
    @Test
    public void ifWordListEmptythenPatternThrowsIllegalStateException() throws FileNotFoundException {
        setUp("emptyDictionary", 5, 5);
        assertThrows(IllegalStateException.class,
                () -> this.hangman.pattern());
    }

    /**
     * Checks if constructor throws an IllegalArgumentException if input length is less than one
     * @throws FileNotFoundException if dictionary file does not exist
     */
    @Test
    public void ifLengthLessthanOnethenConstructorThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> setUp("dictionary", 0, 5));
    }

    /**
     * Checks if constructor throws an IllegalArgumentException if input guesses is less than one
     * @throws FileNotFoundException if dictionary file does not exist
     */
    @Test
    public void ifGuessesLessthanOnethenConstructorThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> setUp("dictionary", 5, 0));
    }

    /**
     * Checks if record() throws an IllegalStateException if guesses is less than one
     * @throws FileNotFoundException if dictionary file does not exist
     */
    @Test
    public void ifGuessesLessthanOnethenRecordThrowsIllegalStateException() throws FileNotFoundException {
        setUp("dictionary", 5, 1);
        this.hangman.record('a');
        assertThrows(IllegalStateException.class,
                () -> this.hangman.record('e'));
    }

    /**
     * Checks if record() throws an IllegalStateException if words is empty
     * @throws FileNotFoundException if dictionary file does not exist
     */
    @Test
    public void ifWordsEmptythenRecordThrowsIllegalStateException() throws FileNotFoundException {
        setUp("emptyDictionary", 5, 5);
        assertThrows(IllegalStateException.class,
                () -> this.hangman.record('e'));
    }

    /**
     * Checks if record() throws an IllegalArgumentException if words is not empty and letters contains the input guess
     * @throws FileNotFoundException if dictionary file does not exist
     */
    @Test
    public void ifWordsNotEmptyandLettersContainsGuessthenRecordThrowsIllegalArgumentException() throws FileNotFoundException {
        setUp("dictionary", 5, 5);
        this.hangman.record('a');
        assertThrows(IllegalArgumentException.class,
                () -> this.hangman.record('a'));
    }


    /**
     * Checks if sample run of the game has user win with good
     * @throws FileNotFoundException if dictionary file does not exist
     */
    @Test
    public void testGamewithFourLetterWordandSevenWrongGuessesUserWinswithGood() throws FileNotFoundException {
        setUp("testDictionary", 4, 7);
        String target = "good";
        this.hangman.record('e');
        this.hangman.record('o');
        this.hangman.record('t');
        this.hangman.record('f');
        this.hangman.record('c');
        this.hangman.record('n');
        this.hangman.record('d');
        this.hangman.record('h');
        this.hangman.record('g');
        assertEquals(target, this.hangman.words().iterator().next());
    }

    /**
     * Checks if sample run of the game has user win with good
     * @throws FileNotFoundException if dictionary file does not exist
     */
    @Test
    public void altTestGamewithFourLetterWordandSevenWrongGuessesUserWinswithGood() throws FileNotFoundException {
        setUp("testDictionary", 4, 7);
        String target = "good";
        this.hangman.record('l');
        this.hangman.record('h');
        this.hangman.record('e');
        this.hangman.record('o');
        this.hangman.record('f');
        this.hangman.record('c');
        this.hangman.record('g');
        this.hangman.record('d');
        assertEquals(target, this.hangman.words().iterator().next());
    }

    /**
     * Checks if sample run of the game has user win with ibex
     * @throws FileNotFoundException if dictionary file does not exist
     */
    @Test
    public void testGamewithFourLetterWordandSevenWrongGuessesUserWinswithIbex() throws FileNotFoundException {
        setUp("testDictionary", 4, 8);
        String target = "ibex";
        this.hangman.record('u');
        this.hangman.record('t');
        this.hangman.record('o');
        this.hangman.record('e');
        this.hangman.record('w');
        this.hangman.record('x');
        this.hangman.record('r');
        this.hangman.record('b');
        this.hangman.record('i');
        assertEquals(target, this.hangman.words().iterator().next());
    }

    /**
     * Checks if sample run of the game fails user with 'clucks' with these input letters
     * @throws FileNotFoundException if dictionary file does not exist
     */
    @Test
    public void sampleGamewithSixLetterWordandTwelveWrongGuessesFailsUserwithClucks() throws FileNotFoundException {
        setUp("dictionary", 6, 12);
        String target = "clucks";
        this.hangman.record('a');
        this.hangman.record('e');
        this.hangman.record('i');
        this.hangman.record('o');
        this.hangman.record('u');
        this.hangman.record('y');
        this.hangman.record('g');
        this.hangman.record('n');
        this.hangman.record('t');
        this.hangman.record('r');
        this.hangman.record('s');
        this.hangman.record('l');
        this.hangman.record('p');
        this.hangman.record('m');
        this.hangman.record('b');
        assertEquals(target, this.hangman.words().iterator().next());
    }

    /**
     * Checks if sample run of the game has user win with upswinging
     * @throws FileNotFoundException if dictionary file does not exist
     */
    @Test
    public void sampleGamewithTenLetterWordandFifteenWrongGuessesUserWinswithUpswinging() throws FileNotFoundException {
        setUp("dictionary", 10, 15);
        String target = "upswinging";
        this.hangman.record('e');
        this.hangman.record('o');
        this.hangman.record('a');
        this.hangman.record('i');
        this.hangman.record('u');
        this.hangman.record('y');
        this.hangman.record('t');
        this.hangman.record('g');
        this.hangman.record('n');
        this.hangman.record('l');
        this.hangman.record('h');
        this.hangman.record('m');
        this.hangman.record('r');
        this.hangman.record('s');
        this.hangman.record('w');
        assertEquals(target, this.hangman.words().iterator().next());
    }
}