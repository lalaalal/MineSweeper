package com.lalaalal.minesweeper.command;

import com.lalaalal.minesweeper.GameHandler;
import com.lalaalal.minesweeper.Point;
import com.lalaalal.minesweeper.state.GameState;

public class OpenCommand implements GameCommand {
    protected final GameHandler game;

    public OpenCommand(GameHandler game) {
        this.game = game;
    }

    @Override
    public GameState run() {
        Point point = game.getSelectedPoint();
        game.openTile(point);

        return game.getOpenState(point);
    }
}
