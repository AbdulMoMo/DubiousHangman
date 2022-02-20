# DubiousHangman
Have you ever asked yourself, what if I wanted to play Hangman and have a bad time? Well then I have just the game for you! Enter a spin on Hangman that
will make you question your guessing ability to the point of **total failure!**

## How To Play
Simply run main.HangmanMain.java and a new game of Hangman will begin! You will be asked to supply these to numbers to define the state of the game upon execution
in this order:

- length : the number of letters in the word that you will try to guess
- guesses : how many **wrong** guesses are allowed before you fail the game 

If you correctly guess the word within the given number of wrong answered allowed, you win the game, otherwise...well you fail. 

To play the game again, simply run main.HangmanMain.java again. 

## How It Works
main.HangmanMain handles the basic file processing of the input dictionary, user interaction (taking and sending responses) as well as keeping track of the game
state. main.HangmanManager, and main.DubiousHangmanManager as well, is where the magic happens. The game initializes with a set of words from an input dictionary that 
match the desired letter count of a target word to guess from the user. With every guess, the game will associate patterns between the player's guess and the
available words to select from. 

As an example, if the player chooses to guess a five letter word, the game will start off with all five letter words from an input dictionary of over 100,000 words. 
With every character guess from the player, the game will iterate through the current set of words, and for each word, create a pattern mapping to the guess. 
If I have a set of words {play, jump, trap, buck, tray} and the player guesses “a” then I would have two pattern families: 

    - - - - : jump, buck

    - - a - : play, trap, tray

The game would then pick the new set of words to be chosen from as {play, trap, tray} with a ‘correct’ guess of the word having one ‘a’. The catch is that the 
set of words was picked because it provided the highest number of possible words for the player to guess from. So the game will keep trying 
to flip-flop on possible word families to choose from relative to whichever has the highest word count.

The player will only have a chance of winning the game if they are able to shrink the decision space of words to be considered by main.HangmanManager/main.DubiousHangmanManager
to just one word. With every matching guess from there on, the string pattern will creep every so closer to the "true" target word. That being said, it is not 
an easy task to shrink the decision space---hence the option to define the wrong guess threshold as high as you desire. 

## Design Considerations 
There are two variations of main.HangmanManager, the driver for the game logic, in this project: main.HangmanManager and main.DubiousHangmanManager. main.DubiousHangmanManager
largely follows the same execution flow as main.HangmanManager except for one key difference. If the player is down to one guess, if possible, the program
will look for a word in the available list that does not contain the player's latest guess. main.DubiousHangmanManager will then clear the list of words, and
add only the first word it found that does not match the player's guess. That way, when the game checks if the letter was a valid guess, the player automatically
fails as it's considered a wrong guess. 

Internally, I mainly leveraged TreeSets and TreeMaps to represents guessed characters, the pattern families and the current words considered. These variations
allow for a simple natural ordering of the input elements short of a comparator. To streamline shrinking the decision space, all trimming is done iteratively. 
That being said, I also attempted to compartmentalize methods, and their helpers, to distinct functions in order to maintain readability.

One important point I've walked away with after this implementation is the importance of encapsulation. Currently, main.HangmanMain calls on words() and guesses() to
get references to the objects that store the current words considered and the guessed characters respectively. Due to that implementation choice, main.HangmanMain would
have the ability to mutate these data structures and interfere with the state of the game. For simplicity's sake, and to streamline a "Proof of Concept" for
a more dubious Hangman I have kept the methods publicly accessible. Though moving forward, it is important to consider programs should be designed around 
modularity and mobility when it comes to locking down its composite pieces. 

New note: With a recent push as of 2/14, I have restricted access to words and letters by HangmainMain.

## Closing Thoughts
All the same, I had a fantastic time working on this small program! I believe it has also afforded me some great lessons to bring to my next small project...
**Evil Wordle**.
