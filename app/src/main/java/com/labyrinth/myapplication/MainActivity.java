package com.labyrinth.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.WindowManager;

public class MainActivity extends Activity {
	private GLSurfaceView glSurfaceView;
	private GLRenderer render;
	private Handler mHandler = new Handler();
	private Boolean RPause = false;
	private int FPS = 60;
    private TestTouch tt = new TestTouch();

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		glSurfaceView = new GLSurfaceView(this);
		if (Build.VERSION.SDK_INT > 10) {
            glSurfaceView.setPreserveEGLContextOnPause(true);
        }
		glSurfaceView.getHolder().setFormat(PixelFormat.RGBA_8888);
		glSurfaceView.setEGLContextClientVersion(2);
		// glSurfaceView.setEGLConfigChooser(ConfigChooser = new Config3D888());
		glSurfaceView.setEGLConfigChooser(true);
		render = new GLRenderer(this);
		glSurfaceView.setRenderer(render);
		glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		setContentView(glSurfaceView);
		Shared.res = getResources();
	}

	void reqRend() {
		mHandler.removeCallbacks(mDrawRa);
		if (!RPause) {
			mHandler.postDelayed(mDrawRa, 1000 / FPS); 	//mDrawRa
			glSurfaceView.requestRender();
		}
	}

	private final Runnable mDrawRa = new Runnable() {
		public void run() {
			reqRend();
		}
	};

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        glSurfaceView.queueEvent(new Runnable() {
            public void run() {
                render.onTouchEvent(event);
//                tt.onTouch(null, event);
            }});
        return true;
    }

	@Override
	protected void onPause() {
		super.onPause();
		glSurfaceView.onPause();
		RPause = true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		glSurfaceView.onResume();
		RPause = false;
		reqRend();
	}

	@Override
	protected void onStop() {
		super.onStop();
		RPause = true;
		this.finish();
	}
}
