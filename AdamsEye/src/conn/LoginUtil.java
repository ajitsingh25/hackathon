package conn;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class LoginUtil {
    
    
  private static List<String> cookies;
  private static HttpURLConnection conn;
  private static URL obj= null;
  public static String sessionid="";
  public static Map<String, String> loginCookies=null;
  
  
  public static void main(String[] args) throws Exception {
    String url = "http://localhost:8080/insecure/public/Login.jsp";
     //url = "http://iiit.ac.in";
    new LoginUtil().getAllUrls(url, null, null);
  }
 

    public String isRedirect(String url) {
        Connection.Response response;
        String redirect = null;
        try {
            response = Jsoup.connect(url).followRedirects(false).method(Connection.Method.GET).execute();
            redirect = response.header("location");
        } catch (IOException ex) {
            Logger.getLogger(RedirectCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            return redirect;
        }
    }

    public ArrayList<String> getFormParameters(String url) {
        try{
            System.out.println("Extracting form's data...");
            Connection.Response response = Jsoup.connect(url).method(Connection.Method.GET).execute();
            Document document = response.parse();
            Elements forms = document.getElementsByTag("form");
            if(forms.isEmpty()){
                System.out.println("No input Form");
                return null;
            }
            Elements inputElements = forms.get(0).getElementsByTag("input");
            Iterator<Element> elementIterator = inputElements.iterator();
            ArrayList<String> params = new ArrayList<>();
            while (elementIterator.hasNext()) {
                params.add(elementIterator.next().attr("name"));
            }
            return params;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public HashSet<String> getAllUrls(String url, ArrayList<String> params, ArrayList<String> paramsValues) throws Exception{
        LoginUtil http = new LoginUtil();
 	CookieHandler.setDefault(new CookieManager());
        Connection.Response response = Jsoup.connect(url).method(Method.GET).execute();
        loginCookies = response.cookies();
        String paramAppended = getAppend(params, paramsValues);
        Connection.Response getRes2 = Jsoup.connect(url+paramAppended).method(Method.GET).cookies(loginCookies).execute();
        sessionid = getRes2.cookie("JSESSIONID");
        
        System.out.println("session id from response 2"+sessionid);
        
        
        Document document2 = getRes2.parse();
        String baseUri = document2.baseUri();
        String htmlString = document2.html();
        
        CrawlerTest ct = new CrawlerTest();
        try{
             
             ct.crawlSite(htmlString,loginCookies, baseUri);
        }catch (Exception e){
            e.printStackTrace();
        }
        
         
         	System.out.println("Crawling Done..... " + ct.urlHS.size());
			for (String s : ct.urlHS) {
				System.out.println(s);

			}
       
      return ct.urlHS;
  
        
    }

    private String getAppend(ArrayList<String> params, ArrayList<String> paramsValues) {
        try{
            if(params==null || paramsValues==null) return "";
            String ret = "?";
            int noOfParams = params.size();
            for(int i=0;i<noOfParams;i++){
                if(params.get(i)!=null && params.get(i).length()!=0){
                    ret=ret+ params.get(i) + "=" + paramsValues.get(i);
                    if(i!=noOfParams-1)
                        ret = ret + "&";
                }
            }
            return ret;
        }
        catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }
 
  
}
