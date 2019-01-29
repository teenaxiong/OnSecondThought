<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>
<%@include file="user-navigation.jsp" %>
<div class="bodyColumn">
    <%@include file="site-navigation.jsp" %>

    <!--the main body-->
    <div class="mainColumn">
        <section class="main_body">
            <c:choose>
                <c:when test="${not empty messageNoUser}">
                    <c:out value=" ${messageNoUser}"/>
                </c:when>
                <c:otherwise>
                    <h4><a href='index.jsp'>Home</a>
                        > <a href="CatalogController">Category</a>
                        > <a href="CatalogController?itemCode=<c:out value="${itemVar.getItemCode()}"/>">Item</a>
                        > My Swap</h4>

                    <div class='swapBody_Parent'>
                        <!--picture of the item-->
                        <div class='swapBody_left'>
                            <img src ="images/<c:out value="${itemVar.getItemImage()}"/>" alt="<c:out value="${itemVar.getItemAlt()}"/>" class="swapPicture">
                        </div>

                        <!--Information regarding the item-->
                        <div class='swapBody_right'>
                            <p class="greenHeading itemName"><c:out value="${itemVar.getItemName()}"/></p>
                            <p class="itemDescription"><c:out value="${itemVar.getItemDescription()}"/></p>

                            <!--form that list items available to swap-->
                            <p class="greenHeading">Select an item from your available swap</p>

                            <form name="swapItemChosen" method="get" action="${ProfileController}" class="swapAvailableList">
                                <c:forEach var="dataVar" items="${availableItemsArray}">  
                                    <input type="hidden" name="itemList" value="<c:out value="${dataVar.getUserItem().getItemCode()}"/>">
                                    <input type="radio" name="theItem" value="<c:out value="${dataVar.getUserItem().getItemCode()}"/>" />
                                    <span class="listedHeading"><c:out value="${dataVar.getUserItem().getItemName()}"/></span><br> 
                                </c:forEach>    
                                <input type="hidden" name="theWantedItem" value=<c:out value="${itemVar.getItemCode()}"/>
                                <input type="hidden" name="myOffer" value="true"> 
                                <input type="submit" name="action" value="Confirm" />
                            </form>
                        </div>
                    </div>

                </c:otherwise>
            </c:choose>
        </section>
    </div>
</div>
<%@include file="footer.jsp" %>


