package com.blowduel.object;

/**
 * Created by IntelliJ IDEA.
 * User: hl-away
 * Date: 09.08.13
 * Time: 0:34
 */
public class Point {
    public double x;
    public double y;

    public Point() {

    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void addToX(double x) {
        this.x += x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void addToY(double y) {
        this.y += y;
    }

    public void setPosition(double x, double y) {
        setX(x);
        setY(y);
    }
}
