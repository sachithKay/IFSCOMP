
package org.kay.ini;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import org.openide.util.Exceptions;


public class TreeDiagram extends JFrame  {
   
    String filePath;
   
    JScrollPane last;
    JTree tree;
    ConnectionList list;
   // ConnectionList_SINGLETON list;
    DefaultMutableTreeNode Activeroot;
    
 

    public TreeDiagram(String filePath) 
            
    {

        this.filePath = filePath;
        final DefaultMutableTreeNode root;
        
       // this.list = ConnectionList_SINGLETON.getInstance();
      this.list = new ConnectionList(filePath);
        createTreeModel();
        setSize(350, 500);
        setResizable(false);
        setTitle("Connection Graph");
      

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

   
    public void createTreeModel() 
    
    {
    
       
      Activeroot = new TreeRoot(filePath).getRootNode();
      
      tree = new JTree(Activeroot);
      tree.setCellRenderer(new TreeCellRendering(list));
      last = new JScrollPane(tree);
      add(last, BorderLayout.CENTER);

      JButton addButton = new JButton("Load more");
      JButton staticOnlyButton = new JButton("Static");
      JButton dynamicOnlyButton = new JButton("Dynamic");
      JButton allTypesButton = new JButton("All");
      JButton allButton = new JButton();
      JMenuBar exportMenu = new JMenuBar();
      JMenu menu = new JMenu("Export");
      
      
      JMenuItem item1 = new JMenuItem(new AbstractAction("Export TXT") {
         
         @Override
         public void actionPerformed(ActionEvent e)
         {
   JFileChooser chooser = new JFileChooser();
   chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
   chooser.setFileFilter(new FileNameExtensionFilter("txt", "txt"));
   int result = chooser.showSaveDialog(null);

   if (result == chooser.APPROVE_OPTION) 
   { 
      String saveTo = chooser.getSelectedFile().toString();
   
   if(!saveTo.endsWith(".txt"))
      saveTo += ".txt";
   
   ExportTxt txt = new ExportTxt(saveTo);
   Enumeration en =  Activeroot.breadthFirstEnumeration();
   ArrayList<Connection> exportList = new ArrayList<Connection>();
   
   
   while(en.hasMoreElements())
   {
   //get all elements in the root node
 DefaultMutableTreeNode node = (DefaultMutableTreeNode) en.nextElement();
 
 if(node.isRoot())
    continue;
 
 else
 {

   Connection con = (Connection) node.getUserObject();
   exportList.add(con);
 }
   
   }
   
   txt.createText(exportList);
   
   
   
   } 
         }
      });
      
      
      
      
      JMenuItem item2 = new JMenuItem(new AbstractAction("Export PDF") {
         
         @Override
         public void actionPerformed(ActionEvent e) 
         {
   JFileChooser chooser = new JFileChooser();
   chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
   chooser.setFileFilter(new FileNameExtensionFilter("pdf", "pdf"));
   
   int result = chooser.showSaveDialog(null);
   
   
      if (result == chooser.APPROVE_OPTION) 
   { 
   
   String saveTo = chooser.getSelectedFile().toString();
   
   if(!saveTo.endsWith(".pdf"))
      saveTo += ".pdf";
   
   ExportPDF pdf = new ExportPDF(saveTo);
   Enumeration en =  Activeroot.breadthFirstEnumeration();
   ArrayList<Connection> exportList = new ArrayList<Connection>();
   
   
   while(en.hasMoreElements())
   {
   //get all elements in the root node
 DefaultMutableTreeNode node = (DefaultMutableTreeNode) en.nextElement();
 
 if(node.isRoot())
    continue;
 
 else
 {
   Connection con = (Connection) node.getUserObject();
   exportList.add(con);
 }
   
   }
   
   
   pdf.createPDF(exportList);

   }
   }        
      });
      
      
      
  JMenuItem item3 = new JMenuItem(new AbstractAction("Export PNG") {
     
     @Override
     public void actionPerformed(ActionEvent e) {
        
   JFileChooser chooser = new JFileChooser();
   chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
   chooser.setFileFilter(new FileNameExtensionFilter("png", "png"));
   int result = chooser.showSaveDialog(null);
   
       if (result == chooser.APPROVE_OPTION) 
   { 
   String saveTo = chooser.getSelectedFile().toString();
   
   if(!saveTo.endsWith(".png"))
      saveTo += ".png";
   
   ExportPNG PNG = new ExportPNG(saveTo);
   PNG.createPNG(Activeroot,list);
   
   }
        
        
        
     }
  });
  
  
  JMenuItem item4 = new JMenuItem(new AbstractAction("Export CSV") {
     
     @Override
     public void actionPerformed(ActionEvent e) {
       
   JFileChooser chooser = new JFileChooser();
   chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
   chooser.setFileFilter(new FileNameExtensionFilter("csv", "csv"));
   int result = chooser.showSaveDialog(null);
   
     if (result == chooser.APPROVE_OPTION) 
   { 
   String saveTo = chooser.getSelectedFile().toString();
   
   if(!saveTo.endsWith(".csv"))
      saveTo += ".csv";
   
  ExportCSV csv = new ExportCSV(saveTo);
      try {
         csv.createCSV(Activeroot);
      } catch (IOException ex) {
         Exceptions.printStackTrace(ex);
      }
   
   }
   
   
        
        
        
        
        
     }
  });

      menu.add(item1);
      menu.add(item2);
      menu.add(item3);
      menu.add(item4);
      exportMenu.add(menu);

      addButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e)
         {
            remove(last);
           list.addnNewLevel(Activeroot);
     
            JTree newTree = new JTree(Activeroot);

            for (int i = 0; i < newTree.getRowCount(); i++) 
            {
               newTree.expandRow(i);
            }

            newTree.setCellRenderer(new TreeCellRendering(list));
            last = new JScrollPane(newTree);
            add(last, BorderLayout.CENTER);
            validate();
            repaint();
         }
      });
      
      staticOnlyButton.addActionListener(new ActionListener() {

         
         
         @Override
         public void actionPerformed(ActionEvent e)
         {
          //Removes dynamic connections
            remove(last);
            Activeroot = new TreeRoot(filePath).getRootNode();
            list.staticOnly(Activeroot);
            JTree staticOnlyTree = new JTree(Activeroot);
            staticOnlyTree.setCellRenderer(new TreeCellRendering(list));
            last = new JScrollPane(staticOnlyTree);
            add(last,BorderLayout.CENTER);
            validate();
            repaint();

         }
  
      });
      
      
      
      dynamicOnlyButton.addActionListener(new ActionListener()
              
      {

         @Override
         public void actionPerformed(ActionEvent e) 
         {
            //removes static connections     
            remove(last);
            Activeroot = new TreeRoot(filePath).getRootNode();
            list.dynamicOnly(Activeroot);
            JTree dynamicOnlyTree = new JTree(Activeroot);
            dynamicOnlyTree.setCellRenderer(new TreeCellRendering(list));
            last = new JScrollPane(dynamicOnlyTree);
            add(last,BorderLayout.CENTER);
            validate();
            repaint();

         }
         
      });
      
      
      allTypesButton.addActionListener(new AbstractAction() {

         @Override
         public void actionPerformed(ActionEvent e) {
         
            remove(last);
            Activeroot = new TreeRoot(filePath).getRootNode();
            JTree dynamicOnlyTree = new JTree(Activeroot);
            dynamicOnlyTree.setCellRenderer(new TreeCellRendering(list));
            last = new JScrollPane(dynamicOnlyTree);
            add(last,BorderLayout.CENTER);
            validate();
            repaint();
            
           
         }
      });
      
      

       JPanel buttonPanel = new JPanel();
       
       
       buttonPanel.add(addButton);
       buttonPanel.add(staticOnlyButton);
       buttonPanel.add(dynamicOnlyButton);
       buttonPanel.add(allTypesButton);
       
       add(buttonPanel,BorderLayout.SOUTH);
        
      JPanel menuPanel = new JPanel();
      menuPanel.add(exportMenu,BorderLayout.EAST);
      add(menuPanel,BorderLayout.NORTH);

   }
    
    
    private DefaultMutableTreeNode cloneRoot(DefaultMutableTreeNode root)
    {
    
   Enumeration en =root.breadthFirstEnumeration();
   DefaultMutableTreeNode clonedRoot = new DefaultMutableTreeNode();
   
   while(en.hasMoreElements())
   {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) en.nextElement();
            clonedRoot.add(node);

   }
    
    File file = new File(filePath);
   
   return clonedRoot;
   
    }
    
    
    

}
