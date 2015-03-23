package com.labyrinth.myapplication;

public class Vec2 {
	public float[] v = new float[4];;
	
	public Vec2(float x, float z) {
		v[0] = x;
		v[1] = 0;
		v[2] = z;
		v[3] = 0;
	}
	
	public void add(Vec2 second) {
		v[0] += second.getX();
		v[2] += second.getZ();
	}
	public void minus(Vec2 second) {
		v[0] -= second.getX();
		v[2] -= second.getZ();
	}
	static public Vec2 minus(Vec2 first, Vec2 second) {
		Vec2 rez = new Vec2(0.0f, 0.0f);
		rez.v[0] = first.v[0] - second.v[0];
		rez.v[2] = first.v[2] - second.v[2];
		
		return rez;
	}
    static public Vec2 multiply(Vec2 first, Vec2 second) {
        Vec2 rez = new Vec2(0.0f, 0.0f);
        rez.v[0] = first.v[0] * second.v[0];
        rez.v[2] = first.v[2] * second.v[2];

        return rez;
    }
    static public Vec2 multiply(Vec2 first, float second) {
        Vec2 rez = new Vec2(0.0f, 0.0f);
        rez.v[0] = first.v[0] * second;
        rez.v[2] = first.v[2] * second;

        return rez;
    }
	static public float length(Vec2 vec) {
		return (float) Math.sqrt((vec.v[0] * vec.v[0] + vec.v[2] * vec.v[2]));
	}
	static public Vec2 normalize(Vec2 vec) {
		Vec2 rez = new Vec2(0.0f, 0.0f);
		float length = length(vec);
		rez.v[0] = vec.v[0] / length;
		rez.v[2] = vec.v[2] / length;
		
		return rez;
	}
	static public float dot(Vec2 vec1, Vec2 vec2) {
		return vec1.v[0] * vec2.v[0] + vec1.v[2] * vec2.v[2];
	}
	
	public float getX() {
		return v[0];
	}
	public float getZ() {
		return v[2];
	}
	public float[] getVec() {
		return v;
	}
	public void setX(float x) {
		v[0] = x;
	}
	public void setZ(float z) {
		v[2] = z;
	}

    public boolean isZero() {
        if(v[0] == 0.0f && v[2] == 0.0f)
            return true;
        return false;
    }

}
