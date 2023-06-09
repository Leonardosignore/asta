import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class UdpServerThread extends Thread {
    
    private String ipServer;
    private int portServer;
    private Item item;
    private Repository repository;
    private int offerMAX; 

    public UdpServerThread(
            int portServer,
            String ipServer, 
            Item item,
            Repository repository
            ) {
        this.portServer = portServer;
        this.ipServer = ipServer;
        this.item = item;
        this.repository = repository;
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

            TimerThread timer = new TimerThread(ipServer);
            timer.start();

            int offerInt = 0;
            //receive offers
            while (offerInt!=-1) {
                DatagramPacket dataPacket = new DatagramPacket(
                        receiveData,
                        receiveData.length);

                multicastSocket.receive(dataPacket);

                String offer = new String (dataPacket.getData()).substring(0,dataPacket.getLength());
                offerInt = Integer.parseInt(offer);

                System.out.println("received new offer " + offerInt + " from group " + ipServer);
                if (offerMAX<offerInt) offerMAX = offerInt;

                System.out.println("Offer max : " + offerMAX);
            }

            System.out.println("FINISHED");

            System.out.println("L'offerta vincitrice è " + offerMAX);

            //delete item on database
            repository.deleteItemById(item.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        receiveUdpMessage();
    }
}
