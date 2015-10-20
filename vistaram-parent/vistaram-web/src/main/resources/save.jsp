<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*"%>
<%try{

String email=request.getParameter("email");
String password=request.getParameter("password");

 String dbURL = "jdbc:derby://localhost/sample;create=true";
        String user = "app";
          String pwd = "app";

 Connection conn = DriverManager.getConnection(dbURL, user, pwd);
 PreparedStatement ps=conn.prepareStatement(  
     "insert into  company_mailer_user values(?,?,?)");

ps.setInt(1,2);
ps.setString(2,email);
ps.setString(3,password);

int s=ps.executeUpdate();
if(s>0){
%>
<jsp:forward page="admin.jsp"></jsp:forward>
<% 
}
else{
out.print("sorry!please fill correct detail and try again" );
}
}catch(SQLException e2){
out.print("sorry!please fill correct detail and try again" );
e2.printStackTrace();
}
%>

