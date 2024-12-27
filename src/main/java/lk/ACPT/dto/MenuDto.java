package lk.ACPT.dto;
public class MenuDto {
    private String name;
    private double unitPrice;
    private String description;
    private String imagePath;

    public MenuDto(String name, double unitPrice, String description, String imagePath) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.description = description;
        this.imagePath = imagePath;
    }

    public MenuDto() {

    }

    public MenuDto(String itemName, double updatedPrice, String updatedDesc) {
        this.name = itemName;
        this.unitPrice = updatedPrice;
        this.description = updatedDesc;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
