package com.thinking.machines.instructors;
import com.thinking.machines.Connection.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.Base64.Encoder.*;
import com.google.gson.*;
import com.thinking.machines.beans.*;
public class GradeTask extends HttpServlet
{
public void doGet(HttpServletRequest rq,HttpServletResponse rs)
{
try
{
System.out.println("Request arrrived...");
HttpSession session=rq.getSession();
String username=(String)session.getAttribute("username");
String task_name=rq.getParameter("task_name");
session.setAttribute("task_name",task_name);
String path=getServletContext().getRealPath("/")+"WEB-INF"+File.separator+"responses"+File.separator+task_name+File.separator;
System.out.println(path);
File f=new File(path);
File[] files=f.listFiles();
rs.setContentType("application/json");
PrintWriter pw=rs.getWriter();
AJAXResponse response=new AJAXResponse();
response.success=true;
response.exception=null;
List<Task> list=new LinkedList<>();
for(int i=0;i<files.length;i++)
{
Task task=new Task();
System.out.println("chala");
task.stud_name=files[i].getName().substring(0,files[i].getName().indexOf("."));
byte[] fileContent = Files.readAllBytes(Paths.get(path+files[i].getName()));
String encodedString = java.util.Base64.getEncoder().encodeToString(fileContent);
task.encodedString=encodedString;
list.add(task);
}
response.response=list;
Gson gson=new Gson();
String responseString=gson.toJson(response);
pw.print(responseString);
}catch(Exception e)
{
e.printStackTrace();
}
}
}