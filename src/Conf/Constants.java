/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conf;
import java.util.LinkedList;
import org.tartarus.snowball.ext.englishStemmer;
/**
 *
 * @author MAHE
 */
public class Constants 
{
    static String stopwords[]={"the","a","an","as","of","in","and","on","to","why","when","where","how","for",
        "at","by","with","this","he","she","his","her","there","has","video","say","from","new","be",
        "are","that","it","was","(reuters)","is"};
    static char symbols[]={'?','.','!',':','&',',','"','|'};
    
    static int matchesThreshold=3;
    static double diceThreshold=0.2;
    static double cosineThreshold=0.35;
    static String userKeywords[]={"spacex"};
    static int userKeyMultiplier=12;
    
    static double reDice=0.6;
    static double reCosine=0.75;
    
    static double minDice=0.15;
    static double minCosine=0.25;
    
    static final String filesPrefix="AppData/";
    static String userPrefix="UserData/";
    static String imgPrefix=filesPrefix+"Images/";
    
    static String id="";
    static String username="";
    
    static englishStemmer stemmer = new englishStemmer();
    static Users user=new Users();
    
    static LinkedList<ConfluentArticle> clusters=new LinkedList();
    
    static int clusterHeight=400;
    static int articleHeight=175;
    
    public static void stemUserKeys()
    {
        String temp[]=getRoots(userKeywords);
        userKeywords=temp;
    }
    
    public static String[] getRoots(String raw)
    {
        return getRoots(raw.split("[ ]"));
    }
    public static String[] getRoots(String[] words)
    {      
        String roots="";
        for(String stem:words)
        {
            stemmer.setCurrent(stem);
            if (stemmer.stem())
            {
                String root=stemmer.getCurrent();
                if(roots.contains(root)) ;
                else 
                    roots+=root.toLowerCase()+" ";                    
            }
        }
        return roots.split("[ ]");
    }
    public static String[] getRoots(LinkedList<String> raw)
    {       
        String roots="";
        for(String stem:raw)
        {
            stemmer.setCurrent(stem);
            if (stemmer.stem())
            {
                String root=stemmer.getCurrent();
                if(roots.contains(root)) ;
                else 
                    roots+=root+" ";                    
            }
        }
        return roots.split("[ ]");
    }
}
