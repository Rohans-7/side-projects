class Client{
    public static void main(String[] args){
        System.out.println("Starting client...");
        try{
            Socket socket = new Socket("localhost", 8080);
            PrintWriter pw = new PrintWrite(socket.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            pw.println("PUT 1 100");
            System.out.println("Response: " + br.readLine());

            pw.println("GET 1");
            System.out.println("Response: " + br.readLine());

            pw.println("DELETE 1");
            System.out.println("Response: " + br.readLine());  

            pw.println("EXIT");

            System.out.println("Response: " + br.readLine());
            socket.close();     
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}