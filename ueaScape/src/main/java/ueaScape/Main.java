package ueaScape;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;


import ueaScape.mesh.Mesh;
import ueaScape.math.*;
import ueaScape.node.*;

public class Main{

   private long window;

   public long init_window(){
      System.out.println("Initialising Window....");

      GLFWErrorCallback.createPrint(System.err).set();

      if(!glfwInit())
         throw new IllegalStateException("Init GLFW failed");

      glfwDefaultWindowHints();
      glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
      glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

      System.out.println("Initialised Window.");

      window = glfwCreateWindow(1600, 900, "ueascape", NULL, NULL);

      if(window == NULL)
         throw new RuntimeException("Failed to Create GLFW Window");

      glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
         if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
            glfwSetWindowShouldClose(window, true);
      });

      glfwMakeContextCurrent(window);
      glfwSwapInterval(1);
      glfwShowWindow(window);

      return window;
   }

   public void deinit_window(long window){ 
      glfwFreeCallbacks(window);
      glfwDestroyWindow(window);


      glfwTerminate();
      glfwSetErrorCallback(null).free();
   }

   public void loop(){
      GL.createCapabilities();

      int sVert=-1;
      int sFrag=-1;

      int p = Graphics.create_program();
      System.out.println("-----------WORKING PATH---------");
      System.out.println(Paths.get("/home/jacek/Desktop/temp/vertexshader").toString());
      try{
         sVert = Graphics.create_vert_shader(
               Files.readString(Paths.get("/home/jacek/Desktop/temp/vertexshader"))
                  );
         sFrag = Graphics.create_frag_shader(
               Files.readString(Paths.get("/home/jacek/Desktop/temp/fragshader"))
                  );
      }catch(Exception e){
         System.err.println("FAILED TO CREATE SHADERS");
         System.err.println(e);
      }

      Graphics.bind_shader(p, sVert);
      Graphics.bind_shader(p, sFrag);
      Graphics.link_program(p);
      Graphics.use_program(p);

      //Mat4x4f transform = new Mat4x4f(); 
      //int mpos = glGetUniformLocation(p, "obj");
      //glUniformMatrix4fv(mpos, false, transform.d);
      
      //int vao = Graphics.create_vao();
      //int t = Graphics.create_vert_buffer();

      Mesh m = new Mesh().quick_tri(
               new Vector3(-0.5f, 0.5f, 0), 0.0f, 1.0f,
               new Vector3(-0.5f, -0.5f, 0), 0.0f, 0.0f,
               new Vector3(0.5f, -0.5f, 0), 1.0f, 0.0f
               ).quick_tri(
               new Vector3(-0.5f, 0.5f, 0), 0.0f, 1.0f,
               new Vector3(0.5f, 0.5f, 0), 1.0f, 1.0f,
               new Vector3(0.5f, -0.5f, 0), 1.0f, 0.0f
               )
         ; 
      
      int vao = Graphics.vao_from_mesh(m);

      glClearColor(0.1f, 0.1f, 0.1f, 0.0f);

      System.out.println(Mat4x4f.xRot(0)+"\n");
      System.out.println(Mat4x4f.yRot(0)+"\n");
      System.out.println(Mat4x4f.zRot(0));

      Mesh3DNode n = new Mesh3DNode();
      n.mesh = m;
      float scale = 1f;
      n.scale = new Vector3f(scale, scale, scale);
      n.position = n.position.add(new Vector3f(0f*scale, 0f*scale, -1f));
      n.rotation = new Vector3f(0.01f, 0, 0);
      System.out.println(n.calc_transform());
      System.out.println(n.calc_transform());
      n.shader = p;

      while(!glfwWindowShouldClose(window)){
         //glUniformMatrix4fv(mpos, false, transform.d);
         glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

         Graphics.draw_mesh(n);
         glfwSwapBuffers(window);


         glfwPollEvents();
         n.rotation = n.rotation.add(new Vector3f(0.00f, 0.000f, 0.00f));
         //n.position = n.position.add(new Vector3f(0, 0, -0.1f));
         //Graphics.currentCamera.transform.translated(new Vector3f(0, 0, -1f));
      }

   }

   public void run(boolean server){
      long window = -1; 

      if(!server)
         window = init_window(); 

      loop();

      if(!server)
         deinit_window(window);
   }

   public static void main(String argv[]){
      for(String s : argv){
         System.out.println(s);
      }
      boolean isServer = false;
      if(argv.length > 0){
         isServer = (argv[0].equals("-s"));
      }
      new Main().run(isServer);
   }
}
