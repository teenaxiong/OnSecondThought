<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>
<%@include file="user-navigation.jsp" %>
<div class="bodyColumn">
    <%@include file="site-navigation.jsp" %>
    <!--the main body-->
    <div class="mainColumn">
        <section class="main_body">

            <div  class="registrationForm">
                <c:if test="${not empty message}">
                    <c:out value="${message}"/><br><br>
                </c:if>
                <form action="RegistrationController" method="POST">
                    <div class="smallHeader">
                        Email Address:
                        <input type="text" name="userEmail" value="" placeholder="Email Address" required="required" />
                        <br><Br>
                        Name:
                        <input type="text" name="userFirstName" value="" placeholder="First Name" required="required" />
                        <input type="text" name="userLastName" value="" placeholder="Last Name" required="required"/>
                        <Br><br>
                        Address:
                        <input type="text" name="userAddress01" value="" placeholder="Street Address" required="required" size="50" />
                        <br>
                        <input type="text" name="userAddress02" value="" placeholder="Street Address" size="50"/>
                        <Br>
                        <input type="text" name="userCity" value="" placeholder="City" required="required" size="33" />
                        <input type="text" name="userState" value="" placeholder="State" required="required" size="33" />
                        <input type="text" name="userZipcode" value="" placeholder="ZipCode" required="required" size="33" />
                        <Br>
                        <input type="text" name="userCountry" value="" placeholder="Country" required="required"  />
                        <br><br>
                        Password:
                        <input type="password" name="userPassword" value="" placeholder="Password" required="required"/>
                        <br><br>
                        Confirm Password:
                        <input type="password" name="userPassword02" value="" placeholder="Password" required="required"/>
                        <br><Br>
                        <input type="submit" value="submit" name="action" />
                    </div>
                </form>               
            </div>
        </section>
    </div>
</div>

<%@include file="footer.jsp" %>