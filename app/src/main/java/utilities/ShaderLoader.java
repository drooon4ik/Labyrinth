package utilities;

import com.labyrinth.myapplication.Shader;

import java.util.ArrayList;

public class ShaderLoader {

    private ArrayList<lShader> ArrShader = new ArrayList<lShader>();
        public ShaderLoader(){
        }
    public Shader Load(String name){
        for(int i=0;i<ArrShader.size();i++){
            if(ArrShader.get(i).name.equals(name))return ArrShader.get(i).shad;
        }
    Shader shad = new Shader(name);
        lShader S = new lShader(name,shad);
        ArrShader.add(S);
    return shad;
    }
    private class lShader{
        String name;
        Shader shad;
        public lShader(String name,Shader shad){
            this.name = name;
            this.shad = shad;
        }
    }
}
