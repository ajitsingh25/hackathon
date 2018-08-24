package conn;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;

public class BasicAuth {
       public static boolean status=false;
       public static StringBuffer response;
       
       public static void main(String args[]){
       boolean res = BasicAuth.loginBasic("http://10.0.0.10:7001/basic", "weblogic", "weblogic1234");
       System.out.print(res);
       }

	public static boolean loginBasic(String webPage, String name, String password) {
           
		try {
			

			String authString = name + ":" + password;
			System.out.println("auth string: " + authString);
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);
			System.out.println("Base64 encoded auth string: " + authStringEnc);

			URL url = new URL(webPage);
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
			InputStream is = urlConnection.getInputStream();
                        int responseCode = urlConnection.getResponseCode();
                        System.out.println("response code"+responseCode);
                        
                        if(responseCode == 200){
                        status=true;
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			response = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				response.append(charArray, 0, numCharsRead);
			}
			String result = response.toString();
			System.out.println(result);
                        }
                        
		} catch (Exception e) {
                    System.out.println("Response code not 200");
		  //	e.printStackTrace();
		} 
                
                return status;
        }

}