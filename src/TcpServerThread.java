import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class TcpServerThread extends Thread {
    private Socket socket;
    private Repository repository;
    private static ArrayList<String> ipAddressesGroup = new ArrayList<String>();

    public TcpServerThread(Socket socket, Repository repository) {
        this.socket = socket;
        this.repository = repository;
    }

    public void run() {

        System.out.println("Thread started!\n");
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            DataInputStream din = new DataInputStream(in);
            DataOutputStream dout = new DataOutputStream(out);

            // [0] send category
            ObjectOutputStream objOut = new ObjectOutputStream(dout);
            ArrayList<String> categories = repository.selectCategories();
            objOut.writeObject(categories);
            System.out.println("[0] send categories" + categories.toString());

            // [1] receive category
            String category = din.readUTF();
            System.out.println("[1] received category " + category);

            // [2] send Item by category
            ArrayList<Item> items = repository.selectItemsByCategory(category);
            if (items.size()>0){
                objOut.writeObject(items);
                System.out.println("[2] send Item by category " + items.toString());
            } else {
                System.out.println("La categoria selezionata non ha items");
                items.add(new Item("default", "default", "default"));
                objOut.writeObject(items);
            }
            
            // [3] receive Item
            Item item = null;
            try {
                ObjectInputStream objIn = new ObjectInputStream(din);
                item = (Item) objIn.readObject();
                System.out.println("[3] received item " + item.toString());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // credenziali accesso al gruppo multicast
            String ipGroup = item.getIpGroup();
            int portGroup = 5000;

            //create UdpServerThread
            if (!ipAddressesGroup.contains(ipGroup)) {
                UdpServerThread udpServerThread = new UdpServerThread(
                        portGroup,
                        ipGroup,
                        item,
                        repository);
                udpServerThread.start();

                ipAddressesGroup.add(ipGroup);
            } else {
                System.out.println("Udp Server just started!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
