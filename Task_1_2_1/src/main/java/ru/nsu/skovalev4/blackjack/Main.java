package ru.nsu.skovalev4.blackjack;

import java.util.Scanner;

/**
 * Starts the console blackjack application.
 */
public class Main {

    /**
     * Starts the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        BlackjackGame game = new BlackjackGame();
        ConsoleView view = new ConsoleView();
        Scanner scanner = new Scanner(System.in);

        BlackjackController controller = new BlackjackController(game, view, scanner);

        controller.run();


    }
}
