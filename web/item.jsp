<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>
<%@include file="user-navigation.jsp" %>
<div class="bodyColumn">
    <%@include file="site-navigation.jsp" %>
    <!--the main body-->
    <div class="mainColumn">
        <section class="main_body">

            <!--Breadcrumbs-->
            <h4><a href='index.jsp'>Home</a>
                > <a href="CatalogController">Category</a>
                > Item</h4>

            <c:choose>
                <c:when test="${not empty message}"> <p><c:out value = "${message}"/></p>
                </c:when>
                <c:otherwise>       
                    <!--Item Details-->
                    <div class="itemBody">
                        <!--Item Image-->
                        <div class="itemImageColumn">
                            <img src ="images/<c:out value="${userItem.getItemImage()}"/> "  class="swapPicture" alt="<c:out value="${userItem.getItemAlt()}"/>">
                        </div>
                        <div class="itemDescriptionColumn">

                            <!--Item Name-->
                            <p class="greenHeading"><c:out value="${userItem.getItemName()}"/></p>
                            <p class="text_heading">Average rating:</p>
                            <div class="avgItemRating"> 

                                <c:forEach begin="1" end="5" var="i" >
                                    <c:choose>
                                        <c:when test="${avgItemRating>=i}">
                                            <span class="myRating checked"></span>

                                        </c:when>
                                        <c:otherwise>
                                            <span class="myRating"></span>
                                        </c:otherwise>                                                    
                                    </c:choose>
                                </c:forEach></div>
                            <Br>
                            <!--Item Description-->
                            <p><c:out value = "${userItem.getItemDescription()}"/></p> 

                            <c:choose>
                                <c:when test="${itemStatus eq 'pending'}">
                                    <p class="greenHeading">You have an offer for this item. Click on Update to see offer</p>

                                    <form action="ProfileController" method="get">
                                        <c:forEach var="itemsVar" items="${currentProfile.getUserProfileArray()}">                                        
                                            <input type="hidden" name="itemList" value="<c:out value="${itemsVar.getSwapItem().getItemCode()}"/>">
                                        </c:forEach>   
                                        <input type="hidden" name="theItem" value="<c:out value="${userItem.getItemCode()}"/>">
                                        <input type="submit" value="Update" name="action">
                                    </form>      

                                </c:when>
                                <c:when test="${itemStatus eq 'swapped'}">
                                    <p class="greenHeading">OH! Awesome. You have already swapped this item</p><br>
                                    <p class="text_heading">Rate This Item!</p>
                                    <div class='itemRating'> 

                                        <div class="rating itemRating">                                    
                                            <a href="ProfileController?theItem=<c:out value="${userItem.getItemCode()}"/>&ratingScore=5&action=rating"><span class="star"></span></a>
                                            <a href="ProfileController?theItem=<c:out value="${userItem.getItemCode()}"/>&ratingScore=4&action=rating"><span class="star"></span></a>
                                            <a href="ProfileController?theItem=<c:out value="${userItem.getItemCode()}"/>&ratingScore=3&action=rating"><span class="star"></span></a>
                                            <a href="ProfileController?theItem=<c:out value="${userItem.getItemCode()}"/>&ratingScore=2&action=rating"><span class="star"></span></a>
                                            <a href="ProfileController?theItem=<c:out value="${userItem.getItemCode()}"/>&ratingScore=1&action=rating"><span class="star"></span>   </a>
                                        </div>
                                    </div>

                                </c:when>
                                <c:when test = "${userItem.getUserID() ne currentProfile.getUserProfileID()}">
                                    <a href="ProfileController?theItem=<c:out value="${userItem.getItemCode()}"/>&swap=yes&action=Offer">  
                                        <img src="images/swap_button.png" height="50" width="100" alt='swap it' class='swapImageButton'>
                                    </a>   
                                </c:when>
                                <c:when test ="${(userItem.getUserID() eq currentProfile.getUserProfileID())}">
                                    <p class="text_heading">Rate your Item!</p>
                                    <div class='itemRating'> 
                                        <div class="rating itemRating">                                    
                                            <a href="ProfileController?theItem=<c:out value="${userItem.getItemCode()}"/>&ratingScore=5&action=rating"><span class="star"></span></a>
                                            <a href="ProfileController?theItem=<c:out value="${userItem.getItemCode()}"/>&ratingScore=4&action=rating"><span class="star"></span></a>
                                            <a href="ProfileController?theItem=<c:out value="${userItem.getItemCode()}"/>&ratingScore=3&action=rating"><span class="star"></span></a>
                                            <a href="ProfileController?theItem=<c:out value="${userItem.getItemCode()}"/>&ratingScore=2&action=rating"><span class="star"></span></a>
                                            <a href="ProfileController?theItem=<c:out value="${userItem.getItemCode()}"/>&ratingScore=1&action=rating"><span class="star"></span>   </a>
                                        </div>
                                    </div>
                                </c:when>
                            </c:choose> 
                        </div> 
                    </div>  
                </c:otherwise>
            </c:choose>
        </section>
    </div>
</div>
<%@include file="footer.jsp" %>


