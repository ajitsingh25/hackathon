package conn;

import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JSoupTest {

    public static void main(String[] args) throws Exception {

        JSoupTest test = new JSoupTest();
        String loginURL = "http://localhost:8080/insecure/public/index.jsp";

       // test.getFormName(loginURL);

    }

    public static String getFormName(Document document) {
        
           String formID = "";
            String name = "";
        try {
            //Document document = Jsoup.connect(url).get();
            
        //    Document document = Jsoup.parse(inp);

        //   System.out.println(document);
            Elements elements = document.body().select("*");

            List<FormElement> frmList = elements.forms();
         

            for (FormElement fe : frmList) {
                formID = fe.attr("action");
             //   System.out.println("form action " + formID);
                name = fe.attr("name");

            //    System.out.println("form name " + name);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return name;

    }
}