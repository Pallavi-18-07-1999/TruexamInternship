package com.thinking.machines.registrations;
import com.thinking.machines.Connection.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import com.thinking.machines.beans.*;
import java.io.*;
public class RegisterInstructor extends HttpServlet
{
public void doGet(HttpServletRequest rq,HttpServletResponse rs)
{
try
{
String name=rq.getParameter("name");
String password=rq.getParameter("password");
System.out.println(name);
DAOConnection daoConnection=new DAOConnection();
Connection  con=daoConnection.getConnection();
PreparedStatement ps=con.prepareStatement("select * from instructors where name=?");
ps.setString(1,name);
ResultSet result_set=ps.executeQuery();
boolean instructorExists=false;
if(result_set.next()) instructorExists=true;
if(instructorExists)
{
System.out.println("instrcutor already exists");
rs.setContentType("application/json");
PrintWriter pw=rs.getWriter();
AJAXResponse response=new AJAXResponse();
response.success=false;
response.exception="User already exists";
response.response=null;
Gson gson=new Gson();
String responseString=gson.toJson(response);
System.out.println(responseString);
pw.print(responseString);
}
else
{
System.out.println("instrcutor doesnt exists");
ps=con.prepareStatement("insert into instructors(name,password) values(?,?)");
ps.setString(1,name);
ps.setString(2,password);
ps.executeUpdate();
rs.setContentType("application/json");
PrintWriter pw=rs.getWriter();
AJAXResponse response=new AJAXResponse();
response.success=true;
response.exception=null;
response.response="Instructor registered successfully....";
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