class Main{
    public static void main(String[] args) {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

        while(true){
            System.out.println("Enter key and Value to insert into map:");
            Integer key, value;
            try(Scanner sc = new Scanner(System.int)){
                key = sc.nextInt();
                value = sc.nextInt();
                map.put(key, value);
            }
        }
        System.out.println("Hello World!!!");
    }
}