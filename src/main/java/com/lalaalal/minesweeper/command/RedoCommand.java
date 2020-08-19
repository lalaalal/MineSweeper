package com.lalaalal.minesweeper.command;

import com.lalaalal.minesweeper.GameHandler;
import com.lalaalal.minesweeper.state.GameState;

public class RedoCommand implements GameCommand {
    protected final GameHandler game;

    public RedoCommand(GameHandler game) {
        this.game = game;
    }

    @Override
    public GameState run() throws Exception {
        return game.redo();
    }
}
