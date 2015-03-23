package com.labyrinth.myapplication;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.IntBuffer;
import java.util.regex.Pattern;


public class Shader {
    public int prog_id;
    private String PACKAGE_NAME;


    public Shader(String ShaderName){
        PACKAGE_NAME = G.getContext().getApplicationContext().getPackageName();
        String s_shader = LoadRes(ShaderName);
        String[] split_vs_fr = s_shader.split(Pattern.quote("[FRAGMENT]"));
        String o_vs=split_vs_fr[0];
        String o_fr=split_vs_fr[1];
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, o_vs);
        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, o_fr);

        prog_id = GLES20.glCreateProgram();
        GLES20.glAttachShader(prog_id, vertexShader);
        GLES20.glAttachShader(prog_id, pixelShader);
        LinkProgram();
        GLES20.glReleaseShaderCompiler();
    }

    public void LinkProgram(){
        int[] un = new int[1];
        un[0] = 1;
        GLES20.glLinkProgram(prog_id);
        GLES20.glGetProgramiv(prog_id, GLES20.GL_LINK_STATUS,un,0);
        if(un[0] == 0)
            System.out.println("Could not link program");
    }


    private int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
                if (compiled[0] == 0) {
                    Log.e("Error", "Could not compile shader name:" + source + " =>" + shaderType + ":");
                    Log.e("Error", GLES20.glGetShaderInfoLog(shader));
                    GLES20.glDeleteShader(shader);
                    shader = 0;
            }

        }
        return shader;
    }
    private String LoadRes(String Name){
        String ST="";

        Resources res = G.getContext().getResources();
        int res_id = res.getIdentifier(PACKAGE_NAME + ":raw/" + Name, null, null);
        BufferedReader reader = new BufferedReader(new InputStreamReader(res.openRawResource(res_id)));
        String sline ="";
        while(sline!=null){
            try {
                sline = reader.readLine();
                if(sline!=null)ST+=sline+"\n";
            } catch (IOException e) {}
        }
        return ST;
    }
}
