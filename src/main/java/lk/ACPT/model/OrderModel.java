package lk.ACPT.model;

import javafx.scene.text.Text;
import lk.ACPT.db.DBConnection;
import lk.ACPT.dto.OrderDetailDto;
import lk.ACPT.dto.OrderDto;

import java.sql.*;
import java.util.ArrayList;

public class OrderModel {

    public static boolean placeOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException {

        try {
            Connection connection = DBConnection.getDBConnection().getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("insert into orders(orderDate,orderTime,totalAmount ) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, orderDto.getOrderDate());
            preparedStatement.setObject(2, orderDto.getOrderTime());
            preparedStatement.setObject(3, orderDto.getSubTotal());

            int i = preparedStatement.executeUpdate();

            if (i > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);

                    for (OrderDetailDto dto : orderDto.getOrderDetail()) {
                        PreparedStatement preparedStatement1 = connection.prepareStatement("insert into order_details(oid,itemname,qty,price) values(?,?,?,?)");
                        preparedStatement1.setObject(1, id);
                        preparedStatement1.setObject(2, dto.getItemsName());
                        preparedStatement1.setObject(3, dto.getQty());
                        preparedStatement1.setObject(4, dto.getTotalPrice());

                        int i1 = preparedStatement1.executeUpdate();
                        if (i1 <= 0) {
                            connection.rollback();
                            connection.setAutoCommit(true);
                            return false;
                        }

                    }
                }
                connection.commit();
                connection.setAutoCommit(true);
                return true;

            } else {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static int recipt() {
        int lastOrderNumber = 0;  // Default value if no orders exist

        String query = "SELECT id FROM orders ORDER BY id DESC LIMIT 1"; // SQL to get the last order number

        try { // Execute the query
            Connection conn = DBConnection.getDBConnection().getConnection(); // Get connection to the DB
            Statement stmt = conn.createStatement(); // Create a statement
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) { // If the result set has a record
                lastOrderNumber = rs.getInt("id"); // Fetch the last order number
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());  // Print any SQL exception that occurs
        }
        return lastOrderNumber;
    }

    public static OrderDto orderSearch(int id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDBConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from orders where id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        OrderDto odr = new OrderDto();
        if (resultSet.next()) {
            odr.setOrderDate(resultSet.getString(2));
            odr.setOrderTime(resultSet.getString(3));
            odr.setSubTotal(resultSet.getDouble(4));
        }
        return odr;

    }

    public static ArrayList<OrderDetailDto> orderDetailSearch(int id) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailDto> orderDetailDtos = new ArrayList<>();

        // Establishing database connection
        Connection connection = DBConnection.getDBConnection().getConnection();

        // SQL query to fetch item names for the given order ID
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT itemname FROM order_details WHERE oid = ?");
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        // Fetching item names from the ResultSet
        while (resultSet.next()) {
            // Using column name for clarity
            String itemName = resultSet.getString("itemname");
            orderDetailDtos.add(new OrderDetailDto(itemName)); // Assuming OrderDetailDto has a matching constructor
        }

        // Logging retrieved item names for debugging
        for (OrderDetailDto item : orderDetailDtos) {
            System.out.println(item.getItemsName());
        }

        return orderDetailDtos;
    }




}
