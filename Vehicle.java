import java.awt.*;
import java.util.List;
import java.util.Observable;

public abstract class Vehicle extends Observable implements VehicleActions{

    private int nrDoors;
    private double enginePower;
    private double currentSpeed;
    private Color color;
    private String modelName;
    private double x;
    private double y;
    private int direction; // 0=North 1=East 2=South 3=West
    private VehicleState state;




    public Vehicle(int nrDoors, double enginePower, Color color, String modelName){

        this.nrDoors = nrDoors;
        this.enginePower = enginePower;
        this.color = color;
        this.modelName = modelName;
        this.x = 0; // Start position x
        this.y = 0; // Start position y
        this.direction = 0; // Start direction North
        this.state = new StoppedState();
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
        setChanged();
        notifyObservers();

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
        if (state instanceof StoppedState) {
            state.startEngine(this);
        }
    }

    public void stopEngine(){
        if (state instanceof StartedState) {
            state.stopEngine(this);
            currentSpeed = 0;
        }
    }

    public abstract double speedFactor();

    private void setCurrentSpeed(double speed) {
        currentSpeed = Math.max(0, Math.min(speed, enginePower));
    }
    private void incrementSpeed(double amount){
        currentSpeed = Math.min(getCurrentSpeed() + (speedFactor() * amount * 0.5), enginePower);
    }

    private void decrementSpeed(double amount){
        currentSpeed = Math.max(getCurrentSpeed() - speedFactor() * amount * 3,0);
    }

    public void gas(double amount){
        if (state instanceof StartedState) {
            incrementSpeed(amount);
        } else {
            System.out.println("Can't gas while the engine is off");
        }
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

    public VehicleState getState() {
        return state;
    }

    private void setState(VehicleState newState) {
        this.state = newState;
    }

    public void transitionToState(VehicleState newState) {
        setState(newState);
    }

}