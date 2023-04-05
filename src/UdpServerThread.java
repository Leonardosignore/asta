import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class UdpServerThread extends Thread {

    private String ipClient;
    private String ipServer;

    private int portServer;
    private int portClient;

    public UdpServerThread(
            String ipClient,
            int portServer,
            String ipServer,
            int portClient) {
        this.ipClient = ipClient;
        this.portServer = portServer;
        this.ipServer = ipServer;
        this.portClient = portClient;
    }

    public void sendOffer(String offer) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            InetAddress group = InetAddress.getByName(ipClient);
            byte[] msg = offer.getBytes();
            DatagramPacket packet = new DatagramPacket(
                    msg,
                    msg.length,
                    group,
                    portClient);
            socket.send(packet);
            System.out.println("Send offer to group Client " + offer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (socket != null) {
            socket.close();
        }

    }

    public void receiveUdpMessage() {

        byte[] receiveData = new byte[1024];

        try {
            MulticastSocket multicastSocket = new MulticastSocket(portServer);
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

                byte[] byteOffer = dataPacket.getData();

                System.out.println("received data " + byteOffer.toString()+ " from server");

                sendOffer(byteOffer.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        receiveUdpMessage();
    }
}
