<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
</style>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
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
<form action = "ln" method = "POST">
	Loan type:<input type = "text" name="loantype" required><br>
	Amount:<input type = "number" name="amount"required><br>
	Duration:<input type = "number" name="duration" required><br>
	<input type="submit" value="submit">
	
</form>
</body>
</html>