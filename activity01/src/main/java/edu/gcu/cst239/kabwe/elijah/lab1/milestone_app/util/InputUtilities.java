package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.util;

import java.time.LocalDate;
import java.util.Scanner;

public class InputUtilities {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static LocalDate readLocalDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " (YYYY-MM-DD): ");
                return LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid date format. Use YYYY-MM-DD.");
            }
        }
    }
}