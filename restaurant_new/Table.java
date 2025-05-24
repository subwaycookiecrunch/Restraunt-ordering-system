public class Table {
    private int tableNumber;
    private int capacity;
    private boolean isOccupied;
    private Customer customer;
    
    public Table(int tableNumber, int capacity) {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.isOccupied = false;
        this.customer = null;
    }
    
    public int getTableNumber() {
        return tableNumber;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public boolean isOccupied() {
        return isOccupied;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void assignCustomer(Customer customer) {
        this.customer = customer;
        this.isOccupied = true;
    }
    
    public void freeTable() {
        this.customer = null;
        this.isOccupied = false;
    }
    
    @Override
    public String toString() {
        return "Table #" + tableNumber + " (Capacity: " + capacity + ", " + 
               (isOccupied ? "Occupied by " + customer.getName() : "Available") + ")";
    }
} 