package com.lalaalal.minesweeper.command;

import com.lalaalal.minesweeper.state.GameState;

import java.util.Stack;

public class CommandManager {

    private final Stack<UndoableCommand> history = new Stack<>();
    private final Stack<UndoableCommand> undone = new Stack<>();

    public GameState execute(GameCommand command) throws Exception {
        GameState state = command.run();
        if (command instanceof UndoableCommand) {
            history.push((UndoableCommand) command);
            if (!undone.isEmpty())
                undone.clear();
        }

        return state;
    }

    public GameState undo() throws Exception {
        if (history.isEmpty())
            throw new Exception("Nothing to undo");
        UndoableCommand command = history.pop();
        undone.push(command);

        return command.undo();
    }

    public GameState redo() throws Exception {

        if (undone.isEmpty())
            throw new Exception("Nothing to redo");
        UndoableCommand command = undone.pop();
        history.push(command);

        return command.redo();
    }
}
