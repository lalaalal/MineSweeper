package com.lalaalal.minesweeper.console;

import com.lalaalal.minesweeper.command.UndoCommand;
import com.lalaalal.minesweeper.state.GameState;

public class ConsoleUndoCommand extends UndoCommand {
    public ConsoleUndoCommand(ConsoleGameHandler game) {
        super(game);
    }

    @Override
    public GameState run() throws Exception {
        GameState gameState = super.run();
        ((ConsoleGameHandler) game).displayBoard();

        return gameState;
    }
}
