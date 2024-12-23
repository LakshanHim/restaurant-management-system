package lk.ACPT.model;

import lk.ACPT.db.DBConnection;
import lk.ACPT.dto.OrderDetailDto;
import lk.ACPT.dto.OrderDto;

import java.sql.*;

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

}
