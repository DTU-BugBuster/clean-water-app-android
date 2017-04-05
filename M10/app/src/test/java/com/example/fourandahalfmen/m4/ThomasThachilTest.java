package com.example.fourandahalfmen.m4;

import com.example.fourandahalfmen.m4.data.WaterPurityReport;
import com.example.fourandahalfmen.m4.data.WaterReport;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ThomasThachilTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    WaterPurityReport sampleReport = new WaterPurityReport();

    @Test(expected = IllegalArgumentException.class)
    public void nullContaminant() throws Exception {
        sampleReport.setContaminantPPM(null);
    }

    @Test
    public void validContaminant() throws Exception {
        Double val = 48.9;
        assertEquals(true, sampleReport.setContaminantPPM(val));
    }

    @Test
    public void invalidContaminant() throws Exception {
        Double val = -56.9;
        assertEquals(false, sampleReport.setContaminantPPM(val));
    }

}