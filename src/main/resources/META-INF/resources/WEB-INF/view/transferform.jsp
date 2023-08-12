<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
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
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body onload = "showmessage()">
<nav>
            	<ul>
            		<li>
                        <a class="active" href="home">Home</a>
                    </li>
                    	
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
<form action="Transfer" method="POST">
Account No: <input type="number" name="acnum" required>
Amount: <input type="number" name="amount" required>
<input type="submit" value="Transfer">
</form>
</body>
<script>

function showmessage(){
	<c:if test="${not empty msg}">
		alert("${msg}");
	</c:if>
}

</script>
</html>