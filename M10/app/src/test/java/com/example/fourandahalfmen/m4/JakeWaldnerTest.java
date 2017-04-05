package com.example.fourandahalfmen.m4;

import com.example.fourandahalfmen.m4.data.Users;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class JakeWaldnerTest {

    Users jake = new Users();

    @Test
    public void addition_isCorrect() throws Exception {
        assertTrue(jake.setEmail("Hello022!!"));
        assertFalse(jake.setEmail("Hello"));
        assertFalse(jake.setEmail("1"));
        assertFalse(jake.setEmail("hello022!!"));
        assertTrue(jake.setEmail("HELLO!!$$"));
        assertFalse(jake.setEmail("Hello022+!!"));
        assertFalse(jake.setEmail("Hello022-!!"));
        assertFalse(jake.setEmail("H1113333%"));
        assertTrue(jake.setEmail("H1113333!"));
    }


    /**
     * tests all possible exceptions
     */

    @Test(expected = IllegalArgumentException.class)
    public void testNull() {
        jake.setEmail(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmpty() {
        jake.setEmail("");
    }
}