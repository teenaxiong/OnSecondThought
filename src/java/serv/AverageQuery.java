/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author teena
 */
public class AverageQuery {
    

    public static int getAverageUserRating(int ratedUserID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "select avg(ratingForSwapper) from userFeedback where ratedUserID = ?"; 
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, ratedUserID);
            rs = ps.executeQuery();
            
            int rating = 0; 
            if (rs.next()) {
                rating = rs.getInt("avg(ratingForSwapper)"); 
            }
            return rating;
        } catch (SQLException e) {
            System.out.println(e);
            return 0; 
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static int getAverageItemRating(String itemCode){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "select avg(itemRating) from itemFeedback where itemCode = ?";   
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, itemCode);
            rs = ps.executeQuery();
            
            int rating = 0; 
            if (rs.next()) {
                rating = rs.getInt("avg(itemRating)"); 
            }
            return rating;
        } catch (SQLException e) {
            System.out.println(e);
            return 0; 
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
}
