/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conf;

/**
 *
 * @author MAHE
 */
public class FeedArticle 
{
    String source;
    String title;
    String description;
    String link;
    String image;
    String roots[];
    
    public void print()
    {
        System.out.println(title);
        System.out.println(description);
        System.out.println(roots);
    }    
    
    public String source(){
        return source;
    }    
    public String title(){
        return title;
    }    
    public String description(){
        return description;
    }    
    public String link(){
        return link;
    }    
    public String image(){
        return image;
    }    
    public String[] roots(){
        return roots;
    }
}
