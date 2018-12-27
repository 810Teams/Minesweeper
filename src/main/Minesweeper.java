/**
 * `Minesweeper` Main Class
 * by Teerapat Kraisrisirikul
 */

package main;

public final class Minesweeper {
    public static void main(String[] args) {
        macSetup();
        MinesweeperLauncher launcher = new MinesweeperLauncher();
        launcher.run();
    }

    public static void macSetup() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", "Minesweeper");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Minesweeper");
    }
}
