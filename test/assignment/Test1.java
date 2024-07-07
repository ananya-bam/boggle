package assignment;
import java.io.IOException;
import java.util.Scanner;
public class Test1 {
    public static void main(String[] args) throws IOException {
        //System.out.println("test something here!");

        // creates gameManager and gameDictionary object DONE
        GameManager game1 = new GameManager();
        GameDictionary dict1 = new GameDictionary();

        // loads dictionary with normal words text file DONE
        dict1.loadDictionary("words.txt");

        // testing isPrefix and contains DONE
        // BUG: both methods always return false
        System.out.println("isPrefix test with 'adroit' (T): " + dict1.isPrefix("adroit"));
        System.out.println("isPrefix test with 'adroiteeeee' (F): " + dict1.isPrefix("adroiteeeee"));
        System.out.println("contains test with 'adroitest' (T): " + dict1.contains("adroitest"));
        System.out.println("contains test with 'alsdjkfjkdla' (F): " + dict1.contains("alsdjkfjfkdla"));

        // create new game with original cubes file DONE
        game1.newGame(2, 1, "cubes.txt", dict1);



        //Boggle.main(null);

        /*
        // run game
        boolean run = true;
        Scanner scan = new Scanner(System.in);
        System.out.println("find a word: ");
        String str = scan.nextLine();

        while (run) {
            if (str.equals("stop")) {
                run = false;
                System.out.println("Game has ended. Final score is: " + game.getScores()[0]);//+ game.scores[0]);
                break;
            }
            str = str.toLowerCase();
            game.addWord(str, 0);
            System.out.println("current score: " + game.getScores()[0]);//+ game.scores[0]);
            System.out.println("find a word again: ");
            str = scan.nextLine();
        }*/

        // test different dictionary with other characters DONE
        // test different game board size w more/less cubes DONE
        // test that it's case insensitive
        // test that the game stops
        // test that scores add correctly for four letter, five letter
        // test blank dictionary
        // test words that would go back on its used squares
        // test words greater than entire board size
        // test same word twice
        // test words too short (have to be 4 letters at least)

        //System.out.println(dict.);

        // creates new gameManager and gameDictionary objects
        GameManager game2 = new GameManager();
        GameDictionary dict2 = new GameDictionary();

        //loads in dictionary with other ascii characters
        dict2.loadDictionary("dictDART.txt");

        //creates new game with a different size (size: 2 instead of 4) and new cubes file to account for this
        game2.newGame(2, 1, "cubes2x2.txt", dict2);

        //print out game board


        System.out.println("First instance of game: ");
        char[][] board = game2.getBoard();
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                System.out.print("[" + board[r][c] + "] ");
            }
            System.out.println();
        }

        boolean run = true;
        Scanner scan = new Scanner(System.in);
        System.out.println("find a word: ");
        String str = scan.nextLine();

        while (run) {
            if (str.equals("stop the game")) {
                run = false;
                System.out.println("Game has ended. Final score is: " + game1.getScores()[0]);
                break;
            }
            str = str.toLowerCase();
            game1.addWord(str, 0);
            System.out.println("current score: " + game1.getScores()[0]);
            System.out.println("find a word again: ");
            str = scan.nextLine();
        }


        /*
        GameManager game3 = new GameManager();

        game3.newGame(2, 1, "cubes2.txt", dict2);

        System.out.println("Second instance of game: ");

        char[][] board3 = game3.getBoard();
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                System.out.print("[" + board[r][c] + "] ");
            }
            System.out.println();
        }
        */

        //scan.close();

    }
}
