package com.example.fourandahalfmen.m4;

import org.junit.Test;
import com.example.fourandahalfmen.m4.data.Users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ShivamPatelTest {

    Users shivam = new Users();

    @Test
    public void password_isCorrect() throws Exception {
        assertTrue(shivam.setPassword("Hello022!!"));
        assertFalse(shivam.setPassword("Hello"));
        assertFalse(shivam.setPassword("1"));
        assertFalse(shivam.setPassword("hello022!!"));
        assertTrue(shivam.setPassword("HELLO!!$$"));
        assertFalse(shivam.setPassword("Hello022+!!"));
        assertFalse(shivam.setPassword("Hello022-!!"));
        assertFalse(shivam.setPassword("H1113333%"));
        assertTrue(shivam.setPassword("H1113333!"));
    }


    /**
     * tests all possible exceptions
     */

    @Test(expected = IllegalArgumentException.class)
    public void testNull() {
        shivam.setPassword(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmpty() {
        shivam.setPassword("");
    }

}