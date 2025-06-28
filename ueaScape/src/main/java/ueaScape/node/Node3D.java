package ueaScape.node;

import ueaScape.node.Node;
import ueaScape.math.*;

public class Node3D extends Node{
   public Mat4x4f transform = new Mat4x4f();
   public Vector3f position = new Vector3f();
   public Vector3f rotation = new Vector3f();
   public Vector3f scale = new Vector3f(1, 1, 1);

   public Mat4x4f calc_transform(){
      return new Mat4x4f()
         .scaled(scale)
         .rotated_x(rotation.x).rotated_y(rotation.y).rotated_z(rotation.z)
         .translated(position);
   }
}
