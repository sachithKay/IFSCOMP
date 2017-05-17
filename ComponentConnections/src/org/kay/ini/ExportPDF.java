/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kay.ini;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author sacklk
 */
public class ExportPDF {

   String saveTo;

   public ExportPDF(String saveTo) 
   {
      this.saveTo = saveTo;
     
   }

   public void createPDF(ArrayList<Connection> exportList) {


      PDFont font = PDType1Font.HELVETICA_BOLD;

      try {

         PDDocument document = new PDDocument();

         PDPage page = new PDPage();

         document.addPage(page);

         PDPageContentStream content = new PDPageContentStream(document, page, true, true);
         content.beginText();
         content.setFont(font, 12);

         content.moveTextPositionByAmount(100, 700);
         for (Connection con:exportList)
         {
            content.drawString(con.getKey()+" = "+ con.getValue());
            content.moveTextPositionByAmount(0, -15);
         }

         content.endText();
         content.close();

         document.save(saveTo);
         JOptionPane.showMessageDialog(null, "success");

      } catch (Exception e) {
         JOptionPane.showMessageDialog(null, e.getMessage());

      }

   }

}
