import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket socket = null;
        int port = 6000;

        try {
            serverSocket = new ServerSocket(port);
            while(true){
                socket = serverSocket.accept();
                TcpThread tcp = new TcpThread(socket);
                tcp.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
