import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Enter a single word, then a single number as input:");

        Scanner sc = new Scanner(System.in);

        String userInput = sc.next();
        int userNumber = sc.nextInt();

        String uppercase = userInput.toUpperCase();
        System.out.println(uppercase);

        char firstCharacter = userInput.charAt(0);
        System.out.println("The first character of the input is " + firstCharacter);
        System.out.println("You entered the number: " + userNumber);

        // can check if a string contains a substring
        System.out.println("Contains the string `enter`: " + userInput.contains("Enter".toLowerCase()));
    }
}
