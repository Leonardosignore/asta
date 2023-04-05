import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class UdpThreadListener extends Thread {

    private String ipClient;
    private int port;

    public UdpThreadListener(String ipClient, int port) {
        this.ipClient = ipClient;
        this.port = port;
    }

    public void receiveUdpMessage() {

        byte[] receiveData = new byte[1024];

        try {
            MulticastSocket multicastSocket = new MulticastSocket(port);
            InetAddress address = InetAddress.getByName(ipClient);
            InetSocketAddress group = new InetSocketAddress(address, port);
            NetworkInterface netIf = NetworkInterface.getByName("bge0");

            multicastSocket.joinGroup(group, netIf);

            System.out.println("[5] Listener udp started");
            
            while (true) {

                DatagramPacket dataPacket = new DatagramPacket(
                        receiveData,
                        receiveData.length);

                multicastSocket.receive(dataPacket);

                byte[] byteOffer = dataPacket.getData();

                System.out.println("new offer received " + byteOffer.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        receiveUdpMessage();
    }

}
