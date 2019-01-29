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
public class ItemDB {
    
  
        
    /**
     *
     * @return the arraylist 
     */
    public ArrayList<Item> getItemDatabase() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Statement s = null;
        ResultSet rs = null;
        
        String query = "Select * from item";
        ArrayList<Item> itemDatabase = new ArrayList(); 
        
        try { 
            s = connection.createStatement(); 
            rs = s.executeQuery(query);    
            while(rs.next()){
            itemDatabase.add(new Item(rs.getString("itemCode"), rs.getInt("userID"), rs.getString("itemName"),
                    rs.getString("itemCategory"), rs.getString("itemDescription"), rs.getInt("itemRating"),
                    rs.getString("itemImage"), rs.getString("itemAlt"), rs.getString("itemStatus"))); 
            }
          
            return itemDatabase; 
        } catch (SQLException ex) {
           System.out.println(ex);
            return null;
        }finally{
                 DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(s);
            pool.freeConnection(connection);
        }    
        
       
    }   
    
    /**
     *
     * @param itemID
     * @param itemId Item Code for the wanted item
     * @return the information about the item
     */
    public Item getItem(String itemID) {
        
         ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM item "
                + "WHERE itemCode = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, itemID);
            rs = ps.executeQuery();
            Item itemWanted = null;
            if (rs.next()) {
                itemWanted = new Item();
                itemWanted.setItemCode(rs.getString("itemCode"));
                itemWanted.setUserID(rs.getInt("userID"));
                itemWanted.setItemName(rs.getString("itemName"));
                itemWanted.setCategory(rs.getString("itemCategory"));
                itemWanted.setItemDescription(rs.getString("itemDescription"));
                itemWanted.setItemRating(rs.getInt("itemRating"));
                itemWanted.setItemImage(rs.getString("itemImage"));
                itemWanted.setItemAlt(rs.getString("itemAlt"));
                itemWanted.setItemStatus(rs.getString("itemStatus"));       
            }
            return itemWanted;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public void addItem(String itemCode, int userID, String itemName, String category, String itemDescription, int itemRating, 
            String itemImage, String itemAlt, String itemStatus){
        
        Item item = new Item (itemCode, userID, itemName, category, itemDescription, itemRating, itemImage, itemAlt,
                        itemStatus); 
        
        addItem(item); 
        
    }
    
    public int addItem(Item item){
             ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO item "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, item.getItemCode()); 
            ps.setInt(2, item.getUserID());
            ps.setString(3, item.getItemName());
            ps.setString(4, item.getCategory());
            ps.setString(5, item.getItemDescription());
            ps.setInt(6, item.getItemRating());
            ps.setString(7, item.getItemImage());
            ps.setString(8, item.getItemAlt());
            ps.setString(9, item.getItemStatus());
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public ArrayList<Item> availableItems(UserProfile userProfile){
        ArrayList<Item> availableItems = new ArrayList<>();
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int userID = userProfile.getUserProfileID(); 
        String query = "Select * from item WHERE userID != ? and itemActive!='swapped' and itemStatus !='pending'";
        try { 
            ps = connection.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();  
        
            while(rs.next()){
            availableItems.add(new Item(rs.getString("itemCode"), rs.getInt("userID"), rs.getString("itemName"),
                    rs.getString("itemCategory"), rs.getString("itemDescription"), rs.getInt("itemRating"),
                    rs.getString("itemImage"), rs.getString("itemAlt"), rs.getString("itemStatus"))); 
            }
            return availableItems; 
        } catch (SQLException ex) {
           System.out.println(ex);
            return null;
        }finally{
                 DBUtil.closeResultSet(rs);
                DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        } 
    }
        public static int itemInactive(Item inactive){
           ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
         PreparedStatement ps01 = null;
        
        String itemCode = inactive.getItemCode(); 
        String query01 = "update item  set item.itemActive = 'swapped' where item.itemCode =?"; 

        try {
            ps = connection.prepareStatement(query01);
            ps.setString(1, itemCode);
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        
    }
   
}
