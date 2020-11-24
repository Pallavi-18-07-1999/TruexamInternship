package com.thinking.machines.instructors;
import com.thinking.machines.Connection.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.thinking.machines.beans.*;
import com.google.gson.*;
import java.nio.file.*;
public class CreateTask extends HttpServlet
{
public void doPost(HttpServletRequest rq,HttpServletResponse rs)
{
try
{
System.out.println("Request arrrived...");
HttpSession session=rq.getSession();
String username=(String)session.getAttribute("username");
System.out.println(username);
String info=rq.getParameter("info");
String name_of_file=rq.getParameter("name_of_file");
String path=getServletContext().getRealPath("/")+"WEB-INF"+File.separator+"uploads"+File.separator;
System.out.println(path);
String task_name=rq.getParameter("name");
Part part=rq.getPart("filename");
if(part==null) System.out.println("it is null");
String cd=part.getHeader("content-disposition");
String[] pcs=cd.split(";");
for(String pc:pcs)
{
System.out.println("chala");
System.out.println(pc);
if(pc.trim().startsWith("filename"))
{
System.out.println(pc);
String fn=pc.substring(pc.indexOf("=")+2,pc.length()-1);
File file=new File(path+name_of_file);
if(file.exists()) file.delete();
part.write(path+name_of_file);
System.out.println(path+name_of_file);
}

}
byte[] fileContent = Files.readAllBytes(Paths.get(path+name_of_file));
String encodedString = java.util.Base64.getEncoder().encodeToString(fileContent);

DAOConnection daoConnection=new DAOConnection();
Connection  con=daoConnection.getConnection();
PreparedStatement ps=con.prepareStatement("insert into tasks(instructor_name,task_name,info,encoded_string) values(?,?,?,?)");
ps.setString(1,username);
ps.setString(2,task_name);
ps.setString(3,info);
ps.setString(4,encodedString);
ps.executeUpdate();
rs.setContentType("application/json");
PrintWriter pw=rs.getWriter();
AJAXResponse response=new AJAXResponse();
response.success=true;
response.exception=null;
response.response="Task created !";
Gson gson=new Gson();
String responseString=gson.toJson(response);
pw.print(responseString);
}catch(Exception e)
{
e.printStackTrace();
}
}
}