package com.chris.hqteach;

import android.os.SystemClock;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testSystemClock() throws Exception{
        long currentTime1 = System.currentTimeMillis();
        long currentTime2 = SystemClock.uptimeMillis();
        System.out.println(currentTime1);
        System.out.println(currentTime2);
    }
}