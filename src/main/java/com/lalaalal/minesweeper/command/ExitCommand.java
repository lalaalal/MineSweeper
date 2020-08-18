package com.lalaalal.minesweeper.command;

import com.lalaalal.minesweeper.state.GameState;

public class ExitCommand implements GameCommand {
    @Override
    public GameState run() {
        return () -> "Bye";
    }
}
