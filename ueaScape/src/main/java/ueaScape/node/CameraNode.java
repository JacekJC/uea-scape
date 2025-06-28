package ueaScape.node;

import ueaScape.math.*;
import ueaScape.node.Node3D;

public class CameraNode extends Node3D{
   public Mat4x4f cameraMatrix;

   public CameraNode set_camera_matrix(Mat4x4f mat){
      cameraMatrix = mat;
      return this;
   }
}
