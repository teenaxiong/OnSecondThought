/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serv;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author teena
 */
public class UserProfile implements Serializable{
    
    
    private int userProfileID;
    ArrayList<UserItem> userProfileArray;

    
    public UserProfile() {
        this.userProfileArray = new ArrayList<UserItem>();
    }
    
    public int getUserProfileID() {
        return userProfileID;
    }

    public void setUserProfileID(int userProfileID) {
        this.userProfileID = userProfileID;
    }

    public ArrayList<UserItem> getUserProfileArray() {
        return userProfileArray;
    }

    public void setUserProfileArray(ArrayList<UserItem> userProfileArray) {
        this.userProfileArray = userProfileArray;
    }
    
  
    
    public void addUserItem(UserItem item) {
        this.userProfileArray.add(item);
        this.userProfileID = item.getUserID(); 
    }
    
    //search the array for the item and remove it
    public int removeUserItem(Item removeItem) {
         ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
         PreparedStatement ps01 = null;
        
        String itemCode = removeItem.getItemCode(); 
        String query01 = "update item  set item.itemActive = 'inactive', "
                + "item.itemStatus = 'withdrawn' where item.itemCode =?"; 
        String query02 = "update offertable set offertable.offertablestatus='withdrawn' "
                + "where itemCode=? or offerTableSwapItem=?";

        try {
            ps = connection.prepareStatement(query01);
            ps01 = connection.prepareStatement(query02);
            ps.setString(1, itemCode);
            ps01.setString(1, itemCode);
            ps01.setString(2, itemCode);
            ps.executeUpdate();
            return ps01.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closePreparedStatement(ps01);
            pool.freeConnection(connection);
        }
    }
    
    //returns a list of UserItems associated with the user id by going through the array and comparing with user id
    public ArrayList<UserItem> getUserItem(int userID){
        ArrayList<UserItem> individualUserProfileArray = new ArrayList<>();        
        
        for (int x = 0; x < userProfileArray.size(); x++) {
            if (userProfileArray.get(x).userID == userID) {
                individualUserProfileArray.add(userProfileArray.get(x));
            }
        }
        return individualUserProfileArray;        
    }
    
    public void emptyProfile(){
        userProfileArray.clear(); 
    }
    
    
    
    
}



