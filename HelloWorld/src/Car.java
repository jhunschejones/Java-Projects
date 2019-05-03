import java.awt.*;

public class Car {

    double averageMilesPerCGallon;
    String licensePlate;
    Color paintColor;
    boolean areTailLightsWorking;

    public Car(double inputAverageMPG,
               String inputLicensePlate,
               Color inputPaintColor,
               boolean inputTailLights) {

        this.averageMilesPerCGallon = inputAverageMPG;
        this.licensePlate = inputLicensePlate;
        this.paintColor = inputPaintColor;
        this.areTailLightsWorking = inputTailLights;
    }

    public void changePaintColor(Color newPaintColor) {
        this.paintColor = newPaintColor;
    }

    public double speeedUp(double currentSpeed ) {
        currentSpeed += 100;
        return currentSpeed;
    }
}
