package com.thinking.machines.Connection;
import java.sql.*;

public class DAOConnection
{
public static Connection con;
public String table;
public Connection getConnection()
{
try
{

Class.forName("com.mysql.cj.jdbc.Driver");
con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","Pallavi@12");
return con;

}catch(Exception e)
{
e.printStackTrace();
return null;
}
}

}

