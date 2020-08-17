package com.lalaalal.minesweeper.command;

import com.lalaalal.minesweeper.GameHandler;
import com.lalaalal.minesweeper.Point;
import com.lalaalal.minesweeper.state.GameState;

public class OpenCommand implements GameCommand {
    @Override
    public GameState run(GameHandler game) {
        Point point = game.getSelectedPoint();
        game.openTile(point);

        return game.getOpenState(point);
    }
}
