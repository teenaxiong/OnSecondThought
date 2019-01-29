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
public class RegistrationController extends HttpServlet {

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
            out.println("<title>Servlet RegistrationController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegistrationController at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
    }

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

        HttpSession session = request.getSession(true);
        User theUser = null;
        UserProfile currentProfile = null;
        UserDB userDB = new UserDB();
        String action = EsapiUtil.validateUtil(request.getParameter("action"));
        String userEmail = request.getParameter("userEmail");
        String userFirstName = EsapiUtil.validateUtil(request.getParameter("userFirstName"));
        String userLastName = EsapiUtil.validateUtil(request.getParameter("userLastName"));
        String userAddress01 = EsapiUtil.validateUtil(request.getParameter("userAddress01"));
        String userAddress02 = "";
        if (request.getParameter("userAddresss02") != null) {
            userAddress02 = EsapiUtil.validateUtil(request.getParameter("userAddress02"));
        }
        String userCity = EsapiUtil.validateUtil(request.getParameter("userCity"));
        String userState = EsapiUtil.validateUtil(request.getParameter("userState"));
        int userZipcode = 0;
        if (request.getParameter("userZipcode") != null) {
            userZipcode = EsapiUtil.validateInteger(Integer.parseInt(request.getParameter("userZipcode").trim()));
        }
        String userCountry = EsapiUtil.validateUtil(request.getParameter("userCountry"));
        String userPassword = EsapiUtil.validateUtil(request.getParameter("userPassword"));
        String userPassword02 = EsapiUtil.validateUtil(request.getParameter("userPassword02"));
        String url = "";
        String message = "";
        boolean noUser = true;

        ArrayList<User> userArray = userDB.getUsers();

        if (action != null) {
            if (action.equalsIgnoreCase("submit")) {
                try {
                    userEmail = ESAPI.validator().getValidInput("userEmail", userEmail, "Email", 200, false);

                    if (userPassword.length() < 8) {
                        message = "Sorry. Please enter a password greater than 8 characters.";
                        noUser = false;
                        url = "/registration.jsp";
                    } else if (!userPassword.equals(userPassword02)) {
                        message = "Sorry. Password does not match. Please re-enter";
                        noUser = false;
                        url = "/registration.jsp";
                    }
                    //looking inside array of all users, comparing userEmail to see if it already exist
                    for (int x = 0; x < userArray.size(); x++) {
                        if (userEmail.equalsIgnoreCase(userArray.get(x).getUserEmail())) {
                            message = "Sorry. That email already exist. Please login or register with a different email";
                            x = userArray.size();
                            noUser = false;
                            url = "/registration.jsp";
                        }
                    }

                    //if no user with same email exist and all criteria are met, add user's info to database
                    if (noUser) {
                        UserDB.addUser(userFirstName, userLastName, userEmail, userAddress01, userAddress02, userCity, userState, userZipcode, userCountry);
                        
                        String salt = PasswordUtil.getSalt();
                        try { 
                             userPassword = PasswordUtil.hashPassword(userPassword+salt);
                        } catch (NoSuchAlgorithmException ex) {
                            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        Login.setLogin(userEmail, userPassword, salt);
                        url = "/myItems.jsp";
                        theUser = UserDB.getUser(userEmail);

                        session.setAttribute("theUser", theUser);
                        currentProfile = userDB.getUserProfile(theUser.getUserId()); //gets the user profile  
                        session.setAttribute("currentProfile", currentProfile);
                    }

                } catch (ValidationException | IntrusionException ex) {
                    Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (action.equalsIgnoreCase("register")) {
                url = "/registration.jsp";
            }

        }

        url = ESAPI.encoder().canonicalize(url);
        message = ESAPI.encoder().canonicalize(message);
        request.setAttribute("message", message);

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);

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
