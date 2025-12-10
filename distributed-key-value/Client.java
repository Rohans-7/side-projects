import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        System.out.println("===== Distributed Key-Value Store Client =====");

        String host = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(host, port);
             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server at " + host + ":" + port);
            printHelp();

            while (true) {
                System.out.print("\nEnter command: ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("EXIT")) {
                    pw.println("EXIT");
                    System.out.println("Server: " + br.readLine());
                    break;
                }

                if (input.equalsIgnoreCase("HELP")) {
                    printHelp();
                    continue;
                }

                pw.println(input);
                String response = br.readLine();

                if (response == null) {
                    System.out.println("Server disconnected.");
                    break;
                }

                System.out.println("Server: " + response);
            }

        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\nClient terminated.");
    }

    private static void printHelp() {
        System.out.println("\nAvailable Commands:");
        System.out.println("  PUT <key> <value>   - Insert or update a key");
        System.out.println("  GET <key>           - Retrieve a value");
        System.out.println("  DELETE <key>        - Delete a key");
        System.out.println("  HELP                - Show this help message");
        System.out.println("  EXIT                - Close connection");
        System.out.println("================================================");
    }
}
