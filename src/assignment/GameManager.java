package assignment;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;


public class GameManager implements BoggleGame {

    // static char[][] cubes = new char[16][6];
    // static char[][] board = new char[4][4];
    private char[][] cubes;
    private char[][] board;
    int[] scores;
    private ArrayList<String> wordList = new ArrayList<>();
    //TreeSet<String> validWords = new TreeSet<>();
    private ArrayList<String> validWords = new ArrayList<>();
    private String checkWord = "";
    private ArrayList<Point> usedCubes = new ArrayList<>();
    private SearchTactic tact = BoggleGame.SEARCH_DEFAULT;
    private GameDictionary dict = new GameDictionary();
    private ArrayList<Point> lastAdded = new ArrayList<>();
    private ArrayList<ArrayList<Point>> wordPoints = new ArrayList<ArrayList<Point>>();
    int numPlayers;
    private ArrayList<String> computerWords = new ArrayList<>();


    @Override
    public int addWord(String word, int player) {


        //System.out.println(validWords);
        if (!dict.contains(word)) {
            System.out.println("This word does not exist in the dictionary.");
            return 0;
        }
        else if (!(validWords.contains(word.toUpperCase()))) {
            System.out.println("This word does not exist in the board.");
            return 0;
        }
        else if (word.length() < 4) {
            System.out.println("This word is too short. It must be at least four letters.");
            return 0;
        }
        else if (wordList.contains(word)) {
            System.out.println("This word has already been found.");
            return 0;
        }
        System.out.println("You found a word!");
        wordList.add(word);
        for (int i = 0; i < validWords.size(); i++) {
            if (validWords.get(i).equals(word.toUpperCase())) {
                //System.out.println("word points gm2: " + wordPoints);
                lastAdded = wordPoints.get(i);
                //System.out.println("la: " + lastAdded);
            }
        }
        int points = word.length() - 3;
        //setComputerWords();
        //computerWords.remove(word);
        scores[player] += points;
        return points;
    }


    @Override
    public Collection<String> getAllWords() {
        if (tact == SearchTactic.SEARCH_BOARD) {
            searchBoard();
            return validWords;
        }
        else if (tact == SearchTactic.SEARCH_DICT) {
            return searchDict();
        }
        return validWords;
    }


