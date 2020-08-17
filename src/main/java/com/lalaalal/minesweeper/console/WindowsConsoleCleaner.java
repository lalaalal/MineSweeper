package com.lalaalal.minesweeper.console;

import java.io.IOException;

public class WindowsConsoleCleaner extends ConsoleCleaner {
    @Override
    public void clean() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
