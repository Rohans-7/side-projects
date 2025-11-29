import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
public class Server {

    public static void main(String[] args){
        System.out.println("Server is running...");
        ServerSocket serverSocket = new ServerSocket(5000);
        ExecutorService service = Executors.newFixedThreadPool(10);
        System.out.println("KV store started on port 5000");
        while(true){
            Socket clientSocket = serverSocket.accept();
            service.submit(()->{
                handleClient(clientSocket);
            })
        }
    }
}