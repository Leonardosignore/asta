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

public class buyer {
    @SuppressWarnings({ "unchecked" })
    public static void main(String[] args) {
        String host = "localhost";
        int portSocketTcp = 7090;
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
            Scanner scanner = new Scanner(System.in);
            String category = null;
            while (!categories.contains(category)) {
                System.out.println("Type category..");
                category = scanner.nextLine();
            }
            dout.writeUTF(category);
            System.out.println("[1] send category " + category);

            // [2] receive items by category
            ArrayList<Item> items = null;
            try {
                items = (ArrayList<Item>) objIn.readObject();
                if (items.get(0).getName() == "default")
                    System.out.println("items non disponibili");
                else {
                    System.out.println("[2] items by category \n");
                    int cont = 0;
                    for (Item item : items) {
                        System.out.println(cont + ") " + item.toString());
                        cont++;
                    }
                    System.out.println();
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // [3] send item selected
            ObjectOutputStream objOut = new ObjectOutputStream(dout);

            Scanner scan = new Scanner(System.in);
            Item item = null;
            int index = items.size();
            while (index >= items.size()) {
                System.out.println("Inserisci la posizione dell'item");
                index = scan.nextInt();
            }
            item = items.get(index);
            objOut.writeObject(item);
            System.out.println("[3] send item " + item.getName());

            String ipGroup = item.getIpGroup();
            int portGroup = 5000;

            // [5] start udpThreadClient
            UdpClientThread udpThreadClient = new UdpClientThread(
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
