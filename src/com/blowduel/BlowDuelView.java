package com.blowduel;

import android.content.Context;
import android.graphics.*;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.blowduel.object.*;
import com.blowduel.util.SoundMeter;

import java.util.LinkedList;
import java.util.List;

/**
 * User: hl-away
 * Date: 08.08.13
 */
public class BlowDuelView extends SurfaceView implements SurfaceHolder.Callback {
    private BlowDuelManager blowDuelLoopThread;
    private SoundMeter soundMeter;
    private List<Bitmap> images;
    private List<Bubble> bubbles;
    private int maxBubbles = 0;
    private int width;
    private int width_d;
    private int height;
    private Paint textPaint;
    private Paint maxTextPaint;
    private SoundPool soundPool;
    private int soundID;
    private float currentX = 0;
    private float currentY = 0;
    private Game game;

    public BlowDuelView(Context context, AttributeSet attr) {
        super(context, attr);

        width = context.getWallpaperDesiredMinimumWidth()/2;
        width_d = width / 2;
        height = context.getWallpaperDesiredMinimumHeight();

        getHolder().addCallback(this);
        blowDuelLoopThread = new BlowDuelManager(this);
        soundMeter = new SoundMeter();
        soundMeter.start();
        initImages();
        initTextPaint();
        initOnClickListener();
        initSound();
        bubbles = new LinkedList<Bubble>();

        game = new Game(width, height);
    }

    private void initOnClickListener() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
                    currentX = event.getX();
                    currentY = event.getY();
                } else {
                    if(event.getAction() == MotionEvent.ACTION_POINTER_UP) {
                        currentX = 0;
                        currentY = 0;
                    } else {
                        if(event.getAction() == MotionEvent.ACTION_MOVE) {
                            currentX = event.getX();
                            currentY = event.getY();
                        }
                    }
                }
                return true;
            }
        });
    }

    private void initSound() {
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundID = soundPool.load(getContext(), R.raw.bubble, 10);
    }

    private void initTextPaint() {
        maxTextPaint = new Paint();
        maxTextPaint.setColor(Color.GREEN);
        maxTextPaint.setTextSize(40);
        textPaint = new Paint();
        textPaint.setColor(Color.LTGRAY);
        textPaint.setTextSize(40);
    }

    private void initImages() {
        images = new LinkedList<Bitmap>();
        //images.add(BitmapFactory.decodeResource(getResources(), R.drawable.bubble120));
        //images.add(BitmapFactory.decodeResource(getResources(), R.drawable.bubble231));
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.bubble28));
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.bubble34));
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.bubble51));
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.bubble67));
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.bubble90));
    }


    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        blowDuelLoopThread.setRunning(false);
        while (retry) {
            try {
                blowDuelLoopThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // Zzzzzzz
            }
        }
    }

    public void surfaceCreated(SurfaceHolder holder)
    {
        blowDuelLoopThread.setRunning(true);
        blowDuelLoopThread.start();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        double amp = soundMeter.getAmplitude();

        drawLevel(canvas);

        String s = String.valueOf(maxBubbles);
        String s2 = String.valueOf(bubbles.size());
        String s3 = String.valueOf(game.getCurrentLevel() + 1);
        canvas.drawText(s, 30, 50, maxTextPaint);
        canvas.drawText(s2, 30, 100, textPaint);
        canvas.drawText(s3, 30, 150, textPaint);

        if(amp > 0) {
            for(int i = (int) amp / 2; i > 0; i--) {
                int imageNumber = (int) (Math.random() * (images.size() - 1));
                Bubble bubble = new Bubble();
                bubble.setPosition(width_d - images.get(imageNumber).getWidth()/2, height - 10);
                bubble.setImageNumber(imageNumber);
                bubble.setSpeed(amp);
                bubble.setAngle((Math.random() * 6) - 3);
                bubbles.add(bubble);
            }
            if(bubbles.size() > maxBubbles) {
                maxBubbles = bubbles.size();
            }
        }

        if(bubbles.size() > 0) {
            processBubbles(canvas);
        }
    }

    private int savedLevel = -1;
    private Bitmap levelImage = null;

    private void drawLevel(Canvas canvas) {
        int currentLevel = game.getCurrentLevel();
        if(savedLevel != currentLevel) {
            savedLevel = currentLevel;
            Level level = game.getLevels().get(currentLevel);
            levelImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas levelCanvas = new Canvas(levelImage);
            for(Line line: level.getLines()) {
                Paint linePaint = new Paint();
                linePaint.setColor(line.isBonusLine()? Color.GREEN: Color.RED);
                linePaint.setStrokeWidth(5);
                levelCanvas.drawLine((int) line.getP1().x, (int) line.getP1().y,
                        (int) line.getP2().x, (int) line.getP2().y, linePaint);
            }
        }
        canvas.drawBitmap( levelImage, 0, 0, new Paint() );
    }

    private int currentProcess = 0;

    private void processBubbles(Canvas canvas) {
        for(Bubble bubble: bubbles) {
            if (bubble.isShow()) {
                moveBubble(bubble);
                burstBubble(bubble);
                canvas.drawBitmap(images.get(bubble.getImageNumber()), (int) bubble.getX(), (int) bubble.getY(), new Paint());
            }
        }

        if(currentProcess == 100) {
            LinkedList<Bubble> tempBubbles = new LinkedList<Bubble>();
            for(Bubble bubble: bubbles) {
                if(bubble.isShow()) {
                    tempBubbles.add(bubble);
                }
            }
            if(tempBubbles.size() != bubbles.size()) {
                bubbles = tempBubbles;
            }
            currentProcess = 0;
        } else {
            currentProcess++;
        }
    }

    private void moveBubble(Bubble bubble) {
        bubble.addToY(-bubble.getSpeed());
        bubble.addToX(bubble.getAngle());
        if(bubble.getY() < 0 || bubble.getX() < 0 || bubble.getX() + images.get(bubble.getImageNumber()).getWidth() > width) {
            soundPool.play(soundID, 50, 50, 1, 0, 1f);
            bubble.setShow(false);
        }
    }

    private void burstBubble(Bubble bubble) {
        if(currentX != 0 && currentY != 0) {
            if(currentX > bubble.getX() &&  currentY > bubble.getY() &&
                    currentX < bubble.getX() + images.get(bubble.getImageNumber()).getWidth() &&
                     currentY < bubble.getY() + images.get(bubble.getImageNumber()).getHeight()) {
                soundPool.play(soundID, 50, 50, 1, 0, 1f);
                bubble.setShow(false);
            }
        }
    }
}
