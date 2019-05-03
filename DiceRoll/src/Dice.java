import java.util.Random;

public class Dice {

    private int faces;

    public Dice() {
        this.faces = 6;
    }

    public Dice(int faces) {
        this.faces = faces;
    }

    public int roll() {
        Random random = new Random();
        return random.nextInt(this.faces) + 1;
    }

    public int[] rullMultiple(int numberOfRolls) {
        int[] randomNumbers = new int[numberOfRolls];
        for (int i = 0; i < numberOfRolls; i++) {
            randomNumbers[i] = this.roll();
        }

        return randomNumbers;
    }

}
