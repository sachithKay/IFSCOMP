
package org.kay.ini;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author sacklk
 */
public class ExportCSV 
{
 
   String saveTo;
   private final String [] FILE_HEADER = {"connection","type"};
   FileWriter fileWrite;
   
   
   public ExportCSV(String saveTo)
   {
   this.saveTo = saveTo;
 
   }
  
   public void createCSV(DefaultMutableTreeNode ActiveRoot) throws IOException
   {
      
      try
      {
   fileWrite = new FileWriter(saveTo);
   fileWrite.append("component");
   fileWrite.append(",");
   fileWrite.append("type");
   fileWrite.append("\n");



        
   
    Enumeration en = ActiveRoot.breadthFirstEnumeration();
    
    while(en.hasMoreElements())
    {
     DefaultMutableTreeNode node = (DefaultMutableTreeNode) en.nextElement();
 
 if(node.isRoot())
    continue;
 
 else
 {
   Connection con = (Connection) node.getUserObject();
   fileWrite.append(con.getKey());
   fileWrite.append(",");
   fileWrite.append(con.getValue());
   fileWrite.append("\n");

   
 }
    }
   
   
      }
      
      catch(Exception e)
      {
      
      
      
      
      }
      
      finally
      {
      fileWrite.flush();
      fileWrite.close();
      
      }
   
   
   }
   
   
}
