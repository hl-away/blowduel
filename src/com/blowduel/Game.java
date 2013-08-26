package com.blowduel;

import com.blowduel.object.Level;
import com.blowduel.object.Line;
import com.blowduel.object.Point;

import java.util.LinkedList;

/**
 * User: hl-away
 * Date: 26.08.13
 */
public class Game {
    private LinkedList<Level> levels;
    private int currentLevel;

    public Game(int width, int height) {
        levels = new LinkedList<Level>();
        Level level1 = new Level();
        level1.setLimitBubbles(100);
        LinkedList<Line> lines1 = new LinkedList<Line>();
        lines1.add(new Line(new Point(0,0), new Point(0,height), false));
        lines1.add(new Line(new Point(0,0), new Point(width,0), true));
        lines1.add(new Line(new Point(width,0), new Point(width,height), false));
        level1.setLines(lines1);
        levels.add(level1);
    }

    public LinkedList<Level> getLevels() {
        return levels;
    }

    public void setLevels(LinkedList<Level> levels) {
        this.levels = levels;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}
