package com.lalaalal.minesweeper.state;

import com.lalaalal.minesweeper.Point;

public class PlayingState implements GameState {
    private final Point point;

    public PlayingState(Point point) {
        this.point = point;
    }
    @Override
    public String getMessage() {
        return "Playing";
    }

    public Point getPoint() {
        return point;
    }
}
