package core;

import settings.GameSettings;

/**
 * A class to store and use both x and y values at the same, for example when storing positions.
 */
public class Vector2D {
    private double x;
    private double y;

    private GameSettings settings = GameSettings.getInstance();

    public double getX() {
        return x;
    }

    public void setX(double x){
        this.x = x;
    }

    public int intX() {
        return (int)x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y){
        this.y = y;
    }

    public int intY() {
        return (int)y;
    }

    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void add(Vector2D vectorToAdd){
        x += vectorToAdd.getX();
        y += vectorToAdd.getY();
    }

    public void subtract(Vector2D vectorToAdd){
        x -= vectorToAdd.getX();
        y -= vectorToAdd.getY();
    }

    public boolean isInRangeOf(Vector2D position) {
        if(position != null) {
            return Math.abs(x - position.getX()) < settings.getProximityRange() && Math.abs(y - position.getY()) < settings.getProximityRange();
        }
        return false;
    }

    public Vector2D getCopy() {
        return new Vector2D(x, y);
    }

    public Vector2D directionBetweenPositions(Vector2D other){
        Vector2D direction = new Vector2D(x - other.getX(), y - other.getY());
        direction.normalize();

        return direction;
    }

    public static double dotProduct(Vector2D v1, Vector2D v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }

    public void reset(boolean resetX, boolean resetY) {
        if(resetX){
            x = 0;
        }
        if(resetY){
            y = 0;
        }
    }

    public void multiply(double i) {
        x *= i;
        y *= i;
    }

    public void normalize() {
        double length = Math.sqrt(x * x + y * y);
        if(x != 0){
            x = x / length;
        }
        if(y != 0){
            y = y / length;
        }
    }

    public double distanceTo(Vector2D otherPosition){
        double deltaX = this.getX() - otherPosition.getX();
        double deltaY = this.getY() - otherPosition.getY();

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
