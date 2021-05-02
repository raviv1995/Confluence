/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conf;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class ReCluster 
{
    LinkedList<ConfluentArticle> initial;
    public ReCluster(LinkedList<ConfluentArticle> initial)
    {
        this.initial=initial;
    }
    
    public void combineIntersect()
    {
        for(int i=0; i<initial.size(); i++)
        {
            ConfluentArticle current=initial.get(i);
            LinkedList<FeedArticle> currentArticles=current.matchedArticles;
            Iterator<ConfluentArticle> j=initial.iterator();  
            while(j.hasNext())
            {
                
                boolean wasMerged=false;
                ConfluentArticle other=j.next();
                if(current==other)
                    continue;
                LinkedList<FeedArticle> otherArticles=other.matchedArticles;
                for(FeedArticle a:currentArticles)
                {
                    for(FeedArticle b:otherArticles)
                    {
                        if(a.title.equals(b.title))
                        {
                            mergeClusters(current,other);
                            wasMerged=true;
                            System.out.println(">>>>>>Something was Removed");
                            System.out.println(">>>>>>"+b.title);
                            break;
                        }
                    }
                    if(wasMerged)
                        break;
                }
                if(wasMerged)
                    j.remove();
            }
        }
    }
    
    public void mergeClusters(ConfluentArticle retain, ConfluentArticle discard)
    {
        FeedArticle retainArticles[]=new FeedArticle[retain.matchedArticles.size()]; 
        retain.matchedArticles.toArray(retainArticles);
        
        for(FeedArticle y:discard.matchedArticles)
        {
            if(arrayHasElement(retainArticles,y))
                continue;
            retain.addArticle(y);
        }    
    }
    
    public boolean arrayHasElement(FeedArticle[] check, FeedArticle against)
    {
        for(FeedArticle element:check)
        {
            if(element.title.equals(against.title))
                return true;
        }
        return false;
    }
}
