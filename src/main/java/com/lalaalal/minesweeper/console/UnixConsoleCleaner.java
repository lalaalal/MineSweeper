package com.lalaalal.minesweeper.console;

public class UnixConsoleCleaner extends ConsoleCleaner {
    @Override
    public void clean() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
