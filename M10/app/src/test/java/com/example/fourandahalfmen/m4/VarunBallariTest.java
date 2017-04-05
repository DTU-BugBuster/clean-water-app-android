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
public class VarunBallariTest {
    
    Users varun = new Users();

    @Test
    public void addition_isCorrect() throws Exception {
        assertTrue(varun.setPassword("Hello022!!"));
        assertFalse(varun.setPassword("Hello"));
        assertFalse(varun.setPassword("1"));
        assertFalse(varun.setPassword("hello022!!"));
        assertTrue(varun.setPassword("HELLO!!$$"));
        assertFalse(varun.setPassword("Hello022+!!"));
        assertFalse(varun.setPassword("Hello022-!!"));
        assertFalse(varun.setPassword("H1113333%"));
        assertTrue(varun.setPassword("H1113333!"));
    }


    /**
     * tests all possible exceptions
     */

    @Test(expected = IllegalArgumentException.class)
    public void testNull() {
        varun.setPassword(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmpty() {
        varun.setPassword("");
    }
}