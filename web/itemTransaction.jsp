<%@include file="header.jsp" %>
<%@include file="user-navigation.jsp" %>
<div class="bodyColumn">
    <%@include file="site-navigation.jsp" %>
    <!--the main body-->
    <div class="mainColumn">
        <section class="main_body">
            <section class="main_body">
                <c:if test="${not empty message}">
                    <Br><br>
                    <c:out value=" ${message}"/>
                    <Br><Br>
                </c:if>
                <h3 class='greenSubheading'>Your Swapped History</h3>


                <table id='myItemTable'>
                    <thead>
                        <tr>
                            <th>Your Item</th>
                            <th>Swapped Item</th>  
                            <th></th>                                   
                        </tr>
                    </thead>
                    <tbody>
                        <tr class="row_border">  
                            <td> 
                                <p class="listedHeading"><c:out value="${userItemObj.getUserItem().getItemName()}"/></p>
                                <a href ="CatalogController?action=history&itemCode=<c:out value="${userItemObj.getUserItem().getItemCode()}"/>">
                                    <img src="images/<c:out value="${userItemObj.getUserItem().getItemImage()}"/>" 
                                         class='myItemTableImage' alt="<c:out value="${userItemObj.getUserItem().getItemName()}"/>"> 
                                </a>
                            </td>
                            <td>
                                <p class="listedHeading"><c:out value="${userItemObj.getSwapItem().getItemName()}"/></p>
                                <a href ="CatalogController?action=history&itemCode=<c:out value="${userItemObj.getSwapItem().getItemCode()}"/>">
                                    <img src='images/<c:out value="${userItemObj.getSwapItem().getItemImage()}"/>' class='myItemTableImage' 
                                         alt='<c:out value="${userItemObj.getSwapItem().getItemName()}"/>'>
                                </a>
                            </td>                              

                        </tr>
                        <tr>
                            <td>
                                <Br>
                                <div class="myItemsRatingOuter"> 
                                    Your rating for your item:  <Br>
                                    <c:forEach begin="1" end="5" var="i" >
                                        <c:choose>
                                            <c:when test="${userItemObj.getUserRating()>=i}">
                                                <span class="myRating checked"></span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="myRating"></span>
                                            </c:otherwise>                                                    
                                        </c:choose>
                                    </c:forEach></div>
                            </td>
                            <td>
                                <Br>
                                <div class="myItemsRatingOuter"> 
                                    Your rating for this item:  <Br>
                                    <c:forEach begin="1" end="5" var="i" >
                                        <c:choose>
                                            <c:when test="${userItemObj.getUserRatingForSwappedItem()>=i}">
                                                <span class="myRating checked"></span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="myRating"></span>
                                            </c:otherwise>                                                    
                                        </c:choose>
                                    </c:forEach></div>
                                <Br>

                                <div class="myItemsRatingOuter"> 
                                    Your rating for user: <c:out value = "${swapperEmail}"/><br>
                                    <c:forEach begin="1" end="5" var="i" >
                                        <c:choose>
                                            <c:when test="${ userItemObj.getUserRatingForSwapper()>=i}">
                                                <span class="myRating checked"></span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="myRating"></span>
                                            </c:otherwise>                                                    
                                        </c:choose>
                                    </c:forEach></div>
                            </td>
                        <tr>
                            <td>
                                <div class="rating itemRating">        
                                    <a href="ProfileController?theItem=<c:out value="${userItemObj.getUserItem().getItemCode()}"/>&ratingScore=5&action=rating"><span class="star"></span></a>
                                    <a href="ProfileController?theItem=<c:out value="${userItemObj.getUserItem().getItemCode()}"/>&ratingScore=4&action=rating"><span class="star"></span></a>
                                    <a href="ProfileController?theItem=<c:out value="${userItemObj.getUserItem().getItemCode()}"/>&ratingScore=3&action=rating"><span class="star"></span></a>
                                    <a href="ProfileController?theItem=<c:out value="${userItemObj.getUserItem().getItemCode()}"/>&ratingScore=2&action=rating"><span class="star"></span></a>
                                    <a href="ProfileController?theItem=<c:out value="${userItemObj.getUserItem().getItemCode()}"/>&ratingScore=1&action=rating"><span class="star"></span>   </a>
                                    Rate your item!
                                </div>
                            </td>
                            <td>
                                <div class="rating itemRating">                                    
                                    <a href="ProfileController?theItem=<c:out value="${userItemObj.getSwapItem().getItemCode()}"/>&ratingScore=5&action=rating"><span class="star"></span></a>
                                    <a href="ProfileController?theItem=<c:out value="${userItemObj.getSwapItem().getItemCode()}"/>&ratingScore=4&action=rating"><span class="star"></span></a>
                                    <a href="ProfileController?theItem=<c:out value="${userItemObj.getSwapItem().getItemCode()}"/>&ratingScore=3&action=rating"><span class="star"></span></a>
                                    <a href="ProfileController?theItem=<c:out value="${userItemObj.getSwapItem().getItemCode()}"/>&ratingScore=2&action=rating"><span class="star"></span></a>
                                    <a href="ProfileController?theItem=<c:out value="${userItemObj.getSwapItem().getItemCode()}"/>&ratingScore=1&action=rating"><span class="star"></span>   </a>

                                    Rate this Item!
                                </div>

                                <br>  <Br>
                                <div class="rating itemRating">                                    
                                    <a href="ProfileController?theItem=<c:out value="${userItemObj.getSwapItem().getItemCode()}"/>&ratingScore=5&action=rateUser"><span class="star"></span></a>
                                    <a href="ProfileController?theItem=<c:out value="${userItemObj.getSwapItem().getItemCode()}"/>&ratingScore=4&action=rateUser"><span class="star"></span></a>
                                    <a href="ProfileController?theItem=<c:out value="${userItemObj.getSwapItem().getItemCode()}"/>&ratingScore=3&action=rateUser"><span class="star"></span></a>
                                    <a href="ProfileController?theItem=<c:out value="${userItemObj.getSwapItem().getItemCode()}"/>&ratingScore=2&action=rateUser"><span class="star"></span></a>
                                    <a href="ProfileController?theItem=<c:out value="${userItemObj.getSwapItem().getItemCode()}"/>&ratingScore=1&action=rateUser"><span class="star"></span>   </a>

                                    Rate this user:  <c:out value = "${swapperEmail}"/>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </section> 
        </section>
    </div>
</div>
<%@include file="footer.jsp" %>
