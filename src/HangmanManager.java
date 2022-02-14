import java.util.*;

/**
 * EvilHangman handles internal game logic: contracting the decision space with user guesses and storing
 * data on game state.
 */
public class HangmanManager {

    /**
     * Guesses left.
     */
    private int guesses;
    /**
     * Current set of words considered.
     */
    protected TreeSet<String> words;
    /**
     * Guessed letters by user.
     */
    protected TreeSet<Character> letters;
    /**
     * Current pattern for largest word list.
     */
    private String pattern;
    /**
     * Mappings of word patterns to their families.
     */
    private TreeMap<String, TreeSet<String>> allPatterns;

    /**
     * Creates new HangmanManager and initializes class variables.
     *
     * @param dictionary of words to guess from
     * @param length of word to guess
     * @param guesses that player is granted
     *
     * @throws IllegalArgumentException if number of provided guesses is less than 0 or if
     * proposed word length is less than 1
     */
    public HangmanManager(Collection<String> dictionary, int length, int guesses) {
        if (length < 1 || guesses < 0) {
            throw new IllegalArgumentException();
        }
        this.letters = new TreeSet<>();
        this.words = new TreeSet<>();
        this.guesses = guesses;
        this.pattern = "";
        for (int i = 0; i < length; i++) {
            pattern += "-";
        }
        for (String word : dictionary) {
            if (word.length() ==  length) this.words.add(word);
        }
        allPatterns = new TreeMap<>();
    }

    /**
     * 'Getter' for words.
     *
     * @return Current set of words
     */
    public Set<String> words() {
        return this.words;
    }

    /**
     * 'Getter' for guesses.
     *
     * @return guesses left
     */
    public int guessesLeft() {
        return this.guesses;
    }

    /**
     * 'Getter' for guessed letters.
     *
     * @return guessed letters
     */
    public Set<Character> guesses() {
        return this.letters;
    }

    /**
     * 'Getter' for current pattern.
     *
     * @return current pattern for current pattern family
     * @throws IllegalStateException if the current word list is empty
     */
    public String pattern() {
        if (this.words.isEmpty()) {
            throw new IllegalStateException();
        }
        return this.pattern.replace("", " ").trim();
    }

    /**
     * Takes user guess and separates current word list off of pattern families and returns
     * the number of matching characters with the largest pattern family.
     *
     * @param guess that user provided
     * @return number of character matches to the 'target' word
     *
     * @throws IllegalStateException if the number of guesses left is less than 1 or the current
     * list of words is empty
     * @throws IllegalArgumentException if the current list of words is empty and the provided
     * character has already been guessed
     */
    public int record(char guess) {
        if (guessesLeft() < 1 || this.words.isEmpty()) {
            throw new IllegalStateException();
        } else if (!words.isEmpty() && letters.contains(guess)) {
            throw new IllegalArgumentException();
        }
        this.letters.add(guess);
        buildWordMap(guess);
        if (words.size() == 1) {
            finalWordGuesses(guess);
        } else {
            findLargestList();
        }
        return countMatches(guess);
    }


    /**
     * 'Helper' for record that creates the pattern mappings to compare against.
     *
     * @param guess that user provided
     */
    private void buildWordMap (char guess) {
        String tempPattern = "";
        for (String word : this.words) {
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == guess) {
                    tempPattern += guess;
                } else if (this.letters.contains(word.charAt(i))) {
                    tempPattern += word.charAt(i);
                } else {
                    tempPattern += "-";
                }
            }
            TreeSet<String> tempWords = allPatterns.get(tempPattern);
            if (tempWords == null) tempWords = new TreeSet<>();
            tempWords.add(word);
            allPatterns.put(tempPattern, tempWords);
            tempPattern = "";
        }
    }

    /**
     * 'Helper' for record that finds the largest word list amongst the pattern families.
     */
    private void findLargestList () {
        int size = 0;
        TreeSet<String> auxWordList = new TreeSet<>();
        for (String variant : allPatterns.keySet()) {
            TreeSet<String> tempWordList = allPatterns.get(variant);
            if(tempWordList.size() > size) {
                size = tempWordList.size();
                this.pattern = variant;
                auxWordList = tempWordList;
            }
        }
        this.words = auxWordList;
        allPatterns.clear();
    }

    /**
     * 'Helper' for record that counts how many times the user's guess appeared in the
     * new pattern
     *
     * @param guess that user provided
     * @return number of matches
     */
    private int countMatches (char guess) {
        int count = 0;
        for (int i = 0; i < this.pattern.length(); i++) {
            if (pattern.charAt(i) == guess) count++;
        }
        if (count == 0) this.guesses--;
        return count;
    }

    /**
     * Updates pattern based on user guess if there is only one word left
     *
     * @param guess that user provided
     */
    private void finalWordGuesses(char guess) {
        StringBuilder modifiedPattern = new StringBuilder(this.pattern);
        String lastWord = this.words.first();
        for (int i = 0; i < lastWord.length(); i++) {
            if (lastWord.charAt(i) == guess) {
                modifiedPattern.setCharAt(i, guess);
            }
        }
        this.pattern = modifiedPattern.toString();
    }

}
