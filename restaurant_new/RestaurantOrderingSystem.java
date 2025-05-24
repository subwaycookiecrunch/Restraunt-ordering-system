// Enums

enum Category {
    APPETIZER,
    MAIN_COURSE,
    DESSERT,
    BEVERAGE
}

enum OrderStatus {
    CREATED,
    IN_PROGRESS,
    COMPLETED,
    PAID
}

// Utility Classes

class Customer {
    private static int nextId = 1;
    private int id;
    private String name;
    public Customer(String name) {
        this.id = nextId++;
        this.name = name;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    @Override
    public String toString() { return "Customer #" + id + ": " + name; }
}

class Table {
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
    public int getTableNumber() { return tableNumber; }
    public int getCapacity() { return capacity; }
    public boolean isOccupied() { return isOccupied; }
    public Customer getCustomer() { return customer; }
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

class MenuItem {
    private int id;
    private String name;
    private String description;
    private double price;
    private Category category;
    public MenuItem(int id, String name, String description, double price, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public Category getCategory() { return category; }
    @Override
    public String toString() {
        return String.format("%-4d %-20s $%.2f\n     %s", id, name, price, description);
    }
}

// Main Classes

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

class Order {
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
    public int getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public Table getTable() { return table; }
    public List<MenuItem> getOrderedItems() { return orderedItems; }
    public Date getOrderTime() { return orderTime; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}

class Bill {
    private Order order;
    private Date generationTime;
    private boolean isPaid;
    private double totalAmount;
    private double tip;
    private double amountPaid;
    public Bill(Order order) {
        this.order = order;
        this.generationTime = new Date();
        this.isPaid = false;
        this.totalAmount = order.calculateTotal();
        this.tip = 0;
        this.amountPaid = 0;
    }
    public void generateBill() {
        System.out.println("===== BILL =====");
        System.out.println("Order #: " + order.getOrderId());
        System.out.println("Customer: " + order.getCustomer().getName());
        System.out.println("Table #: " + order.getTable().getTableNumber());
        System.out.println("Date: " + generationTime);
        System.out.println("Items:");
        for (MenuItem item : order.getOrderedItems()) {
            System.out.printf("%-25s $%.2f%n", item.getName(), item.getPrice());
        }
        System.out.println("------------------------");
        System.out.printf("Subtotal: $%.2f%n", totalAmount);
        System.out.printf("Tax (8%%): $%.2f%n", calculateTax());
        System.out.printf("Total: $%.2f%n", calculateTotalWithTax());
    }
    public double calculateTax() {
        return totalAmount * 0.08; // Assuming 8% tax
    }
    public double calculateTotalWithTax() {
        return totalAmount + calculateTax();
    }
    public void pay(double amount) {
        if (amount >= calculateTotalWithTax()) {
            this.amountPaid = amount;
            this.tip = amount - calculateTotalWithTax();
            this.isPaid = true;
            order.setStatus(OrderStatus.PAID);
            System.out.println("Payment successful!");
            System.out.printf("Amount paid: $%.2f%n", amount);
            System.out.printf("Change: $%.2f%n", 0.0); // No change in this system
            System.out.printf("Tip: $%.2f%n", tip);
        } else {
            System.out.println("Insufficient payment amount!");
        }
    }
    public Order getOrder() { return order; }
    public Date getGenerationTime() { return generationTime; }
    public boolean isPaid() { return isPaid; }
    public double getTotalAmount() { return totalAmount; }
    public double getTip() { return tip; }
}

class Restaurant {
    private String name;
    private List<Table> tables;
    private Map<Integer, MenuItem> menuItems;
    public Restaurant(String name, int numTables) {
        this.name = name;
        this.tables = new ArrayList<>();
        this.menuItems = new HashMap<>();
        for (int i = 1; i <= numTables; i++) {
            tables.add(new Table(i, 4));
        }
    }
    public void addMenuItem(MenuItem item) {
        menuItems.put(item.getId(), item);
    }
    public MenuItem getMenuItem(int id) {
        return menuItems.get(id);
    }
    public void displayMenu() {
        System.out.println("\n===== " + name + " Menu =====");
        System.out.println("\nAPPETIZERS:");
        for (MenuItem item : menuItems.values()) {
            if (item.getCategory() == Category.APPETIZER) {
                System.out.println(item);
            }
        }
        System.out.println("\nMAIN COURSES:");
        for (MenuItem item : menuItems.values()) {
            if (item.getCategory() == Category.MAIN_COURSE) {
                System.out.println(item);
            }
        }
        System.out.println("\nDESSERTS:");
        for (MenuItem item : menuItems.values()) {
            if (item.getCategory() == Category.DESSERT) {
                System.out.println(item);
            }
        }
        System.out.println("\nBEVERAGES:");
        for (MenuItem item : menuItems.values()) {
            if (item.getCategory() == Category.BEVERAGE) {
                System.out.println(item);
            }
        }
    }
    public int assignTable(Customer customer) {
        for (Table table : tables) {
            if (!table.isOccupied()) {
                table.assignCustomer(customer);
                return table.getTableNumber();
            }
        }
        return -1; // No available tables
    }
    public void freeTable(int tableNumber) {
        if (tableNumber > 0 && tableNumber <= tables.size()) {
            tables.get(tableNumber - 1).freeTable();
        }
    }
    public Table getTable(int tableNumber) {
        if (tableNumber > 0 && tableNumber <= tables.size()) {
            return tables.get(tableNumber - 1);
        }
        return null;
    }
    public void displayTableStatus() {
        System.out.println("\n===== Table Status =====");
        for (Table table : tables) {
            System.out.println(table);
        }
    }
    public String getName() { return name; }
}

// Main class

public class RestaurantOrderingSystem {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant("Gourmet Delight", 10);
        
