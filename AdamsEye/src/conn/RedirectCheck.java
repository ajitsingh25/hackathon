/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conn;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

/**
 *
 * @author Dilwar
 */
public class RedirectCheck {
    public static void main(String[] args){
        String url = "http://localhost:8080/insecure/public/Login.jsp";
        Response response;
        try {
            response = Jsoup.connect(url).followRedirects(false).method(Connection.Method.GET).execute();
            System.out.println(response.header("location"));
        } catch (IOException ex) {
            Logger.getLogger(RedirectCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
