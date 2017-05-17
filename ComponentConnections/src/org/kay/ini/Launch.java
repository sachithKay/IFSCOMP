
package org.kay.ini;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JOptionPane;
import org.openide.loaders.DataObject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Bugtracking",
        id = "org.kay.ini.SomeAction"
)
@ActionRegistration(
        displayName = "View Connections",
        iconBase = "org/kay/ini/icon.png"
)
@ActionReference(path = "Loaders/folder/any/Actions", position = 0, separatorAfter = 50)
@Messages("CTL_SomeAction= View Connections")
public final class Launch implements ActionListener {

   String filePath;
   
   private final DataObject context;

   public Launch(DataObject context) {
      this.context = context;

   }

   
   @Override
   public void actionPerformed(ActionEvent ev)
   {
    
     org.openide.filesystems.FileObject primaryFile = context.getPrimaryFile();
     this.filePath = primaryFile.getPath()+"/deploy.ini";
     File file= new File(filePath);
    
    if(!file.exists()) 
       JOptionPane.showMessageDialog(null, "Select a component from workspace", "Warning", JOptionPane.WARNING_MESSAGE);
     
    else
       
    {   
     
 TreeDiagram x = new TreeDiagram(filePath);
 x.setVisible(true);

     
     
   }
   
   }
}
