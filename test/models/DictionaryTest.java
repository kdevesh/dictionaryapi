package models;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class DictionaryTest {
    public static final String WORD = "KAFKA";
    public static final String WORD2 = "MART";
    private Dictionary dict1,dict2;
    @Before
    public void setUp() throws Exception {
        dict1 = new Dictionary("KAFKA");
        dict2 = new Dictionary();
        dict2.setWord("MART");
    }
    @Test
    public void getWord(){
        assertEquals(WORD, dict1.getWord());
        assertEquals(WORD2,dict2.getWord());

    }
    @After
    public void tearDown() throws Exception {
        System.out.println("Test Completed");

    }
}
