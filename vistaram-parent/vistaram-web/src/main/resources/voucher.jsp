<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Vistaram Services..</title>
<link href="style2.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function ctck()
{
var sds = document.getElementById("dum");

}
</script>

</head>

<body>

<div id="top_links">
  

<div id="header">
	<h1>VISTARAM <span class="style1"></span></h1>
    <h2>ExtraOrdinary Service</h2>
    <A href="index.html"><IMG SRC="images/home1.gif"></IMG></A>	
</div>

<div id="navigation">
    <ul>
    <li><a href="create.html">New Agents</a></li>
    <li><a href="create.html">Existing Agents</a></li>
    <li><a href="deposit1.jsp">Deals</a></li>
    <li><a href="transfer1.jsp">Status</a></li>
     <li><a href="transfer1.jsp">Complaints</a></li>
    <li><a href="closeac1.jsp">Contact Us</a></li>
    <li><a href="about.jsp">Logout</a></li>
    </ul>
</div>



<table style="width:897px; background:#FFFFFF; margin:0 auto;">
<tr >
	<td width="300" valign="top" style="border-right:#666666 1px dotted;">
    	<div id="services"><h1>Services</h1><br>
		    <ul>
    <li><a href="voucher.jsp">Voucher Details</a></li>
     <li><a href="create.html">Reservations</a></li>
      <li><a href="create.html">Rewards</a></li>


    
           
            </ul>
			
       </div>
	</td>
    
    <td width="1200" valign="top">
        <div id="welcome" style="border-right:#666666 1px dotted;"><h1>VOUCHER DETAILS</h1><br>
    	    <table  align="center" bgcolor="white">
		<tr>
		
		</tr>
		<tr>
			<td><div><%if(request.getAttribute("balance")!=null)
			{
			out.print(request.getAttribute("balance"));
			}
			
			 %></div>
				<form name=F1 onSubmit="return dil(this)" action="admin.jsp" >
				   <table cellspacing="5" cellpadding="3">	
				  <tr><td>VOUCHER NO:</td><td> <input type="text" name="accountno"/></td></tr>
					<tr><td>USER NAME:</td><td> <input type="text" name="username"/></td></tr>
					<tr><td>BOOKING DATE</td><td> <input type="password" name="password"/></td></tr>
					
					<tr><td></td><td><input type="submit" value="Submit"/>
					
                   
                   <INPUT TYPE=RESET VALUE="CLEAR"></td></tr>
             	</table>
				</form>
			</td>
		</tr>
	</table>
    	   </div>      

    	
    	<% 
%>
<table>
    
</table><%
%>
    	
    	
		</table>


<%@ page import="java.sql.*"%>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*"%>
  
