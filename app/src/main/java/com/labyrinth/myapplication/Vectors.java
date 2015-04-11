package com.labyrinth.myapplication;

import java.lang.reflect.InvocationTargetException;

public class Vectors implements  Cloneable{

    static public Vector minus(Vector first, Vector second) {
        if(first.getClass() != second.getClass()) {
            throw new IllegalArgumentException("Incompatible arguments: first = " + first.getClass() + ", second " + second.getClass());
        }
        float[] params = new float[first.getVec().length];
        for (int i = 0; i < first.getVec().length; i++) {
            params[i] = first.getVec()[i] - second.getVec()[i];
        }
        return instantiate(params, first.getClass());
    }

    static public Vector multiply(Vector first, Vector second) {
        if(first.getClass() != second.getClass()) {
            throw new IllegalArgumentException("Incompatible arguments: first = " + first.getClass() + ", second " + second.getClass());
        }
        float[] params = new float[first.getVec().length];
        for (int i = 0; i < params.length; i++) {
            params[i] = first.getVec()[i] * second.getVec()[i];
        }
        return instantiate(params, first.getClass());
    }

    static public Vector multiply(Vector first, float number) {
        float[] params = new float[first.getVec().length];
        for (int i = 0; i < params.length; i++) {
            params[i] = first.getVec()[i] * number;
        }
        return instantiate(params, first.getClass());
    }

    static public float length(Vector vec) {
        float len = 0;
        for (int i = 0; i < vec.getVec().length; i++) {
            len += vec.getVec()[i] * vec.getVec()[i];
        }
        return (float) Math.sqrt(len);
    }

    static public Vector normalize(Vector vec) {
        float[] params = new float[vec.getVec().length];
        for (int i = 0; i < params.length; i++) {
            params[i] = vec.getVec()[i] / length(vec);
        }
        return instantiate(params, vec.getClass());
    }

    private static Vector instantiate(float[] params, Class clazz) {
        try {
            return (Vector)clazz.getConstructor(float[].class).newInstance(params);
        } catch (NoSuchMethodException |
                InvocationTargetException |
                InstantiationException |
                IllegalArgumentException |
                IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static float dot(Vector first, Vector second) {
        float result = 0;
        for (int i = 0; i < first.getVec().length; i++) {
            result += first.getVec()[i] * second.getVec()[i];
        }
        return result;
    }

    public static Vector convert(Vector vec, float sampleLength) {
        float[] params = new float[vec.getVec().length];
        for (int i = 0; i < params.length; i++) {
            params[i] = vec.getVec()[i] / sampleLength;
        }
        return instantiate(params, vec.getClass());
    }
}
