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

public class ClientDebug {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 7090;
        Socket socket;
        try {
            socket = new Socket(host, port);

            System.out.println("Client connected to server");

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            DataInputStream din = new DataInputStream(in);
            DataOutputStream dout = new DataOutputStream(out);

            // hello server
            // System.out.println("Invio messaggio..");
            // dout.writeUTF("Hello Server");
            // String response = din.readUTF();
            // System.out.println("Server response " + response);

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
            String ip = din.readUTF();
            System.out.println("[4] received ip " + ip);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
