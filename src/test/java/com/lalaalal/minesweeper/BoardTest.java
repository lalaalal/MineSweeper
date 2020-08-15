package com.lalaalal.minesweeper;

import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {
    private byte[][] testBoardData = {
            { 0x01, 0x01, 0x00 },
            { 0x0B, 0x01, 0x00 },
            { 0x01, 0x01, 0x00 }
    };

    @Test public void testCheckingPlayableBoard() throws Exception {
        Board board = new Board(testBoardData, 1);
        Point[] bombPoints = { new Point(0, 1) };
        assertFalse(board.isPlayableBoard(bombPoints, new Point(1, 1)));
        assertFalse(board.isPlayableBoard(bombPoints, new Point(0, 1)));
        assertTrue(board.isPlayableBoard(bombPoints, new Point(2, 2)));
    }
}
