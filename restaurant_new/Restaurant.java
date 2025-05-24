import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant {
    private String name;
    private List<Table> tables;
    private Map<Integer, MenuItem> menuItems;
    
    public Restaurant(String name, int numTables) {
        this.name = name;
        this.tables = new ArrayList<>();
        this.menuItems = new HashMap<>();
        
        // Initialize tables with default capacity of 4
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
    
    public String getName() {
        return name;
    }
} 