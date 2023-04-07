import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UdpThreadClient extends Thread {

    private int portServer;
    private String ipServer;

    public UdpThreadClient(
            int portServer,
            String ipServer) {
        this.portServer = portServer;
        this.ipServer = ipServer;
    }

    private void sendOffer(String offer) {
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
            System.out.println("Send offer to server " + offer + " in group " + ipServer + " " + portServer);
            offer = "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (socket != null) {
            socket.close();
        }
    }

    public void run() {

        System.out.println("new UdpThreadClient started");

        UdpThreadListener udpThreadListener = new UdpThreadListener(ipServer, portServer);
        udpThreadListener.start();

        boolean close = false;
        Scanner s = new Scanner(System.in);
        while (!close){
            String offer = "";

            System.out.println("Write new offer..");
            int offerInt = s.nextInt();

            offer += offerInt;

            int offerInteger = Integer.parseInt(offer);

            System.out.println("Numero offerta: " + offerInteger);

            sendOffer(offer);
        }
        s.close();
    }
}
