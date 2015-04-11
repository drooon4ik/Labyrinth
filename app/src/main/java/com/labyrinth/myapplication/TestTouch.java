package com.labyrinth.myapplication;

import android.view.MotionEvent;
import android.view.View;

import com.core.Player;

public class TestTouch implements View.OnTouchListener {

    private float xStart, xFinish;
    private float yStart, yFinish;
    private Vector previous;
    private float length;
    private Player player;
    public TestTouch(Player player) {
        this.player = player;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xStart = x;
                yStart = y;
                yFinish = y;
                xFinish = x;
                break;
            case MotionEvent.ACTION_UP:
                xStart = x;
                yStart = y;
                yFinish = y;
                xFinish = x;
                break;
            case MotionEvent.ACTION_MOVE:
                xFinish = x;
                yFinish = y;
                break;
        }

        Vec2 vec = new Vec2(xFinish - xStart, yFinish - yStart);
        if(Vectors.length(vec) > 100) {

            float k = previous.getVec()[0] / previous.getVec()[1];
            float a = (1 + k * k);
            float b = -(2 * xFinish + 2 * k * k * xFinish);
            float c = (float)(xFinish * xFinish + k * k * xFinish * xFinish - 100 * 100);

            float D = (float)Math.sqrt((Math.pow(b ,2) - 4 * a * c));

            float xStartNew1 = (-b - D) / (2 * a);
            float xStartNew2 = (-b + D) / (2 * a);

            float yStartNew1 = yFinish + k * (xStartNew1 - xFinish);
            float yStartNew2 = yFinish + k * (xStartNew2 - xFinish);

            float length1 = (float)Math.sqrt(Math.pow(xStartNew1 - xStart, 2) + Math.pow(yStartNew1 - yStart, 2));
            float length2 = (float)Math.sqrt(Math.pow(xStartNew2 - xStart, 2) + Math.pow(yStartNew2 - yStart, 2));

            if(length1 < length2) {
                xStart = xStartNew1;
                yStart = yStartNew1;
            } else {
                xStart = xStartNew2;
                yStart = yStartNew2;
            }
            vec = new Vec2(xFinish - xStart, yStart - yFinish);
        }
        player.move((Vec2)Vectors.convert(vec,100));
        previous = new Vec2(vec.getVec()[0], vec.getVec()[1]);
        return true;
    }
}