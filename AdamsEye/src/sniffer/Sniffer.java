package sniffer;


//import parser.*;
import jpcap.*;
import jpcap.packet.Packet;
import javax.swing.*;
import java.util.*;
import javax.swing.plaf.ScrollPaneUI;

/*
 * Class that does the sniffing work...sort of.
 * snifferthread passes an instance of Sniffer to the pcap library to pass packets back too.
 */

public class Sniffer implements PacketReceiver
{
    
    private boolean displayhex;
    private int pknum;
    public JEditorPane dataPane;
    public StringBuffer dataStringBuffer=null;
    /* Constructor */
    public Sniffer( boolean hex, JEditorPane dataPane)
    {
     
        this.dataPane=dataPane;
        displayhex=hex;
        pknum = 0;
    }
    
   
    
    /* Function that is passed to packets by pcap.*/
    public void receivePacket(Packet packet) 
    {
        dataStringBuffer =AnalyzePacket.analysis(packet);
        System.out.println("*** Data From Sniffer ***\n"+dataStringBuffer.toString());
        dataPane.setText(dataStringBuffer.toString());
        
        
        //JTextArea jtxt = new JTextArea();
        
    //    jtxt.setText(jtxt.toString());
    //    ScrollPaneUI sui = new ScrollPaneUI();
      //  scrollPane.setUI(null);
       
    }
}