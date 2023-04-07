public class Timer extends Thread {
    public void run (){
        System.out.println("Timer started");
        for(int i=0;i<50;i++){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("timer finished");
    }
}
