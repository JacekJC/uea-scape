package ueaScape.mesh;

import org.lwjgl.system.*;
import ueaScape.math.Vector3;
import java.util.ArrayList;

public class Mesh{

   static final int VERT_COUNT = 3;
   static final int UV_COUNT = 2;
   static final int NORMAL_COUNT = 3;
  
   public int vertCount;
   public int triCount;

   ArrayList<Float> verts = new ArrayList<Float>();
   ArrayList<Float> uv = new ArrayList<Float>();
   ArrayList<Float> normal = new ArrayList<Float>();
   
   public float[] compile(){
      System.out.println("Vertex Count : " + vertCount);


      int arrSize = 
         (VERT_COUNT*vertCount)+ 
         (NORMAL_COUNT*vertCount)+
         (UV_COUNT*vertCount);
      float[] arr = new float[arrSize];
      
      int vcounter = 0; 
      int ucounter = 0; 
      int ncounter = 0;

      for(int i = 0; i < arrSize; i++){
         //System.out.println("compile ind : " + i + " val : " + (i%8));
         if(i%8 < 3){
            //System.out.println("vpos");
            arr[i] = verts.get(vcounter);
            vcounter+=1;
         }else if(i%8 < 6){
            //System.out.println("npos");
            arr[i] = normal.get(ncounter);
            ncounter+=1;
         }else{
            //System.out.println("upos");
            arr[i] = uv.get(ucounter);
            ucounter+=1;
         }
      }
      return arr;
   }

   public Mesh quick_tri(Vector3 a, float uvax, float uvay, 
                         Vector3 b, float uvbx, float uvby,
                         Vector3 c, float uvcx, float uvcy){
      vertCount+=3;
      for(float f : a.to_array()){
         verts.add(f);
      }
      uv.add(uvax);
      uv.add(uvay);
      for(float f : b.to_array()){
         verts.add(f);
      }
      uv.add(uvbx);
      uv.add(uvby);
      for(float f : c.to_array()){
         verts.add(f);
      }
      uv.add(uvcx);
      uv.add(uvcy);
      Vector3 norm = a.cross(b);
      for(int i = 0; i < 3; i++){
         for(float f : norm.to_array()){
            normal.add(f);
         }
      }
      return this;
   }
}
