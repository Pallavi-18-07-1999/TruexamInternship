package com.thinking.machines.instructors;
import com.thinking.machines.Connection.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.google.gson.*;
import java.util.*;
import com.thinking.machines.beans.*;
public class FetchTask extends HttpServlet
{
public void doGet(HttpServletRequest rq,HttpServletResponse rs)
{
try
{
System.out.println("Request arrrived...");
HttpSession session=rq.getSession();
String username=(String)session.getAttribute("username");
DAOConnection daoConnection=new DAOConnection();
Connection  con=daoConnection.getConnection();
PreparedStatement ps=con.prepareStatement("select * from tasks where instructor_name=?");
ps.setString(1,username);
ResultSet result_set=ps.executeQuery();
rs.setContentType("application/json");
PrintWriter pw=rs.getWriter();
AJAXResponse response=new AJAXResponse();
response.success=true;
response.exception=null;
List<String> list=new LinkedList<>();
while(result_set.next())
{
list.add(result_set.getString("task_name"));
}
response.response=list;
Gson gson=new Gson();
String responseString=gson.toJson(response);
pw.print(responseString);
System.out.println(responseString);
}catch(Exception e)
{
e.printStackTrace();
}
}
}