    // runs search for every square in board as the START of words (checks all 16 for 4x4)
    public void searchBoard() {
        //String word = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                usedCubes.clear();
                checkWord = "";
                String start = "" + board[i][j];
                if (dict.isPrefix(start)) {
                    search(i, j);
                }
            }
        }
        System.out.println("searchBoard valid words: " + validWords);
        System.out.println("searchBoard valid words length: " + validWords.size());
    }

    public ArrayList<String> setComputerWords() {
        //ArrayList<String> compWords = new ArrayList<>();
        
        for (int i = 0; i < validWords.size(); i++) {
            boolean playerDid = false;
            for (int k = 0; k < wordList.size(); k++) {
                if (validWords.get(i).equals(wordList.get(k).toUpperCase())) {
                    playerDid = true;
                }
            }
            if (!computerWords.contains(validWords.get(i).toLowerCase()) && !playerDid && validWords.get(i).length() >= 4)
                computerWords.add(validWords.get(i).toLowerCase());
        }
        return computerWords;
    }

    public boolean notUsed(int iTouch, int jTouch, ArrayList<Point> usedCubes){
        for (int n = 0; n < usedCubes.size(); n++) {
            if (usedCubes.get(n).getX() == iTouch && usedCubes.get(n).getY() == jTouch) {
                return false;
            }
        }
        return true;
    }


    public void search(int i, int j) {
        //boolean base = true;

        //lastAdded.add(new Point(i, j));
        checkWord = checkWord + board[i][j];
        //System.out.println("check word " + i + j + " : " + checkWord);
        usedCubes.add(new Point(i, j));
        //System.out.println(usedCubes);

        //System.out.println("used cubes again: " + usedCubes);
        if (dict.contains(checkWord)) {
            wordPoints.add(new ArrayList<>(usedCubes));
            // System.out.println("word points gm: " + wordPoints);
            validWords.add(checkWord);
        }
        // check + and - 1 x or y spot from the original word to see if more words can be formed
        // need to make sure we dont go back to used squares
        for (int iTouch = i - 1; iTouch < i + 2; iTouch++) {
            for (int jTouch = j - 1; jTouch < j + 2; jTouch++) {
                if (iTouch >= 0 && jTouch >= 0 && iTouch < board.length && jTouch < board[0].length) {
                    if ((dict.isPrefix(checkWord + board[iTouch][jTouch])) && notUsed(iTouch, jTouch, usedCubes)) {
                        //  base = false;
                        search(iTouch, jTouch);
                    }
                }
            }
        }
        checkWord = checkWord.substring(0, checkWord.length() - 1);
        for (int index = 0; index < usedCubes.size(); index++) {
            if (usedCubes.get(index).getX() == i && usedCubes.get(index).getY() == j) {
                usedCubes.remove(index);
            }
        }
    }


    public ArrayList<String> searchDict () {
        Iterator<String> it = dict.iterator();
        ArrayList<String> allWords = new ArrayList<>();

        while (it.hasNext()) {
            String word = it.next();
            if (searchForWord(word)) {
                allWords.add(word.toUpperCase());
            }
        }
        System.out.println("dict all words: " + allWords);
        System.out.println("dict all words length: " + allWords.size());
        validWords = allWords;
        return allWords;
    }

    public boolean searchForWord(String word) {
        word = word.toUpperCase();
        boolean dupeLetter = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                // System.out.println(board[i][j]==word.charAt(0));
                // System.out.println("bij: " + board[i][j]);
                // System.out.println("charat: " + word.charAt(0));
                if (!word.equals("")) {
                    if (board[i][j]==word.charAt(0)) {
                        //if (searchForWordMore(i, j, word)) {
                        //System.out.println("reaching");
                        if(searchForWordMore(i, j, word)){
                            dupeLetter = true;
                        }
                    }
                }
            }
        }
        return dupeLetter;
    }


    public boolean searchForWordMore(int i, int j, String word) {
        boolean fin = false;
        int[] x = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] y = {-1, -1, 0, 1, 1, 1, 0, -1};

        if (board[i][j] != word.charAt(0)) {
            //System.out.println("bij: " + board[i][j]);
            return false;
        }
        int length = word.length();
        int l;
        int r = i;
        int c = j;
        String compare = word.substring(0, 1);
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(r, c));
        for (l = 1; l < length; l++) {
            for (int k = 0; k < 8; k++) {
                if (r + x[k] >= board.length || r + x[k] < 0 || c + y[k] >= board[0].length || c + y[k] < 0)
                    fin = false;
//                else if (board[r + x[k]][c + y[k]] != word.charAt(l)){
//                    fin = false;
//                }
                else if ((board[r + x[k]][c + y[k]] == word.charAt(l)) && notUsed(r + x[k], c + y[k], points)){
                    for (int back = 7; back>=0; back--){
                        int backW = 0;
                        if (r + x[back] >= board.length || r + x[back] < 0 || c + y[back] >= board[0].length || c + y[back] < 0){
                            fin = false;
                        }
                        else if ((board[r + x[back]][c + y[back]] == word.charAt(l)) && notUsed(r + x[back], c + y[back], points)){
                            for (int k2 = 0; k2 < 8; k2++) {
                                int i1 = r + x[back] + x[k2];
                                int j1 = c + x[back] + y[k2];
                                points.add(new Point(r + x[back], c + x[back]));
                                if (length > l + 1){
                                    if (i1 >= board.length || i1 < 0 || j1 >= board[0].length || j1 < 0){
                                        fin = false;
                                    }
                                    else if ((board[i1][j1] == word.charAt(l+1)) && notUsed(i1, j1, points)){
                                        backW++;
                                    }
                                }
                            }
                        }
                        if (backW == 1) {
                            compare = compare + word.substring(l, l + 1);
                            r += x[k];
                            c += y[k];
                            points.add(new Point(r, c));
                            break;
                        }
                    }
                    compare = compare + word.substring(l, l + 1);
                    r += x[k];
                    c += y[k];
                    points.add(new Point(r, c));
                    break;
                }
            }
//        if (l == length)
//            return true;
        }
        if (compare.equals(word)){
            wordPoints.add(points);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public char[][] getBoard() {
        return board;
    }

    @Override
    public List<Point> getLastAddedWord() {
        //check fi empty, return null, new pt list array, iterate through every pt
        return lastAdded;
    }

    @Override
    public int[] getScores() {
        return scores;
    }

    @Override
    public void newGame(int size, int numPlayers, String cubeFile, BoggleDictionary dict) throws IOException {
        board = new char[size][size];
        this.numPlayers = numPlayers;
        if (numPlayers <= 0)
            System.err.println("Number of players is too low");

        File f = new File(cubeFile);
        if (!f.canRead())
        {
            System.err.println("Input file cannot be opened.");
            System.exit(0);
            return;
        }

        setCubes(size, cubeFile);
    //    this.scores = new int [numPlayers];
        scores = new int[numPlayers];

        //randomly shuffle then randomly distribute
        ArrayList<char[]> temp = new ArrayList<>();
        temp.addAll(Arrays.asList(cubes));
        for (int r = 0; r < board.length; r++){
            for (int c = 0; c < board[r].length; c++){
                int rand1 = (int) (Math.random() * temp.size());
                int rand2 = (int) (Math.random() * temp.get(rand1).length);
                board[r][c] = temp.get(rand1)[rand2];
                //System.out.print("[" + board[r][c] + "] ");
            }
            //System.out.println();
        }
    }

    public void viewGame() {
        for (int i = 0; i < lastAdded.size(); i++) {
            String s = "" + (board[(int) (lastAdded.get(i).getX())][(int) (lastAdded.get(i).getY())]);
            s = s.toLowerCase();
            board[(int) (lastAdded.get(i).getX())][(int) (lastAdded.get(i).getY())] = s.charAt(0);
        }
        for (int r = 0; r < board.length; r++) {


            for (int c = 0; c < board[r].length; c++) {
                //System.out.print("[" + board[r][c] + "] ");
                System.out.print(board[r][c] + " ");
            }

            System.out.println();
        }
        for (int i = 0; i < lastAdded.size(); i++) {
            String s = "" + (board[(int) (lastAdded.get(i).getX())][(int) (lastAdded.get(i).getY())]);
            s = s.toUpperCase();
            board[(int) (lastAdded.get(i).getX())][(int) (lastAdded.get(i).getY())] = s.charAt(0);
        }
        getAllWords();
        //setComputerWords();
    }

    public void setCubes(int size, String cubeFile) throws IOException{
        cubes = new char[size * size][6];
        File f = new File(cubeFile);
        if (!f.canRead())
		{
			System.err.println("Input file cannot be opened.");
            return;
		}
        Scanner scanner = new Scanner(f);
        if (cubes.length != size * size) {
            System.err.println("Too many or too few cubes.");
            return;
        }
        for (int i = 0; i < cubes.length; i++) {
        //    while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
            if (str.length() != 6) {
                System.err.println("Too many or too few faces in one of your cubes (needs to be 6).");
                return;
            }
            for (int j = 0; j < cubes[i].length; j++) {
                    //if (!str.equals(""))
                cubes[i][j] = str.charAt(j);
                    // else {
                    //     i++;
                    //     j++;
                    // }
                    //System.out.println(cubes[i][j]);
                }
        //    }
        }
        // if # of cubes is not perfect 4 or 16
        // if $ of cube faces in each line doesn't equal 6
        scanner.close();
    }

    @Override
    public void setGame(char[][] board) {
        this.board = board;
        for (int i = 0; i < scores.length; i++)
            scores[i] = 0;
    }


    @Override
    public void setSearchTactic(SearchTactic tactic) {
        //check if valid tactic or not
        if ((tactic != BoggleGame.SEARCH_DEFAULT) && (tactic != BoggleGame.SearchTactic.SEARCH_DICT) &&
                (tactic != BoggleGame.SearchTactic.SEARCH_BOARD)){
            System.err.println("Tactic is not valid :(");
        }

        tact = tactic;
    }
}