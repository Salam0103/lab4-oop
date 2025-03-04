import java.awt.*;

public abstract class Vehicle implements VehicleActions{

    private int nrDoors; // Number of doors on the car
    private double enginePower; // Engine power of the car
    private double currentSpeed; // The current speed of the car
    private Color color; // Color of the car
    private String modelName; // The car model name

    private double x; // car's position on x-axis
    private double y; // car's position on y-axis
    private int direction; // 0=North 1=East 2=South 3=West

    public Vehicle(int nrDoors, double enginePower, Color color, String modelName){

        this.nrDoors = nrDoors;
        this.enginePower = enginePower;
        this.color = color;
        this.modelName = modelName;
        this.x = 0; // Start position x
        this.y = 0; // Start position y
        this.direction = 0; // Start direction North
        stopEngine();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public void move() {
        switch (direction) {
            case 0: // North
                y += currentSpeed;
                break;
            case 1: // East
                x += currentSpeed;
                break;
            case 2: // South
                y -= currentSpeed;
                break;
            case 3: // West
                x -= currentSpeed;
                break;
        }
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public void turnLeft() {
        direction = (direction - 1 + 4) % 4;
    }

    @Override
    public void turnRight() {
        direction = (direction + 1) % 4;
    }

    public int getNrDoors(){
        return nrDoors;
    }

    public double getEnginePower(){
        return enginePower;
    }

    public double getCurrentSpeed(){
        return currentSpeed;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color clr){
        color = clr;
    }

    public void startEngine(){
        currentSpeed = 0.1;
    }

    public void stopEngine(){
        currentSpeed = 0;
    }

    public abstract double speedFactor();

    private void incrementSpeed(double amount){
        currentSpeed = Math.min(getCurrentSpeed() + speedFactor() * amount,enginePower);
    }

    private void decrementSpeed(double amount){
        currentSpeed = Math.max(getCurrentSpeed() - speedFactor() * amount * 3,0);
    }

    public void gas(double amount){
        if (amount < 0 || amount > 1 ) {
            throw new IllegalArgumentException("Only accepts values in the range [0,1]");
        }
        incrementSpeed(amount);
    }

    public void brake(double amount){
        if (amount < 0 || amount > 1 ) {
            throw new IllegalArgumentException("Only accepts values in the range [0,1]");
        }
        decrementSpeed(amount);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

}