package com.labyrinth.myapplication;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.core.Player;

import utilities.ShaderLoader;


public class Scene implements GLSurfaceView.Renderer {

    private ShaderLoader ShdLoad;
	private TextureLoader TexLoad;
	private Player sm;
	private Player[] sm2 = new Player[10];

    private float x, y;
	public Scene(Context context) {
        G.setContext(context);
    }

    public void onDrawFrame(GL10 glUnused) {
        G.TimeUpdate();
        GLES20.glClearColor(0.0f,1.04f,0.0f,1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        //GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        
        sm.Draw();
        for(int i = 0; i < 4; i++) {
            sm2[i].Draw();
        }
    }
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
    	System.out.println("Helo mather fucker");
        GLES20.glViewport(0, 0, width, height);
        G.setScreenXY(width, height);
        Vec2 pos = new Vec2(0.0f,0.0f);
        sm = new Player("mesh1",ShdLoad.Load("sample1"),TexLoad.Load("texture",true,true,true), pos);
        for(int i = 0; i < 3; i++) {
            sm2[i] = new Player("mesh1",ShdLoad.Load("sample1"),TexLoad.Load("texture",true,true,true), new Vec2(i, 1));
        }
        sm2[3] = new Player("mesh1",ShdLoad.Load("sample1"),TexLoad.Load("texture",true,true,true), new Vec2(2, 0));

        float ratio = (float) width / height;

        Camera.setfrustumMProj(-ratio, ratio, -1, 1, 1.0f, 60);
        Camera.setLookAtM(0.0f, 4.1f, 0.1f, 0, 0, 0, 0, 1.0f, 0);

        //Camera.EyeDirection_worldspace = GLES20.glGetUniformLocation(sm.shad.prog_id,  "EyeDirection_worldspace");
        //GLES20.glUniform3fv(Camera.EyeDirection_worldspace,1,Camera.eyePos,0);
    }


	@Override
	public void onSurfaceCreated(GL10 gl,
			javax.microedition.khronos.egl.EGLConfig config) {
		// TODO Auto-generated method stub
        ShdLoad = new ShaderLoader();
        TexLoad = new TextureLoader();


        System.gc();
	}
    public void onTouchEvent(final MotionEvent event) {
        int e = event.getAction();

        if(e == MotionEvent.ACTION_MOVE) {
            float nx = event.getX();
            float ny = event.getY();
            float dx = nx - x;
            float dy = ny - y;
            if(Math.abs(dx) < 100 && Math.abs(dy) < 100) {
                sm.translate(new Vec2(dx / 100.0f, dy / 100.0f));
                for(int i = 0; i < 4; i++) {
                    sm.collide(sm2[i].aabb);
                }
            }

            x = event.getX();
            y = event.getY();
        }

        Camera.setLookAtM(sm.ModelMatrix[12], 4.0f, sm.ModelMatrix[14]+0.001f, sm.ModelMatrix[12], 0, sm.ModelMatrix[14], 0, 1.0f, 0);
        //GLES20.glUniform3fv(Camera.EyeDirection_worldspace,1,Camera.eyePos,0);
    }
}
