package com.labyrinth.myapplication;

import android.opengl.Matrix;

public class AABB {
	public Vector[] vertices;
	public Vector[] verticesMod;
	public int numPoints = 4;
	
//	private Vec2 min_axis = new Vec2(0, 0);
	private Vector[] m_Axis = new Vec2[4];
	
	public AABB(Vec2 position, float w, float h) {

        vertices = new Vec2[] {
                new Vec2( - w / 2, - h / 2),
                new Vec2( + w / 2, - h / 2),
                new Vec2( + w / 2, + h / 2),
                new Vec2( - w / 2, + h / 2)
        };

        verticesMod = new Vec2[4];
		for(int i = 0; i < 4; i++) {
            m_Axis[i] = new Vec2(0.0f,0.0f);
            verticesMod[i] = new Vec2(0,0);
			verticesMod[i].v = vertices[i].v.clone();
		}
	}
	
	public void updateVerts(float[] matrix) {

        for (int i = 0; i < 4; i++) {
            float[] rez = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
            float[] mul = new float[4];

            mul[0] = vertices[i].v[0];
            mul[1] = 0;
            mul[2] = vertices[i].v[1];
            mul[3] = 1.0f;

            Matrix.multiplyMV(rez, 0, matrix, 0, mul, 0);
            verticesMod[i].v[0] = rez[0];
            verticesMod[i].v[1] = rez[2];
        }

        for(int i = 0; i < 4; i++) {
            m_Axis[i] = Vectors.minus(verticesMod[(i + 1) % numPoints], verticesMod[i]);
            m_Axis[i] = Vectors.normalize(m_Axis[i]);
            m_Axis[i] = new Vec2(-m_Axis[i].v[1], m_Axis[i].v[0]);
        }
	}

	public boolean CollideRect(AABB other, float[] matrix) {
		float[] min_t = new float[]{0.0f};
		Vec2 min_axis = new Vec2(0.0f, 0.0f);
		if(!TestOverlaps(other, min_axis, min_t)) return false;
		if(!other.TestOverlaps(this, min_axis, min_t)) return false;
		
		Vector mvec = Vectors.multiply(min_axis,  min_t[0]);
		Matrix.translateM(matrix,0,mvec.v[0],0,mvec.v[1]);

		return true;
	}
	
	private boolean TestOverlaps(AABB other, Vec2 min_axis, float[] min_t) {
		final Vector[] other_corner = other.verticesMod.clone();
		int other_corner_count = other.numPoints;
		for(int i = 0; i < numPoints; i++) {
			float[] minMaxA = new float[]{0.0f,0.0f};
			float[] minMaxB = new float[]{0.0f,0.0f};
			getInterval(verticesMod, numPoints, m_Axis[i], minMaxA);
			getInterval(other_corner, other_corner_count, m_Axis[i], minMaxB);
			if((minMaxA[1] <= minMaxB[0]) || (minMaxB[1] <= minMaxA[0])) {
				return false;
			}
            float t = 0;
            if(minMaxA[1] >= minMaxB[0]) {
                t = minMaxB[0] - minMaxA[1];
                if(min_axis.isZero() || Math.abs(min_t[0]) > Math.abs(t)) {
                    min_t[0] = t;
                    min_axis.v = m_Axis[i].v.clone();
                }
            }
            if(minMaxB[1] >= minMaxA[0]) {
                t = minMaxB[1] - minMaxA[0];
                if(min_axis.isZero() || Math.abs(min_t[0]) > Math.abs(t)) {
                    min_t[0] = t;
                    min_axis.v = m_Axis[i].v.clone();
                }
            }
		}
		return true;
	}
	private void getInterval(Vector[] verts, int count, Vector axis, float[] minMax) { //������ min � max int?
		minMax[0] = minMax[1] = Vectors.dot(axis, verts[0]);
		for(int i = 1; i < count; i++) {
			float value = Vectors.dot(axis, verts[i]);
			minMax[0] = Math.min(minMax[0],  value);
			minMax[1] = Math.max(minMax[1],  value);
		}
	}
}
