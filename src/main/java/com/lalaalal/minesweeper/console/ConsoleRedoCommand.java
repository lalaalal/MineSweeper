package com.lalaalal.minesweeper.console;

import com.lalaalal.minesweeper.command.RedoCommand;
import com.lalaalal.minesweeper.state.GameState;

public class ConsoleRedoCommand extends RedoCommand {
    public ConsoleRedoCommand(ConsoleGameHandler game) {
        super(game);
    }

    @Override
    public GameState run() throws Exception {
        GameState gameState = super.run();
        ((ConsoleGameHandler) game).displayBoard();

        return gameState;
    }
}
