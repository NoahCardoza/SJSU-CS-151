import java.util.Scanner;

public class MyCalendarTester {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CLI cli = new CLI(scanner);

        cli.mainLoop();

        scanner.close();
    }
}