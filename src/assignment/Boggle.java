package assignment;

// implement your UI here! we should be able to run it with 
//      java assignment.Boggle

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

import assignment.BoggleGame.SearchTactic;

public class Boggle {

    private static GameManager game;
    private static GameDictionary dict;
    private static Scanner scan = new Scanner(System.in);
    private static int players;
    public static void main(String[] args) throws IOException {

       System.out.println("How many players are there?");
       int num = Integer.parseInt(scan.nextLine());

       players = num;

        boolean play = true;
        while (play) {
            playBoggle();
            System.out.println("Do you want to play another game? Say 'yes' if you do; if not, say 'no'.");
            String str = scan.nextLine();
            if (str.toLowerCase().equals("no")) {
                play = false;
            }
            else if (!str.toLowerCase().equals("yes")) {
                System.out.println("Not a valid response.");
            }
        }
        System.out.println("Thank you for playing!");
        scan.close();


        /*
        System.out.println("isPrefix test (T): " + dict.isPrefix("adroit"));
        System.out.println("isPrefix test (F): " + dict.isPrefix("adroiteeeee"));
        System.out.println("isPrefix test (T): " + dict.isPrefix("ADROIT"));
        System.out.println("contains test (T): " + dict.contains("adroitest"));
        System.out.println("contains test (F): " + dict.contains("alsdjkfjfkdla"));
        */
        
    }

    public static void playBoggle() {
        game = new GameManager();
        dict = new GameDictionary();

        try {
            dict.loadDictionary("words.txt");
            game.newGame(4, players, "cubes.txt", dict);
        } catch (IOException e) {
            System.err.println("Cannot load files.");
        }
        
        game.setSearchTactic(SearchTactic.SEARCH_BOARD);

        System.out.println("BOGGLE GAME BOARD: \n");
        game.viewGame();
        //game.setSearchTactic(SearchTactic.SEARCH_DICT);
        boolean run = true;
        //Scanner scan = new Scanner(System.in);
        String str;

        while (run) {
            for (int i = 0; i < players; i++) {
                System.out.println("Player " + i + ", find a word in the Boggle board. Type 'stop the game' to exit the game.");
                str = scan.nextLine();
                
                str = str.toLowerCase();
                game.addWord(str, i);
                System.out.println("Player " + i + ", your current score is " + game.scores[i] + ".\n");
                game.viewGame();
                
                if (str.equals("stop the game")) {
                    run = false;
                    System.out.println("Game has ended. ");
                    for (int j = 0; j < players; j++) {
                        System.out.print("Final score for Player " + j + " is " + game.scores[j]+".\n");
                    }
                    break;
                }
            }
        }
        System.out.println("Here are all of the words that you missed: \n");
        System.out.println(game.setComputerWords());
        //scan.close();
    }

}
