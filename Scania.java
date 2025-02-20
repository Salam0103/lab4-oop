import java.awt.*;

public class Scania extends Truck {
    private double flakAngle;
    private boolean hasPrintedWarning;


    public Scania() {
        super(2, 400, Color.GRAY, "Scania");
        this.flakAngle = 0;
        this.hasPrintedWarning = false;
    }

    @Override
    public void brake(double amount) {
        double reducedBrakeAmount = amount * 0.1;
        super.brake(reducedBrakeAmount);

    }

    @Override
    public double speedFactor() {
        return getEnginePower() * 0.03;
    }

    @Override
    public void move(){
        if (flakAngle > 0 && getCurrentSpeed() > 0) {
            if (!hasPrintedWarning) {
                System.out.println("Flaket är uppe! Får ej köras");
                hasPrintedWarning = true;
            }
            return;
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
    @Override
    public void gas(double amount) {
        super.gas(amount*0.1);
    }

}
