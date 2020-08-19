package com.lalaalal.minesweeper.command;

import com.lalaalal.minesweeper.state.GameState;

public abstract class UndoableCommand implements GameCommand {
    public abstract GameState undo() throws Exception;
    public abstract GameState redo() throws Exception;
}
