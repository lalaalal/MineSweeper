package com.lalaalal.minesweeper.command;

import com.lalaalal.minesweeper.state.GameState;

import java.util.Stack;

public class CommandManager {
    private final Stack<UndoableCommand> history = new Stack<>();

    public GameState execute(GameCommand command) throws Exception {
        if (command instanceof UndoableCommand)
            history.push((UndoableCommand) command);

        return command.run();
    }

    public GameState undo() throws Exception {
        if (!history.isEmpty()) {
            UndoableCommand command = history.pop();
            return command.undo();
        }
        throw new Exception("Nothing to undo");
    }
}
