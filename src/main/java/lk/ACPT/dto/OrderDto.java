package lk.ACPT.dto;

import java.util.ArrayList;

public class OrderDto {
    private String orderDate;
    String orderTime;
    private double subTotal;
    private ArrayList<OrderDetailDto> orderDetail;

    public OrderDto(String orderDate, String orderTime, double subTotal, ArrayList<OrderDetailDto> orderDetail) {
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.subTotal = subTotal;
        this.orderDetail = orderDetail;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }



    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public ArrayList<OrderDetailDto> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(ArrayList<OrderDetailDto> orderDetail) {
        this.orderDetail = orderDetail;
    }
}
