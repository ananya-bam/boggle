package assignment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;

/* 
 * Any comments and methods here are purely descriptions or suggestions.
 * This is your test file. Feel free to change this as much as you want.
 */

public class BoggleTest {
    GameManager game1, game2;
    GameDictionary dict1;
    GameDictionary dictDart;
    // This will run ONCE before all other tests. It can be useful to setup up
    // global variables and anything needed for all of the tests.
    @BeforeAll
    static void setupAll() throws IOException {

    }

    // This will run before EACH test.
    @BeforeEach
    void setupEach() throws IOException {
//        game1 = new GameManager();
//        dict1 = new GameDictionary();
//        dict1.loadDictionary("words.txt");

        // specific game2 setup
        game2 = new GameManager();
        dictDart = new GameDictionary();
        dictDart.loadDictionary("dictDART.txt");
        System.out.println(dictDart.getWords());
    }

    // You can test Boggle here. You will want to make additional tests as well
    @Test
    void testBoggle() {

    }

    // You can test GameManager here. You will want to make additional tests as well
    @Test
    void testGameManager() throws IOException {
        test2x2();
    }

    void test2x2() throws IOException {
        Assertions.assertEquals(dictDart.getWords().get(0), "ad");
        Assertions.assertEquals(dictDart.getWords().get(3), "drat");
    }

    // You can test GameDictionary here. You will want to make additional tests as well
//    @Test
//    void testGameDictionary() {
//        Assertions.assertTrue(dict1.isPrefix("adroit"));
//        Assertions.assertFalse(dict1.isPrefix("adroiteeeee"));
//        Assertions.assertTrue(dict1.contains("adroitest"));
//        Assertions.assertFalse(dict1.contains("alsdjkfjfkdla"));
//    }

}
