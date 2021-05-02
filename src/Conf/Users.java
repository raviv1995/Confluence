/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conf;

import java.util.Scanner;
import java.io.*;
/**
 *
 * @author student
 */
public class Users extends FileHandler
{
    public String id;    
    Scanner sc;    
    public Users()
    {
        super("users.txt");
        id="";
        sc=new Scanner(System.in);
    }
    
    public int signIn(String user, String pass)
    {
        int r=0;
        for(String[] x: data)
        {
            if(x[1].equals(user) && x[2].equals(pass))
            {
                System.out.println("Sign In successful!");
                id=x[0];
                r=1;
                Constants.id=id;
                Constants.username=user;
                break;
            }
        }
        return r;
    }
    
    public int signIn()
    {
        pl("Input username: "); String user=sc.nextLine();
        pl("Input password: "); String pass=sc.nextLine();
        int r=this.signIn(user,pass);
        return r;
    }
    
    public int create()
    {
        pl("Input username: "); String user=sc.nextLine();
        pl("Input password: "); String pass=sc.nextLine();
        id=randomString(6);
        try
        {
            String basePath=Constants.filesPrefix+Constants.userPrefix+"/"+id+"/";
            File folder=new File(basePath);
            boolean success=folder.mkdirs();
           
            PrintWriter writer = new PrintWriter(basePath+"feeds.txt");
            pl("Input feed URL: "); String f1=sc.nextLine();
            pl("Input feed URL: "); String f2=sc.nextLine();
            writer.println(f1);
            writer.println(f2);
            writer.close();            
            
            writer = new PrintWriter(basePath+"keywords.txt");
            pl("Enter your keywords, seperated by spaces, in a single line"); 
            pl("Try to enter proper nouns only. Example:");
            pl("Narendra Modi London");
            String keys=sc.nextLine();
            writer.println(keys);
            writer.close();
            
            if(success)
                System.out.println("User Creation Successful");
            else
            {
                System.out.println("Error: User Creation Failed!");
                return 0;
            }
        }
        catch(Exception e)
        {
            pln(e.toString());
            return 0;
        }
        newData=id+","+user+","+pass;
        update();
        Constants.id=id;
        Constants.username=user;
        return 1;
    }
    
    public int create(String user, String pass, String keys, String[] feeds)
    {
        id=randomString(6);
        try
        {
            String basePath=Constants.filesPrefix+Constants.userPrefix+"/"+id+"/";
            File folder=new File(basePath);
            boolean success=folder.mkdirs();
           
            PrintWriter writer = new PrintWriter(basePath+"feeds.txt");
            for(String feed:feeds)
            {
                writer.println(feed);
            }
            writer.close();            
            
            writer = new PrintWriter(basePath+"keywords.txt");
            writer.println(keys);
            writer.close();
            
            if(success)
                System.out.println("User Creation Successful");
            else
            {
                System.out.println("Error: User Creation Failed!");
                return 0;
            }
        }
        catch(Exception e)
        {
            pln(e.toString());
            return 0;
        }
        newData=id+","+user+","+pass;
        update();
        Constants.id=id;
        Constants.username=user;
        return 1;
    }
    
    public void deleteUser()
    {
        deleteRecord(id);
    }
    
    public String getID()
    {
        return id;
    }
}
