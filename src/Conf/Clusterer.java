
package Conf;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class Clusterer 
{
    LinkedList<ConfluentArticle> clusters;
    LinkedList<String> sources;
    LinkedList<FeedReader> feeds;
    
    private String matchedOn="";
    private final String userKeywords[]=Constants.userKeywords;
    
    public Clusterer(LinkedList<String> sources)
    {
        clusters=new LinkedList();
        feeds=new LinkedList();
        this.sources=sources;
    }    
    
    public void loadData() throws Exception
    {
        for(String src:sources)
        {
            FeedReader feed;
            try
            {
                feed=new FeedReader(src);
                feed.setPrintMode(false);
                feed.main();
            }
            catch(Exception e)
            {
                System.out.println(e);
                continue;
            }
            feeds.add(feed);
        }
    }
    
    public void newCluster() throws Exception
    {
        loadData();        
        int counter=0;
        for(FeedReader currentFeed:feeds)
        {
            counter++;
            //Get list of articles in this feed
            LinkedList<FeedArticle> currentArticles=currentFeed.articles;
            Iterator<FeedArticle> i=currentArticles.iterator();            
                        
            //Compare this feed with all other feeds, including itself
            for(FeedReader otherFeed:feeds)
            {              
                //if(otherFeed==currentFeed)
                //    continue;                
                //Get list of all articles in this other feed
                LinkedList<FeedArticle> otherArticles=otherFeed.articles; 
                
                //Loop through all articles in this feed
                while(i.hasNext())
                {                   
                    //Get current article of this feed
                    FeedArticle current=i.next();                   
                    String[] currentRoots=current.roots;
                    
                    //List of possible clusters for this article of this feed
                    LinkedList<ConfluentArticle> possibleClusters=new LinkedList();                 
                    
                    ConfluentArticle x=new ConfluentArticle();
                    x.addArticle(current);
                    
                    //Check for user keyword matches
                    int userkeys=0;
                    LinkedList<String> userKeyMatches=new LinkedList();
                    for(String currentRoot: currentRoots)
                    {
                        for(String userKey:userKeywords)
                        {
                            if(userKey.equals(currentRoot))
                            {
                                //x.addMatchedOn(userKey);
                                userkeys++;
                                userKeyMatches.add(userKey);
                                System.out.println(">>>>>User keyword: "+userKey);
                            }
                        }
                    }
                    for(String keyMatch:userKeyMatches)
                    {
                        x.addMatchedOn(keyMatch);
                    }
                    x.userMatches+=userkeys;
                    //Loop through all articles of this other feed
                    Iterator<FeedArticle> j=otherArticles.iterator();
                    while(j.hasNext())
                    {             
                        //Get current article of other feed
                        FeedArticle other=j.next();                        
                        String[] otherRoots=other.roots; 
                        
                        if(current==other) //Don't check an article against itself
                            continue;
                        
                        LinkedList<VectorData> vectorMatrix=Scores.constructVectorMatrix(currentRoots, otherRoots);
                        double matches=Scores.matches(vectorMatrix);
                        this.matchedOn=Scores.matchedOn;
                        double dice=Scores.dice(vectorMatrix);
                        double cosine=Scores.cosine(vectorMatrix);
                        if(matches>=Constants.matchesThreshold)
                        {             
                            if(dice>=Constants.diceThreshold || cosine>=Constants.cosineThreshold)
                            {
                                x.addArticle(other);
                                x.addMatchedOn(matchedOn); 
                                x.finalizeCluster(); //Now or later?
                                possibleClusters.add(x); 
                                //if(otherFeed!=currentFeed)
                                    //j.remove();
                            }                            
                        }                        
                    }
                    
                    //If current artice formed any clusters, choose the best one
                    if(possibleClusters.size()>0)
                    {
                        Collections.sort(possibleClusters);
                        clusters.add(possibleClusters.getFirst());
                        //i.remove(); //Remove the current article from future comparisions
                    }
                    else 
                    {
                        if(counter==1)
                        {
                            /*ConfluentArticle single=new ConfluentArticle();
                            single.addArticle(current);
                            single.userMatches=userkeys;
                            single.finalizeCluster();
                            clusters.add(single);*/
                            x.finalizeCluster();
                            clusters.add(x);
                        }
                    }
                }
            }            
        }
        Collections.sort(clusters);
    }
    
    public void display()
    {
        System.out.println();
        System.out.println("Matched Articles:");
        for(ConfluentArticle x:clusters)
        {
            x.print();
        }
    }
    
    public int compare(String[] x1, String[] x2)
    {        
        int score=0;
        for(String y1:x1)
        {
            for(String y2:x2)
            {
                if(y1.equals(y2))
                {
                    matchedOn+=y1+",";
                    score++;
                }
            }            
        }
        return score;
    }
}
