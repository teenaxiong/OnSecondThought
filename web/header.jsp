<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>On Second Thought Consignment Store and Boutique Swapping</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
        <link rel='stylesheet' type='text/css' href='main.css'>
        <link href="https://fonts.googleapis.com/css?family=Dancing+Script" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Gloria+Hallelujah" rel="stylesheet">

    </head>
    <body>

        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <div class="wrapper"> 

            <!--Header with logo and title-->
            <header>
                <div class="headerSection">
                    <img src="images/on_second_thought.png" alt="logo" id="logo">
                    <h1>Where swapping is a pleasure</h1>
                    <h5 class="welcome_user">Welcome, 
                        <c:choose>
                            <c:when test="${empty theUser}">
                                Guest
                            </c:when>
                            <c:otherwise>
                                <c:out value="${theUser.getUserFirstName()}"/></h5>
                                <div class="userAvgRating"> 
                                           <c:forEach begin="1" end="5" var="i" >
                                                <c:choose>
                                                    <c:when test="${avgUserRating>=i}">
                                                        <span class="myRating checked"></span>

                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="myRating"></span>
                                                    </c:otherwise>                                                    
                                                </c:choose>
                                            </c:forEach></div>
                                
                            </c:otherwise>
                        </c:choose>
                </div>
            </header>