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
    public void username_isCorrect() throws Exception {
        assertTrue(varun.setUsername("HEllo"));
        assertFalse(varun.setUsername("HEll1"));
        assertFalse(varun.setUsername("Hello"));
        assertTrue(varun.setUsername("HEllobbbbbbbbbb"));
        assertFalse(varun.setUsername("HEllo$"));
        assertFalse(varun.setUsername("hello"));
        assertFalse(varun.setUsername("1333"));
        assertFalse(varun.setUsername("HELlo"));
    }



    /**
     * tests all possible exceptions
     */

    @Test(expected = IllegalArgumentException.class)
    public void testNull() {
        varun.setUsername(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmpty() {
        varun.setUsername("");
    }
}