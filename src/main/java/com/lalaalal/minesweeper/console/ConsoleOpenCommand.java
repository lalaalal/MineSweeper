package com.lalaalal.minesweeper.console;

import com.lalaalal.minesweeper.command.OpenCommand;
import com.lalaalal.minesweeper.state.GameState;

public class ConsoleOpenCommand extends OpenCommand {
    public ConsoleOpenCommand(ConsoleGameHandler game) {
        super(game);
    }

    @Override
    public GameState run() {
        GameState state = super.run();
        ((ConsoleGameHandler) game).displayBoard();

        return state;
    }
}
