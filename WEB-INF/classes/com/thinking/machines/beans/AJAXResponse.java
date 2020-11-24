package com.thinking.machines.beans;
public class AJAXResponse  implements java.io.Serializable
{
public Object response;
public boolean success;
public Object exception;
public AJAXResponse()
{
response=null;
success=false;
exception=null;
}
}