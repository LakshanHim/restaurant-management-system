package lk.ACPT.model;

import lk.ACPT.db.DBConnection;
import lk.ACPT.dto.OrderDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderModel {

    public static void placeOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDBConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into orders(orderDate,amount) values(?,?)", Statement.RETURN_GENERATED_KEYS);


    }

}
