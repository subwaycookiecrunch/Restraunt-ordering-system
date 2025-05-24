import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private static int nextOrderId = 1000;
    
    private int orderId;
    private Customer customer;
    private Table table;
    private List<MenuItem> orderedItems;
    private Date orderTime;
    private OrderStatus status;
    
    public Order(Customer customer, Table table) {
        this.orderId = nextOrderId++;
        this.customer = customer;
        this.table = table;
        this.orderedItems = new ArrayList<>();
        this.orderTime = new Date();
        this.status = OrderStatus.CREATED;
    }
    
    public void addItem(MenuItem item) {
        orderedItems.add(item);
        status = OrderStatus.IN_PROGRESS;
    }
    
    public void removeItem(MenuItem item) {
        orderedItems.remove(item);
    }
    
    public double calculateTotal() {
        double total = 0;
        for (MenuItem item : orderedItems) {
            total += item.getPrice();
        }
        return total;
    }
    
    public void completeOrder() {
        status = OrderStatus.COMPLETED;
    }
    
    public void displayOrderDetails() {
        System.out.println("Order #" + orderId);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Table: " + table.getTableNumber());
        System.out.println("Order Time: " + orderTime);
        System.out.println("Status: " + status);
        System.out.println("Items:");
        
        for (MenuItem item : orderedItems) {
            System.out.println("- " + item.getName() + " ($" + item.getPrice() + ")");
        }
        
        System.out.println("Total: $" + String.format("%.2f", calculateTotal()));
    }
    
    public int getOrderId() {
        return orderId;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public Table getTable() {
        return table;
    }
    
    public List<MenuItem> getOrderedItems() {
        return orderedItems;
    }
    
    public Date getOrderTime() {
        return orderTime;
    }
    
    public OrderStatus getStatus() {
        return status;
    }
    
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
} 