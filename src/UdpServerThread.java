import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class UdpServerThread extends Thread {
    private String ipServer;

    private int portServer;

    public UdpServerThread(
            String ipClient,
            int portServer,
            String ipServer,
            int portClient) {
        this.portServer = portServer;
        this.ipServer = ipServer;
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

            System.out.println("[5] Listener udp started");

            while (true) {
                DatagramPacket dataPacket = new DatagramPacket(
                        receiveData,
                        receiveData.length);

                multicastSocket.receive(dataPacket);

                String offer = new String (dataPacket.getData());

                System.out.println("received new offer " + offer + " from Client " + dataPacket.getPort());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        receiveUdpMessage();
    }
}
