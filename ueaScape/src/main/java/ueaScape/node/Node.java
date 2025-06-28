package ueaScape.node;

import java.util.ArrayList;

public class Node{
   public ArrayList<Node> children = new ArrayList<Node>(); 
   private Node parent;

   public void set_parent(Node parent){
      this.parent.remove_child(this);
      parent.add_child(this);
   }

   public void remove_child(Node child){
      children.remove(child);
   }

   void remove_parent(){
      this.parent.remove_child(this);
      this.parent = null;
   }

   public void add_child(Node child){
      child.remove_parent();
      this.children.add(child);
   } 

}
