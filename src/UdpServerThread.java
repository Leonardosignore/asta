import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class UdpServerThread extends Thread {
    
    private String ipServer;
    private int portServer;
    private int offerMAX; 

    public UdpServerThread(
            int portServer,
            String ipServer
            ) {
        this.portServer = portServer;
        this.ipServer = ipServer;
        this.offerMAX = 0;
    }

    public void receiveUdpMessage() {

        byte[] receiveData = new byte[1024];
        MulticastSocket multicastSocket = null;

        try {
            multicastSocket = new MulticastSocket(portServer);
            InetAddress address = InetAddress.getByName(ipServer);
            InetSocketAddress group = new InetSocketAddress(address, portServer);
            NetworkInterface netIf = NetworkInterface.getByName("bge0");

            multicastSocket.joinGroup(group, netIf);

            System.out.println("[5] Listener udp started on ip " + ipServer + ", on port " + portServer);

            while (true) {
                DatagramPacket dataPacket = new DatagramPacket(
                        receiveData,
                        receiveData.length);

                multicastSocket.receive(dataPacket);

                String offer = new String (dataPacket.getData()).substring(0,dataPacket.getLength());

                int offerInt = Integer.parseInt(offer);

                System.out.println("received new offer " + offerInt + " from Client " + dataPacket.getPort());

                if (offerMAX<offerInt) offerMAX = offerInt;

                System.out.println("Offer max : " + offerMAX);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        receiveUdpMessage();
    }
}
