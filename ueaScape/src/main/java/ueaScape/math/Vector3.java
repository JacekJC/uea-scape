package ueaScape.math;

public class Vector3{
   private float x, y, z; 

   public Vector3(float x, float y, float z){
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vector3 add(Vector3 rhs){
      return new Vector3(x+rhs.x, y+rhs.y, z+rhs.z);
   }
   public Vector3 sub(Vector3 rhs){
      return new Vector3(x-rhs.x, y+rhs.y, z+rhs.z);
   }
   public Vector3 div(double rhs){
      return new Vector3((float)(x/rhs), (float)(y/rhs), (float)(z/rhs));
   }
   public Vector3 mul(double rhs){
      return new Vector3((float)(x*rhs), (float)(y*rhs), (float)(z*rhs));
   }

   public float[] to_array(){
      float arr[] = {this.x, this.y, this.z}; 
      return arr;
   }

   public Vector3 cross(Vector3 rhs){
      return new Vector3(
            this.y*rhs.z-this.y*rhs.x,
            this.z*rhs.x-this.z*rhs.y,
            this.x*rhs.y-this.x-rhs.z);
   }

   public double length(){
      return Math.sqrt(
            Math.pow(this.x,2)+
            Math.pow(this.y,2)+
            Math.pow(this.z,2)
            );
   }

   public Vector3 dir(){
      return new Vector3(x,y,z).div(length());
   }

   public float dot(Vector3 rhs){
      return (x*rhs.x)+(y*rhs.y)+(z*rhs.z);
   }
}
