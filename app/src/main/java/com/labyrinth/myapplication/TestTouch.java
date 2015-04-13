package com.labyrinth.myapplication;

import android.view.MotionEvent;
import android.view.View;

import com.core.Player;

public class TestTouch implements View.OnTouchListener {

    private float xStart, xFinish;
    private float yStart, yFinish;
    private Player player;
    private int radius;
    public TestTouch(Player player) {
        this.player = player;
        radius = 300;
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

                break;
            case MotionEvent.ACTION_MOVE:
                xFinish = x;
                yFinish = y;
                break;
        }

        Vec2 vec = new Vec2(xFinish - xStart, yFinish - yStart);
        if(Vectors.length(vec) > 300) {
            Vec2 bound = (Vec2)Vectors.add(new Vec2(xStart,yStart), Vectors.multiply(Vectors.normalize(vec),  radius));//Точка, находящаяся на границе выхода за радиус.
            Vec2 moveVector = new Vec2(xFinish - bound.v[0], yFinish - bound.v[1]);
            xStart += moveVector.v[0];
            yStart += moveVector.v[1];
            vec = new Vec2(xFinish - xStart, yFinish - yStart);
        }
        player.move((Vec2)Vectors.convert(vec,300));
        return true;
    }
}