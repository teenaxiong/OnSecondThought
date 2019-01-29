 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--Navigation bar on top-->
            <nav class="upper_nav">
                <ul class="upper_list">
                    <c:choose>
                        <c:when test="${empty theUser}">
                            <li><a href="ProfileController?action=signin">Sign In</a></li>
                            </c:when>
                            <c:otherwise>
                            <li><a href="ProfileController?action=Signout" >Sign Out</a></li>
                            </c:otherwise>
                        </c:choose>
                    <li><a href="ProfileController?action=myShop">My Shop</a></li>
                    <li><a href="ProfileController?action=mySwap">My Swaps</a></li>
                </ul>
            </nav>