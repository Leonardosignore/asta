import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.ArrayList;

public class UdpThreadListener extends Thread {

    private String ipClient;
    private int port;

    public UdpThreadListener(
        String ipClient, 
        int port) {
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

            int offerInteger = 0;

            ArrayList<Integer> offers = new ArrayList<Integer>();

            while (offerInteger!=-1) {
                DatagramPacket dataPacket = new DatagramPacket(
                        receiveData,
                        receiveData.length);

                multicastSocket.receive(dataPacket);

                String offer = new String(dataPacket.getData()).substring(0,dataPacket.getLength());

                offerInteger = Integer.parseInt(offer);
                offers.add(offerInteger);

                System.out.println("new offer received " + "'" + offerInteger + "'");
            }

            int offerWinner = 0;

            for (Integer integer : offers) {
                if (offerWinner<integer){
                    offerWinner = integer;
                }
            }

            System.out.println("Offerta vincitrice: " + offerWinner);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        receiveUdpMessage();
    }

}
