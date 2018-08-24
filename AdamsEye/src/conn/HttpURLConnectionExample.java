package conn;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.List;
 
 
public class HttpURLConnectionExample {
 
	
	public static void main(String[] args) throws Exception {
            
                String url = "http://127.0.0.1:8080/insecure/public/index.jsp";
 
		HttpURLConnectionExample http = new HttpURLConnectionExample();
		System.out.println("\n Send Http POST request");
		http.sendPost(url);
 
	}
 
	// HTTP POST request
	public void sendPost(String url) throws Exception {

	
                
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		//add reuqest header
		con.setRequestMethod("TRACE");
	//	String urlParameters = "USER=abc&PASSWORD=xyz";
	//	con.setDoOutput(true);
	//	DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	//	wr.writeBytes(urlParameters);
	//	wr.flush();
	//	wr.close();
 
		int responseCode = con.getResponseCode();
                System.out.println("responseCode=="+responseCode);
		
                checkResponseHeaders(con);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine="";
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
	
 
	}
        
       public void  checkResponseHeaders(HttpURLConnection con){
           
        Map<String, List<String>> map = con.getHeaderFields();
	for (Map.Entry<String, List<String>> entry : map.entrySet()) {
		System.out.println("Key : " + entry.getKey() + 
                 " ,Value : " + entry.getValue());
	}
        
         String strictTSH = con.getHeaderField("Strict-Transport-Security");
         
         if( strictTSH == null) {
         
         System.out.println(" Strict-Transport-Security header is missing");
         
         }
         
         
         
        
        }
 
}