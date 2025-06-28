package ueaScape.math;

public class Matrixf{

   public float d[];
   int x=0;
   int y=0;

   public Matrixf(){}

   public Matrixf(int x, int y){
      this.x = x;
      this.y = y;
      this.d = new float[x*y];
   }

   public Matrixf multiply(Matrixf o){
      Matrixf m = new Matrixf(this.x, o.y);
      for(int x = 0; x < this.x; x++){
         for(int y = 0; y < o.y; y++){
            int i = x+(y*this.x);
            m.d[i] = m(x, y, o);
         }
      }
      d = m.d;
      return this;
   }

   private float m(int x, int y, Matrixf b){
      float r = 0;
      //for(int rx = 0; rx < this.y; rx++){
      for(int ry = 0; ry < b.x; ry++){
         //System.out.println(x+(rx*this.y) + " : " + (y*b.x)+ry);
         //r += this.d[x+(rx*this.y)]*b.d[(y*b.x)+ry];
         r += this.d[x+(ry*this.y)]*b.d[(y*b.x)+ry];
      }
      //}
      return r;
   }

   /*private float mult_single(int gx, int gy, Matrixf a, Matrixf b){
      float res = 0;
      for(int x = (0%a.y)*a.x; x < a.x; x++){
         for(int y = (0%b.x)*b.y; y < b.y; y++){
            res+=a.d[x]*b.d[y];
         }
      }
      return res;
   }*/
}
