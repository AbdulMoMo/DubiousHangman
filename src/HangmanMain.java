import java.util.*;
import java.io.*;

/**
 * Main module for DubiousHangman. Handles game state, file processing and user interaction. Reads a dictionary of words
 * that is fed into HangmanManager/DubiousHangmanManager to play a game. This version of hangman cheats by contracting
 * the decision space of given words based on each of the player's guesses.
 */
public class HangmanMain  {
    /**
     * Reference to relative dictionary file location.
     */
    public static final String DICTIONARY_FILE = "dictionary";
    /**
     * Whether to show count of current words considered by the game
     */
    public static final boolean SHOW_COUNT = false;

    /**
     * Creates EvilHangman and starts game.
     *
     * @param args any command line arguments
     * @throws FileNotFoundException if cannot find dictionary
     */
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to a totally normal game of Hangman.");
        System.out.println();

        Scanner input = new Scanner(new File(DICTIONARY_FILE));
        List<String> dictionary = new ArrayList<>();
        while (input.hasNext())
            dictionary.add(input.next().toLowerCase());

        Scanner console = new Scanner(System.in);
        System.out.print("What length word do you want to use? ");
        int length = console.nextInt();
        System.out.print("How many wrong answers allowed? ");
        int max = console.nextInt();
        System.out.println();

        List<String> dictionary2 = Collections.unmodifiableList(dictionary);
        HangmanManager hangman = new DubiousHangmanManager(dictionary2, length, max);
        if (hangman.words().isEmpty()) {
            System.out.println("No words of that length in the dictionary.");
        } else {
            playGame(console, hangman);
            showResults(hangman);
        }
    }

    /**
     * Takes guesses from user to progress the game and outputs guess matches.
     *
     * @param console for reading user input
     * @param hangman for managing game
     */
    public static void playGame(Scanner console, HangmanManager hangman) {
        while (hangman.guessesLeft() > 0 && hangman.pattern().contains("-")) {
            System.out.println("guesses : " + hangman.guessesLeft());
            if (SHOW_COUNT) {
                System.out.println("words   : " + hangman.words().size());
            }
            System.out.println("guessed : " + hangman.guesses());
            System.out.println("current : " + hangman.pattern());
            System.out.print("Your guess? ");
            char ch = console.next().toLowerCase().charAt(0);
            if (hangman.guesses().contains(ch)) {
                System.out.println("You already guessed that");
            } else {
                int count = hangman.record(ch);
                if (count == 0) {
                    System.out.println("Sorry, there are no " + ch + "'s");
                } else if (count == 1) {
                    System.out.println("Yes, there is one " + ch);
                } else {
                    System.out.println("Yes, there are " + count + " " + ch +
                            "'s");
                }
            }
            System.out.println();
        }
    }

    /**
     * Prints results of the game.
     *
     * @param hangman for pulling the target word
     */
    public static void showResults(HangmanManager hangman) {
        String answer = hangman.words().iterator().next();
        System.out.println("answer = " + answer);
        if (hangman.guessesLeft() > 0) {
            System.out.println("You beat me");
        } else {
            System.out.println("Sorry, you lose");
        }
    }
}
