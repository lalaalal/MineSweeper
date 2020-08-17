package com.lalaalal.minesweeper.command;

import com.lalaalal.minesweeper.GameHandler;
import com.lalaalal.minesweeper.state.GameState;
import com.lalaalal.minesweeper.Point;

public class FlagCommand implements GameCommand {
    @Override
    public GameState run(GameHandler game) {
        Point point = game.getSelectedPoint();
        game.flagTile(point);

        return game.getFlagState(point);
    }
}
