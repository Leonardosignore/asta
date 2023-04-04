import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TcpThread extends Thread{
    private Socket socket;

    public TcpThread (Socket socket){
        this.socket = socket;
    }

    public void run (){
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            DataInputStream din = new DataInputStream(in);
            DataOutputStream dout = new DataOutputStream(out);
            ObjectOutputStream objOut = new ObjectOutputStream(dout);

            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
