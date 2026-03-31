package vn.edu.ueh.speedyeats;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import vn.edu.ueh.speedyeats.Util.SearchValidator;

public class SearchValidatorTest {

    @Test
    public void testValidQuery() {
        assertFalse(SearchValidator.isValidQuery(""));
        assertFalse(SearchValidator.isValidQuery(null));
        assertTrue(SearchValidator.isValidQuery("Pizza"));
    }

    @Test
    public void testNormalize() {
        assertEquals("Pizza", SearchValidator.normalizeQuery("  Pizza  "));
    }

    @Test
    public void testVoiceResult() {
        ArrayList<String> list = new ArrayList<>();
        assertFalse(SearchValidator.isVoiceResultValid(list));

        list.add("Pho");
        assertTrue(SearchValidator.isVoiceResultValid(list));
    }
}