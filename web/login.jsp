<%-- 
    Document   : login
    Created on : Nov 20, 2018, 7:39:16 PM
    Author     : teena
--%>
<%@include file="header.jsp" %>
<%@include file="user-navigation.jsp" %>
<div class="bodyColumn">
    <%@include file="site-navigation.jsp" %>

    <!--the main body-->
    <div class="mainColumn">
        <section class="main_body">

            <div class="signin_outer">
                <c:if test="${not empty message}">
                    <c:out value="${message}"/><br><br>
                </c:if>
                <div class="login">    
                    <form name="signin" action="ProfileController">
                        Email: <input type="text" name="userEmail" required="required" size="30"/><br> 
                        Password: <input type="password" name="userPassword" required="required" size="30"/><br><br>
                        <input type="hidden" name="action" value="signIn"> 
                        <input type="submit" value="Sign In" />
                        <p>Don't have an account? </p>
                    </form>
                    <form name="action" action="RegistrationController" method="POST">
                        <input type="submit" value="Register" name="action" />   
                    </form>
                </div>
            </div>
<%@include file="footer.jsp" %>
