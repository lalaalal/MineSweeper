package com.lalaalal.minesweeper.console;

public abstract class ConsoleCleaner {
    public static ConsoleCleaner getConsoleCleaner() {
        String os = System.getProperty("os.name");
        if (os.contains("Windows"))
            return new WindowsConsoleCleaner();
        else
            return new UnixConsoleCleaner();
    }

    public abstract void clean();
}
