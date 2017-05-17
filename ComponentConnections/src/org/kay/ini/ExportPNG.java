/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kay.ini;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author sacklk
 */
public class ExportPNG {
   
   String saveTo;
   
   public ExportPNG(String saveTo)
   {
      
   this.saveTo = saveTo;
   
   }

   
   public void createPNG(DefaultMutableTreeNode activeRoot,ConnectionList list)
   {
   
      JTree tree = new JTree(activeRoot);
      tree.setSize(350, 800);
      
    tree.setCellRenderer(new TreeCellRendering(list));
      
        for (int i = 0; i < tree.getRowCount(); i++) 
            {
               tree.expandRow(i);
            }

      
        BufferedImage image = new BufferedImage(tree.getWidth(), tree.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        tree.paint(g);
        
        g.dispose();
        
         File imageOut = new File(saveTo);
        try {
            ImageIO.write(image, "png", imageOut);
            } 
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
      
   
   }
   
   
}
