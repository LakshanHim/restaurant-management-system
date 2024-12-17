package lk.ACPT.model;

import lk.ACPT.db.DBConnection;
import lk.ACPT.dto.OrderDetailDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchModel {

    public static OrderDetailDto addCart(int id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDBConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from menu where id=?");
        preparedStatement.setInt(1, id);


        ResultSet resultSet = preparedStatement.executeQuery();

        OrderDetailDto detail = new OrderDetailDto();
        if(resultSet.next()) {
            detail.setItemsName(resultSet.getString(2));
            detail.setUnitPrice(resultSet.getDouble(3));
        }
        return detail;


    }

}
