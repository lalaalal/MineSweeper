package com.lalaalal.minesweeper;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        GameHandler game = new GameHandler();

        game.consolePlay();

        System.out.println("Press Enter to Exit");
        new Scanner(System.in).nextLine();
    }
}
