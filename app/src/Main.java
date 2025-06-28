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
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

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

      window = glfwCreateWindow(300, 300, "ueascape", NULL, NULL);

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
      try{
         sVert = Graphics.create_vert_shader(
               Files.readString(Paths.get("src/vertexshader")
                  ));
         sFrag = Graphics.create_frag_shader(
               Files.readString(Paths.get("src/fragshader")
                  ));
      }catch(Exception e){
         System.err.println("FAILED TO CREATE SHADERS");
         System.err.println(e);
      }

      Graphics.bind_shader(p, sVert);
      Graphics.bind_shader(p, sFrag);
      Graphics.link_program(p);
      Graphics.use_program(p);
      
      int vao = Graphics.create_vao();
      int t = Graphics.create_vert_buffer();

      glClearColor(0.1f, 0.1f, 0.1f, 0.0f);

      Node n = new Node();

      while(!glfwWindowShouldClose(window)){
         glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

         Graphics.draw_arr();
         glfwSwapBuffers(window);


         glfwPollEvents();
      }

   }

   public void run_game(boolean server){
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
      boolean isServer = (argv[0].equals("-s"));
      new Main().run_game(isServer);
   }
}
