package ueaScape;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.*;

import ueaScape.mesh.Mesh;
import ueaScape.node.*;
import ueaScape.math.*;

import static org.lwjgl.opengl.GL33.*;
//import org.lwjgl.glfw.GLFW.*;

public class Graphics{

   static float camScale = 0.004f;
   public static CameraNode currentCamera = new CameraNode().set_camera_matrix(
         Mat4x4f.persp(-16, 16, -9, 9, 0.1f, 100, 0.016f));
         //Mat4x4f.persp(0, 16*camScale, 0, 9*camScale, 0.01f, 1000));
         //Mat4x4f.persp(0, 1600, 0, 900, 0.01f, 100));

   final static int FLOAT_SIZE = 4;

   public static int create_vao(){
      int vao = glGenVertexArrays();
      glBindVertexArray(vao);
      return vao;
   }

   public static int create_vert_buffer(){
      int vertCount = 3;
      int vertSize = 3; 

      float[] vData = new float[]{
         0f, 0.5f, 0f,
         -1f, -1f, 0f,
         1f, -1f, 0f
      };

      int vbo = glGenBuffers();
      glBindBuffer(GL_ARRAY_BUFFER, vbo);
      glBufferData(GL_ARRAY_BUFFER, vData, GL_STATIC_DRAW);

      glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
      glEnableVertexAttribArray(0);

      return  vbo;
   }

   public static int create_vert_shader(String shaderText){
      System.out.println("compiling : \n" + shaderText);
      int s = glCreateShader(GL_VERTEX_SHADER);
      glShaderSource(s, shaderText);
      glCompileShader(s);
      int[] params = new int[1];
      glGetShaderiv(s, GL_COMPILE_STATUS, params);
      if(params[0] != GL_TRUE){
         System.err.println("Failed to compile vert shader");
      }
      return s;

   }
   
   public static int create_frag_shader(String shaderText){
      System.out.println("compiling : \n" + shaderText);
      int s = glCreateShader(GL_FRAGMENT_SHADER);
      glShaderSource(s, shaderText);
      glCompileShader(s);
      int[] params = new int[1];
      glGetShaderiv(s, GL_COMPILE_STATUS, params);
      if(params[0] != GL_TRUE){
         System.err.println("Failed to compile frag shader");
      }
      return s;
   }

   public static int vao_from_mesh(Mesh m){
      int vao = glGenVertexArrays();
      glBindVertexArray(vao);
      float[] data = m.compile(); 
      int vbo = glGenBuffers();
      glBindBuffer(GL_ARRAY_BUFFER, vbo);
      glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
      glVertexAttribPointer(0, 3, GL_FLOAT, false, 8*FLOAT_SIZE, 0);
      glVertexAttribPointer(2, 3, GL_FLOAT, false, 8*FLOAT_SIZE, 3*FLOAT_SIZE);
      glVertexAttribPointer(1, 2, GL_FLOAT, false, 8*FLOAT_SIZE, 6*FLOAT_SIZE);
      glEnableVertexAttribArray(0);
      glEnableVertexAttribArray(1);
      glEnableVertexAttribArray(2);
      return vao;
   }

   public static int create_program(){
      int p = glCreateProgram();
      glUseProgram(p);
      return p;
   }

   public static void bind_shader(int program, int shader){
      glAttachShader(program, shader);
   }

   public static void link_program(int program){
      glLinkProgram(program);
   }

   public static void use_program(int p){
      glUseProgram(p);
   }

   public static void draw_mesh(Mesh3DNode m){
      int obj = glGetUniformLocation(m.shader, "obj");
      glUniformMatrix4fv(obj, false, m.calc_transform().d);
      int view = glGetUniformLocation(m.shader, "view");
      glUniformMatrix4fv(view, false, currentCamera.calc_transform().d);
      int persp = glGetUniformLocation(m.shader, "persp");
      glUniformMatrix4fv(persp, false, currentCamera.cameraMatrix.d);
      glDrawArrays(GL_TRIANGLES, 0, m.mesh.vertCount);
   }

   public static void draw_arr(){
      glDrawArrays(GL_TRIANGLES, 0, 3);
   }
}
