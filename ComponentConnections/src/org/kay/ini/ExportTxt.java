/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kay.ini;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.openide.util.Exceptions;

/**
 *
 * @author sacklk
 */
public class ExportTxt {
   
   String saveTo;
   
   public ExportTxt(String saveTo)
   {
      
   this.saveTo = saveTo;
   
   }
   
   
   public void createText(ArrayList<Connection> exportList)
   {
   
    try
      {
         
    PrintWriter writer = new PrintWriter(saveTo, "UTF-8");
    
    for(Connection con:exportList)
    {
    
    writer.println(con.getKey()+" = "+ con.getValue());
   
    }
   
    writer.close();
      
      } 
    
    
      catch (IOException ex)
      {
         Exceptions.printStackTrace(ex);
      }
   
   
   
   }
   
   
}
