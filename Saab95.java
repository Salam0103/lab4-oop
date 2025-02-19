import java.awt.*;

public class Saab95 extends Car {
    private boolean turboOn;

    public Saab95() {
        super(2, 125, Color.red, "Saab95");
        turboOn = false;
    }

    public void setTurboOn() {
        turboOn = true;
    }

    public void setTurboOff() {
        turboOn = false;
    }

    @Override
    public double speedFactor() {
        double turbo = turboOn ? 1.3 : 1;
        return getEnginePower() * 0.01 * turbo;
    }

}

