package lk.ACPT.tm;

public class MenuTM {
    private int id;
    private String itemName;
    private double unitPrice;

    public MenuTM(int id, String itemName, double unitPrice) {
        this.id = id;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
