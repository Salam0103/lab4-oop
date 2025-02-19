import java.awt.*;

public class Scania extends Truck {
    private double flakAngle;

    public Scania() {
        super(2, 400, Color.GRAY, "Scania");
        this.flakAngle = 0;
    }

    @Override
    public double speedFactor() {
        return getEnginePower() * 0.1;
    }

    @Override
    public void move(){
        if (flakAngle > 0) {
            throw new IllegalStateException("Lastbilen får inte köra med flaket uppe");
        }
        super.move();
    }

    public double getFlakAngle() {
        return flakAngle;
    }

    public void setFlakAngle(double angle){
        if(angle < 0 || angle > 70) {
            throw new IllegalArgumentException("Flaket kan inte ha vinkel lägre än 0 eller högre än 70");
        }
        flakAngle = angle;
    }

    public void raiseFlak() {
        if (getCurrentSpeed() > 0) {
            throw new IllegalStateException("Flaket kan ej höjas vid rörelse");
        }
        if (flakAngle < 70) {
            flakAngle += 10; //Höjs med 10 grader (finns ej någon specifik värde)
        }
    }

    public void LowerFlak() {
        if (flakAngle > 0) {
            flakAngle -= 10;
        }
    }

}
