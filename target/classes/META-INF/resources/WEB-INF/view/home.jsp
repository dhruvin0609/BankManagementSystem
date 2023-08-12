<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Banking System</title>
<style>
ul {
  list-style-type: none;
  margin: 0;
  padding: 0px;
  overflow: hidden;
  background-color: lightgray;
}

li {
  float: left;
    border-right: 1px solid blue;
}

li a {
  display: block;
  color: blue;
 font-size:20px;
  text-align: center;
  padding: 10px 20px;
  text-decoration: none;
}
.active{
background-color: gray;
color: white;
}
li a:hover {
  background-color: orange;
  color: white;
} 
nav{
margin-bottm:10px;
}
@import url(https://fonts.googleapis.com/css?family=Roboto:400,100,500,300italic,500italic,700italic,900,300);
</style>
<body onload = "displaymessage()">
<nav>
            	<ul>
            		<li>
                        <a class="active" href="home">Home</a>
                    </li>
                  
                    <%int rstl = (int)request.getAttribute("rslt"); %>
					<% 
					if(rstl== 0){%>	
					 <li >
                        <a class="nav-link" href="loan">Loan</a>
                    </li>
                    <%}else {%>
                     <li>
                        <a class="nav-link" href="loandetails">Loandetails</a>
                    </li>
                    <%} %>	
               		<li>
                        <a class="nav-link" href="showtransection">Showtransection</a>
                    </li>
                    <li>
                        <a class="nav-link" href="transfer">Transfer</a>
                    </li>
					<li>
                        <a class="nav-link" href="viewprofile">Profile</a>
                    </li>
                      <li >
                        <a  class="nav-link" href="logout">Logout</a>
                    </li>
            	</ul>
</nav><br>
Name : ${username }<br>

Balance: ${balance }<br>
<form action="deposit" method="POST">
Deposit: <input type="number" name="depositvalue" required>
<input type="submit" value="deposit">
</form>
<form action="withdraw" method="POST">
Withdraw: <input type="number" name="withdrawvalue" required>
<input type="submit" value="withdraw">
</form> 	
<%-- <form action="logout" method="POST">
<input type="submit" value="logout">
</form>
<%int rstl = (int)request.getAttribute("rslt"); %>
<% 
if(rstl== 0){%>	
<form action="loan" method="POST">
<input type="submit" value="loan">
</form>
<%}else {%>
<form action="loandetails" method="POST">
<input type="submit" value="loandetails">
</form>
<%} %>		
<form action="viewprofile" method = "POST">
<input type = "submit" name = "details" value = "details">
</form>
<form action="showtransection" method = "POST">
<input type = "submit"  value = "Showtransections">
</form>
<form action="transfer" method = "POST">
<input type="submit" value="Transfer">
</form>--%>

</body>
<script>
function displaymessage(){
	<c:if test="${not empty message}">
    	alert("${message}");
	</c:if>
	
	<c:if test="${not empty result}">
		alert("${result}");
	</c:if>
}
</script>
</html>