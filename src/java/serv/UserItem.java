/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serv;

import java.io.Serializable;

/**
 *
 * @author teena
 */
public class UserItem implements Serializable{
    int userID; 
    private Item userItem;
    private int offerToUserID; 
    private int userRating;
    private String itemStatus;
    private Item swapItem;
    private int userRatingForSwappedItem; 
    private int userRatingForSwapper; 
    private int offerFromUserID; 
    private int offerTableID; 

    
    public UserItem(int offerTableID, int userID, int offerFromUserID, Item userItem, int offerToUserID, int userRating, String itemStatus, Item swapItem, 
            int userRatingForSwappedItem, int userRatingForSwapper) {
        this.offerTableID = offerTableID; 
        this.userID = userID;
        this.offerFromUserID = offerFromUserID; 
        this.userItem = userItem;
        this.offerToUserID = offerToUserID; 
        this.userRating = userRating;
        this.itemStatus = itemStatus;
        this.swapItem = swapItem;
        this.userRatingForSwappedItem = userRatingForSwappedItem;
        this.userRatingForSwapper = userRatingForSwapper;
    }
    
    
    public UserItem() {
    }

    public int getOfferTableID() {
        return offerTableID;
    }

    public void setOfferTableID(int offerTableID) {
        this.offerTableID = offerTableID;
    }
    
    
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getOfferFromUserID() {
        return offerFromUserID;
    }

    public void setOfferFromUserID(int offerFromUserID) {
        this.offerFromUserID = offerFromUserID;
    }

    
    
    public Item getUserItem() {
        return userItem;
    }

    public void setUserItem(Item userItem) {
        this.userItem = userItem;
    }
    public int getOfferToUserID() {
        return offerToUserID;
    }

    public void setOfferToUserID(int offerToUserID) {
        this.offerToUserID = offerToUserID;
    }
    



    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    
    
    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Item getSwapItem() {
        return swapItem;
    }

    public void setSwapItem(Item swapItem) {
        this.swapItem = swapItem;
    }

    public int getUserRatingForSwappedItem() {
        return userRatingForSwappedItem;
    }

    public void setUserRatingForSwappedItem(int userRatingForSwappedItem) {
        this.userRatingForSwappedItem = userRatingForSwappedItem;
    }

    public int getUserRatingForSwapper() {
        return userRatingForSwapper;
    }

    public void setUserRatingForSwapper(int userRatingForSwapper) {
        this.userRatingForSwapper = userRatingForSwapper;
    }


    
    
    
}
