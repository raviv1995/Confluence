/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conf;
import java.util.*;
import java.io.*;
/**
 *
 * @author student
 */
public class FileHandler
{
    FileInputStream fis;
    BufferedReader reader;
    LinkedList<String[]> data;
    //String data[][];
    String label;
    String labels[];
    String newData;
    String path;
    public FileHandler(String path)
    {
        newData = "***";
        this.path=Constants.filesPrefix+path;
        try
        {
            this.read();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    }
    
    public final void read() throws IOException
    {                       
        fis = new FileInputStream(path);
        reader = new BufferedReader(new InputStreamReader(fis));       
            
        label=reader.readLine();
        labels=label.split("[,]"); 
        data=new LinkedList();
        int c=0;
        String x;
        while ((x=reader.readLine()) != null)
        {
            
            String y[]=x.split("[,]");
            data.add(y);
        }
        fis.close();
        reader.close();
    }
    
    public void dump()
    {
        printer(labels);
        for (String[] x : data) {
            printer(x);
        }
    }
    
    public void dump(int skipIndex)
    {
        printer(labels, skipIndex);
        for (String[] x : data) {
            printer(x, skipIndex);
        }
    }
    
    public void printer(String a[])
    {
        for(String x:a)
        {
            System.out.print(x+" \t\t");
        }
        System.out.println();
    }
    
    public void printer(String a[], int skipIndex)
    {
        int c=0;
        for(String x:a)
        {
            if(c!=skipIndex)
                System.out.print(x+" \t\t");
            c++;
        }
        System.out.println();
    }
    
    public void printerLabels(String a[])
    {
        printer(labels);
        printer(a);
    }
    
    public int findID(String id)
    {
        int c=0;
        for(String x[]:data)
        {
            if(x[0].equals(id))
                return c;
            c++;
        }
        return -1;
    }
    
    public void update()
    {
        try
        {
        File file=new File(path);
        FileWriter writer=new FileWriter(file);
        writer.write(label+"\r\n");
        for (String[] data1 : data) 
        {
            String out="";
            for (String x : data1)
                    out+=x+",";
            writer.write(out+"\r\n");
        }
        
        if(!(newData.equals("***")))
            writer.write(newData);
        
        writer.flush();
        writer.close();
        newData="***";
        this.read();
        }
        catch(Exception e)
        {
            pln(e.toString());
        }
    }    
    
    public void deleteRecord(String id)
    {
        try
        {
        File file=new File(path);
        FileWriter writer=new FileWriter(file);
        writer.write(label+"\r\n");
        for(int i=0; i<data.size(); i++)
        {
            if(data.get(i)[0].equals(id))
                continue;
            
            String out="";
            for(String x:data.get(i))
                out+=x+",";
            writer.write(out+"\r\n");
        }
        writer.flush();
        writer.close();
        newData="***";
        this.read();
        }
        catch(Exception e)
        {
            pln(e.toString());
        }
    }
    
    public boolean deleteFile()
    {
        File x=new File(path);
        return x.delete();
    }
    
    public static void pln(String x)
    {
        System.out.println(x);
    }
    
    public static void pl(String x)
    {
        System.out.print(x);
    }
    
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static Random rnd = new Random();

    String randomString( int len )
    {
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ ) 
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }
    
    public String uniqueID()
    {
        String x=randomString(6);
        boolean isUnique=true;
        do
        {
            for(String a[]:data)
            {
                if(a[0].equals(x))
                {
                    isUnique=false;
                    break;
                }
            }
        }while(!isUnique);
        return x;
    }
}
