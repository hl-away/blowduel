package com.blowduel;

import android.graphics.Canvas;

/**
 * Created by IntelliJ IDEA.
 * User: hl-away
 * Date: 08.08.13
 * Time: 23:58
 */
public class BlowDuelManager extends Thread
{
    static final long FPS = 25;

    private BlowDuelView view;

    private boolean running = false;

    public BlowDuelManager(BlowDuelView view) {
        this.view = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run()
    {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas canvas = null;
            startTime = System.currentTimeMillis();
            try {
                canvas = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.postInvalidate();
                }
            } finally {
                if (canvas  != null) {
                    view.getHolder().unlockCanvasAndPost(canvas);
                }
            }
            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception ignored) {}
        }
    }
}
