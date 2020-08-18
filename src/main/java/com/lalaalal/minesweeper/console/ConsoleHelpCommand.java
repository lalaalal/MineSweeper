package com.lalaalal.minesweeper.console;

import com.lalaalal.minesweeper.command.GameCommand;
import com.lalaalal.minesweeper.state.GameState;
import com.lalaalal.minesweeper.state.PlayingState;

public class ConsoleHelpCommand implements GameCommand {
    @Override
    public GameState run() {
        System.out.println("OPEN [x] [y]");
        System.out.println("FLAG [x] [y]");
        System.out.println("EXIT");
        System.out.println();

        return new PlayingState();
    }
}
