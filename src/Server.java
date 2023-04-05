import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket socket = null;
        int port = 7090;

        String url = "jdbc:mysql://localhost:3306/asta";
        String username = "root";
        String password = "leopoldodinarnia";

        Repository repository = new Repository(
            url, 
            username,
            password);

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started!\n");
            while(true){
                socket = serverSocket.accept();

                System.out.println("Client accepted!\n");

                TcpThread tcp = new TcpThread(socket,repository);
                tcp.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
