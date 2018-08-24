
package sniffer;

//import com.sun.xml.internal.bind.v2.TODO;
import java.io.UnsupportedEncodingException;
//import static java.lang.Character.UnicodeScript.of;
import static java.net.Proxy.Type.HTTP;
import jpcap.packet.ARPPacket;
import jpcap.packet.DatalinkPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;
import security.PacketSniffer;
import static sun.misc.Signal.handle;

/**
 *
 * @author FAISAL
 */
public class AnalyzePacket {

    public static StringBuffer analysis(Packet packet) {
        StringBuffer sb = new StringBuffer();
        sb.append("------ the packet analysis ------- \n");
        sb.append("Captured Length:" + packet.caplen + "byte \n");
        sb.append("Length of this Packet:" + packet.len + "byte \n");
        sb.append("Length of Header:" + packet.header.length + "byte \n");
        sb.append("Data:" + packet.data + "\n");
        sb.append("Length of Data:" + packet.data.length + "byte \n");
        sb.append("--- Ethernet header information --- \n"); 

        TCPPacket tPacket = (TCPPacket) packet;
        String hexHeader = Utility.bytesToString(tPacket.header, true);
        sb.append("Header:" + hexHeader + "\n");
        
        sb.append("Source Port:" + tPacket.src_port + "\n");
        sb.append("Destination Port:" + tPacket.dst_port + "\n");
        sb.append("Sequence Number:" + tPacket.sequence + "\n");
        sb.append("Acknowledge Number:" + tPacket.ack_num + "\n");
        sb.append("URG:" + tPacket.urg + "\n");
        sb.append("ACK:" + tPacket.ack + "\n");
        sb.append("PSH:" + tPacket.psh + "\n");
        sb.append("RST:" + tPacket.rst + "\n");
        sb.append("SYN:" + tPacket.syn + "\n");
        sb.append("FIN:" + tPacket.fin + "\n");
        sb.append("Window Size:" + tPacket.window + "\n");
        sb.append("Urgent Pointer:" + tPacket.urgent_pointer + "\n");
        sb.append("Option:" + tPacket.option + "\n");
        sb.append("------------------ \n");

                // analyze the HTTP protocol 
        sb.append("--- HTTP --- \n");

        byte[] data = tPacket.data;
        if (data.length == 0) {
            sb.append("This is without data response message! \n");
        } else {
            if (tPacket.src_port == 80) { // accept HTTP response
                String str = null;
                try {
                    String str1 = new String(data, "UTF-8");
                    if (str1.contains("HTTP/1.1")) {
                        str = str1;
                    } else {
                        String str2 = new String(data, "GB2312");
                        if (str2.contains("HTTP/1.1")) {
                            str = str2;
                        } else {
                            String str3 = new String(data, "GBK");
                            if (str3.contains("HTTP/1.1")) {
                                str = str3;
                            } else {
                                str = new String(data, "Unicode");
                            }
                        }
                    }
                    sb.append(str + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (tPacket.dst_port == 80) {
                    try {
                        String str1 = new String(data, "ASCII");
                        sb.append(str1);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
        }
        
        sb.append ("\n");
        System.out.println(sb);
        return sb;
        
       
    }

    
}

   
