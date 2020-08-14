package com.lalaalal.minesweeper;

import org.junit.Test;
import static org.junit.Assert.*;

public class PointTest {
    private boolean doesDuplicationExist(Point[] sample) {
        for (int i = 0; i < sample.length - 1; i++) {
            for (int j = i + 1; j < sample.length; j++) {
                if (sample[i].equals(sample[j])) {
                    return true;
                }
            }
        }

        return false;
    }

    @Test public void testSample() {
        Point[] sample = Point.sample(2, 2, 2);

        assertFalse(doesDuplicationExist(sample));
    }
}
