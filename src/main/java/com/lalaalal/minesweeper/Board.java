package com.lalaalal.minesweeper;

import java.util.concurrent.atomic.AtomicBoolean;

public class Board {
    public final int ROW_LENGTH;
    public final int COL_LENGTH;
    public final int BOMB_COUNT;

    protected final byte BOMB_VALUE = 0x0B;
    protected final byte FLAG_VALUE = 0x10;
    protected final byte OPEN_VALUE = 0x20;
    protected final byte VALUE_MASK = 0x0F;

    /*
    0x0_ : value (bombCount or bomb)
    0x_0 : open, flag (0 ~ 2)
     */
    private byte[][] board;
    private int answer = 0;

    public Board(int rowLength, int columnLength, int bombCount) throws Exception {
        if (rowLength < 3 || columnLength < 3 || 50 < rowLength || 50 < columnLength
                || bombCount < 1 || bombCount >= rowLength * columnLength)
            throw new Exception("Wrong Parameter");
        ROW_LENGTH = rowLength;
        COL_LENGTH = columnLength;
        BOMB_COUNT = bombCount;
    }

    public Board(byte[][] board, int bombCount) throws Exception {
        if (bombCount < 1)
            throw new Exception("Wrong Parameter");
        this.board = board;
        COL_LENGTH = board.length;
        ROW_LENGTH = board[0].length;
        BOMB_COUNT = bombCount;
    }

    public void setBomb(Point point) {
        setBomb(point.x, point.y);
    }

    public void setBomb(int x, int y) {
        board[y][x] = BOMB_VALUE;
    }

    public boolean isBomb(Point point) {
        return isBomb(point.x, point.y);
    }

    public boolean isBomb(int x, int y) {
        return getTileValue(x, y) == BOMB_VALUE;
    }

    protected byte getTile(Point point) {
        return getTile(point.x, point.y);
    }

    protected byte getTile(int x, int y) {
        return board[y][x];
    }

    public byte getTileValue(Point point) {
        return getTileValue(point.x, point.y);
    }

    public byte getTileValue(int x, int y) {
        return (byte)(getTile(x, y) & VALUE_MASK);
    }

    public String getTileValueAsString(int x, int y) {
        if (isTileFlagged(x, y)) {
            return "X";
        } else if (isTileOpened(x, y)) {
            if (isBomb(x, y)) return "B";
            else return Integer.toString(getTileValue(x, y));
        } else
            return " ";
    }

    protected void addTileValue(Point point) {
        addTileValue(point.x, point.y);
    }

    protected void addTileValue(int x, int y) {
        board[y][x]++;
    }

    public boolean isTileOpened(Point point) {
        return isTileOpened(point.x, point.y);
    }

    public boolean isTileOpened(int x, int y) {
        return (getTile(x, y) & OPEN_VALUE) == OPEN_VALUE;
    }

    public boolean isTileFlagged(Point point) {
        return isTileFlagged(point.x, point.y);
    }

    public boolean isTileFlagged(int x, int y) {
        return (getTile(x, y) & FLAG_VALUE) == FLAG_VALUE;
    }

    public void toggleTileFlag(Point point) {
        toggleTileFlag(point.x, point.y);
    }

    public void toggleTileFlag(int x, int y) {
        if (isBomb(x, y)) {
            if (isTileFlagged(x, y)) answer--;
            else answer++;
        } else {
            if (isTileFlagged(x, y)) answer++;
            else answer--;
        }

        board[y][x] ^= FLAG_VALUE;
    }

    public void openTile(Point point) {
        openTile(point.x, point.y);
    }

    public void openTile(int x, int y) {
        board[y][x] |= OPEN_VALUE;
    }

    public int getAnswer() {
        return answer;
    }

    public void makeBoardWithBombPoints(Point[] bombPoints) {
        for (Point bombPoint : bombPoints) {
            setBomb(bombPoint);
            runSurroundAction(bombPoint, (point) -> {
                if (!isBomb(point))
                    addTileValue(point);
            });
        }
    }

    public void initBoard() {
        board = new byte[COL_LENGTH][ROW_LENGTH];
        Point[] bombPoints = Point.sample(BOMB_COUNT, ROW_LENGTH, COL_LENGTH);

        makeBoardWithBombPoints(bombPoints);
    }

    public boolean isPlayableBoard(Point[] bombPoints, Point openPoint) {
        AtomicBoolean result = new AtomicBoolean();
        for (Point bombPoint : bombPoints) {
            if (bombPoint.equals(openPoint))
                return false;
            if (BOMB_COUNT * 2 < ROW_LENGTH * COL_LENGTH)
                runSurroundAction(bombPoint, (point) -> result.set(result.get() | point.equals(openPoint)));
            if (result.get())
                return false;
        }
        return true;
    }

    public void initBoard(Point openPoint) {
        if (!isPointInRange(openPoint))
            throw new IndexOutOfBoundsException("Point " + openPoint + " out of bounds");
        board = new byte[COL_LENGTH][ROW_LENGTH];

        Point[] bombPoints;
        do {
            bombPoints = Point.sample(BOMB_COUNT, ROW_LENGTH, COL_LENGTH);
        } while (!isPlayableBoard(bombPoints, openPoint));
        makeBoardWithBombPoints(bombPoints);
    }

    public boolean isPointInRange(Point point) {
        return (0 <= point.x && point.x < ROW_LENGTH)
                && (0 <= point.y && point.y < COL_LENGTH);
    }

    public void runSurroundAction(Point basePoint, SurroundAction action) {
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                Point targetPoint = new Point(basePoint.x + x, basePoint.y + y);
                if (isPointInRange(targetPoint))
                    action.run(targetPoint);
            }
        }
    }

    public interface SurroundAction {
        void run(Point point);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < COL_LENGTH; y++) {
            for (int x = 0; x < ROW_LENGTH; x++) {
                builder.append(String.format("0x%02X", board[y][x]));
                builder.append(" ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}
