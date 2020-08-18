package com.lalaalal.minesweeper.console;

import com.lalaalal.minesweeper.command.FlagCommand;
import com.lalaalal.minesweeper.state.GameState;

public class ConsoleFlagCommand extends FlagCommand {

    public ConsoleFlagCommand(ConsoleGameHandler game) {
        super(game);
    }

    @Override
    public GameState run() {
        GameState state = super.run();
        ((ConsoleGameHandler) game).displayBoard();

        return state;
    }
}
