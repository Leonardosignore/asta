
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientDebug {
    public static void main(String[] args) {
        String host = "localhost";
        int portSocketTcp = 7090;
        int portServer = 5000;
        String ipServer = "224.0.1.1";
        Socket socket;
        try {
            socket = new Socket(host, portSocketTcp);

            System.out.println("Client connected to server");

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            DataInputStream din = new DataInputStream(in);
            DataOutputStream dout = new DataOutputStream(out);

            ObjectInputStream objIn = new ObjectInputStream(din);


            // [0] receive categories
            ArrayList<String> categories = null;
            try {
                System.out.println("waiting categories..");
                categories = (ArrayList<String>) objIn.readObject();
                System.out.println("[0] received categories " + categories.toString());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // [1] send category
            String category = categories.get(0);
            dout.writeUTF(category);
            System.out.println("[1] send category " + category);

            // [2] receive items by category
            ArrayList<Item> items = null;
            try {
                items = (ArrayList<Item>) objIn.readObject();
                System.out.println("[2] items by category " + items.toString());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // [3] send item
            ObjectOutputStream objOut = new ObjectOutputStream(dout);
            Item item = items.get(0);
            objOut.writeObject(item);
            System.out.println("[3] send item " + item);

            // [4] receive ip multicast
            Scanner scanIP = new Scanner(System.in);
            Scanner scanPort = new Scanner(System.in);

            System.out.println("Inserisci l'ip del gruppo");
            String ipGroup = scanIP.nextLine();

            System.out.println("Inserisci la porta del gruppo");
            int portGroup = scanPort.nextInt();

            dout.writeUTF(ipGroup);
            dout.writeInt(portGroup);

            /*
             * UDP SECTION !!!
             */

            // [5] start udpThreadClient
            UdpThreadClient udpThreadClient = new UdpThreadClient(
                portGroup, 
                ipGroup);
            udpThreadClient.start();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
