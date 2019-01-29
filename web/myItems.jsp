<%@include file="header.jsp" %>
<%@include file="user-navigation.jsp" %>
<div class="bodyColumn">
    <%@include file="site-navigation.jsp" %>

    <!--the main body-->
    <div class="mainColumn">
        <section class="main_body">
            <c:choose>
                <c:when test="${empty currentProfile.getUserProfileArray()}">
                    You have no items in your profile at this time. 
                </c:when>
                <c:when test="${not empty messageNoUser}">
                    <c:out value="${messageNoUser}"/>
                </c:when>
                <c:when test="${not empty message}">
                    <c:out value="${message}"/>
                </c:when>
                <c:otherwise>
                    <h3 class='greenSubheading'>Your items for Swap</h3>
                    <div class="myItemTableStyle">
                    <table id='myItemTable'>
                        <thead>
                            <tr>
                                <th>Item</th>
                                <th>Category</th>
                                <th>My Ratings</th>
                                <th>Swapped</th>
                                <th></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="dataVar" items="${currentProfile.getUserProfileArray()}" varStatus="loop" >    
                                <tr>  
                                    <td><img src="images/<c:out value="${dataVar.getUserItem().getItemImage()}"/>" 
                                             class='myItemTableImage' alt="<c:out value="${dataVar.getUserItem().getItemName()}"/>"> </td>
                                    <td><c:out value="${dataVar.getUserItem().getCategory()}"/></td>
                                    <td><div class="myItemsRatingOuter"> 
                                            <c:forEach begin="1" end="5" var="i" >
                                                <c:choose>
                                                    <c:when test="${dataVar.getUserRating()>=i}">
                                                        <span class="myRating checked"></span>

                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="myRating"></span>
                                                    </c:otherwise>                                                    
                                                </c:choose>
                                            </c:forEach>
                                        </div>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${dataVar.getItemStatus() eq 'swapped'}"> 
                                                <img src="images/green_check_icon.jpg" class='swapCheck_Button' alt='swap available'></a>
                                            </c:when>
                                            <c:when test="${dataVar.getItemStatus() eq 'pending'}">
                                                <img src="images/refresh.png" class='swapCheck_Button' alt='swap available'></a>
                                            </c:when>
                                            <c:otherwise>
                                                <p></p>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <form action="ProfileController" method="get">
                                            <c:forEach var="itemsVar" items="${currentProfile.getUserProfileArray()}">                                        
                                                <input type="hidden" name="itemList" value="<c:out value="${itemsVar.getUserItem().getItemCode()}"/>">
                                            </c:forEach>   
                                            <input type="hidden" name="theItem" value="<c:out value="${dataVar.getUserItem().getItemCode()}"/>">
                                             <c:choose>
                                            <c:when test = "${dataVar.getItemStatus() eq 'swapped'}">
                                               <input type="submit" name="action" value="Rate">  
                                            </c:when> 
                                               <c:otherwise><input type="submit" name="action" value="Update"></c:otherwise>
                                            </c:choose>
                                        </form>
                                    </td>
                                    <td>
                                        <form action="ProfileController" method="get">
                                            <c:forEach var="itemsVar" items="${currentProfile.getUserProfileArray()}">                                        
                                                <input type="hidden" name="itemList" value="<c:out value="${itemsVar.getUserItem().getItemCode()}"/>">
                                            </c:forEach>   
                                            <input type="hidden" name="theItem" value="<c:out value="${dataVar.getUserItem().getItemCode()}"/>">
                                            <input type="submit" name="action" value="Delete">  
                                        </form>
                                    </td>
                                </tr>                    
                            </c:forEach>   
                        </tbody>
                    </table>
                    </div>
                    <p class='legend'>Legend: <img src='images/refresh.png' width='20' height='20' alt='swap item'> Swap offer available.
                        <img src='images/green_check_icon.jpg' width='20' height='20' alt='already swap'> Already swapped
                    </p>   
                </c:otherwise>
            </c:choose>
        </section>
    </div>
</div>
<%@include file="footer.jsp" %>


