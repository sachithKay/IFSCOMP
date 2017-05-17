
package org.kay.ini;

import java.awt.Component;
import java.awt.Font;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.plaf.FontUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author sacklk
 */
public class TreeCellRendering implements TreeCellRenderer{
   
    private JLabel label;
    URL staticImgURL;
    URL dynamicImgURL ;
    URL parentImgURL;
    URL unDynaImgURL;
    URL unStatImgURL;
    
    //ConnectionList_SINGLETON list = ConnectionList_SINGLETON.getInstance();
   ConnectionList list; 
    public  TreeCellRendering(ConnectionList list){
    this.list = list;
   
    label = new JLabel();
    staticImgURL = getClass().getResource("static.png");
    dynamicImgURL = getClass().getResource("dynamic.png");
    parentImgURL = getClass().getResource("parent.png");
    unDynaImgURL = getClass().getResource("step0001.png");
    unStatImgURL = getClass().getResource("step0002.png");

    
    
    
    }
    
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
      
    
    Object object = ((DefaultMutableTreeNode)value).getUserObject();
    
    if(object instanceof Connection)
    {
    
    Connection connection = (Connection)object; 
    
    if((connection.getValue().trim()).equals("STATIC"))
           {
              
   if(list.existsInWorkSpace(connection.getKey()))
   {
            label.setIcon(new ImageIcon(new ImageIcon(staticImgURL).getImage().getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
            label.setText(connection.getKey());
   }
   
   else
   {
   
            label.setIcon(new ImageIcon(new ImageIcon(unStatImgURL).getImage().getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
            label.setText(connection.getKey());
        
            
   }
      
           }

    else
           {
              
           if(list.existsInWorkSpace(connection.getKey())){
              
           label.setIcon(new ImageIcon(new ImageIcon(dynamicImgURL).getImage().getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
           label.setText(connection.getKey());
           }
           
           else
           {
           label.setIcon(new ImageIcon(new ImageIcon(unDynaImgURL).getImage().getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
           label.setText(connection.getKey());
         
           
           
           }
           }
            
    
    }
    
    else
    {
       
         label.setIcon(new ImageIcon(new ImageIcon(parentImgURL).getImage().getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH)));
         label.setText(object.toString().toUpperCase());
   
    }
      
    return  label;
    }

}
