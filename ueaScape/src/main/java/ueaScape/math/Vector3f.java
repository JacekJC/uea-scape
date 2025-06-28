package ueaScape.math;

public class Vector3f{
   public float x, y, z; 

   public Vector3f(float x, float y, float z){
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vector3f(){this.x=0;this.y=0;this.z=0;}

   public Vector3f add(Vector3f rhs){
      return new Vector3f(x+rhs.x, y+rhs.y, z+rhs.z);
   }
   public Vector3f sub(Vector3f rhs){
      return new Vector3f(x-rhs.x, y+rhs.y, z+rhs.z);
   }
   public Vector3f div(double rhs){
      return new Vector3f((float)(x/rhs), (float)(y/rhs), (float)(z/rhs));
   }
   public Vector3f mul(double rhs){
      return new Vector3f((float)(x*rhs), (float)(y*rhs), (float)(z*rhs));
   }

   public float[] to_array(){
      float arr[] = {this.x, this.y, this.z}; 
      return arr;
   }

   public Vector3f cross(Vector3f rhs){
      return new Vector3f(
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

   public Vector3f dir(){
      return new Vector3f(x,y,z).div(length());
   }

   public float dot(Vector3f rhs){
      return (x*rhs.x)+(y*rhs.y)+(z*rhs.z);
   }
}
