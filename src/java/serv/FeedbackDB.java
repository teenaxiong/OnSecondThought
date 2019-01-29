/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author teena
 */
public class FeedbackDB {
    
    static int addUserFeedback(int offerTableID, int raterID, int ratedUserID, int ratingForSwapper){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO userFeedback (offerTableID, raterID, ratedUserID, ratingForSwapper) "
                + "VALUES (?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, offerTableID);
            ps.setInt(2, raterID);
            ps.setInt(3, ratedUserID);
            ps.setInt(4, ratingForSwapper);
      
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
        static int updateUserFeedback(int offerTableID, int raterID, int ratingForSwapper){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query01 = "update userFeedback  set userFeedback.ratingForSwapper = ? where "
                + "offerTableID = ? and raterID = ?"; 
        
        try {
            ps = connection.prepareStatement(query01);
            ps.setInt(1, ratingForSwapper);
            ps.setInt(2, offerTableID);
            ps.setInt(3, raterID);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    static int addItemFeedback(int offerTableID, String itemCode, int offerFromUserID, int itemRating){
         ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO itemFeedback (offerTableID, itemCode, offerFromUserID, itemRating) "
                + "VALUES (?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, offerTableID);
            ps.setString(2, itemCode);
            ps.setInt(3, offerFromUserID);
            ps.setInt(4, itemRating);
      
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    static int updateItemFeedback(int offerFromUserID, String itemCode, int itemRating){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query01 = "update itemFeedback  set itemFeedback.itemRating = ? where itemFeedback.itemCode = ? and "
                + "itemFeedback.offerFromUserID=?"; 

        try {
            ps = connection.prepareStatement(query01);
            ps.setInt(1, itemRating);
            ps.setString(2, itemCode);
            ps.setInt(3, offerFromUserID);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    /*
    FeedbackDB – create the class “FeedbackDB” to store and retrieve the feedback users give for items and 
    other users from the database:

addUserFeedback(int offerID, int… userID1, userID2, int rating) – this method adds a feedback that user 
    with userID1 is giving for user with userID2. Both users have completed a swap. Using the offerID we can 
    verify and link this to an offer. The corresponding database table would use the offerID and userID1 as primary keys.
    
addItemFeedback(String itemCode, int userID, int rating) – this method adds a feedback that user with userID 
    is giving for item with itemCode. The corresponding database table would use the userID and itemCode as primary keys.
    */
}

