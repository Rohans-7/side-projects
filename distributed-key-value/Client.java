import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        System.out.println("Starting client...");

        String host = "localhost";
        int port = 5000;
        try (Socket socket = new Socket(host, port);
             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            pw.println("PUT 1 100");
            System.out.println("Response: " + br.readLine());

            pw.println("GET 1");
            System.out.println("Response: " + br.readLine());

            pw.println("DELETE 1");
            System.out.println("Response: " + br.readLine());

            pw.println("EXIT");
            System.out.println("Response: " + br.readLine());

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Client finished.");
    }
}
