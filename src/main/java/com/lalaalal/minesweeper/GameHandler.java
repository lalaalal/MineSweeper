package com.lalaalal.minesweeper;

import com.lalaalal.minesweeper.command.CommandManager;
import com.lalaalal.minesweeper.command.GameCommand;
import com.lalaalal.minesweeper.state.GameOverState;
import com.lalaalal.minesweeper.state.GameState;
import com.lalaalal.minesweeper.state.PlayingState;
import com.lalaalal.minesweeper.state.WinState;

public abstract class GameHandler {
    private final CommandManager commandManager = new CommandManager();
    
    protected final Board board;
    protected int flagCount = 0;

    public GameHandler(Board board) {
        this.board = board;
    }

    public void openTile(Point point) {
        openTile(point.x, point.y);
    }

    public void openTile(int x, int y) {
        if (board.isTileOpened(x, y))
            return;
        board.openTile(x, y);
        if (board.getTileValue(x, y) == 0) {
            board.runSurroundAction(new Point(x, y), (point) -> openTile(point.x, point.y));
        }
    }

    public void flagTile(Point point) { flagTile(point.x, point.y); }

    public void flagTile(int x, int y) {
        if (board.isTileFlagged(x, y)) flagCount--;
        else flagCount++;
        board.toggleTileFlag(x, y);
    }

    protected abstract GameState ready();

    public void play() {
        GameState state = ready();
        while (state instanceof PlayingState)
            state = process(state);

        finale(state);
    }

    public abstract GameState process(GameState prevState);

    public GameState executeCommand(GameCommand command) throws Exception {
        return commandManager.execute(command);
    }

    public abstract Point getSelectedPoint();

    public abstract void finale(GameState state);

    public GameState getOpenState(Point point) {
        if (board.isBomb(point))
            return new GameOverState();
        return new PlayingState(point);
    }

    public GameState getFlagState(Point point) {
        if (board.BOMB_COUNT == board.getAnswer())
            return new WinState();
        return new PlayingState(point);
    }

    public GameState undo() throws Exception {
        return commandManager.undo();
    }

    public GameState redo() throws Exception {
        return commandManager.redo();
    }
}
