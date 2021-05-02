/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conf;

import java.net.URL;
import java.util.*;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import org.tartarus.snowball.ext.englishStemmer;

/**
 *
 * @author student
 */
public class FeedReader {
    
    URL url;
    public SyndFeed feed;
    public LinkedList<FeedArticle> articles;
    public int count;
    static String[] remove=Constants.stopwords;
    static char[] symbols=Constants.symbols;
    boolean print;
    public FeedReader(String x)
    {
        print=true;
        try{
            System.out.println("Starting");
            url=new URL(x);
            System.out.println("URL OK, Getting Data...");
            articles=new LinkedList();
            count=0;
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    }
        
    public int main() throws Exception
    {
        SyndFeedInput input=new SyndFeedInput();
        feed = input.build(new XmlReader(url));
        String src=feed.getTitle();
        
        System.out.println("Feed " +src+ " Parsed Successfuly!");    
        
        Iterator entryIter = feed.getEntries().iterator();
        while (entryIter.hasNext())
        {
            count++;
            SyndEntry entry = (SyndEntry) entryIter.next();
            String title=entry.getTitle();
            String desc=entry.getDescription().getValue();
            //desc=desc.substring(0, desc.indexOf("<br"));
            desc=desc.replaceAll("\\<.*?>","").replaceAll("\\s+", " ");
            String temp=(title+" "+desc).toLowerCase();
            String words[]=temp.split("[ ]");            
            LinkedList<String> words2=new LinkedList();
            for(String word:words)
            {
                String cleanWord=removeSymbols(word);
                boolean add=true;
                for(String stopword:remove)
                {
                    if(cleanWord.equals(stopword))
                    {
                        add=false;
                        break;
                    }
                }
                if(add)
                    words2.add(cleanWord);
            }
                        
            FeedArticle x=new FeedArticle();
            x.title=title;
            x.description=desc;
            x.link=entry.getLink();
            x.image="";
            x.roots=Constants.getRoots(words2);
            x.source=src;
            if(print)
            {
                x.print();            
                System.out.println();
            }
            articles.add(x);
        }
        System.out.println(count + " Articles");
        System.out.println();
        return count;
    }
    
    public String removeSymbols(String x)
    {
        boolean add=true;
        String a=""; 
        int l=x.length();
        for(int i=0; i<l; i++)
        {
            char ch=x.charAt(i);
            for(char c:symbols)
            {
                if(ch==c)
                {
                    add=false;
                    break;
                }
            }
            if(add)
                a+=ch;
        }
        return a;
    }
    
    public void setPrintMode(boolean x)
    {
        print=x;
    }
    
    public static void main(String args[]) throws Exception
    {
        System.setProperty("java.net.useSystemProxies", "true");
        FeedReader x=new FeedReader("http://rss.cnn.com/rss/edition.rss");
        //FeedReader x=new FeedReader("http://feeds.bbci.co.uk/news/world/rss.xml");
        x.main();
    }    
}