/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author KIT376
 */
public class InsecureHTTPMethodTest {

    public static void main(String[] args) {

        String url = "http://127.0.0.1:8080/insecure/public/index.jsp";

        InsecureHTTPMethodTest http = new InsecureHTTPMethodTest();
        System.out.println("\n Send Http POST request");

        try {
            boolean isInsecure = http.testInsecure(url);
            System.out.println("isInsecure" + isInsecure);
        } catch (Exception e) {

        }

    }

    public boolean testInsecure(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("TRACE");

        int responseCode = con.getResponseCode();
        System.out.println("responseCode==" + responseCode);
        if (responseCode == 405) {
            return true;
        }

        return false;

    }

}
