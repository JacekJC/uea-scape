package ueaScape.math;

import ueaScape.math.Matrixf;
import ueaScape.math.Vector3f;

//import java.Math;

public class Mat4x4f extends Matrixf{
   
   public Mat4x4f(){
      d = new float[4*4];
      x=4;
      y=4;
      d[0] = 1f;
      d[5] = 1f;
      d[10] = 1f;
      d[15] = 1f;
   }

   public Mat4x4f translated(Vector3f v){
      d[12]=d[12]+v.x;
      d[13]=d[13]+v.y;
      d[14]=d[14]+v.z;
      return this;
   }

   public Mat4x4f scaled(Vector3f v){
      d[0]=d[0]*v.x;
      d[5]=d[5]*v.y;
      d[10]=d[10]*v.z;
      return this;
   }

   public Mat4x4f rotated_x(float amount){
      multiply(xRot(amount));
      return this;
   }
   public Mat4x4f rotated_y(float amount){
      multiply(yRot(amount));
      return this;
      //return (Mat4x4f)(yRot(amount).multiply(this));
   }
   public Mat4x4f rotated_z(float amount){
      multiply(zRot(amount));
      return this;
   }

   public static Mat4x4f xRot(float amount){
      Mat4x4f m = new Mat4x4f();
      m.d[5]=(float)Math.cos(amount);
      m.d[6]=(float)Math.sin(amount);
      m.d[9]=(float)-Math.sin(amount);
      m.d[10]=(float)Math.cos(amount);
      return m;
   }
   public static Mat4x4f yRot(float amount){
      Mat4x4f m = new Mat4x4f();
      m.d[0]=(float)Math.cos(amount);
      m.d[2]=(float)-Math.sin(amount);
      m.d[8]=(float)Math.sin(amount);
      m.d[10]=(float)Math.cos(amount);
      return m;
   }
   public static Mat4x4f zRot(float amount){
      Mat4x4f m = new Mat4x4f();
      m.d[0]=(float)Math.cos(amount);
      m.d[1]=(float)Math.sin(amount);
      m.d[4]=(float)-Math.sin(amount);
      m.d[5]=(float)Math.cos(amount);
      return m;
   }

   public void mult(Mat4x4f o){
      //System.out.println();
      Mat4x4f m = new Mat4x4f();
      m.d[0] = m(0,0,o);m.d[1] = m(1,0,o);m.d[2] = m(2,0,o);m.d[3] = m(3,0,o);
      d = m.d;
   }
   private float m(int x, int y, Matrixf b){
      float r = 0;
      for(int rx = 0; rx < this.y; rx++){
         for(int ry = 0; ry < b.x; ry++){
            r += this.d[x+(rx*4)]*b.d[(y*4)+ry];
         }
      }
      return r;
   }

   public static Mat4x4f ortho(
         float left, float right, 
         float bottom, float top, 
         float near, float far){
      Mat4x4f m = new Mat4x4f();
      m.d[0] = 2/(right-left);
      m.d[5] = 2/(top-bottom);
      m.d[10] = -(2/(far-near));

      m.d[12] = -((right+left)/(right-left));
      m.d[13] = -((top+bottom)/(top-bottom));
      m.d[14] = -((far+near)/(far-near));

      return m;
   }

   public String toString(){
      String s = (""+d[0]+" "+d[4]+" "+d[8]+" "+d[12])+"\n"+
      (""+d[1]+" "+d[5]+" "+d[9]+" "+d[13])+"\n"+
      (""+d[2]+" "+d[6]+" "+d[10]+" "+d[14])+"\n"+
      (""+d[3]+" "+d[7]+" "+d[11]+" "+d[15])+"\n";
      return s;
   }

   public static Mat4x4f persp(
         float pleft, float pright, 
         float pbottom, float ptop, 
         float near, float far, float scale){

      Mat4x4f m = new Mat4x4f();
      float left = pleft*scale;
      float right = pright*scale;
      float top = ptop*scale;
      float bottom = pbottom*scale;

      m.d[0] = (2*near)/(right-left);
      m.d[5] = (2*near)/(top-bottom);
      m.d[10] = -((far+near)/(far-near));
      m.d[15] = 0;

      m.d[8] = (right+left)/(right-left);
      m.d[9] = (top+bottom)/(top-bottom);
      m.d[11] = -1;

      m.d[14] = -(2*far*near)/(far-near);
      return m;
   }

   public static Mat4x4f persp(
         float fov, float near, float far){

      Mat4x4f m = new Mat4x4f();
      float s = 1f/(float)(Math.tan((fov/2)*(Math.PI/180)));
      //System.out.println("RIGHT : " + ((2*near)/(right-left)));
      m.d[0] = fov/far;
      m.d[5] = fov/far;
      /*m.d[0] = s;
      m.d[5] = s;

      //m.d[8] = (right+left)/(right-left);
      //m.d[9] = (top+bottom)/(top-bottom);
      m.d[10] = -(far/(far-near));
      //m.d[11] = -1;
      
      m.d[11] = -((far*near)/(far-near));

      //m.d[12] = -((right+left)/(right-left));
      //m.d[13] = -((top+bottom)/(top-bottom));
      m.d[14] = -1;
      m.d[15] = 0;*/
      return m;
   }

}
