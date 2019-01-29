/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serv;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

/**
 *
 * @author teena
 */
public class EsapiUtil {
    
    public static String validateUtil (String x){
             if (x != null) {
            try {
                x = ESAPI.validator().getValidInput(x, x,
                        "SafeString", 50, false);
            } catch (ValidationException | IntrusionException ex) {
                Logger.getLogger(CatalogController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return x;
    }

    public static int validateInteger(int x) {
        int validInt = 0; 
        try {
            validInt = ESAPI.validator().getValidInteger("validInt", Integer.toString(x), 1, 100000, false);

        } catch (ValidationException | IntrusionException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return validInt; 
    }

}
