/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Conf;

import static Conf.MainFrame.jButton1;
import static Conf.MainFrame.jPanel1;
import static Conf.Splash.jLabel2;
import com.google.gson.Gson;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author Ravi Vasista
 */
public class SplashScreen 
{
    public MainFrame form1 = new MainFrame();
    public Splash form2 = new Splash();
    public Settings form3 = new Settings();
    
    
    public static LinkedList <InnerFrame> inframe = new LinkedList();
    
    public SplashScreen()
    {
        ;
    }
    
    
     private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {
      is.close();
    }
  }
    
    
    
    
    void display()
    {
        LinkedList<ConfluentArticle> clusters=Constants.clusters;
        int n=clusters.size();    
        int i=0;                       
        form2.setVisible(false);
        form1.setExtendedState(JFrame.MAXIMIZED_BOTH);         
        form1.setVisible(true);
        Dimension k = new Dimension();
        k.width = 500;
        k.height = Constants.clusterHeight*n;
        jPanel1.setPreferredSize(k);
                
        try 
        {
            Image img = ImageIO.read(getClass().getResource("setting.png"));
            jButton1.setIcon(new ImageIcon(img));
            jButton1.setText(null);
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }            
        for(ConfluentArticle x:clusters)
        {
            int height=Constants.clusterHeight;
            InnerFrame add = new InnerFrame(x);  
            add.setResizable(true);
            add.setSize(370, 400);
            Dimension q = form1.getMaximumSize();
            add.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            add.setBounds(0, i*height, 370, height);                                            
            jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.PAGE_AXIS));                                        
            jPanel1.add(add);
            jPanel1.add(Box.createRigidArea(new Dimension(200,0)));
            q.height = height;
            add.setVisible(true);
            add.showArticles();
            inframe.add(add); 
            i++;
        }
    }
    
    public static void main(String[] args) throws IOException, JSONException
    {
        System.setProperty("java.net.useSystemProxies", "true");
        SplashScreen x=new SplashScreen();
        x.splash();
    }
    
    public void splash() throws IOException, JSONException 
    {        
        form2.getContentPane().setBackground(Color.BLACK);
        form1.getContentPane().setBackground(Color.WHITE);
        form2.setLayout(new BorderLayout());
        form2.add(form2.jLabel2, BorderLayout.SOUTH);
        form2.add(form2.jLabel1, BorderLayout.NORTH);
        form2.add(form2.jLabel3, BorderLayout.CENTER);
        form2.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        form2.setVisible(true);        
        Gson k = new Gson();
        
        JSONObject json = readJsonFromUrl("http://api.wunderground.com/api/d2726a4d41bfb00a/conditions/q/India/Udupi.json");
        System.out.println(json.toString());
        json = json.getJSONObject("current_observation");
        System.out.println(json.toString());
        System.out.println(json.get("temp_c"));
        String imgUrl = json.getString("icon_url");
        URL img = new URL(imgUrl);
        ImageIcon image = new ImageIcon(img);
        
        form1.jLabel2.setText("  Udupi : "+(String) json.get("temp_c").toString()+" °C");
        form1.jLabel3.setIcon(image);
        ActionListener actionListener;
        actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {                        
                jLabel2.setText("Gathering the feeds");                                                
            }
        };
        javax.swing.Timer timer = new javax.swing.Timer(2000, actionListener);
        timer.start();
        timer.setRepeats(false);                
        ActionListener actionListener2;
        actionListener2 = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {                        
                jLabel2.setText("Parsing the XML files");
            }
        };
        javax.swing.Timer timer2 = new javax.swing.Timer(4000, actionListener2);
        timer2.start();
        timer2.setRepeats(false);                
        
        actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {                        
                jLabel2.setText("Finalizing");    
            }
        };
        javax.swing.Timer timer3 = new javax.swing.Timer(6000, actionListener);
        timer3.start();
        timer3.setRepeats(false);

        actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {                                                                        
                jLabel2.setText("Let's Begin!");                          
            }
        };
        javax.swing.Timer timer4 = new javax.swing.Timer(8000, actionListener);
        timer4.start();
        timer4.setRepeats(false);


        ActionListener actionListener5;
        actionListener5 = new ActionListener() 
        {
            public void actionPerformed(ActionEvent actionEvent) 
            {
                //Confluence x=new Confluence();
                //display();                        
            }
        };
        javax.swing.Timer timer5;
        timer5 = new javax.swing.Timer(10000, actionListener5);
        timer5.start();
        timer5.setRepeats(false);


        jButton1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                form1.setVisible(false);
                form3.setExtendedState(JFrame.MAXIMIZED_BOTH); 
                form3.setVisible(true);

            }
        });

        form3.jButton28.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                form3.dispose();
                form1.setVisible(true);   
            }
        });
    }
}
