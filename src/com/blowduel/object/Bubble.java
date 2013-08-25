package com.blowduel.object;

/**
 * Created by IntelliJ IDEA.
 * User: hl-away
 * Date: 09.08.13
 * Time: 0:33
 */
public class Bubble extends Point {
    private int imageNumber = 0;
    private double angle = 0;
    private double speed = 1;
    private boolean show = true;

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }

    public int getImageNumber() {
        return imageNumber;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
