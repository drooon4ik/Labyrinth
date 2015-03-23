package com.labyrinth.myapplication;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Navi
 * Date: 25.01.13
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
public class TextureLoader {

    private ArrayList<lTexture> ArrTexture = new ArrayList<lTexture>();
    public TextureLoader(){

    }

    public Texture Load(String name){
        return Load(name,false,false, false);
    }
    public Texture Load(String name, Boolean filter){
        return Load(name,filter,false, false);
    }
    public Texture Load(String name, Boolean filter, Boolean dupli){
       return Load(name,filter,dupli, false);
    }

    public Texture Load(String name,Boolean filter, Boolean dupli, Boolean mipmaps){
        for(int i=0;i<ArrTexture.size();i++){
            if(ArrTexture.get(i).name.equals(name))return ArrTexture.get(i).tex;
        }
        Texture shad = new Texture(name,filter,dupli,mipmaps);
        lTexture T = new lTexture(name,shad);
        ArrTexture.add(T);
        return shad;
    }
    private class lTexture{
        String name;
        Texture tex;
        public lTexture(String name,Texture tex){
            this.name = name;
            this.tex = tex;
        }
    }
}
