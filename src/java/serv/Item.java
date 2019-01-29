package serv;


import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author teena
 * 
 * This is a my JavaBeans for the Model. 
 * 9/30/2018
 * 
 */
public class Item implements Serializable{
    
    String itemCode;
    String itemName;
    String category;
    String itemDescription;
    int itemRating;
    String itemImage; 
    String itemAlt; 
    int userID; 
    String itemStatus; 

    public Item() {
        
    } 

    public Item(String itemCode, int userID, String itemName, String category, String itemDescription, int itemRating, 
            String itemImage, String itemAlt, String itemStatus) {
        this.userID = userID; 
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.category = category;
        this.itemDescription = itemDescription;
        this.itemRating = itemRating;
        this.itemImage = itemImage;
        this.itemAlt = itemAlt; 
        this.itemStatus = itemStatus; 
    }
    

    
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getItemRating() {
        return itemRating;
    }

    public void setItemRating(int itemRating) {
        this.itemRating = itemRating;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemAlt() {
        return itemAlt;
    }

    public void setItemAlt(String itemAlt) {
        this.itemAlt = itemAlt;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }
    
    
 
}



