package sniffer;

import sniffer.AnalyzePacket;
import sniffer.Sniffer;
import sniffer.Utility;
import java.net.*;
import java.io.*;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.*;
	
	class Main {
	    
	   	/* variables */
	   	JpcapCaptor captor;
	   	NetworkInterface[] list;
	   	String str,info;
	   	int x, choice;
	    	
	    	public static void main(String args[]) {
	      	   new Main();
	    	}

	    	public Main() {
	    	     
		     /* first fetch available interfaces to listen on */
		        list = JpcapCaptor.getDeviceList();
			System.out.println("Available interfaces: ");
			
			for(x=0; x<list.length; x++) {
			     System.out.println(x+" -> "+list[x].description);  
			}
		            System.out.println("-------------------------\n");
		            choice = Integer.parseInt(getInput("Choose interface (0,1..): "));
			    System.out.println("Listening on interface -> "+list[choice].description);
			    System.out.println("-------------------------\n");
		  
		  
		  
		  /*Setup device listener */
		  try {
		         captor=JpcapCaptor.openDevice(list[choice], 65535, false, 20);
			 /* listen for TCP/IP only */
			// captor.setFilter("ip and tcp", true);
                         captor.setFilter("port 80",true);
		      }
		         catch(IOException ioe) { ioe.printStackTrace(); }
		  
		  
		  /* start listening for packets */
		  while (true) { 
	                
	                Packet info = captor.getPacket();
		           if(info != null){
                             System.out.println("packet captured!");
                             AnalyzePacket.analysis(info);
		             //getPacketText(info);
                           }
	             }
	    	}
	   
        
        /* get user input */
        public static String getInput(String q) {
	      String input = "";
	      System.out.print(q);
	      BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in));
	           try {
	               input = bufferedreader.readLine();
	             }
	               catch(IOException ioexception) { }
	        return input;
            }
      
     
     /* return packet data in true text */
     void getPacketText(Packet pack){
         
           System.out.println("pack.header.length"+pack.header.length);
           System.out.println("pack.data.length"+pack.data.length);
           
            byte[] bytesData=  pack.data;
            byte[] bytesHeader= pack.header;
            
          //  bytesToString(bytesData,true);
             Utility.bytesToString(bytesData,false);
             
           //  bytesToString(bytesHeader,true);
             Utility.bytesToString(bytesHeader,false);
           
         
      	 }
     
     
   
} 

