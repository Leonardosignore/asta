import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpThreadActive extends Thread {

    private String ipServer;
    private int portServer;
    private String offer;

    public UdpThreadActive(String ipServer, int port, String offer) {
        this.ipServer = ipServer;
        this.portServer = port;
        this.offer = offer;
    }

    public void run() {
        sendOffer(
            offer, 
            ipServer, 
            portServer);
    }

    public void sendOffer(
            String offer,
            String ipServer,
            int portServer) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            InetAddress group = InetAddress.getByName(ipServer);
            byte[] msg = offer.getBytes();
            DatagramPacket packet = new DatagramPacket(
                msg,
                msg.length, 
                group, 
                portServer);
            socket.send(packet);
            System.out.println("Send offer to server " + offer);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (socket != null) {
            socket.close();
        }
    }
}
