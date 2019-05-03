import java.util.Arrays;
import java.util.List;

public class Dog {

    private String color;
    private String name;
    private String[] toys = new String[2];

    public Dog() {
        this.color = "brown";
        this.name = "Sparkles";
        this.toys[0] = "Bone";
    }

    public Dog(String name) {
        this.color = "brown";
        this.name = name;
        this.toys[0] = "Bone";
    }

    public Dog(String name, String color) {
        this.color = color;
        this.name = name;
        this.toys[0] = "Bone";
    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }

    public String[] getToys() {
        return this.toys;
    }

    public void addToys(String newToy) {
        if (this.toys[1] != null) {
            System.out.println("You replaced " + this.name + "'s " + this.toys[1] + " with a " + newToy + ".\n");
        }
        this.toys[1] = newToy;
    }
}
