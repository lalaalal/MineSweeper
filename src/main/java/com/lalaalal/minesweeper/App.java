package com.lalaalal.minesweeper;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try {
            GameHandler game = new GameHandler();

            game.consolePlay();

            System.out.println("Press Enter to Exit");
            new Scanner(System.in).nextLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
