/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serv;

import java.io.IOException;
import java.io.PrintWriter;
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
public class CatalogController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if itemData servlet-specific error occurs
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
            out.println("<title>Servlet CatalogController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CatalogController at " + request.getContextPath() + "</h1>");
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
     * @throws ServletException if itemData servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String url = "";
        ItemDB databaseVar = new ItemDB();
        Item itemVar = new Item();
        ArrayList<Item> itemData = databaseVar.getItemDatabase(); //itemData holds all the data 
        String itemCode = request.getParameter("itemCode");
        String swap = request.getParameter("swap"); //stored user's decision to swap or not
        String confirm = request.getParameter("confirmSwap"); //stored confirmation on swapping
        String catalogCategory = request.getParameter("catalogCategory"); //stored user's input on catalog in url
        ArrayList<Item> tempData = new ArrayList<>(); //temp array that stores filtered data according to catalog parameter
        ArrayList<Item> availableItemsArray = null;
        User theUser = (User) session.getAttribute("theUser");
        UserProfile userProfile = (UserProfile) session.getAttribute("currentProfile");
        String action = EsapiUtil.validateUtil(request.getParameter("action"));

        //validate itemCode
        itemCode = EsapiUtil.validateUtil(itemCode);
        swap = EsapiUtil.validateUtil(swap);
        confirm = EsapiUtil.validateUtil(confirm);
        catalogCategory = EsapiUtil.validateUtil(catalogCategory);

        if (theUser != null) {
            ItemDB itemDB = new ItemDB();
            availableItemsArray = itemDB.availableItems(userProfile);
            url = "/categories.jsp";

            if (itemCode != null && itemCode.length() < 5 && itemCode.length() > 0) {
                for (int x = 0; x < availableItemsArray.size(); x++) {
                    String currentItem = EsapiUtil.validateUtil(availableItemsArray.get(x).getItemCode());
                    
                    String userItemCode = ""; 
                    if(x<userProfile.getUserProfileArray().size()){
                         userItemCode = EsapiUtil.validateUtil(userProfile.getUserProfileArray().get(x).getUserItem().getItemCode());
                    }
                    
                    int avgItemRating = 0;

                    if (AverageQuery.getAverageUserRating(theUser.getUserId()) != 0) {
                        avgItemRating = EsapiUtil.validateInteger(AverageQuery.getAverageItemRating(itemCode));
                    }

                    if (currentItem.equalsIgnoreCase(itemCode)) {
                        itemVar = databaseVar.getItem(itemCode.toUpperCase());
                        request.setAttribute("avgItemRating", avgItemRating);
                        url = "/item.jsp";
                        break;
                    } else if (x <= userProfile.getUserProfileArray().size() && itemCode.equalsIgnoreCase(userItemCode)) {
                        itemVar = databaseVar.getItem(itemCode.toUpperCase());
                        request.setAttribute("avgItemRating", avgItemRating);
                        url = "/item.jsp";
                        break;
                    } else {
                        url = "/categories.jsp";
                    }
                }
                request.setAttribute("itemDatabase", availableItemsArray);
                request.setAttribute("userItem", itemVar);
            } else {
                request.setAttribute("itemDatabase", availableItemsArray);
                url = "/categories.jsp";
            }

            //this runs if user is looking for a certain catalog   
            if (catalogCategory != null && catalogCategory.length() > 0) {
                for (Item x : availableItemsArray) {
                    if (x.getCategory().equalsIgnoreCase(catalogCategory)) {
                        String itemC = EsapiUtil.validateUtil(x.getItemCode());
                        tempData.add(databaseVar.getItem(itemC));
                    }
                }
                if (!tempData.isEmpty()) {
                    itemData.clear();
                    itemData = tempData;
                    request.setAttribute("itemDatabase", itemData);
                }
                // request.setAttribute("userItem", itemVar);
                url = "/categories.jsp";
            }

        } else { //runs if no user
            if (catalogCategory != null && catalogCategory.length() > 0) {
                for (Item x : itemData) {
                    if (x.getCategory().equalsIgnoreCase(catalogCategory)) {
                        String itemC = EsapiUtil.validateUtil(x.getItemCode());
                        tempData.add(databaseVar.getItem(itemC));
                    }
                }
                if (!tempData.isEmpty()) {
                    itemData.clear();
                    itemData = tempData;
                }
                request.setAttribute("userItem", itemVar);
                request.setAttribute("itemDatabase", itemData);
                url = "/categories.jsp";
            }

            if (itemCode != null && itemCode.length() < 5 && itemCode.length() > 0) {
                for (Item x : itemData) {
                    if (x.getItemCode().equalsIgnoreCase(itemCode)) {
                        itemVar = databaseVar.getItem(itemCode.toUpperCase());
                        int avgItemRating = EsapiUtil.validateInteger(AverageQuery.getAverageItemRating(itemCode));
                        request.setAttribute("avgItemRating", avgItemRating);
                        url = "/item.jsp";
                        break;
                    } else {
                        url = "/categories.jsp";
                    }
                }
                request.setAttribute("itemDatabase", itemData); //access to the database values
                request.setAttribute("userItem", itemVar);
            } else {
                url = "/categories.jsp";
                request.setAttribute("itemDatabase", itemData);
            }

        }

        //////////////////////////////
        //determining if itemCode matches data. if matches, goes to item. if not, goes back to catalogue
        if (action != null) {
            if (action.equalsIgnoreCase("history") && itemCode != null && itemCode.length() < 5 && itemCode.length() > 0) {

                for (Item x : itemData) {
                    if (x.getItemCode().equalsIgnoreCase(itemCode)) {
                        itemVar = databaseVar.getItem(itemCode.toUpperCase());
                        String itemStatus = "";
                        if (x.getUserID() != userProfile.getUserProfileID()) {
                            itemStatus = EsapiUtil.validateUtil(itemVar.getItemStatus());
                            request.setAttribute("itemStatus", itemStatus);
                        }
                        int avgItemRating = 0;

                        if (AverageQuery.getAverageUserRating(theUser.getUserId()) != 0) {
                            avgItemRating = EsapiUtil.validateInteger(AverageQuery.getAverageItemRating(itemCode));
                        }
                        request.setAttribute("avgItemRating", avgItemRating);
                        request.setAttribute("userItem", itemVar);
                        url = "/item.jsp";
                        break;
                    } else {
                        request.setAttribute("itemDatabase", availableItemsArray);
                        url = "/categories.jsp";
                    }
                }
            }
        }

        //determine if user wants to swap items
        if (swap != null && swap.equals("yes")) {
            url = "/swap.jsp";
        }

        //determine confirmation of swap
        if (confirm != null && confirm.equalsIgnoreCase("Confirm")) {
            url = "/myItems.jsp";
        }

        url = ESAPI.encoder().decodeForHTML(url);

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if itemData servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns itemData short description of the servlet.
     *
     * @return itemData String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
