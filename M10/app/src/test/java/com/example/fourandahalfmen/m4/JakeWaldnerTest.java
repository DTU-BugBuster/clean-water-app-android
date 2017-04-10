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
    public void email_isCorrect() throws Exception {
        assertTrue(jake.setEmail("jake@waldner.com"));
        assertFalse(jake.setEmail("jake"));
        assertFalse(jake.setEmail("jake@"));
        assertFalse(jake.setEmail("jake!"));
        assertTrue(jake.setEmail("jake123@waldner.edu"));
        assertFalse(jake.setEmail("jake123@waldner.edcation"));
        assertFalse(jake.setEmail("@waldner.edcation"));
        assertFalse(jake.setEmail("jake."));
        assertTrue(jake.setEmail("1@2.com"));
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