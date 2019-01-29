/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serv;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

/**
 *
 * @author teena
 */
public class ProfileController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProfileController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProfileController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        UserDB userDB = new UserDB();
        ArrayList<User> userArray = userDB.getUsers();

        String action = EsapiUtil.validateUtil(request.getParameter("action"));
        String theItem = EsapiUtil.validateUtil(request.getParameter("theItem"));
        String[] itemList = request.getParameterValues("itemList");
        String theWantedItem = EsapiUtil.validateUtil(request.getParameter("theWantedItem"));
        String ratingScoreString = EsapiUtil.validateUtil(request.getParameter("ratingScore"));
        int rating = 0;
        String url = "";
        User theUser = (User) session.getAttribute("theUser");

        UserProfile userCurrentProfile = (UserProfile) session.getAttribute("currentProfile");
        String message = "";
        UserProfile currentProfile = null;
        String userEmail = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");
        String status = "";

        if (theUser == null) {
            if (action != null) {
                if (action.equalsIgnoreCase("mySwap") || action.equalsIgnoreCase("offer") || action.equalsIgnoreCase("myshop")) {
                    message = "You do not have any items. Please click on sign-in to start swapping!";
                    message = ESAPI.encoder().canonicalize(message);
                    request.setAttribute("messageNoUser", message);
                    url = "/mySwaps.jsp";
                } else if (action.equalsIgnoreCase("signIn")) {
                    //checking parameter for userEmail
                    if (userEmail != null && userPassword != null) {
                        try {
                            userEmail = ESAPI.validator().getValidInput("userEmail", userEmail, "Email", 200, false);
                            userPassword = ESAPI.validator().getValidInput("userPassword", userPassword, "SafeString", 200, false);

                            String salt = ""; 
                            
                            if (LoginUtil.getUserSalt(userEmail) != null) {
                                salt = LoginUtil.getUserSalt(userEmail);
                            } else{//if user does not have salted password, it gets one here
                                System.out.println("hii");
                                salt = PasswordUtil.getSalt();
                                userPassword = PasswordUtil.hashPassword(userPassword + salt);  
                                LoginUtil.updateLogin(userEmail, userPassword, salt); 
                            }
                            String hashSaltPassword = PasswordUtil.hashPassword(userPassword + salt);

                            //if useremail and password matches, it goes into this for loop
                            if (LoginUtil.validate(userEmail, hashSaltPassword)) {
                                for (int x = 0; x < userArray.size(); x++) {
                                    if (userEmail.equalsIgnoreCase(userArray.get(x).getUserEmail())) {
                                        theUser = userArray.get(x);
                                        session.setAttribute("theUser", theUser);
                                        currentProfile = userDB.getUserProfile(theUser.getUserId()); //gets the user profile  
                                        session.setAttribute("currentProfile", currentProfile);

                                        int avgUserRating = 0;
                                        if (AverageQuery.getAverageUserRating(theUser.getUserId()) != 0) {
                                            avgUserRating = EsapiUtil.validateInteger(AverageQuery.getAverageUserRating(theUser.getUserId()));
                                        }
                                        session.setAttribute("avgUserRating", avgUserRating);
                                        url = "/myItems.jsp";
                                        x = userArray.size();
                                    }
                                }//end of for loop
                            } else {
                                message = "Sorry. Invalid email and password. Please re-enter";
                                message = ESAPI.encoder().canonicalize(message);
                                request.setAttribute("message", message);
                                url = "/login.jsp";
                            }
                        } catch (ValidationException | IntrusionException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                            message = "Sorry. Invalid email and password. Please re-enter";
                            message = ESAPI.encoder().canonicalize(message);
                            request.setAttribute("message", message);
                            url = "/login.jsp";
                        } catch (NoSuchAlgorithmException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        url = "/login.jsp";
                    }
                }
            }
        } else {//this starts if there is an existing user
            if (userCurrentProfile.getUserProfileArray().isEmpty()) {
                message = "There are no items to display.";
                message = ESAPI.encoder().canonicalize(message);
                request.setAttribute("message", message);
                url = "/myItems.jsp";
            }
            //this loads the userprofile everytime
            userCurrentProfile = userDB.getUserProfile(theUser.getUserId());
            int avgUserRating = 0;
            if (AverageQuery.getAverageUserRating(theUser.getUserId()) != 0) {
                avgUserRating = EsapiUtil.validateInteger(AverageQuery.getAverageUserRating(theUser.getUserId()));
            }
            session.setAttribute("avgUserRating", avgUserRating);
            if (action != null) {
                if ((action.equalsIgnoreCase("Update") || action.equalsIgnoreCase("Accept") || action.equalsIgnoreCase("Reject") || action.equalsIgnoreCase("Withdraw")
                        || action.equalsIgnoreCase("Offer") || action.equalsIgnoreCase("Delete") || action.equalsIgnoreCase("Signout") || action.equalsIgnoreCase("Confirm")
                        || action.equalsIgnoreCase("mySwap") || action.equalsIgnoreCase("rating") || action.equalsIgnoreCase("rateUser") || action.equalsIgnoreCase("myShop")
                        || action.equalsIgnoreCase("signIn") || action.equalsIgnoreCase("rate"))) {

                    switch (action.toLowerCase()) {
                        case "update":
                            if (theItem != null && theItem.length() < 5 && theItem.length() > 0) {
                                for (int x = 0; x < itemList.length; x++) {//checking to see if item is in the list
                                    if (theItem.equals(itemList[x])) {
                                        for (int z = 0; z < userCurrentProfile.getUserProfileArray().size(); z++) {
                                            String myCode = EsapiUtil.validateUtil(userCurrentProfile.getUserProfileArray().get(z).getUserItem().getItemCode());

                                            //if user hasn't swap item yet, this if statements takes user to their individual item page
                                            if (userCurrentProfile.getUserProfileArray().get(z).getSwapItem() == null) {
                                                if (theItem.equals(myCode)) {
                                                    //the code are now equal. we found the match. 
                                                    UserItem userItemObj = userCurrentProfile.getUserProfileArray().get(z);//assigning the item to the obect 
                                                    status = EsapiUtil.validateUtil(userItemObj.getItemStatus());
                                                    if (status.equalsIgnoreCase("available")) {
                                                        Item a = userItemObj.getUserItem();
                                                        request.setAttribute("userItem", a);
                                                        request.setAttribute("userItemObj", userItemObj);

                                                        int avgItemRating = AverageQuery.getAverageItemRating(theItem);
                                                        request.setAttribute("avgItemRating", avgItemRating);
                                                        url = "/item.jsp";
                                                    }
                                                }
                                            } else if (theItem.equals(EsapiUtil.validateUtil(userCurrentProfile.getUserProfileArray().get(z).getSwapItem().getItemCode()))
                                                    || theItem.equals(myCode)) {
                                                //the code are now equal. we found the match. 
                                                UserItem userItemObj = userCurrentProfile.getUserProfileArray().get(z);//assigning the item to the obect 
                                                status = EsapiUtil.validateUtil(userItemObj.getItemStatus());

                                                if (status.equalsIgnoreCase("pending")) { //checking to see status
                                                    request.setAttribute("swapItem", userItemObj);
                                                    url = "/mySwaps.jsp";
                                                }
                                            }//end of if statement, checking and getting the right item
                                        }//end of for loop
                                    }
                                }
                            }//end of if statement validating theItem code
                            else {
                                url = "/myItems.jsp";
                            }
                            break;
                        case "accept":
                        case "reject":
                        case "confirm":
                        case "withdraw":
                        
                            if (theItem != null && theItem.length() < 5 && theItem.length() > 0) {
                                for (int y = 0; y < itemList.length; y++) {//checking to see if item is in the list
                                    if (theItem.equals(itemList[y])) {
                                        for (int z = 0; z < userCurrentProfile.getUserProfileArray().size(); z++) { //finding item in userprofile array
                                            String myCode = EsapiUtil.validateUtil(userCurrentProfile.getUserProfileArray().get(z).getUserItem().getItemCode());
                                            if (theItem.equals(myCode)) {
                                                //the code are now equal. we found the match. 
                                                UserItem userItemObj = userCurrentProfile.getUserProfileArray().get(z);//assigning the item to the obect 
                                                status = EsapiUtil.validateUtil(userItemObj.getItemStatus());
                                                if (status.equalsIgnoreCase("pending")) { //checking to see status
                                                    String userItemCode = EsapiUtil.validateUtil(userItemObj.getUserItem().getItemCode());
                                                    String swapItemCode = EsapiUtil.validateUtil(userItemObj.getSwapItem().getItemCode());
                                                    int getOfferTableID = EsapiUtil.validateInteger(userItemObj.getOfferTableID());

                                                    if (action.toLowerCase().equalsIgnoreCase("reject") || action.toLowerCase().equals("withdraw")) {
                                                        userItemObj.setItemStatus("available"); //reset the status of item
                                                        status = EsapiUtil.validateUtil(userItemObj.getItemStatus());
                                                        OfferDB.updateOffer(getOfferTableID, userItemCode, swapItemCode, status);
                                                    } else if (action.toLowerCase().equalsIgnoreCase("accept")) {
                                                        userItemObj.setItemStatus("swapped");
                                                        status = EsapiUtil.validateUtil(userItemObj.getItemStatus());
                                                        OfferDB.updateOffer(getOfferTableID, userItemCode, swapItemCode, status);

                                                        //this deactivate the item so that if item is swapped, it gets
                                                        //removed from the catalogue where user wont see it again. 
                                                        ItemDB.itemInactive(userItemObj.getUserItem());
                                                        ItemDB.itemInactive(userItemObj.getSwapItem());
                                                    }
                                                    session.setAttribute("currentProfile", userCurrentProfile);
                                                    url = "/myItems.jsp";
                                                } else if (status.equalsIgnoreCase("available")) {//use to display the data when user clicks confirm
                                                    userItemObj.setItemStatus("pending");
                                                    if (theWantedItem != null) {
                                                        ItemDB itemDB = new ItemDB();
                                                        Item theWantedItemObj = null;
                                                        ArrayList<Item> availableItemsArray = itemDB.availableItems(userCurrentProfile);
                                                        for (int x = 0; x < availableItemsArray.size(); x++) {
                                                            String itemCode = EsapiUtil.validateUtil(availableItemsArray.get(x).getItemCode());
                                                            if (theWantedItem.equals(itemCode)) {
                                                                theWantedItemObj = availableItemsArray.get(x);
                                                            }
                                                        }
                                                        userItemObj.setSwapItem(theWantedItemObj);
                                                        int userID = EsapiUtil.validateInteger(userItemObj.getUserID());
                                                        String userItemCode = EsapiUtil.validateUtil(userItemObj.getUserItem().getItemCode());
                                                        int swapUserID = EsapiUtil.validateInteger(userItemObj.getSwapItem().getUserID());
                                                        status = EsapiUtil.validateUtil(userItemObj.getItemStatus());
                                                        String swapItemCode = EsapiUtil.validateUtil(userItemObj.getSwapItem().getItemCode());
                                                        OfferDB.addOffer(userID, userItemCode, swapUserID, status, swapItemCode);
                                                    }
                                                    //have to reload currentprofile here

                                                    session.setAttribute("currentProfile", userCurrentProfile);
                                                    url = "/myItems.jsp";
                                                }
                                            }//end of if statement, checking and getting the right item
                                        }
                                    }
                                }//end of for loop
                            } else {
                                url = "/myItems.jsp";  //end of if statement validating theItem code
                            }
                            break;
                        case "offer":
                            ArrayList<UserItem> availableItemsArray = new ArrayList<>();
                            Item theOfferItem = null;
                            ItemDB databaseVar = new ItemDB();
                            ArrayList<Item> itemData = databaseVar.getItemDatabase();
                            if (theItem != null && theItem.length() < 5 && theItem.length() > 0) {
                                //this for loop validates that the item belongs in the catalogue
                                for (int y = 0; y < itemData.size(); y++) {
                                    String itemCode = EsapiUtil.validateUtil(itemData.get(y).getItemCode());
                                    if (itemCode.equals(theItem)) {
                                        theOfferItem = itemData.get(y);
                                    }
                                }

                                for (int z = 0; z < userCurrentProfile.getUserProfileArray().size(); z++) {
                                    UserItem userItemObj = userCurrentProfile.getUserProfileArray().get(z);//assigning the item to the obect 
                                    status = EsapiUtil.validateUtil(userItemObj.getItemStatus());
                                    if (status.equalsIgnoreCase("available")) {
                                        availableItemsArray.add(userItemObj);
                                    }
                                }//end of for loop
                                
                                //if user has no items in their profile, this message pops up
                                if (availableItemsArray.isEmpty()) {
                                    message = "Sorry,\n"
                                            + "you do not have any available items for swapping. Please add\n"
                                            + "more items to start swapping again!";
                                    message = ESAPI.encoder().canonicalize(message);
                                    request.setAttribute("message", message);
                                    url = "/item.jsp";
                                } else if (theOfferItem == null) {
                                    url = "/categories.jsp";
                                } else {
                                    url = "/swap.jsp";
                                    request.setAttribute("availableItemsArray", availableItemsArray);
                                    request.setAttribute("itemVar", theOfferItem);
                                }
                            }//end of for loop
                            else {
                                url = "/myItems.jsp";
                            }
                            break;

                        case "rateuser": //rate the other user
                            if (ratingScoreString != null) {
                                rating = EsapiUtil.validateInteger(Integer.parseInt(ratingScoreString));
                            }

                            if (theItem != null && theItem.length() < 5 && theItem.length() > 0) {
                                for (int z = 0; z < userCurrentProfile.getUserProfileArray().size(); z++) {
                                    if (userCurrentProfile.getUserProfileArray().get(z).getSwapItem() != null
                                            && theItem.equalsIgnoreCase(EsapiUtil.validateUtil(userCurrentProfile.getUserProfileArray().get(z).getSwapItem().getItemCode()))) {//the items is one of the user
                                        UserItem userItemObj = userCurrentProfile.getUserProfileArray().get(z);
                                        int offerTableID = EsapiUtil.validateInteger(userItemObj.getOfferTableID());
                                        int swapperID;
                                        int userID = 0;
                                        int theUserID = EsapiUtil.validateInteger(theUser.getUserId()); 
                                        if (theUserID != 0 && theUserID == userItemObj.getUserItem().getUserID()) {
                                            userID = EsapiUtil.validateInteger(userItemObj.getUserID());
                                            swapperID = EsapiUtil.validateInteger(userItemObj.getSwapItem().getUserID());
                                        } else {
                                            userID = EsapiUtil.validateInteger(userItemObj.getUserID());
                                            swapperID = EsapiUtil.validateInteger(userItemObj.getUserItem().getUserID());
                                        }

                                        if (userItemObj.getUserRatingForSwapper() != 0) {
                                            FeedbackDB.updateUserFeedback(offerTableID, userID, rating);

                                            String swapperEmail = "";

                                            if (userItemObj.getSwapItem() != null) {
                                                swapperEmail = UserDB.getUser(EsapiUtil.validateInteger(userItemObj.getSwapItem().getUserID())).getUserEmail();
                                                try {
                                                    swapperEmail = ESAPI.validator().getValidInput("swapperEmail", swapperEmail, "Email", 200, false);
                                                } catch (ValidationException | IntrusionException ex) {
                                                    Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            }
                                            userCurrentProfile = userDB.getUserProfile(EsapiUtil.validateInteger(theUser.getUserId()));
                                            userItemObj = userCurrentProfile.getUserProfileArray().get(z);

                                            message = "Thank you for rating!";
                                            message = ESAPI.encoder().canonicalize(message);
                                            session.setAttribute("currentProfile", userCurrentProfile);
                                            request.setAttribute("swapperEmail", swapperEmail);
                                            request.setAttribute("userItemObj", userItemObj);
                                            request.setAttribute("message", message);
                                            url = "/itemTransaction.jsp";

                                            z += userCurrentProfile.getUserProfileArray().size();

                                        } else {
                                            FeedbackDB.addUserFeedback(offerTableID, userID, swapperID, rating);

                                            String swapperEmail = "";

                                            if (userItemObj.getSwapItem() != null) {
                                                swapperEmail = UserDB.getUser(EsapiUtil.validateInteger(userItemObj.getSwapItem().getUserID())).getUserEmail();
                                                try {
                                                    swapperEmail = ESAPI.validator().getValidInput("swapperEmail", swapperEmail, "Email", 200, false);
                                                } catch (ValidationException | IntrusionException ex) {
                                                    Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            }
                                            userCurrentProfile = userDB.getUserProfile(theUser.getUserId());
                                            userItemObj = userCurrentProfile.getUserProfileArray().get(z);
                                            message = "Thank you for rating!";
                                            message = ESAPI.encoder().canonicalize(message);
                                            session.setAttribute("currentProfile", userCurrentProfile);
                                            request.setAttribute("swapperEmail", swapperEmail);
                                            request.setAttribute("userItemObj", userItemObj);
                                            url = "/itemTransaction.jsp";
                                            request.setAttribute("message", message);

                                            z += userCurrentProfile.getUserProfileArray().size();
                                        }
                                    }
                                }
                            }
                            break;

                        case "rating":

                            if (ratingScoreString != null) {
                                rating = Integer.parseInt(ratingScoreString);
                            }

                            for (int z = 0; z < userCurrentProfile.getUserProfileArray().size(); z++) {
                                UserItem userItemObj = userCurrentProfile.getUserProfileArray().get(z);
                                String userItemCode = EsapiUtil.validateUtil(userItemObj.getUserItem().getItemCode());
                                String swapperEmail = "";

                                if (userItemObj.getSwapItem() != null) {
                                    swapperEmail = UserDB.getUser(EsapiUtil.validateInteger(userItemObj.getSwapItem().getUserID())).getUserEmail();
                                    try {
                                        swapperEmail = ESAPI.validator().getValidInput("swapperEmail", swapperEmail, "Email", 200, false);
                                    } catch (ValidationException | IntrusionException ex) {
                                        Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }

                                if (theItem.equalsIgnoreCase(userItemCode)) {//the items is one of the user
                                    //adding feedback to user's item
                                    if (userItemObj.getUserRating() != 0) {

                                        int userID = EsapiUtil.validateInteger(userItemObj.getUserID());
                                        FeedbackDB.updateItemFeedback(userID, userItemCode, rating);
                                        userCurrentProfile = userDB.getUserProfile(EsapiUtil.validateInteger(theUser.getUserId()));
                                        userItemObj = userCurrentProfile.getUserProfileArray().get(z);
                                        session.setAttribute("currentProfile", userCurrentProfile);

                                        if (userItemObj.getItemStatus().equalsIgnoreCase("available")) {
                                            url = "/myItems.jsp";
                                        } else {
                                            message = "Thank you for rating!";
                                            message = ESAPI.encoder().canonicalize(message);
                                            request.setAttribute("swapperEmail", swapperEmail);
                                            request.setAttribute("userItemObj", userItemObj);
                                            request.setAttribute("message", message);
                                            url = "/itemTransaction.jsp";
                                        }

                                        z += userCurrentProfile.getUserProfileArray().size();
                                    } else {

                                        int getOfferTableID = EsapiUtil.validateInteger(userItemObj.getOfferTableID());
                                        int userID = EsapiUtil.validateInteger(userItemObj.getUserID());
                                        FeedbackDB.addItemFeedback(getOfferTableID, userItemCode, userID, rating);

                                        userCurrentProfile = userDB.getUserProfile(EsapiUtil.validateInteger(theUser.getUserId()));
                                        userItemObj = userCurrentProfile.getUserProfileArray().get(z);
                                        session.setAttribute("currentProfile", userCurrentProfile);
                                        if (userItemObj.getItemStatus().equalsIgnoreCase("available")) {
                                            url = "/myItems.jsp";
                                        } else {
                                            message = "Thank you for rating!";
                                            message = ESAPI.encoder().canonicalize(message);
                                            request.setAttribute("swapperEmail", swapperEmail);
                                            request.setAttribute("userItemObj", userItemObj);
                                            request.setAttribute("message", message);
                                            url = "/itemTransaction.jsp";
                                        }
                                        z += userCurrentProfile.getUserProfileArray().size();
                                    }

                                } else if (userItemObj.getSwapItem() != null && theItem.equalsIgnoreCase(EsapiUtil.validateUtil(userItemObj.getSwapItem().getItemCode()))) {
                                    //adding feedback to the swap item
                                    String swappedItemCode = EsapiUtil.validateUtil(userItemObj.getSwapItem().getItemCode());

                                    if (userItemObj.getUserRatingForSwappedItem() != 0) {

                                        int userID = EsapiUtil.validateInteger(userItemObj.getUserID());
                                        FeedbackDB.updateItemFeedback(userID, swappedItemCode, rating);
                                        message = "Thank you for rating!";
                                        message = ESAPI.encoder().canonicalize(message);
                                        userCurrentProfile = userDB.getUserProfile(theUser.getUserId());
                                        userItemObj = userCurrentProfile.getUserProfileArray().get(z);
                                        session.setAttribute("currentProfile", userCurrentProfile);
                                        request.setAttribute("swapperEmail", swapperEmail);
                                        request.setAttribute("userItemObj", userItemObj);
                                        request.setAttribute("message", message);
                                        url = "/itemTransaction.jsp";
                                        z += userCurrentProfile.getUserProfileArray().size();

                                    } else {

                                        //adding feedback to the swap item
                                        int getOfferTableID = EsapiUtil.validateInteger(userItemObj.getOfferTableID());
                                        String swapItemCode = EsapiUtil.validateUtil(userItemObj.getSwapItem().getItemCode());
                                        int userID = EsapiUtil.validateInteger(userItemObj.getUserID());
                                        FeedbackDB.addItemFeedback(getOfferTableID, swapItemCode, userID, rating);

                                        message = "Thank you for rating!";
                                        message = ESAPI.encoder().canonicalize(message);

                                        userCurrentProfile = userDB.getUserProfile(theUser.getUserId());
                                        userItemObj = userCurrentProfile.getUserProfileArray().get(z);
                                        session.setAttribute("currentProfile", userCurrentProfile);
                                        request.setAttribute("swapperEmail", swapperEmail);
                                        request.setAttribute("userItemObj", userItemObj);
                                        request.setAttribute("message", message);
                                        url = "/itemTransaction.jsp";
                                        z += userCurrentProfile.getUserProfileArray().size();
                                    }
                                }
                            }
                            break;

                        case "delete":
                            if (theItem != null && theItem.length() < 5 && theItem.length() > 0) {
                                for (int x = 0; x < itemList.length; x++) {//checking to see if item is in the list
                                    if (theItem.equals(itemList[x])) {
                                        for (int z = 0; z < userCurrentProfile.getUserProfileArray().size(); z++) { //finding item in userprofile array
                                            String userItemCode = EsapiUtil.validateUtil(userCurrentProfile.getUserProfileArray().get(z).getUserItem().getItemCode());
                                            if (theItem.equals(userItemCode)) {
                                                //this is our item. we found the match. 
                                                userCurrentProfile.removeUserItem(userCurrentProfile.getUserProfileArray().get(z).getUserItem());
                                                userCurrentProfile = userDB.getUserProfile(EsapiUtil.validateInteger(theUser.getUserId()));
                                                session.setAttribute("currentProfile", userCurrentProfile);
                                                url = "/myItems.jsp";
                                                z += userCurrentProfile.getUserProfileArray().size();
                                                x += itemList.length;
                                            }//end of if statement, checking and getting the right item
                                        }//end of for loop
                                    } else {
                                        url = "/myItems.jsp";  //end of checking item in itemlist                              
                                    }
                                }//end of for loop
                            } else {
                                url = "/myItems.jsp";  //end of if statement validating theItem code
                            }
                            break;

                        case "myswap":
                            ArrayList<UserItem> mySwapList = new ArrayList<>();
                            for (int z = 0; z < userCurrentProfile.getUserProfileArray().size(); z++) {
                                status = EsapiUtil.validateUtil(userCurrentProfile.getUserProfileArray().get(z).getItemStatus());
                                if (status.equals("pending")) {
                                    mySwapList.add(userCurrentProfile.getUserProfileArray().get(z));
                                }
                            }
                            if (mySwapList.isEmpty()) {
                                message = "There are no items in your swap list right now.";
                                message = ESAPI.encoder().canonicalize(message);
                                request.setAttribute("noItemMessage", message);
                            }
                            request.setAttribute("mySwapList", mySwapList);
                            url = "/mySwaps.jsp";
                            break;
                        case "myshop":
                            url = "/myItems.jsp";
                            break;
                        case "signout":
                            session.invalidate();
                            url = "/index.jsp";
                            break;
                        case "signin":
                            if (theUser != null) {
                                url = "/myItems.jsp";
                            }
                            break;
                        case "rate":
                            if (theItem != null && theItem.length() < 5 && theItem.length() > 0) {
                                for (int x = 0; x < itemList.length; x++) {//checking to see if item is in the list
                                    if (theItem.equals(itemList[x])) {
                                        for (int z = 0; z < userCurrentProfile.getUserProfileArray().size(); z++) {
                                            if (theItem.equals(userCurrentProfile.getUserProfileArray().get(z).getUserItem().getItemCode())) {
                                                UserItem userItemObj = userCurrentProfile.getUserProfileArray().get(z);//assigning the item to the obect 
                                                
                                               
                                                String swapperEmail = UserDB.getUser(EsapiUtil.validateInteger(userItemObj.getSwapItem().getUserID())).getUserEmail();
                                                try {
                                                    swapperEmail = ESAPI.validator().getValidInput("swapperEmail", swapperEmail, "Email", 200, false);
                                                } catch (ValidationException | IntrusionException ex) {
                                                    Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                                request.setAttribute("swapperEmail", swapperEmail);
                                                request.setAttribute("userItemObj", userItemObj);
                                                url = "/itemTransaction.jsp";
                                                z = userCurrentProfile.getUserProfileArray().size();
                                                x = itemList.length;
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        default:
                            url = "/myItems.jsp";
                            break;

                    }//end of switch
                } else {
                    url = "/myItems.jsp";
                }
            } else {//end of outer if statement
                url = "/myItems.jsp";
            }
        }//end of else if 

        url = ESAPI.encoder().canonicalize(url);

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);

    }//end of the the doGet

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
