package com.lalaalal.minesweeper.command;

import com.lalaalal.minesweeper.GameHandler;
import com.lalaalal.minesweeper.state.GameState;

public interface GameCommand {
    GameState run(GameHandler game);
}
