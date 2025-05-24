import java.util.Date;

public class Bill {
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
    
    public Order getOrder() {
        return order;
    }
    
    public Date getGenerationTime() {
        return generationTime;
    }
    
    public boolean isPaid() {
        return isPaid;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public double getTip() {
        return tip;
    }
} 