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
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author teena
 */
public class UserDB {
    
    ArrayList<User> userDB;
    User user;
    UserProfile userProfileVar; 
    ArrayList<UserItem> userItemDB; 
    
    public UserDB() {
    
    }
    
    public static int addUser(String firstName, String lastName, String email, String address01, String address02, 
            String city, String state, int zipcode, String country){
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO User (userFirstName, userLastName, userEmail,"
                + "userAddress01, userAddress02, userCity, userState, userZipcode, userCountry) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, address01);
            ps.setString(5, address02);
            ps.setString(6, city);
            ps.setString(7, state);
            ps.setInt(8, zipcode);
            ps.setString(9, country);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static int addUser(User user){
              ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "INSERT INTO User (userFirstName, userLastName, userEmail,"
                + "userAddress01, userAddress02, userCity, userState, userZipcode, userCountry) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getUserFirstName());
            ps.setString(2, user.getUserLastName());
            ps.setString(3, user.getUserEmail());
            ps.setString(4, user.getUserAddress01());
            ps.setString(5, user.getUserAddress02());
            ps.setString(6, user.getUserCity());
            ps.setString(7, user.getUserState());
            ps.setInt(8, user.getUserZipcode());
            ps.setString(9, user.getUserCountry());
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    

    //returns a set of all the users in the hardcoded database
    public ArrayList<User> getUsers(){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Statement s = null;
        ResultSet rs = null;
        userDB = new ArrayList<>();
        String query = "Select * from user";
        
        
        try { 
            s = connection.createStatement(); 
            rs = s.executeQuery(query);    
            while(rs.next()){
              
            userDB.add(new User(rs.getInt("userID"), rs.getString("userFirstName"), rs.getString("userLastName"),
                    rs.getString("userEmail"), rs.getString("userAddress01"), rs.getString("userAddress02"), rs.getString("userCity"), rs.getString("userState"),
                    rs.getInt("userZipcode"), rs.getString("userCountry"))); 
            }
            return userDB; 
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(s);
            pool.freeConnection(connection);
        }

    }

    public ArrayList<UserItem> getUserItemDB() {
        
        
        return userItemDB;
    }

    public void setUserItemDB(ArrayList<UserItem> userItemDB) {
        this.userItemDB = userItemDB;
    }
    
    
    public static User getUser(String userEmail){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM User "
                + "WHERE userEmail = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, userEmail);
            rs = ps.executeQuery();
            User userVAR = null;
            if (rs.next()) {
                userVAR = new User();
                userVAR.setUserId(rs.getInt("userID"));
                userVAR.setUserFirstName(rs.getString("userFirstName"));
                userVAR.setUserLastName(rs.getString("userLastName"));
                userVAR.setUserEmail(rs.getString("userEmail"));
                userVAR.setUserAddress01(rs.getString("userAddress01"));
                userVAR.setUserAddress02(rs.getString("userAddress02"));
                userVAR.setUserCity(rs.getString("userCity"));
                userVAR.setUserState(rs.getString("userState"));
                userVAR.setUserZipcode(rs.getInt("userZipcode"));
                userVAR.setUserCountry(rs.getString("userCountry"));
            }
            return userVAR;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static User getUser(int userID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM User "
                + "WHERE userID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            User userVAR = null;
            if (rs.next()) {
                userVAR = new User();
                userVAR.setUserId(rs.getInt("userID"));
                userVAR.setUserFirstName(rs.getString("userFirstName"));
                userVAR.setUserLastName(rs.getString("userLastName"));
                userVAR.setUserEmail(rs.getString("userEmail"));
                userVAR.setUserAddress01(rs.getString("userAddress01"));
                userVAR.setUserAddress02(rs.getString("userAddress02"));
                userVAR.setUserCity(rs.getString("userCity"));
                userVAR.setUserState(rs.getString("userState"));
                userVAR.setUserZipcode(rs.getInt("userZipcode"));
                userVAR.setUserCountry(rs.getString("userCountry"));
            }
            return userVAR;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    //returns a UserProfile object for a specific user
    //the for loop is adding the item to the array in the UserProfile object
    public UserProfile getUserProfile (int userID){
     
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserProfile a = new UserProfile(); 
        String query = 
"SELECT offertable.offerTableID\n" +
"	,offertable.offerFromUserID AS userid\n" +
"    ,offertable.offerFromUserID\n" +
"    ,offertable.itemcode AS UserItemCode\n" +
"    ,offertable.offertoUserID\n" +
"    ,userItem.itemRating AS userItemRating\n" +
"    ,offertable.offerTableStatus AS itemStatus\n" +
"    ,offertable.offerTableSwapItem AS swapItemCode\n" +
"    ,swapItem.itemRating AS swapItemRating\n" +
"    ,userfeedback.ratingForSwapper as ratingForSwapper\n" +
"FROM offertable \n" +
"	LEFT JOIN itemfeedback AS userItem\n" +
"		ON offertable.offerFromUserID = userItem.offerFromUserID\n" +
"		AND offertable.itemcode = userItem.itemcode\n" +
"	LEFT JOIN itemfeedback AS swapItem\n" +
"		ON offertable.offerFromUserID = swapItem.offerFromUserID\n" +
"		AND offertable.OfferTableSwapItem = swapItem.itemcode\n" +
"	LEFT JOIN userFeedback\n" +
"		ON offertable.offertableid = userfeedback.offertableid\n"
                + "and offertable.offerFromUserID = userfeedback.raterID" +
"	LEFT JOIN item ON offerTable.itemCode = item.itemCode\n" +
"WHERE offertable.offerfromuserid = ? AND offertable.offerTableStatus != 'withdrawn'\n" +
"AND item.itemActive != 'inactive'\n" +
"\n" +
"UNION\n" +

"\n" +
"SELECT offertable.offerTableId\n" +
"	,offertable.offerToUserID\n" +
"    ,offertable.offerFromUserID\n" +
"    ,offertable.offerTableSwapItem\n" +
"    ,offertable.offerToUserID\n" +
"    ,userItem.itemRating\n" +
"    ,offertable.offerTableStatus\n" +
"    ,offertable.itemCode\n" +
"    ,CASE WHEN offertable.offerTableStatus='swapped' THEN swapItem.itemRating ELSE NULL END\n" +
"    ,userfeedback.ratingForSwapper as ratingForSwapper " +
"FROM offerTable \n" +
"	LEFT JOIN itemfeedback AS userItem\n" +
"		ON offertable.offerTableSwapItem = userItem.itemCode\n" +
"        AND offertable.offertableid = userItem.offertableid\n"
                + "and offertable.offerToUserID = userItem.offerFromUserID " +
"	LEFT JOIN itemfeedback AS swapItem\n" +
"		ON offertable.itemCode = swapItem.itemCode\n" +
"        AND offertable.offertableid = swapItem.offertableid\n"
                + "and offertable.offerToUserID = swapItem.offerFromUserID " +
"	LEFT JOIN userFeedback\n" +
"		ON offertable.offertableid = userfeedback.offertableid\n"
                + "and offertable.offerToUserID = userfeedback.raterID" +
"	LEFT JOIN item ON offerTable.itemCode = item.itemCode\n" +
"WHERE offerTable.offerTableStatus != 'withdrawn' \n" +
"	AND offertable.offertouserid = ? AND item.itemActive != 'inactive'\n" +
"    \n" +
"UNION\n" +

"    SELECT NULL AS offerTableID\n" +
"		,UserID\n" +
"        ,userID AS offerFromUserID \n" +
"        ,item.itemCode AS userItemCode\n" +
"        ,NULL AS offerToUserID\n" +
"        ,itemfeedback.itemRating AS userItemRating\n" +
"        ,itemStatus\n" +
"        ,NULL AS offerTableSwapItem \n" +
"        ,NULL AS swapItemRating\n" +
"        ,NULL AS offerTableRatingForSwapper \n" +
"FROM item \n" +
"LEFT JOIN itemfeedback \n" +
"	ON item.itemCode = itemfeedback.itemCode\n" +
"WHERE item.itemStatus = 'available' AND item.itemActive != 'inactive'\n" +
"	AND userID=? order by itemstatus\n" ; 




        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userID);
            ps.setInt(2, userID);
            ps.setInt(3, userID);
            rs = ps.executeQuery();
             ItemDB var=new ItemDB();
   
            Item userItem; 
            Item swapItem;
            while (rs.next()) {
                int offerTableID = rs.getInt("offerTableID");
                if(rs.wasNull()){
                    offerTableID=0; 
                }
                
                userItem = var.getItem(rs.getString("userItemCode"));
                swapItem = var.getItem(rs.getString("swapItemCode")); 
                if(rs.wasNull()){
                    swapItem = null; 
                }
                
                int offerToUserID = rs.getInt("offerToUserID"); 
                if(rs.wasNull()){
                    offerToUserID = 0; 
                }
                
                 int userItemRating = rs.getInt("userItemRating"); 
                if(rs.wasNull()){
                    userItemRating = 0; 
                }
                
                int swapItemRating = rs.getInt("swapItemRating"); 
                if(rs.wasNull()){
                    swapItemRating = 0; 
                }
                
                int ratingForSwapper = rs.getInt("ratingForSwapper");
                if(rs.wasNull()){
                    ratingForSwapper=0; 
                }
            
                UserItem b = new UserItem(offerTableID, rs.getInt("userID"),rs.getInt("offerFromUserID"), userItem , offerToUserID,
                    userItemRating, rs.getString("itemStatus"), swapItem,
                    swapItemRating, ratingForSwapper); 
                
                a.addUserItem(b);                
            }
            return a;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    
}
