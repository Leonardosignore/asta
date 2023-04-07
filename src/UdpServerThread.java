import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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

            Timer timer = new Timer();
            timer.start();

            while (timer.isAlive()) {
                DatagramPacket dataPacket = new DatagramPacket(
                        receiveData,
                        receiveData.length);

                multicastSocket.receive(dataPacket);

                String offer = new String (dataPacket.getData()).substring(0,dataPacket.getLength());

                int offerInt = Integer.parseInt(offer);

                System.out.println("received new offer " + offerInt + " from group " + ipServer);

                if (offerMAX<offerInt) offerMAX = offerInt;

                System.out.println("Offer max : " + offerMAX);
            }

            System.out.println("FINISHED");

            String msg = "-1";

            byte[] msgByte = msg.getBytes();

            DatagramPacket ds = new DatagramPacket(
                msgByte,
                msgByte.length,
                address, 
                portServer);
            
            multicastSocket.send(ds);

            System.out.println("L'offerta vincitrice è " + offerMAX);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        receiveUdpMessage();
    }
}
