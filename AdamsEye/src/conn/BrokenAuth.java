package conn;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BrokenAuth {

    public static boolean status=false;
    

    public static String sessionid1 = "";
    public static String sessionid2 = "";
    public static Map<String, String> loginCookies = null;
    public static String relUrl = "";

    public static void main(String args[]) {

        String url = "http://localhost:8080/insecure/public/Login.jsp";
        
        BrokenAuth ba = new BrokenAuth();
    
        System.out.println("Broken authentication present? "+  ba.checkBrokenAuth(url));
    }

    public  boolean checkBrokenAuth(String url) {

        try {

            System.out.println("url" + url);
            Connection.Response getRes1 = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .execute();

            loginCookies = getRes1.cookies();
            sessionid1 = getRes1.cookie("JSESSIONID");
            System.out.println("session id before login==" + sessionid1);

            Document document1 = getRes1.parse();

            //ArrayList<String> params = LoginUtil.getFormParams(document1);
            String params = "login=admin&pass=secret";
            Connection.Response getRes2 = Jsoup.connect(url + "?" + params)
                    .method(Connection.Method.GET)
                    .cookies(loginCookies)
                    .execute();

            sessionid2 = getRes2.cookie("JSESSIONID");

            System.out.println("session id after login==" + sessionid2);
            
            if(sessionid1 != null && sessionid2 != null && sessionid1.equals(sessionid2)) {
               return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;

    }

}