        // Initialize menu items
        restaurant.addMenuItem(new MenuItem(1, "Caesar Salad", "Fresh romaine lettuce with Caesar dressing", 8.99, Category.APPETIZER));
        restaurant.addMenuItem(new MenuItem(2, "Garlic Bread", "Toasted bread with garlic butter", 4.99, Category.APPETIZER));
        restaurant.addMenuItem(new MenuItem(3, "Grilled Salmon", "Atlantic salmon with lemon butter", 19.99, Category.MAIN_COURSE));
        restaurant.addMenuItem(new MenuItem(4, "Pasta Carbonara", "Creamy pasta with bacon and egg", 14.99, Category.MAIN_COURSE));
        restaurant.addMenuItem(new MenuItem(5, "Steak", "8oz ribeye steak with vegetables", 24.99, Category.MAIN_COURSE));
        restaurant.addMenuItem(new MenuItem(6, "Cheesecake", "New York style cheesecake", 6.99, Category.DESSERT));
        restaurant.addMenuItem(new MenuItem(7, "Ice Cream", "Vanilla ice cream with chocolate sauce", 5.99, Category.DESSERT));
        restaurant.addMenuItem(new MenuItem(8, "Soda", "Cola or lemon-lime", 2.99, Category.BEVERAGE));
        restaurant.addMenuItem(new MenuItem(9, "Coffee", "Freshly brewed coffee", 3.49, Category.BEVERAGE));
        
        // Demo of restaurant system
        System.out.println("=== Restaurant Ordering System Demo ===");
        
        // Display menu
        restaurant.displayMenu();
        
        // Customer arrives
        Customer customer1 = new Customer("John Doe");
        System.out.println("\nCustomer " + customer1.getName() + " has arrived.");
        
        // Assign table
        int tableNumber = restaurant.assignTable(customer1);
        if (tableNumber != -1) {
            System.out.println("Customer assigned to table #" + tableNumber);
            
            // Create order
            Order order = new Order(customer1, restaurant.getTable(tableNumber));
            
            // Add items to order
            order.addItem(restaurant.getMenuItem(1)); // Caesar Salad
            order.addItem(restaurant.getMenuItem(3)); // Grilled Salmon
            order.addItem(restaurant.getMenuItem(9)); // Coffee
            
            System.out.println("\nOrder details:");
            order.displayOrderDetails();
            
            // Generate bill
            Bill bill = new Bill(order);
            System.out.println("\nGenerating bill...");
            bill.generateBill();
            
            // Pay bill - paying 40.0 to cover the bill total and leave a tip
            bill.pay(40.0);
            
            // Free the table
            restaurant.freeTable(tableNumber);
            System.out.println("Table #" + tableNumber + " is now free.");
        } else {
            System.out.println("Sorry, no tables available at the moment.");
        }
    }
} 