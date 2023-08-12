<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bus Service</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<style>
       input[type=text], input[type=email], input[type=number], input[type=password]{
      width: 600px;
      border: 1px solid black;
    }
    select{
      width: 600px;
      border: 1px solid black;
      border-radius: 5px;
      height: 38px;
    }
    form {
        margin-left: 40px;;
    }
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
</head>
<body onload = "displaymessage()" >
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
	<!-- <h1 style="text-align:center;">Your Personal Info</h1>
	<br>
	<form action="read" method="post">
		Email : <input type="email" name="email" required><br>
		<input type="submit" value="Read" onclick="displaymessage()">
	</form>
	<hr>
	<h1 style="text-align:center;">Update Your Info</h1>
	<form action="update" method="post">
		Email : <input type="email" name="email" required><br>
		New Name : <input type="text" name="name"><br>
		New Mobile No : <input type="number" name="mobile"><br>
		New Email : <input type="email" name="nemail"><br>
		New Password : <input type="password" name="password"><br>
		<input type="submit" value="Update" onclick="displaymessage()">
	</form>
	<hr>
	<h1 style="text-align:center;">Delete the Profile</h1>
	<form action="delete" method="post">
		Email : <input type="email" name="email" required><br>
		<input type="submit" value="Delete">
	</form> -->
	
	<h1 style="text-align: center;">View your Details</h1>
    <form action='read' method="post">
        <button type="submit" class="btn btn-primary" name="read">See Details</button>
    </form>

    <hr>       
    <h1 style="text-align: center;">Update your Details</h1> 
        <form action='update' method="post">
            <div class="form-group">
                <label for="exampleInputPassword1">Enter Your Email ID :  </label>
                <input type="email" class="form-control" id="exampleInputPassword1" name="email" required>
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">New Email-ID</label>
                <input type="email" class="form-control" id="exampleInputPassword1" name="nemail">
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">New Name </label>
                <input type="text" class="form-control" id="exampleInputPassword1" name="name">
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">New Contact No </label>
                <input type="number" class="form-control" id="exampleInputPassword1" name="mobile">
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">New Password </label>
                <input type="password" class="form-control" id="exampleInputPassword1" name="password">
            </div>
            <button type="submit" class="btn btn-primary" name="update">Update</button>
        </form>
        
        <hr>    
        <h1 style="text-align: center;">Delete the Profile</h1>    
        <form action='delete' method="post">
            <div class="form-group">
                <label for="exampleInputPassword1">Enter Your Email ID :  </label>
                <input type="email" class="form-control" id="exampleInputPassword1" name="email" required>
            </div>
            <button type="submit" class="btn btn-primary" name="delete">Close Account</button>
        </form>
	
	<script>
	
		function displaymessage(){
			setTimeout(function() {
	    	<c:if test="${not empty message}">
	        	alert("${message}");
	    	</c:if>
		}, 200);
	    }
	
	</script>
</body>
</html>