package com.lalaalal.minesweeper.console;

import com.lalaalal.minesweeper.command.ExitCommand;
import com.lalaalal.minesweeper.command.GameCommand;

public class ConsoleCommandFactory {
    private final ConsoleGameHandler game;

    public ConsoleCommandFactory(ConsoleGameHandler game) {
        this.game = game;
    }

    public GameCommand createCommand(String command) {
        return switch (command) {
            case "OPEN", "open", "o" -> new ConsoleOpenCommand(game);
            case "FLAG", "flag", "f" -> new ConsoleFlagCommand(game);
            case "EXIT", "exit", "x" -> new ExitCommand();
            case "HELP", "help", "h" -> new ConsoleHelpCommand();
            case "UNDO", "undo", "u" -> new ConsoleUndoCommand(game);
            default -> throw new IllegalStateException("Unexpected command: " + command);
        };
    }
}
