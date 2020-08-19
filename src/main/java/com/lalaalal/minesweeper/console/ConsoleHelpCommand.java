package com.lalaalal.minesweeper.console;

import com.lalaalal.minesweeper.command.GameCommand;
import com.lalaalal.minesweeper.state.GameState;
import com.lalaalal.minesweeper.state.PlayingState;

public class ConsoleHelpCommand implements GameCommand {
    @Override
    public GameState run() {
        System.out.println("(OPEN, open, o) [x] [y]");
        System.out.println("(FLAG, flag, f) [x] [y]");
        System.out.println("(UNDO, undo, u)");
        System.out.println("(HELP ,help, h)");
        System.out.println("(EXIT, exit, x)");
        System.out.println();

        return new PlayingState("HELP");
    }
}
