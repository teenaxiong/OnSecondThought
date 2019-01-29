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
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.ValidationException;

/**
 *
 * @author teena
 */
public class LoginUtil {
    
    public static boolean validate(String userEmail, String hashSaltPassword) {		
	boolean status = false;
	ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query="select * from login where userEmail= ? and binary userPassword=?";
        
        try{
           
          ps = connection.prepareStatement(query);
            ps.setString(1, userEmail);
            ps.setString(2, hashSaltPassword); 
            rs = ps.executeQuery();
            
            status = rs.next();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return status; 
    }
    
    
 
    public static String getUserSalt (String userEmail){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String salt = null; 
        
        String query="select salt from login where userEmail = ?";
        
        try{
          ps = connection.prepareStatement(query);
            ps.setString(1, userEmail);
            rs = ps.executeQuery();
            if (rs.next()){
		salt=rs.getString("salt");
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return salt; 
    }

    
    static int updateLogin(String userEmail, String userPassword, String salt){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query01 = "update login  set userPassword = ? , salt = ? where "
                + "userEmail = ?"; 
        
        try {
            ps = connection.prepareStatement(query01);
            ps.setString(1, userPassword);
            ps.setString(2, salt);
            ps.setString(3, userEmail);
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
