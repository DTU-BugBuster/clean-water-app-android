package com.example.fourandahalfmen.m4;

import com.example.fourandahalfmen.m4.data.WaterPurityReport;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RileyOsbornTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    WaterPurityReport sampleReport = new WaterPurityReport();

    @Test(expected = IllegalArgumentException.class)
    public void nullVirus() throws Exception {
        sampleReport.setVirusPPM(null);
    }

    @Test
    public void validVirus() throws Exception {
        Double val = 48.9;
        assertEquals(true, sampleReport.setVirusPPM(val));
    }

    @Test
    public void invalidVirus() throws Exception {
        Double val = -56.9;
        assertEquals(false, sampleReport.setVirusPPM(val));
    }

}