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

import java.io.*;
import java.net.URL;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.SSLSocket;

import java.util.Arrays;


public class CheckWeakSSLCipher {
    
    public static void main(String[] args){
     String urlString = "https://localhost:8043/console";
     boolean status = CheckWeakSSLCipher.isWeakSSLCipherUsed(urlString);
     
     
    }

    public static boolean isWeakSSLCipherUsed(String urlString) {

        try {
          //  String urlString = "https://localhost:7002/console/login/LoginForm.jsp";
            
            System.setProperty("javax.net.ssl.trustStore","C:\\JAVA\\trust.jks");
            
            String trustStore = System.getProperty("javax.net.ssl.trustStore");
            System.out.println(trustStore);
            
           
            URL url = new URL(urlString);

            SSLSocketFactory factory
                    = (SSLSocketFactory) SSLSocketFactory.getDefault();
            //SSLSocket socket = (SSLSocket) factory.createSocket(url.getHost(), 7002);
            
            SSLSocket socket = (SSLSocket) factory.createSocket(url.getHost(), 8043);
            
            String[] enCiphersuite=socket.getEnabledCipherSuites();
            System.out.println("Enabled ciphersuites are: "+Arrays.toString(enCiphersuite));
            
           String pickedCipher[] ={"SSL_RSA_WITH_NULL_MD5","SSL_RSA_WITH_NULL_SHA","SSL_RSA_WITH_DES_CBC_SHA","SSL_DHE_RSA_WITH_DES_CBC_SHA","SSL_DHE_DSS_WITH_DES_CBC_SHA"                                                                                                                                                                                                                                                                               }; 
            
            socket.setEnabledCipherSuites(pickedCipher);
        //    System.out.println("ciphersuite set to: "+Arrays.toString(pickedCipher));
            
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
			
			
			System.out.println("Server supports Weak Ciphers");
                        return true;

        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return false;
    }

}

