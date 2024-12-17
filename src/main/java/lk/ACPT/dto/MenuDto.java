package lk.ACPT.dto;

public class MenuDto {
    private int id;
    private String name;
    private double unitPrice;

    public MenuDto(String name, double unitPrice) {
        this.unitPrice = unitPrice;
        this.name = name;
    }

    public MenuDto(int id, String name, double unitPrice) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public MenuDto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
