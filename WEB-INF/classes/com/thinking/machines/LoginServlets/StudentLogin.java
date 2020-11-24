package com.thinking.machines.LoginServlets;
import com.thinking.machines.Connection.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import com.thinking.machines.beans.*;
import java.io.*;
public class StudentLogin extends HttpServlet
{
public void doGet(HttpServletRequest rq,HttpServletResponse rs)
{
try
{
String name=rq.getParameter("name");
String password=rq.getParameter("password");
DAOConnection daoConnection=new DAOConnection();
Connection  con=daoConnection.getConnection();
PreparedStatement ps=con.prepareStatement("select * from students where name=?");
ps.setString(1,name);
ResultSet result_set=ps.executeQuery();
boolean studentExists=false;
if(result_set.next())
{
studentExists=true;
}
if(studentExists)
{
HttpSession session=rq.getSession();
session.setAttribute("username",name);
session.setAttribute("password",password);
rs.setContentType("application/json");
PrintWriter pw=rs.getWriter();
AJAXResponse response=new AJAXResponse();
response.success=true;
response.exception=null;
response.response=null;
Gson gson=new Gson();
String responseString=gson.toJson(response);
System.out.println(responseString);
pw.print(responseString);
}
else
{
rs.setContentType("application/json");
PrintWriter pw=rs.getWriter();
AJAXResponse response=new AJAXResponse();
response.success=false;
response.exception="Given user doesnt exist"; 
response.response=null;
Gson gson=new Gson();
String responseString=gson.toJson(response);
System.out.println(responseString);
pw.print(responseString);
}
}catch(Exception e)
{
e.printStackTrace();
}
}
}