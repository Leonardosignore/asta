
public class UdpThread extends Thread {
    
    private int portServer;
    private int portClient;
    private String ipClient = "224.0.0.1"; 
    private String ipServer = "224.0.1.1";

    public UdpThread (int portServer, int portClient){
        this.portServer = portServer;
        this.portClient = portClient;
    }

    public void run (){
        System.out.println("Thread udp started!");

        UdpServerThread udpServerListener = new UdpServerThread(
            ipClient,
            portServer,
            ipServer, 
            portClient);
        udpServerListener.start();
    }

}
