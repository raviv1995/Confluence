/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;
import java.io.File;
import org.json.JSONException;
/**
 *
 * @author student
 */
public class Runner extends Thread
{
    LinkedList<String> sources;
    int proceed;
    Users user=Constants.user;
    
    public Runner() throws IOException
    {
        sources=new LinkedList();
        /*sources.add("http://rss.cnn.com/rss/edition.rss");
        sources.add("http://feeds.bbci.co.uk/news/world/rss.xml");
        sources.add("http://www.aljazeera.com/xml/rss/all.xml");
        sources.add("http://feeds.reuters.com/reuters/INworldNews?format=xml");
        sources.add("http://timesofindia.indiatimes.com/rssfeeds/296589292.cms");*/
        /*sources.add((new File("feeds/bbc.xml")).toURI().toURL().toString());
        sources.add((new File("feeds/cnn.rss")).toURI().toURL().toString());        
        sources.add((new File("feeds/aljazeera.xml")).toURI().toURL().toString());
        sources.add((new File("feeds/reuters.xml")).toURI().toURL().toString());
        sources.add((new File("feeds/toi.rss")).toURI().toURL().toString());*/
        menu();
    }
    
    public Runner(int proceed)
    {
        this.proceed=proceed;
    }
    
    public void begin() throws IOException, JSONException
    {       
        final SplashScreen ui=new SplashScreen();
        ui.splash();

        ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) 
        { 
            try 
            {
                if(proceed==-1)
                    useDefaultFeeds();
                else
                    useUserFeeds(Constants.id);
 
                Constants.stemUserKeys();
                Clusterer x=new Clusterer(sources);
                x.newCluster();
                ReCluster y=new ReCluster(x.clusters);
                y.combineIntersect(); 
                Constants.clusters=x.clusters;
                ui.display();
                //x.display();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
                            
        }
        };
        javax.swing.Timer timer7 = new javax.swing.Timer(10000, actionListener);
        timer7.start();
        timer7.setRepeats(false);  
    }
    
    public final void menu() throws IOException
    {
        Scanner sc=new Scanner(System.in);       
        while(proceed==0)
        {
            System.out.println("1. Sign In 2. Create User 3. Skip for now");
            int choice=sc.nextInt();
            if(choice==1)
            {
                proceed=user.signIn();
                if(proceed==0)
                System.out.println("Wrong username or password. Try again.");
            }
            else if(choice==2)
            {
                proceed=user.create();
                if(proceed==0)
                System.out.println("Something went wrong. Try again.");
            }
            else
                proceed=-1;
        }
    }
    
    public void useDefaultFeeds() throws IOException
    {
        sources=new LinkedList();
        FileInputStream fis = new FileInputStream(Constants.filesPrefix+"defaultFeeds.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        String x;
        while ((x=reader.readLine()) != null)
        {
            if(x.contains("http"))
                sources.add(x);
            else
                sources.add((new File(x)).toURI().toURL().toString());
        }
        fis.close();
        reader.close();
    }
    
    public void useUserFeeds(String id) throws IOException
    {
        sources=new LinkedList();
        FileInputStream fis = new FileInputStream(Constants.filesPrefix+Constants.userPrefix+id+"/feeds.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        String x;
        while ((x=reader.readLine()) != null)
        {
            if(x.contains("http"))
                sources.add(x);
            else
                sources.add((new File(x)).toURI().toURL().toString());
        }
        
        fis = new FileInputStream(Constants.filesPrefix+Constants.userPrefix+id+"/keywords.txt");
        reader = new BufferedReader(new InputStreamReader(fis));
        Constants.userKeywords=reader.readLine().split("[ ]");
        
        fis.close();
        reader.close();
    }
    
    public static void main(String args[]) throws Exception
    {
        System.setProperty("java.net.useSystemProxies", "true");
        Runner run=new Runner();
        run.begin();
    }
}
