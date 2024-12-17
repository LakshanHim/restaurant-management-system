package lk.ACPT.tm;

public class OrderTM {
    private String itemName;
    private int qty;
    private double unitPrice;
    private double totalPrice;

    public OrderTM(double totalPrice, double unitPrice, int qty, String itemName) {
        this.totalPrice = totalPrice;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
