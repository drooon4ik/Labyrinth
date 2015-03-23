package com.labyrinth.myapplication;

import android.annotation.SuppressLint;
import android.opengl.Matrix;
import android.view.MotionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Navi
 * Date: 04.04.13
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
@SuppressLint("NewApi")
public class Camera {
    static int EyeDirection_worldspace;
    static float[] ProjMatrix = new float[16];
    static float[] ViewMatrix = new float[16];
    static float[] ProjViewMatrix = new float[16];
    static float[] eyePos = new float[3];
    static float[] viewDir = {0,0,-1,1};
	static int x,y, dx,dy;
	static float[] rotMatrix = new float[16];
    public static void setfrustumMProj(float left, float right, float bottom, float top, float near, float far){
        Matrix.frustumM(ProjMatrix, 0,left,right, bottom, top, near, far);
    }
    public static void setPerspectiveM( float fovy, float aspect, float zNear, float zFar){
        Matrix.perspectiveM(ProjMatrix, 0,fovy,aspect,zNear,zFar);
    }
    public static void setLookAtM(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ){
        Matrix.setIdentityM(rotMatrix, 0);
    	Matrix.setLookAtM(ViewMatrix,0,eyeX,eyeY,eyeZ,centerX,centerY, centerZ,upX, upY, upZ);
        Matrix.multiplyMM(ProjViewMatrix, 0, ProjMatrix, 0, ViewMatrix, 0);
        eyePos[0]=eyeX;eyePos[1]=eyeY;eyePos[2]=eyeZ;
    }
    public static float[] getProjMatrix(){
        return ProjMatrix;
    }
    public static float[] getViewMatrix(){
        return ViewMatrix;
    }
    public static float[] getProjViewMatrix(){
        return ProjViewMatrix;
    }
    public static float[] getEyePos(){
        return eyePos;
    }
    public static void rot(MotionEvent event) {
    	if(Matrix.length(event.getX()-x, event.getY()-y, 0) > 100) {
    		x = (int)event.getX();
    		y = (int)event.getY();
    	}
    	dx = (int)event.getX() - x;
    	dy = (int)event.getY() - y;
    	
    	boolean enter = false;
    	if(dy != 0)  {
    		Matrix.rotateM(rotMatrix, 0, (float)dy / 50, 1, 0, 0);
    		enter = true;
    	}
    	if(dx != 0) {
    		Matrix.rotateM(rotMatrix, 0, (float)dx / 50, 0, 1, 0);
    		enter = true;
    	}
    	if(enter) {
	    	Matrix.multiplyMV(viewDir, 0, rotMatrix, 0, viewDir, 0);
	    	Matrix.setLookAtM(ViewMatrix,0,0,0,0,viewDir[0],viewDir[1], viewDir[2],0, 1, 0);
	        Matrix.multiplyMM(ProjViewMatrix, 0, ProjMatrix, 0, ViewMatrix, 0);
	        Matrix.setIdentityM(rotMatrix, 0);
	    }
    	x = (int)event.getX();
		y = (int)event.getY();
    }
}
