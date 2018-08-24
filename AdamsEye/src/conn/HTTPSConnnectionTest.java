package conn;

import java.io.*;
import java.net.URL;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.HttpsURLConnection;
import java.util.Arrays;


public class HTTPSConnnectionTest {

    public static void main(String[] args) {

        try {
          //  String urlString = "https://staging-native.usablenet.com";
            
            System.setProperty("javax.net.ssl.trustStore","C:\\certs\\trust.jks");
            
            String trustStore = System.getProperty("javax.net.ssl.trustStore");
            System.out.println(trustStore);
            
            String urlString = "https://staging-native.usablenet.com";
            URL url = new URL(urlString);

            SSLSocketFactory factory
                    = (SSLSocketFactory) SSLSocketFactory.getDefault();
            //SSLSocket socket = (SSLSocket) factory.createSocket(url.getHost(), 7002);
            
            SSLSocket socket = (SSLSocket) factory.createSocket(url.getHost(), 8043);
            
            String[] enCiphersuite=socket.getEnabledCipherSuites();
            System.out.println("Enabled ciphersuites are: "+Arrays.toString(enCiphersuite));
            
            String pickedCipher[] ={"SSL_RSA_WITH_RC4_128_MD5"}; 
            
            socket.setEnabledCipherSuites(pickedCipher);
            System.out.println("ciphersuite set to: "+Arrays.toString(pickedCipher));
            
          //  String pickedCipher[] ={"TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA"}; 
          //  socket.setEnabledCipherSuites(pickedCipher);
            
          //  System.out.println("ciphersuite set to: "+Arrays.toString(pickedCipher));

            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(
                            socket.getOutputStream()));
            out.println("GET " + urlString + " HTTP/1.1");
            out.println();
            out.flush();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

            String line;

            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            out.close();
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
