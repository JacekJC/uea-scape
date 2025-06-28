package ueaScape.math;

import ueaScape.math.Matrixf;
import ueaScape.math.Vector3f;

public class Mat4f extends Matrixf{

   public Mat4f(){
      d = new float[4];
      x=4;
      y=1;
   }

   public Mat4f(Vector3f v){
      d = new float[]{v.x, v.y, v.z, 0};
   }

}
