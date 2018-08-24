package conn;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.StringReader;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker; // deprecated
import com.itextpdf.text.pdf.PdfWriter;
import static conn.VulnerabilityTester.vulnerabilityList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PDFGenerator {
  public static void main(String[] args) {
     
  }
  
   public  void generateReport(String currentDir, String reportDirectory, HashMap<String, HashSet<String>> map){
    String content = "";
    System.out.println("Inside Generate PDF");
       
     try {
      Document document = new Document(PageSize.LETTER);
      String reportDir=reportDirectory.replace("\\","\\\\");
      PdfWriter.getInstance(document, new FileOutputStream(reportDir.toString()));
      document.open();
      document.addAuthor("Adam's eye");
      document.addCreator("Adam's Eye");
      document.addSubject("Thanks for your support");
      document.addCreationDate();
      document.addTitle("Please read this");
      
      HTMLWorker htmlWorker = new HTMLWorker(document);
      String str = "";
              
      for (String vulnerability : vulnerabilityList) {
            
            System.out.println("Inside scanBtn"+vulnerability);
            StringBuilder contentBuilder = new StringBuilder();
        try {
            
            //InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("./src/html/" + vulnerability+".html"));
            //BufferedReader in = new BufferedReader(isr);
            //BufferedReader in = new BufferedReader(new FileReader(currentDir+"\\src\\html\\"+vulnerability+".html"));
            URL u = new URL("http://adamzeye.com/html/"+vulnerability+".html");
            InputStreamReader insr = new InputStreamReader(u.openStream());
            BufferedReader in = new BufferedReader(insr);
            String strFile;
            while ((strFile = in.readLine()) != null) {
                contentBuilder.append(strFile);
            }
            in.close();
            
            contentBuilder.append(addAffectedUrls(vulnerability, map.get(vulnerability)));
            
            content = content +contentBuilder.toString()+"\n\n\n\n****************************************************************************************\n";    
        } 
        catch (Exception ex) {
                   ex.printStackTrace();
               }

        System.out.println("Content:::"+content);
    }
      htmlWorker.parse(new StringReader(content));
      document.close();
      System.out.println("Done");
      }
    catch (Exception e) {
      e.printStackTrace();
    }
  
   
   
   }
  
  public static void generatePDFOld(String param){
    Document document = new Document();

    try {
        PdfWriter.getInstance(document, new FileOutputStream("D:\\htmlfiles\\Report.pdf"));

         document.open();
         int urlNo=1;
         System.out.println("Outside for loop");
       
            Paragraph paragraph = new Paragraph();
         //   paragraph.add(new Phrase(url));
            Chapter chapter = new Chapter(paragraph, urlNo++);
            System.out.println("Inside For Loop");
            chapter.addSection("Test:::");
            /*
            for(String vulnerability : allVulnerabilityList.get(url)){
                Section section1 = chapter.addSection(vulnerability, 2);
            }
                    */
            
            document.add(chapter);
        
        document.close();
       // return true;

    } catch (DocumentException e) {
        e.printStackTrace();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    //return false;
}
  
  public static boolean generatePDF(HashMap<String, ArrayList<String> > allVulnerabilityList){
    Document document = new Document();

    try {
        PdfWriter.getInstance(document, new FileOutputStream("D:\\htmlfiles\\Report.pdf"));

         document.open();
         int urlNo=1;
         System.out.println("Outside for loop");
        for(String url : allVulnerabilityList.keySet()){
            Paragraph paragraph = new Paragraph();
            paragraph.add(new Phrase(url));
            Chapter chapter = new Chapter(paragraph, urlNo++);
            System.out.println("Inside For Loop");
            chapter.addSection("Test:::");
            /*
            for(String vulnerability : allVulnerabilityList.get(url)){
                Section section1 = chapter.addSection(vulnerability, 2);
            }
                    */
            
            document.add(chapter);
        }
        document.close();
        return true;

    } catch (DocumentException e) {
        e.printStackTrace();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    return false;
}

    private static String addAffectedUrls(String vul, HashSet<String> list) {
        String temp = "<html><head><title></title></head><body><br/><p><span style=\"font-family: arial,helvetica,sans-serif;\"><span style=\"color: rgb(0, 0, 255);\"><strong><span style=\"font-size: 14px;\">";
        temp = temp + "Affected URLs" + "</span>:&nbsp;</strong></span></span></p>";
        int i=1;
        for(String s : list){
            temp = temp + "<div>&nbsp;&nbsp;&nbsp;" + i++ + ". "+ s + "</div>";
        }
        
        temp = temp + "<br/></body></html>";
         
        return temp;
    }
}