<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>
<%@include file="user-navigation.jsp" %>
<div class="bodyColumn">
    <%@include file="site-navigation.jsp" %>
    <!--the main body-->
    <div class="mainColumn">
        <section class="main_body">
            <!--breadcrumbs-->
            <h4><a href='index.jsp'>Home</a> 
                > Category
            </h4>

            <!--Category 1. Each categories call the servlet-->
            <article>
                <h3 class='greenSubheading'><a href="CatalogController?catalogCategory=man">Man's Clothing and Accessories</a></h3>
                <c:forEach var="itemVar" items="${itemDatabase}">
                    <c:if test='${itemVar.getCategory().equalsIgnoreCase("Man")}'>
                        <table class='category_table'>
                            <tbody>
                            <td><img src="images/<c:out value="${itemVar.getItemImage()}"/>" class='category_items' alt="<c:out value="${itemVar.getItemAlt()}"/>"></td>
                            <td><a href="CatalogController?itemCode=<c:out value="${itemVar.getItemCode()}"/>"><c:out value="${itemVar.getItemName()}"/></a><br></td>
                            </tr>    </tbody>
                        </table>
                    </c:if>          
                </c:forEach>
            </article> 

            <!--Category 2-->
            <article>

                <h3 class='greenSubheading'><a href="CatalogController?catalogCategory=woman">Woman's Clothing and Accessories</a></h3>
                <c:forEach var="itemVar" items="${itemDatabase}">
                    <c:if test='${itemVar.getCategory().equalsIgnoreCase("Woman")}'>
                        <table class='category_table'>
                            <tbody>
                                <tr>
                                    <td><img src="images/<c:out value="${itemVar.getItemImage()}"/>" class='category_items' alt="<c:out value="${itemVar.getItemAlt()}"/>"></td>
                                    <td><a href="CatalogController?itemCode=<c:out value="${itemVar.getItemCode()}"/>"><c:out value="${itemVar.getItemName()}"/></a><br></td>
                                </tr>    </tbody>
                        </table>
                    </c:if>          
                </c:forEach>
            </article> 

            <!--Category 3-->
            <article>
                <h3 class='greenSubheading'><a href="CatalogController?catalogCategory=baby">Baby's Clothing and Accessories</a></h3>
                <c:forEach var="itemVar" items="${itemDatabase}">
                    <c:if test='${itemVar.getCategory().equalsIgnoreCase("Baby")}'>                                    
                        <table class='category_table'>
                            <tbody>
                                <tr>
                                    <td><img src="images/<c:out value="${itemVar.getItemImage()}"/>" class='category_items' alt="<c:out value="${itemVar.getItemAlt()}"/>" ></td>
                                    <td><a href="CatalogController?itemCode=<c:out value="${itemVar.getItemCode()}"/>"><c:out value="${itemVar.getItemName()}"/></a><br></td>
                                </tr>    </tbody>
                        </table>
                    </c:if>          
                </c:forEach>
            </article> 
        </section>
    </div>
</div>

<%@include file="footer.jsp" %>


