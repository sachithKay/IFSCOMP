
package org.kay.ini;

import java.io.File;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author sacklk
 */
public class TreeRoot {
   
   String filePath;
   ConnectionList list;
  // ConnectionList_SINGLETON list;
   
   public TreeRoot(String filePath)
   {
      
   this.filePath = filePath;
   this.list = new ConnectionList(filePath);
  // this.list = ConnectionList_SINGLETON.getInstance();
   }
   
   
      public StringBuilder getChildComponentPath(String component) 
   {

      String[] arr = filePath.split("/");
      StringBuilder builtPath = new StringBuilder();
      builtPath.append(arr[0]);

      for (int i = 1; i < (arr.length); i++) {

         if (i == (arr.length - 2)) {

            builtPath.append("/" + component);

         } else {
            builtPath.append("/" + arr[i]);
         }

      }
      return builtPath;
   }
      
      
      
       public DefaultMutableTreeNode getRootNode()

    {
 
    ArrayList<Connection> list = new ArrayList<Connection>();
    ArrayList<Connection> subList = new ArrayList<Connection>();
    ArrayList<Connection> subSubList = new ArrayList<Connection>();
    String[] array = filePath.split("/");
    
   final DefaultMutableTreeNode root = new DefaultMutableTreeNode(array[(array.length - 2)]);
     
      
       list = this.list.getAllConnections();

       for (Connection connection : list) 
       {
          DefaultMutableTreeNode parent = new DefaultMutableTreeNode(connection);
          String childComponentPath = (getChildComponentPath(connection.getKey())).toString();

          if (new File(childComponentPath).exists())   
          {         
            subList =this.list.getSubConnections(childComponentPath);

             for (Connection subConnection : subList)
             {

               DefaultMutableTreeNode child = new DefaultMutableTreeNode(subConnection);
               String subChildPath = (getChildComponentPath(subConnection.getKey())).toString();

                if (new File(subChildPath).exists()) 
                {

                   subSubList =this.list.getSubConnections(subChildPath);

                   for (Connection subSubConnection : subSubList)
                   {
                      DefaultMutableTreeNode subChild = new DefaultMutableTreeNode(subSubConnection);
                      child.add(subChild);
                   }
                   
                }

                parent.add(child);
             }
          }
        
          root.add(parent);
      }
     
    
    return root;
    
    }

   
   
}
