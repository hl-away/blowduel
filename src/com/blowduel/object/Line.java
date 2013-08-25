package com.blowduel.object;

/**
 * Created by IntelliJ IDEA.
 * User: hl-away
 * Date: 16.08.13
 * Time: 0:36
 */
public class Line {
    private Point p1;
    private Point p2;
    private boolean bonusLine = false;

    public Line(Point p1, Point p2, boolean bonusLine) {
        this.p1 = p1;
        this.p2 = p2;
        this.bonusLine = bonusLine;
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public boolean isBonusLine() {
        return bonusLine;
    }

    public void setBonusLine(boolean bonusLine) {
        this.bonusLine = bonusLine;
    }
}
