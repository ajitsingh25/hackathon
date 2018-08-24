/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sniffer;

import java.math.BigInteger;

/**
 *
 * @author KIT376
 */
public class Utility {
    
    public static void main(String args[]){
    
     String hex="2c";
     
     
     hexToInteger(hex);
    }
    
    
    
    public static void hexToInteger(String hex) 
    {   
        Integer i = Integer.parseInt(hex,16);
        
        System.out.print(i);
       
    }
    
     public static String byteToHex(byte b) 
    {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7','8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] array = {hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f]};
        return new String(array);
    }

    /* Converts a byte array into a string.*/
    public static String bytesToString(byte[] array, boolean displayhex)
    {
        int x=0;
        StringBuffer str = new StringBuffer();
        if(displayhex == true)
        {
            for(int k = 0; k < array.length; k++) 
            {
                if(x<9)
                {
                    str.append(" " + byteToHex(array[k]));
                    ++x;
                }
                else
                {
                    x=0;
                    str.append(" " + byteToHex(array[k])+ "\r\n");
                }
            }
            
            return str.toString();
        }
        else
        {  
           
            return new String(array);
        }
    }
    
}
