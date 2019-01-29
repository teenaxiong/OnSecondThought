<%@include file="header.jsp" %>
<%@include file="user-navigation.jsp" %>
<div class="bodyColumn">
    <%@include file="site-navigation.jsp" %>
    <!--the main body-->
    <div class="mainColumn">
        <section class="main_body">
            <c:choose>
                <c:when test="${not empty messageNoUser}">
                    <c:out value="${messageNoUser}"/>
                </c:when>
                <c:when test="${not empty noItemMessage}">
                    <c:out value=" ${noItemMessage}"/>
                </c:when>
                <c:otherwise>
                    <h3 class='greenSubheading'>Your Swap Offers</h3>

                    <div class="myItemTableStyle">
                    <!--Tables that holds items to swap with -->
                    <table id='myItemTable'>
                        <thead>
                            <tr>
                                <th>Item</th>
                                <th>Swap Offer</th>  
                                <th></th>                                   
                            </tr>
                        </thead>
                        <tbody>

                            <c:choose>
                                <c:when test="${empty mySwapList}">
                                    <tr>  
                                        <td> <p class="listedHeading"><c:out value="${swapItem.getUserItem().getItemName()}"/></p>
                                            <img src="images/<c:out value="${swapItem.getUserItem().getItemImage()}"/>" 
                                                 class='myItemTableImage' alt="<c:out value="${swapItem.getUserItem().getItemName()}"/>"> 
                                        </td>
                                        <td><p class="listedHeading"><c:out value="${swapItem.getSwapItem().getItemName()}"/></p>
                                            <img src='images/<c:out value="${swapItem.getSwapItem().getItemImage()}"/>' class='myItemTableImage' alt='<c:out value="${swapItem.getSwapItem().getItemName()}"/>'>
                                        </td>                              
                                        <td>
                                            <c:choose>
                                                <c:when test="${swapItem.getOfferFromUserID() eq currentProfile.getUserProfileID()}">
                                                    <form action="ProfileController" method="get">
                                                        <c:forEach var="itemsVar" items="${currentProfile.getUserProfileArray()}">                                        
                                                            <input type="hidden" name="itemList" value="<c:out value="${itemsVar.getUserItem().getItemCode()}"/>">
                                                        </c:forEach>   
                                                        <input type="hidden" name="theItem" value="<c:out value="${swapItem.getUserItem().getItemCode()}"/>">
                                                        <input type="submit" value="Withdraw" name="action">
                                                    </form>  
                                                </c:when>
                                                <c:otherwise> 
                                                    <form action="ProfileController" method="get">
                                                        <c:forEach var="itemsVar" items="${currentProfile.getUserProfileArray()}">                                        
                                                            <input type="hidden" name="itemList" value="<c:out value="${itemsVar.getUserItem().getItemCode()}"/>">
                                                        </c:forEach>   
                                                        <input type="hidden" name="theItem" value="<c:out value="${swapItem.getUserItem().getItemCode()}"/>">
                                                        <input type="submit" value="Accept" name="action">
                                                    </form> 

                                                    <form action="ProfileController" method="get">
                                                        <c:forEach var="itemsVar" items="${currentProfile.getUserProfileArray()}">                                        
                                                            <input type="hidden" name="itemList" value="<c:out value="${itemsVar.getUserItem().getItemCode()}"/>">
                                                        </c:forEach>   
                                                        <input type="hidden" name="theItem" value="<c:out value="${swapItem.getUserItem().getItemCode()}"/>">
                                                        <input type="submit" value="Reject" name="action">
                                                    </form> 
                                                </c:otherwise>
                                            </c:choose> 
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="swapData" items="${mySwapList}">
                                        <tr>
                                            <td> <p class="listedHeading"><c:out value="${swapData.getUserItem().getItemName()}"/></p>
                                                <img src="images/<c:out value="${swapData.getUserItem().getItemImage()}"/>" 
                                                     class='myItemTableImage' alt="<c:out value="${swapData.getUserItem().getItemName()}"/>">
                                            </td>
                                            <td><p class="listedHeading"><c:out value="${swapData.getSwapItem().getItemName()}"/></p>
                                                <img src='images/<c:out value="${swapData.getSwapItem().getItemImage()}"/>' class='myItemTableImage' alt='<c:out value="${swapItem.getSwapItem().getItemName()}"/>'>
                                            </td>    
                                            <td>
                                                <c:choose>
                                                    <c:when test="${swapData.getOfferFromUserID() eq currentProfile.getUserProfileID()}">
                                                        <form action="ProfileController" method="get">
                                                            <c:forEach var="itemsVar" items="${currentProfile.getUserProfileArray()}">                                        
                                                                <input type="hidden" name="itemList" value="<c:out value="${itemsVar.getUserItem().getItemCode()}"/>">
                                                            </c:forEach>   
                                                            <input type="hidden" name="theItem" value="<c:out value="${swapData.getUserItem().getItemCode()}"/>">
                                                            <input type="submit" value="Withdraw" name="action">
                                                        </form>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <form action="ProfileController" method="get">
                                                            <c:forEach var="itemsVar" items="${currentProfile.getUserProfileArray()}">                                        
                                                                <input type="hidden" name="itemList" value="<c:out value="${itemsVar.getUserItem().getItemCode()}"/>">
                                                            </c:forEach>   
                                                            <input type="hidden" name="theItem" value="<c:out value="${swapData.getUserItem().getItemCode()}"/>">
                                                            <input type="submit" value="Accept" name="action">
                                                        </form> 
                                                        <form action="ProfileController" method="get">
                                                            <c:forEach var="itemsVar" items="${currentProfile.getUserProfileArray()}">                                        
                                                                <input type="hidden" name="itemList" value="<c:out value="${itemsVar.getUserItem().getItemCode()}"/>">
                                                            </c:forEach>   
                                                            <input type="hidden" name="theItem" value="<c:out value="${swapData.getUserItem().getItemCode()}"/>">
                                                            <input type="submit" value="Reject" name="action">
                                                        </form>  
                                                    </c:otherwise>
                                                </c:choose>  
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose> 
                        </tbody>
                    </table>
                    </div>
                    
                </c:otherwise>
            </c:choose>
        </section>
    </div>
</div>

<%@include file="footer.jsp" %>


