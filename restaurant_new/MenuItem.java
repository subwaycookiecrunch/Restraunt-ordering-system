public class MenuItem {
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
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public double getPrice() {
        return price;
    }
    
    public Category getCategory() {
        return category;
    }
    
    @Override
    public String toString() {
        return String.format("%-4d %-20s $%.2f\n     %s", id, name, price, description);
    }
} 