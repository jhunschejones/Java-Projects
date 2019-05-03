import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.print("Enter an age: ");
        Scanner input = new Scanner(System.in);
        int userAge = input.nextInt();

        if (userAge > 0 && userAge < 5) {
            System.out.println("How are you typing? " + userAge + " is a baby!");
        } else if (userAge > 4 && userAge < 12) {
            System.out.println(userAge + "is a kid");
        } else if (userAge > 11 && userAge < 18) {
        System.out.println(userAge + " is a teenager");
        } else if (userAge > 17) {
            System.out.println(userAge + " is an adult");
        } else {
            System.out.println(userAge + " is invalid input.");
        }
    }
}
