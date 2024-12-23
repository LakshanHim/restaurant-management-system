package lk.ACPT.dto;

public class OrderDetailDto {
    private String itemsName;
    private int qty;
    private double unitPrice;
    private double totalPrice;

    public OrderDetailDto(String itemsName, double unitPrice) {
        this.itemsName = itemsName;
        this.unitPrice = unitPrice;
    }

    public OrderDetailDto() {

    }

    public OrderDetailDto(double totalPrice) {
    }

    public OrderDetailDto(String itemName, int quantity, double total) {
        this.itemsName = itemName;
        this.qty = quantity;
        this.totalPrice = total;
    }

    public String getItemsName() {
        return itemsName;
    }

    public void setItemsName(String itemsName) {
        this.itemsName = itemsName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
