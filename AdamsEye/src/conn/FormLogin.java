package conn;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author KIT376
 */
public class FormLogin {

    private List<String> cookies;
    private static HttpURLConnection conn;
    private static URL obj = null;
    public static StringBuffer response = null;
    public static boolean result=false;

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        String getUrl = "http://localhost:7001/form/protected/protected.jsp";
        String postUrl = "http://localhost:7001/form/j_security_check";
   //     System.out.println(formLogin(getUrl, "weblogic1", "weblogic123"));
        System.out.println(formLogin(getUrl, "weblogic", "weblogic123"));
     //   System.out.println(formLogin(getUrl, "weblogic2", "weblogic123"));
    }

    public static boolean formLogin(String url, String username, String password) {

        String getUrl = url;
        String relUrl = URLTest.getRelativeURL(url);
        boolean result = false;
        String postUrl = relUrl + "/j_security_check";
        System.out.println("post url :: " + postUrl);

        //   String getUrl = "http://localhost:8180/console/login/LoginForm.jsp";
        //   String postUrl = "http://localhost:8180/console/j_security_check";
        FormLogin http = new FormLogin();

        // make sure cookies is turn on
        CookieHandler.setDefault(new CookieManager());

        try {
            
            System.out.println("Inside Try Block--- Calling getPageContent\n");
            String page = http.GetPageContent(getUrl);
            
            System.out.println("Calling getFormParams****\n");
            String postParams = http.getFormParams(page, username, password);
            
            System.out.println("**** POst Params ****"+postParams.toString());
            
            System.out.println("*** Calling SendPost ***");

            result = http.sendPost(postUrl, postParams);
            System.out.println("result from sendPost"+result);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        System.out.println("returning from formLogin"+result);
        return result;
    }

    private boolean sendPost(String url, String postParams) throws Exception {

        boolean result=false;
        System.out.println("Inside the SendPost*****");
       
        response = null;
        // Acts like a browser
        URL obj = new URL(url);
        conn = (HttpURLConnection) obj.openConnection();
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Host", "10.0.0.7:7001");
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//	for (String cookie : this.cookies) {
//		conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
//	}
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Referer", "http://localhost:7001/form/login.jsp");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));

        conn.setDoOutput(true);
        conn.setDoInput(true);

        // Send post request
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(postParams);
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code is::::: " + responseCode);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + postParams);
        System.out.println("Response Code sendPost :  " + responseCode);

        if (responseCode == 200) {
            result = true;
            System.out.println(" result is true - entered if");
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {

                response.append(inputLine);
            }

            in.close();

        }
        System.out.println(" returning result from sendPost"+result);
        return result;

    }

    private String GetPageContent(String url) throws Exception {

        URL obj = new URL(url);
        conn = (HttpURLConnection) obj.openConnection();

        // default is GET
        conn.setRequestMethod("GET");

        conn.setUseCaches(false);

        // act like a browser
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
       
        System.out.println("2");
        if (cookies != null) {
            for (String cookie : this.cookies) {
                conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
            }
        }
        
        System.out.println("3\n");
        System.out.println("Conn Object:::"+conn.toString());
        int responseCode = conn.getResponseCode();
      
         System.out.println("response code"+responseCode);
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in
                = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Get the response cookies
        setCookies(conn.getHeaderFields().get("Set-Cookie"));

        return response.toString();

    }

    

    public String getFormParams(String html, String username, String password)
            throws UnsupportedEncodingException {

        System.out.println("Extracting form's data..." + html);

        Document doc = Jsoup.parse(html);

        Element loginform = doc.getElementById("loginData");
        Elements inputElements = loginform.getElementsByTag("input");
        List<String> paramList = new ArrayList<String>();
        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

            if (key.equals("j_username")) {

                value = username;
                paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
            } else if (key.equals("j_password")) {
                value = password;
                paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
            } else if (key.equals("j_character_encoding")) {
                paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));

            }
        }

        // build parameters list
        StringBuilder result = new StringBuilder();
        for (String param : paramList) {
            if (result.length() == 0) {
                result.append(param);
            } else {
                result.append("&" + param);
            }
        }

        return result.toString();
    }

    public List<String> getCookies() {
        return cookies;
    }

    public void setCookies(List<String> cookies) {

        System.out.println("Printing cookies.." + cookies);
        this.cookies = cookies;
    }

}
