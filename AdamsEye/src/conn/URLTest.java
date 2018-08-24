package conn;



import java.net.*;
import java.io.*;
import java.util.StringTokenizer;

public class URLTest
{
   public static void main(String [] args)
   {
       URLTest.getRelativeURL("http://localhost:8080/insecure/public/Login.jsp");
   }
   
   public static String getRelativeURL(String urlStr){
      
        String context ="";
        String authority ="";
        String protocol ="";
        
      try
        {
         URL url = new URL(urlStr);
         System.out.println("URL is " + url.toString());
         System.out.println("protocol is "
                                    + url.getProtocol());
         protocol = url.getProtocol();
         System.out.println("authority is "
                                    + url.getAuthority());
         authority = url.getAuthority();
         
    
         StringTokenizer token = new StringTokenizer(url.getPath(),"/");
         if(token.hasMoreTokens())
            context = token.nextToken();
         
         System.out.println("context=="+context);
         
       
      }catch(IOException e)
      {
         e.printStackTrace();
      }
      String retStr=protocol+"://"+authority+"/"+context;
      System.out.println("retStr=="+retStr);
      
      return retStr;
   }
}