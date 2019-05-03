import java.awt.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World.");

        Car joshCar = new Car(34,
                "1BC32E",
                Color.PINK,
                true);
        Car daniCar = new Car(21.5,
                "475DAK",
                Color.WHITE,
                true);
        System.out.println("My car's license plate is " + joshCar.licensePlate);
        System.out.println("Dani's car's license plate is " + daniCar.licensePlate);

        System.out.println("My car used to be " + joshCar.paintColor);
        joshCar.changePaintColor(Color.BLACK);
        System.out.println("But now it's " + joshCar.paintColor);

        double currentSpeed = 50;
        System.out.println("Speeding now! " + joshCar.speeedUp(currentSpeed));
    }
}
