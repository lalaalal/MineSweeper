package com.lalaalal.minesweeper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameHandler {
    private enum GameStatus {
        GAME_OVER,
        GAME_PLAYING,
        GAME_WIN,
        GAME_EXIT
    }

    private final String os = System.getProperty("os.name");
    private final Scanner scanner = new Scanner(System.in);

    private final Board board;
    private int flagCount = 0;

    public GameHandler() throws Exception {
        System.out.print("Row Length    ( 3 ~ 50 )   : ");
        int rowLength = scanner.nextInt();
        System.out.print("Column Length ( 3 ~ 50 )   : ");
        int columnLength = scanner.nextInt();
        System.out.printf("Bomb Count    ( 1 ~ %04d ) : ", rowLength * columnLength - 1);
        int bombCount = scanner.nextInt();

        board = new Board(rowLength, columnLength, bombCount);
    }

    public GameHandler(int rowLength, int columnLength, int bombCount) throws Exception {
        board = new Board(rowLength, columnLength, bombCount);
    }

    public GameHandler(Board board) {
        this.board = board;
    }

    public void clearConsole() {
        try {
            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makePlayableBoard() {
        try {
            System.out.print("Select First Point : ");
            Point point = Point.scanPoint(scanner);

            board.initBoard(point);
            openTile(point);
        } catch (InputMismatchException e) {
            System.out.println("Wrong Input\n");
            scanner.nextLine();
            makePlayableBoard();
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage() + "\n");
            makePlayableBoard();
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n");
            scanner.nextLine();
            makePlayableBoard();
        }
    }

    public void consolePlay() {
        makePlayableBoard();
        displayBoard();
        GameStatus gameStatus = GameStatus.GAME_PLAYING;
        while (gameStatus == GameStatus.GAME_PLAYING)
            gameStatus = runConsoleCommand();
        switch (gameStatus) {
            case GAME_WIN -> System.out.println("You Win!");
            case GAME_OVER -> System.out.println("Game Over!");
        }
    }

    public GameStatus runConsoleCommand() {
        try {
            System.out.print("Command : ");
            String command = scanner.next();

            ConsoleCommand consoleCommand = makeCommand(command);
            return consoleCommand.run();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Wrong Input\n");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage() + "\n");
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println(e.getMessage() + "\n");
        }
        return GameStatus.GAME_PLAYING;
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

    public GameStatus getOpenStatus(Point point) {
        if (board.isBomb(point))
            return GameStatus.GAME_OVER;
        else
            return GameStatus.GAME_PLAYING;
    }

    public void flagTile(Point point) { flagTile(point.x, point.y); }

    public void flagTile(int x, int y) {
        if (board.isTileFlagged(x, y)) flagCount--;
        else flagCount++;
        board.toggleTileFlag(x, y);
    }

    public GameStatus getFlagStatus() {
        if (board.getAnswer() == board.BOMB_COUNT)
            return GameStatus.GAME_WIN;
        else
            return GameStatus.GAME_PLAYING;
    }

    public void displayBoard() {
        clearConsole();
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

    public ConsoleCommand makeCommand(String command) throws Exception {
        return switch (command) {
            case "OPEN", "open", "Open" -> new OpenCommand();
            case "FLAG", "flag", "Flag" -> new FlagCommand();
            case "HELP", "help", "Help" -> new HelpCommand();
            case "EXIT", "exit", "Exit" -> new ExitCommand();
            default -> throw new Exception(String.format("Unknown Command (%s)", command));
        };
    }

    private interface ConsoleCommand {
        GameStatus run();
    }

    private class OpenCommand implements ConsoleCommand {
        @Override
        public GameStatus run() {
            Point point = Point.scanPoint(scanner);
            openTile(point);
            displayBoard();
            return getOpenStatus(point);
        }
    }

    private class FlagCommand implements ConsoleCommand {
        @Override
        public GameStatus run() {
            Point point = Point.scanPoint(scanner);
            flagTile(point);
            displayBoard();
            return getFlagStatus();
        }
    }

    private static class HelpCommand implements ConsoleCommand {
        @Override
        public GameStatus run() {
            System.out.println("COMMAND (OPEN, FLAG, EXIT) [x] [y]\n");
            return GameStatus.GAME_PLAYING;
        }
    }

    private static class ExitCommand implements ConsoleCommand {
        @Override
        public GameStatus run() {
            return GameStatus.GAME_EXIT;
        }
    }
}