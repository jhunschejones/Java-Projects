package com.company;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static SimpleCalculator c = new SimpleCalculator();

    public static void main(String[] args) {
        System.out.println("Welcome to the simple calculator!\n");

        String firstValue = askForInput("Enter a numeric value: ").trim();
        String operation = askForInput("Choose an operation ( + - * / ): ").trim();
        String secondValue = askForInput("Enter another numeric value: ").trim();

        // change terminal text color to blue then reset
        System.out.println(String.format("%s %s %s is \u001B[34m%s\u001B[0m",
                firstValue, operation, secondValue, c.preformOperation(operation, firstValue, secondValue)));
    }

    public static String askForInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
