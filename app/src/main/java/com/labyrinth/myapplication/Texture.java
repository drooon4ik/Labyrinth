package com.labyrinth.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class Texture {
    private String PACKAGE_NAME;
    private int[] texture_id;
    private boolean mipmaps=false;

    public int sizeX,sizeY = 0;


    public Texture(String name, Boolean filter,Boolean dupli, Boolean mipmaps){
        this.mipmaps=mipmaps;

        PACKAGE_NAME = G.getContext().getApplicationContext().getPackageName();
        Bitmap bm = Load_Bitmap(name);
        initTexture(bm, dupli, filter);
    }

    public Texture(Bitmap bm, Boolean dupli, Boolean filter){
        initTexture(bm, dupli, filter);
    }

    private void initTexture(Bitmap bm,Boolean dupli,Boolean filter){
        texture_id = new int[1];
        GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, 1);
        GLES20.glGenTextures(1, texture_id, 0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture_id[0]);

        if(bm!=null){
            int format = GLUtils.getInternalFormat(bm);
            Log.e("OpenGL", "InternalFormat ="+format);
            int type = GLES20.GL_UNSIGNED_BYTE;
            try{
                type = GLUtils.getType(bm);
            }catch(IllegalArgumentException e){
                Log.e("OpenGL", "bitmap illegal type");
            }


            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, format, bm, type, 0);

            int err = GLES20.glGetError();
                        sizeX = bm.getHeight();
            sizeY = bm.getWidth();
            bm.recycle();
            bm = null;
            System.gc();
        }

        if(!mipmaps){
            if(filter){
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_LINEAR);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);}
            else{
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_NEAREST);
            }
        }
        else{
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_LINEAR_MIPMAP_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR_MIPMAP_LINEAR);
        }

        if(!dupli){
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);}
        else {
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        }

        if(mipmaps){
            GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
        }

    }

    public int getTexture_id(){
        return texture_id[0];
    }



    public void Use(int TexID){
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + TexID);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture_id[0]);
    }
    public void Delete(){
        GLES20.glDeleteTextures(1, texture_id, 0);
    }

    public Bitmap Load_Bitmap(String Name){
        final Bitmap b;
        int res_id = G.getContext().getResources().getIdentifier(PACKAGE_NAME+":raw/"+Name, null, null);
        InputStream is = G.getContext().getResources().openRawResource(res_id);

        try {b = BitmapFactory.decodeStream(is);} finally {try {is.close();
        } catch(IOException e) {
            Log.e("OpenGL","Load texture "+Name+" FAILED");}
        }
        return b;
    }
}

