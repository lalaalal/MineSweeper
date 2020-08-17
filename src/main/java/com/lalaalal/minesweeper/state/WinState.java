package com.lalaalal.minesweeper.state;

public class WinState implements GameState {
    @Override
    public String getMessage() {
        return "You Win!";
    }
}
