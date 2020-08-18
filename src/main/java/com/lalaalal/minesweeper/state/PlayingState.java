package com.lalaalal.minesweeper.state;

import com.lalaalal.minesweeper.Point;

public class PlayingState implements GameState {
    private final String message;

    public PlayingState(Point point) {
        message = point + " Changed";
    }

    public PlayingState() {
        this("Playing");
    }

    public PlayingState(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
