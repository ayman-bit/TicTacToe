import java.util.Scanner;

/**
 * Contains the main method. Creates a ConsoleRunner and then calls its
 * mainLoop method.
 *
 * @author Ayman Abu Awad
 */

public class TicTacToeApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cont = "Y";
        System.out.println("Welcome to TicTacToe Game!");
        while(cont.matches("Y") || cont.matches("y")) {
            ConsoleRunner myObj = new ConsoleRunner();
            myObj.mainLoop();
            System.out.println("Would you like to play again?");
            cont = scanner.next();
        }
        System.out.println("GoodBye!");
    }
}