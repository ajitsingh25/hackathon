/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sniffer;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;


/**
 *
 * @author FAISAL
 */
public class PacketReader {
    
    
    
    
    public static void main(String args[]) {

        //Obtain the list of network interfaces
       NetworkInterface[] devices = JpcapCaptor.getDeviceList();

        //for each network interface
        for (int i = 0; i < devices.length; i++) {
            //print out its name and description
            System.out.println(i + ": " + devices[i].name + "(" + devices[i].description + ")");

            //print out its datalink name and description
            System.out.println(" datalink: " + devices[i].datalink_name + "(" + devices[i].datalink_description + ")");

            //print out its MAC address
            System.out.print(" MAC address:");
            for (byte b : devices[i].mac_address) {
                System.out.print(Integer.toHexString(b & 0xff) + ":");
            }
            System.out.println();

            //print out its IP address, subnet mask and broadcast address
            for (NetworkInterfaceAddress a : devices[i].addresses) {
                System.out.println(" address:" + a.address + " " + a.subnet + " " + a.broadcast);
            }
            
            
           
         int index=5;  // set index of the interface that you want to open.
         boolean type= false;  // true for hex;
         
        
        try{
          JpcapCaptor jpcap = JpcapCaptor.openDevice(devices[index], 2000, false, 20);
          jpcap.setFilter("port 80",true);
        //  jpcap.loopPacket(-1,new Sniffer(type));
          
         }catch(Exception e){
         e.printStackTrace();
         } 

      
         
            
        }

    }

}
