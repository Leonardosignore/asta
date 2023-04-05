
public class UdpThread extends Thread {
    
    private String ip;

    public UdpThread (String ip){
        this.ip = ip;
    }

    public void run (){
        System.out.println("Thread udp started!");
    }

}
