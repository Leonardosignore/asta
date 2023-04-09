import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TimerThread extends Thread {

    private String ipGroup;

    public TimerThread (String ipGroup){
        this.ipGroup = ipGroup;
    }

    public void run (){
        System.out.println("Timer started");
        for(int i=0;i<50;i++){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        int portGroup = 5000;

        String msg = "-1";

            byte[] msgByte = msg.getBytes();
            InetAddress group = null;
            try {
                group = InetAddress.getByName(ipGroup);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            DatagramPacket dp = new DatagramPacket(
                msgByte,
                msgByte.length,
                group, 
                portGroup);
            
            try {
                ds.send(dp);
                System.out.println("Send finisch packet");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
