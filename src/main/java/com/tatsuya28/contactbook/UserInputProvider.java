package com.tatsuya28.contactbook;

import java.util.Scanner;

public class UserInputProvider {
    private static Scanner scanner = new Scanner(System.in);

    public static String getUserInput(String userRequest) {
        System.out.println(userRequest);
        return scanner.nextLine();
    }

    public static void setUserInputProvider(Scanner userInputScanner) {
        scanner = userInputScanner;
    }

    public static void closeScanner() {
        scanner.close();
    }
}
