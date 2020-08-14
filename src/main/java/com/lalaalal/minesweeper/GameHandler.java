package com.lalaalal.minesweeper;

import java.util.Scanner;

public class GameHandler {
    enum GameStatus {
        GAME_OVER,
        GAME_PLAYING,
        GAME_WIN,
        GAME_EXIT
    }

    private final Scanner scanner = new Scanner(System.in);

    private final Board board;

    public GameHandler() {
        System.out.print("Row Length      : ");
        int rowLength = scanner.nextInt();
        System.out.print("Column Length   : ");
        int columnLength = scanner.nextInt();
        System.out.print("Bomb Count      : ");
        int bombCount = scanner.nextInt();

        board = new Board(rowLength, columnLength, bombCount);
        board.initBoard();
    }

    public GameHandler(int rowLength, int columnLength, int bombCount) {
        board = new Board(rowLength, columnLength, bombCount);
        board.initBoard();
    }

    public GameHandler(Board board) {
        this.board = board;
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void consolePlay() {
        GameStatus gameStatus = GameStatus.GAME_PLAYING;
        displayBoard();
        while (gameStatus == GameStatus.GAME_PLAYING) {
            gameStatus = runConsoleCommand();
        }
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
        } catch (final Exception e) {
            System.out.println(e.getMessage());
        }
        return GameStatus.GAME_PLAYING;
    }

    public void openTile(int x, int y) {
        if (board.isTileOpened(x, y))
            return;
        board.openTile(x, y);
        if (board.getTileValue(x, y) == 0) {
            board.runSurroundAction(new Point(x, y), (point) -> openTile(point.x, point.y));
        }
    }

    public void flagTile(int x, int y) {
        board.toggleTileFlag(x, y);
    }

    public void displayBoard() {
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
    }

    public ConsoleCommand makeCommand(String command) throws Exception {
        return switch (command) {
            case "OPEN", "open", "Open" -> new OpenCommand();
            case "FLAG", "flag", "Flag" -> new FlagCommand();
            case "HELP", "help", "Help" -> new HelpCommand();
            case "EXIT", "exit", "Exit" -> new ExitCommand();
            default -> throw new Exception("Unknown Command");
        };
    }

    private interface ConsoleCommand {
        GameStatus run();
    }

    private class OpenCommand implements ConsoleCommand {
        @Override
        public GameStatus run() {
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            openTile(x, y);
            clearConsole();
            displayBoard();
            if (board.isBomb(x, y))
                return GameStatus.GAME_OVER;
            else
                return GameStatus.GAME_PLAYING;
        }
    }

    private class FlagCommand implements ConsoleCommand {
        @Override
        public GameStatus run() {
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            flagTile(x, y);
            clearConsole();
            displayBoard();
            if (board.getAnswer() == board.BOMB_COUNT)
                return GameStatus.GAME_WIN;
            else
                return GameStatus.GAME_PLAYING;
        }
    }

    private static class HelpCommand implements ConsoleCommand {
        @Override
        public GameStatus run() {
            System.out.println("COMMAND (OPEN, FLAG, HELP) [x] [y]\n");
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