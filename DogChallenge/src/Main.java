import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Dog dog = new Dog("Pedro", "white");
        dog.addToys("Ball");
        dog.addToys("Stick");

        String[] myToys = dog.getToys();
        String toysString;
        if (myToys[1] != null) {
            toysString = myToys[0] + " and a " + myToys[1];
        } else {
            toysString = myToys[0];
        }

        System.out.println(dog.getName() + " the " + dog.getColor() + " dog has a " + toysString + ".");
    }
}
