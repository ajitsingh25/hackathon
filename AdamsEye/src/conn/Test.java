/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conn;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;

/**
 *
 * @author Dilwar
 */
public class Test {
    public static void main(String[] args) {
        try {
            String uu = "http://localhost:8080/insecure/public/Login.jsp";
            Connection.Response getRes = Jsoup.connect(uu).method(Method.GET).execute();
            Map<String, String> x = getRes.cookies();
            for(String y : x.keySet()){
                System.out.println(y+" --> "+x.get(y));
            }
            String u = "http://localhost:8080/insecure/public/Login.jsp?login=admin&pass=secret";
            getRes = Jsoup.connect(u).method(Method.GET).cookies(x).execute();
            String url1 = "http://localhost:8080/insecure/admin/admin.jsp";
            
            //x.put("JSESSIONID", "XXXXXXXXXXXXXXX"+x.get("JSESSIONID"));
            //x.remove(x.get("JSESSIONID"));
            for(String y : x.keySet()){
                System.out.println(y+" => "+x.get(y));
            }
            String session = x.get("JSESSIONID");
            Connection.Response getRes1 = Jsoup.connect(url1).method(Method.GET).cookies(x).execute();
            String t = "http://localhost:8080/insecure/public/index.jsp?productSearch=abcd\" </td></tr><table><script>alert(\"xyz\")</script><table><tr><td>kuchbhi";
            Jsoup.connect(t).method(Method.GET).cookies(x).execute();
            System.out.println(getRes1.parse().html());
            
            
            /*
            url = url1+"?productSearch=abcdefgh'";
            //url=url1+"?keys=abcdefg'";
            getRes2 = Jsoup.connect(url).method(Connection.Method.GET).cookies(loginCookies).execute();
            
            String correct = getRes2.parse().html();
            correct=correct.replaceAll("abcdefgh", "abc");
            correct=correct.replaceAll("&quot", "\"");
            //System.out.println(correct.replaceAll("abcd", "abc;"));
            
            url = url1+"?productSearch=abc\'";
            //url=url1+"?keys=abc";
            getRes2 = Jsoup.connect(url).method(Connection.Method.GET).cookies(loginCookies).execute();
            
            String incorrect = getRes2.parse().html();
            incorrect=incorrect.replaceAll("&quot;", "\"");
            incorrect=incorrect.replaceAll("abc\"", "abc");
            System.out.println(correct+"\n\n\n\n\n\n\n\n\n\n"+incorrect);
            System.out.println(correct.length()+" "+incorrect.length());
            for(int i=0;i<correct.length();i++){
                if(correct.charAt(i)!=incorrect.charAt(i)){
                    System.out.println("NOT MATCHED "+i);
                    System.out.println(correct.substring(i));
                    System.out.println(incorrect.substring(i,i+10));
                    break;
                
                }
            }
            url = url1+"?productSearch=deded";
            //getRes2 = Jsoup.connect(url).method(Connection.Method.GET).cookies(loginCookies).execute();
            
            String incorrect1 = getRes2.parse().html();
            
            url = url1+"?productSearch=abc'";
            //System.out.println(url);
            //getRes2 = Jsoup.connect(url).method(Connection.Method.GET).cookies(loginCookies).execute();
            
            String incorrecttrue = getRes2.parse().html();
            
            System.out.println("plain vs correct "+plain.equalsIgnoreCase(correct));
            System.out.println("correct vs incorrect "+correct.equalsIgnoreCase(incorrect));
            
            System.out.println();
            System.out.println("---------------");
                    */
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
    }
}
