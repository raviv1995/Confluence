/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conf;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author MAHE
 */
public class ConfluentArticle implements Comparable
{
    public LinkedList<FeedArticle> matchedArticles;
    int score;
    double cosine;
    double dice;
    int userMatches;
    int normalMatches;
    LinkedList<String> matchedOn;
    public ConfluentArticle()
    {
        matchedOn=new LinkedList();
        score=0;
        cosine=0;
        dice=0;
        userMatches=0;
        normalMatches=0;
        matchedArticles=new LinkedList();
    }
    
    public void addArticle(FeedArticle x)
    {
        if(x==null) return;
        matchedArticles.add(x);
        score++;
    }
    
    public void addMatchedOn(String in)
    {
        
        String inArray[]=in.split("[,]");
        int l=matchedOn.size();
        if(l<1)
        {
            matchedOn.addAll(Arrays.asList(inArray));
            return;
        }
        for (String word : inArray) 
        {
            boolean add=true;
            for(String present: matchedOn)
            {
                if(present.equals(word))
                {
                    add=false;
                    break;
                }
            }
            if(add)
                matchedOn.add(word);
        }
    }
    
    public void finalizeCluster()
    {
        int x=matchedOn.size();
        score+=x;
        score+=userMatches*Constants.userKeyMultiplier;
    }
    
    public void print()
    {
        String temp="";
        for(String x:matchedOn)
        {
            temp+=x+",";
        }
        System.out.println(temp);
        System.out.println(score);
        for(FeedArticle x:matchedArticles)
        {
            System.out.println(x.source+": "+x.link);
            System.out.println(x.title);
            System.out.println(x.description);
        }
        System.out.println("----------");
    }
    
    @Override
    public int compareTo(Object x)
    {
        ConfluentArticle y=(ConfluentArticle)x;
        return y.score-this.score;
    }
}
