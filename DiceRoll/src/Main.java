import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Dice d = new Dice();
        Scanner s = new Scanner(System.in);
        String userInput = new String();

        System.out.print("Press enter to roll the dice...");
        userInput = s.nextLine();

        while(userInput.isEmpty()) {
            System.out.println("Dice 1 rolled: " + d.roll() + "\nDice 2 rolled: " + d.roll());
            userInput = s.nextLine();
        }
    }
}
