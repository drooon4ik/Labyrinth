package com.labyrinth.myapplication;

import java.util.Arrays;

/**
 * Created by Пользователь on 10.04.2015.
 */
public abstract class Vector {

    public float[] v;

    public Vector() {
        createFloatArray();
    }

    public Vector(float... x) {
        createFloatArray();
        for(int i = 0; i < x.length; i++) {
            v[i] = x[i];
        }
    }

    /**
     * Specifies the exact length of the inner array. Method is invoked within the constructor.
     */
    protected abstract void createFloatArray();

    public abstract void add(Vector second);

    public abstract void minus(Vector second);

    public float[] getVec() {
        return v.clone();
    }

    public abstract boolean isZero();

    @Override
    public String toString() {
        return Arrays.toString(v);
    }
}
