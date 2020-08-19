package com.lalaalal.minesweeper.command;

import com.lalaalal.minesweeper.GameHandler;
import com.lalaalal.minesweeper.state.GameState;
import com.lalaalal.minesweeper.Point;
import com.lalaalal.minesweeper.state.PlayingState;

public class FlagCommand extends UndoableCommand {
    protected final GameHandler game;
    private Point point;

    public FlagCommand(GameHandler game) {
        this.game = game;
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public GameState run() throws Exception {
        point = game.getSelectedPoint();
        game.flagTile(point);

        return game.getFlagState(point);
    }

    @Override
    public GameState undo() {
        game.flagTile(point);

        return new PlayingState(point);
    }

    @Override
    public GameState redo() {
        return undo();
    }
}
