import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
public class Server {
    private static ConcurrentHashMap<Integer, Integer> keyValueStore = new ConcurrentHashMap<>();
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
    public static void handleClient(Socket socket){
        try{
            BufferedReader data = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            String line;
            while((line = data.readLine()) != null){
                String parts[] = line.split(" ");
                String command = parts[0];
                switch(command.toUpperCase()){
                    case "PUT":
                        int key = Integer.parseInt(parts[1]);
                        int value = Integer.parseInt(parts[2]);
                        keyValueStore.put(key, value);
                        pw.println("Stored");
                        break;
                    case "GET":
                        key = Integer.parseInt(parts[1]);
                        Integer val = keyValueStore.get(key);
                        pw.println(val == null ? "NULL" : val);
                        break;
                }
                pw.println(response);
            }
        }
    }

}