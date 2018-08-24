/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conn;

/**
 *
 * @author KIT376
 */
import static conn.LoginUtil.sessionid;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import org.jsoup.Connection;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerTest {

    public static int totalSize = 0;
    public static HashSet<String> urlHS = new HashSet<String>();
    public static Map<String, String> loginCookies = null;
    public static int recursionLevel = 3; // set it accordingly
    public String app = "google";///insecure";
    public static void main(String args[]) {
        System.out.println(new CrawlerTest().getAppName("http://google.co.in"));
    }

    public  HashSet<String> crawlSite(String html, Map<String, String> _loginCookies, String baseUri) throws Exception {
        
        loginCookies = _loginCookies;
        Document doc = Jsoup.parse(html);
        Elements links = doc.select("a[href]");
        String absoluteUrl="";
        System.setProperty("https.proxyHost", "proxy.iiit.ac.in");
        System.setProperty("https.proxyPort", "8080");
        System.setProperty("http.proxyHost", "proxy.iiit.ac.in");
        System.setProperty("http.proxyPort", "8080");
        app = getAppName(baseUri);
        System.out.println("app name is "+app);
        try{
            System.out.println("no of links in 1st page= "+links.size());
            for (Element link : links) { 
                link.setBaseUri(baseUri);
                absoluteUrl = link.absUrl("href");
                System.out.println("base URI: "+link.baseUri());
                System.out.println("absURL= "+absoluteUrl);
                if(absoluteUrl==null || absoluteUrl.equals("") || absoluteUrl.length()==0){
                    System.out.println("INVALID ABSOLUTE URL\n\n\n\n\n\n\n\n\n\n\n");
                    continue;
                }

                if(!urlHS.contains(absoluteUrl) && absoluteUrl.contains(app) && !absoluteUrl.toLowerCase().contains("logout"))
                    crawl(absoluteUrl, recursionLevel);

            }
                
        }
        catch(Exception e){
            System.out.println("FALIED FOR URL : "+absoluteUrl);
        }







        System.out.println("Crawling Done..... " + urlHS.size());
			for (String s : urlHS) {
				System.out.println(s);

			}
        return urlHS;
    }

    public void crawl(String url, int level) {
        if (level == 0 || url.equals("/") || url.contains("Logout.jsp")) {
            return;
        }
        System.out.println("Crawling level" + level);
        System.out.println("Crawling url " + url);
		//TODO
        //Put conditions for the URLs you need not crawl -- /insecure/
        //TODO
        //Have to convert URLs like xyz/abc/../somepage.jsp to xyz/somepage.jsp
        if(!isValidPage(url)) return;
        urlHS.add(url);
        try{
            Connection.Response getRes2 = Jsoup.connect(url).method(Method.GET).cookies(loginCookies).execute();
            System.out.println("======");
            Document document2 = getRes2.parse();
            System.out.println("BASE" +document2.baseUri());
            String htmlString = document2.html();
            Document doc = Jsoup.parse(htmlString);

            String baseUri = document2.baseUri();
            Elements links = doc.select("a[href]");
                           // Elements links = doc.select("a[href$=jsp]");
            System.out.println("no of links in "+url +" = "+links.size());
            for (Element link : links) { 
                link.setBaseUri(baseUri);
                String absoluteUrl = link.absUrl("href");
                //System.out.println("base URI: "+link.baseUri());
                System.out.println("absURL= "+absoluteUrl);
                if(absoluteUrl==null || absoluteUrl.equals("") || absoluteUrl.length()==0){
                    String urlFrmLink = link.attr("href");
                    if(urlFrmLink.equals("/")) continue;
                    System.out.println("=="+link.attr("abs:href"));
                    System.out.println("current page: "+url+"     got link: "+urlFrmLink);
                    absoluteUrl = convertToAbsoluteUrl(url, urlFrmLink);
                    System.out.println("converted to "+absoluteUrl+" from "+urlFrmLink);
                }

                if(!urlHS.contains(absoluteUrl) && absoluteUrl.contains(app) && !absoluteUrl.toLowerCase().contains("logout"))
                    crawl(absoluteUrl, level - 1);

            }
        }
        catch(Exception e){
            System.out.println("FALIED FOR URL : "+url);
            e.printStackTrace();
        }
        

    }

    private String convertToAbsoluteUrl(String prefix, String urlFrmLink) {
        return prefix+urlFrmLink;
    }

    private boolean isValidPage(String ur) {
        if(ur==null || (ur.endsWith(".java") || ur.endsWith(".xml")||ur.endsWith(".zip") || ur.endsWith(".txt") || ur.endsWith(".pdf")))
            return false;
        if(!ur.toLowerCase().startsWith("http") || !ur.toLowerCase().startsWith("http"))
            return false;
        return true;
    }

    private String getAppName(String baseUri) {
        String ret="";
        if(baseUri.contains("insecure")) return "insecure";
        if(baseUri.contains("localhost")) return "localhost";
        if(baseUri.contains("http://127.0.0.1/")) return "http://127.0.0.1/";
        try{
            if(baseUri.contains("www.")){
                ret = baseUri.substring(baseUri.indexOf("www.")+4);
                ret = ret.substring(0,ret.indexOf("."));
            }
            else{
                ret = baseUri.substring(baseUri.indexOf("//")+2);
                ret = ret.substring(0,ret.indexOf("."));
            }
        }
        catch(Exception e){e.printStackTrace();}
        finally{
            System.out.println("app name for "+baseUri+" is "+ ret);
            return ret;
        }
    }
}

// src
// http://stackoverflow.com/questions/17048871/how-to-navigate-websites-using-jsoup-in-java?rq=1
