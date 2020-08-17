package com.lalaalal.minesweeper;

import com.lalaalal.minesweeper.console.ConsoleGameHandler;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try {
            GameHandler game = new ConsoleGameHandler();

            game.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.print("Press Enter to Continue");
            new Scanner(System.in).nextLine();
        }
    }
}
