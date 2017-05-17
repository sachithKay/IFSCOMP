package org.kay.ini;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.ini4j.Ini;
import org.ini4j.Wini;

/**
 *
 * @author sacklk
 */
public class ConnectionList {

   String path;
   String connections;

   ArrayList<Connection> list = new ArrayList<Connection>();

   public ConnectionList(String path) 
   {
      this.path = path;
   }

   public ArrayList<Connection> getAllConnections() {

      try {

         Wini ini = new Wini(new File(path));
         Ini.Section section = ini.get("Connections");

         for (String key : section.keySet()) {
            Connection connection = new Connection();
            connection.setKey(key);
            connection.setValue(ini.get("Connections", key, String.class));
            list.add(connection);
         }

      } catch (Exception e) {

         System.out.println(e.getMessage());

      }

      return list;
   }

   public ArrayList<Connection> getSubConnections(String subComponentPath) {

      ArrayList<Connection> subList = new ArrayList<Connection>();

      try {

         Wini ini = new Wini(new File(subComponentPath));
         Ini.Section section = ini.get("Connections");

         for (String key : section.keySet()) {
            Connection connection = new Connection();
            connection.setKey(key);
            connection.setValue(ini.get("Connections", key, String.class));
            subList.add(connection);

         }

      } catch (Exception e) {

         System.out.println(e.getMessage());
      }

      return subList;
   }

   public DefaultMutableTreeNode addnNewLevel(DefaultMutableTreeNode root) {

      /* This method is initiated by 'Add more levels Button'*/
      int level = root.getDepth();
      Enumeration en = root.breadthFirstEnumeration();
      TreeDiagram getPath = new TreeDiagram(path);
      String componentPath;
      JTree tree = new JTree(root);
      DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

      while (en.hasMoreElements()) {

         DefaultMutableTreeNode node = (DefaultMutableTreeNode) en.nextElement();

         if (node.getLevel() == level) {

            Connection con = (Connection) node.getUserObject();
            componentPath = getPath.getChildComponentPath(con.getKey()).toString();

            if (new File(componentPath).exists()) {

               ArrayList<Connection> cons = new ArrayList<Connection>();
               cons = getSubConnections(componentPath);

               for (Connection c : cons) {
                  node.add(new DefaultMutableTreeNode(c));
               }

            }

            model.reload(node);

         }

      }

      return (DefaultMutableTreeNode) model.getRoot();

   }

   public DefaultMutableTreeNode staticOnly(DefaultMutableTreeNode root) {

     
      JTree tree = new JTree(root);
      DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
      final Enumeration en = ((DefaultMutableTreeNode)model.getRoot()).breadthFirstEnumeration();

      while (en.hasMoreElements()) {

         DefaultMutableTreeNode node = (DefaultMutableTreeNode) en.nextElement();

         if (node.isRoot()) {

            continue;

         } else {
            Connection con = (Connection) node.getUserObject();
            if (con.getValue().trim().equals("DYNAMIC")) {
               model.removeNodeFromParent(node);
               staticOnly((DefaultMutableTreeNode)model.getRoot());

            }

         }
      }

      return (DefaultMutableTreeNode) model.getRoot();

   }

   public DefaultMutableTreeNode dynamicOnly(DefaultMutableTreeNode root) 
   {
      JTree tree = new JTree(root);
      DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
      final Enumeration en = ((DefaultMutableTreeNode)model.getRoot()).breadthFirstEnumeration();
      
      while (en.hasMoreElements()) 
      {

         DefaultMutableTreeNode node = (DefaultMutableTreeNode) en.nextElement();

         if (node.isRoot()) {

            continue;

         } else {
            Connection con = (Connection) node.getUserObject();
            if (con.getValue().trim().equals("STATIC")) {
               model.removeNodeFromParent(node);
               dynamicOnly((DefaultMutableTreeNode) model.getRoot());

            }

         }

      }

      return (DefaultMutableTreeNode) model.getRoot();

   }
   
public boolean existsInWorkSpace(String component)
{

File file = new File(path);
String directory = file.getParentFile().getParent()+"\\"+component;

if(new File(directory).exists())
   return true;

else 
   return false;
           


}
}
