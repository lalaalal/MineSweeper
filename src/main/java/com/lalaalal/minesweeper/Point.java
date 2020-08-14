package com.lalaalal.minesweeper;

import java.util.Objects;
import java.util.Random;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point[] sample(int size, int xBound, int yBound) {
        Point[] sample = new Point[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            int x = random.nextInt(xBound);
            int y = random.nextInt(yBound);
            sample[i] = new Point(x, y);

            for (int j = 0; j < i; j++) {
                if (sample[i].equals(sample[j])) {
                    i = i - 1;
                    break;
                }
            }
        }

        return sample;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
