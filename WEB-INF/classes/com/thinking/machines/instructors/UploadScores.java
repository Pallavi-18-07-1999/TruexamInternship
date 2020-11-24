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
public class UploadScores extends HttpServlet
{
public void doPost(HttpServletRequest rq,HttpServletResponse rs)
{
try
{
System.out.println("Upload Score Request arrrived...");
BufferedReader br = rq.getReader();
String line;
StringBuilder sb=new StringBuilder();
while(true)
{
line=br.readLine();
if(line!=null) sb.append(line);
else break;
}
String str=sb.toString();
System.out.println(str);
Gson gson=new Gson();
Score score=(Score)gson.fromJson(str,Score.class);
System.out.println(score.scores.size());
HttpSession session=rq.getSession();
String username=(String)session.getAttribute("username");
String task_name=(String)session.getAttribute("task_name");
DAOConnection daoConnection=new DAOConnection();
Connection con=daoConnection.getConnection();
String path=getServletContext().getRealPath("/")+"WEB-INF"+File.separator+"responses"+File.separator+task_name+File.separator;
System.out.println(path);
File f=new File(path);
File[] files=f.listFiles();
PreparedStatement ps=con.prepareStatement("select * from responses where task_name=? and instructor_name=? and username=?");
PreparedStatement ps1=con.prepareStatement("insert into scores(stud_name,task_name,instructor_name,score) values(?,?,?,?)");
for(String gr:score.stud_name)
{
int k=0;
for(int i=0;i<files.length;i++)
{
if(files[i].getName().substring(0,files[i].getName().indexOf(".")).equals(gr))
{
ps1.setString(1,gr);
ps1.setString(2,task_name);
ps1.setString(3,(String)session.getAttribute("username"));
ps1.setInt(4,score.scores.get(k));
ps1.executeUpdate();
k+=1;
}
}
}

rs.setContentType("application/json");
PrintWriter pw=rs.getWriter();
AJAXResponse response=new AJAXResponse();
response.success=true;
response.exception=null;
response.response="Scores uploaded successfully";
gson=new Gson();
String responseString=gson.toJson(response);
pw.print(responseString);
}catch(Exception e)
{
e.printStackTrace();
}
}
}