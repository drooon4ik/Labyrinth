package com.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.annotation.SuppressLint;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.labyrinth.myapplication.AABB;
import com.labyrinth.myapplication.Camera;
import com.labyrinth.myapplication.G;
import com.labyrinth.myapplication.R;
import com.labyrinth.myapplication.Shader;
import com.labyrinth.myapplication.Shared;
import com.labyrinth.myapplication.Texture;
import com.labyrinth.myapplication.Vec2;

import utilities.ModelData;
import utilities.Utilities;

@SuppressLint("NewApi")
public class Player {
    private int count;
    private Texture tex;
    public Shader shad;
    public final float[] ModelMatrix = new float[16];
    private final float[] MVPMatrix = new float[16];

    private int vertexPosition_modelspace, vertexNormal_modelspace;
    private int MVP, M;
    private int myTextureSampler, MV;

    private int light_position;
    static float[] lightPos = new float[3];

    int vertex_id, normal_id, uv_id;

    private Vec2 dir;
    private float angle;
    private float velocity;
    private float S;
    
	private String PACKAGE_NAME;
    
	public AABB aabb;

    public Player(String name, Shader shad, Texture tex, Vec2 pos){
        PACKAGE_NAME = G.getContext().getApplicationContext().getPackageName();
        try {
            Load_Mesh(name);
        } catch (IOException e) {
            Log.e("MeshLoader", "Name=" + name + " Error: " + e.getMessage());
        }

        this.shad = shad;
        this.tex = tex;

        vertexPosition_modelspace = GLES20.glGetAttribLocation(shad.prog_id, "vertexPosition_modelspace");
        vertexNormal_modelspace = GLES20.glGetAttribLocation(shad.prog_id, "vertexNormal_modelspace");
        MVP = GLES20.glGetUniformLocation(shad.prog_id, "MVP");
        M = GLES20.glGetUniformLocation(shad.prog_id, "M");
        light_position = GLES20.glGetUniformLocation(shad.prog_id, "light_position");

//      myTextureSampler = GLES20.glGetUniformLocation(shad.prog_id, "myTextureSampler");


        angle = 45;
        velocity = 11;

        aabb = new AABB(pos, 1, 1);

        Matrix.setIdentityM(ModelMatrix, 0);
        translate(pos);
        lightPos[0] = ModelMatrix[12];
        lightPos[1] = ModelMatrix[13] + 2;
        lightPos[2] = ModelMatrix[14];
    }

    @SuppressLint("NewApi")
	public void Draw(){
        GLES20.glUseProgram(shad.prog_id);
        tex.Use(0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertex_id);
        GLES20.glEnableVertexAttribArray(vertexPosition_modelspace);
        GLES20.glVertexAttribPointer(vertexPosition_modelspace, 3, GLES20.GL_FLOAT, false, 0, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, normal_id);
        GLES20.glEnableVertexAttribArray(vertexNormal_modelspace);
        GLES20.glVertexAttribPointer(vertexNormal_modelspace, 3, GLES20.GL_FLOAT, false, 0, 0);

        Matrix.multiplyMM(MVPMatrix, 0, Camera.getProjViewMatrix(), 0, ModelMatrix, 0);
        GLES20.glUniformMatrix4fv(MVP, 1, false,MVPMatrix, 0);
        GLES20.glUniformMatrix4fv(M, 1, false,ModelMatrix, 0);

        GLES20.glUniform3fv(light_position,1,lightPos,0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,count);

        GLES20.glDisableVertexAttribArray(vertexPosition_modelspace);
        GLES20.glDisableVertexAttribArray(vertexNormal_modelspace);
    }


    void Load_Mesh(String Name) throws IOException {

        ModelData md = Utilities.loadModelExt(Shared.res.openRawResource(R.raw.sphere));

        count = md.v.length;
        FloatBuffer mTriangleVertices; // ��������� ������
        ByteBuffer bb =  ByteBuffer.allocateDirect(md.v.length * 4); // �������� "����" ������
        bb.order(ByteOrder.nativeOrder()); // ������� ������
        mTriangleVertices = bb.asFloatBuffer(); 
        mTriangleVertices.put(md.v); // ���������� ������ � "����" ������
        mTriangleVertices.position(0);

        int[] id = new int[1];
        GLES20.glGenBuffers(1,id, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, id[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,mTriangleVertices.capacity() * 4,mTriangleVertices,GLES20.GL_STATIC_DRAW);

        vertex_id = id[0];


        FloatBuffer mTriangleNormals; // ��������� ������
        ByteBuffer bb_n =  ByteBuffer.allocateDirect(md.v.length * 4); // �������� "����" ������
        bb_n.order(ByteOrder.nativeOrder()); // ������� ������
        mTriangleNormals = bb_n.asFloatBuffer();
        mTriangleNormals.put(md.vn); // ���������� ������ � "����" ������
        mTriangleNormals.position(0);

        GLES20.glGenBuffers(1,id, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, id[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,mTriangleNormals.capacity() * 4,mTriangleNormals,GLES20.GL_STATIC_DRAW);

        normal_id = id[0];


        FloatBuffer mTriangleUVs; // ��������� ������
        ByteBuffer bbUV =  ByteBuffer.allocateDirect(md.v.length * 4); // �������� "����" ������
        bbUV.order(ByteOrder.nativeOrder()); // ������� ������
        mTriangleUVs = bbUV.asFloatBuffer();
        mTriangleUVs.put(md.vt); // ���������� ������ � "����" ������
        mTriangleUVs.position(0);

        GLES20.glGenBuffers(1,id, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, id[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,mTriangleUVs.capacity() * 4,mTriangleUVs,GLES20.GL_STATIC_DRAW);

        uv_id = id[0];

    }

    public void translate(Vec2 dir){
    	float x = dir.v[0];
    	float y = dir.v[2];
    	Matrix.translateM(ModelMatrix,0, x, 0, y);
        //if(aabb != null)
        aabb.updateVerts(ModelMatrix);

        lightPos[0] = ModelMatrix[12];
        lightPos[1] = ModelMatrix[13] + 4;
        lightPos[2] = ModelMatrix[14];

    }
    public void scale(float x, float y, float z){
        Matrix.scaleM(ModelMatrix,0,x,y,z);
    }
    public void rotateX(float d){
        Matrix.rotateM(ModelMatrix,0,d,1.0f,0,0);
    }
    public void rotateY(float d){
        Matrix.rotateM(ModelMatrix,0,d,0,1.0f,0);
    }
    public void rotateZ(float d){
        Matrix.rotateM(ModelMatrix,0,d,0,0,1.0f);
    }
    public void reset(){
        Matrix.setIdentityM(ModelMatrix,0);
    }
    
    private void lifeCicle() {
    	translate(dir);
    }

    public void collide(AABB other_aabb) {

        if(aabb.CollideRect(other_aabb, ModelMatrix)) {
            aabb.updateVerts(ModelMatrix);
        }
    }
}
