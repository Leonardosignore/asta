import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class Timer extends Thread {

    private String ipGroup;
    private int portGroup;
    
    public Timer (
        String ipGroup, 
        int portGroup){
            this.ipGroup = ipGroup;
            this.portGroup = portGroup;
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
        System.out.println("timer finished");

        try {
            MulticastSocket multicastSocket = new MulticastSocket(portGroup);

            

            DatagramPacket ds = new DatagramPacket(null, MAX_PRIORITY);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
