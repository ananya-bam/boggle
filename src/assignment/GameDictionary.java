package assignment;

import java.io.File;
import java.util.*;
import java.io.IOException;

public class GameDictionary implements BoggleDictionary {
    public static int ALPHABET = 26;
    public static TrieNode root = new TrieNode();

    @Override
    public boolean contains(String word) {
        word = word.toLowerCase();
        int index;
        TrieNode n = root;

        for (int i = 0; i < word.length(); i++){
            //index = word.charAt(i) - 'a';
            index = word.charAt(i);
            char c = word.charAt(i);
            if (!n.theKid.containsKey(c))
                return false;
            n = n.theKid.get(c);
        }
        return (n.isEnd);
    }

    @Override
    public boolean isPrefix(String prefix) {
        prefix = prefix.toLowerCase();
        TrieNode n = root;
        for (int i = 0; i < prefix.length(); i++){
            char c = prefix.charAt(i);
            //n = n.theKid[c - 'a']; <-- from ojas
            // if (n.theKid.get(c - 'a') != null){
            //     n = n.theKid.get(c - 'a');
            // }
            if (n.theKid.get(c) != null) {
                n = n.theKid.get(c);
            }
            else {
                n = null;
                break;
            }
        }
        return (n != null);
    }
    public void insert(String word) {
        int index;
        char c;
        TrieNode n = root;
        for (int i = 0; i < word.length(); i++){
            //index = word.charAt(i) - 'a';
            index = word.charAt(i);
            c = word.charAt(i);
            if (!n.theKid.containsKey(c)) {
                //n.theKid.get(index) = new TrieNode();
                n.theKid.put(c, new TrieNode());
            }
            n = n.theKid.get(c);
        }
        n.isEnd = true;
    }
    @Override
    public void loadDictionary(String filename) throws IOException {
        // we'll need a scanner again to read each word, top down
        // add nodes for each letter that exists
        // have to check if the prefix exists,
        // in which case we'll continue off of those existing nodes for the existing prefix
        ArrayList<String> words = new ArrayList<>();
        File f = new File(filename);
        if (!f.canRead()) {
			System.err.println("Input file cannot be opened.");
            System.exit(0);
			return;
		}
        Scanner scanner = new Scanner(f);
        while (scanner.hasNextLine()) {
            String store = scanner.nextLine();
            words.add(store);
        }
        int len = words.size();
        for (int i = 0; i < len; i++) {
            insert(words.get(i));
        }
        scanner.close();
    }

    private class GameDictionaryIterator implements Iterator<String> {
        
        private ArrayList<String> words;
        private int index;

        GameDictionaryIterator() {
            words = getWords();
            index = 0;
        }

        @Override
        public boolean hasNext() {
            //System.out.println("index: " + index + ", words.size(): " + words.size());
            return index < words.size();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return words.get(index++);
        }
    }

    public ArrayList<String> getWords() {
        ArrayList<String> words = new ArrayList<>();
        TrieNode n = root;
        String word = "";
        ///StringBuilder word = new StringBuilder();
        goThroughTrie(n, word, words);
        //System.out.println(("words: " + words));
        return words;
    }

    private void goThroughTrie(TrieNode node, String word, ArrayList<String> words) {
        if (node == null) {
            return;
        }

        //System.out.println("node.isend: " + node.isEnd);
        if (node.isEnd) {
            //System.out.println("adding");
            words.add(word);
        }

        // System.out.println(node.theKid);
        for (char c : node.theKid.keySet()) {
            //System.out.println(c);
            // System.out.println(node.theKid.get(c));
            if (node.theKid.get(c) != null) {
                //word.deleteCharAt(word.length() -1); 
                word += c;
                //word.append(c);
                goThroughTrie(node.theKid.get(c), word, words);
                word = word.substring(0, word.length()-1);   
                //word.deleteCharAt(word.length()-1);
            }
        }
    }

    @Override
    public Iterator<String> iterator() {
        // override hasNext and next method
        // traverse through the trie and add words when is word ended, adds current word, adds to an arraylist
        // TrieNode n = root;
        // while (!n.isEnd)
        // return null;
        // need to use Stack<> data structure
        // essentially when you traverse the words, you're automatically stacking letters
        // the stack data structure allows you to remember which letter you're "on" and haven't left yet so there's no need to
        // re-traverse the start or prefix of the word bc then you're just printing a list of all the words
        // you also have to do this one by one - next() looks for that cuz otherwise you're gonna be overlapping a lot of prefixes

        // psuedocode from shashank OH
        // hasNext() and next() method
        //

        return new GameDictionaryIterator();
        
    }

    static class TrieNode {
        //TrieNode[] theKid = new TrieNode[ALPHABET];
        //ArrayList<TrieNode> theKid = new ArrayList<>();
        Map<Character, TrieNode> theKid = new TreeMap<>();
        boolean isEnd;

        TrieNode() {
            isEnd = false;
            //Arrays.fill(theKid, null);
            //theKid.add(null);
            // for (int i = 0; i < 128; i++) {
            //     //theKid.add(null);
            //     theKid.put(null, null);
            // }
        }
    }
}