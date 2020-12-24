import java.util.*;

public class Main {

    public static void main(String[] args) {

        // Setup number of guesses allowed
        int guessesRemaining = 3;

        // Create scanner object to read user input
        Scanner input = new Scanner(System.in);

        // Generate game word
        char[] GAME_WORD = generateGameWord();
        char[] gameBoard = GAME_WORD.clone();

        // Set game board to all _
        for(int itr=0; itr < gameBoard.length; itr++)
            gameBoard[itr] = "_".charAt(0);


        // Boolean to track state of game
        boolean game_over = false;
        
        // Print initial status
        System.out.println("Welcome to hangman. Here is your word to guess");
        printGameBoard(gameBoard);
        
        // Main game loop, use game_over boolean to be more readable than infinite while with breaks
        while(!game_over){

            System.out.println("Please enter a single valid character to guess");
            String letter = input.next();

            // Validate input is a singe char
            while(!validInput(letter)){
                System.out.println("Error: Invalid input provided. Please enter a single letter to guess");
                letter = input.next();
            }

            // Convert char to lower case so we can accept upper or lower case guesses
            letter = letter.toLowerCase(Locale.ROOT);

            System.out.println("You guessed " + letter);

            // Process the guessed letter
            if(!guessLetter(letter, gameBoard, GAME_WORD)){
                System.out.println("You guessed incorrectly. Minus one guess");
                guessesRemaining--;
            }

            // Print state of game board
            printGameBoard(gameBoard);
            System.out.println("Lives remaining: " + String.valueOf(guessesRemaining));

            // Check win and loss condition
            if(guessesRemaining <= 0){
                System.out.println("GAME OVER. Out of lives");
                game_over = true;
                break;
            }

            if(String.valueOf(gameBoard).equals(String.valueOf(GAME_WORD))){
                System.out.println("CONGRATULATIONS YOU WON");
                game_over = true;
                break;
            }
        }
    }

    /**
     * Helper function to print the game board with spaces between letters
     * @param gameBoard
     */
    private static void printGameBoard(char[] gameBoard){
        for(char c : gameBoard)
            System.out.print(c + " ");
        System.out.println();
    }


    /**
     * Helper function for processing a guessed letter
     * @param letter - letter that user provided as guess
     * @param gameBoard - gameBoard used to represent state of guessed characters and unknown
     * @param GAME_WORD - the selected GAME_WORD the user is trying to guess
     * @return boolean - return true if user guessed a letter correctly
     */
    private static boolean guessLetter(String letter, char[] gameBoard, char[] GAME_WORD){
        boolean correctGuess = false;

        // Iterate through gamestate and reveal all correctly guessed letters
        for(int itr = 0; itr < GAME_WORD.length; itr++){
            char guessedChar = letter.charAt(0);
            // Check this letter with the string
            if(GAME_WORD[itr] == guessedChar){
                gameBoard[itr] = guessedChar;
                correctGuess = true;
            }
        }

        return correctGuess;
    }


    /**
     * Helper function to validate input from user
     * @param input - String read in by Scanner.next
     * @return boolean - true for valid input
     */
    private static boolean validInput(String input){
        // Provided no input
        if(input.length() == 0 || input == null){
            return false;
        }

        // Provided more than one letter
        if(input.length() > 1){
            return false;
        }

        // Input is not a letter
        if(!Character.isLetter(input.charAt(0))){
            return false;
        }

        return true;
    }


    /**
     * Helper function to randomly select a word
     * @return char[] - returns a randomly selected word as char array
     */
    private static char[] generateGameWord(){
        // Create list to store game words, using arraylist allows for dynamically adding words instead of static array
        List<String> WORDS = new ArrayList<String>();
        WORDS.add("hat");
        WORDS.add("car");

        // create random object
        Random ran = new Random();

        // Generate random number between 1 and number of available WORDS
        int randomNum = ran.nextInt(WORDS.size());

        // Select random gameword from available options
        String GAME_WORD = WORDS.get(randomNum);

        // Return game word as char array as Strings are immutable
        return GAME_WORD.toCharArray();
    }
}
