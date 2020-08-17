package com.lalaalal.minesweeper.console;

import com.lalaalal.minesweeper.Board;
import com.lalaalal.minesweeper.command.*;
import com.lalaalal.minesweeper.GameHandler;
import com.lalaalal.minesweeper.Point;
import com.lalaalal.minesweeper.state.GameState;
import com.lalaalal.minesweeper.state.PlayingState;

import java.util.Scanner;

public class ConsoleGameHandler extends GameHandler {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ConsoleCleaner consoleCleaner = ConsoleCleaner.getConsoleCleaner();

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

        return new Board(rowLength, columnLength, bombCount);
    }

    public GameCommand getCommand() {
        System.out.print("Command : ");
        String command = scanner.next();

        CommandFactory commandFactory = new ConsoleCommandFactory(command);
        return commandFactory.createCommand();
    }


    @Override
    public GameState runCommand() {
        try {
            return getCommand().run(this);
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println(e.getMessage());
            return runCommand();
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

    public class ConsoleCommandFactory implements CommandFactory {
        private final String command;

        public ConsoleCommandFactory(String command) {
            this.command = command;
        }

        @Override
        public GameCommand createCommand() {
            return switch (command) {
                case "OPEN", "open", "o" -> new ConsoleOpenCommand();
                case "FLAG", "flag", "f" -> new ConsoleFlagCommand();
                case "EXIT", "exit" -> new ExitCommand();
                default -> throw new IllegalStateException("Unexpected command: " + command);
            };
        }
    }

    public class ConsoleOpenCommand extends OpenCommand {
        @Override
        public GameState run(GameHandler game) {
            GameState state = super.run(game);
            displayBoard();

            return state;
        }
    }

    public class ConsoleFlagCommand extends FlagCommand {
        @Override
        public GameState run(GameHandler game) {
            GameState state = super.run(game);
            displayBoard();

            return state;
        }
    }
}
