package com.labyrinth.myapplication;

public class Vec2 extends Vector implements  Cloneable{
	
	public Vec2(float x, float y) {
		super(x, y);
	}

    public Vec2(float... fs) {
        super(fs);
    }

    @Override
    protected void createFloatArray() {
        v = new float[2];
    }

    @Override
	public void add(Vector second) {
		v[0] += second.getVec()[0];
		v[1] += second.getVec()[1];
	}

    @Override
	public void minus(Vector second) {
        v[0] -= second.getVec()[0];
        v[1] -= second.getVec()[1];
	}

    @Override
    public boolean isZero() {
        if(v[0] == 0.0f && v[1] == 0.0f)
            return true;
        return false;
    }
}