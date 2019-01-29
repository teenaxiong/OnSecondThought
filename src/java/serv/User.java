/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serv;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author teena
 */
public class User implements Serializable {
    
    private int userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userAddress01;
    private String userAddress02;
    private String userCity;
    private String userState;
    private int userZipcode;
    private String userCountry;    
    
    
    public User() {
    }

    public User(int userId, String userFirstName, String userLastName, String userEmail, String userAddress01, 
            String userAddress02, String userCity, String userState, int userZipcode, String userCountry
          ) {
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userAddress01 = userAddress01;
        this.userAddress02 = userAddress02;
        this.userCity = userCity;
        this.userState = userState;
        this.userZipcode = userZipcode;
        this.userCountry = userCountry;
       
    }
    
    
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    
    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAddress01() {
        return userAddress01;
    }

    public void setUserAddress01(String userAddress01) {
        this.userAddress01 = userAddress01;
    }

    public String getUserAddress02() {
        return userAddress02;
    }

    public void setUserAddress02(String userAddress02) {
        this.userAddress02 = userAddress02;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public int getUserZipcode() {
        return userZipcode;
    }

    public void setUserZipcode(int userZipcode) {
        this.userZipcode = userZipcode;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }
    
    
}
