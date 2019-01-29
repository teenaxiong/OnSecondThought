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
public class OfferDB {
    
    static int addOffer(int offerFromUserID, String itemCodeOwn, int offerToUserID, String itemStatus,
            String itemCodeWant){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps01 = null;
       PreparedStatement ps02 = null;
       PreparedStatement ps03 = null;
       
        String query01 = "INSERT INTO offertable (offertable.offerFromUserID, offertable.itemCode, "
                + "offertable.offerToUserID, offertable.offerTableStatus, offertable.offerTableSwapItem) values"
                + " (?, ?, ?, ?, ?)"; 
        String query02 = "update item set item.itemStatus=? where item.itemCode=?"; 
        String query03 = "update item set item.itemStatus=? where item.itemCode=?"; 
        try {
            ps01 = connection.prepareStatement(query01);
            ps01.setInt(1, offerFromUserID);
            ps01.setString(2, itemCodeOwn);
            ps01.setInt(3, offerToUserID);
            ps01.setString(4, itemStatus);
            ps01.setString(5, itemCodeWant);
            
            ps02 = connection.prepareStatement(query02);
            ps02.setString(1, itemStatus);
            ps02.setString(2, itemCodeOwn);
            
            ps03 = connection.prepareStatement(query03);
            ps03.setString(1, itemStatus);
            ps03.setString(2, itemCodeWant);
        
            ps01.executeUpdate();
            ps02.executeUpdate();
            return ps03.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps01);
            DBUtil.closePreparedStatement(ps02);
            DBUtil.closePreparedStatement(ps03);
            pool.freeConnection(connection);
        }
    }
    
    static int updateOffer(int offerTableID, String itemCode, String swapItem, String status){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps01 = null;
        PreparedStatement ps02 = null;
        PreparedStatement ps03 = null;
        PreparedStatement ps04 = null;
        
        String acceptOrWithdrawn = null;
        if(status.equals("swapped")){
            acceptOrWithdrawn = "swapped"; 
        }else  acceptOrWithdrawn = "withdrawn"; 

        
        String query01 = "update item  set item.itemStatus = ? where item.itemCode = ?"; 
        String query02 = "update item set item.itemStatus=? where item.itemCode=?"; 
        String query03 ="update offertable set offertable.offerTableStatus = ? where offertable.itemCode = ?"
                + "and offerTable.offerTableID = ?"; 
        String query04="update offertable set offertable.offerTableStatus = ? where offertable.itemCode = ?"
                + "and offerTable.offerTableID = ?"; 
        
        try {
            ps01 = connection.prepareStatement(query01);
            ps01.setString(1, status);
            ps01.setString(2, itemCode);
            ps02 = connection.prepareStatement(query02);
            ps02.setString(1, status);
            ps02.setString(2, swapItem);
            ps03 = connection.prepareStatement(query03);
              ps03.setString(1, acceptOrWithdrawn);
              ps03.setString(2, itemCode);
              ps03.setInt(3, offerTableID);
            ps04 = connection.prepareStatement(query04);
            ps04.setString(1, acceptOrWithdrawn);
            ps04.setString(2, swapItem);
            ps04.setInt(3, offerTableID);
            
            ps01.executeUpdate();
            ps02.executeUpdate();ps03.executeUpdate();
            return ps04.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps01);
            DBUtil.closePreparedStatement(ps02);
            DBUtil.closePreparedStatement(ps03);
                  DBUtil.closePreparedStatement(ps04);
            pool.freeConnection(connection);
        }
    }
    
    
  /*  
    addOffer(String/int/… userID, String itemCodeOwn, String  itemCodeWant, String/int/… itemStatus) – 
    this method adds an offer to the database. The userID here refers to the user that is making the offer
    and itemCodeOwn is the itemCode that this user owns and itemCodeWant is the item code they would like to get.

    
    updateOffer(String/int/… offerID, String/int/… itemStatus) – this method updates the status of the offer in the database.

*/
}
