package com.blowduel.object;

import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: hl-away
 * Date: 16.08.13
 * Time: 0:09
 */
public class Level {
    private int number = 0;
    private int limitBubbles = 0;
    LinkedList<Line> lines;

    public Level() {
        lines = new LinkedList<Line>();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getLimitBubbles() {
        return limitBubbles;
    }

    public void setLimitBubbles(int limitBubbles) {
        this.limitBubbles = limitBubbles;
    }

    public LinkedList<Line> getLines() {
        return lines;
    }

    public void setLines(LinkedList<Line> lines) {
        this.lines = lines;
    }
}
