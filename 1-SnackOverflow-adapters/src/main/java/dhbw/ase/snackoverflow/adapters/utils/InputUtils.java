package dhbw.ase.snackoverflow.adapters.utils;

import java.util.Scanner;

public class InputUtils {
    public static int getIntInput(String output, Scanner scanner) {
        while (true) {
            try {
                System.out.print(output);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
