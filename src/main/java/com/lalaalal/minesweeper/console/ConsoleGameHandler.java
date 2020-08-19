package com.lalaalal.minesweeper.console;

import com.lalaalal.minesweeper.Board;
import com.lalaalal.minesweeper.GameHandler;
import com.lalaalal.minesweeper.Point;
import com.lalaalal.minesweeper.command.GameCommand;
import com.lalaalal.minesweeper.state.GameState;
import com.lalaalal.minesweeper.state.PlayingState;

import java.util.Scanner;

public class ConsoleGameHandler extends GameHandler {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ConsoleCleaner consoleCleaner = ConsoleCleaner.getConsoleCleaner();
    private final ConsoleCommandFactory commandFactory = new ConsoleCommandFactory(this);

    public ConsoleGameHandler() throws Exception{
        super(createBoard());
    }

    public static Board createBoard() throws Exception{
        System.out.print("Row Length    ( 3 ~ 50 )   : ");
        int rowLength = scanner.nextInt();
        System.out.print("Column Length ( 3 ~ 50 )   : ");
        int columnLength = scanner.nextInt();
        System.out.printf("Bomb Count    ( 1 ~ %04d ) : ", rowLength * columnLength - 1);
        int bombCount = scanner.nextInt();
        System.out.println();

        return new Board(rowLength, columnLength, bombCount);
    }

    public GameCommand getCommand() {
        System.out.print("Command : ");
        String command = scanner.next();

        return commandFactory.createCommand(command);
    }


    @Override
    public GameState runCommand(GameState prevState) {
        try {
            System.out.println("Prev : " + prevState.getMessage());
            GameCommand command = getCommand();
            return commandManager.execute(command);
        } catch (Exception e) {
            if (!(e instanceof IndexOutOfBoundsException))
                scanner.nextLine();
            System.out.println(e.getMessage() + "\n");
            return runCommand(() -> "Bad Command");
        }
    }

    @Override
    public Point getSelectedPoint() {
        return Point.scanPoint(scanner);
    }

    @Override
    protected GameState ready() {
        System.out.print("Open : ");
        Point initPoint = Point.scanPoint(scanner);

        board.initBoard(initPoint);
        openTile(initPoint);
        displayBoard();

        return new PlayingState(initPoint);
    }

    @Override
    public void finale(GameState state) {
        System.out.println(state.getMessage());
    }

    public void displayBoard() {
        consoleCleaner.clean();
        System.out.print("   ");
        for (int i = 0; i < board.ROW_LENGTH; i++)
            System.out.printf("  %02d", i);
        System.out.println();

        System.out.print("   ");
        for (int i = 0; i < board.ROW_LENGTH; i++)
            System.out.print("====");
        System.out.println("=");

        for (int y = 0; y < board.COL_LENGTH; y++) {
            System.out.printf("%02d |", y);
            for (int x = 0; x < board.ROW_LENGTH; x++) {
                System.out.print(" " + board.getTileValueAsString(x, y) + " |");
            }
            System.out.println();

            System.out.print("   ");
            for (int i = 0; i < board.ROW_LENGTH; i++)
                System.out.print("====");
            System.out.println("=");
        }
        System.out.println("Total Bomb Count : " + board.BOMB_COUNT);
        System.out.println("Flag Count       : " + flagCount);
        System.out.println();
    }

}

