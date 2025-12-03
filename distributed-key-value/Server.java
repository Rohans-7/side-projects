import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Server {
    private static final ConcurrentHashMap<Integer, Integer> keyValueStore = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        System.out.println("Server is running...");

        ExecutorService executor = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Listening on port 5000");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());
                executor.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    public static void handleClient(Socket socket) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    pw.println("EMPTY_COMMAND");
                    continue;
                }

                String[] parts = line.split("\\s+");
                String command = parts[0].toUpperCase();

                try {
                    switch (command) {
                        case "PUT":
                            if (parts.length < 3) {
                                pw.println("ERROR: PUT requires key and value");
                                break;
                            }
                            int putKey = Integer.parseInt(parts[1]);
                            int putValue = Integer.parseInt(parts[2]);
                            keyValueStore.put(putKey, putValue);
                            pw.println("OK");
                            break;

                        case "GET":
                            if (parts.length < 2) {
                                pw.println("ERROR: GET requires key");
                                break;
                            }
                            int getKey = Integer.parseInt(parts[1]);
                            Integer val = keyValueStore.get(getKey);
                            pw.println(val == null ? "NULL" : val.toString());
                            break;

                        case "DELETE":
                            if (parts.length < 2) {
                                pw.println("ERROR: DELETE requires key");
                                break;
                            }
                            int delKey = Integer.parseInt(parts[1]);
                            keyValueStore.remove(delKey);
                            pw.println("OK");
                            break;

                        case "EXIT":
                            pw.println("BYE");
                            socket.close();
                            return;

                        default:
                            pw.println("ERROR: Unknown command");
                            break;
                    }
                } catch (NumberFormatException nfe) {
                    pw.println("ERROR: key/value must be integers");
                }
            }
        } catch (IOException e) {
            System.err.println("Client handler error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (!socket.isClosed()) socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
