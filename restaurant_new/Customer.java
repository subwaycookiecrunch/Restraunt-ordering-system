public class Customer {
    private static int nextId = 1;
    private int id;
    private String name;
    
    public Customer(String name) {
        this.id = nextId++;
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "Customer #" + id + ": " + name;
    }
} 